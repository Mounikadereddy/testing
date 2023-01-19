/*
 * Spouse.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.coreframework.xom;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 *
 * @since March 1, 2011
 * @version 1.0
 * @author Omar.Gaye
 *
 **/
public class Spouse extends Dependent {

    private static final long serialVersionUID = 7020744013681787038L;

    private boolean     isVet;
    private String      fileNumber;

    public boolean isVet() {
        return isVet;
    }

    public void setVet(final boolean isVet) {
        this.isVet = isVet;
    }

    public String getFileNumber() {
        return fileNumber;
    }

    public void setFileNumber(final String fileNumber) {
        this.fileNumber = fileNumber;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .appendSuper(super.toString())
                .append( "vet",             isVet )
                .append( "file number",     fileNumber )
                .toString();
    }

}
