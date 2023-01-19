/*
 * SojSignatureDetails.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.services.populators.utils;



/**
 *      This class is used to holds the name and title
 *      of the approval/ denial letter signatory.
 */
public class SojSignatureDetails {


    private String signature;
    private String title;



    public String getSignature() {

        return signature;
    }
    public void setSignature(final String signature) {

        this.signature = signature;
    }


    public String getTitle() {

        return title;
    }
    public void setTitle(final String title) {

        this.title = title;
    }
}
