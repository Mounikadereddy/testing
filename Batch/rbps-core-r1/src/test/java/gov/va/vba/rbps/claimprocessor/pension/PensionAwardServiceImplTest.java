package gov.va.vba.rbps.claimprocessor.pension;

import gov.va.vba.rbps.claimprocessor.pension.exception.DependentHasIncomeException;
import gov.va.vba.rbps.claimprocessor.pension.exception.FailedToCreateIncomeDecisionException;
import gov.va.vba.rbps.claimprocessor.pension.exception.InvalidAdjustmentException;
import gov.va.vba.rbps.claimprocessor.pension.exception.NetWorthException;
import gov.va.vba.rbps.claimprocessor.util.ConnectionFactory;
import gov.va.vba.rbps.builder.XomBuilder;

import gov.va.vba.rbps.coreframework.util.SimpleDateUtils;
import gov.va.vba.rbps.coreframework.xom.Child;
import gov.va.vba.rbps.coreframework.xom.ChildType;
import gov.va.vba.rbps.coreframework.xom.DependentDecisionType;
import gov.va.vba.rbps.coreframework.xom.DependentStatusType;
import gov.va.vba.rbps.coreframework.xom.Marriage;
import gov.va.vba.rbps.coreframework.xom.MarriageTerminationType;
import gov.va.vba.rbps.coreframework.xom.Spouse;
import gov.va.vba.rbps.coreframework.xom.Veteran;

import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.naming.NamingException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;


@RunWith(PowerMockRunner.class)
@PrepareForTest({ConnectionFactory.class})
public class PensionAwardServiceImplTest {

    private static Connection connection = mock(Connection.class);
    private static CallableStatement cs = mock(CallableStatement.class);
    private PensionAwardService pensionAwardService = new PensionAwardServiceImpl();

    @Before
    public void setUp() throws SQLException, NamingException {
        mockStatic(ConnectionFactory.class);
        when(ConnectionFactory.getConnection("wbvms")).thenReturn(connection);
        when(connection.prepareCall("{call ws_award_data_prc_lv.verifyCplDepNoincome(?,?,?)}")).thenReturn(cs);
    }

    @Test
    public void createDecision() throws SQLException, FailedToCreateIncomeDecisionException, DependentHasIncomeException, NetWorthException, InvalidAdjustmentException, NamingException {
        String json = "{\n" +
                "   \"return_code\":0,\n" +
                "   \"message\":\"No existing net worth decision. rows inserted: 1\",\n" +
                "   \"vet_id\":\"12960359\",\n" +
                "   \"efctv_date\":\"2020-02-12\",\n" +
                "   \"net_worth_limit\":\"129094\",\n" +
                "   \"net_worth_limit_date\":\"2019-12-01\",\n" +
                "   \"net_worth_amt\":\"0\",\n" +
                "   \"net_worth_over_limit\":false,\n" +
                "   \"net_worth_is_a_bar\":false,\n" +
                "   \"deps_with_income\":[]\n" +
                "}";
        when(cs.getString(3)).thenReturn(json);
        Veteran vet = XomBuilder.createVeteran("123456789", "John", "DOE");
        vet.setCorpParticipantId(1215215L);
        setUpDependents(vet);

        pensionAwardService.createIncomeDecision(vet);

    }

    @Test(expected=DependentHasIncomeException.class)
    public void createDecision_WithDepHasIncomeFromReturn() throws SQLException, FailedToCreateIncomeDecisionException, DependentHasIncomeException, InvalidAdjustmentException, NetWorthException {
        String json = "{\n" +
                "   \"return_code\":3,\n" +
                "   \"message\":\"No existing net worth decision. rows inserted: 1\",\n" +
                "   \"vet_id\":\"12960359\",\n" +
                "   \"efctv_date\":\"2020-02-12\",\n" +
                "   \"net_worth_limit\":\"129094\",\n" +
                "   \"net_worth_limit_date\":\"2019-12-01\",\n" +
                "   \"net_worth_amt\":\"0\",\n" +
                "   \"net_worth_over_limit\":false,\n" +
                "   \"net_worth_is_a_bar\":false,\n" +
                "   \"deps_with_income\":[\"102442\"]\n" +
                "}";
        when(cs.getString(3)).thenReturn(json);
        Veteran vet = XomBuilder.createVeteran("123456789", "John", "DOE");
        vet.setCorpParticipantId(1215215L);
        setUpDependents(vet);

        pensionAwardService.createIncomeDecision(vet);

    }

    @Test(expected=DependentHasIncomeException.class)
    public void createDecision_WithDepHasIncomeFromInput() throws SQLException, FailedToCreateIncomeDecisionException, DependentHasIncomeException, NetWorthException, InvalidAdjustmentException {
        String json = "{\n" +
                "   \"return_code\":0,\n" +
                "   \"message\":\"No existing net worth decision. rows inserted: 1\",\n" +
                "   \"vet_id\":\"12960359\",\n" +
                "   \"efctv_date\":\"2020-02-12\",\n" +
                "   \"net_worth_limit\":\"129094\",\n" +
                "   \"net_worth_limit_date\":\"2019-12-01\",\n" +
                "   \"net_worth_amt\":\"0\",\n" +
                "   \"net_worth_over_limit\":false,\n" +
                "   \"net_worth_is_a_bar\":false,\n" +
                "   \"deps_with_income\":[]\n" +
                "}";
        when(cs.getString(3)).thenReturn(json);
        Veteran vet = XomBuilder.createVeteran("123456789", "John", "DOE");
        vet.setCorpParticipantId(1215215L);
        setUpDependents(vet);
        vet.getChildren().get(0).setHasIncome(true);

        pensionAwardService.createIncomeDecision(vet);

    }

    @Test(expected=NetWorthException.class)
    public void createDecision_WithNetWorthIsAbar() throws SQLException, FailedToCreateIncomeDecisionException, DependentHasIncomeException, NetWorthException, InvalidAdjustmentException {
        String json = "{\n" +
                "   \"return_code\":3,\n" +
                "   \"message\":\"No existing net worth decision. rows inserted: 1\",\n" +
                "   \"vet_id\":\"12960359\",\n" +
                "   \"efctv_date\":\"2020-02-12\",\n" +
                "   \"net_worth_limit\":\"129094\",\n" +
                "   \"net_worth_limit_date\":\"2019-12-01\",\n" +
                "   \"net_worth_amt\":\"0\",\n" +
                "   \"net_worth_over_limit\":false,\n" +
                "   \"net_worth_is_a_bar\":true,\n" +
                "   \"deps_with_income\":[]\n" +
                "}";
        when(cs.getString(3)).thenReturn(json);
        Veteran vet = XomBuilder.createVeteran("123456789", "John", "DOE");
        vet.setCorpParticipantId(1215215L);
        setUpDependents(vet);

        pensionAwardService.createIncomeDecision(vet);

    }


    @Test(expected=NetWorthException.class)
    public void createDecision_WithNetWorthOverLimitFromInput() throws SQLException, FailedToCreateIncomeDecisionException, DependentHasIncomeException, NetWorthException, NamingException, InvalidAdjustmentException {
        String json = "{\n" +
                "   \"return_code\":0,\n" +
                "   \"message\":\"No existing net worth decision. rows inserted: 1\",\n" +
                "   \"vet_id\":\"12960359\",\n" +
                "   \"efctv_date\":\"2020-02-12\",\n" +
                "   \"net_worth_limit\":\"129094\",\n" +
                "   \"net_worth_limit_date\":\"2019-12-01\",\n" +
                "   \"net_worth_amt\":\"0\",\n" +
                "   \"net_worth_over_limit\":false,\n" +
                "   \"net_worth_is_a_bar\":false,\n" +
                "   \"deps_with_income\":[]\n" +
                "}";
        when(cs.getString(3)).thenReturn(json);
        Veteran vet = XomBuilder.createVeteran("123456789", "John", "DOE");
        vet.setCorpParticipantId(1215215L);
        vet.setNetWorthOverLimit(true);
        setUpDependents(vet);

        pensionAwardService.createIncomeDecision(vet);

    }

    @Test(expected=NetWorthException.class)
    public void createDecision_WithNetWorthOverLimitFromReturn() throws SQLException, FailedToCreateIncomeDecisionException, DependentHasIncomeException, NetWorthException, NamingException, InvalidAdjustmentException {
        String json = "{\n" +
                "   \"return_code\":3,\n" +
                "   \"message\":\"No existing net worth decision. rows inserted: 1\",\n" +
                "   \"vet_id\":\"12960359\",\n" +
                "   \"efctv_date\":\"2020-02-12\",\n" +
                "   \"net_worth_limit\":\"129094\",\n" +
                "   \"net_worth_limit_date\":\"2019-12-01\",\n" +
                "   \"net_worth_amt\":\"0\",\n" +
                "   \"net_worth_over_limit\":true,\n" +
                "   \"net_worth_is_a_bar\":false,\n" +
                "   \"deps_with_income\":[]\n" +
                "}";
        when(cs.getString(3)).thenReturn(json);
        Veteran vet = XomBuilder.createVeteran("123456789", "John", "DOE");
        vet.setCorpParticipantId(1215215L);
        setUpDependents(vet);

        pensionAwardService.createIncomeDecision(vet);

    }

    @Test(expected=FailedToCreateIncomeDecisionException.class)
    public void createDecision_WithSpouseRemoval() throws SQLException, FailedToCreateIncomeDecisionException, DependentHasIncomeException, NetWorthException, InvalidAdjustmentException {
        String json = "{\n" +
                "   \"return_code\":0,\n" +
                "   \"message\":\"No existing net worth decision. rows inserted: 1\",\n" +
                "   \"vet_id\":\"12960359\",\n" +
                "   \"efctv_date\":\"2020-02-12\",\n" +
                "   \"net_worth_limit\":\"129094\",\n" +
                "   \"net_worth_limit_date\":\"2019-12-01\",\n" +
                "   \"net_worth_amt\":\"0\",\n" +
                "   \"net_worth_over_limit\":false,\n" +
                "   \"net_worth_is_a_bar\":false,\n" +
                "   \"deps_with_income\":[]\n" +
                "}";
        when(cs.getString(3)).thenReturn(json);
        Veteran vet = XomBuilder.createVeteran("123456789", "John", "DOE");
        vet.setCorpParticipantId(1215215L);
        setUpDependents(vet);

        vet.getCurrentMarriage().getMarriedTo().getAward().setDependencyDecisionType(DependentDecisionType.DEATH);

        pensionAwardService.createIncomeDecision(vet);

    }


    private void setUpDependents(Veteran veteran) {
        Child child1 = XomBuilder.createChild(ChildType.BIOLOGICAL_CHILD, veteran);
        child1.setAward(XomBuilder.createAward(DependentDecisionType.ELIGIBLE_MINOR_CHILD, DependentStatusType.SCHOOL_CHILD, SimpleDateUtils.parseInputDate("01/01/2020")));
        child1.setHasIncome(false);
        child1.setCorpParticipantId(102422L);

        Child child2 = XomBuilder.createChild(ChildType.BIOLOGICAL_CHILD, veteran);
        child2.setAward(XomBuilder.createAward(DependentDecisionType.ELIGIBLE_MINOR_CHILD, DependentStatusType.SCHOOL_CHILD, SimpleDateUtils.parseInputDate("02/01/2020")));
        child2.setHasIncome(false);
        child2.setCorpParticipantId(102442L);

        Spouse spouse = XomBuilder.createSpouse("123458754", true, false, XomBuilder.createAward(DependentDecisionType.MARRIAGE, DependentStatusType.SPOUSE, SimpleDateUtils.parseInputDate("02/01/2015")));
        spouse.setAward(XomBuilder.createAward(DependentDecisionType.MARRIAGE, DependentStatusType.SPOUSE, SimpleDateUtils.parseInputDate("01/01/2020")));
        spouse.setHasIncome(false);
        spouse.setCorpParticipantId(1024552L);

        Marriage marriage = XomBuilder.createMarriage(SimpleDateUtils.parseInputDate("01/01/2020"), MarriageTerminationType.DIVORCE, spouse);

        veteran.addChild(child1);
        veteran.addChild(child2);
        veteran.setCurrentMarriage(marriage);
    }
}