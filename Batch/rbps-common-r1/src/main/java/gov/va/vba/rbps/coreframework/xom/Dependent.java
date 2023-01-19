/*
 * Dependant.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.coreframework.xom;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


public abstract class Dependent extends Person {

    private static final long serialVersionUID = 9018142162402075522L;

    private boolean             isLivingWithVeteran;
    private boolean             isOnCurrentAward;
    private boolean             isNewSchoolChild;
    private boolean				isOnAwardAsMinorChild;
    private boolean				isTerminatedOn18thBirthday;
    private boolean             isDeniedAward;
    private boolean				isEverOnAward;
    private boolean				isChildPresentForSpouse;
    private Boolean             hasIncome;
    private Date 				deniedDate;
    private Award               award;
    private List<FormType>      forms;
    protected Date				awardEffectiveDate;
    

    public Dependent() {
        forms = new ArrayList<FormType>();
    }

    public Award getAward() {
        return award;
    }

    public void setAward(final Award award) {
        this.award = award;
    }

    public boolean isLivingWithVeteran() {
        return isLivingWithVeteran;
    }

    public void setLivingWithVeteran(final boolean isLivingWithVeteran) {
        this.isLivingWithVeteran = isLivingWithVeteran;
    }

    public Boolean hasIncome() {
        return hasIncome;
    }

    public void setHasIncome(Boolean hasIncome) {
        this.hasIncome = hasIncome;
    }

    public void setOnCurrentAward(final boolean isOnCurrentAward) {
        this.isOnCurrentAward = isOnCurrentAward;
    }

    public boolean isOnCurrentAward() {
        return isOnCurrentAward;
    }

    public boolean isNewSchoolChild() {
		return isNewSchoolChild;
	}

	public void setNewSchoolChild(final boolean isNewSchoolChild) {
		this.isNewSchoolChild = isNewSchoolChild;
	}

    public boolean isOnAwardAsMinorChild() {
		return isOnAwardAsMinorChild;
	}

	public void setOnAwardAsMinorChild(final boolean isOnAwardAsMinorChild) {
		this.isOnAwardAsMinorChild = isOnAwardAsMinorChild;
	}
	
	public boolean isTerminatedOn18thBirthday() {
		return isTerminatedOn18thBirthday;
	}

	public void setTerminatedOn18thBirthday(boolean isTerminatedOn18thBirthday) {
		this.isTerminatedOn18thBirthday = isTerminatedOn18thBirthday;
	}
	
    public void setIsEverOnAward(final boolean isEverOnAward) {
        this.isEverOnAward = isEverOnAward;
    }

    public boolean isEverOnAward() {
        return isEverOnAward;
    }
    
    public boolean isDeniedAward() {
		return isDeniedAward;
	}

    
    public boolean isChildPresentForSpouse(){
    	return isChildPresentForSpouse;
    }

    public void setIsChildPresentForSpouse(final boolean isChildPresentForSpouse) {
        this.isChildPresentForSpouse = isChildPresentForSpouse;
    }
    
	public void setIsDeniedAward(final boolean isDeniedAward) {
		this.isDeniedAward = isDeniedAward;
	}

    public Date getDeniedDate() {
		return deniedDate;
	}

	public void setDeniedDate(final Date deniedDate) {
		this.deniedDate = deniedDate;
	}

	public void addForms(final List<FormType> forms) {
        this.forms = forms;
    }

    public void addForm(final FormType form) {
        this.forms.add(form);
    }

    public List<FormType> getForms() {
        return forms;
    }
    
    public Date getAwardEffectiveDate() {
		return awardEffectiveDate;
	}

	public void setAwardEffectiveDate(Date awardEffectiveDate) {
		this.awardEffectiveDate = awardEffectiveDate;
	}

	

	@Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .appendSuper(super.toString())
                .append( "Living With Veteran?",    isLivingWithVeteran )
                .append( "On Current Award?",       isOnCurrentAward )
                .append( "Forms",                   forms )
                .append( "Award",                   award )
                .append("AwardEffectiveDate", awardEffectiveDate)
                .toString();
    }


}
