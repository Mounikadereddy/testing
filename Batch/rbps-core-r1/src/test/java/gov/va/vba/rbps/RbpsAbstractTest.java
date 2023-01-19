/*
 * RbpsAbstractTest.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps;


import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 *      RBPS framework Abstract test case. All test cases should extend this class
 *      and share commonalities such as application context, logger, etc...
 */
public abstract class RbpsAbstractTest {

    protected final Log logger = LogFactory.getLog(this.getClass());

    private ApplicationContext context;


    public void setup() {

        new QuietTest().setup( false );

        try {
            context = new ClassPathXmlApplicationContext(new String[] {
                    "rbps-core-spring-config.xml",
                    "rbps-ws-client-spring-config.xml",
                    "rbps-rule-session-spring-config.xml" });
        }
        catch (Throwable ex) {

            ex.printStackTrace();
            TestCase.fail();
        }
    }


    public void tearDown() throws Exception {

        if ( context == null ) {
            return;
        }

        context = null;
    }


    public Object getBean( final String  beanName ) {

        return context.getBean( beanName );
    }


    public ApplicationContext getContext() {

        return context;
    }
}
