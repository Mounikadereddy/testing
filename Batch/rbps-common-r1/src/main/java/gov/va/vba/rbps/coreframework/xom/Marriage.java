/*
 * Marriage.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */

package gov.va.vba.rbps.coreframework.xom;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Marriage implements Serializable {

    private static final long serialVersionUID = -2459708041826966835L;

    private Date                    startDate;
    private Date                    endDate;
    private Address                 marriedPlace;
    private Address                 terminationPlace;
    private MarriageTerminationType terminationType;
    private Spouse                  marriedTo;

    public Spouse getMarriedTo() {
        return marriedTo;
    }

    public void setMarriedTo(final Spouse marriedTo) {
        this.marriedTo = marriedTo;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(final Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(final Date endDate) {
        this.endDate = endDate;
    }

    public Address getMarriedPlace() {
        return marriedPlace;
    }

    public void setMarriedPlace(final Address marriedPlace) {
        this.marriedPlace = marriedPlace;
    }

    public Address getTerminationPlace() {
        return terminationPlace;
    }

    public void setTerminationPlace(final Address terminationPlace) {
        this.terminationPlace = terminationPlace;
    }

    public MarriageTerminationType getTerminationType() {
        return terminationType;
    }

    public void setTerminationType(final MarriageTerminationType terminationType) {
        this.terminationType = terminationType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("Marriage Date",        startDate)
                .append("Termination Date",     endDate)
                .append("Married To",           marriedTo)
                .append("Married Place",        marriedPlace)
                .append("Termination Place",    terminationPlace)
                .append("Termination Type",     terminationType)
                .toString();
    }

}
