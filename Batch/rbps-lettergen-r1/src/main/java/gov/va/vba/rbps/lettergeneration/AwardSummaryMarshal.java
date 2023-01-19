/*
 * AwardSummary.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.lettergeneration;



import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;




/**
 *      This class is used to build the "Your award amount" table in
 *      the approve and approve_deny notification letters.  It's just
 *      a simplification of the data fed back by the AWARDS web service.
 *      We want the code in the velocity template to be very simple, this
 *      aids in that.
 */
public class AwardSummaryMarshal {


    private String                  formattedTotalVABenefit;
    private String                  formattedAmountWithheld;
    private String                  formattedAmountPaid;
    private String                  paymentChangeDate;
    private String					firstFormattedReason;
    private List<String> 			restOfFormattedReasons;
    private List<String> 			formattedReasons = new ArrayList<String>();

    public AwardSummaryMarshal() {
        //      Do nothing.
    }

    
    public String getFormattedTotalVABenefit() {
    	
    	return formattedTotalVABenefit;
    }
    public void setFormattedTotalVABenefit( String formattedTotalVABenefit ) {
    	
    	this.formattedTotalVABenefit = formattedTotalVABenefit;
    }

    
    public String getFormattedAmountWithheld() {
    	
    	return formattedAmountWithheld;
    }
    public void setFormattedAmountWithheld( String formattedAmountWithheld ) {
    	
    	this.formattedAmountWithheld = formattedAmountWithheld;
    }

    
    public String getFormattedAmountPaid() {
    	
    	return formattedAmountPaid;
    }
    public void setFormattedAmountPaid( String formattedAmountPaid ) {
    	
    	this.formattedAmountPaid = formattedAmountPaid;
    }
    
    
    public String getFirstFormattedReason() {
    	
    	return firstFormattedReason;
    }
    public void setFirstFormattedReason( String firstFormattedReason ) {
    	
    	this.firstFormattedReason = firstFormattedReason;
    }
    
    
    public List<String> getRestOfFormattedReasons() {
    	
    	return restOfFormattedReasons;
    }
    public void setRestOfFormattedReasons( List<String> restOfFormattedReasons) {
    	
    	this.restOfFormattedReasons = restOfFormattedReasons;
    }    
    
    
    
    public String getPaymentChangeDate() {
    	
    	return paymentChangeDate;
    }
    public void setPaymentChangeDate( String paymentChangeDate ) {
    	
    	this.paymentChangeDate = paymentChangeDate;
    }
    
    
    public void addFormattedReason( String formattedReason ) {
    	
    	 formattedReasons.add( formattedReason );
    }  
    public List<String> getFormattedReasons() {
    	
    	return formattedReasons;
    }
    public void setFormattedReasons( List<String> formattedReasons) {
    	
    	this.formattedReasons = formattedReasons;
    }  
    
    
    
}
