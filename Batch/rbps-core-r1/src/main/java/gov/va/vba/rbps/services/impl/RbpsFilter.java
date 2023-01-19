/*
 * RbpsFilter
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.services.impl;


import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.util.NullSafeGetter;
import gov.va.vba.rbps.coreframework.xom.Child;
import gov.va.vba.rbps.coreframework.xom.Spouse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class RbpsFilter {

    private static Logger logger = Logger.getLogger(RbpsFilter.class);

    private List<ChildFilter>       childFilters = new ArrayList<ChildFilter>();


    public RbpsFilter() {
    	
    	childFilters = Arrays.asList( new ChildTypeFilter(), new NewSchoolChildFilter() );
    	
//        childFilters = Arrays.asList( new ChildSchoolIdFilter(),
//                                      new ChildTypeFilter(),
//                                      new NewSchoolChildFilter(),
//                                      new NewStepChildNotAlreadyOnAwardFilter() );
    }


    public void filter( final RbpsRepository       repository ) {

        filterDependents( repository );
    }


    private void filterDependents(final RbpsRepository repo) {

        filterChildren( repo );
//        filterSpouse( repo );
    }


//    private void filterSpouse( final RbpsRepository repo ) {
//
//        Spouse  spouse = (Spouse) NullSafeGetter.getAttribute( repo, "veteran.currentMarriage.marriedTo" );
//
//
//    }

    private void filterChildren(final RbpsRepository repository) {

        List<Child> children    = repository.getVeteran().getChildren();

        for ( Child child       :       children ) {

            for ( ChildFilter   filter : childFilters ) {

                filter.filter( repository, child );
            }
        }
    }
}
