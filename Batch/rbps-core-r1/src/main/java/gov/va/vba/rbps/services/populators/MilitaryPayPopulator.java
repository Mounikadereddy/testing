/*
 * MilitaryPayPopulator.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.services.populators;


import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.vo.RetirementPayType;
import gov.va.vba.rbps.services.ws.client.mapping.awards.militarypay.FindMilitaryPayResponse;
import gov.va.vba.rbps.services.ws.client.mapping.awards.militarypay.RetirementPayVO;

import java.util.List;

import org.springframework.util.CollectionUtils;


/**
 *      This class populates Military data received from FindMilitaryPay web service.
 */
public class MilitaryPayPopulator {

    private static Logger logger = Logger.getLogger(MilitaryPayPopulator.class);

    
    // static class now
    private MilitaryPayPopulator() {}

    /**
     * Sets receivingMilitaryRetirePay field from FindMilitaryPayResponse
     *
     * @param rbpsRepository
     * @param findMilitaryPayResponse
     */
    public static final void populateFromFindMilitaryPayWS(final FindMilitaryPayResponse findMilitaryPayResponse, RbpsRepository repo) {

        if (repo == null) {

            logger.debug(MilitaryPayPopulator.class.getName() + ": " + new Exception().getStackTrace()[0].getMethodName() + " => RbpsRepository is null.");
            return;
        }
        if (findMilitaryPayResponse == null || findMilitaryPayResponse.getReturn() == null ) {

            logger.debug(MilitaryPayPopulator.class.getName() + ": " + new Exception().getStackTrace()[0].getMethodName() + " => FindMilitaryPayResponse is null.");
            return;
        }

        repo.getVeteran().setReceivingMilitaryRetirePay(isReceivingMilitaryRetirePay(findMilitaryPayResponse.getReturn().getRtrmntPayVOList()));
    }


    private static final boolean isReceivingMilitaryRetirePay(final List<RetirementPayVO> retirementPayList) {

        if ( CollectionUtils.isEmpty( retirementPayList ) ) {
            return false;
        }

        for (RetirementPayVO retirementPay : retirementPayList) {

            if (RetirementPayType.find(retirementPay.getRetirementPayType()) != null) {

                return true;
            }
        }

        return false;
    }
}
