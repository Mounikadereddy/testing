/*
 * NullSafeGetterTest
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.coreframework.util;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;


public class NullSafeGetterTest {


    @Test
    public void shouldReturnRightPathToFinalObjectWithMultipleDots() {

        NullSafeGetter  grabber = new NullSafeGetter();

        assertThat( grabber.getPathToFinalObject( "a.b.c" ), is(equalTo( "a.b" ) ) );
    }


    @Test
    public void shouldReturnRightPathToFinalObjectWithNoDots() {

        NullSafeGetter  grabber = new NullSafeGetter();

        assertThat( grabber.getPathToFinalObject( "a" ), is(nullValue() ) );
    }


    @Test
    public void shouldReturnRightAttributeNameWithNoDots() {

        NullSafeGetter  grabber = new NullSafeGetter();

        assertThat( grabber.getFinalAttributeName( "a" ), is(equalTo( "a" )) );
    }


    @Test
    public void shouldReturnRightAttributeNameWithMultipleDots() {

        NullSafeGetter  grabber = new NullSafeGetter();

        assertThat( grabber.getFinalAttributeName( "a.b.c" ), is(equalTo( "c" )) );
    }


    @Test
    public void shouldHandleSettingListPropertySafely() {

        A a = new A();
        B b = new B();
        a.b = b;

        NullSafeGetter  grabber = new NullSafeGetter();
        grabber.setAttribute( a, "b.names[0]", "joe" );

        assertThat( (String) grabber.getAttribute( a, "b.names[0]" ), is(equalTo( "joe" ) ) );
    }


    @Test
    public void shouldGetPropertySafely() {

        A a = new A();
        B b = new B();
        C c = new C();
        a.b = b;
        b.c = c;
        String      testName = "joe";
        c.name = testName;
        NullSafeGetter  grabber = new NullSafeGetter();

        assertThat( (String) grabber.getAttribute( a, "b.c.name" ), is(equalTo( testName ) ) );
    }


    @Test
    public void shouldHandleNullPropertySafely() {

        A a = new A();
        NullSafeGetter  grabber = new NullSafeGetter();

        assertThat( grabber.getAttribute( a, "b.c.name" ), is(nullValue() ) );
    }


    @Test
    public void shouldHandleListPropertySafely() {

        A a = new A();
        B b = new B();
        a.b = b;

        NullSafeGetter  grabber = new NullSafeGetter();

        assertThat( (String) grabber.getAttribute( a, "b.names[0]" ), is(equalTo( "tom" ) ) );
    }


    class C {

        String   name;

        public String getName() {

            return name;
        }
    }
    class B {
        C  c;
        List<String>        names = Arrays.asList( "tom", "karma", "keya" );

        public C getC() {
            return c;
        }

        public List<String> getNames() {
            return names;
        }
    }
    class A {

        B   b;

        public B getB() {
            return b;
        }
    }
}
