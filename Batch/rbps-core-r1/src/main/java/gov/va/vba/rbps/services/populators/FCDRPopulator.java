package gov.va.vba.rbps.services.populators;

import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.claimvalidator.GenericUserInformationValidatorImpl;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.exception.RbpsRuntimeException;
import gov.va.vba.rbps.coreframework.util.CommonUtils;
import gov.va.vba.rbps.coreframework.util.SimpleDateUtils;
import gov.va.vba.rbps.services.ws.client.handler.share.GetDecisionsAtIssueWSHandler;
import gov.va.vba.rbps.services.ws.client.mapping.share.ratingComparison.CombinedPercentVO;
import gov.va.vba.rbps.services.ws.client.mapping.share.ratingComparison.CompareByDateRangeResponse;
import gov.va.vba.rbps.services.ws.client.mapping.share.ratingComparison.GetDecisionsAtIssueResponse;
import gov.va.vba.rbps.services.ws.client.mapping.share.ratingComparison.RatingComparisonServiceReturnVO;
import gov.va.vba.rbps.services.ws.client.mapping.share.ratingComparison.RbaPrfil;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;


public class FCDRPopulator {


    private static Logger logger = Logger.getLogger(FCDRPopulator.class);

    private GetDecisionsAtIssueWSHandler	wsHandler					= new GetDecisionsAtIssueWSHandler();
    private DecisionsAtIssuePopulator		populator					= new DecisionsAtIssuePopulator();
    

    public void populateRatingDates( final CompareByDateRangeResponse ratingDatesResponse, final RbpsRepository repository ) {

        RatingComparisonServiceReturnVO     retVal	= 	ratingDatesResponse.getReturn();

        try {
	        checkForRejectReason( retVal );
	        findAndSetRatingEffectiveDate( retVal, repository );
	        findCurrentSCRating( retVal, repository );
	        saveRatings(retVal, repository);

	    	if ( repository.getVeteran().getServiceConnectedDisabilityRating() < 30 ) {
	    		
	    		CommonUtils.log( logger, "Veteran combined rating less than 30 percent." );
	    		return;
	    	}
	        assignRatingDate( retVal, repository );
	        
	      //SR# 723083
	        //throwExceptionIfRatingEffectiveDateIsNull( retVal, repository );
	        handleRatingProfiles( retVal, repository );
	        reviewRatingDate( repository );
	        CalculateAndSetAllowableDate(repository);

        } catch ( Exception ex ) {
        	String message = getExceptionMessage( ex );
        	CommonUtils.log( logger, message );
        	throw new RbpsRuntimeException( message );
        }
        
        return;
    }
 
    
    public void checkForRejectReason( final RatingComparisonServiceReturnVO     retVal ) {

        String      rejectReason    = retVal.getRejectReason();

        if ( StringUtils.isBlank( rejectReason ) ) {

            return;
        }
        
        if ( rejectReason.toUpperCase().contains( "Failure to Prosecute".toUpperCase() ) ) {
        	
        	return;
        }
        
        throw new RbpsRuntimeException( GenericUserInformationValidatorImpl.FAIL + rejectReason );
    }
    

    
    public void findAndSetRatingEffectiveDate( final RatingComparisonServiceReturnVO retVal, final RbpsRepository repository ) {
    	
    	if ( retVal.getEarliestDependencyEligibilityDate() == null ) {
    		
    		repository.getVeteran().setRatingEffectiveDate( null );
    		return;
    	}
    	
    	repository.getVeteran().setRatingEffectiveDate( SimpleDateUtils.xmlGregorianCalendarToDay( retVal.getEarliestDependencyEligibilityDate() ) );
    }
    
    
    public void findCurrentSCRating( final RatingComparisonServiceReturnVO	retVal, final RbpsRepository repository ) {

    	
    	List<CombinedPercentVO>	combinedPercentVOList = retVal
														.getCombinedPercentCurrentList()
														.getCombinedPercentCurrent();
    	
    	if( CollectionUtils.isEmpty(combinedPercentVOList) && retVal.getEarliestDependencyEligibilityDate() != null ) {
    		
    		throw new RbpsRuntimeException( "Unexpected data when calling RatingComparisonService; no CombinedPercent but shows as eligible for Dependency." );
    	}
    	
    	
		Date				 							latestEffectiveDt  = null;
		Date				lessThan30EffectiveDateAfterLatestEffectiveDate = null;	 
		
    	for ( CombinedPercentVO combinedPercent : combinedPercentVOList ) {
    		
    		XMLGregorianCalendar effectiveDate 		= combinedPercent.getEffectiveDate();
    		Date				 effectiveDt		= SimpleDateUtils.xmlGregorianCalendarToDay( effectiveDate );
    		int 				 scPercent			= combinedPercent.getServiceConnCombinedPct();
    		
    		if ( scPercent >= 30 ) {
    			
        		if ( latestEffectiveDt == null ) {
        			latestEffectiveDt = effectiveDt;
        			repository.getVeteran().setServiceConnectedDisabilityRating( scPercent * 1.0 );
        		}
    			
    			if ( effectiveDt.after( latestEffectiveDt ) && SimpleDateUtils.isOnOrBefore( effectiveDt,  new Date() ) ) { 
    				latestEffectiveDt = effectiveDt;
    				repository.getVeteran().setServiceConnectedDisabilityRating( scPercent * 1.0 );
    			}
    			
    		} else { //SR# 723083
    			
        		if ( lessThan30EffectiveDateAfterLatestEffectiveDate == null ) {
        			lessThan30EffectiveDateAfterLatestEffectiveDate = effectiveDt;
        		}
        		
        		if ( latestEffectiveDt == null) {
        			latestEffectiveDt = effectiveDt;
        		}
        		
        		if ( effectiveDt.after(latestEffectiveDt) && ( ! effectiveDt.after(lessThan30EffectiveDateAfterLatestEffectiveDate) )) {
        			lessThan30EffectiveDateAfterLatestEffectiveDate = effectiveDt;
        		}
    			
    		}
    	} 
    	
    	//SR# 723083
    	repository.setLessThan30EffectiveDateAfterLatestEffectiveDate(lessThan30EffectiveDateAfterLatestEffectiveDate); 
    }
    
    // JR - 09-04-2018 - SR # 813152 - save all combined percent ratings in repository
    public void saveRatings( final RatingComparisonServiceReturnVO	retVal, final RbpsRepository repository ) {
        
    	List<CombinedPercentVO>	combinedPercentVOList = retVal.getCombinedPercentCurrentList().getCombinedPercentCurrent();

    	if( CollectionUtils.isEmpty(combinedPercentVOList) && retVal.getEarliestDependencyEligibilityDate() != null ) {

    		throw new RbpsRuntimeException( "Unexpected data when calling RatingComparisonService; no CombinedPercent but shows as eligible for Dependency." );
    	}

    	TreeMap<Date, Integer> ratingMap = new TreeMap<Date, Integer>();

    	for ( CombinedPercentVO combinedPercent : combinedPercentVOList ) {
    		
    		XMLGregorianCalendar effectiveDate 		= combinedPercent.getEffectiveDate();
    		Date				 effectiveDt		= SimpleDateUtils.xmlGregorianCalendarToDay( effectiveDate );
    		int 				 scPercent			= combinedPercent.getServiceConnCombinedPct();
   
    		ratingMap.put(effectiveDt, scPercent);
    	}
    	
    	repository.setRatingMap(ratingMap);
    }
    
    public void throwExceptionIfRatingEffectiveDateIsNull( final RatingComparisonServiceReturnVO	retVal, final RbpsRepository repository ) {
    	
    	
    	if ( retVal.getEarliestDependencyEligibilityDate() == null && repository.getVeteran().getServiceConnectedDisabilityRating() >= 30 ) {
    		
    		throw new RbpsRuntimeException( "Rating Effective Date returned for Veteran Disability Rating >= 30 percent is null" );
    	}
    }
    
    
    public void handleRatingProfiles( final RatingComparisonServiceReturnVO    retVal, final RbpsRepository repository ) {
    	
    	if ( isRatingProfileEmpty( retVal ) ) {
    		setFCDRToNullIfRatingProfileIsEmpty( repository );
    		return;
    	}

    	List<RbaPrfil>	ratingProfileList	= retVal.getRatingProfileList().getRatingProfile();

    	for ( RbaPrfil	ratingProfile	:	ratingProfileList ) {

    		Date promulgationDate = SimpleDateUtils.truncateToDay( SimpleDateUtils.xmlGregorianCalendarToDay( ratingProfile.getPrmlgnDt() ) ); 
    		
    		// JR - 09-19-2018 - SR # 824272 - Remove RBPS Exception Rating 366 to 372 days past 
    		//checkIfPromulgatedBetween366And372DaysInclusive( repository.getVeteran().getClaim().getReceivedDate(), promulgationDate );
    		if ( areThereRatingWithIn366Days( ratingProfile.getPrmlgnDt(), repository )) {
    			logger.debug("Found rating date within 366 days. Populate fcdr based on decision at issue");
    			populateFcdrBasedOnDecisionAtIssue( ratingProfile, repository );
    		}
    	}
    }

    
    private void reviewRatingDate( final RbpsRepository repository ) {
    	
    	if ( repository.getVeteran().getFirstChangedDateofRating() != null ){
    		
    		return;
    	}
    	
    	Date ratingDate		   = repository.getVeteran().getRatingDate();
    	Date claimReceivedDate = repository.getVeteran().getClaim().getReceivedDate();
    	Date oneYearOfDOC	   = SimpleDateUtils.addDaysToDate(-366, claimReceivedDate);
    	
    	// if there were ratings with in one year of DOC, 
    	//and FCDR could not be determined because there was no increase
    	//then Rating Date is set before one year of DOC
    	
    	if ( ratingDate.after(oneYearOfDOC) ) {
    		
    		Date newRatingDate = SimpleDateUtils.addDaysToDate(-400, claimReceivedDate);
     		
    		String      message = String.format( "Rating date %s which was with in one year of date of claim with no FCDR was set to a date 400 days before date of claim  %s", 
    											SimpleDateUtils.standardShortLogDayFormat(ratingDate), 
    											SimpleDateUtils.standardShortLogDayFormat(newRatingDate) );
    		
       		repository.getVeteran().setRatingDate( newRatingDate );
            CommonUtils.log( logger, message );
    		
    	}
    }

    
    private boolean isRatingProfileEmpty( RatingComparisonServiceReturnVO retVal ) {
    	
    	if ( retVal == null ) {
    		return true;
    	}
    	
    	if ( retVal.getRatingProfileList() == null ) {
    		return true;
    	}
    	
    	if ( CollectionUtils.isEmpty( retVal.getRatingProfileList().getRatingProfile() ) ) {
    		return true;
    	}
    	
    	return false;
    }
    
    
    private void setFCDRToNullIfRatingProfileIsEmpty( final RbpsRepository repository ) {
    	
    	repository.getVeteran().setFirstChangedDateofRating( null );
    }

    
	private void populateFcdrBasedOnDecisionAtIssue( final RbaPrfil ratingProfile, final RbpsRepository repository ) {

		GetDecisionsAtIssueResponse	response	= callDecisionsAtIssueWebServiceForRatingProfile( ratingProfile, repository );
		if ( response.getReturn() != null ) {
			populator.determineFCDR( repository, response );
		}
	}
    
    
    public void checkIfPromulgatedBetween366And372DaysInclusive( Date dateOfClaim, Date promulgationDate ) {
    	
        Date	threeSixtySixDaysBeforeClaimDate     = SimpleDateUtils.truncateToDay( SimpleDateUtils.addDaysToDate(-366, dateOfClaim) );
    	Date	threeSeventyTwoDaysBeforeClaimDate    = SimpleDateUtils.truncateToDay( SimpleDateUtils.addDaysToDate(-372, dateOfClaim) );
        
        if ( SimpleDateUtils.isOnOrBefore( threeSeventyTwoDaysBeforeClaimDate, promulgationDate ) && 
        		SimpleDateUtils.isOnOrAfter( threeSixtySixDaysBeforeClaimDate, promulgationDate ) ) {
        	
        	throwExceptionForPromulgationBetween366And372DaysInclusive( promulgationDate, 
        																threeSixtySixDaysBeforeClaimDate, 
        																threeSeventyTwoDaysBeforeClaimDate, 
        																dateOfClaim );
        }
    }
    
    public GetDecisionsAtIssueResponse callDecisionsAtIssueWebServiceForRatingProfile( RbaPrfil	ratingProfile, final RbpsRepository repository ) {
    	
    	XMLGregorianCalendar	profileDate 	= ratingProfile.getCompId().getPrfilDt();

    	GetDecisionsAtIssueResponse	response	= wsHandler.getDecisionsAtIssue( profileDate, repository );
    	
    	return response;
    }
    
    private void assignRatingDate( final RatingComparisonServiceReturnVO    retVal, final RbpsRepository repository ) {
    	
        if ( null == retVal.getPromulgationDt() ) {

            String      message = GenericUserInformationValidatorImpl.FAIL + "Rating date not provided for Veteran >= 30 percent.";
            CommonUtils.log( logger, message );
            throw new RbpsRuntimeException( message );
        }

        Date    promulgationDate = SimpleDateUtils.truncateToDay( SimpleDateUtils.xmlGregorianCalendarToDay( retVal.getPromulgationDt() ) );
        
        
//    	if ( promulgationDate.after( repository.getVeteran().getClaim().getReceivedDate() ) ) {
//    		
//    		throw new RbpsRuntimeException( String.format( "Rating for Veteran %s %s promulgated after date of claim, please review", 
//    														repository.getVeteran().getFirstName(), repository.getVeteran().getLastName() ) );
//    	}
    	
        repository.getVeteran().setRatingDate( promulgationDate );
    }

    
    public void throwExceptionForPromulgationBetween366And372DaysInclusive( final Date promulgationDate, 
    																		final Date threeSixtySevenDaysBeforeClaimDate, 
    																		final Date threeSeventyTwoDaysBeforeClaimDate, 
    																		final Date claimDate ) {
    	
    	String message 	=	String.format( "Rating Profile %s promulgated between %s and %s :  claim date %s ", 
    																		promulgationDate.toString(), 
    																		threeSixtySevenDaysBeforeClaimDate.toString(), 
    																		threeSeventyTwoDaysBeforeClaimDate.toString(), 
    																		claimDate.toString() );
    	CommonUtils.log( logger, message );
    	
    	String exceptionMessage = "Auto Dependency Processing Reject Reason - Date of Veteran\'s rating is between 366 to 372 days in the past.  " +
    							  "Please review date of notification letter to determine correct date to add dependent(s). ";
    	
    	throw new RbpsRuntimeException( exceptionMessage );
    	
    }
  
    
    public void throwExceptionForNoRatingEffectiveDate() {
    	
    	String message = "Veteran must have a Rating with a Rating effective date.";
    	throw new RbpsRuntimeException( message );
    }
    
    public void checkForNoProfileDateInRatingProfile( RbaPrfil	ratingProfile ) {
    	
        if ( null != ratingProfile.getCompId().getPrfilDt() ) {
            return;
        }

        CommonUtils.log( logger, "There are missing rating profile dates in Rating Profiles." );
        String      msg = " Rating Profile missing profile date.";
        throw new RbpsRuntimeException( msg );
    }


    
    private boolean areThereRatingWithIn366Days( final XMLGregorianCalendar	promulgationDate, final RbpsRepository repository  ) {
    	
    	Date	dateOfClaim							 = SimpleDateUtils.truncateToDay( repository.getVeteran().getClaim().getReceivedDate() );
    	Date	threeSixtySevenDaysBeforeClaimDate   = SimpleDateUtils.truncateToDay( SimpleDateUtils.addDaysToDate(-367, dateOfClaim) );
    	Date	today								 = new Date();
    	
		boolean ifThereAreRatingWithIn366Days = false;
    		
    		Date promulgDate 	= SimpleDateUtils.xmlGregorianCalendarToDay( promulgationDate );
    		
    		if ( promulgDate.after( threeSixtySevenDaysBeforeClaimDate ) && SimpleDateUtils.isOnOrBefore( promulgDate, today ) ) {
    			ifThereAreRatingWithIn366Days = true;
    		}
		
		return ifThereAreRatingWithIn366Days;
    }

    
    private String getExceptionMessage( Exception ex ){
    	
    	if (  StringUtils.isNotEmpty( ex.getMessage() ) ) {
    		
    		return ex.getMessage();
    	}
    	
    	if ( ( ex.getCause() != null ) && StringUtils.isNotEmpty( ex.getCause().getMessage() ) ) {
    		
    		return ex.getCause().getMessage();
    	}
    	return "no message available";
    }
    
    
    public void setDecisionsAtIssueWSHandler( GetDecisionsAtIssueWSHandler wsHandler ) {
    	this.wsHandler = wsHandler;
    }
 
   /*
	*	Determine Allowable Date as follows:
	*	a.	Determine claim date minus one year (365 days? leap year?), 
	*	b.	Determine if there was a recent rating (any rating promulgated between today and claim date less 365 days 
	*		a.	If so determine FCDR
	*		b.	If there is no FCDR, ignore FCDR.
	*	c.	Determine Veteran�s earliest 30% SC or more rating Effective Date (reference rule � if not 30% get denial letter)
	*	d.	Calculate the earliest date based on  (a) and (b).  
	*		a.	If (d) is earlier than (c) (30% SC Effective Date)  the allowable Date is (c) otherwise: the Allowable date is (d)
	*
 	*/
    public void CalculateAndSetAllowableDate(final RbpsRepository repository ) 
    {
    	Date allowableDate = null;
    	Date claimReceivedDate = repository.getVeteran().getClaim().getReceivedDate();
    	Date EarliestSCRatingDate = repository.getVeteran().getRatingEffectiveDate();
    	Date ratingDate = repository.getVeteran().getRatingDate();
    	Date FCDR = repository.getVeteran().getFirstChangedDateofRating();
    	
		String message 	=	String.format( "claim Date %s  Earliest Effective Date %s, Rating Effective Date %s", 
											claimReceivedDate.toString(), EarliestSCRatingDate.toString(), ratingDate.toString());
		CommonUtils.log( logger, message );

    	if (claimReceivedDate == null || EarliestSCRatingDate == null || ratingDate == null) {
    		message 	=	String.format( "one of the Dates is NULL: claim Date %s  Earliest Effective Date %s, Rating Effective Date %s", 
    											claimReceivedDate.toString(), EarliestSCRatingDate.toString(), ratingDate.toString());
    		CommonUtils.log( logger, message );
    		return;
    	}
    	
    	Date claimDateMinus365Days = SimpleDateUtils.truncateToDay(SimpleDateUtils.addDaysToDate(-365, claimReceivedDate));
    	
		message 	=	String.format( "claimDateMinus365Days: %s",claimDateMinus365Days.toString());									
		CommonUtils.log( logger, message );
    	
		//Date today = new Date();;
		//Date todayMinus365Days = SimpleDateUtils.truncateToDay(SimpleDateUtils.addDaysToDate(-365, today));
		
		if (FCDR != null && FCDR.before(claimDateMinus365Days)) {
			allowableDate = FCDR;
			message 	=	String.format( "allowableDate set to FCDR: %s  ",allowableDate.toString());									
			CommonUtils.log( logger, message );
		}
		else {
			allowableDate = claimDateMinus365Days;
			message 	=	String.format( "allowableDate set to claimDateMinus365Days: %s  ",allowableDate.toString());									
			CommonUtils.log( logger, message );
		}
	
    	if (EarliestSCRatingDate.after(allowableDate)) {
    		allowableDate = EarliestSCRatingDate;
    		message 	=	String.format( "allowableDate set to EarliestSCRatingDate: %s  ",EarliestSCRatingDate.toString());									
			CommonUtils.log( logger, message );
    	}
    	message 	=	String.format( "Allowable Date = %s",allowableDate.toString());
    	CommonUtils.log( logger, message );
    	repository.getVeteran().setAllowableDate(allowableDate);
    }
    
}
