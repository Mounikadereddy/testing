/*
 * FindDependentsWSHandlerTest.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.services.ws.client.handler.share;


import gov.va.vba.rbps.RbpsAbstractTest;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.fixtures.CommonFactory;
import gov.va.vba.rbps.services.ws.client.mapping.share.familytree.FindDependentsResponse;
import gov.va.vba.rbps.services.ws.client.mapping.share.familytree.Shrinq3Person;
import gov.va.vba.rbps.services.ws.client.util.SoapFaultPrinter;

import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;


/**
 *      Test case for FindDependentsWSHandler class
 */
@RunWith( Parameterized.class )
public class FindDependentsWSHandlerTest extends RbpsAbstractTest {

    private FindDependentsWSHandler    findDependentsWSHandler;
    private RbpsRepository             repository;

    private String                     fileNumber;


    public FindDependentsWSHandlerTest( final String fileNumber ) {

        this.fileNumber = fileNumber;
    }


    @Override
    @Before
    public void setup() {

        super.setup();
        findDependentsWSHandler = (FindDependentsWSHandler) getBean( "findDependentsWSHandler" );

        repository = (RbpsRepository) getBean( "repository" );
        repository.setVeteran( CommonFactory.georgeVeteran() );
        repository.getVeteran().setCorpParticipantId( 30843464 );
    }


    /**
     * Test case for findDependents method
     * @throws Throwable
     */
    @Test
    public void testfindDependents() throws Throwable {

        try {
            repository.getVeteran().setFileNumber( fileNumber );
            FindDependentsResponse response = findDependentsWSHandler.findDependents( repository );

            System.out.println( String.format( "%s: number of dependents: %s",
                                               fileNumber,
                                               response.getReturn().getNumberOfRecords() ) );

            for ( Shrinq3Person person : response.getReturn().getPersons() ) {
                System.out.println( ToStringBuilder.reflectionToString( person ).replace( ",", "\n\t" ) );
            }
        }
        catch ( Throwable ex ) {

            System.out.println( new SoapFaultPrinter().printSoapFaultInfo( ex ) );

            throw ex;
        }

//      assertEquals("GUIE50004", response.getReturn().get );
//      assertEquals("Benefit Claim not found on Corporate Database.", response.getReturn().getReturnMessage());
    }


    @Parameters
    public static Collection<Object[]> fileNumbers() {

        return Arrays.asList( new Object[][] {
                { "999806541" },
        });
//        return CommonFactory.testFileNumbers();
    }
}
