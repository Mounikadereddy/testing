/*
 * Person.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */

package gov.va.vba.rbps.coreframework.xom;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


/**
 *
 * @since March 1, 2011
 * @version 1.0
 * @author Omar.Gaye
 *
 **/
public abstract class Person implements Serializable {

    private static final long serialVersionUID = -5971115361693902489L;

    private long                   vnpParticipantId;
    private long                   corpParticipantId;
    private boolean                isAlive;
    private String                 ssn;
    private String                 firstName;
    private String                 lastName;
    private String                 middleName;
    private String                 suffixName;
    private String                 email;
    private Address                mailingAddress;
    private Address                permanentAddress;
    private Address                residenceAddress;
    private List<Marriage>         previousMarriages;
    private Marriage               latestPreviousMarriage;
    private Marriage               currentMarriage;
    private Date                   birthDate;
    private List<Child>            children;
    private GenderType             genderType;
    private NoSSNReasonType        noSsnReason;
    private SSNVerificationType    ssnVerification;
    private Date				   deathDate;

    public Person() {
        currentMarriage     = null;
        isAlive             = true;
        previousMarriages   = new ArrayList<Marriage>();
        children            = new ArrayList<Child>();
    }

    public boolean hasChildren() {
        return !children.isEmpty();
    }

    public boolean hasMarriages() {
        return (!previousMarriages.isEmpty() || (currentMarriage != null));
    }

    public GenderType getGender() {
        return genderType;
    }

    public void setGender(final GenderType genderType) {
        this.genderType = genderType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getSuffixName() {
        return suffixName;
    }

    public void setSuffixName(final String suffixName) {
        this.suffixName = suffixName;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(final String ssn) {
        this.ssn = ssn;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(final String middleName) {
        this.middleName = middleName;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(final boolean isAlive) {
        this.isAlive = isAlive;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(final Date birthDate) {
        this.birthDate = birthDate;
    }

    public Marriage getCurrentMarriage() {
        return currentMarriage;
    }

    public void setCurrentMarriage(final Marriage currentMarriage) {
        this.currentMarriage = currentMarriage;
    }

    public void setPreviousMarriages(final List<Marriage> previousMarriages) {
        this.previousMarriages = previousMarriages;
    }

    public void appendPreviousMarriages(final List<Marriage> previousMarriages) {
        this.previousMarriages.addAll(previousMarriages);
    }

    public List<Marriage> getPreviousMarriages() {
        return previousMarriages;
    }

    public void addPreviousMarriage(final Marriage marriage) {
        this.previousMarriages.add(marriage);
    }

    public Marriage getLatestPreviousMarriage() {
        return latestPreviousMarriage;
    }

    public void setLatestPreviousMarriage(final Marriage latestPreviousMarriage) {
        this.latestPreviousMarriage = latestPreviousMarriage;
    }

    public List<Child> getChildren() {
        return children;
    }

    public void setChildren(final List<Child> children) {
        this.children = children;
    }

    public void addChild(final Child child) {
        this.children.add(child);
    }

    public void appendChildren(final List<Child> children) {
        this.children.addAll(children);
    }

    public Address getMailingAddress() {
        return mailingAddress;
    }

    public void setMailingAddress(final Address mailingAddress) {
        this.mailingAddress = mailingAddress;
    }

    public void setVnpParticipantId(final long vnpParticipantId) {
        this.vnpParticipantId = vnpParticipantId;
    }

    public long getVnpParticipantId() {
        return vnpParticipantId;
    }

    public void setCorpParticipantId(final long corpParticipantId) {
        this.corpParticipantId = corpParticipantId;
    }

    public long getCorpParticipantId() {
        return corpParticipantId;
    }

    public void setSsnVerification(final SSNVerificationType ssnVerification) {
        this.ssnVerification = ssnVerification;
    }

    public SSNVerificationType getSsnVerification() {
        return ssnVerification;
    }

    public void setNoSsnReason(final NoSSNReasonType noSsnReason) {
        this.noSsnReason = noSsnReason;
    }

    public NoSSNReasonType getNoSsnReason() {
        return noSsnReason;
    }

    public void setPermanentAddress(final Address permanentAddress) {
        this.permanentAddress = permanentAddress;
    }

    public Address getPermanentAddress() {
        return permanentAddress;
    }

    public void setResidenceAddress(final Address residenceAddress) {
        this.residenceAddress = residenceAddress;
    }

    public Address getResidenceAddress() {
        return residenceAddress;
    }
    public Date getDeathDate() {
		return deathDate;
	}

	public void setDeathDate(Date deathDate) {
		this.deathDate = deathDate;
	}
    @Override
    public String toString() {
        String countryCode = "no mailing address";

        if ( mailingAddress != null ) {
            countryCode = "\ncountry code: >" + mailingAddress.getCountry() + "<";
        }
        
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append( "First Name",              firstName )
                .append( "Last Name",               lastName )
                .append( "Middle Name",             middleName )
                .append( "Suffix Name",             suffixName )
                .append( "Corp Participant ID",     corpParticipantId )
                .append( "Vnp Participant ID",      vnpParticipantId )
                .append( "SSN #",                   ssn )
                .append( "Alive?",                  isAlive )
                .append( "Birth Date",              birthDate )
                .append( "Current Marriage",        currentMarriage )
                .append( "Previous Marriages",      previousMarriages)
                .append( "Children",                children )
                .append( "Mailing Address",         mailingAddress )
                .append( "Residence Address",       residenceAddress )
                .append( "Gender",                  genderType )
                .append( "Email",                   email )
                .append( "Permanent Address",       permanentAddress )
                .append( "No SSN Reason",           noSsnReason )
                .append( "SSN Verification",        ssnVerification )
                .append("DeathDate", deathDate)
                .toString() + countryCode;
    }
}
