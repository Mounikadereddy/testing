package gov.va.vba.rbps.claimprocessor.util;

import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.util.CommonUtils;
import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.coreframework.util.SimpleDateUtils;
import gov.va.vba.rbps.coreframework.xom.*;
import org.apache.commons.lang.time.DateUtils;

import java.util.Date;

public class OmnibusFlagHelper {

    private static Logger logger              = Logger.getLogger(OmnibusFlagHelper.class);
    private LogUtils logUtils            = new LogUtils( logger, true );


    public static String getOmnibusFlag(Veteran vet, Award award, Date eventDate) {
        if ( isDenialForMarriageTermination( award ) ) {

            return "Y";
        }

        if ( ClaimProcessorHelper.isDenial( award ) ) {

            return "N";
        }


        if ( vet.getFirstChangedDateofRating() != null ) {
            if ( isSameDay( eventDate, vet.getFirstChangedDateofRating() ) ||
                    isSameDay( eventDate, vet.getRatingEffectiveDate() ) )
            {
                return "N";
            }
        }
        else if ( isSameDay( eventDate,
                vet.getRatingEffectiveDate() ) ) {

            return "N";
        }

        return "Y";
    }

    private static final boolean isSameDay( final Date       lhs,
                                            final Date       rhs ) {

        return DateUtils.isSameDay( SimpleDateUtils.truncateToDay( lhs ),
                SimpleDateUtils.truncateToDay( rhs ) );
    }

    private void checkForCoincidentChildDate( final Dependent      dependent,
                                              RbpsRepository repo) {

        if ( ! (dependent instanceof Child) ) {

            return;
        }

        Child child = (Child) dependent;

        if ( child.getBirthDate() == null ) {

            logUtils.log( String.format( "Child %s missing birth date, can't check for coincidence.",
                    CommonUtils.getStandardLogName( child ) ), repo );
            return;
        }
    }

    private void checkForCoincidentSpouseDate( final Dependent      dependent,
                                               RbpsRepository repo) {
        if ( ! (dependent instanceof Spouse) ) {

            return;
        }

        Spouse spouse = (Spouse) dependent;

        if ( spouse.getCurrentMarriage() == null
                || spouse.getCurrentMarriage().getStartDate() == null ) {

            logUtils.log( String.format( "Spouse %s missing marriage date, can't check for coincidence.",
                    CommonUtils.getStandardLogName( spouse ) ), repo );
            return;
        }
    }

    private static final boolean isDenialForMarriageTermination( final Award award ) {

        if ( ( award.getDependencyDecisionType().equals(DependentDecisionType.MARRIAGE_TERMINATED) )
                || ( award.getDependencyDecisionType().equals(DependentDecisionType.DEATH ) ) ) {

            return true;
        }

        return false;
    }

}
