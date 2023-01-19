/*
 * ChildSchoolIdFilter
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.services.impl;


//import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.exception.RbpsRuntimeException;
//import gov.va.vba.rbps.coreframework.util.CommonUtils;
//import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.coreframework.xom.Child;
import gov.va.vba.rbps.coreframework.xom.Education;
import gov.va.vba.rbps.coreframework.xom.EducationLevelType;
import gov.va.vba.rbps.coreframework.xom.FormType;

import java.util.List;

import org.springframework.util.CollectionUtils;


/**
 *      If a child has a school w/o a valid weams id, throw an exception.
 */
public class ChildSchoolIdFilter implements ChildFilter {


//    private static Logger logger = Logger.getLogger(ChildSchoolIdFilter.class);


//    private CommonUtils     utils       = new CommonUtils();
//    private LogUtils        logUtils    = new LogUtils( logger, true );



    @Override
    public void filter( final RbpsRepository   repository, final Child child ) {

        if ( ! child.isSchoolChild() ) {

//            logUtils.log( "not a School Child: "  + utils.getStandardLogName( child ));
            return;
        }

        if ( ! has674( child ) ) {

//            logUtils.log( "not a 674 Child: "  + utils.getStandardLogName( child ));
            return;
        }

        filterCurrentTerm( child );
        filterPreviousTerms( child );
    }


    private void filterCurrentTerm( final Child child ) {

        Education currentTerm   = child.getCurrentTerm();

        validateSchool( currentTerm, child, "current" );
    }


    private void filterPreviousTerms( final Child child ) {

       List<Education> previousTerms    = child.getPreviousTerms();

       if (CollectionUtils.isEmpty( previousTerms )) {

//           logUtils.log( "No Previous Terms for the Child: "  + utils.getStandardLogName( child ));
           return;
       }
//       logUtils.log( String.format( "Number of Previous Terms >%d< for the Child: ", previousTerms.size() )  + utils.getStandardLogName( child ));

       int counter = 0;
       for ( Education previousTerm     :   previousTerms ) {

           validateSchool( previousTerm, child, "previous " + counter++  );
       }
    }


    private void validateSchool( final Education term, final Child child, final String termIndicator ) {


        if ( term == null) {

//            logUtils.log( String.format( "%s is null for the Child: ", termIndicator )  + utils.getStandardLogName( child ) );
            return;
        }

        if ( term.getSchool() == null) {

//            logUtils.log( "No school info for the Child: " + utils.getStandardLogName( child ));
            return;
        }

        if ( ! term.getEducationLevelType().equals( EducationLevelType.POST_SECONDARY ) ) {

//            logUtils.log( String.format( "%s School not post-secondary/college for the Child: ", termIndicator ) + utils.getStandardLogName( child ) );
            return;
        }

        if (noValidWeamsIdForEducationalInstitution( term ) ) {

//            logUtils.log( String.format( "%s No valid weams id for the Child: ", termIndicator ) + utils.getStandardLogName( child ) );
            throw new RbpsRuntimeException( exceptionMessage( child, termIndicator ) );
        }
    }


    public boolean noValidWeamsIdForEducationalInstitution( final Education term ) {

        long weamsId = term.getSchool().getEduInstnPtcpntId();

//        logUtils.log( String.format( "weams id %d: ", weamsId ) );

        return weamsId == 0;
    }


    private boolean has674(final Child child) {

       return  ( child.getForms().contains( FormType.FORM_21_674 ) );
    }


    private String exceptionMessage( final Child child, final String termIndicator ) {

      return String.format( "Auto Dependency Processing Reject Reason: No Education Institution Id present for %s term for the child %s, %s: ",
                            termIndicator,
                            child.getLastName(),
                            child.getFirstName() );
    }
}
