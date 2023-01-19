/*
 * ClaimLabelType.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.coreframework.xom;


import java.util.Arrays;
import java.util.List;


/**
 *      Claim label enum: use to update claim label
 */
public enum ClaimLabelType {

    NEW_686C("Automated Dependency 686c", "130DPNDCYAUT"),
    REJECTED_686C("Automated Dependency 686c Reject", "130DPNAUTREJ"),

    NEW_674("Automated School Attendance 674", "130SCHATTAUT"),
    REJECTED_674("Automated School Attendance 674 Reject", "130SCHAUTREJ"),

    NEW_PMC_686C("PMC Automated Dependency 686c", "130DPNPMCAUT"),
    REJECTED_PMC_686C("PMC Automated Dependency 686c Reject", "130DPMCAUREJ"),

    NEW_PMC_674("PMC Automated School Attendance 674", "130SCHPMCAUT"),
    REJECTED_PMC_674("PMC Automated School Attendance 674 Reject", "130SCPMAUREJ"),

    NEW_EBENEFITS_686C("eBenefits Dependency Adjustment", "130DPNEBNADJ"),
    REJECTED_EBENEFITS_686C("eBenefits Dependency Adjustment Reject", "130DPEBNAJRE"),

    NEW_EBENEFITS_674("eBenefits School Attendance", "130SCHATTEBN"),
    REJECTED_EBENEFITS_674("eBenefits School Attendance Reject", "130SCHEBNREJ"),

    NEW_PHONE_DEPENDENCY_ADJUSTMENT("Phone Dependency Adjustment", "130PDA" ),
    NEW_PHONE_DEPENDENCY_ADJUSTMENT_EXCEPTION("Phone Dependency Adjustment Exception", "130PDAE" ),
    
    NEW_PHONE_SCHOOL_ATTENDANCE("Phone School Attendance", "130PSA" ),
    NEW_PHONE_SCHOOL_ATTENDANCE_EXCEPTION("Phone School Attendance Exception", "130PSAE" ),
    
    
    /* SStephen -3/28/17  RTC - 488006
     * Update Claim Labels and Claim Type Codes for 21-686c and 21-674 Claims - Java changes
     */
    NEW_D2D_DEPNDANCY_ADJUSTMENT("D2D-Dependency Adjustment", "130DAD2D"),
    REJECTED_D2D_DEPNDANCY_ADJUSTMENT("D2D-Dependency Adjustment Reject", "130DARD2D"),
    
    NEW_D2D_SCHOOL_ATTENDANCE("D2D-School Attendance", "130SAD2D"),
    REJECTED_D2D_SCHOOL_ATTENDANCE("D2D-School Attendance Reject", "130SARD2D"),
    
    NEW_D2D_PENSION_DEPNDANCY_ADJUSTMENT("D2D-Dependency Adjustment", "130PDAD2D"),
    NEW_D2D_PENSION_SCHOOL_ATTENDANCE("D2D-Pension School Attendance", "130PSAD2D"),
    
	/** add new claim types ***/
    NEW_686C_DEPENDENCY_ADJ_REMOVAL("Self Service - Removal of Dependent", "130SSRD" ),
	NEW_686C_DEPENDENCY_ADJ_REMOVAL_EXCEPTION("Self Service - Removal of Dependent Exception", "130SSRDE" ),

	NEW_686C_PMC_DEPENDENCY_ADJ_REMOVAL("PMC - Self Service - Removal of Dependent", "130SSRDPMC" ),
	NEW_686C_PMC_DEPENDENCY_ADJ_REMOVAL_EXCEPTION("PMC - Self Service - Removal of Dependent Exception", "130SSRDPMCE" );


    private final List<String> values;

    private ClaimLabelType(final String... values) {
        this.values = Arrays.asList(values);
    }

    public static ClaimLabelType find(final String name) {

        for (ClaimLabelType label : ClaimLabelType.values()) {

            if (label.values.contains(name)) {
                return label;
            }
        }

        return null;
    }

    /**
     * @deprecated
     * @param name
     * @return
     */
    @Deprecated
    public static ClaimLabelType findReject(final String name) {
        return findReject(find(name));
    }

    public static ClaimLabelType findReject(final ClaimLabelType label) {

        if (label != null) {
            for (ClaimLabelType labels : ClaimLabelType.values()) {
                /**
                 * if the label passed in is a reject label already, or
                  *if the label passed in is a non-reject that has a matching reject label,
                  *return the reject label
                **/
                if ((labels.equals(label) && labels.getValue().trim().endsWith("Reject")) ||
                		(labels.equals(label) && labels.getValue().trim().endsWith("Exception")) ||
                        (labels.getValue().contains(label.getValue() + " Reject")) ||
                        (labels.getValue().contains(label.getValue() + " Exception")) ) {
                    return labels;
                }
            }
        }

        return null;
    }

    public String getValue() {
        return values.get(0);
    }

    public String getCode() {
        return values.get(1);
    }

}
