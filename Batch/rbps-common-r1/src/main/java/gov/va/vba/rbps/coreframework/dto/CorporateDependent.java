/*
 * CorporateDependent.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.coreframework.dto;


import gov.va.vba.rbps.coreframework.vo.RelationshipType;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;


/**
 *      This is a child or spouse that is retrieved from the corporate database,
 *      not info from the claim.  It's used to compare and validate
 *      the dependents on the claim with what's already known about
 *      the veteran's children.
 *
 * @since   March 1, 2011
 * @version 1.0
 * @author  Tom.Corbin
 *
 *      Revision History
 *      Date        Name        Description
 *      --------------------------------------------------------------------
 *      04/01/2011  T.Corbin    Initial Version
 *      06/30/2011  O.Gaye      Added serializable stuff
 */
public class CorporateDependent
    implements
        Serializable,
        Comparable<CorporateDependent> {

	private static final long serialVersionUID = 7165333207171479962L;

	private Long               participantId;
	private String             firstName;
	private String             lastName;
	private Date               birthDate;
	private String             socialSecurityNumber;
	private boolean            onAward;
	private RelationshipType   relationship;


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName( final String firstName ) {
        this.firstName = firstName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate( final Date birthDate ) {
        this.birthDate = birthDate;
    }

    public String getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    public void setSocialSecurityNumber( final String socialSecurityNumber ) {
        this.socialSecurityNumber = socialSecurityNumber;
    }

    public Long getParticipantId() {
        return participantId;
    }

    public void setParticipantId( final Long participantId ) {
        this.participantId = participantId;
    }

    public boolean isOnAward() {
        return onAward;
    }

    public void setOnAward( final boolean onAward ) {
        this.onAward = onAward;
    }

    public String getLastName() {

        return lastName;
    }

    public void setLastName( final String lastName ) {
        this.lastName = lastName;
    }

    public RelationshipType getRelationship() {

        return relationship;
    }


    public void setRelationship( final RelationshipType  relationship ) {

        this.relationship = relationship;
    }


    @Override
    public int compareTo( final CorporateDependent rhs ) {

        return getId().compareTo( rhs.getId() );
    }


    public CorporateDependentId getId() {

        return new CorporateDependentId( participantId,
                                         firstName,
                                         birthDate,
                                         socialSecurityNumber );
    }


    @Override
    public String toString() {

        return new ToStringBuilder( this )
            .append( "participantId",               getParticipantId() )
            .append( "firtName",                    getFirstName() )
            .append( "lastName",                    getLastName() )
            .append( "birthDate",                   getBirthDate() )
            .append( "socialSecurityNumber",        getSocialSecurityNumber() )
            .append( "on award",                    isOnAward() )
            .append( "relationship",                getRelationship() )
            .toString();
    }
}
