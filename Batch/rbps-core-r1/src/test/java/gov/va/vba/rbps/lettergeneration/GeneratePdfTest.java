/*
 * GeneratePdfTest.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.lettergeneration;


import gov.va.vba.framework.esb.documentmanagement.PdfDocument;
import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.RbpsAbstractTest;
import gov.va.vba.rbps.claimprocessor.impl.EP130ClaimPostProcessor;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.exception.RbpsRuntimeException;
import gov.va.vba.rbps.coreframework.util.CommonUtils;
import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.coreframework.util.SaiToStringStyle;
import gov.va.vba.rbps.coreframework.xom.Award;
import gov.va.vba.rbps.coreframework.xom.Child;
import gov.va.vba.rbps.coreframework.xom.Spouse;
import gov.va.vba.rbps.coreframework.xom.Veteran;
import gov.va.vba.rbps.fixtures.CommonFactory;
import gov.va.vba.rbps.fixtures.CoreFactory;
import gov.va.vba.rbps.services.populators.FindDependentOnAwardPopulator;
import gov.va.vba.rbps.services.ws.client.handler.awards.FindDependentOnAwardWSHandler;
import gov.va.vba.rbps.services.ws.client.handler.awards.util.ProcessAwardDependentRequestBuilder;
import gov.va.vba.rbps.services.ws.client.mapping.awards.amend.ProcessAwardDependentResponse;
import gov.va.vba.rbps.services.ws.client.mapping.awards.dep.FindDependentOnAwardResponse;
import gov.va.vba.rbps.services.ws.client.util.TestUtils;
import gov.va.vba.rbps.services.ws.client.util.XmlUnmarshaller;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.time.DateUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.util.CollectionUtils;



/**
 *      Test case for the <code>GeneratePdf</code> class
 */
public class GeneratePdfTest   extends RbpsAbstractTest {

    private static Logger   logger          = Logger.getLogger(GeneratePdfTest.class);


    private Veteran         				veteran;
    private TestUtils       				testUtils   = new TestUtils();
    private LogUtils        				logUtils    = new LogUtils( logger, true );
    private FindDependentOnAwardWSHandler	depHandler;
    private FindDependentOnAwardPopulator	depOnAwardPopulator;
    

    // no longer spring beans test must change FUTURE
    private RbpsRepository                  repo = new RbpsRepository();

    private GeneratePdf                     pdf;


    @Before
    public void setup() {

    	super.setup();
    	
        LogUtils.setGlobalLogit( true );
        depHandler 			= ( FindDependentOnAwardWSHandler ) getBean("awardsFindDependentOnAwardWSHandler");
        depOnAwardPopulator = ( FindDependentOnAwardPopulator ) getBean("findDependentOnAwardPopulator");
        pdf = ( GeneratePdf) getBean("generatePdf");
    }

    
    @Test
    public void shouldCorrectSchoolTerm() {

        testUtils.populateUserInfo( repo, "gov/va/vba/rbps/lettergeneration/schoolBeginDate.userInfo.response.xml" );

        repo.setClaimStationLocationId( 1943L );
        repo.getClaimStationAddress().setStationId("335");
        testUtils.populateSojAddress( repo, 1943L );

        testAgainstSampleXmlFile( repo,
                                  "gov/va/vba/rbps/lettergeneration/schoolBeginDate.awards.response.xml" );
    }   
    
    
    @Ignore
    @Test
    public void shouldFiduciaryInLetter() {

        //testUtils.populateUserInfo( repo, "gov/va/vba/rbps/lettergeneration/fiduciary.userInfo.response.xml" );

        repo.setClaimStationLocationId( 1943L );
        testUtils.populateSojAddress( repo, 1943L );

        testUtils.populateFiduciaryInfo(repo, "gov/va/vba/rbps/services/populators/findFiduciary.response.xml");
        testAgainstSampleXmlFile( repo,
                                  "gov/va/vba/rbps/lettergeneration/fiduciary.awards.response.xml" );
    }
    
    @Ignore
    @Test
    public void shouldLowercaseFirstName() {

        //testUtils.populateUserInfo( repo, "gov/va/vba/rbps/lettergeneration/lowercaseFirstName.userInfo.response.xml" );

        repo.setClaimStationLocationId( 1943L );
        testUtils.populateSojAddress( repo, 1943L );

        testAgainstSampleXmlFile( repo,
                                  "gov/va/vba/rbps/lettergeneration/lowercaseFirstName.awards.response.xml" );
    }
    
    @Ignore
    @Test
    public void shouldWeHaveDecidedParaPreviousTerm() {

        //testUtils.populateUserInfo( repo, "gov/va/vba/rbps/lettergeneration/lastTerm.userInfo.response.xml" );

        repo.setClaimStationLocationId( 1943L );
        testUtils.populateSojAddress( repo, 1943L );

        testAgainstSampleXmlFile( repo,
                                  "gov/va/vba/rbps/lettergeneration/lastTerm.award.response.xml" );
    }
    
    
    @Ignore
    @Test
    public void shouldWeHaveDecidedParaMoreThanTwoChildren() {

        //testUtils.populateUserInfo( repo, "gov/va/vba/rbps/lettergeneration/moreThan2Children.userInfo.response.xml" );

        repo.setClaimStationLocationId( 1943L );
        testUtils.populateSojAddress( repo, 1943L );

        testAgainstSampleXmlFile( repo,
                                  "gov/va/vba/rbps/lettergeneration/moreThan2Children.award.response.xml" );
    }
    
    @Ignore
    @Test
    public void shouldWeHaveDecidedParaTest1() {

        //testUtils.populateUserInfo( repo, "gov/va/vba/rbps/lettergeneration/test1.userInfo.response.xml" );

        repo.setClaimStationLocationId( 1943L );
        testUtils.populateSojAddress( repo, 1943L );

        testAgainstSampleXmlFile( repo,
                                  "gov/va/vba/rbps/lettergeneration/test1.award.response.xml" );
    }
    
    @Ignore
    @Test
    public void shouldWeHaveDecidedParaSpouseNoChildren() {

        //testUtils.populateUserInfo( repo, "gov/va/vba/rbps/lettergeneration/spouseNoChildren.userInfo.response.xml" );

        repo.setClaimStationLocationId( 1943L );
        testUtils.populateSojAddress( repo, 1943L );

        testAgainstSampleXmlFile( repo,
                                  "gov/va/vba/rbps/lettergeneration/spouseNoChild.award.response.xml" );
    }
    
    @Ignore
    @Test
    public void shouldWeHaveDecidedPara() {

        //testUtils.populateUserInfo( repo, "gov/va/vba/rbps/lettergeneration/796136568.userInfo.response.xml" );

        repo.setClaimStationLocationId( 1943L );
        testUtils.populateSojAddress( repo, 1943L );
        
        repo.getVeteran().setCorpParticipantId( 32218612 );
        repo.getVeteran().getCurrentMarriage().getMarriedTo().setCorpParticipantId( 32218612 );
        testAgainstSampleXmlFile( repo,
                                  "gov/va/vba/rbps/lettergeneration/796136568.awards.response.xml" );
    }
    


    @Ignore
    @Test
    public void generateLetterAndCsv() {

//        GeneratePdf                     pdf         = new GeneratePdf();
        ProcessAwardDependentResponse   response    = CoreFactory.createProcessAwardDependentResponse( veteran );
        RbpsRepository                  repository  = new RbpsRepository();

        repository.setVeteran( veteran );

        pdf.generateLetterAndCsv( repository, response );
    }


    @Ignore
    @Test
    public void processAwardShouldTruncateDates() {
//
//        CommonUtils utils = new CommonUtils();
        Calendar cal = Calendar.getInstance();
        cal.set( Calendar.HOUR_OF_DAY, 12 );
        cal.set( Calendar.MINUTE, 1 );

//        logUtils.log( "rounded time " + DateUtils.round( cal.getTime(), Calendar.DAY_OF_MONTH ) );
//        logUtils.log( "trunc time " + DateUtils.truncate( cal.getTime(), Calendar.DAY_OF_MONTH ) );

        Award                           award   = CommonFactory.generateRandomAward( null );
        award.setEndDate( cal.getTime() );
        award.setEventDate( cal.getTime() );

        ProcessAwardDependentRequestBuilder  builder = new ProcessAwardDependentRequestBuilder();

        Date eventDate  = builder.getEventDate( award, repo );
        Date endDate    = builder.getEndDate( award );

//        logUtils.log( "event date: " + eventDate );
//        logUtils.log( "end date: " + endDate );
    }


    @Ignore
    @Test
    public void shouldNotHaveXmlErrorFromAmpersand() {

        testFunkyAddressCharacter( "Foo & bar" );
    }


    @Ignore
    @Test
    public void shouldNotHaveXmlErrorFromDash() {

        testFunkyAddressCharacter( "Foo - bar" );
    }


    @Ignore
    @Test
    public void shouldNotHaveXmlErrorFromUnderscore() {

        testFunkyAddressCharacter( "Foo _ bar" );
    }


    @Ignore
    @Test
    public void shouldNotHaveXmlErrorFromLessThan() {

        testFunkyAddressCharacter( "Foo < bar" );
    }


    @Ignore
    @Test
    public void shouldNotHaveXmlErrorFromComma() {

        testFunkyAddressCharacter( "Foo, bar" );
    }


    @Ignore
    @Test
    public void shouldNotHaveMissingParts() {

        RbpsRepository                  repo  = new RbpsRepository();
        //testUtils.populateUserInfo( repo, "gov/va/vba/rbps/lettergeneration/missingParts/userInfo.missingParts.response.xml" );

        testUtils.populateSojAddress( repo, repo.getClaimStationLocationId() );

        testAgainstSampleXmlFile( repo,
                                  "gov/va/vba/rbps/lettergeneration/missingParts/awards.missingParts.response.xml" );
    }


    @Ignore
    @Test
    public void generateDenyLetterAndCsv() {

        //      Pooh should be denied.
        //      Compensation rating adjustment
        //      Child Dependency, Foo, Rob, Bar added.
        //      Child Dependency, Rob removed.
        //      Rob removed before being added - just because of how the test was made.
        testAgainstSampleXmlFile( "gov/va/vba/rbps/lettergeneration/awardsOutput_deny.xml" );
    }

    @Ignore
    @Test
    public void generateSpouseTwoKidsLetterAndCsv() {

        //      Twinone Denial and Twintwo Denial added and removed.
        //      Spouse added.
        //      Childtwo Denial Cistontest was denied.
        //      Childone Denial Cistontest was denied.
        testAgainstSampleXmlFile( "gov/va/vba/rbps/lettergeneration/awardsOutput_spouse_two_kids.xml" );
    }


    @Ignore
    @Test
    public void generateSpouseTwoKidsSameNameLetterAndCsv() {

        //      Cool and Mat (spouse) added.
        //      Cool added (a different Cool)
        //      Cool and Cool removed.
        //      Mat and Cool and Cool listed in paragraph.
        testAgainstSampleXmlFile( "gov/va/vba/rbps/lettergeneration/awardsOutput_spouse_two_kids_same_name.xml" );
    }


    @Ignore
    @Test
    public void generateFirstActualDenialFromAwards() {

        //      Maggie Moyet denied.
        testAgainstSampleXmlFile( "gov/va/vba/rbps/lettergeneration/999656884.response" );
    }


    @Ignore
    @Test
    public void generateSchoolAttendenceBegins() {

        //      Mark added and removed for no longer attending school.
        testAgainstSampleXmlFile( "gov/va/vba/rbps/lettergeneration/999111234.response" );
    }


    @Ignore
    @Test
    public void generateAwardsWorkaroundForMissingDependent() {

        //      George, Jack, Kerry, Martha (spouse) added
        //      George, Jack, Kerry removed.
        //      Test: there's no "Dependent was removed" in the award summary table.
        testAgainstSampleXmlFile( "gov/va/vba/rbps/lettergeneration/186370.response" );
    }


    @Ignore
    @Test
    public void generate999565987ShouldShowSpouse() {

        //      Randy, Brandy, and Sandy (spouse) added.
        //      Brandy, Randy removed.
        //      Test: Sandy was missing.
        testAgainstSampleXmlFile( "gov/va/vba/rbps/lettergeneration/999565897.awards.response" );
    }


  @Ignore
    @Test
    public void generate999565982ShouldShowRightNumberOfDependents() {

        //      Nancy, Carl, and Jeff (spouse) added.
        //      Nancy and Carl removed.
        //      Test: wrong number of dependents in list in paragraph.
        testAgainstSampleXmlFile( "gov/va/vba/rbps/lettergeneration/999565892.awards.response.xml" );
    }


    @Ignore
    @Test
    public void generate999565988ShouldNotShowDupsInParagraph() {

        //      Kiran added.
        //      Kiran turns 18
        //      Kiran goes to school. (should also be removed, but data is bad from awards)
        //      Test: Kiran should show once in list in paragraph.
        testAgainstSampleXmlFile( "gov/va/vba/rbps/lettergeneration/999565898.awards.response.xml" );
    }


    @Ignore
    @Test
    public void generateAvoidNpe() {

        testAgainstSampleXmlFile( "gov/va/vba/rbps/lettergeneration/999565898.awards.response.xml" );
    }


    @Ignore
    @Test
    public void generateAvoidYetAnotherNpe() {

        testAgainstSampleXmlFile( "gov/va/vba/rbps/lettergeneration/796100365.NPE.awards.response.xml" );
    }


    @Ignore
    @Test
    public void shouldGenerateLetterFor109() {

        //
        //      Was not generating the table at the top because of a bug in how the AwardSummary was
        //      figuring out if any of the reasons had a claim id.
        //
        testAgainstSampleXmlFile( "gov/va/vba/rbps/lettergeneration/999981527/awards.response.xml" );
    }


    @Ignore
    @Test
    public void generateAvoidAnotherNpe() {

        //       This one has bad data in it, at least for now

        RbpsRepository                  repo  = new RbpsRepository();
        //testUtils.populateUserInfo( repo, "gov/va/vba/rbps/lettergeneration/999912564.userInfo.response.xml" );
        testAgainstSampleXmlFile( repo, "gov/va/vba/rbps/lettergeneration/999912564.awards.response.xml" );
    }


    @Ignore
    @Test
    public void shouldIncludeDenial() {

        //      the first change date causes isPrior
        //      to not include the denial (the only thing in the letter)
        //      So we cause isPrior to ignore denials on the claim.
        //
        RbpsRepository                  repo  = new RbpsRepository();
        //testUtils.populateUserInfo( repo, "gov/va/vba/rbps/lettergeneration/234565321.userInfo.response.xml" );
        testAgainstSampleXmlFile( repo, "gov/va/vba/rbps/lettergeneration/234565321.awards.response.xml" );
    }


    @Ignore
    @Test
    public void generateSpouseNPE() {

        //      Spouse Jamie was added to award.
        veteran.getClaim().setReceivedDate( new Date() );
        testAgainstSampleXmlFile( "gov/va/vba/rbps/lettergeneration/999565895.response" );
    }


    @Ignore
    @Test
    public void generateDenialWithDenialStanza() {

        //      spouse giraffe added
        //      school child added and removed due to no longer attending school.
        //      minor child removed for turning 18.
        //      school child also denied.
        //      due to weirdness in the test data, spouse not in paragraph.
        veteran.getClaim().setReceivedDate( new Date() );
        testAgainstSampleXmlFile( "gov/va/vba/rbps/lettergeneration/0089_2_out.xml" );
    }


    @Ignore
    @Test
    public void generateSchoolAttendanceBeginsMessage() {

        //      Changed the SCHATTB message - %s was added to your award as a school child

        RbpsRepository                  repo  = new RbpsRepository();
//        //testUtils.populateUserInfo( repo, "gov/va/vba/rbps/lettergeneration/999807603.userInfo.response.xml" );
        testAgainstSampleXmlFile( repo, "gov/va/vba/rbps/lettergeneration/999807603.awards.response.xml" );
    }


    @Ignore
    @Test
    public void generate1422LetterAndCsv() {

        testAgainstSampleXmlFile( "gov/va/vba/rbps/lettergeneration/1422.response" );
    }


    @Ignore
    @Test
    public void generate1423LetterAndCsv() {

//        Date foo = Calendar.getInstance( TimeZone.getTimeZone( "GMT-8" ) ).getTime();
//        logUtils.log( RbpsUtil.dayToXMLGregorianCalendar( DateUtils.truncate( foo, Calendar.DAY_OF_MONTH ) ) );
//        logUtils.log( RbpsUtil.dayToXMLGregorianCalendar( foo ) );
//        logUtils.log( RbpsUtil.dateToXMLGregorianCalendar( null ) );
        testAgainstSampleXmlFile( "gov/va/vba/rbps/lettergeneration/1423.response" );
    }


//    @Test
//    public void generateTwoDenialsLetterAndCsv() {
//
//        testAgainstSampleXmlFile( "gov/va/vba/rbps/lettergeneration/awardsOutput_two_denials.xml" );
//    }


    private void testAgainstSampleXmlFile( final String   xmlPath ) {

        RbpsRepository                  repository  = new RbpsRepository();
        testAgainstSampleXmlFile( repository, xmlPath );
    }


    private void testAgainstSampleXmlFile( final RbpsRepository     repo,
                                           final String             xmlPath ) {

        ProcessAwardDependentResponse   response    = getResponseFromXml( xmlPath );

        if ( repo.getClaimStationLocationId() == null ) {

            repo.setClaimStationLocationId( 1943L );
            testUtils.populateSojAddress( repo, 1943L );
        }

        if ( repo.getVeteran() == null ) {
            repo.setVeteran( veteran );
        }
       
        boolean hasPdf = CommonFactory.getRandomBoolean();
        String  poaOrganizationName = null;
        if ( hasPdf ) {

            poaOrganizationName = "Disabled Veterans Association";
        }
        repo.setPoaOrganizationName( poaOrganizationName );

        pdf.generateLetterAndCsv( repo, response );
    }


    public void testFunkyAddressCharacter( final String       addressLine1 ) {

        RbpsRepository                  repo  = new RbpsRepository();
        //testUtils.populateUserInfo( repo, "gov/va/vba/rbps/lettergeneration/xmlError/userInfo.xmlError.response.xml" );

        repo.setClaimStationLocationId( 1943L );
        testUtils.populateSojAddress( repo, 1943L );

        repo.getClaimStationAddress().setAddressLine1( addressLine1 );

        testAgainstSampleXmlFile( repo,
                                  "gov/va/vba/rbps/lettergeneration/xmlError/awards.xmlError.response.xml" );
    }

    
    private long getDependentId( final RbpsRepository repository ) {
    	
    	Spouse spouse = repository.getVeteran().getCurrentMarriage().getMarriedTo();
    	
    	
    	if ( null != spouse ) {
    		
    		return spouse.getCorpParticipantId();
    	}
    	
    	List<Child> children = repository.getVeteran().getChildren();

    	if ( ! CollectionUtils.isEmpty(children ) ) {
    		
    		return children.get( 0 ).getCorpParticipantId(); 
    	}
    	
    	return 0;
    }
    

//    @Test
//    public void shouldCreateRightNumberOfAwardSummaryFromAwardResponse() {
//
//        List<AwardSummary> summaryList = getSummaryFromResponse( );
//
//        assertThat( summaryList.size(), is(equalTo( seed.size() ) ) );
//    }


//    @Test
//    public void shouldCreateAwardSummaryWithRightAmountsFromAwardResponse() {
//
//        List<AwardSummary> summaryList = getSummaryFromResponse( );
//
//        assertThat( summaryList.get(0).getAwardAmount().doubleValue(),
//                    is(equalTo( seed.get(0).getAwardAmount().doubleValue() ) ) );
//    }


//    @Test
//    public void shouldCreateAwardSummaryWithRightReasonFromAwardResponse() {
//
//        //      FIXME: the reasons shouldn't necessarily be equal
//        //              the dependent name should be in front of some.
//        List<AwardSummary>      summaryList = getSummaryFromResponse( );
//
//        assertThat( summaryList.get(1).getReasons(), is(equalTo( seed.get(1).getReasons() ) ) );
//    }


//    @Test
//    public void shouldCreateAwardSummaryWithRightDateFromAwardResponse() {
//
//        List<AwardSummary> summaryList = getSummaryFromResponse( );
//
//        assertThat( summaryList.get(2).getPaymentChangeDate(),
//                    is(equalTo( seed.get(2).getPaymentChangeDate() ) ) );
//    }


    private ProcessAwardDependentResponse getResponseFromXml( final String xmlPath ) {

        return (ProcessAwardDependentResponse) new XmlUnmarshaller().loadResponse( xmlPath,
                                                       ProcessAwardDependentResponse.class );
    }


//    private List<AwardSummary> getSummaryFromResponse() {
//
//        GeneratePdf                     pdf         = new GeneratePdf();
//        ProcessAwardDependentResponse   response    = CoreFactory.createProcessAwardDependentResponse( veteran );
//        List <AwardSummary>             summaryList = pdf.responseToSummary( response );
//
//        return summaryList;
//    }


//    public static List<AwardSummary> createAwardSummarySeed() {
//
//        List<AwardSummary> seed = new ArrayList<AwardSummary>();
//
//        seed.add( new AwardSummary( 453.0, CommonFactory.getRandomDate(), "gone fishin",    ApprovalType.GRANT ) );
//        seed.add( new AwardSummary( 425.0, CommonFactory.getRandomDate(), "gone shoppin",   ApprovalType.GRANT ) );
//        seed.add( new AwardSummary( 415.0, CommonFactory.getRandomDate(), "gone west",      ApprovalType.DENIAL ) );
//
//        return seed;
//    }
    @Ignore
	@Test
	public void generateLetterFromINputXML() {

		RbpsApplicationDetails rbpsApplicationDetails = new RbpsApplicationDetails();
		rbpsApplicationDetails.setAppname("RBPS");
		rbpsApplicationDetails.setClientmachine("317-WS-3N0255");
		rbpsApplicationDetails.setServerName("vbaausappdev1.vba.va.gov");
		rbpsApplicationDetails.setStationid("335");
		rbpsApplicationDetails.setUsername("281RBPS");
		rbpsApplicationDetails.setPassword("VaRbps!1");
		try {
			//java.io.InputStream inputStream = new FileInputStream(
		//			"C:\\061514-prod\\547159610\\547159610-letter.xml");
		java.io.InputStream inputStream = new FileInputStream(
					"C:\\061514-prod\\438417327\\438417327-letter.xml");
		//	java.io.InputStream inputStream = new FileInputStream(
		//			"C:\\061514-prod\\438417327\\438417327-letter.xml");
		//	java.io.InputStream inputStream = new FileInputStream(
		//			"C:\\061514-prod\\438417327\\438417327-letter.xml");
		//	java.io.InputStream inputStream = new FileInputStream(
		//			"C:\\061514-prod\\438417327\\438417327-letter.xml");

			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			int nRead;
			byte[] data = new byte[1024];
			while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
				buffer.write(data, 0, nRead);
			}
			buffer.flush();

			GenericLetterGeneration genericLetterGeneration = new GenericLetterGeneration();
			//String pdfFileName = genericLetterGeneration.generateFileName("");
			String pdfFileName="438417327-08112014";
			PdfDocument ltetterPdf=genericLetterGeneration.generateRbpsLettter(repo,
			rbpsApplicationDetails, buffer, pdfFileName, true, false,
				false);
			System.out.println("pdf is genrated");
		} catch (Exception ex) {

			throw new RbpsRuntimeException(
					"Error creating xml to generate Rbps letter");
		}

	}
    @Test
	public void generateAwardprint() {

			RbpsRepository repo = new RbpsRepository();
			Veteran vet= new Veteran();
			vet.setFileNumber("535197442");
			repo.setVeteran(vet);
			String xmlPath="gov/va/vba/rbps/lettergeneration/061514-prod/535197442/535197442.awards.reponse.xml";
			ProcessAwardDependentResponse response = getResponseFromXml(xmlPath);
			 if ( response == null ) {

		            return;
		        }
		 AwardPrintSaver   awardPrintSaver= new AwardPrintSaver();
		        awardPrintSaver.saveAwardPrintAndCsv( response, repo );
		  System.out.println("File is genrated");
	}


}
