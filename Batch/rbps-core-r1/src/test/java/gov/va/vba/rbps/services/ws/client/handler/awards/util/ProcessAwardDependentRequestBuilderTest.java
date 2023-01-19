/*
 * ProcessAwardDependentRequestFilterTest
 *
 * Copyright 2012 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.services.ws.client.handler.awards.util;



import gov.va.vba.rbps.RbpsAbstractTest;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.coreframework.util.RbpsUtil;
import gov.va.vba.rbps.coreframework.util.SimpleDateUtils;
import gov.va.vba.rbps.fixtures.CommonFactory;
import gov.va.vba.rbps.services.ws.client.mapping.awards.amend.AmendAwardDependencyInputVO;
import gov.va.vba.rbps.services.ws.client.mapping.awards.amend.AmendDependencyDecisionVO;
import gov.va.vba.rbps.services.ws.client.mapping.awards.amend.ProcessAwardDependent;

import org.junit.Before;
import org.junit.Test;




public class ProcessAwardDependentRequestBuilderTest extends RbpsAbstractTest {

    private CommonFactory                           commonFactory   = new CommonFactory();
    private SimpleDateUtils                         dateUtils       = new SimpleDateUtils();
    private ProcessAwardDependentRequestBuilder     builder         = new ProcessAwardDependentRequestBuilder();
    private RbpsRepository      repo ;


    @Override
    @Before
    public void setup() {

        super.setup();

        LogUtils.setGlobalLogit( true );

        RbpsRepository      repo = new RbpsRepository();
//        builder.setRepository( repo );
    }


    //      Shouldn't throw an exception.
    //      Should log about no request.
    @Test
    public void shouldHandleNullRequest() {

        builder.logRequest( null, repo );
    }


    //      Shouldn't throw an exception.
    //      Should log about no request.
    @Test
    public void shouldHandleEmptyRequest() {

        builder.logRequest( new ProcessAwardDependent(), repo );
    }


    //      Shouldn't throw an exception.
    //      Should log about no request.
    @Test
    public void shouldHandleEmptyDependencyInput() {

        ProcessAwardDependent request = new ProcessAwardDependent();
        request.setAmendAwardDependencyInput( new AmendAwardDependencyInputVO() );

        builder.logRequest( request, repo );
    }


    //      Shouldn't throw an exception.
    //      Should log about no request.
    @Test
    public void shouldHandleEmptyDependentList() {

        ProcessAwardDependent           request = new ProcessAwardDependent();
        AmendAwardDependencyInputVO     input   = new AmendAwardDependencyInputVO();

        input.getDependencyList().clear();
        request.setAmendAwardDependencyInput( input );

        builder.logRequest( request, repo );
    }


    //      Shouldn't throw an exception.
    //      Should log about no request.
    @Test
    public void shouldHandleNonEmptyDependentList() {

        ProcessAwardDependent           request = new ProcessAwardDependent();
        AmendAwardDependencyInputVO     input   = new AmendAwardDependencyInputVO();

        input.getDependencyList().add( createSchattbProspect() );
        request.setAmendAwardDependencyInput( input );

        builder.logRequest( request, repo );
    }


    public AmendDependencyDecisionVO createSchattbProspect() {

        AmendDependencyDecisionVO       prospect    = new AmendDependencyDecisionVO();

        prospect.setBirthdayDate( RbpsUtil.dateToXMLGregorianCalendar( commonFactory.createDay( 1993, 04, 15 ) ) );
        prospect.setEventDate( RbpsUtil.dateToXMLGregorianCalendar( commonFactory.createDay( 2011, 12, 20 ) ) );
        prospect.setDecisionEndDate( RbpsUtil.dateToXMLGregorianCalendar( commonFactory.createDay( 2016, 06, 02 ) ) );
        prospect.setFirstName( "SALLY" );
        prospect.setLastName( "ADAMS" );
        prospect.setOmnibusFlag( "Y" );
        prospect.setPersonID( 9100780270L );
        prospect.setSocialSecurityNumber( "999817610" );
        prospect.setDependencyDecisionType( "SCHATTB" );
        prospect.setDependencyStatusType( "SCHCHD" );

        return prospect;
    }
}
