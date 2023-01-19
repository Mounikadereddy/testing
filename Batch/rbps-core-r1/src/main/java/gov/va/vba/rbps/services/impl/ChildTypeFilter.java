/*
 * ChildTypeFilter
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.services.impl;


import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.exception.RbpsRuntimeException;
import gov.va.vba.rbps.coreframework.util.CommonUtils;
import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.coreframework.xom.Child;
import gov.va.vba.rbps.coreframework.xom.ChildType;


/**
 *      Makes sure the child has the right child type: biological or step-child.
 */
public class ChildTypeFilter implements ChildFilter {

    private static Logger logger = Logger.getLogger(ChildTypeFilter.class);

//    private CommonUtils     utils;
    private LogUtils        logUtils    = new LogUtils( logger, true );



    @Override
    public void filter( final RbpsRepository   repository, final Child child ) {

        if ( isValidChildType( child ) ) {

            return;
        }

        String  msg = String.format( "Invalid child type >%s< for %s - it must be either biological or step-child",
                                     child.getUnfilteredChildType(),
                                     CommonUtils.getStandardLogName( child ) );

        logUtils.log( msg, repository );
        throw new RbpsRuntimeException( msg );
    }


    private boolean isValidChildType( final Child child ) {

        if ( child.isOnCurrentAward() ) {

            return true;
        }

        ChildType  childType = child.getChildType();

        return ( childType.equals( ChildType.BIOLOGICAL_CHILD ) || childType.equals( ChildType.STEPCHILD ) );
    }
}
