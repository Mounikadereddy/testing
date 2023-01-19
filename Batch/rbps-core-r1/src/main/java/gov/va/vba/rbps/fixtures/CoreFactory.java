/*
 * CoreFactory.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.fixtures;


//import gov.va.vba.rbps.coreframework.util.CommonUtils;
import gov.va.vba.rbps.coreframework.util.RbpsUtil;
import gov.va.vba.rbps.coreframework.util.SimpleDateUtils;
import gov.va.vba.rbps.coreframework.vo.RelationshipType;
import gov.va.vba.rbps.coreframework.xom.Child;
import gov.va.vba.rbps.coreframework.xom.Dependent;
import gov.va.vba.rbps.coreframework.xom.Spouse;
import gov.va.vba.rbps.coreframework.xom.Veteran;
import gov.va.vba.rbps.services.ws.client.mapping.awards.amend.AmendAwardServiceReturnVO;
import gov.va.vba.rbps.services.ws.client.mapping.awards.amend.AwardEventAuthVO;
import gov.va.vba.rbps.services.ws.client.mapping.awards.amend.AwardLineSummaryVO;
import gov.va.vba.rbps.services.ws.client.mapping.awards.amend.AwardReasonSeqNbrVO;
import gov.va.vba.rbps.services.ws.client.mapping.awards.amend.AwardSummaryVO;
import gov.va.vba.rbps.services.ws.client.mapping.awards.amend.DependencyDecisionResultVO;
import gov.va.vba.rbps.services.ws.client.mapping.awards.amend.ProcessAwardDependentResponse;
import gov.va.vba.rbps.services.ws.client.mapping.awards.dep.DependentOnAwardServiceReturnVO;
import gov.va.vba.rbps.services.ws.client.mapping.awards.dep.FindDependentOnAwardResponse;
import gov.va.vba.rbps.services.ws.client.mapping.share.familytree.FindDependentsResponse;
import gov.va.vba.rbps.services.ws.client.mapping.share.familytree.FindPOAResponse;
import gov.va.vba.rbps.services.ws.client.mapping.share.familytree.Shrinq3Person;
import gov.va.vba.rbps.services.ws.client.mapping.share.familytree.Shrinq3Record;
import gov.va.vba.rbps.services.ws.client.mapping.share.familytree.ShrinqfPersonOrg;
import gov.va.vba.rbps.services.ws.client.util.RbpsWebServiceClientUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 *      This is a test fixture for testing the letter generation code.
 *
 *      This allows us to generate data for tests w/o having to execute
 *      web services for each test.
 */
public class CoreFactory extends CommonFactory {

//    private static CommonUtils      hydratorUtils       = new CommonUtils();
//    private static SimpleDateUtils  dateUtils           = new SimpleDateUtils();


    public static ProcessAwardDependentResponse createProcessAwardDependentResponse( final Veteran veteran ) {

        ProcessAwardDependentResponse   response        = new ProcessAwardDependentResponse();
        AmendAwardServiceReturnVO       returnVO        = new AmendAwardServiceReturnVO();
        AwardSummaryVO                  summaryVO       = new AwardSummaryVO();
        AwardEventAuthVO                authVO          = new AwardEventAuthVO();
        IntegerContainer                reasonSequence  = new IntegerContainer();

        response.setReturn( returnVO );
        returnVO.setAwardSummary( summaryVO );
        summaryVO.setAwardEventSummary( authVO );
        authVO.getAwardLineSummary().clear();

        summaryVO.getDependencySummary().addAll( createDependencySummaryList( veteran, 6 ) );
        authVO.getAwardLineSummary().addAll( createAwardLineSummaryList( veteran, reasonSequence ) );

        addDependentToReason( summaryVO.getDependencySummary(), authVO.getAwardLineSummary() );


//        System.out.println( String.format( "size of AwardLineSummary list: >%d<", authVO.getAwardLineSummary().size() ) );
        return response;
    }


    private static void addDependentToReason( final List<DependencyDecisionResultVO>    dependencySummary,
                                              final List<AwardLineSummaryVO>            awardLineSummary ) {

        List<AwardReasonSeqNbrVO>           reasons         = getTotalReasonList( awardLineSummary );
        int                                 toIndex         = Math.min( dependencySummary.size() / 2,
                                                                        reasons.size() );
        List<DependencyDecisionResultVO>    dependentList   = dependencySummary.subList( 0, toIndex );
        List<Integer>                       chosenSequences = new ArrayList<Integer>();

//        System.out.println( String.format( "%d - %d\n=========================",
//                                           dependencySummary.size() / 2,
//                                           reasons.size() ) );
        for ( DependencyDecisionResultVO dependent : dependentList ) {

            int tries = 0;
            AwardReasonSeqNbrVO reason = reasons.get( getRandomInt( reasons.size() ) );
            while ( chosenSequences.contains( reason.getReasonSequenceNumber() ) && tries++ < 7 ) {

                reason = reasons.get( getRandomInt( reasons.size() ) );
            }

            if ( chosenSequences.contains( reason.getReasonSequenceNumber() ) && tries++ < 7 ) {

                chosenSequences.add( 55 );
                dependent.setAlReasonSequenceNumber( 55 );
                continue;
            }
//            System.out.println( "sequences: " + chosenSequences );

            chosenSequences.add( reason.getReasonSequenceNumber() );
            dependent.setAlReasonSequenceNumber( reason.getReasonSequenceNumber() );
        }

//        System.out.println( "dependency sequences: " + new CommonUtils().listProp( dependencySummary, "alReasonSequenceNumber" ) );
    }


    private static List<DependencyDecisionResultVO> createDependencySummaryList( final Veteran      veteran,
                                                                                 final int          numDependents ) {

        List<DependencyDecisionResultVO>    dependentList   = new ArrayList<DependencyDecisionResultVO>();
        List<Integer>                       sequenceList    = new ArrayList<Integer>();

        for ( int ii = 0; ii < numDependents; ii++ ) {

            Child                       child       = veteran.getChildren().get( ii );
            DependencyDecisionResultVO  dependent   = createDependencySummary( child );

//            while ( sequenceList.contains( dependent.getAlReasonSequenceNumber() ) ) {
//
//                dependent = createDependencySummary( child );
//            }

            dependentList.add( dependent );
            sequenceList.add( dependent.getAlReasonSequenceNumber() );
        }

        return dependentList;
    }


    private static DependencyDecisionResultVO createDependencySummary(final Child child) {

        DependencyDecisionResultVO      dependent     = new DependencyDecisionResultVO();

        dependent.setFirstName( child.getFirstName() );
        dependent.setFullName( String.format( "%s %s", child.getFirstName(), child.getLastName() ) );
        dependent.setAlReasonSequenceNumber( 55 );
        dependent.setAwardEffectiveDate( RbpsUtil.dateToXMLGregorianCalendar( getRandomDate() ) );
        dependent.setDependencyDecisionTypeDescription( getdependencyDecisionTypeDescription() );
        dependent.setDependencyStatusType( getDependencyStatusTypeDescription() );
        dependent.setGrantDenial( getRandomBoolean() ? "Grant" : "Denial" );
        dependent.setRelationshipTypeDescription( getRandomRelationshipType().getValue() );

        return dependent;
    }


    private static List<AwardLineSummaryVO> createAwardLineSummaryList( final Veteran           veteran,
                                                                        final IntegerContainer  reasonSequence ) {

        List<AwardLineSummaryVO>    summaries = new ArrayList<AwardLineSummaryVO>();
        int                         limit     = getRandomInt( 2, 6 );
        System.out.println( String.format( "generating %d summaries", limit ) );

        for ( int ii = 0; ii <= limit; ii++ ) {

            AwardLineSummaryVO  lineSummary = new AwardLineSummaryVO();

            lineSummary.setNetAmount( BigDecimal.valueOf( getRandomInt( 100, 600 )) );
            lineSummary.setGrossAmount( lineSummary.getNetAmount() );
            lineSummary.setEffectiveDate( RbpsWebServiceClientUtil.asXMLGregoriancalendar( SimpleDateUtils.convertDate( "effectiveDate",
                                                                                                                  "MM-dd-yyyy",
                                                                                                                        getRandomDate() ) ) );
            lineSummary.setDisabilityPercent( Double.toString( getRandomDouble( 100 ) ) );
            lineSummary.setAwardLineReportID( (long) getRandomInt( 10000 ) );
            lineSummary.setVeteranID( veteran.getCorpParticipantId() );
            lineSummary.getAwardLineReasons().addAll( createAwardLineReasons( reasonSequence ) );

            summaries.add( lineSummary );
        }

        return summaries;
    }


    private static List<AwardReasonSeqNbrVO> createAwardLineReasons( final IntegerContainer reasonSequence ) {

        List<AwardReasonSeqNbrVO>   reasons     = new ArrayList<AwardReasonSeqNbrVO>();
        int                         limit       = getRandomInt( 3 );

        for ( int ii = 0; ii <= limit; ii++ ) {

            AwardReasonSeqNbrVO         reason   =   new AwardReasonSeqNbrVO();

            reason.setReasonSequenceNumber( reasonSequence.sequence++ );
            reason.setAwardLineReasonTypeDescription( getawardLineReasonTypeDescription() );

            reasons.add( reason );
        }

        return reasons;
    }


    public static List<AwardReasonSeqNbrVO> getTotalReasonList( final List<AwardLineSummaryVO> awardLineSummary ) {

        List<AwardReasonSeqNbrVO>           reasons = new ArrayList<AwardReasonSeqNbrVO>();

        for ( AwardLineSummaryVO summary : awardLineSummary ) {

            reasons.addAll( summary.getAwardLineReasons() );
        }

        return reasons;
    }


    public static FindDependentOnAwardResponse createFindDependentOnAwardResponse( final Veteran        veteran,
                                                                                   final Dependent      dependent,
                                                                                   final Boolean        dependentOnAward) {

        FindDependentOnAwardResponse response = new FindDependentOnAwardResponse();
        DependentOnAwardServiceReturnVO dependentOnAwardServiceReturnVO = new DependentOnAwardServiceReturnVO();

        dependentOnAwardServiceReturnVO.setAwardType( getRandomAwardType() );
        dependentOnAwardServiceReturnVO.setBeneficiaryID( veteran.getCorpParticipantId() );
        dependentOnAwardServiceReturnVO.setIsDependentOnAward( "" + dependentOnAward );
        dependentOnAwardServiceReturnVO.setDependentID( dependent.getCorpParticipantId() );
        dependentOnAwardServiceReturnVO.setVeteranID( veteran.getCorpParticipantId() );

        response.setReturn( dependentOnAwardServiceReturnVO );

//        System.out.println( "Dependent to find on award: " + new CommonUtils().stringBuilder( dependentOnAwardServiceReturnVO ) );

        return response;
    }


    public static FindDependentsResponse getFindDependentsResponse( final int          numChildren,
                                                                    final boolean      includeSpouse ) {

        FindDependentsResponse response    = new FindDependentsResponse();
        Shrinq3Record          record      = new Shrinq3Record();
        String                 lastName    = getRandomLastName();

        response.setReturn( record );

        for ( int ii = 0; ii < numChildren; ii++ ) {

            Shrinq3Person child = createRandomShrinq3Person( lastName, RelationshipType.CHILD );

            record.getPersons().add( child );
        }

        if ( includeSpouse ) {

            Shrinq3Person spouse = createRandomShrinq3Person( lastName, RelationshipType.SPOUSE );

            record.getPersons().add( spouse );
        }

        return response;
    }


    public static FindDependentsResponse getFindDependentsResponseFromXomKids( final List<Child>      kids,
                                                                    final Spouse        spousex ) {

        FindDependentsResponse response    = new FindDependentsResponse();
        Shrinq3Record          record      = new Shrinq3Record();
        String                 lastName    = getRandomLastName();

        response.setReturn( record );

        for ( Child childx : kids ) {

            Shrinq3Person child = createShrinq3Person( childx, RelationshipType.CHILD );

            record.getPersons().add( child );
        }

        if ( spousex != null ) {

            Shrinq3Person spouse = createShrinq3Person( spousex, RelationshipType.SPOUSE );

            record.getPersons().add( spouse );
        }

        return response;
    }


    public static Shrinq3Person createRandomShrinq3Person( final String             lastName,
                                                           final RelationshipType   relationshipType ) {

        Shrinq3Person  child = new Shrinq3Person();

        child.setPtcpntId( "" + getRandomParticipantId() );
        child.setFirstName( getRandomFirstName() );
        child.setLastName( lastName );
        child.setSsn( "" + getRandomSocialSecurityNumber() );
        child.setDateOfBirth( SimpleDateUtils.convertDate( "dateOfBirth",
                                                     SimpleDateUtils.SIMPLE_DATE_FORMAT,
                                                     getRandomDate() ) );
        child.setAwardIndicator( getRandomBoolean() ? "Y" : "N" );
        child.setRelationship( relationshipType.getCode() );

        return child;
    }


    public static Shrinq3Person createShrinq3Person( final Dependent            dependentx,
                                                     final RelationshipType     relationshipType ) {

        Shrinq3Person  dependent = new Shrinq3Person();

        dependent.setPtcpntId( "" + dependentx.getCorpParticipantId() );
        dependent.setFirstName( dependentx.getFirstName() );
        dependent.setLastName( dependentx.getLastName() );
        dependent.setSsn( "" + dependentx.getSsn() );
        dependent.setDateOfBirth( SimpleDateUtils.convertDate( "dateOfBirth",
                                                         SimpleDateUtils.SIMPLE_DATE_FORMAT,
                                                         dependentx.getBirthDate() ) );
        dependent.setAwardIndicator( dependentx.getAward() != null ? "Y" : "N" );
        dependent.setRelationship( relationshipType.getValue().toUpperCase() );

        return dependent;
    }


    public static FindPOAResponse createPoaResponseWithPoa() {

        return createPoaResponse( "035 - MINNESOTA DEPARTMENT OF VETERAN AFFAIRS" , "POA National Organization" );
    }


    public static FindPOAResponse createPoaResponseWithoutPoa() {

        return createPoaResponse( "01A - David Hicks" , "POA Attorney" );
    }


    private static FindPOAResponse createPoaResponse( final String      organizationName,
                                                     final String       organizationType ) {

        FindPOAResponse     poaResponse     =   new FindPOAResponse();
        ShrinqfPersonOrg    poaInfo         =   new ShrinqfPersonOrg();

        poaResponse.setReturn( poaInfo );
        poaInfo.setPersonOrgName( organizationName );
        poaInfo.setPersonOrganizationName( organizationType );

        return poaResponse;
    }


    public static String getdependencyDecisionTypeDescription() {

        return dependencyDecisionTypeDescriptionList.get( random.nextInt( dependencyStatusTypeDescriptionList.size() ) );
    }

    public static String     getDependencyStatusTypeDescription() {

        return dependencyStatusTypeDescriptionList.get( random.nextInt( dependencyStatusTypeDescriptionList.size() ) );
    }


    public static String     getawardLineReasonTypeDescription() {

        return awardLineReasonTypeDescriptionList.get( random.nextInt( awardLineReasonTypeDescriptionList.size() ) );
    }


    public static List<String> dependencyDecisionTypeDescriptionList   = Arrays.asList(
                                                                        "Dependency Established",
                                                                        "Eligible Minor Child",
                                                                        "School Attendance Begins",
                                                                        "Turns 18",
                                                                        "Turns 23" );

    public static List<String> dependencyStatusTypeDescriptionList     = Arrays.asList(
                                                                        "Minor Child",
                                                                        "Not an Award Dependent",
                                                                        "School Child",
                                                                        "Spouse");

    public static List<String> awardLineReasonTypeDescriptionList      = Arrays.asList(
                                                                    "Change in Spouse Status",
                                                                    "Compensation Rating Adjustment",
                                                                    "Minor Child Adjustment",
                                                                    "Original Award",
                                                                    "School Child Adjustment");


//
//
//    public static List<CorporateDependent> getCorporateChildren( final RbpsRepository    repository,
//                                                                 final List<Child>       children ) {
//
//        List<CorporateDependent>    kids = new ArrayList<CorporateDependent>();
//
//        for ( Child child : children ) {
//
//            CorporateDependent  kid = new CorporateDependent();
//
//            kid.setBirthDate( child.getBirthDate() );
//            kid.setFirstName( child.getFirstName() );
//            kid.setLastName( child.getLastName() );
//            kid.setOnAward( child.isOnCurrentAward() );
//            kid.setParticipantId( child.getCorpParticipantId() );
//            kid.setSocialSecurityNumber( child.getSsn() );
//
//            kids.add( kid );
//        }
//
//        return kids;
//    }


    static class IntegerContainer {
        int     sequence = 0;
    }
}
