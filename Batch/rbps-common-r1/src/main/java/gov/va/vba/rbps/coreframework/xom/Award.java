/*
 * Award.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. use is subject to security terms
 */
package gov.va.vba.rbps.coreframework.xom;


import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


/**
 *      This is the award that may be given towards adding a dependent
 *      or some other such thing.
 */
public class Award implements Serializable {

    private static final long serialVersionUID = 6811295920868273484L;

    private DependentDecisionType   dependencyDecisionType;
    private DependentStatusType     dependencyStatusType;
    private Date                    eventDate;
    private Date                    endDate;
    
    public DependentDecisionType getDependencyDecisionType() {
        return dependencyDecisionType;
    }

	public void setDependencyDecisionType(
            final DependentDecisionType dependencyDecisionType) {
        this.dependencyDecisionType = dependencyDecisionType;
    }

    public DependentStatusType getDependencyStatusType() {
        return dependencyStatusType;
    }

    public void setDependencyStatusType(final DependentStatusType dependencyStatusType) {
        this.dependencyStatusType = dependencyStatusType;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(final Date eventDate) {
        this.eventDate = eventDate;
    }

    public void setEndDate(final Date endDate) {
        this.endDate = endDate;
    }

    public Date getEndDate() {
        return endDate;
    }

      
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("Dependency Decision Type", dependencyDecisionType)
                .append("Dependency Status Type",   dependencyStatusType)
                .append("Event Date",               eventDate) 
                .append("End Date",                 endDate)
                .toString();
    }
}
