/*
 * ClaimIdPopulator.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.services.populators;


import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.exception.RbpsRuntimeException;
import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.coreframework.util.RbpsUtil;
import gov.va.vba.rbps.coreframework.util.SimpleDateUtils;
import gov.va.vba.rbps.coreframework.xom.ClaimLabelType;
import gov.va.vba.rbps.services.ws.client.mapping.vonapp.claimlabel.BenefitClaimRecord;
import gov.va.vba.rbps.services.ws.client.mapping.vonapp.claimlabel.ShrinqbcSelection;
import gov.va.vba.rbps.services.ws.client.mapping.vonapp.userinformation.ClaimInfoDTO;
import gov.va.vba.rbps.services.ws.client.mapping.vonapp.userinformation.GetClaimInfoResponse;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.util.CollectionUtils;


public class ClaimIdPopulator {

    private static Logger logger = Logger.getLogger(ClaimIdPopulator.class);

    private static final String PENDING_STATUS	= 	"PEND";
    private static final String CANCEL_STATUS 	=  	"CAN";
    private static final String CLEARED_STATUS 	=  	"CLR";
    
    private static final String SIX_HUNDRED 	=  	"600";
    private static final String TWO_NINETY 		=  	"290";

    private LogUtils            logUtils                = new LogUtils( logger, true );
    private List<String>        claimLabelValues;
    
    
    public ClaimIdPopulator(){}

    
    
    public void populateClaimId(final BenefitClaimRecord response, RbpsRepository repo) {

        List<ShrinqbcSelection>     claimInfo = response.getParticipantRecord().getSelection();
        
        //checkForNon130PendingClaims( claimInfo, repo );
        // for ccr 2074 and 2047 this method is modified now it is used only 
        // set the claim rcvd date.
        if ( locateClaimIdforSameId( claimInfo, repo ) ) {
        	
        	return;
        }
           // changes for ccr 2047 The best guess logic should be taken out. 
          //recordClaimId( match( claimInfo, repo ), repo );
    }
    
    
    public void checkForNon130PendingClaims( final ClaimInfoDTO claimInfoDTO, RbpsRepository repo ) {
    	
// for ccr 2074. if there is a pending claim the webservice will return
  // reject or reject lcd .

    	if(claimInfoDTO.getReason().equalsIgnoreCase("proceed")){
    		return;
    	}
    	else //if(claimInfoDTO.getReason().equalsIgnoreCase("reject")){
    		throw new RbpsRuntimeException( String.format( "Pending claim exists for EP%s series",claimInfoDTO.getBnftClaimTypeCd().subSequence(0, 3) ) );
    		
    		
    	}
    
   
    
    public boolean locateClaimIdforSameId( final List<ShrinqbcSelection> claimInfoList, RbpsRepository repo ) {
    	
    	boolean foundClaimId = false;
    	
    	for ( ShrinqbcSelection	selection	:	claimInfoList ) {
    		
    		if ( selection.getBenefitClaimID().equals( Long.valueOf(repo.getVeteran().getClaim().getClaimId() ).toString() ) && 
    																						isNotCancelledOrCleared( selection ) ) {
    			
    			repo.getVeteran().getClaim().setReceivedDate( SimpleDateUtils.convertDate( "claim received date", selection.getClaimReceiveDate() ) );
    			logUtils.log( "Updating claim received date from Benefit Claim to " + selection.getClaimReceiveDate(), repo );
    			foundClaimId = true;
    			break;
    		}
    	}
    	return foundClaimId;	
    }
    
    
    
    public void recordClaimId( final ShrinqbcSelection claimInfo, RbpsRepository repo ) {

        if ( claimInfo == null ) {

            logUtils.log( "Not updating benefit claim Id, didn't find a matching claim in data from web service", repo);
            return;
        }

        long    oldClaimId =repo.getVeteran().getClaim().getClaimId();
        long    newClaimId = Long.parseLong( claimInfo.getBenefitClaimID() );

        if ( oldClaimId == newClaimId ) {

            logUtils.log( String.format( "Not updating benefit claim Id, already correct: >%d<", oldClaimId ), repo);
            return;
        }

        logUtils.log( String.format( "Updating benefit claim Id from >%d< to >%d<", oldClaimId, newClaimId ),repo );

       repo.getVeteran().getClaim().setClaimId( RbpsUtil.getExceptionSafeLongValue( claimInfo.getBenefitClaimID() ) );
    }


    public ShrinqbcSelection match( final List<ShrinqbcSelection> claimInfo, RbpsRepository repo) {

        if ( CollectionUtils.isEmpty( claimInfo ) ) {

            return null;
        }

        for ( ShrinqbcSelection selection : claimInfo ) {

            if ( isMatch( selection, repo ) ) {

                return selection;
            }
        }

        return null;
    }


    private boolean isMatch( final ShrinqbcSelection selection, RbpsRepository repo) {

        if ( selection == null ) {

            return false;
        }

        return isPending( selection )
                && isSameDay( selection, repo )
                && isValidClaimType( selection );
    }


    private boolean isValidClaimType( final ShrinqbcSelection selection ) {

        return ( getClaimLabelValues().contains( selection.getClaimTypeCode() ) );
    }


    private boolean isSameDay( final ShrinqbcSelection selection, RbpsRepository repo) {

        Date        selectionDate = SimpleDateUtils.convertDate( "claim received date", selection.getClaimReceiveDate() );

//        logUtils.log( "selection claim received date: " + selectionDate );
//        logUtils.log( "xom claim received date: " + repository.getVeteran().getClaim().getReceivedDate() );

        return DateUtils.isSameDay( selectionDate,
                                    repo.getVeteran().getClaim().getReceivedDate() );
    }


    private static final boolean isPending( final ShrinqbcSelection selection ) {

        return selection.getStatusTypeCode().equalsIgnoreCase( PENDING_STATUS );
    }

    private static final boolean isNotCancelledOrCleared( final ShrinqbcSelection selection ) {

    	if ( selection.getStatusTypeCode().equalsIgnoreCase( CANCEL_STATUS ) ) {
    		
    		return false;
    	}
    	
    	if ( selection.getStatusTypeCode().equalsIgnoreCase( CLEARED_STATUS ) ) {
    		
    		return false;
    	}
    	
        return true;
    }

    private List<String> getClaimLabelValues() {

        if ( claimLabelValues != null ) {

            return claimLabelValues;
        }

        claimLabelValues = new ArrayList<String>();

        for ( ClaimLabelType    type : ClaimLabelType.values() ) {

            claimLabelValues.add( type.getCode() );
        }

        return claimLabelValues;
    }

}
