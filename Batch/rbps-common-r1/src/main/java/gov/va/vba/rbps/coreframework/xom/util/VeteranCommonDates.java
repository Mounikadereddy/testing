/*
 * VeteranCommonDates.java
 * 
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */

package gov.va.vba.rbps.coreframework.xom.util;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class VeteranCommonDates implements Serializable {

	private static final long serialVersionUID = 9049937502154414453L;

	private Date ratingDate;
	private Date ratingEffectiveDate;
	private Date firstChangedDateofRating;
	private Date claimDate;
	private Date marriageDate;
	private Date allowableDate;
	public Date getAllowableDate() {
		return allowableDate;
	}

	public void setAllowableDate(Date allowableDate) {
		this.allowableDate = allowableDate;
	}

	public Date getRatingDate() {
		return ratingDate;
	}

	public void setRatingDate(Date ratingDate) {
		this.ratingDate = ratingDate;
	}

	public Date getRatingEffectiveDate() {
		return ratingEffectiveDate;
	}

	public void setRatingEffectiveDate(Date ratingEffectiveDate) {
		this.ratingEffectiveDate = ratingEffectiveDate;
	}

    public Date getFirstChangedDateofRating() {
        return firstChangedDateofRating;
    }

    public void setFirstChangedDateofRating(final Date firstChangedDateofRating) {
        this.firstChangedDateofRating = firstChangedDateofRating;
    }
    
	public Date getClaimDate() {
		return claimDate;
	}

	public void setClaimDate(Date claimDate) {
		this.claimDate = claimDate;
	}

	public Date getMarriageDate() {
		return marriageDate;
	}

	public void setMarriageDate(Date marriageDate) {
		this.marriageDate = marriageDate;
	}

	/* added for JRule to Java conversion */
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append( "ratingDate",             ratingDate )
                .append( "ratingEffectiveDate",     ratingEffectiveDate )
                .append( "firstChangedDateofRating",     firstChangedDateofRating )
                .append( "claimDate",     claimDate )
                .append( "marriageDate",     marriageDate )
                .append( "allowableDate",     allowableDate )
                .toString();
    }

}
