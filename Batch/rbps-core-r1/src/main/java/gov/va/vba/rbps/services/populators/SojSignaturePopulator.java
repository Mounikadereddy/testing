/*
 * FindRatingsProfileWSHandler.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.services.populators;


import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.exception.RbpsRuntimeException;
import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.services.populators.utils.SojSignatureDetails;
import gov.va.vba.rbps.services.ws.client.mapping.share.sojSignature.FindSignaturesByStationNumberResponse;
import gov.va.vba.rbps.services.ws.client.mapping.share.sojSignature.StnPrfilDetailDTO;


/**
 *      Takes the response from the ratingComparison ws handler and figures out
 *      the rating date, rating effective date, and the rating.
 */
public class SojSignaturePopulator {

    private static Logger logger = Logger.getLogger(SojSignaturePopulator.class);

    private LogUtils            logUtils        = new LogUtils( logger, true );



    public static final void assignVeteranServiceManagerSignature( final RbpsRepository                             repo,
                                                      			   final FindSignaturesByStationNumberResponse      response ) {

        SojSignatureDetails sigDetails  = matchServiceManager( repo, response );

        setSignatoryDetails(repo, sigDetails);


        return;
    }


    private static final void setSignatoryDetails( final RbpsRepository          repo,
                                      final SojSignatureDetails     sigDetails) {

        repo.getClaimStationAddress().setServiceManagerSignature( sigDetails.getSignature() );
        repo.getClaimStationAddress().setServiceManagerTitle( sigDetails.getTitle() );
    }



    private static final SojSignatureDetails matchServiceManager( final RbpsRepository                            repo,
                                                     final FindSignaturesByStationNumberResponse     response ) {

        SojSignatureDetails sigDetails  = new SojSignatureDetails();

        for ( StnPrfilDetailDTO detail : response.getStnPrfilDetailDTO()  ) {

            if ( matchesServiceCenterManager( detail ) ) {

                setSojSignatureDetails(sigDetails, detail);

                return sigDetails;
            }
        }

        throw new RbpsRuntimeException( String.format( "Unable to find a Veteran Service Center Manager for station >%s<",
                                                       repo.getClaimStationAddress().getName() ) );
    }


    private static final void setSojSignatureDetails( final SojSignatureDetails      sigDetails,
                                         final StnPrfilDetailDTO        detail) {

        sigDetails.setSignature( detail.getLetterSigntrTxt() );
        sigDetails.setTitle( detail.getLetterSigntrTitleTxt() );
    }


    private static final boolean matchesServiceCenterManager( final StnPrfilDetailDTO detail ) {

//      SELECT * FROM stn_prfil_detail d WHERE d.letter_signtr_title_txt LIKE '%Service Center%'
//      OR d.letter_signtr_title_txt LIKE '%SERVICE CENTER%' OR d.letter_signtr_title_txt LIKE '%VSCM%';

        String title = detail.getLetterSigntrTitleTxt().toLowerCase();

        return title.contains( "service center" ) || title.contains( "vscm" );
    }






    public void setLogit( final boolean logit ) {

        logUtils.setLogit( logit );
    }
}

