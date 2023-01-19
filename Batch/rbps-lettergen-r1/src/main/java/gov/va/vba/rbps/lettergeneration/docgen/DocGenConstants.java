package gov.va.vba.rbps.lettergeneration.docgen;

public final class DocGenConstants {
    public static final String MESSAGE_PREFIX = "DocGen - ";
    public static final String CONNECTION_FAILED = "Unable to connect to DocGen Service @[%(url)]. Please make sure url is correct and service is available.";
    public static final String DOCUMENT_DOWNLOAD_FAILED = "Failed to download document from DocGen service: %(error)";
    public static final String USI_PARSE_FAILED = "Failed to parse usi from response %(error)";
    public static final String MISSING_JWT = "Please provide jwt before requesting document";
}
