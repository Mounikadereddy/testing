package gov.va.vba.rbps.lettergeneration;

import gov.va.vba.framework.domain.entities.jaxb.Veteran.MaritalStatus.Married.Spouse;
import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.util.SimpleDateUtils;
import gov.va.vba.rbps.coreframework.xom.Child;
import gov.va.vba.rbps.coreframework.xom.FormType;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

//import gov.va.vba.rbps.coreframework.util.LogUtils;

public class LetterFieldsMarshalPopulator {

	private static Logger logger = Logger.getLogger(LetterFieldsMarshalPopulator.class);
	// private LogUtils logUtils = new LogUtils( logger, true );
	// private SimpleDateUtils dateUtils = new SimpleDateUtils();
	private final String doubleSpace = "  ";
	private final String singleSpace = " ";

	public void populateLetterFieldsMarshalObject(RbpsRepository repository, LetterFields letterFields,
			LetterFieldsMarshal letterFieldsMarshal) {

		letterFieldsMarshal.setApprovalChildNames(letterFields.getApprovalChildNames());
		letterFieldsMarshal.setApprovalSpouseName(letterFields.getApprovalSpouseName());
		letterFieldsMarshal.setHasApprovals(letterFields.getHasApprovals());
		letterFieldsMarshal.setAwardAmount(letterFields.getAwardAmount());

		letterFieldsMarshal.setChildrenNames(letterFields.getChildrenNames());
		letterFieldsMarshal.setCity(letterFields.getCity().toUpperCase());
		letterFieldsMarshal.setClaimDate(letterFields.getClaimDateString());
		letterFieldsMarshal.setClaimNumber(letterFields.getClaimNumber());
		letterFieldsMarshal.setCountry(letterFields.getCountry().toUpperCase());
		letterFieldsMarshal.setCss(letterFields.getCss());

		letterFieldsMarshal.setDecisionFormTypes(letterFields.getDecisionFormTypes());
		letterFieldsMarshal.setDenialText(getDenialText(repository, letterFields));
		letterFieldsMarshal.setHasDenials(letterFields.getHasDenials());
		letterFieldsMarshal.setMetList(getMetList(repository, letterFields));

		letterFieldsMarshal.setEvidences(letterFields.getEvidences());

		letterFieldsMarshal.setFiduciaryAttentionText(letterFields.getFiduciaryAttentionText() != null
				? letterFields.getFiduciaryAttentionText().toUpperCase() : null);
		letterFieldsMarshal.setFiduciaryName(
				letterFields.getFiduciaryName() != null ? letterFields.getFiduciaryName().toUpperCase() : null);

		letterFieldsMarshal.setFileNumber(letterFields.getFileNumber());
		String firstLast = letterFields.getFirstLast();
		letterFieldsMarshal.setFmLast(letterFields.getFmLast());
		if (firstLast != null) {
			if (firstLast.length() > 35) {
				letterFieldsMarshal.setFirstLast(firstLast.substring(0, 35).toUpperCase());

			} else
				letterFieldsMarshal.setFirstLast(firstLast.toUpperCase());
		} else {
			letterFieldsMarshal.setFirstLast(null);

		}
		letterFieldsMarshal
				.setFirsttName(letterFields.getFirstName() != null ? letterFields.getFirstName().toUpperCase() : null);

		letterFieldsMarshal.setForPoa(letterFields.getForPoa());
		letterFieldsMarshal.setHasApprovals(letterFields.getHasApprovals());
		letterFieldsMarshal.setHasDenials(letterFields.getHasDenials());
		letterFieldsMarshal.setHasPOA(letterFields.getHasPOA());
		letterFieldsMarshal.setSendCcTo(letterFields.getSendCcTo());
		letterFieldsMarshal.setParticipantAddressPostalVO(letterFields.getParticipantAddressPostalVO());
		letterFieldsMarshal.setHasMilitaryPay(letterFields.getHasMilitaryPay());

		logger.debug("setHasMilitaryPay is done");
		letterFieldsMarshal.setToDate(letterFields.getToDate());
		letterFieldsMarshal.setPartOrAll(letterFields.getPartOrAll());

		letterFieldsMarshal.setJoinedApprovalChildNames(letterFields.getJoinedApprovalChildNames());
		String lastFirst = letterFields.getLastFirst();
		if (lastFirst != null) {
			if (lastFirst.length() > 35) {
				letterFieldsMarshal.setLastFirst(lastFirst.substring(0, 35).toUpperCase());

			} else
				letterFieldsMarshal.setLastFirst(lastFirst.toUpperCase());
		} else {
			letterFieldsMarshal.setLastFirst(null);

		}
		letterFieldsMarshal
				.setLastName(letterFields.getLastName() != null ? letterFields.getLastName().toUpperCase() : null);

		letterFieldsMarshal.setLetterHeader(letterFields.getLetterHeader());
		letterFieldsMarshal.setLine1(letterFields.getLine1() != null ? letterFields.getLine1().toUpperCase() : null);
		letterFieldsMarshal.setLine2(letterFields.getLine2() != null ? letterFields.getLine2().toUpperCase() : null);
		letterFieldsMarshal.setLine3(letterFields.getLine3() != null ? letterFields.getLine3().toUpperCase() : null);

		letterFieldsMarshal.setMiddleName(letterFields.getMiddleName());

		letterFieldsMarshal.setPaymentChangeDate(letterFields.getPaymentChangeDate());
		letterFieldsMarshal.setHasPOA(letterFields.getHasPOA());
		letterFieldsMarshal.setPoaName(letterFields.getPoaName());
		letterFieldsMarshal.setPoaNoPoaText(poaNoPoaText(letterFields));
		logger.debug("setPoaNoPoaText is done");
		setPaymentType(letterFieldsMarshal, repository, letterFields);
		setOverPayment(letterFieldsMarshal, repository, letterFields);
		setPriorTerm(letterFieldsMarshal, letterFields);

		letterFieldsMarshal.setReasonForChange(letterFields.getReasonForChange());

		letterFieldsMarshal.setSalutation(letterFields.getSalutation());
		letterFieldsMarshal.setSectionId(letterFields.getSectionId());
		letterFieldsMarshal.setSenderName(letterFields.getSenderName());
		letterFieldsMarshal.setLetterHeader(letterFields.getLetterHeader());
		letterFieldsMarshal.setSenderTitle(letterFields.getSenderTitle());

		letterFieldsMarshal.setServiceOrg(letterFields.getServiceOrg());
		letterFieldsMarshal.setSignature(letterFields.getSignature());

		letterFieldsMarshal.setSojCity(letterFields.getSojCity());
		letterFieldsMarshal.setSojCountry("USA");
		letterFieldsMarshal.setSojLine1(letterFields.getSojLine1());
		letterFieldsMarshal.setSojLine2(letterFields.getSojLine2());
		letterFieldsMarshal.setSojLine3(letterFields.getSojLine3());
		letterFieldsMarshal.setSojState(letterFields.getSojState());
		letterFieldsMarshal.setSojZip(letterFields.getSojZip());
		letterFieldsMarshal.setSojZipPrefix(letterFields.getSojZipPrefix());
		letterFieldsMarshal.setSojZipSuffix1(letterFields.getSojZipSuffix1());
		letterFieldsMarshal.setSojZipSuffix2(letterFields.getSojZipSuffix2());

		letterFieldsMarshal.setSpouseName(letterFields.getSpouseName());
		letterFieldsMarshal.setState(letterFields.getState());

		letterFieldsMarshal.setToday(SimpleDateUtils.convertDate("", "MMM dd, yyyy", new Date()));
		letterFieldsMarshal.setTotalDependents(letterFields.getTotalDependents());
		letterFieldsMarshal.setCurrentMonthlyAmount(letterFields.getCurrentMonthlyAmount());
		letterFieldsMarshal.setTotalDependentsSentence(getTotalDependentsSentence(letterFields.getTotalDependents()));

		letterFieldsMarshal.setYourPaymentIncludes(getYourPaymentIncludesString(letterFields));

		letterFieldsMarshal.setWeMadeThisDecisionText(
				weMadeThisDecisionText(letterFields, letterFields.getTotalDependents(), repository));
		letterFieldsMarshal.setLetUsKnowText(letUsKnowText(letterFields));

		// RTC# 1310694 - Change notification letter - related to RBPS Pension
		// Dependency Claims, Dependents with no Income ESCP 002859
		letterFieldsMarshal.setPensionGrantNoIncome(pensionGrantNoIncomeText(letterFields, repository));

		letterFieldsMarshal.setZip(letterFields.getZip());
		letterFieldsMarshal.setZipPrefix(letterFields.getZipPrefix());
		letterFieldsMarshal.setZipSuffix1(letterFields.getZipSuffix1());
		letterFieldsMarshal.setZipSuffix2(letterFields.getZipSuffix2());
		letterFieldsMarshal.setBenefitInformation(letterFields.getBenefitInformation());
		letterFieldsMarshal.setExplanationPayment(letterFields.getExplanationPayment());
		logger.debug("setExplanationPayment is done");
		setSojAddress(letterFields, letterFieldsMarshal);
		setCityStateCountryZip(letterFields, letterFieldsMarshal);
		setFiduciaryPropositionalPhrase(letterFields, letterFieldsMarshal);
		setSalutation(letterFields, letterFieldsMarshal);
		setAwardSummaries(letterFields, letterFieldsMarshal);
		logger.debug("setAwardSummaries is done");
		setForms(letterFields, letterFieldsMarshal);
		setCallUsField(letterFields, letterFieldsMarshal);
		letterFieldsMarshal.setBenefitDetailList(letterFields.getBenefitDetailList());
		letterFieldsMarshal.setWithholdingType(letterFields.getWithholdingType());
		letterFieldsMarshal.setWithholdingPara(letterFields.getWithholdingPara());
		letterFieldsMarshal.setHasRetiredPay(letterFields.isHasRetiredPay());
		letterFieldsMarshal.setHasSeparationPay(letterFields.isHasSeparationPay());
		letterFieldsMarshal.setHasSeverancePay(letterFields.isHasSeverancePay());
		letterFieldsMarshal.setSeparationWithholdingPara(letterFields.getSeparationWithholdingPara());
		letterFieldsMarshal.setSeveranceWithholdingPara(letterFields.getSeveranceWithholdingPara());
		letterFieldsMarshal.setHasBlindStatus(letterFields.isHasBlindStatus());

	}

	private void setFiduciaryPropositionalPhrase(LetterFields letterFields, LetterFieldsMarshal letterFieldsMarshal) {

		// logUtils.log( "letterFields FiduciaryPrepositionalPhrase: " +
		// letterFields.getFiduciaryPrepositionalPhrase() );
		// logUtils.log( "letterFields FiduciaryAttentionText: " +
		// letterFields.getFiduciaryAttentionText() );

		letterFieldsMarshal.setFiduciaryPrepositionalPhrase(null);
		letterFieldsMarshal.setFiduciaryAttentionText(null);

		if (StringUtils.isNotEmpty(letterFields.getFiduciaryPrepositionalPhrase())) {
			String SPACE = " ";
			String text = letterFields.getFiduciaryPrepositionalPhrase() + SPACE + letterFields.getFirstLast();
			if (text.length() > 35) {
				letterFieldsMarshal.setFirstLast(text.substring(0, 35).toUpperCase());
			} else {
				letterFieldsMarshal.setFirstLast(text.toUpperCase());
			}
		}

		letterFieldsMarshal.setFiduciaryPrepositionalPhrase(letterFields.getFiduciaryPrepositionalPhrase());

		if (!StringUtils.isEmpty(letterFields.getFiduciaryName())) {

			String fiduciaryAttentionText = letterFields.getFiduciaryName();
			if (fiduciaryAttentionText.length() > 35) {
				letterFieldsMarshal.setFiduciaryAttentionText(fiduciaryAttentionText.substring(0, 35).toUpperCase());
			} else {

				letterFieldsMarshal.setFiduciaryAttentionText(fiduciaryAttentionText.toUpperCase());
			}
		}

	}

	private void setSojAddress(LetterFields letterFields, LetterFieldsMarshal letterFieldsMarshal) {

		String DASH = "-";
		String zip = letterFieldsMarshal.getSojZipPrefix();

		if (StringUtils.isNotEmpty(letterFields.getSojLine3())) {

			if (StringUtils.isNotEmpty(letterFields.getSojLine2())) {

				letterFieldsMarshal.setSojLine1(letterFields.getSojLine1() + ", " + letterFields.getSojLine2());
			}

			letterFieldsMarshal.setSojLine2(letterFields.getSojLine3());
		}

		if (StringUtils.isNotEmpty(letterFields.getSojZipSuffix1())) {

			zip = zip + DASH + letterFields.getSojZipSuffix1();
		}

		String cityStateCountryZip = letterFieldsMarshal.getSojCity() + ",  " + letterFieldsMarshal.getSojState() + " "
				+ letterFieldsMarshal.getSojCountry() + " " + zip;

		letterFieldsMarshal.setSojZipPrefix(zip);
		letterFieldsMarshal.setSojCityStateCountryZip(cityStateCountryZip);

	}

	private void setCityStateCountryZip(LetterFields letterFields, LetterFieldsMarshal letterFieldsMarshal) {

		String SPACE = " ";

		String cityStateCountryZip = letterFieldsMarshal.getCity() + ", " + letterFieldsMarshal.getState() + SPACE;
		if (!letterFieldsMarshal.getCountry().equals("USA")) {
			cityStateCountryZip = cityStateCountryZip + letterFieldsMarshal.getCountry() + SPACE;
		}
		cityStateCountryZip = cityStateCountryZip + letterFieldsMarshal.getZip();

		letterFieldsMarshal.setCityStateCountryZip(cityStateCountryZip);
	}

	private void setSalutation(LetterFields letterFields, LetterFieldsMarshal letterFieldsMarshal) {

		String SPACE = " ";
		String DEAR = "Dear";

		if (StringUtils.isNotEmpty(letterFields.getSalutation())) {

			letterFieldsMarshal
					.setSalutation(DEAR + SPACE + letterFields.getSalutation() + SPACE + letterFields.getLastName());
		} else {

			letterFieldsMarshal
					.setSalutation(DEAR + SPACE + letterFields.getFirstName() + SPACE + letterFields.getLastName());
		}

	}

	private void setAwardSummaries(LetterFields letterFields, LetterFieldsMarshal letterFieldsMarshal) {

		Collection<AwardSummary> approvalSummaries = letterFields.getApprovals();
		Collection<AwardSummary> denialSummaries = letterFields.getDenials();

		Collection<AwardSummaryMarshal> approvalSummariesMarshal = new ArrayList<AwardSummaryMarshal>();
		Collection<AwardSummaryMarshal> denialSummariesMarshal = new ArrayList<AwardSummaryMarshal>();

		for (AwardSummary awardSummary : approvalSummaries) {

			AwardSummaryMarshal awardSummaryMarshal = new AwardSummaryMarshal();

			awardSummaryMarshal.setFormattedTotalVABenefit(awardSummary.getFormattedTotalVABenefit());
			awardSummaryMarshal.setFormattedAmountWithheld(awardSummary.getFormattedAmountWithheld());
			awardSummaryMarshal.setFormattedAmountPaid(awardSummary.getFormattedAmountPaid());
			awardSummaryMarshal.setFirstFormattedReason(awardSummary.getFirstFormattedReason());

			awardSummaryMarshal.addFormattedReason(awardSummary.getFirstFormattedReason());

			setPaymentChangeDate(awardSummary, awardSummaryMarshal);

			setRestOfFormattedReasons(awardSummary, awardSummaryMarshal);
			approvalSummariesMarshal.add(awardSummaryMarshal);
		}

		letterFieldsMarshal.setApprovals(approvalSummariesMarshal);

		for (AwardSummary awardSummary : denialSummaries) {

			AwardSummaryMarshal awardSummaryMarshal = new AwardSummaryMarshal();

			awardSummaryMarshal.setFormattedTotalVABenefit(awardSummary.getFormattedTotalVABenefit());
			awardSummaryMarshal.setFormattedAmountWithheld(awardSummary.getFormattedAmountWithheld());
			awardSummaryMarshal.setFormattedAmountPaid(awardSummary.getFormattedAmountPaid());
			awardSummaryMarshal.setFirstFormattedReason(awardSummary.getFirstFormattedReason());

			awardSummaryMarshal.addFormattedReason(awardSummary.getFirstFormattedReason());

			setPaymentChangeDate(awardSummary, awardSummaryMarshal);

			setRestOfFormattedReasons(awardSummary, awardSummaryMarshal);
			denialSummariesMarshal.add(awardSummaryMarshal);
		}

		letterFieldsMarshal.setDenials(denialSummariesMarshal);

	}

	private void setPaymentChangeDate(AwardSummary awardSummary, AwardSummaryMarshal awardSummaryMarshal) {

		Date paymentchangeDate = awardSummary.getPaymentChangeDate();
		String paymentChangeDateStr = SimpleDateUtils.convertDate("", "MMM d, yyyy", paymentchangeDate);

		awardSummaryMarshal.setPaymentChangeDate(paymentChangeDateStr);
	}

	private void setRestOfFormattedReasons(AwardSummary awardSummary, AwardSummaryMarshal awardSummaryMarshal) {

		List<String> restOfFormattedReasons = awardSummary.getRestOfFormattedReasons();

		if (restOfFormattedReasons.size() == 0) {

			restOfFormattedReasons.add("");
		}

		awardSummaryMarshal.setRestOfFormattedReasons(restOfFormattedReasons);

		for (String formattedReason : awardSummary.getRestOfFormattedReasons()) {

			awardSummaryMarshal.addFormattedReason(formattedReason);
		}
	}

	// RTC #1177801: Spouse removal text in Dependency notification letter ESCP
	// 002457
	private String weMadeThisDecisionText(LetterFields letterFields, long totalDependents, RbpsRepository repository) {

		String str = "";

		if (totalDependents > 0) {
			str = "We made this decision because your reported dependents meet the criteria for "
					+ "establishing a relationship and you are in receipt of "
					+ "at least 30% service-connected disability benefits.";
		}

		if (letterFields.getHasApprovals()) {

			for (AwardSummary awardLine : letterFields.getApprovals()) {

				for (AwardReason reason : awardLine.getReasons()) {

					if (reason.getAwardLineReasonType().equals("23") && reason.isRemoval()) {

						String reasonDescription = "";
						if (reason.getDependencyDecisionTypeDescription().compareTo("Marriage Terminated") == 0) {
							reasonDescription = "marriage termination.";
						} else if (reason.getDependencyDecisionTypeDescription().compareTo("Death") == 0) {

							reasonDescription = "your spouse's death";

							if (repository.getVeteran().getLatestPreviousMarriage() != null
									&& repository.getVeteran().getLatestPreviousMarriage().getEndDate() != null) {
								String marriageEndDate = SimpleDateUtils.convertDate("", "MMM d, yyyy",
										repository.getVeteran().getLatestPreviousMarriage().getEndDate());
								reasonDescription = reasonDescription + " on " + marriageEndDate;
							}

							reasonDescription = reasonDescription + ".";
						}

						String effectiveDate = SimpleDateUtils.convertDate("", "MMM d, yyyy",
								reason.getEffectiveDate());

						return str + " " + reason.getFirstName() + " has been removed from your award effective "
								+ effectiveDate + " due to " + reasonDescription;
					}
				}
			}

			return str;
		}

		return null;
	}

	private String letUsKnowText(LetterFields letterFields) {

		String str = "Let us know right away if there is any change in the status of your dependents.";

		if (letterFields.getHasApprovals()) {

			return str;
		}

		return null;
	}

	// RTC# 1310694 - Change notification letter - related to RBPS Pension
	// Dependency Claims, Dependents with no Income ESCP 002859
	private String pensionGrantNoIncomeText(LetterFields letterFields, RbpsRepository repository) {

		if (repository.isProcessPensions()) {
			if (letterFields.getHasPension()) {
				if (letterFields.getHasApprovals()) {
					for (AwardSummary awardLine : letterFields.getApprovals()) {
						for (AwardReason reason : awardLine.getReasons()) {
							if (reason.isGrant()) {

								// Reformats number to readable string. Turns
								// 129034 into 129,034; rounded to nearest whole
								// number
								String pensionNetWorthLimit = String.format("%,6.0f",
										letterFields.getPensionNetWorthLimit());
								return "Your report of no dependent " + "income and household net "
										+ "worth does not exceed $" + pensionNetWorthLimit;
							}

						}
					}
				}
			}
		}

		return null;

	}

	private void setForms(LetterFields letterFields, LetterFieldsMarshal letterFieldsMarshal) {

		List<FormType> formTypes = letterFields.getDecisionFormTypes();

		letterFieldsMarshal
				.setForm1("VA Form " + formTypes.get(0).getValue().toString() + ", " + formTypes.get(0).getCode());

		if (formTypes.size() > 1) {

			letterFieldsMarshal
					.setForm2("VA Form " + formTypes.get(1).getValue().toString() + ", " + formTypes.get(1).getCode());
		}
	}

	private void setCallUsField(LetterFields letterFields, LetterFieldsMarshal letterFieldsMarshal) {

		String USATEXT = "Call us at 1-800-827-1000. If you use a Telecommunications Device for the Deaf (TDD), the Federal number is 711.";
		String NONUSATEXT = "Call or visit the nearest American Embassy or Consulate for assistance. "
				+ "In Canada, call or visit the local office of Veterans Affairs Canada. "
				+ "From Guam, call us by dialing toll free, 475-8387. "
				+ "From American Samoa and N. Marianas, call us at 1-800-844-7928.";

		if (letterFields.getCountry().equalsIgnoreCase("USA")) {

			letterFieldsMarshal.setHereIsWhatToDoCallUs(USATEXT);
		} else {

			letterFieldsMarshal.setHereIsWhatToDoCallUs(NONUSATEXT);
		}
	}

	private String getTotalDependentsSentence(long totalDependents) {

		String returnString = String.format("We are currently paying you as a Veteran with %d dependent(s).",
				totalDependents);
		return returnString;
	}

	private String getYourPaymentIncludesString(LetterFields letterFields) {

		String paymentIncludesString = "";

		if (letterFields.getApprovalSpouseName() != null ||
		// letterFields.getApprovalSpouseName() != "" ||
				letterFields.getApprovalChildNames().size() > 0) {

			paymentIncludesString = "Your payment includes an additional amount for";
		}

		if (letterFields.getApprovalSpouseName() != null && letterFields.getApprovalSpouseName() != "") {

			paymentIncludesString = String.format("%s your spouse %s", paymentIncludesString,
					letterFields.getApprovalSpouseName());
		}

		if (letterFields.getApprovalSpouseName() != null && letterFields.getApprovalSpouseName() != "") {

			if (letterFields.getApprovalChildNames().size() > 0) {

				paymentIncludesString = String.format("%s and ", paymentIncludesString);
			} else {

				paymentIncludesString = String.format("%s.", paymentIncludesString);
			}
		}

		if (letterFields.getApprovalChildNames().size() > 0) {

			paymentIncludesString = String.format("%s your %s.", paymentIncludesString,
					letterFields.getJoinedApprovalChildNames());
		}

		return paymentIncludesString;
	}

	private String getDenialText(RbpsRepository repository, LetterFields letterFields) {

		List<String> metList = new ArrayList<String>();
		// SR# 723083 RTC 993395 remove the
		// repository.getVeteranAbove30AndDenial here
		if (repository.getVeteran().getServiceConnectedDisabilityRating() < 30) {

			/*
			 * String denialText =
			 * "We have denied your claim to add your dependent(s) to your compensation award.  "
			 * +
			 * "In order to receive additional compensation benefits due to dependency, a veteran must have a service-connected evaluation of at least 30%.  "
			 * +
			 * "Because you are currently evaluated less than 30% serviced-connected, we cannot grant your claim at this time."
			 * ;
			 */
			String denialText = "In order to receive additional compensation benefits due to dependency, a Veteran must have a service-connected evaluation of at least 30%.  "
					+ "You are currently evaluated less than 30% serviced-connected (38 CFR 3.4).";

			return denialText;
		}

		StringBuffer denialText = new StringBuffer();

		if (letterFields.getHasDenials()) {

			// denialText.append("We have denied your claim to add one or more
			// of your dependent(s) to your award. ");

			for (AwardSummary awardLine : letterFields.getDenials()) {

				for (AwardReason reason : awardLine.getReasons()) {

					denialText.append(String.format("We cannot add %s due to the following reason: %s ",
							reason.getFullName(), reason.getFormattedReason()));
					denialText.append("(38 CFR 3.57).|");
					// We cannot add Edgar Engholm due to the following reason:
					// Edgar was reported as being 23 years of age or older and
					// not reported as being seriously disabled. (38 CFR 3.57).

				}
			}

		}

		return denialText.toString();
	}

	private ArrayList<String> getMetList(RbpsRepository repository, LetterFields letterFields) {

		ArrayList<String> metList = new ArrayList<String>();
		String met = "";
		if (letterFields.getHasDenials()) {
			
			if (repository.getVeteran().getServiceConnectedDisabilityRating() >= 30) {
				met = "You are rated 30% or greater service connected.";
				
				for (AwardSummary awardLine : letterFields.getDenials()) {

					
					for (AwardReason reason : awardLine.getReasons()) {

					
						long dependentId = reason.getDepndentPersonID();
						for (final Child c : repository.getVeteran().getChildren()) {

							
							if (c.getCorpParticipantId() == dependentId) {

								if (c.getBirthDate() != null) {
									met = met+": You provided " + reason.getFullName() + "'s date of birth.";
								}
								if (c.getSsn() != null) {
									met = met + ": You provided " + reason.getFullName() + "'s social security number.";
								}
								if (c.getFirstName() != null) {
									met = met + ": You provided " + reason.getFullName() + "'s name.";
								}
								if (c.isLivingWithVeteran() == true) {
									met = met + ": You provided " + reason.getFullName() + "'is living with you.";
								}
								if (c.hasMarriages() == false) {
									met = met + ": You provided " + reason.getFullName() + "'s unmarried.";
								}
								metList.add(met);
								met="";
							}

						}
						if (repository.getVeteran().getCurrentMarriage() != null) {
							gov.va.vba.rbps.coreframework.xom.Spouse spouse = repository.getVeteran()
									.getCurrentMarriage().getMarriedTo();
							if (spouse.getCorpParticipantId() == dependentId) {
								if (spouse.getBirthDate() != null) {

									met = "You provided " + spouse.getFirstName() + " " + spouse.getLastName()
											+ "'s date of birth.";
								}
								if (spouse.getSsn() != null) {
									met = met + ": You provided " + spouse.getFirstName() + " " + spouse.getLastName()
											+ "'s social security number.";
								}
								if (spouse.getFirstName() != null) {
									met = met + ": You provided " + spouse.getFirstName() + " " + spouse.getLastName()
											+ "'s name.";
								}
								metList.add(met);
								met="";
							}
						}
					}
				}
			} else {
				// vet is not 30%

				logger.debug("in  ************  // vet is not 30%");

				logger.debug(
						"************************  //repository.getVeteran()" + repository.getVeteran().toString());
				for (final Child c : repository.getVeteran().getChildren()) {
					if (c.getBirthDate() != null) {
						met = "You provided " + c.getFirstName() + " " + c.getLastName() + "'s date of birth.";
					}
					if (c.getSsn() != null) {
						met = met + ": You provided " + c.getFirstName() + " " + c.getLastName()
								+ "'s social security number.";
					}
					if (c.getFirstName() != null) {
						met = met + ": You provided " + c.getFirstName() + " " + c.getLastName() + "'s name.";
					}
					if (c.isLivingWithVeteran() == true) {
						met = met + ": You provided " + c.getFirstName() + " " + c.getLastName()
								+ "'is living with you.";
					}
					if (c.hasMarriages() == false) {
						met = met + ":" + c.getFirstName() + " " + c.getLastName() + " is unmarried.";
					}
					logger.debug("in  ************  // vet is not 30%  with met"+met);
					metList.add(met);
					met="";
				}
				if (repository.getVeteran().getCurrentMarriage() != null) {
					gov.va.vba.rbps.coreframework.xom.Spouse spouse = repository.getVeteran().getCurrentMarriage()
							.getMarriedTo();
					if (spouse.getBirthDate() != null) {

						met = "You provided " + spouse.getFirstName() + " " + spouse.getLastName()
								+ "'s date of birth.";
					}
					if (spouse.getSsn() != null) {
						met = met + ": You provided " + spouse.getFirstName() + " " + spouse.getLastName()
								+ "'s social security number.";
					}
					if (spouse.getFirstName() != null) {
						met = met + ": You provided " + spouse.getFirstName() + " " + spouse.getLastName() + "'s name.";
					}
					logger.debug("in  ************  // vet is not 30%  with met with spouse"+met);
					metList.add(met);
					met="";
				}
				
				//metList.add(met);

			}
		}
		logger.debug("in  ************  return metList"+metList.toString());
		return metList;
	}

	private String poaNoPoaText(LetterFields letterFields) {

		String poaNoPoaText = "";

		if (letterFields.getHasPOA()) {

			poaNoPoaText = String.format(
					"We sent a copy of this letter to your representative, %s, "
							+ "whom you can also contact if you have questions or need assistance.",
					letterFields.getPoaName());
		} else {
			poaNoPoaText = "We have no record of you appointing a service organization or representative to assist you with your claim. "
					+ "You can contact us for a listing of the recognized "
					+ "Veterans\' service organizations and/or representatives. "
					+ "Veterans\' service organizations, which are recognized or approved "
					+ "to provide services to the Veteran community, can also help you with any questions.";
		}

		return poaNoPoaText;
	}

	private void setPaymentType(LetterFieldsMarshal letterFieldsMarshal, RbpsRepository repository,
			LetterFields letterFields) {
		PaymentTypeMarshal paymentType = new PaymentTypeMarshal();
		logger.debug("Setting payment type");
		paymentType.setCheckPayment(repository.getVeteran().getPaymentType().equalsIgnoreCase("Check"));
		logger.debug("Payment type set");
		letterFieldsMarshal.setPaymentType(paymentType);
	}

	private void setOverPayment(LetterFieldsMarshal letterFieldsMarshal, RbpsRepository repository,
			LetterFields letterFields) {
		OverPaymentMarshal overPayment = new OverPaymentMarshal();
		overPayment.setOverPayment(letterFields.isOverPayment());
		letterFieldsMarshal.setOverPymt(overPayment);
	}

	private void setPriorTerm(LetterFieldsMarshal letterFieldsMarshal, LetterFields letterFields) {
		PriorTermMarshal priorTerm = new PriorTermMarshal();

		priorTerm.setPriorSchoolTermRejected(letterFields.isPriorSchoolTermRejected());
		priorTerm.setPriorSchoolTermRejectedText(priorSchoolTermRejectedText(letterFields));
		priorTerm.setPriorSchoolTermRejectedText1(priorSchoolTermRejectedText1(letterFields));
		priorTerm.setPriorSchoolTermRejectedText2(priorSchoolTermRejectedText2(letterFields));
		priorTerm.setPriorSchoolTermRejectedText3(priorSchoolTermRejectedText3(letterFields));
		priorTerm.setPriorSchoolTermRejectedText4(priorSchoolTermRejectedText4(letterFields));
		letterFieldsMarshal.setPriorTerm(priorTerm);
	}

	private String priorSchoolTermRejectedText(LetterFields letterFields) {

		String priorSchoolRejText = "";

		if (letterFields.isPriorSchoolTermRejected()) {
			priorSchoolRejText = String.format("Your claim indicated your school-aged child "
					+ letterFields.getPriorSchoolChildName() + " attended a prior school term from  "
					+ letterFields.getPriorSchoolTermDatesString()
					+ ". This information did not qualify for additional benefits because one or more of the reasons stated below: ");
		}
		return priorSchoolRejText;
	}

	private String priorSchoolTermRejectedText1(LetterFields letterFields) {

		String priorSchoolRejText = "";

		if (letterFields.isPriorSchoolTermRejected()) {

			priorSchoolRejText = String
					.format("If you wish to claim additional school attendance, please verify that prior school term dates are correct, "
							+ "and that we are not already paying you for the claimed prior school term. You may resubmit your claim to add "
							+ "the prior school term.");
		}
		return priorSchoolRejText;
	}

	private String priorSchoolTermRejectedText2(LetterFields letterFields) {

		String priorSchoolRejText = "";

		if (letterFields.isPriorSchoolTermRejected()) {

			priorSchoolRejText = String.format(
					"-	We are already paying you for " + letterFields.getPriorSchoolChildName() + " for these dates");
		}
		return priorSchoolRejText;
	}

	private String priorSchoolTermRejectedText3(LetterFields letterFields) {

		String priorSchoolRejText = "";

		if (letterFields.isPriorSchoolTermRejected()) {

			priorSchoolRejText = String
					.format("-	The dates you provided are before you are eligible to add this dependent");
		}
		return priorSchoolRejText;
	}

	private String priorSchoolTermRejectedText4(LetterFields letterFields) {

		String priorSchoolRejText = "";

		if (letterFields.isPriorSchoolTermRejected()) {

			priorSchoolRejText = String.format("-	We received discrepant information");
		}
		return priorSchoolRejText;
	}
}
