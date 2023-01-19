/*
 * AwardSummary.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.lettergeneration;


//import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.util.CommonUtils;
//import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.coreframework.util.SimpleDateUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.WordUtils;
import org.apache.commons.lang.builder.ToStringBuilder;



/**
 *      This class is used to build the "Your award amount" table in
 *      the approve and approve_deny notification letters.  It's just
 *      a simplification of the data fed back by the AWARDS web service.
 *      We want the code in the velocity template to be very simple, this
 *      aids in that.
 */
public class AwardSummary {


//    private static Logger logger = Logger.getLogger( AwardSummary.class);

//    private CommonUtils                 utils;
//    private SimpleDateUtils             dateUtils   = new SimpleDateUtils();
//    private LogUtils                    logUtils    = new LogUtils( logger, true );

    private BigDecimal					totalVABenefit;
    private BigDecimal					amountWithheld;
    private BigDecimal                  amountPaid;
    private Date                        paymentChangeDate;
    private List<AwardReason>           reasons;
//    private boolean                     logit       = true;


    public AwardSummary() {
        //      Do nothing.
    }


    /**
     *      To make it easy to make AwardSummaries in tests...
     */
    public AwardSummary( final double                       totalVABenefit,
    					 final double						amountWithheld,
    					 final double						amountPaid,
                         final Date                         paymentChangeDate,
                         final String                       reason ) {


        this.totalVABenefit        	= new BigDecimal( totalVABenefit );
        this.amountWithheld        	= new BigDecimal( amountWithheld );
        this.amountPaid        	   	= new BigDecimal( amountPaid );
        this.paymentChangeDate     	= paymentChangeDate;
        this.reasons            	= new ArrayList<AwardReason>();
    }

    
    public String getFormattedTotalVABenefit() {

        if ( totalVABenefit == null ) {

        	totalVABenefit = new BigDecimal( 0 );
        }

        return String.format( "$%.2f", totalVABenefit.doubleValue() );
    }


    public String getFormattedAmountWithheld() {

        if ( amountWithheld == null ) {

        	amountWithheld = new BigDecimal( 0 );
        }

        return String.format( "$%.2f", amountWithheld.doubleValue() );
    }


    public String getFormattedAmountPaid() {

        if ( amountPaid == null ) {

        	amountPaid = new BigDecimal( 0 );
        }

        return String.format( "$%.2f", amountPaid.doubleValue() );
    }


    public boolean hasClaimId() {

        for ( AwardReason reason : reasons ) {

            if ( reason.hasClaimId() ) {

                return true;
            }
        }

        return false;
    }


    public List<String> getRestOfFormattedReasons() {

        List<String>    formattedReasons    = new ArrayList<String>();
        boolean         first               = true;

        for ( AwardReason reason : reasons ) {

            if ( first ) {

                first = false;
                continue;
            }

            formattedReasons.add( reason.getFormattedReason( inFuture() ) );
        }

        return formattedReasons;
    }
    public String getFirstFormattedReason() {

        return reasons.get(0).getFormattedReason( inFuture() );
    }
    public List<AwardReason> getReasons() {

        return reasons;
    }
    public void setReasons( final List<AwardReason> reasons ) {

        this.reasons = reasons;
    }
    public int getRowspan() {

        return reasons.size();
    }


    public String getReason() {

        String  reason = "";

        for ( AwardReason awardReason : reasons ) {

            reason += awardReason.getFormattedReason( inFuture() );
        }

        return reason;
    }


    //
    //      This is only necessary because awards has an issue where
    //      it's associating the spouse dependent with the wrong
    //      award line reason.
    //
    public void filterReasonsWithMissingDependents() {

        List<AwardReason>   toBeRemoved = new ArrayList<AwardReason>();

        for ( AwardReason awardReason : reasons ) {

            if ( ! awardReason.hasTranslationWithMissingDependent() ) {

                continue;
            }

//            logUtils.log( "Removing award reason " + awardReason );
            toBeRemoved.add( awardReason );
        }

        reasons.removeAll( toBeRemoved );
    }


    public static String accumulateApprovalNames( final List<AwardReason>   reasons,
                                                  final List<String>        approvalChildNames ) {

    	SimpleDateFormat 	formatter			=	new SimpleDateFormat("MMM dd, yyyy");
    	String 				approvalSpouseName 	= null;

        for ( AwardReason reason : reasons ) {

            if ( ! reason.isGrant() ) {

//                new AwardSummary().logUtils.log( "Skipping looking at non-grant reason for approval names: " + reason );
                continue;
            }

            Date	effectiveDate	= reason.getEffectiveDate();
            String  formattedDate	= formatter.format( effectiveDate );
        	String name				= WordUtils.capitalize( reason.getFirstName().toLowerCase() );
        	String nameWithDate 	= name + " effective " + formattedDate;
        	
            if ( reason.isChild() && reason.hasClaimId() ) {
            	
                approvalChildNames.add( nameWithDate );
            }
            else if ( ! reason.isChild() && reason.hasClaimId() ) {

                approvalSpouseName = nameWithDate;
            }
        }

        return approvalSpouseName;
    }


    public List<AwardSummary> summaryToList() {

        List<AwardSummary>  summaries = new ArrayList<AwardSummary>();

        if ( ! hasBothApprovalAndDenial() ) {

            summaries.add( this );
            return summaries;
        }

        List<AwardReason>   denials         = removeDenials();
        AwardSummary        denialSummary   = new AwardSummary();
        denialSummary.setReasons( denials );

        summaries.add( this );
        summaries.add( denialSummary );

        return summaries;
    }


    private List<AwardReason> removeDenials() {

        List<AwardReason>   denials = new ArrayList<AwardReason>();

        for ( AwardReason reason : reasons ) {

            if ( reason.isDenial() ) {

                denials.add( reason );
            }
        }

        reasons.removeAll( denials );

        return denials;
    }


    public boolean hasBothApprovalAndDenial() {

        return hasGrantOrRemoval() && isDenial();
    }


    public boolean isEmpty() {

        for ( AwardReason   reason : reasons ) {

            if ( ! reason.isEmpty() ) {

                return false;
            }
        }

        return true;
    }


    public boolean hasGrantOrRemoval() {

        for ( AwardReason reason : reasons ) {

            if ( reason.isGrantOrRemoval() ) {

                return true;
            }
        }

        return false;
    }


    public boolean isGrantOrRemoval() {

        for ( AwardReason reason : reasons ) {

            if ( reason.isDenial() ) {

                return false;
            }
        }

        return true;
    }


    public boolean isGrant() {

        for ( AwardReason reason : reasons ) {

            if ( ! reason.isGrant() ) {

                return false;
            }
        }

        return true;
    }


    public boolean isDenial() {

        for ( AwardReason reason : reasons ) {

            if ( reason.isDenial() ) {

                return true;
            }
        }

        return false;
    }

    public BigDecimal getTotalVABenefit() {

        return totalVABenefit;
    }
    public void setTotalVABenefit( final BigDecimal totalVABenefit ) {

        this.totalVABenefit = totalVABenefit;
    }


    public BigDecimal getAmountWithheld() {

        return amountWithheld;
    }
    public void setAmountWithheld( final BigDecimal amountWithheld ) {

        this.amountWithheld = amountWithheld;
    }


    public BigDecimal getAmountPaid() {

        return amountPaid;
    }
    public void setAmountPaid( final BigDecimal amountPaid ) {

        this.amountPaid = amountPaid;
    }

   

    public Date getPaymentChangeDate() {

        return paymentChangeDate;
    }
    public void setPaymentChangeDate( final Date paymentChangeDate ) {

        this.paymentChangeDate = paymentChangeDate;
    }
    public boolean inFuture() {

        if ( getPaymentChangeDate() == null ) {

            return false;
        }

        Date        now                 = SimpleDateUtils.truncateToDay( new Date() );
        Date        roundedPaymentDate  = SimpleDateUtils.truncateToDay( getPaymentChangeDate() );

        return roundedPaymentDate.after( now );
    }


    @Override
    public String toString() {

        return new ToStringBuilder( this )
                .append( "total VA Benefit",	getTotalVABenefit() )
                .append( "amount Withheld",     getAmountWithheld() )
                .append( "amount paid",         getAmountPaid() )
                .append( "payment date",    	getPaymentChangeDate() )
                .append( "in future",       	inFuture() )
                .append( "reasons",         	CommonUtils.stringBuilder( getReasons() ) )
                .toString();
    }
}
