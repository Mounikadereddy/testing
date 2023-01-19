package gov.va.vba.rbps.claimprocessor.pension;

import gov.va.vba.rbps.claimprocessor.pension.exception.DependentHasIncomeException;
import gov.va.vba.rbps.claimprocessor.pension.exception.FailedToCreateIncomeDecisionException;
import gov.va.vba.rbps.claimprocessor.pension.exception.InvalidAdjustmentException;
import gov.va.vba.rbps.claimprocessor.pension.exception.NetWorthException;
import gov.va.vba.rbps.coreframework.xom.Veteran;

public interface PensionAwardService {


    /**
     * @param veteran
     * @throws FailedToCreateIncomeDecisionException
     */
    void createIncomeDecision(Veteran veteran) throws FailedToCreateIncomeDecisionException, DependentHasIncomeException, NetWorthException, InvalidAdjustmentException;
}
