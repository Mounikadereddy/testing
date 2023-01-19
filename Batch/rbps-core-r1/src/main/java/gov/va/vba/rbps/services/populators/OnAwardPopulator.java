/*
 * RatingDataPopulator.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.services.populators;


import gov.va.vba.rbps.coreframework.dto.CorporateDependent;
import gov.va.vba.rbps.coreframework.dto.CorporateDependentId;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.xom.Child;
import gov.va.vba.rbps.coreframework.xom.Marriage;
import gov.va.vba.rbps.coreframework.xom.Spouse;

import org.springframework.util.CollectionUtils;


/**
 *      Populate the onAward and corp participant id for the xom dependents.
 */
public class OnAwardPopulator {
	
	// static class now
	private OnAwardPopulator(){}




    public static final void setOnCurrentAwardForXomDependent( final RbpsRepository   repo ) {

        setOnCurrentAwardForXomSpouse( repo );
        setOnCurrentAwardForXomChildren( repo );
    }


    public static final void setOnCurrentAwardForXomSpouse( final RbpsRepository   repo ) {

        Marriage    marriage = repo.getVeteran().getCurrentMarriage();
        if ( marriage == null ) {

            return;
        }

        if ( marriage.getMarriedTo() == null ) {

            return;
        }

        if ( repo.getSpouse() == null ) {

            return;
        }

        final Spouse                spouse              = marriage.getMarriedTo();
        final CorporateDependentId  corporateSpouseId   = repo.getSpouse().getId();
        final CorporateDependentId  xomSpouseId         = new CorporateDependentId( spouse );

        if (corporateSpouseId.equals(xomSpouseId)) {

            spouse.setOnCurrentAward( repo.getSpouse().isOnAward() );
            spouse.setCorpParticipantId( corporateSpouseId.getParticipantId() );
        }
    }


     public static final void setOnCurrentAwardForXomChildren( final RbpsRepository   repo ) {

         if ( CollectionUtils.isEmpty( repo.getChildren() ) ) {

             return;
         }

         for ( final Child child : repo.getVeteran().getChildren() ) {

             setOnCurrentAwardForXomChild( repo, child );
         }
    }


    public static final void setOnCurrentAwardForXomChild(  final RbpsRepository   repo, final Child xomChild ) {

        final CorporateDependentId  xomChildId  =   new CorporateDependentId( xomChild );

        for ( final CorporateDependent child   :   repo.getChildren() ) {

            if ( xomChildId.equals( child.getId() ) ) {

                xomChild.setOnCurrentAward( child.isOnAward() );
                xomChild.setCorpParticipantId( child.getParticipantId() );
            }
        }
    }
}
