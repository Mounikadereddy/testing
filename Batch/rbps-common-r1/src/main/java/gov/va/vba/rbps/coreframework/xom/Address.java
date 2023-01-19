/*
 * Address.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.coreframework.xom;


import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


public class Address implements Serializable {

    private static final long serialVersionUID = -5076641093378060018L;

    private String line1;
    private String line2;
    private String line3;
    private String city;
    private String country;
    private String state;
    private String zipPrefix;
    private String zipSuffix1;
    private String zipSuffix2;
    /*
    // new field for ccr 2032.
    
    MLTY_POSTAL_TYPE_CD      VARCHAR2(12)  Y                         
    MLTY_POST_OFFICE_TYPE_CD VARCHAR2(12)  Y
    */
    
    private String mltyPostalTypeCd;
    private String mltyPostOfficeTypeCd;
    

    public String getState() {
        return state;
    }

    public void setState(final String state) {
        this.state = state;
    }

    public String getZipPrefix() {
        return zipPrefix;
    }

    public void setZipPrefix(final String zipPrefix) {
        this.zipPrefix = zipPrefix;
    }

    public String getZipSuffix1() {
        return zipSuffix1;
    }

    public void setZipSuffix1(final String zipSuffix1) {
        this.zipSuffix1 = zipSuffix1;
    }

    public String getZipSuffix2() {
        return zipSuffix2;
    }

    public void setZipSuffix2(final String zipSuffix2) {
        this.zipSuffix2 = zipSuffix2;
    }

    public String getLine1() {
        return line1;
    }

    public void setLine1(final String line1) {
        this.line1 = line1;
    }

    public String getLine2() {
        return line2;
    }

    public void setLine2(final String line2) {
        this.line2 = line2;
    }

    public String getLine3() {
        return line3;
    }

    public void setLine3(final String line3) {
        this.line3 = line3;
    }

    public String getCity() {
        return city;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(final String country) {
        this.country = country;
    }

    public String getMltyPostalTypeCd() {
		return mltyPostalTypeCd;
	}

	public void setMltyPostalTypeCd(String mltyPostalTypeCd) {
		this.mltyPostalTypeCd = mltyPostalTypeCd;
	}

	public String getMltyPostOfficeTypeCd() {
		return mltyPostOfficeTypeCd;
	}

	public void setMltyPostOfficeTypeCd(String mltyPostOfficeTypeCd) {
		this.mltyPostOfficeTypeCd = mltyPostOfficeTypeCd;
	}
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("Line1",        line1)
                .append("Line2",        line2)
                .append("Line3",        line3)
                .append("City",         city)
                .append("Country",      country)
                .append("State",        state)
                .append("Zip Prefix",   zipPrefix)
                .append("Zip Suffix1",  zipSuffix1)
                .append("Zip Suffix2",  zipSuffix2)
                .append("Mlty Postal Type Cd", mltyPostalTypeCd)
                .append("Mlty PostOffice Type Cd",  mltyPostOfficeTypeCd)
                .toString();
    }

	
}
