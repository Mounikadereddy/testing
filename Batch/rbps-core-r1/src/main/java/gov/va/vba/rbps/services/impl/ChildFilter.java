/*
 * ChildFilter
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.services.impl;

import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.xom.Child;


/**
 *      Validates the child for  missing data.
 */
public interface ChildFilter {

    void filter( final RbpsRepository   repository, final Child child );
}
