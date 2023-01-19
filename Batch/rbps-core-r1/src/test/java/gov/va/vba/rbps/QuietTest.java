/*
 * QuietTest
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps;


import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.coreframework.util.RbpsConstants;

import org.apache.commons.lang.builder.ToStringBuilder;


/**
 *      RBPS framework Abstract test case. All test cases should extend this class
 *      and share commonalities such as application context, logger, etc...
 */
public class QuietTest  {


    public void setup( final boolean  logit ) {

        LogUtils.setGlobalLogit( logit );
        ToStringBuilder.setDefaultStyle( RbpsConstants.RBPS_TO_STRING_STYLE );
    }
}
