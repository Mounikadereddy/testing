package gov.va.vba.rbps.lettergeneration.docgen.transformer;

import gov.va.bip.docgen.service.plugin.rbps.api.common.AwardSummaryRequest;
import gov.va.bip.docgen.service.plugin.rbps.api.common.BenefitInformationRequest;
import gov.va.bip.docgen.service.plugin.rbps.api.common.DisplayAddressRequest;
import gov.va.bip.docgen.service.plugin.rbps.api.common.ExplanationPaymentRequest;
import gov.va.bip.docgen.service.plugin.rbps.api.common.LetterFieldsRequest;
import gov.va.bip.docgen.service.plugin.rbps.api.common.RbpsContentBase;
import gov.va.vba.rbps.coreframework.dto.DisplayAddressVO;
import gov.va.vba.rbps.coreframework.util.SimpleDateUtils;
import gov.va.vba.rbps.lettergeneration.AwardSummaryMarshal;
import gov.va.vba.rbps.lettergeneration.BenefitInformation;
import gov.va.vba.rbps.lettergeneration.ExplanationPayment;
import gov.va.vba.rbps.lettergeneration.LetterFieldsMarshal;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LetterFieldsMarshalTransformer {

    public static  LetterFieldsRequest transformLetterFields(LetterFieldsMarshal letterFieldsMarshal) {
        LetterFieldsRequest letterFields = new LetterFieldsRequest();
        letterFields.setToday(letterFieldsMarshal.getToday());
        letterFields.setClaimDate(letterFieldsMarshal.getClaimDate());
        letterFields.setFiduciaryAttentionText(letterFieldsMarshal.getFiduciaryAttentionText());
        letterFields.setFirstLast(letterFieldsMarshal.getFirstLast());
        letterFields.setFmLast(letterFieldsMarshal.getFmLast());
        letterFields.setLine1(letterFieldsMarshal.getLine1());
        letterFields.setLine2(letterFieldsMarshal.getLine2());
        letterFields.setLine3(letterFieldsMarshal.getLine3());
        letterFields.setCityStateCountryZip(letterFieldsMarshal.getCityStateCountryZip());
        letterFields.setFileNumber(letterFieldsMarshal.getFileNumber());
        letterFields.setLastFirst(letterFieldsMarshal.getLastFirst());
        letterFields.setSalutation(letterFieldsMarshal.getSalutation());
        letterFields.setForm1(letterFieldsMarshal.getForm1());
        letterFields.setForm2(letterFieldsMarshal.getForm2());
        letterFields.setHereIsWhatToDoCallUs(letterFieldsMarshal.getHereIsWhatToDoCallUs());
        letterFields.setPoaNoPoaText(letterFieldsMarshal.getPoaNoPoaText());
        letterFields.setSendCcTo(letterFieldsMarshal.getSendCcTo());
        
        if(letterFieldsMarshal.getParticipantAddressPostalVO() != null){
        	letterFields.setPoaAddress(transformPoaAddress(letterFieldsMarshal.getParticipantAddressPostalVO()));
        }else{
        	letterFields.setPoaAddress(transformPoaAddressNull());
        }
        letterFields.setWithholdingType(letterFieldsMarshal.getWithholdingType());
        letterFields.setWithholdingPara(letterFieldsMarshal.getWithholdingPara());
        letterFields.setHasRetiredPay(letterFieldsMarshal.isHasRetiredPay());
        letterFields.setHasSeparationPay(letterFieldsMarshal.isHasSeparationPay());
        letterFields.setHasSeverancePay(letterFieldsMarshal.isHasSeverancePay());
        letterFields.setSeparationWithholdingPara(letterFieldsMarshal.getSeparationWithholdingPara());
        letterFields.setSeveranceWithholdingPara(letterFieldsMarshal.getSeveranceWithholdingPara());
        letterFields.setBlindStatus(letterFieldsMarshal.isHasBlindStatus());
        return letterFields;
    }
    
    /**
     * Getting PoaAddress from LetterFieldsMarshal
     * @param participantAddressPostalVO
     * @return
     */
    public static DisplayAddressRequest transformPoaAddress(DisplayAddressVO participantAddressPostalVO){
    	DisplayAddressRequest poaAddress = new DisplayAddressRequest();
		poaAddress.setDisplayAddressLine1(participantAddressPostalVO.getDisplayAddressLine1());
		poaAddress.setDisplayAddressLine2(participantAddressPostalVO.getDisplayAddressLine2());
		poaAddress.setDisplayAddressLine3(participantAddressPostalVO.getDisplayAddressLine3());
		poaAddress.setDisplayAddressLine4(participantAddressPostalVO.getDisplayAddressLine4());
		poaAddress.setDisplayAddressLine5(participantAddressPostalVO.getDisplayAddressLine5());
		poaAddress.setDisplayAddressLine6(participantAddressPostalVO.getDisplayAddressLine6());
		poaAddress.setDisplayAddressLine7(participantAddressPostalVO.getDisplayAddressLine7());
    	
    	return poaAddress;
    }
    /**
     * Getting PoaAddress from LetterFieldsMarshal
     * @param participantAddressPostalVO
     * @return
     * this code is for covering bug in docgen. once they fix that. we will remove it.
     */
    public static DisplayAddressRequest transformPoaAddressNull(){
    	DisplayAddressRequest poaAddress = new DisplayAddressRequest();
		poaAddress.setDisplayAddressLine1(" ");
		
    	return poaAddress;
    }

    /**
     * @param summary AwardSummaryRequest object
     * @return converted award summary
     */
    public static AwardSummaryRequest transformAwardSummaryMarshal(AwardSummaryMarshal summary) {
        AwardSummaryRequest awardSummary = new AwardSummaryRequest();
        String amountPaidstr = summary.getFormattedAmountPaid();
        String amountWithheldStr = summary.getFormattedAmountWithheld();
        String vaBenefitstr = summary.getFormattedTotalVABenefit();

        awardSummary.setAmountPaid(new BigDecimal(amountPaidstr.replace("$", "")));
        awardSummary.setReasons(summary.getFormattedReasons());
        awardSummary.setAmountWithheld(new BigDecimal(amountWithheldStr.replace("$", "")));
        awardSummary.setPaymentChangeDate(summary.getPaymentChangeDate());
        awardSummary.setTotalVABenefit(new BigDecimal(vaBenefitstr.replace("$", "")));
        return awardSummary;
    }

    /**
     * @param awardSummaries list of AwardSummaryMarshal objects
     * @return converted list of AwardSummaryRequest objects
     */
    public static List<AwardSummaryRequest> transformAwardSummaryMarshals(List<AwardSummaryMarshal> awardSummaries) {
        List<AwardSummaryRequest> converted = new ArrayList<>();
        for (AwardSummaryMarshal summary : awardSummaries) {
            converted.add(transformAwardSummaryMarshal(summary));
        }
        return converted;
    }

    /**
     * @param base RbpsContentBase request
     * @param letterFieldsMarshal LetterFieldsMarshal to transform
     */
    public static void transformDefaults(RbpsContentBase base, LetterFieldsMarshal letterFieldsMarshal) {

        base.setAwardSummaries(
                transformAwardSummaryMarshals(new ArrayList<>(letterFieldsMarshal.getApprovals()))
        );
        
        base.setBenefitInformation(
        		transformBenefitInformationMarshals(new ArrayList<>(letterFieldsMarshal.getBenefitInformation()))
        );
        base.setBenefitDetailList(
        		(letterFieldsMarshal.getBenefitDetailList())
        );
        
       base.setExplanationPayment(
        		transformExplanationPaymentMarshals(new ArrayList<>(letterFieldsMarshal.getExplanationPayment()))
        );
        base.setLetterFields(
                transformLetterFields(letterFieldsMarshal)
        );
        base.setCheckPayment(letterFieldsMarshal.getPaymentType().getCheckPayment());
        base.setOverPymt(letterFieldsMarshal.getOverPymt().getOverPayment());
        base.setTotalDependentsSentence(letterFieldsMarshal.getTotalDependentsSentence());
        base.setYourPaymentIncludes(letterFieldsMarshal.getYourPaymentIncludes());
        base.setPensionGrantNoIncome(letterFieldsMarshal.getPensionGrantNoIncome());
        base.setWeMadeThisDecisionText(letterFieldsMarshal.getWeMadeThisDecisionText());
        base.setPriorSchoolTermRejectedText(letterFieldsMarshal.getPriorTerm().getPriorSchoolTermRejectedText());
        base.setPriorSchoolTermRejectedText1(letterFieldsMarshal.getPriorTerm().getPriorSchoolTermRejectedText1());
        base.setPriorSchoolTermRejectedText2(letterFieldsMarshal.getPriorTerm().getPriorSchoolTermRejectedText2());
        base.setPriorSchoolTermRejectedText3(letterFieldsMarshal.getPriorTerm().getPriorSchoolTermRejectedText3());
        base.setPriorSchoolTermRejectedText4(letterFieldsMarshal.getPriorTerm().getPriorSchoolTermRejectedText4());
    }
    /**
     * @param summary BenefitInformationRequest object
     * @return converted Benefit Information Request
     */
    public static BenefitInformationRequest transformBenefitInformation(BenefitInformation benefitInformation) {
    	BenefitInformationRequest benefit = new BenefitInformationRequest();
    	
    	
    	Date 	paymentchangeDate		=	benefitInformation.getEffectiveDate();
    	if(paymentchangeDate !=null){
		String 	paymentChangeDateStr 	= 	SimpleDateUtils.convertDate( "", "MMM d, yyyy", paymentchangeDate );
	    benefit.setEffectiveDate(paymentChangeDateStr);
    	}
    	else{
    		benefit.setEffectiveDate("");
    	}
    		benefit.setDependentType(benefitInformation.getDependentType());
    		benefit.setFullName(benefitInformation.getFullName());
        return benefit;
    }

    /**
     * @param  list of BenefitInformation objects
     * @return converted list of BenefitInformationRequest objects
     */
    public static List<BenefitInformationRequest> transformBenefitInformationMarshals(List<BenefitInformation> benefitInformation) {
        List<BenefitInformationRequest> converted = new ArrayList<>();
        for (BenefitInformation benefit : benefitInformation) {
            converted.add(transformBenefitInformation(benefit));
        }
        return converted;
    }
    /**
     * @param summary ExplanationPaymentRequest object
     * @return converted Explanation Payment Request
     */
    public static ExplanationPaymentRequest transformExplanationPayment(ExplanationPayment explanationPayment) {
    	
    	ExplanationPaymentRequest payment = new ExplanationPaymentRequest();
    	
    	
    	Date 	paymentchangeDate		=	explanationPayment.getPaymentDate();
    	if(paymentchangeDate !=null){
    		String 	paymentChangeDateStr 	= 	SimpleDateUtils.convertDate( "", "MMM d, yyyy", paymentchangeDate );
    		  payment.setPaymentDate(paymentChangeDateStr);
    	}
    	else{
    		payment.setPaymentDate("");
    	}
    	payment.setFullName(explanationPayment.getFullName());
        return payment;
    }

    /**
     * @param  list of ExplanationPayment objects
     * @return converted list of ExplanationPaymentRequest objects
     */
    public static List<ExplanationPaymentRequest> transformExplanationPaymentMarshals(List<ExplanationPayment> explanationPayment) {
        List<ExplanationPaymentRequest> converted = new ArrayList<>();
        for (ExplanationPayment payment : explanationPayment) {
            converted.add(transformExplanationPayment(payment));
        }
        return converted;
    }
}
