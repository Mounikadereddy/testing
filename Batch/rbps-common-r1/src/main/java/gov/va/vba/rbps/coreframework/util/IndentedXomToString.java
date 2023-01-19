/*
 * IndentedXomToString
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.coreframework.util;


import gov.va.vba.rbps.coreframework.xom.Address;
import gov.va.vba.rbps.coreframework.xom.Award;
import gov.va.vba.rbps.coreframework.xom.AwardStatus;
import gov.va.vba.rbps.coreframework.xom.Child;
import gov.va.vba.rbps.coreframework.xom.Claim;
import gov.va.vba.rbps.coreframework.xom.Dependent;
import gov.va.vba.rbps.coreframework.xom.Education;
import gov.va.vba.rbps.coreframework.xom.Marriage;
import gov.va.vba.rbps.coreframework.xom.Person;
import gov.va.vba.rbps.coreframework.xom.Phone;
import gov.va.vba.rbps.coreframework.xom.School;
import gov.va.vba.rbps.coreframework.xom.Spouse;
import gov.va.vba.rbps.coreframework.xom.Veteran;

import java.util.Date;
import java.util.List;


public class IndentedXomToString {


    //      spaces
    private static final int        INDENT_VAL  = 4;

    private boolean                 log         = false;
    private SimpleDateUtils         dateUtils   = new SimpleDateUtils();



    public String toString( final Veteran     veteran,
                            final int         depth ) {

        String    claimId = "don't have one";

        if ( veteran.getClaim() != null ) {

            claimId = "" + veteran.getClaim().getClaimId();
        }

        logDepth( "veteran", depth );
        String tmp = new KeyValueListFormatter( 40 )
                .append("File #",                               veteran.getFileNumber() )
                .append("Claim id",                             claimId )
                .append( toString( (Person) veteran, 0 ))
                .append("Disability Rating",                    veteran.getServiceConnectedDisabilityRating() )
                .append("Rating Date",                          formatDate( veteran.getRatingDate() ))
                .append("Rating Effective Date",                formatDate( veteran.getRatingEffectiveDate() ))
                .append("First Changed Date of Rating",         formatDate( veteran.getFirstChangedDateofRating() ))
                .append("Receiving Military Retire Pay?",       veteran.isReceivingMilitaryRetirePay() )
                .append("Salutation",                           veteran.getSalutation() )
                .append("Has POA?",                             veteran.hasPOA() )
                .append("Has Military Pay?",                    veteran.hasMilitaryPay() )
                .append("Marital Status",                       veteran.getMaritalStatus() )
                .append("Claim",                                toString( veteran.getClaim(), depth + 1 ) )
                .append("Day Time Phone",                       toString( veteran.getDayTimePhone(),    depth + 1 ) )
                .append("Night Time Phone",                     toString( veteran.getNightTimePhone(),  depth + 1 ) )
                .append("Award Status",                         toString( veteran.getAwardStatus(),     depth + 1 ) )
                .toString();

        return indent( tmp, depth );
    }


    public String toString( final Child   child,
                            final int     depth ) {

        logDepth( "child", depth );
        String tmp = new KeyValueListFormatter( 40 )
              .append(toString( (Dependent) child, 0 ) )
              .append("Attended School Last Term?",   child.getAttendedSchoolLastTerm() )
              .append("Is Previsouly Married?",       child.isPreviouslyMarried() )
              .append("Is School Child?",             child.isSchoolChild() )
              .append("Is Seriously Disabled?",       child.isSeriouslyDisabled() )
              .append("Tuition Paid By Govt?",        child.isTuitionPaidByGovt() )
              .append("Child Type",                   child.getChildType() )
              .append("Unfiltered Child Type",        child.getUnfilteredChildType() )
              .append("Living With",                  child.getLivingWith() )
              .append("Govt Payment Start Date",      formatDate( child.getGovtPaymentStartDate() ))
              .append("Current Term",                 toString( child.getCurrentTerm(),             depth + 1 ) )
              .append("Last Term",                    toString( child.getLastTerm(),                depth + 1 ) )
              .append(String.format( "Previous Terms(%d)", child.getPreviousTerms().size() ),
                                                      termsToString( child.getPreviousTerms(),      depth + 1 ) )
              .append("Minor School Child Award",     toString( child.getMinorSchoolChildAward(),   depth + 1 ) )
              .append("Birth Address",                toString( child.getBirthAddress(),            depth + 1 ) )
              .toString();

        return indent( tmp, depth );
    }


    public String toString( final Spouse     spouse,
                            final int        depth ) {

        if ( spouse == null ) {

            return null;
        }

        logDepth( "spouse", depth );
        String tmp = new KeyValueListFormatter( 40 )
            .append(toString( (Dependent) spouse, 0 ) )
            .append( "vet",             spouse.isVet() )
            .append( "file number",     spouse.getFileNumber() )
            .toString();

        return indent( tmp, depth );
    }


    public String toString( final Dependent       dep,
                            final int             depth ) {

        if ( dep == null ) {

            return null;
        }

        logDepth( "dependent", depth );
        String tmp = new KeyValueListFormatter( 40 )
            .append( toString( (Person) dep, 0 ) )
            .append( "Living With Veteran?",    	dep.isLivingWithVeteran() )
            .append( "On Current Award?",       	dep.isOnCurrentAward() )
            .append( "Is new school child?",    	dep.isNewSchoolChild() )
            .append( "Is on Award as Minor Child?", dep.isOnAwardAsMinorChild() )
            .append( "Is Denied Award?",        	dep.isDeniedAward() )
            .append( "Denied Award Date",       	formatDate( dep.getDeniedDate() ))
            .append( "Forms",                   	dep.getForms() )
            .append( "Award",                   	toString( dep.getAward(), depth + 1 ) )
            .toString();

        return indent( tmp, depth );
    }


    public String toString( final Person       person,
                            final int          depth ) {

        if ( person == null ) {

            return null;
        }

        logDepth( "person", depth );
        String countryCode = "no country";

        if ( person.getMailingAddress() != null ) {
            countryCode = person.getMailingAddress().getCountry();
        }

        String tmp = new KeyValueListFormatter( 40 )
                .append( "First Name",              person.getFirstName() )
                .append( "Last Name",               person.getLastName() )
                .append( "Corp Participant ID",     person.getCorpParticipantId() )
                .append( "Vnp Participant ID",      person.getVnpParticipantId() )
                .append( "SSN #",                   person.getSsn() )
                .append( "Middle Name",             person.getMiddleName() )
                .append( "Suffix Name",             person.getSuffixName() )
                .append( "Alive?",                  person.isAlive() )
                .append( "Age",                     SimpleDateUtils.getCurrentAge( person ) )
                .append( "Birth Date",              formatDate( person.getBirthDate() ) )
                .append( "Age 18",                  formatDate( SimpleDateUtils.getDate18( person ) ) )
                .append( "5 months after Age 18",   formatDate( SimpleDateUtils.get5MonthsAfter18thBirthday( person ) ) )
                .append( "Age 23",                  formatDate( SimpleDateUtils.getDate23( person ) ) )
                .append( "Gender",                  person.getGender() )
                .append( "Email",                   person.getEmail() )
                .append( "No SSN Reason",           person.getNoSsnReason() )
                .append( "SSN Verification",        person.getSsnVerification() )
                .append( "Current Marriage",        toString( person.getCurrentMarriage(), depth + 1 ) )
                .append( String.format( "Previous Marriages(%d)", person.getPreviousMarriages().size() ),
                                                     toString( person.getPreviousMarriages(), depth + 1 ) )
                .append( "Latest Previous Marriage",toString( person.getLatestPreviousMarriage(), depth + 1 ) )
                .append( String.format("Children(%d)", person.getChildren().size() ),
                                                     childrenToString( person.getChildren(), depth + 1 ) )
                .append( "Mailing Address",         toString( person.getMailingAddress(),   depth + 1 ) )
                .append( "Residence Address",       toString( person.getResidenceAddress(), depth + 1 ) )
                .append( "Permanent Address",       toString( person.getPermanentAddress(), depth + 1 ) )
                .append( "Country",                 countryCode )
                .toString().trim();

        return indent( tmp, depth );
    }


    public String toString( final Marriage      marriage,
                            final int          depth ) {

        if ( marriage == null ) {

            return null;
        }

        logDepth( "marriage", depth );
        String tmp = new KeyValueListFormatter( 40 )
            .append("Marriage Date",        formatDate( marriage.getStartDate() ))
            .append("Termination Date",     formatDate( marriage.getEndDate() ))
            .append("Termination Type",     marriage.getTerminationType() )
            .append("Married To",           toString( marriage.getMarriedTo(),          depth + 1 ) )
            .append("Married Place",        toString( marriage.getMarriedPlace(),       depth + 1 ) )
            .append("Termination Place",    toString( marriage.getTerminationPlace(),   depth + 1 ) )
            .toString();

        return indent( tmp, depth );
    }


    public String toString( final Address     address,
                            final int         depth ) {

        if ( address == null ) {

            return null;
        }

        logDepth( "address", depth );
        String tmp = new KeyValueListFormatter( 40 )
                .append("Line1",        address.getLine1() )
                .append("Line2",        address.getLine2() )
                .append("Line3",        address.getLine3() )
                .append("City",         address.getCity() )
                .append("Country",      address.getCountry() )
                .append("State",        address.getState() )
                .append("Zip Prefix",   address.getZipPrefix() )
                .append("Zip Suffix1",  address.getZipSuffix1() )
                .append("Zip Suffix2",  address.getZipSuffix2() )
                .toString();

        return indent( tmp, depth );
    }


    public String toString( final Phone   phone,
                            final int     depth ) {

        if ( phone == null ) {

            return null;
        }

        logDepth( "phone", depth );
        String tmp = new KeyValueListFormatter( 40 )
                .append("Area Code",    phone.getAreaCode() )
                .append("Country Code", phone.getCountryCode() )
                .append("Phone Number", phone.getPhoneNumber() )
                .append("Extension",    phone.getExtension() )
                .append("Phone Type",   phone.getPhoneType() )
                .toString();

        return indent( tmp, depth );
    }


    public String toString( final AwardStatus      awardStatus,
                            final int              depth ) {

        if ( awardStatus == null ) {

            return null;
        }

        logDepth( "award status", depth );
        String tmp = new KeyValueListFormatter( 40 )
            .append( "Changes Made?",                   awardStatus.isChangesMade() )
            .append( "Is Suspended?",                   awardStatus.isSuspended() )
            .append( "Is GAO Used?",                    awardStatus.isGAOUsed() )
            .append( "Has Attorney Fee Agreement?",     awardStatus.hasAttorneyFeeAgreement() )
            .append( "Is Running Award?",               awardStatus.isRunningAward() )
            .append( "Is Proposed?",                    awardStatus.isProposed() )
            .append( "# Dependents on Award",           awardStatus.getNumDependentsOnAward() )
            .toString();

        return indent( tmp, depth );
    }


    public String toString( final Claim   claim,
                            final int     depth ) {

        logDepth( "claim", depth );
        String tmp = new KeyValueListFormatter( 40 )
                .append("Claim ID",             claim.getClaimId() )
                .append("Claim Received Date",  formatDate( claim.getReceivedDate() ))
                .append("EP Code",              claim.getEndProductCode() )
                .append("Claim Label",          claim.getClaimLabel() )
                .append("Has Attachments?",     claim.hasAttachments() )
                .append("Form List",            claim.getForms() )
                .append("Is New?",              claim.isNew() )
                .toString();

        return indent( tmp, depth );
    }


    public String toString( final Award   award,
                            final int     depth ) {

        if ( award == null ) {

            return null;
        }

        logDepth( "award", depth );
        String tmp = new KeyValueListFormatter( 40 )
                .append("Dependency Decision Type", award.getDependencyDecisionType() )
                .append("Dependency Status Type",   award.getDependencyStatusType() )
                .append("Event Date",               formatDate( award.getEventDate() ))
                .append("End Date",                 formatDate( award.getEndDate() ))
                .toString();

        return indent( tmp, depth );
    }


    public String toString( final Education   term,
                            final int         depth ) {

        if ( term == null ) {

            return null;
        }

        String tmp = new KeyValueListFormatter( 40 )
                .append("Course Name",                      term.getCourseName() )
                .append("Subject Name",                     term.getSubjectName() )
                .append("Education Type",                   term.getEducationType() )
                .append("Education Level Type",             term.getEducationLevelType() )
                .append("Official Course Start Date",       formatDate( term.getOfficialCourseStartDate() ))
                .append("Course Student Start Date",        formatDate( term.getCourseStudentStartDate() ))
                .append("Graduation Date",                  formatDate( term.getExpectedGraduationDate() ))
                .append("Course End Date",                  formatDate( term.getCourseEndDate() ))
                .append("5 months after Course End Date",   formatDate( SimpleDateUtils.addMonthsToDate( 5, term.getCourseEndDate() ) ) )
                .append("Hours per Week",                   term.getHoursPerWeek() )
                .append("Sessions per Week",                term.getSessionsPerWeek() )
                .append("School",                           toString( term.getSchool(), depth + 1 ) )
                .toString();

        return indent( tmp, depth );
    }


    public String toString( final School      school,
                            final int         depth ) {

        if ( school == null ) {

            return null;
        }

        String tmp = new KeyValueListFormatter( 40 )
                .append("School Name",      school.getName() )
                .append("School Address",   toString( school.getAddress(), depth + 1 ) )
                .toString();

        return indent( tmp, depth );
    }


    public String toString( final List<Marriage>       marriages,
                            final int                  depth ) {

        String  result = "";

        for ( Marriage marriage : marriages ) {

            result += toString( marriage, depth );
            result += separator( 40, depth );
        }

        return result;
    }


    public String childrenToString( final List<Child>       children,
                                    final int               depth ) {

        String  result = "";

        for ( Child child : children ) {

            result += toString( child, depth );
            result += separator( 40, depth );
        }

        return result;
    }


    private String termsToString( final List<Education> terms, final int depth ) {

        String  result = "";

        for ( Education term : terms ) {

            result += toString( term, depth );
            result += separator( 40, depth );
        }

        return result;
    }


    public String separator( final int i, final int depth ) {

        return indentSpaces( depth ) + new KeyValueListFormatter().repeat( "=", 40 );
    }


    public String indent( final String       val,
                           final int          depth ) {

//        System.out.println( "before:\n" + val );
        String tmp  = removeBlankLinesPrefix( val );
//        System.out.println( "after remove blank lines:\n" + tmp );
        tmp         = addIndentSpace( tmp, depth );
//        System.out.println( "after adding indent space:\n" + tmp );
        tmp         = newlineIfDepth( depth ) + tmp;

//        System.out.println( "after:\n" + tmp );
        return tmp;
    }


    public String addIndentSpace( final String tmp, final int depth ) {

        return tmp.replaceAll( "(?m)^", indentSpaces( depth ) );
    }


    public String removeBlankLinesPrefix( final String val ) {

        String tmp  = val.replaceAll( "(?m)^\\s*$", "" );
        tmp         = tmp.replaceAll( "(?m)^$", "" );

        return tmp;
    }


    private String indentSpaces( final int depth ) {

        String  result          = "";
        int     graduatedDepth  = (depth > 1) ? (depth - 1) : depth;
        graduatedDepth = (depth > 1) ? 1 : depth;

        for ( int ii = 0; ii < graduatedDepth; ++ii ) {

            result += "    ";
        }

//        System.out.println( "indent depth2: " + depth );
//        System.out.println( "indent spaces: >" + result + "<");

        return result;
    }


    private String indentSpacesConstant( final int depth ) {

        if ( depth <= 0 ) {

            return "";
        }


//        System.out.println( "indent depth3: " + depth );

        return "    ";
    }


    private String newlineIfDepth( final int depth ) {

//        if ( depth > 0 ) {
//
//            return "\n" + indentSpaces( depth );
//        }

        return indentSpaces( depth );
    }

    private String formatDate( final Date date ) {

        if ( date == null ) {

            return null;
        }

        return dateUtils.standardLogDayFormat( date );
    }

    private void logDepth( final String   itemType, final int depth ) {

        log( String.format( "%s: depth %d", itemType, depth ) );
    }


    private void log( final String msg ) {

        if ( ! log ) {

            return;
        }

        System.out.println( msg );
    }
}
