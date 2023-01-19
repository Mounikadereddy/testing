package gov.va.vba.rbps.claimprocessor.pension;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.claimprocessor.pension.exception.DependentHasIncomeException;
import gov.va.vba.rbps.claimprocessor.pension.exception.FailedToCreateIncomeDecisionException;
import gov.va.vba.rbps.claimprocessor.pension.exception.InvalidAdjustmentException;
import gov.va.vba.rbps.claimprocessor.pension.exception.NetWorthException;
import gov.va.vba.rbps.claimprocessor.util.ClaimProcessorHelper;
import gov.va.vba.rbps.claimprocessor.util.ConnectionFactory;
import gov.va.vba.rbps.claimprocessor.util.OmnibusFlagHelper;
import gov.va.vba.rbps.coreframework.xom.Award;
import gov.va.vba.rbps.coreframework.xom.Dependent;
import gov.va.vba.rbps.coreframework.xom.DependentDecisionType;
import gov.va.vba.rbps.coreframework.xom.Veteran;
import gov.va.vba.rbps.coreframework.xom.util.RbpsXomUtil;

import javax.naming.NamingException;
import java.io.IOException;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class PensionAwardServiceImpl implements PensionAwardService {
    private static Logger logger = Logger.getLogger(PensionAwardServiceImpl.class);

    @Override
    public void createIncomeDecision(Veteran veteran) throws FailedToCreateIncomeDecisionException, DependentHasIncomeException, NetWorthException, InvalidAdjustmentException {
        if (veteran.isNetWorthOverLimit()) {
            throw new NetWorthException(PensionConstants.NET_WORTH_OVER_LIMIT);
        }

        Map<Long,Date> dependentInput = new HashMap<>();

        for(Dependent dependent :  getDependents(veteran)) {
            Award award = dependent.getAward();

            if (!awardIsReady(award)) {
                continue;
            }

            logger.debug("Dependent: " + dependent.getLastName() + ", " + dependent.getFirstName());
            logger.debug("Award info:  " + award.toString());

            Boolean isDenial = ClaimProcessorHelper.isDenial(award);

            if (isSpouseRemoval(award) && dependent.hasIncome()) {
                throw new FailedToCreateIncomeDecisionException(PensionConstants.PENSION_WITH_SPOUSE_REMOVAL);
            }

            if(!isDenial) {
                if (dependent.hasIncome()) {
                    throw new DependentHasIncomeException(PensionConstants.DEPENDENT_HAS_INCOME);
                }
                String flag = OmnibusFlagHelper.getOmnibusFlag(veteran, award, award.getEventDate());
                Date effectiveDate = flag.equalsIgnoreCase("Y") ? RbpsXomUtil.getOmnibusedDate(award.getEventDate()) : award.getEventDate();
                dependentInput.put(dependent.getCorpParticipantId(), effectiveDate);
            }

        }

        if (!dependentInput.isEmpty()) {
            IncomeDecisionReturn results = createIncomeDecision(veteran.getCorpParticipantId(), dependentInputToString(dependentInput));
            verifyResults(dependentInput, results);
            veteran.setNetWorthLimit(results.getNetWorthLimit());
        } else {
            veteran.setNetWorthLimit(BigDecimal.ZERO);
        }
    }

    /**
     * @param award
     * @return
     */
    private boolean awardIsReady(final Award award) {

        if ( award == null ) {

            return false;
        }

        if ( award.getDependencyDecisionType() == null ) {

            return false;
        }

        if ( award.getDependencyStatusType() == null ) {

            return false;
        }

        if (award.getEventDate() == null) {

            return false;
        }

        return true;
    }
    /**
     * @param dependentInput
     * @param results
     * @throws NetWorthException
     * @throws DependentHasIncomeException
     * @throws FailedToCreateIncomeDecisionException
     * @throws InvalidAdjustmentException
     */
    private void verifyResults(final Map<Long, Date> dependentInput, final IncomeDecisionReturn results) throws NetWorthException, DependentHasIncomeException, FailedToCreateIncomeDecisionException, InvalidAdjustmentException {
        logger.debug(results.toString());

        if (results.getReturnCode() != 0) {
            if (results.isNetWorthABar()) {
                throw new NetWorthException(PensionConstants.NET_WORTH_IS_A_BAR);
            }

            if (results.isNetWorthOverLimit()) {
                throw new NetWorthException(PensionConstants.NET_WORTH_OVER_LIMIT);
            }

            if (results.getDepsWithIncome() != null && results.getDepsWithIncome().length > 0) {
                throw new DependentHasIncomeException(PensionConstants.DEPENDENT_HAS_INCOME);
            }

            throw new FailedToCreateIncomeDecisionException(results.getMessage());
        }

        verifyNetWorthLimitDate(dependentInput, results.getNetWorthLimitDate());
    }

    /**
     * @param dependentInput
     * @param netWorthLimitDate
     * @throws InvalidAdjustmentException
     */
    private void verifyNetWorthLimitDate(final Map<Long, Date> dependentInput, Date netWorthLimitDate) throws InvalidAdjustmentException {
        for (Map.Entry<Long, Date> entry : dependentInput.entrySet()) {
            if (entry.getValue().before(netWorthLimitDate)) {
                throw new InvalidAdjustmentException(PensionConstants.INVALID_ADJUSTMENT);
            }
        }
    }

    /**
     * @param vetId
     * @param depString
     * @return
     * @throws FailedToCreateIncomeDecisionException
     */
    private IncomeDecisionReturn createIncomeDecision(Long vetId, String depString) throws FailedToCreateIncomeDecisionException {
        Connection connection = null;
        try {
            //connection = ConnectionFactory.getConnection("jdbc/vbms/CorpDB");
        	connection = ConnectionFactory.getConnection("jdbc/wbrbps/CorpDB");
            CallableStatement cs = connection.prepareCall("{call ws_award_data_prc.verifyCplDepNoincome(?,?,?)}");
            cs.registerOutParameter(3, Types.VARCHAR);
            cs.setLong(1, vetId);
            cs.setString(2,depString);
            cs.execute();

            String jsonReturn = cs.getString(3);
            ObjectMapper objectMapper = new ObjectMapper();
            return  objectMapper.readValue(jsonReturn, IncomeDecisionReturn.class);
        } catch (IOException e) {
            throw new FailedToCreateIncomeDecisionException("Unable to parse results for SP: call ws_award_data_prc.verifyCplDepNoincome: " + e.getMessage());
        } catch (SQLException e) {
            throw new FailedToCreateIncomeDecisionException("Failed to create income decision: " + e.getMessage());
        } catch (NamingException e) {
            throw new FailedToCreateIncomeDecisionException("Failed to retrieve datasource: " + e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new FailedToCreateIncomeDecisionException("Unable to close connection: " + e.getMessage());
                }
            }
        }
    }

    /**
     * @param veteran
     * @return
     */
    private List<Dependent> getDependents(final Veteran veteran) {
        
        List<Dependent> dependents = new ArrayList<>();
        dependents.addAll(veteran.getChildren());

        if(veteran.getCurrentMarriage() != null && veteran.getCurrentMarriage().getEndDate() == null) {
            dependents.add(veteran.getCurrentMarriage().getMarriedTo());
        }

        if (veteran.getLatestPreviousMarriage() != null) {
            dependents.add(veteran.getLatestPreviousMarriage().getMarriedTo());
        }

        return dependents;
    }

    /**
     * @param award
     * @return
     */
    private boolean isSpouseRemoval(final Award award) {
        return  (award.getDependencyDecisionType().equals(DependentDecisionType.DEATH) || award.getDependencyDecisionType().equals(DependentDecisionType.MARRIAGE_TERMINATED));
    }

    /**
     * @param dependentInput
     * @return
     */
    private String dependentInputToString(final Map<Long,Date> dependentInput) {

        Format formatter = new SimpleDateFormat("yyyy-MM-dd");
        String separator = ",";
        Iterator<Map.Entry<Long, Date>> depInputIterator = dependentInput.entrySet().iterator();
        StringBuffer buffer = new StringBuffer();

        while (depInputIterator.hasNext()) {

            Map.Entry<Long, Date> entry = depInputIterator.next();

            buffer.append("I:" + entry.getKey())
                    .append(separator)
                    .append(formatter.format(entry.getValue()));

            if (depInputIterator.hasNext()) {
                buffer.append(separator);
            }
        }
        String finalInput = buffer.toString();
        logger.debug("Dependent input for income decision: " + finalInput);
        return finalInput;
    }
}
