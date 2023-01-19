/*
 * FindRatingsProfileWSHandler.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.services.populators;


import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.claimvalidator.GenericUserInformationValidatorImpl;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.exception.RbpsRuntimeException;
import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.coreframework.util.SimpleDateUtils;
import gov.va.vba.rbps.services.ws.client.mapping.share.ratingComparison.CompareByDateRangeResponse;
import gov.va.vba.rbps.services.ws.client.mapping.share.ratingComparison.RatingComparisonServiceReturnVO;
import gov.va.vba.rbps.services.ws.client.mapping.share.ratingComparison.RatingDetailsVO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.springframework.util.CollectionUtils;


/**
 *      Takes the response from the ratingComparison ws handler and figures out
 *      the rating date, rating effective date, and the rating.
 */
public class RatingDatesPopulator {

    private static Logger logger = Logger.getLogger(RatingDatesPopulator.class);

//    private CommonUtils         utils       = new CommonUtils();
//    private SimpleDateUtils     dateUtils   = new SimpleDateUtils();
    private LogUtils            logUtils    = new LogUtils( logger, true );


    public void populateRatingDates( final RbpsRepository                repo,
                                     final CompareByDateRangeResponse    ratingDatesResponse) {

//        logUtils.log( " - response: >" + new IndentedToStringUtils().toString( ratingDatesResponse.getReturn(), 0 ) );

        RatingComparisonServiceReturnVO     retVal          = ratingDatesResponse.getReturn();
        List <RatingDetailsVO>              details         = retVal.getRatingDetails();

        checkForRejectReason( repo, retVal );
        checkForNoEffectiveDateInDetails( repo, details );
        assignRatingDate( repo, retVal );
        removeLessThan30Percent( details );
        checkForNoDetails( repo, details );
        RatingDetailsVO earliest = findEarliestRating( details );
        assignRatingEffectiveDate( repo, earliest );

        return;
    }


    private void assignRatingEffectiveDate( final RbpsRepository        repo,
                                            final RatingDetailsVO       earliest ) {

        checkForNoEarliestDetails( repo, earliest );

        Date    effectiveDate = SimpleDateUtils.xmlGregorianCalendarToDay( earliest.getEffectiveDt() );
//        logUtils.log( logger, "effective date: " + effectiveDate );

        repo.getVeteran().setRatingEffectiveDate( effectiveDate );
        repo.getVeteran().setServiceConnectedDisabilityRating( earliest.getScPercent() );
    }


    private RatingDetailsVO findEarliestRating( final List<RatingDetailsVO> details ) {

        sortByDateAsc( details );

        return details.get( 0 );
    }


    private void sortByDateAsc( final List<RatingDetailsVO> details ) {

        Collections.sort( details, new DetailsComparatorByDate() );
    }


    private void removeLessThan30Percent( final List<RatingDetailsVO> details ) {

        List<RatingDetailsVO>       toBeRemoved = new ArrayList<RatingDetailsVO>();

        for ( RatingDetailsVO   ratingInfo : details ) {

            if ( ratingInfo.getScPercent() < 30 ) {

                toBeRemoved.add( ratingInfo );
            }
        }

        details.removeAll( toBeRemoved );
    }


    private void assignRatingDate( final RbpsRepository                     repo,
                                   final RatingComparisonServiceReturnVO    retVal ) {

        if ( null == retVal.getPromulgationDt() ) {

            String      msg = GenericUserInformationValidatorImpl.FAIL + "No rating date available.";
            repo.addValidationMessage( msg );
            throw new RbpsRuntimeException( msg );
        }

        Date    promulgationDate = SimpleDateUtils.truncateToDay( SimpleDateUtils.xmlGregorianCalendarToDay( retVal.getPromulgationDt() ) );

        repo.getVeteran().setRatingDate( promulgationDate );
    }


    public void checkForRejectReason( final RbpsRepository                      repo,
                                      final RatingComparisonServiceReturnVO     retVal ) {

        String      rejectReason    = retVal.getRejectReason();

        if ( StringUtils.isBlank( rejectReason ) ) {

            return;
        }

        rejectReason = GenericUserInformationValidatorImpl.FAIL + rejectReason;
        repo.addValidationMessage( rejectReason );
        throw new RbpsRuntimeException( rejectReason );
    }


    private void checkForNoDetails( final RbpsRepository            repo,
                                    final List<RatingDetailsVO>     details ) {

        if ( ! CollectionUtils.isEmpty( details ) ) {

            return;
        }

//        logUtils.log( "There were no rating details over 30% provided in the response from the ratingComparison WS." );
        String      msg = GenericUserInformationValidatorImpl.FAIL + "No rating details over 30% available.";
        repo.addValidationMessage( msg );
        throw new RbpsRuntimeException( msg );
    }


    public void checkForNoEffectiveDateInDetails( final RbpsRepository          repo,
                                                  final List<RatingDetailsVO>   details ) {

        for ( RatingDetailsVO detail : details ) {

            checkForNoEffectiveDateInDetail( repo, detail );
        }
    }


    public void checkForNoEffectiveDateInDetail( final RbpsRepository      repo,
                                                 final RatingDetailsVO     detail ) {

        if ( null != detail.getEffectiveDt() ) {

            return;
        }

//        logUtils.log( "There are missing rating effective dates in the details." + new IndentedToStringUtils().toString( detail, 0 ) );
        String      msg = GenericUserInformationValidatorImpl.FAIL + "Rating details missing effective date.";
        repo.addValidationMessage( msg );
        throw new RbpsRuntimeException( msg );
    }


    public void checkForNoEarliestDetails( final RbpsRepository         repo,
                                           final RatingDetailsVO        earliest ) {

        if ( null != earliest ) {

            return;
        }

//        logUtils.log( "There are no 'earliest' rating details." );
        String      msg = GenericUserInformationValidatorImpl.FAIL + "No rating effective date available.";
        repo.addValidationMessage( msg );
        throw new RbpsRuntimeException( msg );
    }








    class DetailsComparatorByDate implements Comparator<RatingDetailsVO> {

        @Override
        public int compare( final RatingDetailsVO    lhs,
                            final RatingDetailsVO    rhs ) {

//            logUtils.log( logger, String.format( "lhs dependent : >%s<", utils.stringBuilder( lhs ) ) );
//            logUtils.log( logger, String.format( "rhs dependent : >%s<", utils.stringBuilder( rhs ) ) );

            return new CompareToBuilder()
                .append( SimpleDateUtils.truncateToDay( SimpleDateUtils.xmlGregorianCalendarToDay( lhs.getEffectiveDt() ) ),
                		SimpleDateUtils.truncateToDay( SimpleDateUtils.xmlGregorianCalendarToDay( rhs.getEffectiveDt() ) ) )
                .toComparison();
        }
    }
}

