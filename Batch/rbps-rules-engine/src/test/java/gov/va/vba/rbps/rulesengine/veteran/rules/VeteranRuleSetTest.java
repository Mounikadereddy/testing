package gov.va.vba.rbps.rulesengine.veteran.rules;

import gov.va.vba.rbps.rulesengine.mockobjectbuilders.VeteranObjectBuilder;
import gov.va.vba.rbps.rulesengine.veteran.VeteranMessages;
import gov.va.vba.rbps.coreframework.xom.Marriage;
import gov.va.vba.rbps.coreframework.xom.RuleExceptionMessages;
import gov.va.vba.rbps.coreframework.xom.Veteran;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class VeteranRuleSetTest {

    private Veteran veteran;
    private VeteranRuleSet ruleSet;
    private RuleExceptionMessages exceptionMessages;

    @Before
    public void setUp() {

        exceptionMessages = new RuleExceptionMessages();


        System.out.println("\n\nSetting Mock Veteran Object to default values");

        veteran = new Veteran();
        veteran = VeteranObjectBuilder.createVeteran();
        veteran.setLatestPreviousMarriage(VeteranObjectBuilder.createPreviousSpouse());
        veteran.setPreviousMarriages(new ArrayList<Marriage>() {{ add(veteran.getLatestPreviousMarriage()); }});
        veteran.setCurrentMarriage(VeteranObjectBuilder.createCurrentSpouse());
        veteran.setChildren(VeteranObjectBuilder.createChildren(veteran));
        ruleSet = new VeteranRuleSet(veteran, exceptionMessages);

    }

    @Test
    public void testAttachmentCheck() {
        System.out.println("Running Attachment Check. No exceptions expected.");

        ruleSet.attachmentCheck();

        System.out.println("\nException(s):");
        System.out.println(exceptionMessages.getMessages().toString());
        Assert.assertFalse(exceptionMessages.getMessages().contains(VeteranMessages.CONTAIN_ATTACHMENTS));
    }

    @Test
    public void testAttachmentCheck_WithException() {
        System.out.println("Running Attachment Check. Exception message is expected.");
        System.out.println("Setting claim attachments to true.");
        veteran.getClaim().setHasAttachments(true);

        ruleSet.attachmentCheck();

        System.out.println("\nException(s):");
        System.out.println(exceptionMessages.getMessages().toString());
        Assert.assertTrue(exceptionMessages.getMessages().contains(VeteranMessages.CONTAIN_ATTACHMENTS));
    }

    @Test
    public void testUSResidenceVerification() {
        System.out.println("Running US Residence Verification test. No exceptions expected.");

        ruleSet.USResidenceVerification();

        System.out.println("\nException(s):");
        System.out.println(exceptionMessages.getMessages().toString());
        Assert.assertFalse(exceptionMessages.getMessages().contains(VeteranMessages.ADDRESS_NOT_PROVIDED));
    }

    @Test
    public void testUSResidenceVerification_WithException() {
        System.out.println("Running US Residence Verification test. Exception message is expected.");
        System.out.println("Setting Mailing address to null.");
        veteran.setMailingAddress(null);

        ruleSet.USResidenceVerification();

        System.out.println("\nException(s):");
        System.out.println(exceptionMessages.getMessages().toString());
        Assert.assertTrue(exceptionMessages.getMessages().contains(VeteranMessages.ADDRESS_NOT_PROVIDED));
    }

    @Test
    public void testIsVeteranAlive() {
        System.out.println("Running Is Veteran alive test. No exceptions expected.");

        ruleSet.isVeteranAlive();

        System.out.println("\nException(s):");
        System.out.println(exceptionMessages.getMessages().toString());
        Assert.assertFalse(exceptionMessages.getMessages().contains(VeteranMessages.VETERAN_NOT_LIVING));
    }

    @Test
    public void testIsVeteranAlive_WithException() {
        System.out.println("Running Is Veteran alive test. Exception message is expected.");
        System.out.println("Setting isAlive to false.");
        veteran.setAlive(false);

        ruleSet.isVeteranAlive();

        System.out.println("\nException(s):");
        System.out.println(exceptionMessages.getMessages().toString());
        Assert.assertTrue(exceptionMessages.getMessages().contains(VeteranMessages.VETERAN_NOT_LIVING));
    }

    @Test
    public void testAwardChangesVerification() {
        System.out.println("Running Award Changes Verification test. No exceptions expected.");

        ruleSet.awardChangesVerification();

        System.out.println("\nException(s):");
        System.out.println(exceptionMessages.getMessages().toString());
        Assert.assertFalse(exceptionMessages.getMessages().contains(VeteranMessages.RATING_CHANGES));
    }

    @Test
    public void testAwardChangesVerification_WithException() {
        System.out.println("Running Award Changes Verification test. Exception message is expected.");
        System.out.println("Setting Rating Changes to true.");
        veteran.getAwardStatus().setProposed(true);

        ruleSet.awardChangesVerification();

        System.out.println("\nException(s):");
        System.out.println(exceptionMessages.getMessages().toString());
        Assert.assertTrue(exceptionMessages.getMessages().contains(VeteranMessages.RATING_CHANGES));
    }

    @Test
    public void testAwardCurrentStatusVerification() {
        System.out.println("Running Award Current Status Verification test. No exceptions expected.");

        ruleSet.awardCurrentStatusVerification();

        System.out.println("\nException(s):");
        System.out.println(exceptionMessages.getMessages().toString());
        Assert.assertFalse(exceptionMessages.getMessages().contains(VeteranMessages.AWARD_NOT_CURRENT_OR_SUSPENDED));
    }

    @Test
    public void testAwardCurrentStatusVerification_WithException() {
        System.out.println("Running Award Current Status Verification test. Exception message is expected.");
        System.out.println("Setting Suspended to true.");
        veteran.getAwardStatus().setIsSuspended(true);

        ruleSet.awardCurrentStatusVerification();

        System.out.println("\nException(s):");
        System.out.println(exceptionMessages.getMessages().toString());
        Assert.assertTrue(exceptionMessages.getMessages().contains(VeteranMessages.AWARD_NOT_CURRENT_OR_SUSPENDED));
    }

    @Test
    public void testAwardGAOUsageVerification() {
        System.out.println("Running Award GAO Usage Verification test. No exceptions expected.");

        ruleSet.awardGAOUsageVerification();

        System.out.println("\nException(s):");
        System.out.println(exceptionMessages.getMessages().toString());
        Assert.assertFalse(exceptionMessages.getMessages().contains(VeteranMessages.AWARD_USED_GAO));
    }

    @Test
    public void testAwardGAOUsageVerification_WithException() {
        System.out.println("Running Award GAO Usage Verification test. Exception message is expected.");
        System.out.println("Setting GAO used to true.");
        veteran.getAwardStatus().setGAOUsed(true);

        ruleSet.awardGAOUsageVerification();

        System.out.println("\nException(s):");
        System.out.println(exceptionMessages.getMessages().toString());
        Assert.assertTrue(exceptionMessages.getMessages().contains(VeteranMessages.AWARD_USED_GAO));
    }

    @Test
    public void testAttorneyFeeAgreementCheck() {
        System.out.println("Running Attorney Fee Agreement test. No exceptions expected.");

        ruleSet.attorneyFeeAgreementCheck();

        System.out.println("\nException(s):");
        System.out.println(exceptionMessages.getMessages().toString());
        Assert.assertFalse(exceptionMessages.getMessages().contains(VeteranMessages.ATTORNEY_FEE_CASE));
    }

    @Test
    public void testAttorneyFeeAgreementCheck_WithException() {
        System.out.println("Running Attorney Fee Agreement test. Exception message is expected.");
        System.out.println("Setting Attorney Fee Agreement Check to true.");
        veteran.getAwardStatus().setHasAttorneyFeeAgreement(true);

        ruleSet.attorneyFeeAgreementCheck();

        System.out.println("\nException(s):");
        System.out.println(exceptionMessages.getMessages().toString());
        Assert.assertTrue(exceptionMessages.getMessages().contains(VeteranMessages.ATTORNEY_FEE_CASE));
    }

    @Test
    public void testNumberOfDependentsCheck() {
        System.out.println("Running Number of Dependents Check test. No exceptions expected.");

        ruleSet.numberOfDependentsCheck();

        System.out.println("\nException(s):");
        System.out.println(exceptionMessages.getMessages().toString());
        Assert.assertFalse(exceptionMessages.getMessages().contains(VeteranMessages.DEPENDENT_LIMIT_EXCEEDED));
    }

    @Test
    public void testNumberOfDependentsCheck_WithExceptions() {
        System.out.println("Running Number of Dependents Check test. Exception message is expected.");
        System.out.println("Setting Veteran dependents to 21.");
        veteran.setTotalNumberOfDependents(21);

        ruleSet.numberOfDependentsCheck();

        System.out.println("\nException(s):");
        System.out.println(exceptionMessages.getMessages().toString());
        Assert.assertTrue(exceptionMessages.getMessages().contains(VeteranMessages.DEPENDENT_LIMIT_EXCEEDED));
    }
}