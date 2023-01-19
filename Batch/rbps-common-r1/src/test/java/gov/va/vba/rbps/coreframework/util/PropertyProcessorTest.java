/*
 * PropertyProcessorTest
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.coreframework.util;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;


public class PropertyProcessorTest {


    @Test
    public void shouldModifyProperty() {

        Foo                     foo         = new Foo( "hey" );
        PropertyRewriter        rewriter    = new PropertyRewriter();
        rewriter.modifyProperty( foo, "bar", new UpperCaser() );


        assertThat( foo.getBar(), is(equalTo( "HEY" ) ) );
    }


    @Test
    public void shouldHandleEmptyProperty() {

        Foo                     foo         = new Foo( null );
        PropertyRewriter        rewriter    = new PropertyRewriter();
        rewriter.modifyProperty( foo, "bar", new UpperCaser() );


        assertThat( foo.getBar(), is(equalTo( null ) ) );
    }


    @Test
    public void shouldModifyProperties() {

        Foo                     foo         = new Foo( "hey", "you" );
        PropertyRewriter        rewriter    = new PropertyRewriter();
        List<String>            properties  = Arrays.asList( "bar", "baz" );

        rewriter.modifyProperties( foo, properties, new UpperCaser() );

        assertThat( foo.getBar(), is(equalTo( "HEY" ) ) );
        assertThat( foo.getBaz(), is(equalTo( "YOU" ) ) );
    }


    @Test
    public void shouldHandleOneNull() {

        Foo                     foo         = new Foo( null, "you" );
        PropertyRewriter        rewriter    = new PropertyRewriter();
        List<String>            properties  = Arrays.asList( "bar", "baz" );

        rewriter.modifyProperties( foo, properties, new UpperCaser() );

        assertThat( foo.getBar(), is(equalTo( null ) ) );
        assertThat( foo.getBaz(), is(equalTo( "YOU" ) ) );
    }


    @Test
    public void shouldHandleTwoNulls() {

        Foo                     foo         = new Foo( null, null );
        PropertyRewriter        rewriter    = new PropertyRewriter();
        List<String>            properties  = Arrays.asList( "bar", "baz" );

        rewriter.modifyProperties( foo, properties, new UpperCaser() );

        assertThat( foo.getBar(), is(equalTo( null ) ) );
        assertThat( foo.getBaz(), is(equalTo( null ) ) );
    }


    @Test
    public void shouldNotChangeNotListedProperty() {

        Foo                     foo         = new Foo( "hey", "you", "joe" );
        PropertyRewriter        rewriter    = new PropertyRewriter();
        List<String>            properties  = Arrays.asList( "bar", "baz" );

        rewriter.modifyProperties( foo, properties, new UpperCaser() );

        assertThat( foo.getBar(), is(equalTo( "HEY" ) ) );
        assertThat( foo.getBaz(), is(equalTo( "YOU" ) ) );
        assertThat( foo.getYoz(), is(equalTo( "joe" ) ) );
    }


    class Foo {

        private String          bar;
        private String          baz;
        private String          yoz;

        public Foo( final String  yuck ) {bar = yuck;}
        public Foo( final String  yuck, final String    urg ) {bar = yuck;baz = urg;}
        public Foo( final String  yuck, final String    urg, final String ack ) {bar = yuck;baz = urg;yoz=ack;}

        public String getBar() {return bar; }
        public void setBar( final String bar ) {this.bar = bar;}
        public String getBaz() {return baz; }
        public void setBaz( final String baz ) {this.baz = baz;}
        public String getYoz() {return yoz; }
        public void setYoz( final String yoz ) {this.yoz = yoz;}
    }


    class UpperCaser implements PropertyProcessor {

        @Override
        public Object modifyProperty( final Object value ) {

            if ( value == null ) {

                return null;
            }

            if ( ! (value instanceof String) ) {

                return value;
            }

            String      stringValue = (String) value;

            return stringValue.toUpperCase();
        }
    }
}
