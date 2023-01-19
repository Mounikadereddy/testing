/*
 * ReasonTranslation.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.lettergeneration;


import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.util.LogUtils;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;


public class ReasonTranslation {

    private static Logger logger = Logger.getLogger(ReasonTranslation.class);

    private LogUtils    logUtils            = new LogUtils( logger, true );

    private String      type                    = "bogus";
    private String      description             = "bogus";
    private String      addTranslation          = "bogus";
    private String      removeTranslation       = "bogus";



    public ReasonTranslation( final String    type,
                              final String    description,
                              final String    translation ) {

        this( type, description, translation, translation );
    }


    public ReasonTranslation( final String    type,
                              final String    description,
                              final String    addTranslation,
                              final String    removeTranslation ) {

        this.type               = type;
        this.description        = description;
        this.addTranslation     = addTranslation;
        this.removeTranslation  = removeTranslation;
    }



    public String getReason( final AwardReason          awardReason,
                             final ApprovalType         approvalType,
                             final String               dependentName ) {

        String  protectedName = dependentName;
        if ( StringUtils.isBlank( protectedName) ) {

            protectedName = "Dependent";
        }
        protectedName = WordUtils.capitalize( protectedName.toLowerCase() );

//        logUtils.log( "\n\nAwardReason: " + awardReason );
        if ( approvalType == ApprovalType.GRANT ) {

//            logUtils.log( String.format( "%s/%s: %s formatting >%s<",
//                                         protectedName,
//                                         getType(),
//                                         approvalType,
//                                         getAddTranslation() ) );
            return String.format( getAddTranslation(), protectedName );
        }

//        logUtils.log( String.format( "%s/%s: %s formatting >%s<",
//                                     protectedName,
//                                     getType(),
//                                     approvalType,
//                                     getRemoveTranslation() ) );
        return String.format( getRemoveTranslation(), protectedName );
    }


    public String getRemoveTranslation() {

        if ( StringUtils.isBlank( removeTranslation ) ) {

            return addTranslation;
        }

        return removeTranslation;
    }


    public boolean requiresDependent() {

        return getRemoveTranslation() != null && getRemoveTranslation().contains( "%s" );
    }


    public String getType() {

        return type;
    }

    public String getDescription() {

        return description;
    }

    public String getAddTranslation() {

        return addTranslation;
    }
}
