/*
 * VirtualVaWebServiceCallerTest.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.lettergeneration.virtualva;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;



@Ignore
public class VirtualVaWebServiceCallerTest {

	private VirtualVaWebServiceCaller              virtualVaWebServiceCaller;
	private ClassPathXmlApplicationContext         applicationContext;


	@Before
	public void setUp() {

		applicationContext        = new ClassPathXmlApplicationContext( "rbps-lettergen-spring-config.xml" );
		virtualVaWebServiceCaller = (VirtualVaWebServiceCaller) applicationContext.getBean( "virtualVaWebServiceCaller" );
	}


	@After
    public void tearDown() {

        applicationContext.destroy();

        applicationContext          = null;
        virtualVaWebServiceCaller   = null;
    }


    @Test
    public void shouldCallVirtualVaWebService() {

		@SuppressWarnings( "unused" )
        String result = virtualVaWebServiceCaller.callVirtualVaWebService();
//        System.out.println("Virtual VA Web Service response : " + result );
	}
}
