/*
 * CommonFactory.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.fixtures;


import gov.va.vba.rbps.coreframework.dto.CorporateDependent;
import gov.va.vba.rbps.coreframework.dto.CorporateDependentId;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.util.SimpleDateUtils;
import gov.va.vba.rbps.coreframework.vo.RelationshipType;
import gov.va.vba.rbps.coreframework.xom.Address;
import gov.va.vba.rbps.coreframework.xom.Award;
import gov.va.vba.rbps.coreframework.xom.AwardStatus;
import gov.va.vba.rbps.coreframework.xom.Child;
import gov.va.vba.rbps.coreframework.xom.ChildType;
import gov.va.vba.rbps.coreframework.xom.Claim;
import gov.va.vba.rbps.coreframework.xom.ClaimLabelType;
import gov.va.vba.rbps.coreframework.xom.Dependent;
import gov.va.vba.rbps.coreframework.xom.DependentDecisionType;
import gov.va.vba.rbps.coreframework.xom.DependentStatusType;
import gov.va.vba.rbps.coreframework.xom.Education;
import gov.va.vba.rbps.coreframework.xom.EducationLevelType;
import gov.va.vba.rbps.coreframework.xom.EducationType;
import gov.va.vba.rbps.coreframework.xom.EndProductType;
import gov.va.vba.rbps.coreframework.xom.FormType;
import gov.va.vba.rbps.coreframework.xom.GenderType;
import gov.va.vba.rbps.coreframework.xom.Marriage;
import gov.va.vba.rbps.coreframework.xom.SalutationType;
import gov.va.vba.rbps.coreframework.xom.School;
import gov.va.vba.rbps.coreframework.xom.Spouse;
import gov.va.vba.rbps.coreframework.xom.Veteran;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
//import java.util.Random;
import java.security.SecureRandom;


/**
 *      This is a test fixture for testing the letter generation code.
 *
 *      This allows us to generate data for tests w/o having to execute
 *      web services for each test.
 */
public class CommonFactory {

	//Fortify Insecure Randomness fix
    //protected static Random           random              = new Random();
	protected static SecureRandom           random              = new SecureRandom();
//    private static CommonUtils   hydratorUtils       = new CommonUtils();



    public static Collection<Object[]> testFileNumbers() {

        Collection<Object[]>  results = new ArrayList<Object[]>();

        results.addAll( testValidFileNumbers() );
        results.addAll( testRandomFileNumbers() );

        return results;
    }


    public static Collection<Object[]> testFileNumbers( final String...   fileNumbers ) {

        Collection<Object[]>  results = testFileNumbers();

        for ( String fileNumber : fileNumbers ) {

            results.addAll( Arrays.asList( new Object[][] { { fileNumber } } ) );
        }

        return results;
    }


    public static Collection<Object[]> testValidFileNumbers() {

        return Arrays.asList( new Object[][] {
                { "196101068" },
                { "196101066" },
                { "999111230" },
                { "123320001" },
//                { "50010150" },
//                { "263819301" },
        });
    }


    public static Collection<Object[]> testRandomFileNumbers() {

        return Arrays.asList( new Object[][] {
                { getRandomFileNumber() },
                { getRandomFileNumber() },
                { getRandomFileNumber() },
                { getRandomFileNumber() },
                { getRandomFileNumber() },
                { getRandomFileNumber() },
                { getRandomFileNumber() },
                { getRandomFileNumber() },
        });
    }



    private static Veteran baseVeteran() {

        Veteran     veteran     = new Veteran();

        String  fileNumber = getRandomFileNumber();

        veteran.setMailingAddress( address() );
        veteran.setAlive( true );
        veteran.setClaim( ep130Claim( Integer.parseInt( fileNumber ) ) );
        veteran.setHasPOA( getRandomBoolean() );
        veteran.setMiddleName( "K" );
        veteran.setFileNumber( fileNumber );
        veteran.setSalutation( getRandomSalutation() );
        veteran.setAwardStatus( new AwardStatus() );
        veteran.setSsn( "" + getRandomSocialSecurityNumber() );
        veteran.setBirthDate( getRandomDate() );

        //
        //        This is valid data from Cory.
        //
//        veteran.setFileNumber( "545646549" );
//        veteran.setCorpParticipantId( 710763L );
        veteran.setFileNumber( "" + fileNumber );
        veteran.setCorpParticipantId( Long.parseLong( fileNumber ) );

        return veteran;
    }


    public static Veteran georgeVeteran() {

        Veteran     veteran     = baseVeteran();

        veteran.setLastName( "Washington" );
        veteran.setFirstName( "George" );

        addChildren( veteran );

        return veteran;
    }


    public static Veteran jeffersonNoMiddleNameVeteran() {

        Veteran     veteran     = baseVeteran();

        veteran.setFirstName( "Thomas" );
        veteran.setLastName( "Jefferson" );
        veteran.setMiddleName( null );
        veteran.setCurrentMarriage( getRandomMarriage( veteran ) );

        addChildren( veteran );

        return veteran;
    }


    public static Veteran adamsVeteran() {

        Veteran     veteran     = baseVeteran();

        veteran.setFirstName( "John" );
        veteran.setLastName( "Adams" );
        veteran.setMiddleName( null );
        veteran.setAlive( false );
        veteran.setCurrentMarriage( getRandomMarriage( veteran ) );

        addChildren( veteran );

        return veteran;
    }


    public static Claim ep130Claim( final int     claimId ) {

        Claim   claim = new Claim();

        claim.setReceivedDate( getRandomDate() );
        claim.setClaimId( claimId );
        claim.setEndProductCode( EndProductType.COMPENSATION_EP130 );
        claim.setClaimLabel( ClaimLabelType.NEW_686C );
        claim.setHasAttachments( false );
        claim.setNew( true );
        claim.setForms( getRandomFormType() );

        return claim;
    }


    public static Award generateRandomAward( final Dependent     dependant ) {

        Award  award = new Award();

        award.setEventDate( getRandomDate() );
        award.setEndDate(getRandomDate());
        award.setDependencyDecisionType( getRandomChildDependencyDecision() );
        award.setDependencyStatusType( getRandomChildDependencyStatusType() );
        return award;
    }


    public static Date getRandomDate() {

        int year   = random.nextInt( 40 ) + 1970;
        int month  = random.nextInt( 12 );
        int day    = random.nextInt( 30 );

        return createDate( String.format( "%d/%d/%d", year, month, day ) );
    }


    private static Date createDate( final String    input ) {

        try
        {
            SimpleDateFormat    formatter = new SimpleDateFormat( "yyyy/MM/dd" );

            return formatter.parse( input );

        } catch ( Throwable ex ) {

            throw new IllegalArgumentException( "Can't make date", ex );
        }
    }


    public Date createDay( final int  year,
                           final int  month,
                           final int  day ) {

        Calendar    cal = Calendar.getInstance();

        cal.set( Calendar.YEAR,             year );
        cal.set( Calendar.MONTH,            month - 1);
        cal.set(  Calendar.DAY_OF_MONTH,    day );

        SimpleDateUtils     dateUtils   =   new SimpleDateUtils();

        return dateUtils.truncateToDay( cal.getTime() );
    }


    public static Address address() {

        Address address = new Address();

        address.setLine1( "7600 Metropolis Blvd." );
        address.setCity( "Austin" );
        address.setState( "Texas" );
        address.setCountry( "USA" );
        address.setZipPrefix( "78744" );

        return address;
    }


    public static void addChildren( final Veteran  veteran ) {

        addChildren( veteran, (2 + random.nextInt( 6 )) );
    }


    public static void addChildren( final Veteran   veteran,
                                    final int       desiredNumChildren ) {

        clearChildList( veteran );
        for ( int ii = 0; ii < desiredNumChildren; ii++ ) {

            veteran.addChild( getRandomChild( veteran ) );
        }
    }


    public static Child getRandomChild( final Veteran   veteran ) {

        Child  child = new Child();

        populateDependent( veteran, child );

        child.setChildType(  getRandomChildType() );
        child.setSchoolChild( getRandomBoolean() );
        child.setSeriouslyDisabled( getRandomBoolean() );
       // child.setSeriouslyDisabledVerified( getRandomBoolean() );
        child.setPreviousTerms( getRandomListOfEducation( 4 ) );

        if ( getRandomBoolean() ) {
            child.setMinorSchoolChildAward( generateRandomAward( child ) );
        }

        return child;
    }


    public static List<Education> getRandomListOfEducation( final int num ) {

        List<Education>  education = new ArrayList<Education>();

        for ( int ii = 0; ii < num; ++ii ) {

            education.add( getRandomEducation() );
        }

        return education;
    }


    public static Education getRandomEducation() {

        Education   education   = new Education();
        School      school      = new School();
        school.setName( "Texas A&M University" );
        school.setAddress( address() );

        education.setCourseEndDate( getRandomDate() );
        education.setCourseName( "Organic Chemistry" );
        education.setCourseStudentStartDate( getRandomDate() );
        education.setEducationLevelType( getRandomEducationLevelType() );
        education.setEducationType( getRandomEducationType() );
        education.setExpectedGraduationDate( getRandomDate() );
        education.setHoursPerWeek( 30 );
        education.setOfficialCourseStartDate( education.getCourseStudentStartDate() );
        education.setSchool( school );
        education.setSessionsPerWeek( 7 );
        education.setSubjectName( "Chemistry" );

        return education;
    }


    private static EducationType getRandomEducationType() {

        int index = random.nextInt( EducationType.values().length );

        return EducationType.values()[ index ];
    }


    public static EducationLevelType getRandomEducationLevelType() {

        int index = random.nextInt( EducationLevelType.values().length );

        return EducationLevelType.values()[ index ];
    }


    public static void populateDependent( final Veteran     veteran,
                                          final Dependent   dependant ) {

        dependant.setVnpParticipantId( getRandomParticipantId() );
        dependant.setCorpParticipantId( getRandomParticipantId() );
        dependant.setFirstName( getRandomFirstName() );
        dependant.setMiddleName( getRandomFirstName() );
        dependant.setLastName( veteran.getLastName() );
        dependant.setSsn( "" + getRandomSocialSecurityNumber() );
        dependant.setGender( getRandomGender() );
        dependant.setBirthDate( getRandomDate() );

        if ( getRandomBoolean() ) {
            dependant.setAward( generateRandomAward( dependant ) );
        }
    }


//    public static FindDependentOnAwardResponse createFindDependentOnAwardResponse( final Veteran        veteran,
//                                                                                   final Dependent      dependent,
//                                                                                   final Boolean        dependentOnAward) {
//
//        FindDependentOnAwardResponse response = new FindDependentOnAwardResponse();
//        DependentOnAwardServiceReturnVO dependentOnAwardServiceReturnVO =
//            new DependentOnAwardServiceReturnVO();
//
//        dependentOnAwardServiceReturnVO.setAwardType( getRandomAwardType() );
//        dependentOnAwardServiceReturnVO.setBeneficiaryID( veteran.getParticipantId() );
//        dependentOnAwardServiceReturnVO.setIsDependentOnAward( "" + dependentOnAward );
//        dependentOnAwardServiceReturnVO.setDependentID( dependent.getParticipantId() );
//        dependentOnAwardServiceReturnVO.setVeteranID( veteran.getParticipantId() );
//
//        response.setReturn( dependentOnAwardServiceReturnVO );
//
//        return response;
//    }
//
//
//  public static FindDependentsResponse getFindDependentsResponse( final int          numChildren,
//                                                                  final boolean      includeSpouse ) {
//
//      FindDependentsResponse response    = new FindDependentsResponse();
//      Shrinq3Record          record      = new Shrinq3Record();
//      String                 lastName    = getRandomLastName();
//
//      response.setReturn( record );
//
//      for ( int ii = 0; ii < numChildren; ii++ ) {
//
//          Shrinq3Person child = createRandomShrinq3Person( lastName, RelationshipType.CHILD );
//
//          record.getPersons().add( child );
//      }
//
//      if ( includeSpouse ) {
//
//            Shrinq3Person spouse = createRandomShrinq3Person( lastName, RelationshipType.SPOUSE );
//
//          record.getPersons().add( spouse );
//      }
//
//      return response;
//  }
//
//
//    public static Shrinq3Person createRandomShrinq3Person( final String             lastName,
//                                                           final RelationshipType   relationshipType ) {
//
//        Shrinq3Person  child = new Shrinq3Person();
//
//        child.setPtcpntId( "" + getRandomParticipantId() );
//        child.setFirstName( getRandomFirstName() );
//        child.setLastName( lastName );
//        child.setSsn( "" + getRandomSocialSecurityNumber() );
//        child.setDateOfBirth( hydratorUtils.convertDate( "dateOfBirth", getRandomDate() ) );
//        child.setAwardIndicator( getRandomBoolean() ? "Y" : "N" );
//        child.setRelationship( relationshipType.getCode() );
//
//        return child;
//    }


    public static List<CorporateDependent> getCorporateChildren( final RbpsRepository    repository,
                                                                 final List<Child>       children ) {

        List<CorporateDependent>    kids = new ArrayList<CorporateDependent>();

        for ( Child child : children ) {

            CorporateDependent  kid = new CorporateDependent();

            kid.setBirthDate( child.getBirthDate() );
            kid.setFirstName( child.getFirstName() );
            kid.setLastName( child.getLastName() );
            kid.setOnAward( child.isOnCurrentAward() );
            kid.setParticipantId( child.getCorpParticipantId() );
            kid.setSocialSecurityNumber( child.getSsn() );

            kids.add( kid );
        }

        return kids;
    }


    public static CorporateDependent getCorporateSpouse( final RbpsRepository   repository,
                                                         final Spouse           spouse ) {

        CorporateDependent  corporateSpouse = new CorporateDependent();

        corporateSpouse.setBirthDate( spouse.getBirthDate() );
        corporateSpouse.setFirstName( spouse.getFirstName() );
        corporateSpouse.setLastName( spouse.getLastName() );
        corporateSpouse.setOnAward( spouse.isOnCurrentAward() );
        corporateSpouse.setParticipantId( spouse.getCorpParticipantId() );
        corporateSpouse.setSocialSecurityNumber( spouse.getSsn() );

        return corporateSpouse;
    }


    public static Marriage getRandomMarriage( final Veteran veteran ) {

        Marriage    marriage = new Marriage();

        marriage.setMarriedPlace( address() );
        marriage.setStartDate( getRandomDate() );
        marriage.setMarriedTo( getRandomSpouse( veteran ) );

        return marriage;
    }


    public static Spouse getRandomSpouse( final Veteran  veteran ) {

        Spouse  spouse = new Spouse();

        populateDependent( veteran, spouse );

        spouse.setVet( getRandomBoolean() );
        spouse.setFileNumber( "" + random.nextInt( 999999 ) );

        return spouse;
    }


    public static RelationshipType getRandomRelationshipType() {

        int     relationshipRange = random.nextInt( 12 );

        if ( relationshipRange < 8 ) {

            return RelationshipType.CHILD;
        }

        return RelationshipType.SPOUSE;
    }


    public static long getRandomParticipantId() {

        return random.nextInt( 999999 );
    }


    public static ChildType getRandomChildType() {

        return ChildType.values()[ random.nextInt( ChildType.values().length )];
    }


    public static List<FormType> getRandomFormType() {

        List<FormType> list = new ArrayList<FormType>();
        list.add( FormType.values()[ random.nextInt( FormType.values().length )] );

        return list;
    }


    public static SalutationType getRandomSalutation() {

        return SalutationType.values()[ random.nextInt( SalutationType.values().length )];
    }


    public static DependentDecisionType getRandomChildDependencyDecision() {

        int index = random.nextInt( ChildType.values().length );

        if ( index == 4 ) {

            index = 1;
        }

        return DependentDecisionType.values()[ index ];
    }


    public static DependentStatusType getRandomChildDependencyStatusType() {

        int index = random.nextInt( ChildType.values().length );

        if ( index == 3 ) {

            index = 3;
        }

        return DependentStatusType.values()[ index ];
    }


    public static String getRandomAwardType() {

        return "CPL";
    }


    public static int getRandomSocialSecurityNumber() {

        return (random.nextInt( 9999999 ) + 100000000);
    }


    public static GenderType getRandomGender() {

        return getRandomBoolean() ? GenderType.MALE : GenderType.FEMALE;
    }


    public static boolean getRandomBoolean() {

        return random.nextInt() % 2 == 0;
    }


    public static String getRandomFirstName() {

        if ( unusedFirstNames.isEmpty() ) {

            unusedFirstNames.addAll( firstNames );
        }

        String name = unusedFirstNames.get( random.nextInt( unusedFirstNames.size() ) );
        unusedFirstNames.remove( name );

        return name;
    }


    public static String getRandomLastName() {

        return lastNames.get( random.nextInt( lastNames.size() ) );
    }


    public static double getRandomDouble( final double max ) {

        return random.nextDouble() * max;
    }


    public static int getRandomInt( final int max ) {

        return random.nextInt( max );
    }


    public static int getRandomInt( final int min,
                                    final int max ) {

        return random.nextInt( max - min ) + min;
    }


    public static String getRandomFileNumber() {

        return "" + getRandomInt( 111111111, 999999999 );
    }


    public static void clearChildList( final Veteran veteran ) {

        veteran.setChildren( new ArrayList<Child>() );
    }


    public static void logCorporateKids( final RbpsRepository repository ) {

        List<CorporateDependent>  children = repository.getChildren();

        logCorporateKids( children );
    }


    public static void logCorporateKids( final List<CorporateDependent> children ) {

        System.out.println( "num corporate kids: " + children.size() );

        int index = 0;
        for ( CorporateDependent    child : children ) {

            System.out.println( "" + index++ + " child: " + child.toString().replace( ",", ",\n\t") );
        }
    }


    public static void logCorporateIdsForKids( final List<CorporateDependentId> children ) {

        System.out.println( "num corporate kids/spouse: " + children.size() );

        int index = 0;
        for ( CorporateDependentId    child : children ) {

            logCorporateIdInfo( index, child );
            index++;
        }
    }


    private static void logCorporateIdInfo( final int index, final CorporateDependentId child ) {

        System.out.println( "" + index + " child/spouse: " + child.toString().replace( ",", ",\n\t") );
    }


    public static void logKids( final RbpsRepository repository ) {

        Veteran veteran = repository.getVeteran();

        System.out.println( "file number:        " + veteran.getFileNumber() );
        System.out.println( "particpant id:      " + veteran.getCorpParticipantId() );
        System.out.println( "number of XOM kids: " + veteran.getChildren().size() );
        System.out.println( "has spouse:         " + (veteran.getCurrentMarriage() != null) );

        int index = 0;
        for ( Child child : repository.getVeteran().getChildren() ) {

            System.out.println( "" + index++ + " child: " + child.toString().replace( ",", ",\n\t") );
        }

        if ( veteran.getCurrentMarriage() != null ) {

            System.out.println( "" + index++ + " spouse: " + veteran.getCurrentMarriage().getMarriedTo().toString() );
        }
    }





    private static List<String>     firstNames  = Arrays.asList( "Joe",
                                                                 "Raja",
                                                                 "Hanh",
                                                                 "Sweta",
                                                                 "George",
                                                                 "Thomas",
                                                                 "John",
                                                                 "David",
                                                                 "Teddy",
                                                                 "Franklin",
                                                                 "Ronald",
                                                                 "Herbert",
                                                                 "Dwight",
                                                                 "Calvin",
                                                                 "Abraham",
                                                                 "Andres" );

    private static List<String>     unusedFirstNames    = new ArrayList<String>( firstNames );
    private static List<String>     lastNames           = Arrays.asList( "Washington",
                                                                 "Jefferson",
                                                                 "Adams",
                                                                 "Madison",
                                                                 "Grant",
                                                                 "Hoover",
                                                                 "Coolidge",
                                                                 "Truman",
                                                                 "Reagan",
                                                                 "Bush",
                                                                 "Fillmore",
                                                                 "Beskrowni",
                                                                 "Suman",
                                                                 "Lincoln" );
}
