/*
 * FindRatingDataWSHandlerTest.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.services.ws.client.handler.share;


import gov.va.vba.rbps.RbpsAbstractTest;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.xom.Child;
import gov.va.vba.rbps.fixtures.CommonFactory;
import gov.va.vba.rbps.services.ws.client.mapping.vonapp.claimlabel.UpdateBenefitClaimDependentsResponse;
import gov.va.vba.rbps.services.ws.client.util.SoapFaultPrinter;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.junit.Before;
import org.junit.Test;

/**
 *      Test case for FindRatingDataWSHandler class
 */
public class UpdateBenefitClaimDependentsWSHandlerTest extends RbpsAbstractTest {

    private UpdateBenefitClaimDependentsWSHandler updateBenefitClaimDependentsWSHandler;
    private RbpsRepository     repository;

    @Override
    @Before
    public void setup() {

        super.setup();
        updateBenefitClaimDependentsWSHandler = (UpdateBenefitClaimDependentsWSHandler) getBean( "updateBenefitClaimDependentsWSHandler" );

        repository = (RbpsRepository) getBean( "repository" );
    }


    /**
     * Test case for updateBenefitClaimDependents method
     * @throws Throwable
     */
    @Test
    public void shouldCallUpdateBenefitClaimDependents() throws Throwable {

        try {
            repository.setVeteran( CommonFactory.jeffersonNoMiddleNameVeteran() );
            repository.getChildren().clear();
            CommonFactory.addChildren( repository.getVeteran(), 2 );
            clearAwardFromXomKids( repository );
            repository.getVeteran().getCurrentMarriage().getMarriedTo().setAward( null );
            repository.getVeteran().setCorpParticipantId( 809 );
            repository.getVeteran().setFileNumber( "999111230" );

            UpdateBenefitClaimDependentsResponse response = updateBenefitClaimDependentsWSHandler.updateDependents( repository );
            System.out.println( ToStringBuilder.reflectionToString( response.getReturn() ) );

//          assertEquals("GUIE50004", response.getReturn().get );
//          assertEquals("Benefit Claim not found on Corporate Database.", response.getReturn().getReturnMessage());
        }
        catch ( Throwable ex ) {

            System.out.println( "soap fault: " + new SoapFaultPrinter().printSoapFaultInfo( ex ) );

            throw ex;
        }
    }


    /**
     * Test case for updateBenefitClaimDependents method
     * @throws Throwable
     */
    @Test
    public void shouldCallUpdateBenefitClaimDependentsWithNoSpouse() throws Throwable {

        try {
            repository.setVeteran( CommonFactory.georgeVeteran() );
            repository.getChildren().clear();
            CommonFactory.addChildren( repository.getVeteran(), 1 );
            clearAwardFromXomKids( repository );
            repository.getVeteran().setCorpParticipantId( 809 );
            repository.getVeteran().setFileNumber( "999111230" );

            UpdateBenefitClaimDependentsResponse response = updateBenefitClaimDependentsWSHandler.updateDependents( repository );
            System.out.println( ToStringBuilder.reflectionToString( response.getReturn() ) );

//          assertEquals("GUIE50004", response.getReturn().get );
//          assertEquals("Benefit Claim not found on Corporate Database.", response.getReturn().getReturnMessage());
        }
        catch ( Throwable ex ) {

            System.out.println( "soap fault: " + new SoapFaultPrinter().printSoapFaultInfo( ex ) );

            throw ex;
        }
    }


    private void clearAwardFromXomKids( final RbpsRepository repository ) {

        for ( Child child : repository.getVeteran().getChildren() ) {

//            Award award = CommonFactory.generateRandomAward( child );
//            award.setEventDate( new Date() );
//
//            if ( CommonFactory.getRandomBoolean() ) {
//
//                child.setAward( award );
//            }
//            else {
//
//                child.setMinorSchoolChildAward( award );
//            }
            child.setAward( null );
            child.setMinorSchoolChildAward( null );
        }

    }
}
