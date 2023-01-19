
/*
 * EP130ClaimPostProcessor.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.claimprocessor.impl;

import static gov.va.vba.rbps.coreframework.util.RbpsConstants.CLAIM_STATUS_PROCESSING_COMPLETE;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.naming.NamingException;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.httpclient.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import gov.va.bip.docgen.service.api.model.v1.ContentBase;
import gov.va.bip.docgen.service.api.model.v1.DocumentGenerationRequest;
import gov.va.bip.docgen.service.plugin.awards.api.edoc.EdocDocument;
import gov.va.vba.framework.common.StringUtils;
import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.claimprocessor.util.ClaimProcessorHelper;
import gov.va.vba.rbps.claimprocessor.util.ConnectionFactory;
import gov.va.vba.rbps.coreframework.dto.DisplayAddressVO;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.exception.RbpsLetterGenException;
import gov.va.vba.rbps.coreframework.exception.RbpsRuntimeException;
import gov.va.vba.rbps.coreframework.exception.RbpsWebServiceClientException;
import gov.va.vba.rbps.coreframework.util.EmailSender;
import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.coreframework.util.NullSafeGetter;
import gov.va.vba.rbps.coreframework.util.RbpsConstants;
import gov.va.vba.rbps.coreframework.util.SimpleDateUtils;
import gov.va.vba.rbps.coreframework.xom.Award;
import gov.va.vba.rbps.coreframework.xom.Child;
import gov.va.vba.rbps.coreframework.xom.DependentDecisionType;
import gov.va.vba.rbps.coreframework.xom.DependentStatusType;
import gov.va.vba.rbps.coreframework.xom.FormType;
import gov.va.vba.rbps.coreframework.xom.Marriage;
import gov.va.vba.rbps.coreframework.xom.Spouse;
import gov.va.vba.rbps.coreframework.xom.Veteran;
import gov.va.vba.rbps.lettergeneration.AwardPrintSaver;
import gov.va.vba.rbps.lettergeneration.GeneratePdf;
import gov.va.vba.rbps.lettergeneration.ProcessMilitaryPay;
import gov.va.vba.rbps.lettergeneration.batching.util.FileNameGenerator;
//import gov.va.vba.rbps.lettergeneration.docgen.client.RbpsDocGenClient;
import gov.va.vba.rbps.lettergeneration.docgen.genstore.service.RbpsDocGenGenstoreService;
//import gov.va.vba.rbps.lettergeneration.docgen.service.RbpsDocGenService;
import gov.va.vba.rbps.services.populators.CorporateParticpantIdPopulator;
import gov.va.vba.rbps.services.populators.FiduciaryPopulator;
import gov.va.vba.rbps.services.populators.ReadCurrentAndProposedAwardPopulator;
import gov.va.vba.rbps.services.ws.client.handler.awardDoc.ReadAwardDocDataWSHandler;
import gov.va.vba.rbps.services.ws.client.handler.awards.ProcessAwardDependentWSHandler;
import gov.va.vba.rbps.services.ws.client.handler.awards.ReadCurrentAndProposedAwardWSHandler;
import gov.va.vba.rbps.services.ws.client.handler.getPersonProfile.GetPersonProfileWSHandler;
import gov.va.vba.rbps.services.ws.client.handler.share.ClearBenefitClaimWSHandler;
import gov.va.vba.rbps.services.ws.client.handler.share.FindDependentsWSHandler;
import gov.va.vba.rbps.services.ws.client.handler.share.FindFiduciaryWSHandler;
import gov.va.vba.rbps.services.ws.client.handler.share.UpdateBenefitClaimDependentsWSHandler;
import gov.va.vba.rbps.services.ws.client.mapping.awardDoc.service.ReadAwardDocDataResponse;
import gov.va.vba.rbps.services.ws.client.mapping.awards.adjustmentDecision.ReadRetiredPayDecnResponse;
import gov.va.vba.rbps.services.ws.client.mapping.awards.amend.DependencyDecisionResultVO;
import gov.va.vba.rbps.services.ws.client.mapping.awards.amend.ProcessAwardDependentResponse;
import gov.va.vba.rbps.services.ws.client.mapping.awards.service.ReadCurrentAndProposedAwardResponse;
import gov.va.vba.rbps.services.ws.client.mapping.share.familytree.FindDependentsResponse;
import gov.va.vba.rbps.services.ws.client.mapping.share.familytree.FindFiduciaryResponse;
import gov.va.vba.vbmsc.services.ws.client.mapping.awardData.service.AddressEFTVO;
import gov.va.vba.vbmsc.services.ws.client.mapping.awardData.service.AddressPostalVO;
import gov.va.vba.vbmsc.services.ws.client.mapping.awardData.service.GetPersonProfileResponse;
import gov.va.vba.vbmsc.services.ws.client.mapping.awardData.service.HParticipantVO;
import gov.va.vba.vbmsc.services.ws.client.mapping.awardData.service.HPoaDetailVO;

public class EP130ClaimPostProcessor {

	private static Logger logger = Logger.getLogger(EP130ClaimPostProcessor.class);
	private static String lineSep = "\n\r";

	private LogUtils logUtils = new LogUtils(logger, true);

	private FindFiduciaryWSHandler findFiduciaryWSHandler;
	private FiduciaryPopulator fiduciaryPopulator;
	private FindDependentsWSHandler findDependentsWSHandler;
	private ProcessAwardDependentWSHandler processAwardDependentWSHandler;
	private ClaimProcessorHelper claimProcessorHelper;
	private GeneratePdf generatePdf;
	private AwardPrintSaver awardPrintSaver;
	private CorporateParticpantIdPopulator participantIdPopulator;
	private UpdateBenefitClaimDependentsWSHandler updateBenefitClaimDependentsWSHandler;
	private ReadCurrentAndProposedAwardWSHandler readCurrentAndProposedAwardWSHandler;
	private ClearBenefitClaimWSHandler clearBenefitClaimWSHandler;
	private ProcessMilitaryPay processMilitaryPay;
	private ReadAwardDocDataWSHandler readAwardDocDataWSHandler;
	private GetPersonProfileWSHandler getPersonProfileWSHandler;
	private RbpsDocGenGenstoreService rbpsDocGenGenstoreService;

	private String NO_LONGER_30_OR_HIGHER_MESSAGE = "RBPS letter failed to generate; Veteran is no longer 30% or higher and is not eligible to add dependents to award.  "
			+ "Please send notification letter to Veteran and clear EP";
	private String NO_RATING_EFFECTIVE_DATE = "RBPS letter failed to generate; Veteran is under 30% and is not eligible to add dependents to award.  "
			+ "Please send notification letter to Veteran and clear EP.";

	// reinstated
	public EP130ClaimPostProcessor() {
	}

	public void postProcess(RbpsRepository repository) {

		logUtils.debugEnter(repository);

		try {
			if (repository.hasRuleExceptionMessages()) {
				// update claim label

				reportErrorConditionAndSendToManual(null, repository);
				return;
			}
			/*
			 * logger.debug("repository.getPoaOrganizationName() = "
			 * +repository.getPoaOrganizationName()); if
			 * (repository.getPoaOrganizationName() != null &&
			 * !repository.getPoaOrganizationName().isEmpty()) {
			 * setParticipantMailingAddressDetails(repository); }
			 */
			// going to call this method for all vet as this address is used in
			// award print
			setParticipantMailingAddressDetails(repository);
			logger.debug("after calling setParticipantMailingAddressDetails");

			processFindFiduciary(repository);
			processReadCurrentAndProposedAward(repository);
			sendToAwardsAndGenerateLetter(repository);
		} finally {

			logUtils.debugExit(repository);
		}
	}

	private void sendToAwardsAndGenerateLetter(RbpsRepository repository) {

		if (repository.getVeteran().getServiceConnectedDisabilityRating() < 30) {

			processForDenial(repository, "");
			return;
		}

		// SR# 723083
		if (repository.getVeteran().getRatingEffectiveDate() == null) {

			processForDenial(repository, NO_RATING_EFFECTIVE_DATE);
			return;
		}

		// SR# 723083
		if (repository.getVeteran().getServiceConnectedDisabilityRating() >= 30) {

			// There should be at least one dependent on the claim with event
			// date set by Rules and formerly not on Award
			// for the claim to be sent to isClaimToBeSentForAwardsLetter()
			if (thereIsAtleastOneDependentWithRulesAward(repository)) {

				if (!isClaimToBeSentForAwardsLetter(repository)) {

					// 12/11/2020:
					// If claim made it here, then dependent is going to be
					// rejected because the dates they're being added on,
					// the vet is less than 30%. If claim is Pension and Process
					// Pension is turned on though, go to manual.
					// instead of rejecting dependent.
					if (repository.getVeteran().isPensionAward() && repository.isProcessPensions()) {
						repository.getRuleExceptionMessages().addException(
								"Auto Dependency Processing Reject Reason - Veterans Pension or Survivor Award with under 30 Percent during award dates, please process manually.");
						reportErrorConditionAndSendToManual(null, repository);
						return;
					}

					logger.debug("***EP130ClaimPostProcessor**** Claim to be sent for Denial");
					repository.setVeteranAbove30AndDenial(true);
					processForDenial(repository, NO_LONGER_30_OR_HIGHER_MESSAGE);
					return;
				}
			}
		}

		// addMissingDependentsToCorporate(repository);
		// populateDependentsWithCorporateParticipantId(repository);

		ProcessAwardDependentResponse response = processAward(repository);
		setAwardAuthorizedDate(response,repository);

		for (DependencyDecisionResultVO resultVO : response.getReturn().getAwardSummary().getDependencySummary()) {
			logger.debug(resultVO.getFirstName() + " is current claim: " + resultVO.getCurrentClaim());
		}

		repository.getVeteran().setIsPensionAward(response.getReturn().isPensionAward());
		logger.debug("after setIsPensionAward(response.getReturn().isPensionAward");
		processMilitaryPay(response, repository);
		logger.debug("after processMilitaryPay");
		// new method call for Severance Pay and Separation pay 
		getSeveranceAndSeparationPay(repository);
		generateLetter(response, repository);

		/**
		 * 647228/298341, Completed Claims with Reject Claim Labels (RTC 298341)
		 * - ESCP 545 When the letter is successfully generated, the PROC is set
		 * to Ã¯Â¿Â½CompleteÃ¯Â¿Â½. The claim is Closed using the
		 * same process as PCLR.
		 **/
		logUtils.log("Letter Success ", repository);
		claimProcessorHelper.updateClaimLabelForSpouseRemoval(repository);
		closeClaimForSuccessfulLetter(repository);
		repository.setClaimProcessingState("Pass - AWARDS/Letter");
		claimProcessorHelper.updateClaimStatus(CLAIM_STATUS_PROCESSING_COMPLETE, repository);

		// TODO change claim label and location
		// if claim is pension, change claim label to pension
		if (repository.isProcessPensions() && repository.getVeteran().isPensionAward()) {
			claimProcessorHelper.updateClaimLabel(repository);
			claimProcessorHelper.updateClaimLocationToPMC(repository);
		} /*else {
		
			// check if the spouse removal then update claim label
			claimProcessorHelper.updateClaimLabelForSpouseRemoval(repository);
		}
*/
	}

	// moving this code over here for use authorizedDate in this class also.
	private void setAwardAuthorizedDate(final ProcessAwardDependentResponse response, final RbpsRepository repository) {

		XMLGregorianCalendar propertyValue = (XMLGregorianCalendar) NullSafeGetter.getAttribute(response,
				"return.awardSummary.awardEventSummary.authorizedDate");

		if (propertyValue != null) {

			Date authorizedDate = SimpleDateUtils.xmlGregorianCalendarToDay(propertyValue);

			repository.setAwardAuthorizedDate(authorizedDate);
		}

	}

	// SR# 723083
	private void processForDenial(RbpsRepository repository, String errorMessage) {

		logger.debug("***EP130ClaimPostProcessor**** In Process for Denial");

		// TODO change claim label and location
		// if claim is pension, change claim label to pension
		if (repository.isProcessPensions() && repository.getVeteran().isPensionAward()) {
			claimProcessorHelper.updateClaimLabel(repository);
			claimProcessorHelper.updateClaimLocationToPMC(repository);
		} else {
			claimProcessorHelper.updateClaimLabelToRejectVersion(repository);
		}

		processLessThan30RatingDenial(repository, errorMessage);

		repository.setClaimProcessingState("Pass - AWARDS/Letter");
		// this just to update only on vnp
		claimProcessorHelper.updateClaimStatus(CLAIM_STATUS_PROCESSING_COMPLETE, repository);
	}

	// SR# 723083
	public boolean thereIsAtleastOneDependentWithRulesAward(RbpsRepository repository) {

		boolean spousePresent = false;
		boolean childrenPresent = false;
		Spouse spouse = null;
		// if spouse and children are empty, claim should be sent to awards
		if ((repository.getVeteran().getCurrentMarriage() != null)
				&& (repository.getVeteran().getCurrentMarriage().getMarriedTo() != null)) {

			spouse = repository.getVeteran().getCurrentMarriage().getMarriedTo();
		}

		if ((spouse != null) && (spouse.getAward() != null)) {

			spousePresent = true;
		}

		if ((spouse != null) && (spouse.isOnCurrentAward())) {
			logger.debug("***EP130ClaimPostProcessor**** Spouse on current award");
			spousePresent = false;
		}

		logger.debug("***EP130ClaimPostProcessor**** spousePresent " + spousePresent);

		if (((repository.getVeteran().getChildren() != null) && (!repository.getVeteran().getChildren().isEmpty()))) {
			boolean childPresent = false;
			for (Child child : repository.getVeteran().getChildren()) {

				if (child.getAward() != null) {
					// the disability is already about 30% only check child
					// award.
					if (child.getAward().getDependencyDecisionType().getCode().equals("RATNHEL")
							&& child.getAward().getDependencyStatusType().getCode().equals("NAWDDEP")) {
						logger.debug("***EP130ClaimPostProcessor**** Child " + child.getFirstName()
								+ " setVeteranAbove30AndDenial *****");
						repository.setVeteranAbove30AndDenial(true);
					}
					childPresent = true;
				}

				if (child.getMinorSchoolChildAward() != null) {

					childPresent = true;
				}

				if (child.isOnCurrentAward()) {
					logger.debug("***EP130ClaimPostProcessor**** Child " + child.getFirstName() + "on current award");
					childPresent = false;
				}

				if (isChildWith674(child)) {

					logger.debug("***EP130ClaimPostProcessor**** Child " + child.getFirstName() + "has 674 form");
					childPresent = true;
				}

				logger.debug(
						"***EP130ClaimPostProcessor**** Child " + child.getFirstName() + " present " + childPresent);

				childrenPresent = childrenPresent || childPresent;
			}
		}

		logger.debug("***EP130ClaimPostProcessor**** Children present " + childrenPresent);

		return (spousePresent || childrenPresent);
	}

	// SR# 723083
	public boolean isClaimToBeSentForAwardsLetter(RbpsRepository repository) {

		boolean spouseToAward = true;
		boolean childrenToAward = true;

		Veteran veteran = repository.getVeteran();
		Marriage marriage = veteran.getCurrentMarriage();
		List<Child> children = veteran.getChildren();
		spouseToAward = spouseToBeSentToAwards(marriage, repository);
		childrenToAward = childrenToBeSentToAwards(children, repository);

		return (spouseToAward || childrenToAward);
	}

	// SR# 723083
	private boolean spouseToBeSentToAwards(Marriage marriage, RbpsRepository repository) {

		boolean spouseToAward = true;

		if ((marriage != null) && (marriage.getMarriedTo() != null)) {

			Spouse spouse = marriage.getMarriedTo();
			Award spouseAward = spouse.getAward();

			if (spouseAward != null) {

				Date eventDate = SimpleDateUtils.getOmnibuzzedDate(spouseAward.getEventDate());
				Date lessThan30EffectiveDateAfterLatestEffectiveDate = repository
						.getLessThan30EffectiveDateAfterLatestEffectiveDate();

				// JR - 09-04-2018 - SR # 813152 - check each rating >= 30% to
				// see if it is effective for this spouse
				Date endDate = spouseAward.getEndDate();

				logger.debug("Spouse decision type = " + spouseAward.getDependencyDecisionType() + ", status type = "
						+ spouseAward.getDependencyStatusType());
				logger.debug("spouse omnibuzzed event date = " + eventDate
						+ ", lessThan30EffectiveDateAfterLatestEffectiveDate ="
						+ lessThan30EffectiveDateAfterLatestEffectiveDate);

				// if end date is null, use today as the end date. it should be
				// null for spouse
				if (endDate == null) {
					endDate = new Date();

					logger.debug("Spouse decision end date is NULL, setting End Date = today, " + endDate);
				} else {
					logger.debug("Spouse decision end date is not NULL,  End Date = " + endDate);
				}

				spouseToAward = is30PercentRatingEffectiveForDependent(eventDate, endDate, repository);

				/*
				 * if ( lessThan30EffectiveDateAfterLatestEffectiveDate == null)
				 * {
				 * 
				 * return true; }
				 * 
				 * if ( SimpleDateUtils.isOnOrAfter(eventDate,
				 * lessThan30EffectiveDateAfterLatestEffectiveDate) ) {
				 * 
				 * logger.debug("***EP130ClaimPostProcessor**** Spouse " +
				 * spouse.getFirstName() +
				 * " event date after Veteran became less than 30%" );
				 * spouseToAward = false; }
				 */
			}

			else {
				spouseToAward = false;
				logger.debug("***EP130ClaimPostProcessor**** spouseToBeSentToAwards - " + spouse.getFirstName()
						+ " award is NULL");
			}

		} else {
			spouseToAward = false;
		}

		logger.debug("***EP130ClaimPostProcessor**** spouse to award " + spouseToAward);
		return spouseToAward;
	}

	// SR# 723083
	private boolean childrenToBeSentToAwards(List<Child> children, RbpsRepository repository) {

		boolean childrenToAward = false;

		if ((children != null) && (!children.isEmpty())) {

			for (Child child : children) {

				boolean childToAward = childEligibleForAward(child, repository);
				logger.debug(
						"***EP130ClaimPostProcessor**** Child " + child.getFirstName() + " to award " + childToAward);

				childrenToAward = childrenToAward || childToAward;
			}
		}

		logger.debug("***EP130ClaimPostProcessor**** Children to award " + childrenToAward);
		return childrenToAward;
	}

	// SR# 723083
	private boolean childEligibleForAward(Child child, RbpsRepository repository) {

		Award childAward = child.getAward();
		Award minorToSchoolChildAward = child.getMinorSchoolChildAward();
		boolean childToAward = false;
		boolean minorToSchoolAward = false;

		if (childAward == null) {
			logger.debug("***EP130ClaimPostProcessor**** childEligibleForAward - " + child.getFirstName()
					+ " award is NULL");
		} else {
			childToAward = isChildToAward(childAward, child, repository);
		}

		if (minorToSchoolChildAward == null) {
			logger.debug("***EP130ClaimPostProcessor**** childEligibleForAward - " + child.getFirstName()
					+ " MinorSchoolChildAward is NULL");
		} else {
			minorToSchoolAward = isChildToAward(minorToSchoolChildAward, child, repository);
		}

		logger.debug("***EP130ClaimPostProcessor**** Child childToAward " + child.getFirstName() + " " + childToAward);
		logger.debug("***EP130ClaimPostProcessor**** Child minorToSchoolAward " + child.getFirstName() + " "
				+ minorToSchoolAward);
		return childToAward || minorToSchoolAward;
	}

	private boolean isChildToAward(Award childAward, Child child, RbpsRepository repository) {

		boolean childToAward = true;
		if (childAward != null) {

			Date eventDate = SimpleDateUtils.getOmnibuzzedDate(childAward.getEventDate());
			Date lessThan30EffectiveDateAfterLatestEffectiveDate = repository
					.getLessThan30EffectiveDateAfterLatestEffectiveDate();

			// JR - 09-04-2018 - SR # 813152 - check each rating >= 30% to see
			// if it is effective for this child
			Date endDate = childAward.getEndDate();

			String decTC = childAward.getDependencyDecisionType().toString();
			DependentDecisionType decType = childAward.getDependencyDecisionType();
			DependentStatusType statusType = childAward.getDependencyStatusType();

			// for minor child, use omni-bused 18th birth day as the end date.
			// for school child, use end date
			if (decType == DependentDecisionType.ELIGIBLE_MINOR_CHILD
					&& statusType.equals(DependentStatusType.MINOR_CHILD)) {

				Date birthday18 = SimpleDateUtils.getDate18(child);
				endDate = birthday18;
			} else if (decType == DependentDecisionType.SCHOOL_ATTENDANCE_BEGIN_DATE
					&& statusType.equals(DependentStatusType.SCHOOL_CHILD)) {
				if (endDate != null) {
					endDate = SimpleDateUtils.getOmnibuzzedDate(endDate);
				} else {
					// end date is null for a school child
					throw new RbpsRuntimeException(
							"school child " + child.getFirstName() + " " + child.getLastName() + " has no end date");
				}
			}

			logger.debug("Child decision type = " + childAward.getDependencyDecisionType() + ", status type = "
					+ childAward.getDependencyStatusType());
			logger.debug(
					"child omnibuzzed event date = " + eventDate + ", lessThan30EffectiveDateAfterLatestEffectiveDate ="
							+ lessThan30EffectiveDateAfterLatestEffectiveDate);

			// if end date is null, use today as the end date
			if (endDate == null) {
				endDate = new Date();

				logger.debug("child decision end date is NULL, setting End Date = today, " + endDate);
			} else {
				logger.debug("child decision end date is not NULL,  End Date = " + endDate);
			}

			if (endDate == null)
				endDate = new Date();
			/*
			 * if ( lessThan30EffectiveDateAfterLatestEffectiveDate == null) {
			 * 
			 * logger.debug(
			 * "***EP130ClaimPostProcessor**** Child  LessThan30EffectiveDateAfterLatestEffectiveDate not present"
			 * ); return true; }
			 * 
			 * logger.debug("***EP130ClaimPostProcessor**** Child " +
			 * child.getFirstName() +
			 * " LessThan30EffectiveDateAfterLatestEffectiveDate " +
			 * lessThan30EffectiveDateAfterLatestEffectiveDate.toString() );
			 * logger.debug("***EP130ClaimPostProcessor**** Child " +
			 * child.getFirstName() + " OmnibuzzedDate " + eventDate.toString()
			 * );
			 * 
			 * if ( SimpleDateUtils.isOnOrAfter(eventDate,
			 * lessThan30EffectiveDateAfterLatestEffectiveDate) ) { childToAward
			 * = false; logger.debug("***EP130ClaimPostProcessor**** Child " +
			 * child.getFirstName() +
			 * " event date after Veteran became less than 30%" ); }
			 */
			/*
			 * Remove the changes for end date validation for now.
			 * if(!verifyEndDate(statusType, child, eventDate, endDate)) {
			 * childToAward = false; } else { childToAward =
			 * is30PercentRatingEffectiveForDependent(eventDate, endDate,
			 * repository); }
			 */
			childToAward = is30PercentRatingEffectiveForDependent(eventDate, endDate, repository);
		} else {
			childToAward = false;
			logger.debug("***EP130ClaimPostProcessor**** Child " + child.getFirstName() + " has no award set by Rules");
		}

		logger.debug("***EP130ClaimPostProcessor**** Child isChildToAward" + child.getFirstName() + " " + childToAward);
		return childToAward;
	}

	// JR - 09-04-2018 - SR # 813152 - check if 30 percent rating is active for
	// the dependent
	boolean is30PercentRatingEffectiveForDependent(Date eventDate, Date endDate, RbpsRepository repository) {

		TreeMap<Date, Integer> ratingMap = repository.getRatingMap();

		Date ratingOnOrBeforeEventDate = null;
		Date ratingOnOrAfterEventDate = null;
		Integer percentOnOrBeforeEventDate = null;
		Integer percentOnOrAfterEventDate = null;
		boolean found30PercentRating = false;

		logger.debug(" is30PercentRatingEffectiveForDependent: eventDate = " + eventDate + ", endDate = " + endDate);

		for (Map.Entry<Date, Integer> entry : ratingMap.entrySet()) {
			Date key = entry.getKey();
			Integer percent = entry.getValue();
			logger.debug(" rating date = " + key + " percent = " + percent);
			if (SimpleDateUtils.isOnOrBefore(key, eventDate)) {
				// found a rating before event date
				ratingOnOrBeforeEventDate = key;
				percentOnOrBeforeEventDate = percent;
			} else if (SimpleDateUtils.isOnOrAfter(key, eventDate)) {
				// found a rating after event date
				ratingOnOrAfterEventDate = key;
				percentOnOrAfterEventDate = percent;

				if (percentOnOrAfterEventDate >= 30) {
					logger.debug(" found 30% rating after event date, rating date = " + ratingOnOrAfterEventDate
							+ " percent = " + percentOnOrAfterEventDate);
					break;
				}
			}
		}
		// Fortify Null Dereferences fix for the logging statements.
		logger.debug("lsc - before Fortify Null Dereferences fix");
		if (ratingOnOrBeforeEventDate != null && percentOnOrBeforeEventDate != null) {
			logger.debug("ratingOnOrBeforeEventDate = " + ratingOnOrBeforeEventDate + ", percentOnOrBeforeEventDate = "
					+ percentOnOrBeforeEventDate);
		}
		if (ratingOnOrAfterEventDate != null && percentOnOrAfterEventDate != null) {
			logger.debug(" ratingOnOrAfterEventDate = " + ratingOnOrAfterEventDate + ", percentOnOrAfterEventDate = "
					+ percentOnOrAfterEventDate);
		}
		// check if the last rating before event date >= 30%
		if (ratingOnOrBeforeEventDate != null && percentOnOrBeforeEventDate >= 30) {
			// last rating before event date >= 30%
			found30PercentRating = true;
			logger.debug(" last rating on " + ratingOnOrBeforeEventDate + " before event date >= 30%");
			logger.debug("lsc - after Fortify Null Dereferences fix");
		}
		// check if rating after event date and before end date >= 30%
		else if (ratingOnOrAfterEventDate != null && percentOnOrAfterEventDate >= 30) {
			// no rating before event date or last rating before event date <=
			// 30%, check if rating after event date is 30% and before end date
			if (SimpleDateUtils.isOnOrAfter(endDate, ratingOnOrAfterEventDate)) {
				found30PercentRating = true;
				logger.debug(
						" no last rating before event date or it is <= 30% but rating after event date and before end date is >= 30%");
			}
		}

		return found30PercentRating;
	}

	// SR# 723083
	public boolean isChildWith674(final Child child) {

		boolean result = child.getForms().contains(FormType.FORM_21_674);
		return result;
	}

	private void processLessThan30RatingDenial(RbpsRepository repository, String errorMessage) {

		try {
			generatePdf.generateLetterAndCsv(repository, null);
		} catch (Exception ex) {

			logger.debug("***EP130ClaimPostProcessor******processLessThan30RatingDenial " + ex.getMessage());
			/**
			 * 647228/298341, Completed Claims with Reject Claim Labels (RTC
			 * 298341) - ESCP 545 Commented out throwing exception and calling
			 * the method reportErrorAtLetterGenerationToManual
			 **/
			logger.debug("lsc - before call to reportErrorAtLetterGenerationToManual with new param");
			reportErrorAtLetterGenerationToManual(repository, errorMessage, "letter", null);
			logger.debug("lsc - after call to reportErrorAtLetterGenerationToManual with new param");
			// throw new RbpsRuntimeException( "Error Letter failed to generate
			// during the letter generation process" );
		}

		closeClaimForVeteranDenial(repository);
	}

	private void processReadCurrentAndProposedAward(RbpsRepository repository) {
		ReadCurrentAndProposedAwardResponse response = readCurrentAndProposedAwardWSHandler
				.readCurrentAndProposedAward(repository);
		ReadCurrentAndProposedAwardPopulator.processReadCurrentAndProposedAwardResponse(repository, response);
		// ccr 1789 setting the payment type to veterain to use latter in letter

		if (response != null && response.getCurrAndPropAwardResponse() != null
				&& response.getCurrAndPropAwardResponse().getAwardRecipientList() != null
				&& response.getCurrAndPropAwardResponse().getAwardRecipientList().getAwardRecipient() != null
				&& !response.getCurrAndPropAwardResponse().getAwardRecipientList().getAwardRecipient().isEmpty()) {
			if (response.getCurrAndPropAwardResponse().getAwardRecipientList().getAwardRecipient().get(0)
					.getParticipantPaymentAddressID() != null) {
				repository.getVeteran().setPaymentType(RbpsConstants.CHECK_PAYMENT);
			} else if (response.getCurrAndPropAwardResponse().getAwardRecipientList().getAwardRecipient().get(0)
					.getParticipantDepositAccountID() != null) {
				repository.getVeteran().setPaymentType(RbpsConstants.EFT_PAYMENT);
			}
		}
		logger.debug("***EP130ClaimPostProcessor******Veteran.paymettype" + repository.getVeteran().getPaymentType());
	}

	private void closeClaimForVeteranDenial(RbpsRepository repository) {
		/**
		 * 647228/298341, Completed Claims with Reject Claim Labels (RTC 298341)
		 * - ESCP 545
		 **/
		logUtils.log("In closeClaimForVeteranDenial ", repository);
		clearBenefitClaimWSHandler.call(repository);
	}

	/**
	 * 647228/298341, Completed Claims with Reject Claim Labels (RTC 298341) -
	 * ESCP 545 When the letter is successfully generated, the PROC is set to
	 * Ã¯Â¿Â½CompleteÃ¯Â¿Â½. The claim is Closed using the same
	 * process as PCLR.
	 **/
	public void closeClaimForSuccessfulLetter(RbpsRepository repository) {
		logUtils.log("In closeClaimForSuccessfulLetter ", repository);
		clearBenefitClaimWSHandler.call(repository);
	}

	private void processMilitaryPay(ProcessAwardDependentResponse response, RbpsRepository repository) {

		if (response == null) {

			return;
		}
		processMilitaryPay.processMilitaryPayInformation(repository, response);
	}

	private void getSeveranceAndSeparationPay(RbpsRepository repository) {
		Connection conn = null;
		Long vetId = repository.getVeteran().getCorpParticipantId();

		try {
			conn = ConnectionFactory.getConnection("jdbc/wbrbps/CorpDB");

			logger.debug("Calling ws_award_data_prc_.getVetWithholdingData  with conn" + conn.toString());
			CallableStatement cs = conn
					.prepareCall("{call ws_rbps_prc.getVetWithholdingData(?,?,?,?,?,?,?,?,?,?)}");
			cs.setLong(1, vetId);
			cs.setLong(2, vetId);
			cs.setString(3, RbpsConstants.AWARD_TYPE);
			logger.debug("call ws_rbps_prc.getVetWithholdingData set vetid");
			// cs.registerOutParameter(3, Types.VARCHAR);
			cs.registerOutParameter(4, Types.VARCHAR);
			cs.registerOutParameter(5, Types.VARCHAR);
			cs.registerOutParameter(6, Types.VARCHAR);
			cs.registerOutParameter(7, Types.VARCHAR);
			cs.registerOutParameter(8, Types.VARCHAR);
			cs.registerOutParameter(9, Types.VARCHAR);
			cs.registerOutParameter(10, Types.VARCHAR);
			logger.debug("call ws_rbps_prc.getVetWithholdingData registerOutParameter");
			logger.debug("statement = " + cs.toString());
			cs.execute();
			/*
			 * PROCEDURE getVetWithholdingData (pi_vet_id IN NUMBER ,pi_bene_id
			 * IN NUMBER ,pi_award_type_cd IN VARCHAR2 ,po_return_text OUT
			 * VARCHAR2 ,po_withholding_type OUT VARCHAR2 ,po_has_retired_pay
			 * OUT VARCHAR2 ,po_has_separation_pay OUT VARCHAR2
			 * ,po_has_severance_pay OUT VARCHAR2
			 * ,po_separation_withholding_para OUT VARCHAR2
			 * ,po_severance_withholding_para OUT VARCHAR2 )
			 */
			String withHoldingType = cs.getString(5);
			repository.getVeteran().setWithholdingType(withHoldingType);// even if it is null set it on veteran
			String return_text = cs.getString(4);
			if (withHoldingType == null || withHoldingType.equalsIgnoreCase("None")) {
				logger.debug("Calling ws_rbps_prc.getVetWithholdingData  returned none with return text"
						+ return_text);
				return;
			} else if (withHoldingType != null && withHoldingType.equalsIgnoreCase("Manual")) {
				logger.debug("Calling ws_rbps_prc.getVetWithholdingData  returned Manual with return text"
						+ return_text);
				String message = "Auto Dependency Processing Reject Reason -RBPS could not create withholding paragraph for letter, ";
				String awardAuthorizedDateMessage = message + getAwardAuthDateMessage(repository, "letter");
				logger.debug("awardAuthorizedDateMessage" + awardAuthorizedDateMessage);
				throw new RbpsRuntimeException(awardAuthorizedDateMessage);
			} else {
				if (cs.getString(6).equalsIgnoreCase("Y")) {
					repository.getVeteran().setHasRetiredPay(true);
				}
				if (cs.getString(7).equalsIgnoreCase("Y")) {
					repository.getVeteran().setHasSeparationPay(true);
				}
				if (cs.getString(8).equalsIgnoreCase("Y")) {
					repository.getVeteran().setHasSeverancePay(true);
				}
				String separationWithholdingPara = cs.getString(9);
				String severanceWithholdingPara = cs.getString(10);

				repository.getVeteran().setSeparationWithholdingPara(separationWithholdingPara);
				repository.getVeteran().setSeveranceWithholdingPara(severanceWithholdingPara);
				logger.debug("repository.getVeteran().getSeparationWithholdingPara "
						+ repository.getVeteran().getSeparationWithholdingPara());
				logger.debug(" repository.getVeteran().getSeveranceWithholdingPara "
						+ repository.getVeteran().getSeveranceWithholdingPara());
			}
		} catch (SQLException e) {
			logger.debug("Call to ws_rbps_prc.getVetWithholdingData procedure failed " + e.getStackTrace());
			throw new RbpsRuntimeException(
					"Call to ws_rbps_prc.getVetWithholdingData procedure failed " + e.getMessage());
		} catch (NamingException e) {
			logger.debug("Call to ws_rbps_prc.getVetWithholdingData procedure failed " + e.getStackTrace());
			
			throw new RbpsRuntimeException("Failed to retrieve datasource: " + e.getMessage());
		} catch (Exception e) {
			String detailedMessage = "Call to ws_rbps_prc.getVetWithholdingData procedure failed ";
			logger.debug("Call to ws_rbps_prc.getVetWithholdingData procedure failed " + e.getStackTrace());
			throw new RbpsRuntimeException(e.getMessage());
		} finally {
			if (conn != null) {
				try {
					conn.close();			
				} catch (SQLException e) {
					throw new RbpsRuntimeException("Unable to close connection: " + e.getMessage());
				}
			}
		}

	}
   private String getAwardAuthDateMessage(RbpsRepository repository, String letterName) {

		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM-dd-yyyy");
		Date awd = repository.getAwardAuthorizedDate();
		if (null != awd) {
			String awardAuthorizedDate = DATE_FORMAT.format(repository.getAwardAuthorizedDate());
			logger.debug("r call with new param in getAwardAuthorizedDateMessage with awardAuthorizedDate"+awardAuthorizedDate);
			return "please create " + letterName + " for RBPS award authorized " + awardAuthorizedDate;
		}
		
		logger.debug("awardAuthorizedDate is null ");
	
		 return "please create " + letterName + " for RBPS";
	}
	public void generateLetter(final ProcessAwardDependentResponse response, RbpsRepository repository) {
		// CCR2337 fix. throw an exception indicating a generateLetter failed.
		try {
			generatePdfAndCsv(response, repository);

			logger.debug("after  calling generatePdfAndCsv(response, repository)");
		} catch (HttpClientErrorException ex) {
			// To catch the DocgenService Exception
			logger.debug("- before generateLetter, first ex with new param");
			logUtils.log("Exception occurred at letter generation:\n" + "Exception:" + ex.getClass() + "\n" + "Cause:"
					+ ex.getCause() + "\n" + "StatusCode:" + ex.getStatusCode().toString() + "\n", repository);
			reportErrorAtLetterGenerationToManual(repository, "", "letter", ex.getStatusCode().toString());
			logger.debug(" - after generateLetter first ex with new param");

		} catch (Exception ex) {
			logger.debug("- before generateLetter 2nd  ex with new param");
			ex.printStackTrace();

			/**
			 * 647228/298341, Completed Claims with Reject Claim Labels (RTC
			 * 298341) - ESCP 545 Commented out throwing exception and calling
			 * the method reportErrorAtLetterGenerationToManual
			 **/
			logUtils.log("Exception occurred at letter generation RTC  298341:\n" + "Exception:" + ex.getClass() + "\n"
					+ "Cause:" + ex.getCause() + "\n", repository);
			reportErrorAtLetterGenerationToManual(repository, "", "letter", null);
			logger.debug("- after generateLetter 2nd  ex with new param");
			// throw new RbpsRuntimeException( "Error Letter failed to generate
			// during the letter generation process" );
		}
		logger.debug(" - before save Award Print");

		saveAwardPrint(response, repository);
		logger.debug(" - after save Award Print");
	}

	/**
	 * Get PersonProfile from veteran CorpParticipantId Set Participant Mailing
	 * Address Details
	 * 
	 * @param repository
	 */
	private void setParticipantMailingAddressDetails(RbpsRepository repository) {
		logger.debug(" - at beginning of setParticipantMailingAddressDetails");

		logger.debug(" - getPersonProfileWSHandler : " + getPersonProfileWSHandler);
		GetPersonProfileResponse getPersonProfileResponse = getPersonProfileWSHandler.getPersonProfile(repository);
		logger.debug("- after call to getPersonProfileWSHandler.getPersonProfile " + getPersonProfileResponse);

		List<AddressPostalVO> poaAddressPostalVOList = new ArrayList<AddressPostalVO>();
		List<AddressPostalVO> addressPostalVOList = new ArrayList<AddressPostalVO>();
		List<HPoaDetailVO> hPoaDetailVOList = new ArrayList<HPoaDetailVO>();
		AddressEFTVO addressEFTVO = null;

		logger.debug(" - before first if of setParticipantMailingAddressDetails");

		if (getPersonProfileResponse != null && getPersonProfileResponse.getPersonProfileResponse() != null) {
			List<HParticipantVO> hParticipantVOList = getPersonProfileResponse.getPersonProfileResponse()
					.getPersons() != null
							? getPersonProfileResponse.getPersonProfileResponse().getPersons().getParticipant() : null;
			if (hParticipantVOList != null && hParticipantVOList.size() > 0) {
				logger.debug(" - hParticipantVOList is not empty");
				hPoaDetailVOList = hParticipantVOList.get(0).getHPoaDetailList() != null
						? hParticipantVOList.get(0).getHPoaDetailList().getHPoaDetailVO() : null;
			}
		}

		if (hPoaDetailVOList != null && hPoaDetailVOList.size() > 0) {
			logger.debug("lsc - hPoaDetailVOList is not empty");
			for (HPoaDetailVO hPoaDetailVO : hPoaDetailVOList) {
				if (hPoaDetailVO.getPoaTypeDesc() != null
						&& (hPoaDetailVO.getPoaTypeDesc().equalsIgnoreCase("POA Attorney")
								|| hPoaDetailVO.getPoaTypeDesc().equalsIgnoreCase("POA Agent"))) {
					logger.debug("lsc - hPoaDetailVO.getPoaTypeDesc() is POA Attorney OR POA Agent");
					if (repository.getPoaOrganizationName() != null && !repository.getPoaOrganizationName().isEmpty()
							&& hPoaDetailVO.getAddressPostal() == null) {
						String message = "Auto Dependency Processing Reject Reason -POA Attorney/Agent has no address";
						throw new RbpsRuntimeException(message);
					} else {
						poaAddressPostalVOList.add(hPoaDetailVO.getAddressPostal());
					}

				}

			} // getting POA mailing address
			if (poaAddressPostalVOList != null && poaAddressPostalVOList.size() > 0) {
				logger.debug("lsc - addressPostalVOList is not empty");
				for (AddressPostalVO addressVO : poaAddressPostalVOList) {
					if (addressVO != null && addressVO.getAddressType() != null
							&& addressVO.getAddressType().equalsIgnoreCase("Mailing")) {
						DisplayAddressVO participantAddressPostalVO = new DisplayAddressVO();
						participantAddressPostalVO.setDisplayAddressLine1(addressVO.getDisplayAddressLine1());
						participantAddressPostalVO.setDisplayAddressLine2(addressVO.getDisplayAddressLine2());
						participantAddressPostalVO.setDisplayAddressLine3(addressVO.getDisplayAddressLine3());
						participantAddressPostalVO.setDisplayAddressLine4(addressVO.getDisplayAddressLine4());
						participantAddressPostalVO.setDisplayAddressLine5(addressVO.getDisplayAddressLine5());
						participantAddressPostalVO.setDisplayAddressLine6(addressVO.getDisplayAddressLine6());
						participantAddressPostalVO.setDisplayAddressLine7(addressVO.getDisplayAddressLine7());

						repository.setParticipantAddressVO(participantAddressPostalVO);
						logger.debug("Participant Mailing Address Details : " + participantAddressPostalVO);
						break;
					}

				}
			}
		}
		// get payment vet mailing address
		DisplayAddressVO paymentAddressPostalVO = new DisplayAddressVO();
		if (getPersonProfileResponse != null && getPersonProfileResponse.getPersonProfileResponse() != null) {
			List<HParticipantVO> hParticipantVOList = getPersonProfileResponse.getPersonProfileResponse()
					.getPersons() != null
							? getPersonProfileResponse.getPersonProfileResponse().getPersons().getParticipant() : null;

			if (hParticipantVOList != null && hParticipantVOList.size() > 0) {
				logger.debug(" - hParticipantVOList is not empty");
				addressEFTVO = hParticipantVOList.get(0).getHPoaDetailList() != null
						? hParticipantVOList.get(0).getAddressEFT() : null;
				logger.debug(" payment is not EFT ********************************");
				addressPostalVOList = hParticipantVOList.get(0).getHPoaDetailList() != null
						? hParticipantVOList.get(0).getAddressPostalList().getAddressPostalVO() : null;
			}
			if (addressEFTVO != null && addressEFTVO.getBankName() != null) {
				paymentAddressPostalVO.setDisplayAddressLine1(addressEFTVO.getBankName());
				logger.debug("EFT  ******************* - addressEFTVO.getBankName()" + addressEFTVO.getBankName());
				repository.setPaymentAddressVO(paymentAddressPostalVO);
			} else {
				// no eft payment. so get payment address.
				for (AddressPostalVO addressPostalVO : addressPostalVOList) {

					if (addressPostalVO != null && addressPostalVO.getAddressType() != null
							&& addressPostalVO.getAddressType().equalsIgnoreCase("CP Payment")) {
						paymentAddressPostalVO.setDisplayAddressLine1(addressPostalVO.getDisplayAddressLine1());
						paymentAddressPostalVO.setDisplayAddressLine2(addressPostalVO.getDisplayAddressLine2());
						paymentAddressPostalVO.setDisplayAddressLine3(addressPostalVO.getDisplayAddressLine3());
						paymentAddressPostalVO.setDisplayAddressLine4(addressPostalVO.getDisplayAddressLine4());
						paymentAddressPostalVO.setDisplayAddressLine5(addressPostalVO.getDisplayAddressLine5());
						paymentAddressPostalVO.setDisplayAddressLine6(addressPostalVO.getDisplayAddressLine6());
						paymentAddressPostalVO.setDisplayAddressLine7(addressPostalVO.getDisplayAddressLine7());

						repository.setPaymentAddressVO(paymentAddressPostalVO);
						logger.debug("Participant paymentAddressPostalVO Address Details : " + paymentAddressPostalVO);
						break;
					}
				}
			}
			for (AddressPostalVO addressPostalVO : addressPostalVOList) {
				if (addressPostalVO != null && addressPostalVO.getAddressType() != null
						&& addressPostalVO.getAddressType().equalsIgnoreCase("Mailing")) {
					DisplayAddressVO mailingAddressPostalVO = new DisplayAddressVO();
					mailingAddressPostalVO.setDisplayAddressLine1(addressPostalVO.getDisplayAddressLine1());
					mailingAddressPostalVO.setDisplayAddressLine2(addressPostalVO.getDisplayAddressLine2());
					mailingAddressPostalVO.setDisplayAddressLine3(addressPostalVO.getDisplayAddressLine3());
					mailingAddressPostalVO.setDisplayAddressLine4(addressPostalVO.getDisplayAddressLine4());
					mailingAddressPostalVO.setDisplayAddressLine5(addressPostalVO.getDisplayAddressLine5());
					mailingAddressPostalVO.setDisplayAddressLine6(addressPostalVO.getDisplayAddressLine6());
					mailingAddressPostalVO.setDisplayAddressLine7(addressPostalVO.getDisplayAddressLine7());

					repository.setVetMailingAddressVO(mailingAddressPostalVO);
					logger.debug("Participant Mailing Address Details : " + mailingAddressPostalVO);
					break;
				}
			}

		}
	}

	private void reportErrorConditionAndSendToManual(final Throwable ex, RbpsRepository repository) {

		String msg;
		if (ex != null) {

			msg = "Rbps unable to send to awards and create letter.  " + ex.getMessage();
			repository.addValidationMessage("Please develop manually. " + msg);
		} else {

			msg = "Rule Exception(s) - Manual";
		}

		repository.setClaimProcessingState(msg);
		claimProcessorHelper.sendClaimForManualProcessing(repository);
	}

	/**
	 * 647228/298341, Completed Claims with Reject Claim Labels (RTC 298341) -
	 * ESCP 545 Sends message to mapD if RBPS failed at letter generation. If
	 * the letter fails for whatever reason, 1. the PROC is set to
	 * Ã¯Â¿Â½ManualÃ¯Â¿Â½ 2. the claim type is set to REJ but the
	 * claim is left open. 3. A note is recorded Ã¯Â¿Â½RBPS letter failed
	 * to generate, please create letter for RBPS award authorized
	 * xx/xx/xxxxÃ¯Â¿Â½. Where xx/xx/xxxx is the authorization date of the
	 * award event. SR# 723083 - different error messages for denial letters
	 * fail. New arguments added String letterName, String httpStatusCode to
	 * reuse same method for "Letter" and "Award Print" letterName
	 * ("Letter/Award Print) httpStatusCode if Exception occurred at
	 * DocGenService
	 */
	private void reportErrorAtLetterGenerationToManual(RbpsRepository repository, String errorMessage,
			String letterName, String httpStatusCode) {

		// TODO change claim label and location
		// if claim is pension, change claim label to pension
		if (repository.isProcessPensions() && repository.getVeteran().isPensionAward()) {
			claimProcessorHelper.updateClaimLabel(repository);
			claimProcessorHelper.updateClaimLocationToPMC(repository);
		}

		logUtils.log("In reportErrorAtLetterGenerationToManual ", repository);
		String msg = "";

		if (StringUtils.isEmpty(errorMessage)) {

			logger.debug("lsc - at beg of isEmpty(errorMessage) in reportErrorAtLetterGenerationToManual");
			String awardAuthorizedDateMessage = getAwardAuthorizedDateMessage(repository, letterName);
			// letterName ("Letter/Award Print)
			msg += "RBPS " + letterName + " failed to generate, " + awardAuthorizedDateMessage;
			// if Exception occurred at DocGenService
			if (org.apache.commons.lang.StringUtils.isNotBlank(httpStatusCode)) {
				msg += lineSep + "System message: " + httpStatusCode;
				logUtils.debug(msg, repository);
				logger.debug("lsc - at end of isEmpty(errorMessage) in reportErrorAtLetterGenerationToManual");
			}
		} else {
			logger.debug("lsc - in else  of isEmpty(errorMessage) in reportErrorAtLetterGenerationToManual");
			msg += "RBPS letter failed to generate, " + errorMessage;
			logger.debug("lsc - at end of else in isEmpty(errorMessage) in reportErrorAtLetterGenerationToManual");
		}
		// repository.addValidationMessage( msg );
		// repository.setClaimProcessingState( msg );
		// claimProcessorHelper.sendClaimForManualProcessing(repository);

		// check message if it should be sent by email
		if (msg.toLowerCase().contains("notification letter")
				|| msg.toLowerCase().contains("for rbps award authorized")) {
			logger.debug("failed to generate a letter, sending an email");
			EmailSender.addErrorMsg("Reporting Unexpected error in RBPS for letter generation: ",
					"letter failed to generate " + msg);
		}

		throw new RbpsRuntimeException(msg);
	}

	/**
	 * 647228/298341, Completed Claims with Reject Claim Labels (RTC 298341) -
	 * ESCP 545 Formatting awardAuthorizedDate to "MM-dd-yyyy"
	 **/
	private String getAwardAuthorizedDateMessage(RbpsRepository repository, String letterName) {

		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM-dd-yyyy");
		Date awd = repository.getAwardAuthorizedDate();
		if (null != awd) {
			String awardAuthorizedDate = DATE_FORMAT.format(repository.getAwardAuthorizedDate());
			logger.debug("r call with new param in getAwardAuthorizedDateMessage with awardAuthorizedDate"+awardAuthorizedDate);
			return "please create " + letterName + " for RBPS award authorized " + awardAuthorizedDate;
		}
		// return "please create letter for Veteran disability less than 30";
		// SR# 723083
		logger.debug("lsc - before returinging under 30% message ");

		logger.debug("after - before returinging under 30% message ");
		return "Veteran is under 30% and is not eligible to add dependents to award. "
				+ "Please send notification letter to Veteran and clear EP.";
	}
	

	/*
	 * private boolean verifyEndDate(DependentStatusType statusType, Child
	 * child, Date eventDate, Date endDate) throws Exception {
	 * if(endDate.compareTo(eventDate) <= 0) { logger.debug(
	 * "***EP130ClaimPostProcessor**** Child " + child.getFirstName() +
	 * " end date is before event date." ); //return false; throw new Exception(
	 * "Error: End date is before event date ");
	 * 
	 * // for school child we want to compare end date with 18th birthdate }
	 * else if(statusType.equals(DependentStatusType.SCHOOL_CHILD) &&
	 * endDate.compareTo(SimpleDateUtils.getDate18(child)) <= 0) { logger.debug(
	 * "***EP130ClaimPostProcessor**** Child " + child.getFirstName() +
	 * " end date is before child's 18th birthday." ); //return false; throw new
	 * Exception(
	 * "Error: Child is under 18 and school is not labeled High School or Home school "
	 * );
	 * 
	 * } else if(eventDate.compareTo(SimpleDateUtils.getDate23(child)) >= 0) {
	 * logger.debug("***EP130ClaimPostProcessor**** Child " +
	 * child.getFirstName() + " event date is after child's 23rd birthday." );
	 * //return false; throw new Exception(
	 * "Error: Event date is after child's 23rd birthday. "); } return true; }
	 */
	/**
	 * Finds out whether or not the veteran has a fiduciary.
	 */
	public void processFindFiduciary(RbpsRepository repository) {

		FindFiduciaryResponse response = findFiduciaryWSHandler.findFiduciary(repository);
		fiduciaryPopulator.populateFiduciary(response, repository);
	}

	/**
	 * Handles the case where the Rules Engine did not fire an Exception Rule
	 * and therefore we have Dependents to send to AWARDS.
	 */
	public ProcessAwardDependentResponse processAward(RbpsRepository repository) {

		return processAwardDependentWSHandler.processAwardDependents(repository);
	}

	/**
	 * Will take the repository/xom and the output of the awards ws call and
	 * generates an acceptance/denial letter and csv file.
	 *
	 */
	public void generatePdfAndCsv(final ProcessAwardDependentResponse response, RbpsRepository repository) {

		//
		// If we have no dependents to add to the award,
		// then the response will be null.
		//
		if (response == null) {

			return;
		}
		for (DependencyDecisionResultVO resultVO : response.getReturn().getAwardSummary().getDependencySummary()) {
			logger.debug(resultVO.getFirstName() + " is current claim: " + resultVO.getCurrentClaim());
		}
		generatePdf.generateLetterAndCsv(repository, response);
	}

	/**
	 * Will take the repository/xom and the output of the awards ws call and
	 * generates an acceptance/denial letter and csv file.
	 */
	public void saveAwardPrint(final ProcessAwardDependentResponse response, RbpsRepository repository) {

		if (response == null) {

			return;
		}

		try {
			// repository.setAwardEventID(1471591L);
			logger.debug("lsc - at beg of new try in saveAwardPrint");
			repository.setAwardEventID(response.getReturn().getAwardSummary().getAwardEventSummary().getAwardEventID());
			repository.setAwardEventType(
					response.getReturn().getAwardSummary().getAwardEventSummary().getAwardEventType());
			ReadAwardDocDataResponse readAwardDocDataResponse = readAwardDocDataWSHandler.readAwardDocData(repository);

			if (readAwardDocDataResponse == null) {
				return;
			}

			EdocDocument edocDocument = new EdocDocument();
			ReadAwardDocDataResponse.transformToEdocDocument(edocDocument,
					readAwardDocDataResponse.getAwardDocResponse(), repository);

			logger.debug("ReadAwardDocDataResponse is successfully converted into EdocDocument");
			String pdfFileName = awardPrintSaver.generateFileNameWithoutPath(1, repository);
			  String  fileNumber = repository.getVeteran().getFileNumber();
			//rbpsDocGenService.generateAwardPrintAndSave(edocDocument, pdfFileName);
			rbpsDocGenGenstoreService.generateAwardPrintAndSave(repository,edocDocument, pdfFileName,fileNumber);


		//	awardPrintSaver.saveCsvFile(1, repository);
		} catch (HttpClientErrorException ex) {
			// To catch the Docgen Service Exception
			logger.debug("lsc - at beg of 1st catch in new try in saveAwardPrint");
			logUtils.log("Exception occurred at Award Print generation:\n" + "Exception:" + ex.getClass() + "\n"
					+ "Cause:" + ex.getCause() + "\n" + "StatusCode:" + ex.getStatusCode().toString() + "\n",
					repository);
			reportErrorAtLetterGenerationToManual(repository, "", "Award Print", ex.getStatusCode().toString());
			logger.debug("lsc - at end of 1st catch in new try in saveAwardPrint");
		} catch (Exception ex) {
			// To catch other Exception like SOAP service Exception
			logger.debug("lsc - at beg of 2nd catch in new try in saveAwardPrint");
			logUtils.log("Exception occurred at Award Print generation:\n" + "Exception:" + ex.getClass() + "\n"
					+ "Cause:" + ex.getCause() + "\n", repository);
			reportErrorAtLetterGenerationToManual(repository, "", "Award Print", null);
			logger.debug("lsc - at end of 2nd catch in new try in saveAwardPrint");
		}
	}

	// this method is used only from Junit not called any where else
	public void saveAwardPrintJunit(final ReadAwardDocDataResponse response, RbpsRepository repository) {

		if (response == null) {

			return;
		}

		try {
			// repository.setAwardEventID(1471591L);

			EdocDocument edocDocument = new EdocDocument();
			ReadAwardDocDataResponse.transformToEdocDocument(edocDocument, response.getAwardDocResponse(), repository);

			logger.debug("ReadAwardDocDataResponse is successfully converted into EdocDocument");
			String pdfFileName = awardPrintSaver.generateFileNameWithoutPath(1, repository);
			 String  fileNumber = repository.getVeteran().getFileNumber();
				
			rbpsDocGenGenstoreService.generateAwardPrintAndSave(repository,edocDocument, pdfFileName,fileNumber);

			//awardPrintSaver.saveCsvFile(1, repository);
		} catch (HttpClientErrorException ex) {
			// To catch the Docgen Service Exception
			logger.debug("lsc - at beg of 1st catch in new try in saveAwardPrint");
			logUtils.log("Exception occurred at Award Print generation:\n" + "Exception:" + ex.getClass() + "\n"
					+ "Cause:" + ex.getCause() + "\n" + "StatusCode:" + ex.getStatusCode().toString() + "\n",
					repository);
			reportErrorAtLetterGenerationToManual(repository, "", "Award Print", ex.getStatusCode().toString());
			logger.debug("lsc - at end of 1st catch in new try in saveAwardPrint");
		} catch (Exception ex) {
			// To catch other Exception like SOAP service Exception
			logger.debug("lsc - at beg of 2nd catch in new try in saveAwardPrint");
			logUtils.log("Exception occurred at Award Print generation:\n" + "Exception:" + ex.getClass() + "\n"
					+ "Cause:" + ex.getCause() + "\n", repository);
			reportErrorAtLetterGenerationToManual(repository, "", "Award Print", null);
			logger.debug("lsc - at end of 2nd catch in new try in saveAwardPrint");
		}
	}

	/**
	 * Finds XOM kids who are not on awards and not in corporate and adds them
	 * to corporate. Ditto for spouse.
	 */
	private void addMissingDependentsToCorporate(RbpsRepository repository) {

		updateBenefitClaimDependentsWSHandler.updateDependents(repository);
	}

	/**
	 * Get a list of dependents in the corporate database and use the results to
	 * populate the corporate participant ids of the xom kids and spouse.
	 */
	private void populateDependentsWithCorporateParticipantId(RbpsRepository repository) {

		FindDependentsResponse response = findDependentsWSHandler.findDependents(repository);
		participantIdPopulator.populateParticipantIdFromDependents(response, repository);
	}

	/**
	 * @param processAwardDependentWSHandler
	 */
	public void setProcessAwardDependentWSHandler(final ProcessAwardDependentWSHandler processAwardDependentWSHandler) {
		this.processAwardDependentWSHandler = processAwardDependentWSHandler;
	}

	/**
	 * @param claimProcessorHelper
	 */
	public void setClaimProcessorHelper(final ClaimProcessorHelper claimProcessorHelper) {
		this.claimProcessorHelper = claimProcessorHelper;
	}

	/**
	 * @param generatePdf
	 */
	public void setGeneratePdf(final GeneratePdf generatePdf) {
		this.generatePdf = generatePdf;
	}

	/**
	 * @param awardPrintSaver
	 */
	public void setAwardPrintSaver(final AwardPrintSaver awardPrintSaver) {
		this.awardPrintSaver = awardPrintSaver;
	}

	/**
	 * @param updateBenefitClaimDependentsWSHandler
	 */
	public void setUpdateBenefitClaimDependentsWSHandler(
			final UpdateBenefitClaimDependentsWSHandler updateBenefitClaimDependentsWSHandler) {

		this.updateBenefitClaimDependentsWSHandler = updateBenefitClaimDependentsWSHandler;
	}

	/**
	 * @param participantIdPopulator
	 */
	public void setParticipantIdPopulator(final CorporateParticpantIdPopulator participantIdPopulator) {

		this.participantIdPopulator = participantIdPopulator;
	}

	/**
	 * @param findDependentsWSHandler
	 */
	public void setFindDependentsWSHandler(final FindDependentsWSHandler findDependentsWSHandler) {

		this.findDependentsWSHandler = findDependentsWSHandler;
	}

	/**
	 * @param findFiduciaryWSHandler
	 */
	public void setFindFiduciaryWSHandler(final FindFiduciaryWSHandler findFiduciaryWSHandler) {

		this.findFiduciaryWSHandler = findFiduciaryWSHandler;
	}

	/**
	 * @param fiduciaryPopulator
	 */
	public void setFiduciaryPopulator(final FiduciaryPopulator fiduciaryPopulator) {

		this.fiduciaryPopulator = fiduciaryPopulator;
	}

	/**
	 * @param readCurrentAndProposedAwardWSHandler
	 */
	public void setReadCurrentAndProposedAwardWSHandler(
			final ReadCurrentAndProposedAwardWSHandler readCurrentAndProposedAwardWSHandler) {

		this.readCurrentAndProposedAwardWSHandler = readCurrentAndProposedAwardWSHandler;
	}

	/**
	 * @param clearBenefitClaimWSHandler
	 */
	public void setClearBenefitClaimWSHandler(final ClearBenefitClaimWSHandler clearBenefitClaimWSHandler) {

		this.clearBenefitClaimWSHandler = clearBenefitClaimWSHandler;
	}

	/**
	 * @param processMilitaryPay
	 */
	public void setProcessMilitaryPay(final ProcessMilitaryPay processMilitaryPay) {

		this.processMilitaryPay = processMilitaryPay;
	}

	public void setReadAwardDocDataWSHandler(ReadAwardDocDataWSHandler readAwardDocDataWSHandler) {
		this.readAwardDocDataWSHandler = readAwardDocDataWSHandler;
	}

	public void setRbpsDocGenGenstoreService(RbpsDocGenGenstoreService rbpsDocGengenstoreService) {
		this.rbpsDocGenGenstoreService = rbpsDocGengenstoreService;
	}

	public void setGetPersonProfileWSHandler(GetPersonProfileWSHandler getPersonProfileWSHandler) {
		this.getPersonProfileWSHandler = getPersonProfileWSHandler;
	}
	public void testLetter(ReadRetiredPayDecnResponse mResponse,ProcessAwardDependentResponse response,
			RbpsRepository repository){
	//	processMilitaryPay.processMilitaryPayInformationFromJunit(repository,response,mResponse);
		System.out.println("after processMilitaryPay");
		// new method call for Severance Pay and Separation pay 
	//	getSeveranceAndSeparationPay(repository);
		generateLetter(response, repository);
		System.out.println("after generateLetter");
	}
}