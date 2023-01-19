package gov.va.vba.rbps.rulesengine.child;

public class ChildMessages {
    public static final String SET_SCHOOL_ALLOWABLE_DATE_TO_MARRIAGE = " School Allowable date is set to the marriage date of veteran: ";
    public static final String SET_SCHOOL_ALLOWABLE_DATE = " School Allowable date is set to the allowable date of veteran ";
    public static final String MISSING_DOB = "Auto Dependency Processing Reject Reason - Child : %s, %s DOB was not provided. Please review.";
    public static final String MISSING_FIRST_NAME = "Auto Dependency Processing Reject Reason - Child First Name is not provided. Please review.";
    public static final String MISSING_LAST_NAME = "Auto Dependency Processing Reject Reason - Child Last Name is not provided. Please review.";
    public static final String MISSING_VETERAN_MARRIAGE_DATE_FOR_STEP_CHILD = "Auto Dependency Processing Reject Reason - Date of marriage of the Veteran was not provided for stepchild. Please review";
    public static final String CHILD_PREVIOUSLY_MARRIED = "Auto Dependency Processing Reject Reason - Child : %s, %s reported as previously married. Please review.";
    public static final String BIOLOGICAL_CHILD_NO_MAILING_ADDRESS = "Auto Dependency Processing Reject Reason - Child : %s, %s reported as not living with Veteran but no address for child was submitted. Please review";
    public static final String CHILD_EVENT_DATE_VERIFICATION_FAILURE = "Auto Dependency Processing Reject Reason - Date of Veteran's rating is between 366 and 372 days in the past. Please review date of notification letter to determine correct date to add child : %s, %s .";
    public static final String ADOPTED_CHILD = "Auto Dependency Processing Reject Reason - Child : %s, %s reported as adopted. Please develop for adoption paperwork.";
    public static final String CHILD_SERIOUSLY_DISABLED = "Auto Dependency Processing Reject Reason - Child : %s, %s reported as seriously disabled. Please review.";
    public static final String MARRIED_CHILD = "Auto Dependency Processing Reject Reason - Child : %s, %s reported as married. Please review.";
    public static final String SSN_NOT_PROVIDED = "Auto Dependency Processing Reject Reason - Child : %s, %s SSN was not provided. Please review.";
    public static final String PRIOR_DATE_IS_BEFORE_DATE_OF_SCHOOL_TERM = "Auto Processing Dependency Processing Reject - Begin date of prior school term is before award effective date for school attendance begins.Please Review.";
    public static final String MISSING_SCHOOL_NAME = "Auto Dependency Processing Reject Reason - Child : %s , %s for prior school term - School Name is missing. Please review.";
    public static final String LAST_TERM_NOT_VALID = "last term not valid";
    public static final String MISSING_SCHOOL_ADDRESS = "Auto Dependency Processing Reject Reason - Child : %s , %s for prior school term - School address is missing. Please review.";
    public static final String PRIOR_TERM_INCLUDED_IN_CURRENT_AWARD = "RBPS ignoring prior term as it is included in current award for prior school child : %s , %s. Please review.";
    public static final String LAST_TERM_SAME_AS_CURRENT = "last term  not valid. it is same as current term";
    public static final String CHILD_COURSE_EDUCATION_VERIFICATION = "Auto Dependency Processing Reject Reason - Child : %s, %s's current course name is not provided. Please review.";
    public static final String CHILD_SCHOOL_ADDRESS_VERIFICATION = "Auto Dependency Processing Reject Reason - Child : %s, %s's current school address is not provided. Please review.";
    public static final String CHILD_SCHOOL_NAME_VERIFICATION = "Auto Dependency Processing Reject Reason - Child : %s, %s's current school name is not provided. Please review.";
    public static final String TUITION_PAID_BY_GOVT = "Auto Dependency Processing Reject Reason - Reported that child : %s, %s's tuition/allowance is being paid by US government. Please review.";
    public static final String HOME_SCHOOLED_CHECK = "Auto Dependency Processing Reject Reason - Child : %s, %s reported as being home schooled. Please develop for needed evidence.";
    public static final String MISSING_COURSE_START_DATE = "Auto Dependency Processing Reject Reason - Dependent : %s, %s current course start date was not provided. Please review.";
    public static final String PART_TIME = "Auto Dependency Processing Reject Reason - Child : %s, %s reported as attending school less than 3 sessions/hours per week. Please review.";
    public static final String NO_EVENT_DATE_MINOR_CHILD = "Auto Dependency Processing Reject Reason - RBPS could not set the event date for minor child : %s, %s Please review.";
    public static final String NO_EVENT_DATE_SCHOOL_CHILD = "Auto Dependency Processing Reject Reason - RBPS could not set the event date for school child : %s, %s Please review.";
    public static final String NO_EVENT_DATE_MINOR_TO_SCHOOL_CHILD = "Auto Dependency Processing Reject Reason - RBPS could not set the event date for minor to school child : %s, %s Please review.";
    public static final String NO_EVENT_DATE_PRIOR_SCHOOL_CHILD = "Auto Dependency Processing Reject Reason - RBPS could not set the event date for prior school child : %s, %s Please review.";
    public static final String INVALID_PRIOR_SCHOOL_TERM = "Auto Processing Dependency Processing Reject - Prior school term submitted is shorter than on the award and would result in school attendance not being continuous. Please Review.";
}
