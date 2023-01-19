package gov.va.vba.rbps.lettergeneration.docgen.exception;

import gov.va.vba.rbps.coreframework.exception.RbpsRuntimeException;

import static gov.va.vba.rbps.lettergeneration.docgen.DocGenConstants.MESSAGE_PREFIX;

public class DocGenException extends RbpsRuntimeException {
    public DocGenException(String message) {
        super(message);
    }
}
