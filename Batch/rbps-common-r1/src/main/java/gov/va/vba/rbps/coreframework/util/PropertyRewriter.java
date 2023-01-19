/*
 * PropertyRewriter
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.coreframework.util;

import java.util.List;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;


/**
 *      Given a property name and a property processor,
 *      get the value for the property and call the processor
 *      to get a new value to set on the property.
 *
 *      The property must be a direct property, no nesting allowed.
 */
public class PropertyRewriter {


    public void modifyProperties( final Object              object,
                                  final List<String>        propertyNames,
                                  final PropertyProcessor   processor ) {

        for ( String  name : propertyNames ) {

            modifyProperty( object, name, processor );
        }
    }


    public void modifyProperty( final Object                object,
                                final String                propertyName,
                                final PropertyProcessor     processor ) {

        BeanWrapper     wrapper     = new BeanWrapperImpl( object );
        Object          value       = wrapper.getPropertyValue( propertyName );
        Object          newValue    = processor.modifyProperty( value );

        wrapper.setPropertyValue( propertyName, newValue );
    }
}
