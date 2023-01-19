/*
 * RatingComparisonWSHandler.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.services.ws.client.handler.share;


import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.exception.RbpsWebServiceClientException;
import gov.va.vba.rbps.coreframework.util.CommonUtils;
import gov.va.vba.rbps.coreframework.util.KeyValueListFormatter;
import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.coreframework.util.RbpsUtil;
import gov.va.vba.rbps.coreframework.util.SimpleDateUtils;
import gov.va.vba.rbps.services.ws.client.mapping.share.ratingComparison.CompareByDateRange;
import gov.va.vba.rbps.services.ws.client.mapping.share.ratingComparison.CompareByDateRangeResponse;
import gov.va.vba.rbps.services.ws.client.mapping.share.ratingComparison.RatingDateRangeVO;
import gov.va.vba.rbps.services.ws.client.util.HeaderSetter;
import org.springframework.ws.client.core.WebServiceTemplate;

import java.util.Date;


/**
 *      Call the WS which looks at rating data to determine the rating date and rating effective date.
 */
public class RatingComparisonWSHandler {

    private static Logger logger = Logger.getLogger(RatingComparisonWSHandler.class);

    private final LogUtils              logUtils    = new LogUtils( logger, true );
    private WebServiceTemplate          webServiceTemplate;
    private String                      ratingComparisonUri;


     public CompareByDateRangeResponse findApplicableRatingsData( final RbpsRepository repository ) throws RbpsWebServiceClientException {

         logUtils.debugEnter( repository );

         CompareByDateRange request = buildFindRatingsProfileRequest( repository );

         Object response;
         try {
             response = webServiceTemplate.marshalSendAndReceive( ratingComparisonUri,
                                                                  request,
                                                                  new HeaderSetter( ratingComparisonUri,
                                                                		  repository.getClaimStationAddress() ) );
         }
         catch (Throwable rootCause) {

             if(
                 repository.isProcessPensions() &&
                 repository.getVeteran().isPensionAward() &&
                 rootCause.getMessage().contains("No Ratings exist for this Veteran"))
             {

                 // when a vet has a pension/over 65 with no disability rating, add rule exception message to
                 // notify user this veteran needs to be processed manually
                 return null;
             }
             String detailMessage = "Call to WS RatingComparisonEJBService.compareByDateRange failed";

             repository.addValidationMessage( CommonUtils.getValidationMessage( ratingComparisonUri,
                     request.getClass().getSimpleName(),
                     rootCause.getMessage() ) );

             throw new RbpsWebServiceClientException(detailMessage, rootCause);
         }
         finally {

             logUtils.debugExit( repository );
         }

         return (CompareByDateRangeResponse) response;
     }


     private CompareByDateRange buildFindRatingsProfileRequest( final RbpsRepository repository ) {

         CompareByDateRange  findRatings                     = new CompareByDateRange();
         RatingDateRangeVO   ratingDateRangeVO               = new RatingDateRangeVO();
         Date                claimDate                       = repository.getVeteran().getClaim().getReceivedDate();
         Date                threeSeventyTwoDaysBeforeClaim  = SimpleDateUtils.truncateToDay( SimpleDateUtils.addDaysToDate(-372, claimDate) );
         Date                midnightToday                   = SimpleDateUtils.truncateToDay( SimpleDateUtils.addDaysToDate( 1, new Date() ) );

         logLookupInfo( threeSeventyTwoDaysBeforeClaim, midnightToday, repository );

         ratingDateRangeVO.setPtcpntId( repository.getVeteran().getCorpParticipantId() );
         ratingDateRangeVO.setStartDate( RbpsUtil.dateToXMLGregorianCalendar( threeSeventyTwoDaysBeforeClaim ) );
         ratingDateRangeVO.setEndDate( RbpsUtil.dateToXMLGregorianCalendar( midnightToday ) );
         ratingDateRangeVO.setClaimDate( RbpsUtil.dateToXMLGregorianCalendar( claimDate ) );
         
         findRatings.setRatingDateRange( ratingDateRangeVO);

         return findRatings;
     }


     public void logLookupInfo( final Date 				oneYearAndAMonthBeforeClaim,
                                final Date 				midnightToday,
                                final RbpsRepository 	repository) {

         String tmp = new KeyValueListFormatter( 40 )
             .append( "----> Participant Id",                    repository.getVeteran().getCorpParticipantId() )
             .append( "----> Start Date",                        SimpleDateUtils.standardLogDayFormat( oneYearAndAMonthBeforeClaim ) )
             .append( "----> End Date",                          SimpleDateUtils.standardLogDayFormat( midnightToday ) )
             .append( "----> XMLGregorianCalendar Start Date",   RbpsUtil.dateToXMLGregorianCalendar( oneYearAndAMonthBeforeClaim ) )
             .append( "----> XMLGregorianCalendar End Date",     RbpsUtil.dateToXMLGregorianCalendar( midnightToday ) )
             .toString();

         CommonUtils.log( logger, tmp );
     }

     public void setRatingComparisonUri( final String findRatingsProfileUri ) {

    	 String clusterAddr = CommonUtils.getClusterAddress();
         this.ratingComparisonUri = clusterAddr + "/" + findRatingsProfileUri;
     }

     public void setWebServiceTemplate( final WebServiceTemplate webServiceTemplate ) {

         this.webServiceTemplate = webServiceTemplate;
     }
}
