/*
 * FindFlashesWSHandlerTest.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.services.ws.client.handler.share;


import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.QuietTest;
import gov.va.vba.rbps.RbpsAbstractTest;
import gov.va.vba.rbps.claimprocessor.impl.AttorneyFeeAgreementType;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.coreframework.util.RbpsConstants;
import gov.va.vba.rbps.coreframework.xom.Veteran;
import gov.va.vba.rbps.fixtures.CommonFactory;
import gov.va.vba.rbps.services.populators.AttorneyFeeAgreementPopulator;
import gov.va.vba.rbps.services.ws.client.mapping.share.familytree.FindFlashesResponse;
import gov.va.vba.rbps.services.ws.client.mapping.share.familytree.Flash;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;


/**
 *      Test case for FindFlashesWSHandler class
 */
@RunWith( Parameterized.class )
public class FindFlashesWSHandlerTest extends RbpsAbstractTest {

    private static Logger logger = Logger.getLogger(FindFlashesWSHandlerTest.class);

    private LogUtils                    logUtils                = new LogUtils( logger, true );

    private FindFlashesWSHandler        findFlashesWSHandler;
    private RbpsRepository              repo;

    private String                      fileNumber;


    public FindFlashesWSHandlerTest( final String fileNumber ) {

        this.fileNumber = fileNumber;
    }


    @Override
    @Before
    public void setup() {

        super.setup();

        new QuietTest().setup( true );

        findFlashesWSHandler    = (FindFlashesWSHandler) getBean( "findFlashesWSHandler" );
        repo                    = (RbpsRepository) getBean( "repository" );
    }


    @Test
    public void testfindFlashes() {

        RbpsRepository repo = (RbpsRepository) getBean( "repository" );

        repo.setVeteran( CommonFactory.georgeVeteran() );
        repo.getVeteran().setFileNumber( fileNumber );

//        logUtils.log( "Using file number: " + fileNumber );

        FindFlashesResponse            response = findFlashesWSHandler.call( repo );
        AttorneyFeeAgreementPopulator  populator = new AttorneyFeeAgreementPopulator();
//        populator.setRepository( repo );

        populator.populateFromFlashes( response, repo );

//        logUtils.log( "Have attorney fee agreement: " + repo.getVeteran().getAwardStatus().hasAttorneyFeeAgreement() );
//        System.out.println( "flash response: " + new CommonUtils().stringBuilder( response.getReturn() ) );
//        System.out.println( "flash response: " + new CommonUtils().stringBuilder( response.getReturn().getFlashes() ) );
    }


    @Parameters
    public static Collection<Object[]> fileNumbers() {

        return CommonFactory.testFileNumbers();
    }


    private void savedCode() {

        FindFlashesResponse response = findFlashesWSHandler.call( repo );

        //      List<String>  indicators = Arrays.asList( "Attorney Fee",
        //      "Converted - Potential Attorney Fee",
        //      "Potential Attorney Fee",
        //      "Private Attorney - Fees Payable" );
        List<String> lower = new ArrayList<String>();

        Arrays.asList(  AttorneyFeeAgreementType.values() );
        for ( AttorneyFeeAgreementType indicator : AttorneyFeeAgreementType.values() ) {

            lower.add( indicator.getValue().toLowerCase() );
        }
//        System.out.println( "lower: " + lower );

        boolean    hasAttorneyFeeAgreement = false;
        for ( Flash flash : response.getReturn().getFlashes() ) {

        System.out.println( ToStringBuilder.reflectionToString( flash, RbpsConstants.RBPS_TO_STRING_STYLE ) );

//        for ( String indicator : lower ) {
//            if ( flash.getFlashName().toLowerCase().contains( indicator ) ) {
//
//                hasAttorneyFeeAgreement = true;
//                System.out.println( "****************** found it IN LIST *****************" );
//            }
//        }

        if ( lower.contains( flash.getFlashName().toLowerCase().trim() ) ) {
            hasAttorneyFeeAgreement = true;

            System.out.println( "****************** found it *****************" );
        }

        //if ( flash.getFlashName().toLowerCase().contains( "attorney" ) ) {
        ////hasAttorneyFeeAgreement = true;
        //
        //System.out.println( "******************  ATTORNEY *****************" );
        //}
        }

        Veteran    veteran = ((RbpsRepository) getBean( "repository" )).getVeteran();
        System.out.println( String.format( ">%s, %s< has an attorney fee agreement: >%s<",
                                           veteran.getLastName(),
                                           veteran.getFirstName(),
                                           hasAttorneyFeeAgreement ));

        //assertEquals("GUIE50004", response.getReturn().get );
        //assertEquals("Benefit Claim not found on Corporate Database.", response.getReturn().getReturnMessage());
    }
}
