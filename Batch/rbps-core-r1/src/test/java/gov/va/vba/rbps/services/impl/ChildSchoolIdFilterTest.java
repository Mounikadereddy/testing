/*
 * ChildSchoolIdFilterTest
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.services.impl;


import gov.va.vba.rbps.RbpsAbstractTest;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.exception.RbpsRuntimeException;
import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.coreframework.xom.Child;
import gov.va.vba.rbps.coreframework.xom.Education;
import gov.va.vba.rbps.coreframework.xom.EducationLevelType;
import gov.va.vba.rbps.coreframework.xom.FormType;
import gov.va.vba.rbps.fixtures.CommonFactory;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;


public class ChildSchoolIdFilterTest extends RbpsAbstractTest {


    private ChildSchoolIdFilter     childFilter =       new ChildSchoolIdFilter();
    private RbpsRepository          repository  =       new RbpsRepository();


    @Override
    @Before
    public void setup() {

        LogUtils.setGlobalLogit( false );
        super.setup();
    }


    @Test
    public void shouldFilterChildWithCurrentPrevSchollInd() {

        Child  child    =   new Child();

        child =  addChild( true, true);
        addCurrentTerm( child, 5 );
        addPreviousTerm( child, 3);

        childFilter.filter( repository, child );
    }


   @Test( expected = RbpsRuntimeException.class )
    public void shouldFilterChildWithCurrentWOPrevSchollInd() {

        Child  child    =   new Child();

        child =  addChild( true, true);
        addCurrentTerm( child, 5 );
        addPreviousTerm( child, 0);

        childFilter.filter( repository, child );
    }


   @Test( expected = RbpsRuntimeException.class )
    public void shouldFilterChildWithWOCurrentPrevSchollInd() {

        Child  child    =   new Child();

        child =  addChild( true, true);
        addCurrentTerm( child, 0 );
        addPreviousTerm( child, 5);

        childFilter.filter( repository, child );
    }


    private void addCurrentTerm( final Child child, final long schoolId ) {

        child.setCurrentTerm( CommonFactory.getRandomEducation() );

        child.getCurrentTerm().setEducationLevelType( EducationLevelType.POST_SECONDARY );
        child.getCurrentTerm().getSchool().setEduInstnPtcpntId( schoolId );
    }


    private void addPreviousTerm( final Child child, final long schoolId ) {

        Education   edu         =   CommonFactory.getRandomEducation();
        List<Education> eduList =   new ArrayList<Education>();

        edu.getSchool().setEduInstnPtcpntId( schoolId );
        edu.setEducationLevelType( EducationLevelType.POST_SECONDARY );
        eduList.add( edu );

        child.setPreviousTerms( eduList );
    }

    private Child addChild(final boolean add674, final boolean isSchoolChild) {

        repository.setVeteran( CommonFactory.adamsVeteran() );

        Child       child   =   CommonFactory.getRandomChild( repository.getVeteran() );

        if ( add674 ) {

             child.getForms().add(FormType.FORM_21_674);
        }

        if ( isSchoolChild ) {

            child.setSchoolChild( isSchoolChild );
        }

         return child;
    }
}
