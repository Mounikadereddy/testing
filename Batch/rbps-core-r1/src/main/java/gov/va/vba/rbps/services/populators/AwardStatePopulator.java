/*
 * AwardStatePopulator.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.services.populators;


//import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.util.CommonUtils;
import gov.va.vba.rbps.coreframework.xom.AwardStatus;
import gov.va.vba.rbps.services.ws.client.mapping.awards.state.AwardStateServiceReturnVO;
import gov.va.vba.rbps.services.ws.client.mapping.awards.state.FindAwardStateResponse;


/**
 *      This class populates Award data received from FindAwardState web service.
 */
public class AwardStatePopulator {


    /**
     *         Populates the repository with Award State data
     */
    public void populateFromFindAwardStateWS(final FindAwardStateResponse response, final RbpsRepository repository) {

        AwardStateServiceReturnVO awardStateReturnVO = response.getReturn();

        if ( repository.getVeteran().getAwardStatus() == null ) {

        	repository.getVeteran().setAwardStatus( new AwardStatus() );
        }

        repository.getVeteran().getAwardStatus()
            .setGAOUsed(CommonUtils.convertBoolean(awardStateReturnVO.getIsGaoUsed()));

        repository.getVeteran().getAwardStatus()
            .setChangesMade(CommonUtils.convertBoolean(awardStateReturnVO.getChangeSinceLastAuth()));

        repository.getVeteran().getAwardStatus()
            .setIsSuspended( CommonUtils.convertBoolean( awardStateReturnVO.getIsSuspended() ) );

        repository.getVeteran().getAwardStatus()
            .setRunningAward(CommonUtils.convertBoolean(awardStateReturnVO.getIsRunningAward()));

        repository.getVeteran().getAwardStatus()
            .setProposed(CommonUtils.convertBoolean(awardStateReturnVO.getIsProposed()));
    }

}
