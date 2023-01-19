/*
 * IndentedXomToStringTest
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.coreframework.util;


import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.xom.Child;
import gov.va.vba.rbps.coreframework.xom.Veteran;
import gov.va.vba.rbps.fixtures.CommonFactory;

import org.junit.Test;


public class IndentedXomToStringTest {

    private static Logger logger = Logger.getLogger(IndentedXomToStringTest.class);

    private LogUtils    logUtils = new LogUtils( logger, true );



    @Test
    public void shouldHandleSimpleVet() {

        Veteran vet     = CommonFactory.adamsVeteran();
        Child   child   = vet.getChildren().get( 0 );

        eraseEndOfATerm( child );

        logUtils.log( new IndentedXomToString().toString( vet, 0 ), null );
    }



    //      Shouldn't cause an exception.
    private void eraseEndOfATerm( final Child child ) {

        child.getPreviousTerms().get(0).setCourseEndDate( null );
    }



    @Test
    public void shouldHandleMissingBirthday() {

        Veteran vet = CommonFactory.adamsVeteran();
        vet.setBirthDate( null );

        System.out.println( "Vet's age/18th/23rd stuff should be found, not cause an exception" );
        logUtils.log( new IndentedXomToString().toString( vet, 0 ), null );
    }
}
