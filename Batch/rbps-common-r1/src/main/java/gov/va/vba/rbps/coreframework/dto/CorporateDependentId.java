/*
 * CorporateDependentId
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.coreframework.dto;


//import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.util.SimpleDateUtils;
import gov.va.vba.rbps.coreframework.xom.Dependent;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.time.DateUtils;


/**
 *      This class is used to compare the dependent data from the corporate database
 *      with the xom data based on participantId, firstName and birthDate
 */
public class CorporateDependentId
    implements
        Serializable,
        Comparable<CorporateDependentId> {

//    private static Logger logger = Logger.getLogger(CorporateDependentId.class);

    private static final long serialVersionUID = -7111812457521013466L;

    private Long                participantId;
    private String              firstName;
    private Date                birthDate;
    private String              socialSecurityNumber;


    public CorporateDependentId( final Long         participantId,
                                 final String       firstName,
                                 final Date         birthDate,
                                 final String       socialSecurityNumber ) {

        this.participantId          = participantId;
        this.firstName              = firstName;
        this.birthDate              = birthDate;
        this.socialSecurityNumber   = socialSecurityNumber;

        uppercaseFirstName();
        truncateBirthDateToDay();
    }


    public CorporateDependentId( final Dependent dependent ) {

        participantId           = dependent.getCorpParticipantId();
        firstName               = dependent.getFirstName();
        birthDate               = dependent.getBirthDate();
        socialSecurityNumber    = dependent.getSsn();

        uppercaseFirstName();
    }


    public long getParticipantId() {

        return participantId;
    }


    public String getFirstName() {

        return firstName;
    }


    public void uppercaseFirstName() {

        if ( firstName == null ) {

            return;
        }

        firstName = firstName.toUpperCase();
    }


    private void truncateBirthDateToDay() {

        if ( birthDate == null ) {

            return;
        }

        birthDate = SimpleDateUtils.truncateToDay( birthDate );
    }


    @Override
    public int compareTo( final CorporateDependentId rhs ) {

//        logUtils.log( String.format( "comparing >%s< to other >%s<", this, rhs ) );

        CompareToBuilder    builder = new CompareToBuilder();

        builder.append( firstName, rhs.firstName );
        builder.append( birthDate, rhs.birthDate );

        if ( socialSecurityNumber != null
                && rhs.socialSecurityNumber != null ) {

            builder.append( socialSecurityNumber, rhs.socialSecurityNumber );
        }

        int tmp = builder.toComparison();

//        logUtils.log( "Result is: " + tmp );

        return tmp;
    }


    @Override
    public boolean equals( final Object obj ) {

        if ( getClass() != obj.getClass() ) {

            return false;
        }

        CorporateDependentId other = (CorporateDependentId) obj;

        boolean tmp = sameFirstName( other ) && bornOnSameDay( other ) && sameSocialSecurityNumber( other );

//        logUtils.log( String.format( "testing for equals >%s< to other >%s<", this, obj ) );
//        logUtils.log( "Result is: " + tmp );

        return tmp;
    }


    public boolean sameSocialSecurityNumber( final CorporateDependentId other ) {

        return ObjectUtils.equals( socialSecurityNumber, other.socialSecurityNumber );
    }


    private boolean bornOnSameDay( final CorporateDependentId     other ) {

        return birthDate != null && other.birthDate != null && DateUtils.isSameDay( birthDate, other.birthDate );
    }


    public boolean sameFirstName( final CorporateDependentId other ) {

        if ( StringUtils.isBlank( firstName ) ) {

            return false;
        }

        if ( StringUtils.isBlank( other.firstName ) ) {

            return false;
        }

        return ObjectUtils.equals( firstName, other.firstName );
    }


    /**
     *      We can't really use this method - can't put this object in
     *      a hash map because the other guy, the one that's being used
     *      to find a matching (that's us) dependent ID might not have
     *      the same set of fields - we might have SSN, they might have
     *      participantID and SSN.
     */
    @Override
    public int hashCode() {

//      System.out.println( String.format( "testing for equals >%s< to other >%s<", this, obj ) );

        HashCodeBuilder builder = new HashCodeBuilder();

        if ( participantId != null ) {

            builder.append( participantId );

            return builder.hashCode();
        }

        if ( socialSecurityNumber != null ) {

            builder.append( socialSecurityNumber );

            return builder.hashCode();
        }

        return builder
          .append( firstName )
          .append( birthDate )
          .hashCode();
    }


    @Override
    public String toString() {

        return new ToStringBuilder( this )
            .append( "participantId",           participantId )
            .append( "firstName",               firstName )
            .append( "birthDate",               birthDate )
            .append( "socialSecurityNumber",    socialSecurityNumber )
            .toString();
    }
}
