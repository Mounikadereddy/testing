/*
 * RBPSRuleServiceImpl.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.rule.service.impl;


import static gov.va.vba.rbps.coreframework.util.RbpsConstants.BAR_FORMAT;
import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.exception.RbpsRuleExecutionException;
import gov.va.vba.rbps.coreframework.util.CommonUtils;
import gov.va.vba.rbps.coreframework.xom.Award;
import gov.va.vba.rbps.coreframework.xom.Child;
import gov.va.vba.rbps.coreframework.xom.Education;
import gov.va.vba.rbps.coreframework.xom.MarriageTerminationType;
import gov.va.vba.rbps.coreframework.xom.RuleExceptionMessages; 
import gov.va.vba.rbps.coreframework.xom.Spouse;
import gov.va.vba.rbps.coreframework.xom.Veteran;
import gov.va.vba.rbps.coreframework.xom.util.VeteranCommonDates;
import gov.va.vba.rbps.rule.service.RBPSRuleService;
import gov.va.vba.rbps.rulesengine.engine.Response;
import gov.va.vba.rbps.rulesengine.engine.RulesEngine;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;



/**
 *      Calls the rule service for veteran, spouse, and child rules.
 */
public class RBPSRuleServiceImpl implements RBPSRuleService {  

    private static final long serialVersionUID = 2849800392317273005L;
    private Logger logger = Logger.getLogger(this.getClass());

    private RulesEngine ruleEngine;
//    private RBPSRuleServiceUtil     ruleServiceUtil;
    

    @Override
    public Map<String, Object> executeEP130(final Veteran veteran, final RbpsRepository repository)
            throws RbpsRuleExecutionException {

        final RuleExceptionMessages   messages    = new RuleExceptionMessages();
        final Map<String, Object>     output      = new HashMap<String, Object>();
        final Map<String, Object>     inputParams = new HashMap<String, Object>();

        logger.debug("*** RBPSRuleServiceImpl: Starting execution of rules inside executeEP130 method.");

        // Execute Veteran Rules
        final VeteranCommonDates vetData = executeVeteranRules( veteran,
                                                          inputParams,
                                                          messages, repository );

        // Execute Spouse Rules
        executeSpouseRules( veteran, vetData, inputParams, messages, repository );
        executeSpouseRemovalRules( veteran, vetData, inputParams, messages, repository );

        // Execute Child Rules
        executeChildRules( veteran, vetData, inputParams, messages, repository );

        String claimDetails = "";
        if (veteran.getClaim().getReceivedDate() != null) {
        	claimDetails = "Claim Received Date: " +veteran.getClaim().getReceivedDate().toString();
        }
        
        if (veteran.getFirstChangedDateofRating() != null) {
        	claimDetails += ", FCDR: " + veteran.getFirstChangedDateofRating().toString();
        }
        
        if (veteran.getRatingDate() != null) {
        	claimDetails += ", Rating Date: " + veteran.getRatingDate().toString();
        }
       
        if (veteran.getRatingEffectiveDate() != null) {
        	claimDetails += ", Rating Effective Date: " + veteran.getRatingEffectiveDate().toString();
        }
        
        if (veteran.getAllowableDate() != null) {
        	claimDetails += ", Allowable Date: " + veteran.getAllowableDate().toString();
        }
      
        CommonUtils.log( logger,"\n" + BAR_FORMAT + claimDetails + BAR_FORMAT + "\n");
        
        logger.debug("*** RBPSRuleServiceImpl: Completed executing EP130 Rules for Veteran: "
                + veteran.getLastName() + ", " + veteran.getFirstName());
        logger.debug("*** RBPSRuleServiceImpl: EP130 Exception messages: "
                + messages.getMessages().toString());

        output.put("messages", messages);
        output.put("veteran", veteran);

        return output;
    }


    private VeteranCommonDates executeVeteranRules( final Veteran                   veteran,
                                                    final Map<String, Object>       inputParams,
                                                    final RuleExceptionMessages     messages,
                                                    final RbpsRepository repository ) {

        try {
            inputParams.put("Veteran", veteran);
//            final IlrPath veteranPath = ruleServiceUtil.getEP130VeteranRulePath();
            //modified for JRule 2 Java
            logger.debug("*** RBPSRuleServiceImpl: Starting the Veteran Rules.. with veteran: "+ veteran.toString());
            logger.debug("*** RBPSRuleServiceImpl: Before executing veteran rules.....");
            final Response response = ruleEngine.execute(veteran);

//            ruleExecutionStatistics(response, "veteran", repository);

            final RuleExceptionMessages vetMessages = (RuleExceptionMessages) response
                    .getOutputParameters().get("Exceptions");
            messages.getMessages().addAll(vetMessages.getMessages());

            logExceptionMessageFromRules( vetMessages, "Veteran", repository );
            
            final VeteranCommonDates vetData = gatherCommonDates( veteran );

            // logger.debug("*** RBPSRuleServiceImpl: after the Veteran Rules.. with vetData."+ vetData.toString());
            logger.debug("*** RBPSRuleServiceImpl: after the Veteran Rules.. output of vetData: " + vetData.toString()); 
            logger.debug("*** RBPSRuleServiceImpl: after the Veteran Rules.. output of messages: " + messages.toString());

            return vetData;
        }
        catch (final Exception ex) {

            repository.addValidationMessage( "Exception occurred in RBPS while executing EP130VeteranRules.");
            throw new RbpsRuleExecutionException( "*** RBPSRuleServiceImpl: Exception occurred while executing EP130VeteranRules: ", ex);
        }
    }


    private void executeSpouseRules( final Veteran                  veteran,
                                     final VeteranCommonDates       vetData,
                                     final Map<String, Object>      inputParams,
                                     final RuleExceptionMessages    messages,
                                     final RbpsRepository repository) {

        if ( ! veteranHasMarriage( veteran ) ) {

            return;
        }

        if ( isSpouseOnAward( veteran ) ) {

            return;
        }


        try {
            logger.debug("*** RBPSRuleServiceImpl: Starting the Spouse Rules...");

            final Spouse      spouse        = veteran.getCurrentMarriage().getMarriedTo();
//            final IlrPath     spousePath    = ruleServiceUtil.getEP130SpouseRulePath();
            logger.debug("*** RBPSRuleServiceImpl: Starting the Spouse Rules.. with spouse."+spouse.toString());
            logger.debug("*** RBPSRuleServiceImpl: Starting the Spouse Rules.. with spouse award eff date."+spouse.getAwardEffectiveDate());
            logger.debug("*** RBPSRuleServiceImpl: Starting the Spouse Rules.. with spouse marriage end date."+spouse.getCurrentMarriage().getEndDate());
          
            inputParams.clear();
            inputParams.put("Spouse", spouse);
            inputParams.put("Veteran", vetData);

            final Response spouseResponse = ruleEngine.execute(spouse, vetData);

//            ruleExecutionStatistics(spouseResponse, "spouse", repository);

            RuleExceptionMessages spouseMessages = (RuleExceptionMessages) spouseResponse
                    .getOutputParameters().get("Exceptions");

            spouseMessages = (spouseMessages != null ) ? spouseMessages : new RuleExceptionMessages();

            logger.debug("*** RBPSRuleServiceImpl: Spouse Exception Messages for: "
                    + spouse.getLastName() + ", " + spouse.getFirstName()
                    + " is : " + spouseMessages.getMessages());

            logExceptionMessageFromRules( spouseMessages, "Spouse", repository );
            
            messages.getMessages().addAll(spouseMessages.getMessages());

            retrieveSpouseAward( spouse, spouseResponse );
            // print out for JRule to Java
            logger.debug("*** RBPSRuleServiceImpl: after the Spouse Rules.. output spouse." + spouse.toString());
            logger.debug("*** RBPSRuleServiceImpl: after the Spouse Rules.. output messages: " + messages.toString());
        }
        catch (final Exception ex) {

            repository.addValidationMessage("Exception occurred in RBPS while executing EP130SpouseRules.");
            throw new RbpsRuleExecutionException(
                    "*** RBPSRuleServiceImpl: Exception occurred while executing EP130SpouseRules: ", ex);
        }
    }

    

    private void executeSpouseRemovalRules( final Veteran           veteran,
                                     final VeteranCommonDates       vetData,
                                     final Map<String, Object>      inputParams,
                                     final RuleExceptionMessages    messages,
                                     final RbpsRepository 			repository) {

        if ( veteran.getLatestPreviousMarriage() == null ) {

        	logger.debug("No Spouse for removal. ");
            return;
        }

        if ( veteran.getLatestPreviousMarriage().getMarriedTo() == null ) {

        	logger.debug("No Spouse for removal. ");
            return;
        }
        
        if ( ! veteran.getLatestPreviousMarriage().getMarriedTo().isEverOnAward() ) {

        	logger.debug("Spouse for removal was never on Award. ");
            return;
        }
        
        if ( veteran.getLatestPreviousMarriage().getMarriedTo().isDeniedAward() ) {

        	logger.debug("Spouse for removal was already removed from Award. ");
            return;
        }
        
        

        try {

            logger.debug("*** RBPSRuleServiceImpl: Starting the Spouse Rules for Spouse Removal...");
            
            final Spouse      previousSpouse        = veteran.getLatestPreviousMarriage().getMarriedTo();
//            final IlrPath     spousePath    = ruleServiceUtil.getEP130SpouseRulePath();
            
            
            logger.debug("*** RBPSRuleServiceImpl: Starting the Spouse Rules for Spouse Removal... with terminationtype" +
            previousSpouse.getCurrentMarriage().getTerminationType());
            logger.debug("*** RBPSRuleServiceImpl: Starting the Spouse Rules for Spouse Removal... veteran.isHasStepChildOrUndefinedChildType()" +
            		veteran.isHasStepChildOrUndefinedChildType());
                    
            if( previousSpouse.getCurrentMarriage().getTerminationType().equals(MarriageTerminationType.DIVORCE)  && 
            		veteran.isHasStepChildOrUndefinedChildType()){
         	  
         			   			throw new RbpsRuleExecutionException("Auto Dependency Processing Reject - "
         			   					+ "Spouse removal not completed because child type is stepchild or unknown. "
         			   					+ "Claim not eligible for automated processing.");
         		
            }
            logger.debug("*****RBPSRuleServiceImpl: Starting the Spouse Rules for Spouse Removal.."+ previousSpouse.toString());
            logger.debug("*****RBPSRuleServiceImpl: Starting the Spouse Rules for Spouse Removal. with awardeff date."+ previousSpouse.getAwardEffectiveDate());
            logger.debug("*****RBPSRuleServiceImpl: Starting the Spouse Rules for Spouse Removal."
            		+ " with marriage end date."+ previousSpouse.getCurrentMarriage().getEndDate());
            
            inputParams.clear();
            inputParams.put("Spouse", previousSpouse);
            inputParams.put("Veteran", vetData);

            final Response spouseResponse = ruleEngine.execute(previousSpouse, vetData);

//            ruleExecutionStatistics(spouseResponse, "spouse removal", repository);

            RuleExceptionMessages spouseMessages = (RuleExceptionMessages) spouseResponse
                    .getOutputParameters().get("Exceptions");

            spouseMessages = (spouseMessages != null ) ? spouseMessages : new RuleExceptionMessages();

            logger.debug("*** RBPSRuleServiceImpl: Spouse Removal Exception Messages for: "
                    + previousSpouse.getLastName() + ", " + previousSpouse.getFirstName()
                    + " is : " + spouseMessages.getMessages());

            logExceptionMessageFromRules( spouseMessages, "Spouse for removal", repository );
            
            messages.getMessages().addAll(spouseMessages.getMessages());

            retrieveSpouseAward( previousSpouse, spouseResponse );
            // print out for JRule to Java
            logger.debug("*****RBPSRuleServiceImpl: after the Spouse Rules for Spouse Removal.. Spouse: "+ previousSpouse.toString());
            logger.debug("*****RBPSRuleServiceImpl: after the Spouse Rules for Spouse Removal.. message: "+ messages.toString());
        }
        catch (final Exception ex) {

            repository.addValidationMessage("Exception occurred in RBPS while executing EP130SpouseRules for Spouse Removal.");
            throw new RbpsRuleExecutionException(
                    "*** RBPSRuleServiceImpl: Exception occurred while executing EP130SpouseRules for Spouse Removal: ", ex);
        }
    } 
    
    
    
    
    
    private void executeChildRules( final Veteran                   veteran,
                                    final VeteranCommonDates        vetData,
                                    final Map<String, Object>       inputParams,
                                    final RuleExceptionMessages     messages,
                                    final RbpsRepository repository ) {

        if ( CollectionUtils.isEmpty( veteran.getChildren() ) ) {

            return;
        }

        for ( final Child child : veteran.getChildren() ) {
            try {
//                final IlrPath childPath = ruleServiceUtil.getEP130ChildRulePath();
                inputParams.clear();
                inputParams.put("Child", child);
                inputParams.put("Veteran", vetData);
                //for testing
                CommonUtils.log( logger,
        		        "\n" + BAR_FORMAT + "\n" + child.getFirstName() + " object before child rule: #" + child.toString() + "\n" + BAR_FORMAT + "\n");
               
                final Response childResponse = ruleEngine.execute(child, vetData);
 
                CommonUtils.log( logger,"\n" + BAR_FORMAT + "after executeChildRules\n" + BAR_FORMAT + "\n");
               
//                ruleChildExecutionStats( childResponse, child, repository );
                
                CommonUtils.log( logger,"\n" + BAR_FORMAT + "after ruleChildExecutionStats\n" + BAR_FORMAT + "\n");
                
                final RuleExceptionMessages childMessages = (RuleExceptionMessages) childResponse
                        .getOutputParameters().get("Exceptions");
                
                CommonUtils.log( logger,"\n" + BAR_FORMAT + "after getting Exceptions from child rules\n" + BAR_FORMAT + "\n");
                
                final RuleExceptionMessages logMessages = (RuleExceptionMessages) childResponse
                        .getOutputParameters().get("LogMessage");
                
                logExceptionMessageFromRules( childMessages, "Child", repository );
                
                CommonUtils.log( logger,"\n" + BAR_FORMAT + "after logExceptionMessageFromRules\n" + BAR_FORMAT + "\n");
                
                String claimDetails = "";
                if (veteran.getClaim().getReceivedDate() != null) {
                	claimDetails = "Claim Received Date: " +veteran.getClaim().getReceivedDate().toString();
                }
                
                if (veteran.getFirstChangedDateofRating() != null) {
                	claimDetails += ", FCDR: " + veteran.getFirstChangedDateofRating().toString();
                }
                
                if (child.getLastTerm() != null) {
                	if (child.getLastTerm().getCourseStudentStartDate() != null) {
                		claimDetails = claimDetails +	", Prior Term Course Start Date: " + child.getLastTerm().getCourseStudentStartDate().toString() +
                						", Prior Term Course End Date: " + child.getLastTerm().getCourseEndDate().toString();
                	}
                	
                	if (child.getLastTerm().getCourseEndDate() != null) {
                		claimDetails = claimDetails + ", Prior Term Course End Date: " + child.getLastTerm().getCourseEndDate().toString();
                	}
                }
                
                CommonUtils.log( logger,"\n" + BAR_FORMAT + claimDetails + BAR_FORMAT + "\n");
                
                logDebugMessageFromRules(logMessages, "Child");
                
                messages.getMessages().addAll(childMessages.getMessages());

                CommonUtils.log( logger,"\n" + BAR_FORMAT + "after addAll Messages\n" + BAR_FORMAT + "\n");

                retrieveChildAward( child, childResponse );
                
                CommonUtils.log( logger,"\n" + BAR_FORMAT + "after aretrieveChildAward\n" + BAR_FORMAT + "\n");
                
                retrieveMinorSchoolChildAward( child, childResponse );
                
                CommonUtils.log( logger,"\n" + BAR_FORMAT + "after retrieveMinorSchoolChildAward\n" + BAR_FORMAT + "\n");
                retrievePriorSchoolChildAwardStatus(veteran, child, childResponse );
                
                mergeSchoolTerms(child);
                // print out for JRule to Java conversion
                CommonUtils.log( logger,
        		        "\n" + BAR_FORMAT + "\n" + child.getFirstName() + " object after child rule: #" + child.toString() + "\n" + BAR_FORMAT + "\n");
                logger.debug("*****RBPSRuleServiceImpl: after the child Rules .. message: "+ messages.toString());
            }
            catch (final Exception ex) {

                final String validationMessage = String.format( "Exception occurred in RBPS while executing EP130ChildRules for child %s, %s",
                                                          child.getLastName(),
                                                          child.getFirstName() );

                repository.addValidationMessage( validationMessage );
                
                String exMessage = "Exception in Child Rules, stack: " + ex.getStackTrace().toString();
                CommonUtils.log( logger, exMessage);
                exMessage = "Exception in Child Rules, message: " + ex.getMessage();
                CommonUtils.log( logger, exMessage);
                
               
                throw new RbpsRuleExecutionException( validationMessage, ex);
            }
        }
    }


	private void logExceptionMessageFromRules( final RuleExceptionMessages childMessages, String personString, final RbpsRepository repository ) {
		
		String exceptionMessages = CommonUtils.join( childMessages.getMessages(), "\n\t" );
		
		if ( StringUtils.isBlank( exceptionMessages ) ) {

		    exceptionMessages = "<None>";
		}
		CommonUtils.log( logger,
		        "\n" + BAR_FORMAT + "\n" + personString + " Exception Messages: #" + exceptionMessages + "\n" + BAR_FORMAT + "\n");
	}


	
	private void logDebugMessageFromRules( final RuleExceptionMessages childMessages, String personString) {
		CommonUtils.log( logger, "BEGIN logDebugMessageFromRules");
		if (childMessages == null || childMessages.getMessages() == null) {
			CommonUtils.log( logger, "childMessages is null");
			return;
		}
		
		String debugMessages = CommonUtils.join( childMessages.getMessages(), "\n\t" );
		
		if ( StringUtils.isBlank( debugMessages ) ) {

			debugMessages = "<None>";
		}
		CommonUtils.log( logger,
		        "\n" + BAR_FORMAT + "\n" + personString + " Debug Messages: #" + debugMessages + "\n" + BAR_FORMAT + "\n");
		CommonUtils.log( logger, "END logDebugMessageFromRules");
	}
	
	  private void retrievePriorSchoolChildAwardStatus(final Veteran  veteran,
			  		final Child                child,
	            final Response   childResponse )
	    {
		  logger.debug(  "*** RBPSRuleServiceImpl: retrievePriorSchoolChildAwardStatus ***" );
		  final String priorSchoolStatus = (String) childResponse.getOutputParameters().get("PriorSchoolTermStatus");
		  logger.debug(  "*** RBPSRuleServiceImpl: retrievePriorSchoolChildAwardStatus ***"+priorSchoolStatus );
			  
		  if(priorSchoolStatus !=null &&  priorSchoolStatus.equalsIgnoreCase("ignore")){
			  logger.debug(  "*** RBPSRuleServiceImpl: retrievePriorSchoolChildAwardStatus ***"+priorSchoolStatus );
				
			  veteran.setPriorSchoolTermRejected(true);
			  logger.debug(  "*** RBPSRuleServiceImpl: retrievePriorSchoolChildAwardStatus  afterveteran.setPriorSchoolTermRejected***" );
			
			  Education lastTerm = child.getLastTerm();
			  
			  if (lastTerm != null) {
				  DateFormat  formatter   = new SimpleDateFormat("MMMM dd, yyyy");
			       
				  String priorSchoolTermDatesString = null;
				  
				  if (child.getLastTerm().getCourseStudentStartDate() != null) {
					  priorSchoolTermDatesString = formatter.format(lastTerm.getCourseStudentStartDate());
						 
				  }
				  else {
					  priorSchoolTermDatesString = "No Course Start Date";
				  }
				  
				  if (child.getLastTerm().getCourseEndDate() != null) {
					   
					  priorSchoolTermDatesString += " to " + formatter.format(lastTerm.getCourseEndDate());
				  }
				  else {
					  priorSchoolTermDatesString = " to No Course End Date";
				  }
				  
				  String priorSchoolChildName = child.getFirstName();
				  
				  if (veteran.getPriorSchoolTermDatesString() == null) {
					  logger.debug(  "*** RBPSRuleServiceImpl: retrievePriorSchoolChildAwardStatus,  setting PriorSchoolTermDatesString to [" + priorSchoolTermDatesString + "]");
					  veteran.setPriorSchoolTermDatesString(priorSchoolTermDatesString);
				  } else {
					 String tempString = veteran.getPriorSchoolTermDatesString() + ", " + priorSchoolTermDatesString;
					 logger.debug(  "*** RBPSRuleServiceImpl: retrievePriorSchoolChildAwardStatus, appending PriorSchoolTermDatesString to [" + tempString + "]");
						
					 veteran.setPriorSchoolTermDatesString(tempString);
				  }
				  
				  if (veteran.getPriorSchoolChildName() == null) {
					  logger.debug(  "*** RBPSRuleServiceImpl: retrievePriorSchoolChildAwardStatus,  PriorSchoolChildName =  " + priorSchoolChildName);
					  veteran.setPriorSchoolChildName(priorSchoolChildName);
				  } else {
					 String tempString = veteran.getPriorSchoolChildName() + ", " + priorSchoolChildName;
					 logger.debug(  "*** RBPSRuleServiceImpl: retrievePriorSchoolChildAwardStatus, appending priorSchoolChildName to [" + tempString + "]");
						
					 veteran.setPriorSchoolTermDatesString(tempString);
				  }
			  }
		  }
		  else{
			  logger.debug(  "*** RBPSRuleServiceImpl: retrievePriorSchoolChildAwardStatus before retrievePriorSchoolChildAward ***" );
				
			  retrievePriorSchoolChildAward( child, childResponse );
			  logger.debug(  "*** RBPSRuleServiceImpl: retrievePriorSchoolChildAwardStatus after retrievePriorSchoolChildAward ***" );
				
		  }
		 
	    }
    private void retrievePriorSchoolChildAward( final Child  child,
            final Response   childResponse )
    {
    	final Award priorSchoolChildAward = (Award) childResponse.getOutputParameters().get("PriorSchoolChild");

    	if (priorSchoolChildAward == null) {

    		logger.debug(  "*** RBPSRuleServiceImpl: Prior School Child Award is null ***" );
    		return;
    	}

    	if ( priorSchoolChildAward.getDependencyDecisionType() == null ) {

    		logger.debug(  "*** RBPSRuleServiceImpl: Prior School Child DependencyDecisionType is null ***" );
    		return;
    	}
    	if ( priorSchoolChildAward.getEventDate() == null ) {

    		logger.debug(  "*** RBPSRuleServiceImpl: Prior School Child Event Date is null ***" );
    		logger.debug(  "*** RBPSRuleServiceImpl: priorSchoolChildAward.getDependencyStatusType() ***"+priorSchoolChildAward.getDependencyStatusType() );
        	logger.debug(  "*** RBPSRuleServiceImpl: priorSchoolChildAward.getDependencyDecisionType ***" +priorSchoolChildAward.getDependencyDecisionType());
        	
    		return;
    	}

    	logger.debug( String.format( "*** RBPSRuleServiceImpl: Prior School Child Award Event Date for >%s,%s< is: %s",
    								child.getLastName(),
    								child.getFirstName(),
    								priorSchoolChildAward.getEventDate() ) );
    	logger.debug( String.format( "*** RBPSRuleServiceImpl: Prior School Child Award End Date for >%s,%s< is: %s",
				child.getLastName(),
				child.getFirstName(),
				priorSchoolChildAward.getEndDate() ) );
    	child.setPriorSchoolChildAward(priorSchoolChildAward);
    }
    
    private void retrieveChildAward( final Child                child,
                                     final Response   childResponse ) {

        final Award award = (Award) childResponse.getOutputParameters().get("Award");

        if (award == null) {

        	logger.debug(  "*** RBPSRuleServiceImpl: Child Award is null ***" );
            return;
        }

        if ( award.getDependencyDecisionType() == null ) {

        	logger.debug(  "*** RBPSRuleServiceImpl: Child DependencyDecisionType is null ***" );
        	return;
        }
        if ( award.getEventDate() == null ) {

        	logger.debug(  "*** RBPSRuleServiceImpl: Child Event Date is null ***" );
        	logger.debug(  "*** RBPSRuleServiceImpl: award.getDependencyStatusType() ***"+award.getDependencyStatusType() );
        	logger.debug(  "*** RBPSRuleServiceImpl: award.getDependencyDecisionType ***" +award.getDependencyDecisionType());
        	
        	return;
        }
        
        logger.debug( String.format( "*** RBPSRuleServiceImpl: Award Event Date for >%s,%s< is: %s",
                                     child.getLastName(),
                                     child.getFirstName(),
                                     award.getEventDate() ) );
        child.setAward(award);
        
    }


    private void mergeSchoolTerms(final Child child) {
    	logger.debug("BEGIN mergeSchoolTerms");
    	
    	Award priorTerm = child.getPriorSchoolChildAward();
    	Award currentTerm = child.getMinorSchoolChildAward();
    	
    	if (priorTerm != null && currentTerm != null) {
    		Date priorEventDate = priorTerm.getEventDate();
    		Date priorEndDate = priorTerm.getEndDate();
    		Date currentEventDate = currentTerm.getEventDate();
    		if (priorEventDate != null && priorEndDate != null && currentEventDate != null) {
    			
    			DateFormat  formatter   = new SimpleDateFormat("MMMM dd, yyyy");
    			logger.debug("prior term event date =  " + formatter.format(priorEventDate) +
    					"prior term end date =  " + formatter.format(priorEndDate) +
    					"current term event date =  " + formatter.format(currentEventDate));
    			
    			if (priorEndDate.equals(currentEventDate)) {
    				Calendar cal1 = Calendar.getInstance();
    				Calendar cal2 = Calendar.getInstance();
    				cal1.setTime(priorEventDate);
    				cal2.setTime(currentEventDate);
    			
    				if (cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) && 
    					cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)) {
    					currentTerm.setEventDate(priorEventDate);
    					child.setPriorSchoolChildAward(null);
    					logger.debug("prior term and current term are starting in the same month. ignoring prior term");
    				}
    			}
    		}
    	}
    	logger.debug("END mergeSchoolTerms");
    }
    
    private void retrieveMinorSchoolChildAward( final Child                 child,
                                                final Response    childResponse ) {

        final Award minorSchoolChildAward = (Award) childResponse.getOutputParameters().get("MinorSchoolChildAward");

        if (minorSchoolChildAward == null) {
        	logger.debug(  "*** RBPSRuleServiceImpl: Minor School Child Award is null ***" );
            return;
        }

        if ( minorSchoolChildAward.getDependencyDecisionType() == null ) {

        	logger.debug(  "*** RBPSRuleServiceImpl: Minor School Child DependencyDecisionType is null ***" );
        	return;
        }
        
        if ( minorSchoolChildAward.getEventDate() == null ) {

        	logger.debug(  "*** RBPSRuleServiceImpl: Minor School Child Event Date is null ***" );
        	return;
        }
        
        logger.debug( String.format( "*** RBPSRuleServiceImpl: Minor School Child Event Date for >%s,%s< is: %s",
                                     child.getLastName(),
                                     child.getFirstName(),
                                     minorSchoolChildAward.getEventDate() ) );
        child.setMinorSchoolChildAward(minorSchoolChildAward);
    }


    private void retrieveSpouseAward( final Spouse              spouse,
                                      final Response  spouseResponse ) {

        final Award award = (Award) spouseResponse.getOutputParameters().get("Award");
        if (award == null) {

            return;
        }
        
        if (award.getEventDate() == null) {

            return;
        }
        
        if (award.getDependencyDecisionType() == null) {

            return;
        }
        
        logger.debug( String.format( "*** RBPSRuleServiceImpl: Spouse Event Date for >%s,%s< is: %s for decision type %s",
                                     spouse.getLastName(),
                                     spouse.getFirstName(),
                                     award.getEventDate(),
                                     award.getDependencyDecisionType().getValue() ) );

        spouse.setAward(award);
    }


    private VeteranCommonDates gatherCommonDates( final Veteran veteran ) {

        if (veteran.getClaim() == null) {

            return null;
        }

        final VeteranCommonDates vetData = new VeteranCommonDates();
        logger.debug("*** RBPSRuleServiceImpl: Setting common dates...");

        vetData.setClaimDate(veteran.getClaim().getReceivedDate());

        if (veteran.getCurrentMarriage() != null
                && veteran.getCurrentMarriage().getStartDate() != null) {

            vetData.setMarriageDate(veteran.getCurrentMarriage().getStartDate());
        }

        vetData.setRatingDate(veteran.getRatingDate());
        vetData.setRatingEffectiveDate(veteran.getRatingEffectiveDate());
        vetData.setFirstChangedDateofRating( veteran.getFirstChangedDateofRating() );
        vetData.setAllowableDate(veteran.getAllowableDate());
        
        return vetData;
    }


/*    private void ruleChildExecutionStats( final IlrSessionResponse response, final Child    child, final RbpsRepository repository ) {

        final IlrExecutionTrace             sessionTrace        = response.getRulesetExecutionTrace();
        final long                          numRulesFired       = sessionTrace.getTotalRulesFired();
        final IlrBusinessExecutionTrace     bizExecutionTrace   = new IlrBusinessExecutionTrace(sessionTrace);
        final long                          executionDuration   = bizExecutionTrace.getExecutionDuration();
        final Date                          executionDate       = bizExecutionTrace.getExecutionDate();

        final String formattedRuleExecutionStatistics = "\n" + BAR_FORMAT
            + "\n[" + numRulesFired + "] Child rules fired for >" + child.getLastName() + ", " + child.getFirstName()
            + "< Rules Fired in ["  + executionDuration + "] milliseconds on [" + executionDate.toString() + "]\n"
            + stringListToStringBuilder( bizExecutionTrace.getRuleFiredBusinessNames() )
            + BAR_FORMAT + "\n";

        repository.setRuleExecutionInfo( repository.getRuleExecutionInfo()
                                         + stringListToStringBuilder(bizExecutionTrace.getRuleFiredBusinessNames()));

        CommonUtils.log( logger, formattedRuleExecutionStatistics );
    }*/


/*    private void ruleExecutionStatistics(final IlrSessionResponse response, final String type, final RbpsRepository repository ) {

        final IlrExecutionTrace             sessionTrace        = response.getRulesetExecutionTrace();
        final long                          numRulesFired       = sessionTrace.getTotalRulesFired();
        final IlrBusinessExecutionTrace     bizExecutionTrace   = new IlrBusinessExecutionTrace(sessionTrace);
        final long                          executionDuration   = bizExecutionTrace.getExecutionDuration();
        final Date                          executionDate       = bizExecutionTrace.getExecutionDate();

        final String formattedRuleExecutionStatistics = "\n" + BAR_FORMAT
                + "\n[" + numRulesFired + "] " + type + " Rules Fired in ["  + executionDuration + "] milliseconds on [" + executionDate.toString() + "]\n"
                + stringListToStringBuilder( bizExecutionTrace.getRuleFiredBusinessNames() )
                + BAR_FORMAT + "\n";

        repository.setRuleExecutionInfo( repository.getRuleExecutionInfo()
                + stringListToStringBuilder(bizExecutionTrace.getRuleFiredBusinessNames()));

        CommonUtils.log( logger, formattedRuleExecutionStatistics );
    }*/


    private boolean veteranHasMarriage( final Veteran veteran ) {

        return veteran.getCurrentMarriage() != null
                && veteran.getCurrentMarriage().getMarriedTo() != null;
    }

    private boolean isSpouseOnAward( final Veteran veteran ) {

	    return 	veteran.getCurrentMarriage() != null
	    		&& veteran.getCurrentMarriage().getMarriedTo() != null
	    		&& veteran.getCurrentMarriage().getMarriedTo().isOnCurrentAward();

    }

    private boolean isSpouseDeniedAward( final Veteran veteran ) {

	    return 	veteran.getCurrentMarriage() != null
	    		&& veteran.getCurrentMarriage().getMarriedTo() != null
	    		&& veteran.getCurrentMarriage().getMarriedTo().isDeniedAward();

    }

    public void setRuleEngine(final RulesEngine ruleEngine) {
        this.ruleEngine = ruleEngine;
    }

/*    public void setRuleServiceUtil(final RBPSRuleServiceUtil ruleServiceUtil) {
        this.ruleServiceUtil = ruleServiceUtil;
    }*/
}
