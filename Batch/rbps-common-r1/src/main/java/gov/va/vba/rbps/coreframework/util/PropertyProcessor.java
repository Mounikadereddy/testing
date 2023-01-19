/*
 * PropertyProcessor
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.coreframework.util;


/**
 *      Given the value of a property, modify it in some way
 *      and return the processed value.
 */
public interface PropertyProcessor {


    Object modifyProperty( Object   value );
}
