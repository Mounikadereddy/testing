package gov.va.vba.rbps.rulesengine.spouse;

public class SpouseMessages {
    public static final String DOB_NOT_PROVIDED = "Auto Dependency Processing Reject Reason - Spouse DOB was not provided. Please review.";
    public static final String FIRST_NAME_NOT_PROVIDED = "Auto Dependency Processing Reject Reason - Spouse First Name is not provided. Please review.";
    public static final String LAST_NAME_NOT_PROVIDED = "Auto Dependency Processing Reject Reason - Spouse Last Name is not provided. Please review.";
    public static final String ALREADY_ON_EXISTING_AWARD = "Auto Dependency Processing Reject Reason - Spouse is already existing on award. Please review.";
    public static final String MARRIAGE_VERIFICATION_FAIL = "Auto Dependency Processing Reject Reason - Submitted spouse's prior marriage date(s) do not terminate before date of current marriage. Please review.";
    public static final String SSN_VERIFICATION_FAIL = "Auto Dependency Processing Reject Reason - Spouse SSN is not provided. Please review.";

    public static final String DIVORCE_WITH_CHILDREN_DECISION = "Auto Dependency Processing Validation Reject Reason - Removal of spouse due to divorce with children on award. Please Review for Stepchild Status.";
    public static final String BEFORE_AWARD_EFFECTIVE_DATE = "Auto Dependency Processing Validation Reject Reason - Attempted spouse removal date is before spouse's add date. Please review";

    public static final String SPOUSE_WITH_NO_EVENT_RULE = "Auto Dependency Processing Reject Reason - RBPS could not set the event date for spouse. Please review.";
}
