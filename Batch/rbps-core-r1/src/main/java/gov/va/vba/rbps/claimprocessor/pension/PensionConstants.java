package gov.va.vba.rbps.claimprocessor.pension;

public final class PensionConstants {
    private PensionConstants() {}

    public static final String DEPENDENT_HAS_INCOME =        "PMC - Dependent has income, development required.";
    public static final String NET_WORTH_OVER_LIMIT =        "PMC - Net worth exceeds limit, development required.";
    public static final String NET_WORTH_IS_A_BAR =          "PMC - Net worth previously a bar, development required.";
    public static final String INVALID_ADJUSTMENT =          "PMC - Adjustment involves two calendar years, review of income and net worth required.";
    public static final String PENSION_WITH_SPOUSE_REMOVAL = "PMC - Spouse is being removed";

}
