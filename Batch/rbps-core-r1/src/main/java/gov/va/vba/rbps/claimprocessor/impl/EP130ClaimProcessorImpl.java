/*
 * EP130ClaimProcessorImpl.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.claimprocessor.impl;


import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.claimprocessor.interfaces.ClaimProcessorInterface;
import gov.va.vba.rbps.claimvalidator.ClaimValidatorFactory;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.util.CommonUtils;
import gov.va.vba.rbps.coreframework.util.IndentedXomToString;
import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.coreframework.xom.RuleExceptionMessages;
import gov.va.vba.rbps.rule.service.RBPSRuleService;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

import static gov.va.vba.rbps.coreframework.util.RbpsConstants.BAR_FORMAT;
import static gov.va.vba.rbps.coreframework.util.RbpsUtil.stringListToStringBuilder;

//import gov.va.vba.rbps.coreframework.dto.RbpsRepository;



/**
 *      Process one ep130 claim - send it through the pre-processor to gather
 *      data, send the claim to rules for evaluation, and then onto post-processing
 *      to either send it to awards and letter gen, or mapd for recording
 *      the rules exceptions.
 */
public class EP130ClaimProcessorImpl implements ClaimProcessorInterface {

    private static Logger logger = Logger.getLogger(EP130ClaimProcessorImpl.class);

    private LogUtils                    logUtils                = new LogUtils( logger, true );

    private EP130ClaimPreProcessor      preProcessor;
    private EP130ClaimPostProcessor     postProcessor;
    
    private ClaimValidatorFactory       claimValidatorFactory;
    private RBPSRuleService             rbpsRuleService;


    /**
     *      Entry Method to Process the Claim. Responsible for invoking the Pre-Processor,
     *      Rule Engine, handling Rule Engine Output, then finally invoking Post Processor.
     *      (non-Javadoc)
     *
     *      @see gov.va.vba.rbps.claimprocessor.interfaces.ClaimProcessorInterface#processClaim()
     */
    @Override
    public void processClaim(RbpsRepository repository) {

        logUtils.debugEnter(repository);
        
        try {
            // invoke remaining services to hydrate beans
            preProcessor.preProcess( repository );

            if(!repository.hasRuleExceptionMessages()) {
                //
                //      This is the final data validation. The
                //      <code>AbstractClaimValidator.validate</code> method will run
                //      common data validation and decide whether Claim process will go
                //      through or logger exception
                //
                claimValidatorFactory.getClaimValidator(repository.getVeteran().getClaim().getEndProductCode()).validate(repository);

                if (repository.getVeteran().getServiceConnectedDisabilityRating() >= 30) {

                    RuleExceptionMessages messages = callRules(repository);
                    addJournalMessages(repository);
                    logUtils.log("Rules Output Messages:\n" + stringListToStringBuilder(messages.getMessages()), repository);
                }
            }
            postProcessor.postProcess(repository);
        }
        finally {

            logUtils.debugExit(repository);
        }
    }


    public RuleExceptionMessages callRules(RbpsRepository repository) {

        Map<String, Object> ruleOutputMap = rbpsRuleService.executeEP130(repository.getVeteran(), repository );

        RuleExceptionMessages messages = (RuleExceptionMessages) ruleOutputMap.get("messages");
        repository.setRuleExceptionMessages(messages);

        return messages;
    }


    private void addJournalMessages(RbpsRepository repository) {

        CommonUtils.log( logger,
                   "\n" + BAR_FORMAT + "\nRules Fired for Claim: #" + repository.getVeteran().getClaim().getClaimId()
                                  + "\n" + repository.getRuleExecutionInfo() + BAR_FORMAT + "\n" );

        String exceptionMessages = CommonUtils.join( repository.getRuleExceptionMessages().getMessages(), "\n\t" );
        if ( StringUtils.isBlank( exceptionMessages ) ) {

            exceptionMessages = "<None>";
        }

        CommonUtils.log( logger,
                   "\n" + BAR_FORMAT + "\nRules exception messages for Claim: #" + repository.getVeteran().getClaim().getClaimId()
                                  + "\n" + exceptionMessages + "\n" + BAR_FORMAT + "\n");

        CommonUtils.log( logger,
                   "\n" + BAR_FORMAT
                   + "\nVeteran with ALL Data - POST-Rules:\n"
                   + "proc id: " + repository.getVnpProcId() + "\n"
                   + new IndentedXomToString().toString( repository.getVeteran(), 0 ) + "\n" + BAR_FORMAT + "\n" );
    }

    

    public void setPostProcessor(final EP130ClaimPostProcessor postProcessor) {
        this.postProcessor = postProcessor;
    }
    public void setPreProcessor(final EP130ClaimPreProcessor preProcessor) {
        this.preProcessor = preProcessor;
    }
    
    public void setClaimValidatorFactory(final ClaimValidatorFactory claimValidatorFactory) {
        this.claimValidatorFactory = claimValidatorFactory;
    }
    public void setRbpsRuleService(final RBPSRuleService rbpsRuleService) {
        this.rbpsRuleService = rbpsRuleService;
    }
}
