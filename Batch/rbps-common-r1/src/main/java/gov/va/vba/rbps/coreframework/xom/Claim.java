/*
 * Claim.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.coreframework.xom;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


/**
 *      A claim the veteran has entered, possibly to add a dependent
 *      or some other such thing.
 */
public class Claim implements Serializable {

    private static final long serialVersionUID = 3243520704705337293L;

    private boolean             hasAttachments;
    private boolean             isNew;
    private long                claimId;
    private Date                receivedDate;
    private EndProductType      endProductCode;
    private ClaimLabelType      claimLabelType;
    private List<FormType>      forms;
    
    //ccr 2192
    private String jrnObjId;

    public Claim() {
        forms = new ArrayList<FormType>();
    }

    public Date getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(final Date receivedDate) {
        this.receivedDate = receivedDate;
    }

    public long getClaimId() {
        return claimId;
    }

    public void setClaimId(final long claimId) {
        this.claimId = claimId;
    }

    public boolean isSameClaimId( final long claimId) {

        return this.claimId == claimId;
    }

    public void setEndProductCode(final EndProductType endProductCode) {
        this.endProductCode = endProductCode;
    }

    public EndProductType getEndProductCode() {
        return endProductCode;
    }

    public void setClaimLabel(final ClaimLabelType claimLabelType) {
        this.claimLabelType = claimLabelType;
    }

    public ClaimLabelType getClaimLabel() {
        return claimLabelType;
    }

    public void setHasAttachments(final boolean hasAttachments) {
        this.hasAttachments = hasAttachments;
    }

    public boolean hasAttachments() {
        return hasAttachments;
    }

    public void setNew(final boolean isNew) {
        this.isNew = isNew;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setForms(final List<FormType> forms) {
        this.forms = forms;
    }

    public String getJrnObjId() {
		return jrnObjId;
	}

	public void setJrnObjId(String jrnObjId) {
		this.jrnObjId = jrnObjId;
	}

	public List<FormType> getForms() {
        return forms;
    }

    public void addForm(final FormType form) {
        this.forms.add(form);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("Claim Received Date",  receivedDate )
                .append("Claim ID",             claimId )
                .append("EP Code",              endProductCode )
                .append("Claim Label",          claimLabelType )
                .append("Has Attachments?",     hasAttachments )
                .append("Form List",            forms )
                .append("Is New?",              isNew )
                 .append("JrnObj Id",          jrnObjId )
                .toString();
    }
}
