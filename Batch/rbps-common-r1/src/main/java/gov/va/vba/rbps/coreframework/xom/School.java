/*
 * School.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.coreframework.xom;


import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;



public class School implements Serializable {

    private static final long serialVersionUID = 6644964777372155113L;

    String             name;
    private Address    address;
    private long       eduInstnPtcpntId;


    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(final Address address) {
        this.address = address;
    }

    public long getEduInstnPtcpntId() {
        return eduInstnPtcpntId;
    }

    public void setEduInstnPtcpntId(final long eduInstnPtcpntId) {
        this.eduInstnPtcpntId = eduInstnPtcpntId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("School Name",      name)
                .append("School Address",   address)
                .append("School Id",        eduInstnPtcpntId)
                .toString();
    }
}
