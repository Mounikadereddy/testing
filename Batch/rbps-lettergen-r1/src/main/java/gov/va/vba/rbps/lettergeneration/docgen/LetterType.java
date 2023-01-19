package gov.va.vba.rbps.lettergeneration.docgen;

public enum  LetterType {
    RBPS_LETTER("Letter Document"),
    MILITARY_APPROVAL_DENIAL("Approval Denial Military Letter Document"),
    APPROVAL("Approval Letter Document"),
    DENIAL("Denial Letter Document"),
    MILITARY_APPROVAL("Approval Military Letter Document"),
	AWARD_PRINT("Edoc");

    private final String label;

    LetterType(String documentId) {
        this.label = documentId;
    }

    public String getLabel() {
        return label;
    }
}
