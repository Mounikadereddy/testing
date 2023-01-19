/*
 * TestUtils.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.services.ws.client.util;


import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.dto.ClaimStationAddress;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.util.CommonUtils;
import gov.va.vba.rbps.coreframework.util.IndentedXomToString;
import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.coreframework.xom.Child;
import gov.va.vba.rbps.services.populators.AwardStatePopulator;
import gov.va.vba.rbps.services.populators.CorporateDependentsPopulator;
import gov.va.vba.rbps.services.populators.FiduciaryPopulator;
import gov.va.vba.rbps.services.populators.FindDependencyDecisionByAwardPopulator;
import gov.va.vba.rbps.services.populators.FindDependentOnAwardPopulator;
//import gov.va.vba.rbps.services.populators.NewSchoolChildPopulator;
import gov.va.vba.rbps.services.populators.OnAwardPopulator;
import gov.va.vba.rbps.services.populators.RatingDatesPopulator;
import gov.va.vba.rbps.services.populators.RbpsRepositoryPopulator;
import gov.va.vba.rbps.services.populators.utils.ClaimStationsBean;
import gov.va.vba.rbps.services.ws.client.handler.awards.util.DependencyDecisionByAwardProducer;
import gov.va.vba.rbps.services.ws.client.mapping.awards.adjustmentDecision.ReadRetiredPayDecnResponse;
import gov.va.vba.rbps.services.ws.client.mapping.awards.amend.ProcessAwardDependentResponse;
import gov.va.vba.rbps.services.ws.client.mapping.awards.awardWebService.FindStationOfJurisdictionResponse;
import gov.va.vba.rbps.services.ws.client.mapping.awards.dep.FindDependentOnAwardResponse;
import gov.va.vba.rbps.services.ws.client.mapping.awards.dependencydecision.FindDependencyDecisionByAwardResponse;
import gov.va.vba.rbps.services.ws.client.mapping.awards.state.FindAwardStateResponse;
import gov.va.vba.rbps.services.ws.client.mapping.org.FindPOAsByFileNumbersResponse;
import gov.va.vba.rbps.services.ws.client.mapping.share.familytree.FindDependentsResponse;
import gov.va.vba.rbps.services.ws.client.mapping.share.familytree.FindFiduciaryResponse;
import gov.va.vba.rbps.services.ws.client.mapping.share.familytree.FindPOAResponse;
import gov.va.vba.rbps.services.ws.client.mapping.share.findstations.FindStationAddressResponse;
import gov.va.vba.rbps.services.ws.client.mapping.share.ratingComparison.CompareByDateRangeResponse;
import gov.va.vba.rbps.services.ws.client.mapping.share.ratingComparison.GetDecisionsAtIssueResponse;
import gov.va.vba.rbps.services.ws.client.mapping.share.sojSignature.FindRegionalOfficesResponse;
import gov.va.vba.rbps.services.ws.client.mapping.share.sojSignature.FindSignaturesByStationNumberResponse;
import gov.va.vba.rbps.services.ws.client.mapping.vonapp.claimlabel.FindBenefitClaimResponse;
import gov.va.vba.rbps.services.ws.client.mapping.vonapp.userinformation.FindByDataSuppliedResponse;

import java.util.List;

import org.springframework.context.ApplicationContext;


/**
 *      Some common methods useful in testing.
 */
public class TestUtils {

    private static Logger logger = Logger.getLogger(TestUtils.class);

//    private CommonUtils                     utils       = new CommonUtils();
    private LogUtils                        logUtils    = new LogUtils( logger, false );


    public FindDependentsResponse getFindDependentsResponseFromXml( final String xmlPath ) {

        return (FindDependentsResponse) new XmlUnmarshaller().loadResponse( xmlPath,
                                                                            FindDependentsResponse.class );
    }




    public FindByDataSuppliedResponse getUserInfoResponseFromXml( final String xmlPath ) {

        return (FindByDataSuppliedResponse) new XmlUnmarshaller().loadResponse( xmlPath,
                                                                                FindByDataSuppliedResponse.class );
    }




    public FindPOAsByFileNumbersResponse getPoaResponseFromXml(final String xmlPath ) {
        return (FindPOAsByFileNumbersResponse) new XmlUnmarshaller().loadResponse( xmlPath,
                FindPOAsByFileNumbersResponse.class );
    }


    public FindStationOfJurisdictionResponse getSOJResponseFromXml(final String xmlPath ) {
        return (FindStationOfJurisdictionResponse) new XmlUnmarshaller().loadResponse( xmlPath,
                FindStationOfJurisdictionResponse.class );
    }

    public FindRegionalOfficesResponse getRegionalOfficesResponseFromXml( final String xmlPath ) {

        return (FindRegionalOfficesResponse) new XmlUnmarshaller().loadResponse( xmlPath,
                                                                                 FindRegionalOfficesResponse.class );
    }




//    public FindRatingDataResponse getFindRatingPathResponseFromXml( final String xmlPath ) {
//
//        return (FindRatingDataResponse) new XmlUnmarshaller().loadResponse( xmlPath,
//                                                                            FindRatingDataResponse.class );
//    }




    public FindSignaturesByStationNumberResponse getSojSignatureResponseFromXml( final String xmlPath ) {

        return (FindSignaturesByStationNumberResponse) new XmlUnmarshaller().loadResponse( xmlPath,
                                                                                           FindSignaturesByStationNumberResponse.class );
    }




    public FindDependencyDecisionByAwardResponse getDependencyDecisionResponseFromXml( final String xmlPath ) {

        return (FindDependencyDecisionByAwardResponse) new XmlUnmarshaller().loadResponse( xmlPath,
                                                                                           FindDependencyDecisionByAwardResponse.class );
    }



    public FindFiduciaryResponse getFindFiduciaryResponseFromXml( final String xmlPath ) {

        return (FindFiduciaryResponse) new XmlUnmarshaller().loadResponse( xmlPath, FindFiduciaryResponse.class );
    }


    public CompareByDateRangeResponse getRatingComparisonResponseFromXml( final String xmlPath ) {

        return (CompareByDateRangeResponse) new XmlUnmarshaller().loadResponse( xmlPath,
                                                                                CompareByDateRangeResponse.class );
    }


    public GetDecisionsAtIssueResponse getDecisionsAtIssueResponseFromXml( final String xmlPath ) {

        return (GetDecisionsAtIssueResponse) new XmlUnmarshaller().loadResponse( xmlPath,
        																		 GetDecisionsAtIssueResponse.class );
    }
    

    public FindAwardStateResponse getAwardStateResponseFromXml( final String xmlPath ) {

        return (FindAwardStateResponse) new XmlUnmarshaller().loadResponse( xmlPath,
                                                                            FindAwardStateResponse.class );
    }


    public ProcessAwardDependentResponse getAmendAwardResponseFromXml( final String xmlPath ) {

        return (ProcessAwardDependentResponse) new XmlUnmarshaller().loadResponse( xmlPath,
                                                                                   ProcessAwardDependentResponse.class );
    }


    public FindDependentOnAwardResponse getFindDependentOnAwardResponseFromXml( final String xmlPath ) {

        return (FindDependentOnAwardResponse) new XmlUnmarshaller().loadResponse( xmlPath,
        																		  FindDependentOnAwardResponse.class );
    }


    public FindStationAddressResponse getFindStationAddressResponseFromXml( final String xmlPath ) {

        return (FindStationAddressResponse) new XmlUnmarshaller().loadResponse( xmlPath,
        																		  FindStationAddressResponse.class );
    }    
    
    public FindBenefitClaimResponse getFindBenefitClaimResponseFromXml( final String xmlPath ) {
    	
    	 return (FindBenefitClaimResponse) new XmlUnmarshaller().loadResponse( xmlPath,
    			 															   FindBenefitClaimResponse.class );
    }




    public ReadRetiredPayDecnResponse getRetiredPayDecnResponse( final String xmlPath ) {

        return (ReadRetiredPayDecnResponse) new XmlUnmarshaller().loadResponse( xmlPath,
        																		ReadRetiredPayDecnResponse.class );
    }
    
    public void populateUserInfo( final RbpsRepository  repo,
                                  final String          userInfoPath ) {

        RbpsRepositoryPopulator                 userInfoPopulator       = new RbpsRepositoryPopulator();
        FindByDataSuppliedResponse              userInfoResponse        = getUserInfoResponseFromXml( userInfoPath );

        userInfoPopulator.setShouldPerformSchoolChildIndWorkaround( true );

        userInfoPopulator.populateFromVonapp( userInfoResponse.getReturn().getUserInformation().get(0), repo );
    }



    public void populateCorporateDependents( final RbpsRepository       repo,
                                             final String               dependentsPath ) {

        CorporateDependentsPopulator            dependentsPopulator     = new CorporateDependentsPopulator();
        FindDependentsResponse                  dependentsResponse      = getFindDependentsResponseFromXml( dependentsPath );

//        dependentsPopulator.setRepository( repo );
//        OnAwardPopulator.populateFromDependents( dependentsResponse, repo );
        OnAwardPopulator.setOnCurrentAwardForXomDependent( repo );
    }



    public void populateNewSchoolChild( final RbpsRepository       repo,
                                        final String               path ) {

/*    	NewSchoolChildPopulator  				populator       = new NewSchoolChildPopulator();
        FindDependencyDecisionByAwardResponse   response        = getDependencyDecisionResponseFromXml( path );
        List <Child>                            xomChildren     = repo.getVeteran().getChildren();

        populator.setNewSchoolChild( response, xomChildren );*/
    }


    public void populateRatingDates( final RbpsRepository       repo,
                                     final String               path ) {

        RatingDatesPopulator            populator       = new RatingDatesPopulator();
        CompareByDateRangeResponse      response        = getRatingComparisonResponseFromXml( path );

        populator.populateRatingDates( repo, response );
    }


    public void populateAwardState( final RbpsRepository       repo,
                                    final String               path ) {

        AwardStatePopulator         populator       = new AwardStatePopulator();
        FindAwardStateResponse      response        = getAwardStateResponseFromXml( path );

//        populator.setRepository( repo );
        populator.populateFromFindAwardStateWS( response, repo );
    }


    public void populateSojAddress( final RbpsRepository        repo,
                                    final Long                  claimLocationId ) {

        ClaimStationsBean           populator       = new ClaimStationsBean();
        populateClaimStationsBean( populator );

//        populator.populateClaimStationData( repo );
    }


    public void populateClaimStationsBean( final ClaimStationsBean populator ) {

        populator.addClaimStationAddressToMap( getStPaulAddress() );
        populator.addClaimStationAddressToMap( getStPeteAddress() );
    }


    private ClaimStationAddress getStPaulAddress() {

        ClaimStationAddress     address = new ClaimStationAddress();
        address.setClaimLocationId( 1943 );
        address.setStationId( "335" );
        address.setName( "St. Paul" );
        address.setAddressLine1( "St. Paul Regional Office" );
        address.setAddressLine2( "Bishop Henry Whipple Federal Bldg." );
        address.setAddressLine3( "1 Federal Drive, Fort Snelling" );
        address.setCity( "St. Paul" );
        address.setState( "MN" );
        address.setZipCode( "55111" );
        address.setZipPlus4( "4050" );
        address.setServiceManagerSignature( "K. L. Anderson" );
        address.setServiceManagerTitle( "Veterans Service Center Manager" );

        return address;
    }


    private ClaimStationAddress getStPeteAddress() {

        ClaimStationAddress     address = new ClaimStationAddress();
        address.setClaimLocationId( 1911 );
        address.setStationId( "317" );
        address.setName( "St. Pete" );
        address.setAddressLine1( "St. Pete Regional Office" );
        address.setAddressLine2( "PO Box 1437" );
        address.setCity( "St. Pete" );
        address.setState( "FL" );
        address.setZipCode( "33731" );
        address.setZipPlus4( "1437" );
        address.setServiceManagerSignature( "S. L. Smith" );
        address.setServiceManagerTitle( "Veterans Service Center Manager" );

        return address;
    }




    /**
     *      Populate the repository based on responses from the web services.
     *
     *      @param repo
     *      @param claimId - claim id could be a file number, participant id, etc.  I recommend using file number.
     */
    public void populateViaClaimResponses( final ApplicationContext  context,
                                           final RbpsRepository     repo,
                                           final String             claimId ) {

        DependencyDecisionByAwardProducer producer  =
                                         (DependencyDecisionByAwardProducer) context.getBean( "dependencyDecisionByAwardProducer" );

        String      basePath                = String.format( "gov/va/vba/rbps/services/populators/%s", claimId );
        String      userInfoPath            = addFilePart( basePath, "userInfo" );
        String      dependencyDecisionPath  = addFilePart( basePath, "dependencyDecision" );
        String      ratingDataPath          = addFilePart( basePath, "ratingComparison" );
        String      awardStatePath          = addFilePart( basePath, "findAwardState" );

        populateUserInfo( repo, userInfoPath );
        populateNewSchoolChild( repo, dependencyDecisionPath );
        populateRatingDates( repo, ratingDataPath );
        populateAwardState( repo, awardStatePath );

        setFakeDependencyDecisionResponseFromXml( dependencyDecisionPath,
                                                  producer,
                                                  repo.getVeteran().getClaim().getClaimId() );

//        logUtils.log( logger, new IndentedXomToString().toString( repo.getVeteran(), 0 ) );
    }


    private String addFilePart( final String     basePath,
                                final String     responseType ) {

        return String.format( "%s/%s.response.xml", basePath, responseType );
    }

    private void setFakeDependencyDecisionResponseFromXml( final String                              dependencyDecisionPath,
                                                           final DependencyDecisionByAwardProducer   producer,
                                                           final long claimId ) {

        FindDependencyDecisionByAwardResponse   response  = getDependencyDecisionResponseFromXml( dependencyDecisionPath );
        producer.addFakeResponse( claimId, response );
    }
    
    public void populateFiduciaryInfo( final RbpsRepository repo, final String xmlPath) {
    	
    	FindFiduciaryResponse	response 	= getFindFiduciaryResponseFromXml( xmlPath );
    	FiduciaryPopulator		populator	= new FiduciaryPopulator();
//    	populator.setRepository( repo );
    	populator.populateFiduciary( response, repo );
    	
    }

}
