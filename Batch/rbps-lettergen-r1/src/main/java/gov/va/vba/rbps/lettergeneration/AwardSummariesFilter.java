/*
 * AwardSummariesFilter.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.lettergeneration;

import gov.va.bip.docgen.service.api.model.v1.DocumentGenerationResponse;
import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.dto.DependentVO;
import gov.va.vba.rbps.coreframework.exception.RbpsRuntimeException;
//import gov.va.vba.rbps.coreframework.util.CommonUtils;
//import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.coreframework.util.SimpleDateUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

//import org.apache.commons.lang.StringUtils;
//import org.apache.commons.lang.WordUtils;
import org.springframework.util.CollectionUtils;

/**
 * Filters award summaries so that we get a list of denial, grants, and
 * removals. Since we start out with grants and denials only, we have to decide
 * which denials are removals.
 */
public class AwardSummariesFilter {

	private static Logger logger = Logger.getLogger(AwardSummariesFilter.class);

	// private CommonUtils utils = new CommonUtils();
	// private LogUtils logUtils = new LogUtils( logger, true );

	private List<AwardSummary> approvals = new ArrayList<AwardSummary>();
	private List<AwardSummary> denials = new ArrayList<AwardSummary>();
	private List<String> approvalChildNames = new ArrayList<String>();
	private String approvalSpouseName = "";

	public void init(final List<AwardSummary> summaries) {

		if (CollectionUtils.isEmpty(summaries)) {

			return;
		}

		processReasons(summaries);
		processSummaries(summaries);
		makeReasonsHtmlSafe(summaries);
		accumulateApprovalNames();

		// logUtils.log( "approvals:\n" + utils.stringBuilder( approvals )
		// + "\ndenials:\n" + utils.stringBuilder( denials )
		// + "\napproval names:\n" + approvalChildNames );
	}

	private void makeReasonsHtmlSafe(final List<AwardSummary> summaries) {

		List<AwardReason> totalReasons = getTotalReasons(summaries);

		for (AwardReason reason : totalReasons) {

			reason.makeHtmlSafe();
		}
	}

	private void processReasons(final List<AwardSummary> summaries) {

		List<AwardReason> totalReasons = getTotalReasons(summaries);
		List<AwardReason> approvals = new ArrayList<AwardReason>();

		for (AwardReason reason : totalReasons) {

			logger.debug(reason.getFirstName() + " currentClaim: " + reason.getCurrentClaim());

			addGrantToApprovals(approvals, reason);
			addRemovalToApprovals(approvals, reason);
		}
	}

	private void processSummaries(final List<AwardSummary> summaries) {

		List<AwardSummary> toBeRemoved = new ArrayList<AwardSummary>();

		for (AwardSummary summary : summaries) {

			addGrantToApprovals(summary);
			addToDenials(summary);
			summary.filterReasonsWithMissingDependents();

			if (removeEmptySummaries(summary)) {

				// logUtils.log( "Removing empty summary: " + summary );
				toBeRemoved.add(summary);
			}
		}

		summaries.removeAll(toBeRemoved);
		approvals.removeAll(toBeRemoved);
		denials.removeAll(toBeRemoved);
	}

	private boolean removeEmptySummaries(final AwardSummary summary) {

		return summary.isEmpty();
	}

	private void addGrantToApprovals(final List<AwardReason> approvals, final AwardReason reason) {

		if (!reason.isGrant()) {

			return;
		}

		approvals.add(reason);
	}

	private void addRemovalToApprovals(final List<AwardReason> approvals, final AwardReason reason) {

		if (reason.isGrant()) {

			return;
		}

		if (reason.isRemoval()) {

			approvals.add(reason);
			return;
		}

		if (!approvalsContainsPreviousGrantForDependent(approvals, reason)) {

			return;
		}

		// logUtils.log( "Changing reason to removal: " + reason );
		reason.setApprovalType(ApprovalType.REMOVAL);
		approvals.add(reason);
	}

	private boolean approvalsContainsPreviousGrantForDependent(final List<AwardReason> approvals,
			final AwardReason inQuestion) {

		for (AwardReason reason : approvals) {

			if (!reason.isGrant()) {

				continue;
			}

			if (reason.getFullName() == null) {

				continue;
			}

			if (!reason.getFullName().equals(inQuestion.getFullName())) {

				continue;
			}

			//
			// Is a denial for sure, even if there is a grant.
			// probably will never happen, but Linda gave me
			// a weird test case.
			//
			if (inQuestion.getSequenceNumber() < 0) {

				continue;
			}

			return true;
		}

		return false;
	}

	private void addGrantToApprovals(final AwardSummary summary) {

		if (!summary.isGrantOrRemoval()) {

			return;
		}

		approvals.add(summary);
	}

	private void addToDenials(final AwardSummary summary) {

		if (summary.isGrantOrRemoval()) {

			return;
		}

		denials.add(summary);
	}

	private void accumulateApprovalNames() {

		List<AwardReason> totalReasons = accumulateApprovalReasons();

		approvalSpouseName = AwardSummary.accumulateApprovalNames(totalReasons, approvalChildNames);

		// if ( ! StringUtils.isBlank( tmpSpouseName ) ) {
		//
		// approvalSpouseName = WordUtils.capitalize(
		// tmpSpouseName.toLowerCase() );
		// }

		// logUtils.log( "Approval child names: " + approvalChildNames );
		// logUtils.log( "Approval spouse name: " + approvalSpouseName );
	}

	private List<AwardReason> accumulateApprovalReasons() {

		List<AwardReason> totalReasons = new ArrayList<AwardReason>();

		for (AwardSummary summary : approvals) {

			for (AwardReason reason : summary.getReasons()) {

				if (!reason.isGrant()) {

					continue;
				}

				totalReasons.add(reason);
			}
		}

		return makeUnique(totalReasons);
	}

	private List<AwardReason> makeUnique(final List<AwardReason> totalReasons) {

		List<AwardReason> unique = new ArrayList<AwardReason>(new HashSet<AwardReason>(totalReasons));

		// logUtils.log( "Unique reasons when looking for unique names: " +
		// utils.stringBuilder( unique ) );

		return unique;
	}

	private List<AwardReason> getTotalReasons(final List<AwardSummary> summaries) {

		List<AwardReason> total = new ArrayList<AwardReason>();

		for (AwardSummary summary : summaries) {

			total.addAll(summary.getReasons());
		}

		return total;
	}

	public boolean getHasApprovals() {

		return getCountApprovalsWithClaimId() > 0;
	}

	private int getCountApprovalsWithClaimId() {

		int count = 0;

		for (AwardSummary summary : approvals) {

			if (summary.hasClaimId()) {

				count++;
			}
		}

		return count;
	}

	public boolean getHasDenials() {

		return !denials.isEmpty();
	}

	public Collection<AwardSummary> getApprovals() {

		return approvals;
	}

	public Collection<AwardSummary> getDenials() {

		return denials;
	}

	public Collection<String> getApprovalChildNames() {

		return approvalChildNames;
	}

	public String getApprovalSpouseName() {

		return approvalSpouseName;
	}

	public void setLogit(final boolean logit) {

		// logUtils.setLogit( logit );
	}

	public List<BenefitInformation> buildBenefitInformation(final List<AwardSummary> summaries) {

		List<BenefitInformation> benefitInformationList = new ArrayList<BenefitInformation>();
		List<AwardReason> totalReasons = getTotalReasons(summaries);

		for (AwardReason reason : totalReasons) {

			BenefitInformation benefitInformation = new BenefitInformation();

			if (reason.getCurrentClaim()&&reason.isGrant()) {

				if (reason.isChild()) {

					benefitInformation.setDependentType("Child");
				} else {
					benefitInformation.setDependentType("Spouse");
				}
				benefitInformation.setFullName(reason.getFullName());
				benefitInformation.setEffectiveDate(reason.getEffectiveDate());

				benefitInformationList.add(benefitInformation);
			}
		}
		return benefitInformationList;

	}

	public List<ExplanationPayment> buildExplanationPayment(final List<AwardSummary> summaries, LinkedHashSet<DependentVO> dependentLinkedHashSet) {

		List<ExplanationPayment> explanationPayment = new ArrayList<ExplanationPayment>();
		List<AwardReason> totalReasons = getTotalReasons(summaries);
		ExplanationPayment payment;
		String fullName = "";

		TreeMap<Date, ExplanationPayment> paymentMap = new TreeMap<Date, ExplanationPayment>();
		for (AwardReason reason : totalReasons) {

			payment = new ExplanationPayment();

			if (reason.isActiveDependent() && reason.isGrant()) {
				logger.debug("is a grant adding " + reason.getFullName()+ " isChild = "+reason.isChild());
				logger.debug("Reason= "+reason.isGrant() +" : "+reason.getDependencyDecisionTypeDescription());
				
				dependentLinkedHashSet.add(new DependentVO(reason.getDepndentPersonID(), reason.getFullName()));
				fullName = getDependentsToString(dependentLinkedHashSet);
				logger.debug("PaymentDate "+reason.getEffectiveDate()+" Full Name= "+fullName+"\n\n");
				
				payment.setFullName(fullName);
				payment.setPaymentDate(reason.getEffectiveDate());
				paymentMap.put(reason.getEffectiveDate(), payment);
			}
			if (reason.isRemoval()) {
				logger.debug("is a not grant removing " + reason.getFullName()+ " isChild = "+reason.isChild());
				logger.debug("Reason= "+reason.isGrant() +" : "+reason.getDependencyDecisionTypeDescription());

				DependentVO dependentToRemove = new DependentVO(reason.getDepndentPersonID(), reason.getFullName()); 
				if (dependentLinkedHashSet.contains(dependentToRemove)) {
					dependentLinkedHashSet.remove(dependentToRemove);
				}
				fullName = getDependentsToString(dependentLinkedHashSet);
				logger.debug("PaymentDate "+reason.getEffectiveDate()+" Full Name= "+fullName+"\n\n");
				
				payment.setFullName(fullName);
				payment.setPaymentDate(reason.getEffectiveDate());
				// explanationPayment.add(payment);
				paymentMap.put(reason.getEffectiveDate(), payment);
			}
		}
		
		explanationPayment = new ArrayList<ExplanationPayment>(paymentMap.values());
		if (explanationPayment.isEmpty()) {
			payment = new ExplanationPayment();
			payment.setFullName("None");
			payment.setPaymentDate(summaries.get(0).getPaymentChangeDate());
			explanationPayment.add(payment);
		}
		
		return explanationPayment;
	}
	
	/**
	 * Convert Linked Hash Set to String
	 * @param dependentLinkedHashSet
	 * @return
	 */
	private String getDependentsToString(LinkedHashSet<DependentVO> dependentLinkedHashSet) {
		String dependetentName = "";
		if(dependentLinkedHashSet.size() > 0) {
			dependetentName = dependentLinkedHashSet.stream()
													.map(dependentVO -> String.valueOf(dependentVO.getFullName()))
													.collect(Collectors.joining(", "));
		}else{
			dependetentName = "None";
		}

		return dependetentName;
	}
	
	public List<String> buildBenefitDetailList(final List<AwardSummary> summaries) {

		List<String> benefitDetailList = new ArrayList<String>();
		List<AwardReason> totalReasons = getTotalReasons(summaries);

		benefitDetailList.addAll(buildApprovalList(totalReasons));
		benefitDetailList.addAll(buildRemovalList(totalReasons));

		return benefitDetailList;
	}

	private List<String> buildApprovalList(List<AwardReason> totalReasons) {

		List<String> approvalList = new ArrayList<String>();

		// build grant
		for (AwardReason reason : totalReasons) {

			String description = null;
			String paymentChangeDateStr = SimpleDateUtils.convertDate("", "MMM d, yyyy", reason.getEffectiveDate());
			String eventDateStr = SimpleDateUtils.convertDate("", "MMM d, yyyy", reason.getEventDate());

			if (reason.getCurrentClaim() &&reason.isGrant() && reason.isChild()) {

				description =getFullName(reason.getFullName()) + " has been added to your award effective "
						+ paymentChangeDateStr + ", because you submitted all the required " + "information "
						+ System.getProperty("line.separator")
						+ "and meet the eligibility requirements for the dependency allowance";
				if (reason.getDependencyDecisionType()!=null && reason.getDependencyDecisionType().equals("EMC")) {

					description = description
							+ " (38 CFR 3.4, 38 CFR 3.57, 38 CFR 3.204, 38 CFR 3.209, 38 CFR 3.210, 38 CFR 3.216).";
				} else if (reason.getDependencyDecisionType()!=null && reason.getDependencyDecisionType().equals("SCHATTB")) {
					description = description
							+ " (38 CFR 3.4, 38 CFR 3.57, 38 CFR 3.204, 38 CFR 3.209, 38 CFR 3.210, 38 CFR 3.216, "
							+ "38 CFR 3.667, 38 CFR 3.707, 38 CFR 21.4200).";
				}
			}
			if (reason.getCurrentClaim() && reason.isGrant() &&reason.getDependencyDecisionType()!=null && reason.getDependencyDecisionType().equals("DEPEST")) {
				description =getFullName( reason.getFullName()) + " " + " has been added to your award effective  "
						+ paymentChangeDateStr + ", because you submitted all "
						+ "the required information and meet the eligibility requirements for the dependency allowance"
						+ " (38 CFR 3.4, 38 CFR 3.50, 38 CFR 3.57, 38 CFR 3.204, 38 CFR 3.205, 38 CFR 3.216).";
			}
			if (description != null) {
				approvalList.add(description);
			}

		}
		return approvalList;
	}

	private List<String> buildRemovalList(List<AwardReason> totalReasons) {
		
		
		List<String> removealList = new ArrayList<String>();
		for (AwardReason reason : totalReasons) {
			
			String description = null;
			String paymentChangeDateStr = SimpleDateUtils.convertDate("", "MMM d, yyyy", reason.getEffectiveDate());
			String eventDateStr = SimpleDateUtils.convertDate("", "MMM d, yyyy", reason.getEventDate());
			if (reason.getCurrentClaim()&& reason.isRemoval() && reason.isChild()) {

				description = "We will remove " + getFullName(reason.getFullName()) + " effective "
						+ paymentChangeDateStr + " for the following reason: " + getFullName(reason.getFullName());
				if (reason.getDependencyDecisionType()!=null && reason.getDependencyDecisionType().equals("T18")) {
					description = description + " is over the age of 18 and not attending school"
							+" (38 CFR 3.57, 38 CFR 3.213, 38 CFR 3.503).";
				}
				if (reason.getDependencyDecisionType()!=null && reason.getDependencyDecisionType().equals("T23")) {
					description = description + " is over the age of 23." 
							+ " (38 CFR 3.667(c), 38 CFR 3.57, 38 CFR 3.213, 38 CFR 3.503).";
				}
				if (reason.getDependencyDecisionType()!=null && reason.getDependencyDecisionType().equals("SCHATTT")) {
					description = description + " has stopped attending school on " +eventDateStr
							+ ". (38 CFR 3.667(c),  38 CFR 3.57, 38 CFR 3.213, 38 CFR 3.503).";
				}

			}
			else if (reason.getCurrentClaim()&& reason.getDependencyDecisionType()!=null && reason.getDependencyDecisionType().equalsIgnoreCase("MT")){
						description = "We will remove " + getFullName(reason.getFullName()) + " effective "
							+ paymentChangeDateStr + " because the two of you divorced on "+ eventDateStr
								+ " (38 CFR 3.501, 38 CFR 3.4, 38 CFR 3.50, 38 CFR 3.57, 38 CFR 3.204, 38 CFR 3.205, 38 CFR 3.216).";
					} 
			else if (reason.getCurrentClaim() &&reason.getDependencyDecisionType()!=null &&
							reason.getDependencyDecisionType().equalsIgnoreCase("D")) {

						description = "We will remove " + getFullName(reason.getFullName()) + " effective "
								+ paymentChangeDateStr + ". Our records indicate that  "
								+ getFullName(reason.getFullName()) + " passed away on " + eventDateStr
								+ ". We would like to express our condolences on the passing of your family member, "
								+ getFullName(reason.getFullName())
								+ ". Losing a loved one is never easy, and we understand this must be a difficult time for you."
								+ "  (38 CFR 3.500).";
					}
			if (description != null) {
				removealList.add(description);
			}
		}
		return removealList;
	}
	private  String getFullName(String text){
		
		if(text !=null && !text.equals("") ){
			return text.toUpperCase();
		}
		else{
		// name is null  or "" we can't sent to the letter
		//  send the case to manul processing
		   logger.debug("reason.get full name is null or ''. sending to manul processing.");
           String message = "Unexpected exception occurred in LetterFields: dependent full name is null";
         	throw new RbpsRuntimeException( message );
		
		}
	}
}
