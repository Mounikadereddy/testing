/*
 * CommonUtils.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.services.populators.utils;


import gov.va.vba.rbps.coreframework.dto.ClaimStationAddress;
import gov.va.vba.rbps.coreframework.util.IndentedXomToString;
import gov.va.vba.rbps.coreframework.util.KeyValueListFormatter;
import gov.va.vba.rbps.services.ws.client.mapping.share.ratingComparison.CompareByDateRangeResponse;
import gov.va.vba.rbps.services.ws.client.mapping.share.ratingComparison.RatingComparisonServiceReturnVO;
import gov.va.vba.rbps.services.ws.client.mapping.share.ratingComparison.RatingDetailsVO;
import gov.va.vba.rbps.services.ws.client.mapping.share.ratingComparison.RbaPrfil;
import gov.va.vba.rbps.services.ws.client.mapping.share.sojSignature.FindSignaturesByStationNumberResponse;
import gov.va.vba.rbps.services.ws.client.mapping.share.sojSignature.StnPrfilDetailDTO;

import java.util.List;


public class IndentedToStringUtils extends IndentedXomToString {


    public String toString( final ClaimStationAddress     address,
                            final int                     depth ) {

        if ( address == null ) {

            return null;
        }

        String tmp = new KeyValueListFormatter( 40 )
            .append( "claimLocationId",         address.getClaimLocationId() )
            .append( "stationId",               address.getStationId() )
            .append( "addressLine1",            address.getAddressLine1() )
            .append( "addressLine2",            address.getAddressLine2() )
            .append( "addressLine3",            address.getAddressLine3() )
            .append( "city",                    address.getCity() )
            .append( "state",                   address.getState() )
            .append( "zipCode",                 address.getZipCode() )
            .append( "zipPlus4",                address.getZipPlus4() )
            .append( "serviceManagerTitle",     address.getServiceManagerTitle() )
            .append( "serviceManagerSignature", address.getServiceManagerSignature() )
            .append( "zipSecondSuffix",         address.getZipSecondSuffix() )
            .toString();

        return indent( tmp, depth );
    }


    public String toString( final FindSignaturesByStationNumberResponse     response,
                            final int                                       depth ) {

        if ( response == null ) {

            return null;
        }

        String tmp = new KeyValueListFormatter( 40 )
            .append( "details",          signatureDetailsToString( response.getStnPrfilDetailDTO(), 0 ) )
            .toString();

        return indent( tmp, depth );
    }


    public String signatureDetailsToString( final List<StnPrfilDetailDTO>     details,
                                            final int                       depth ) {

        String  result = "";

        for ( StnPrfilDetailDTO detail : details ) {

            result += toString( detail, depth );
            result += separator( 40, depth );
        }

        return result;
    }


    public String toString( final StnPrfilDetailDTO         detail,
                            final int                       depth ) {

        if ( detail == null ) {

            return null;
        }

//        logDepth( "spouse", depth );
        String tmp = new KeyValueListFormatter( 40 )
            .append( "title",           detail.getLetterSigntrTitleTxt() )
            .append( "signature",       detail.getLetterSigntrTxt() )
            .toString();

        return indent( tmp, depth );
    }








    public String toString( final CompareByDateRangeResponse    response,
                            final int                           depth ) {

        if ( response == null ) {

            return null;
        }

//        logDepth( "spouse", depth );
        String tmp = new KeyValueListFormatter( 40 )
            .append( "return",          toString( response.getReturn(), 0 ) )
            .toString();

        return indent( tmp, depth );
    }


    public String toString( final RatingComparisonServiceReturnVO       detail,
                            final int                                   depth ) {

        if ( detail == null ) {

            return null;
        }

//        logDepth( "spouse", depth );
        String tmp = new KeyValueListFormatter( 40 )
            .append( "anyRatingsWithinRangeFlag",   detail.getAnyRatingsWithinRangeFlag() )
            .append( "differencesFound",            detail.getDifferencesFound() )
            .append( "multipleRatingsFlag",         detail.getMultipleRatingsFlag() )
            .append( "prfilDt",                     detail.getPrfilDt() )
            .append( "promulgationDt",              detail.getPromulgationDt() )
            .append( "ptcpntVetID",                 detail.getPtcpntVetID() )
            .append( "rejectReason",                detail.getRejectReason() )
            .append( "ratingDetails",               ratingDetailsToString( detail.getRatingDetails(), depth + 1 ) )
            .toString();

        return indent( tmp, depth );
    }


    public String ratingDetailsToString( final List<RatingDetailsVO>     details,
                                         final int                       depth ) {

        String  result = "";

        for ( RatingDetailsVO detail : details ) {

            result += toString( detail, depth );
            result += separator( 40, depth );
        }

        return result;
    }


    public String toString( final RatingDetailsVO    response,
                            final int                depth ) {

        if ( response == null ) {

            return null;
        }

//        logDepth( "spouse", depth );
        String tmp = new KeyValueListFormatter( 40 )
            .append( "effective date",                  response.getEffectiveDt() )
            .append( "service connected percent",       response.getScPercent() )
            .toString();

        return indent( tmp, depth );
    }
    


    public String toString( final RbaPrfil	response,
                            final int       depth ) {

        if ( response == null ) {
            return null;
        }

//        logDepth( "spouse", depth );
        String tmp = new KeyValueListFormatter( 40 )
            .append( "Rba profile date",             response.getCompId().getPrfilDt() )
            .append( "Veteran participant id",       response.getCompId().getPtcpntVetId() )
            .toString();

        return indent( tmp, depth );
    }  
    
}
