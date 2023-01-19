/*
 * DependentDiffs.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.services.populators.utils;


import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.dto.CorporateDependent;
import gov.va.vba.rbps.coreframework.dto.CorporateDependentId;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
//import gov.va.vba.rbps.coreframework.util.CommonUtils;
import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.coreframework.xom.Child;
import gov.va.vba.rbps.coreframework.xom.Spouse;

import java.util.ArrayList;
import java.util.List;


/**
 *      Show which dependents are missing in the corporate dependents
 *      and which are missing in the xom dependents.  This logging might
 *      be useful for working with some of the post-rules web services,
 *      where we want to know that we've updated the corporate database
 *      with dependents that are on the claim but not in the corporate
 *      database - because the awards ws needs them.
 */
public class DependentDiffs {

    private static Logger logger = Logger.getLogger(DependentDiffs.class);

    private LogUtils        logUtils        = new LogUtils( logger, true );
   //private CommonUtils		utils;
    private List<String>    logMessages;


    /**
     *      logs the differences between the corporate dependents
     *      and the xom dependents.
     *
     *      @param repository
     */
    public void diff( final RbpsRepository      repository) {

        logNumberOfCorporateKids( repository );
        logNumberOfXomKids( repository );
        logDifferentSizes( repository );
        logMissingXomKids( repository );
        logMissingCorporateKids( repository );
        detectMissingCorporateSpouse( repository );
        detectMissingXomSpouse( repository );
        detectMismatchingSpouses( repository );
    }


    private void detectMismatchingSpouses( final RbpsRepository repository ) {

        if ( ! hasBothSpouses( repository ) ) {

            return;
        }

        if ( ! haveMismatchingSpouses( repository ) ) {

            return;
        }

        logMismatchingSpouses( repository );
    }


    private void detectMissingXomSpouse( final RbpsRepository repository ) {

        if ( ! missingXomSpouse( repository ) ) {

           return;
        }

        logMissingXomSpouse( repository );
    }


    private void detectMissingCorporateSpouse( final RbpsRepository repository ) {

        if ( ! missingCorporateSpouse( repository ) ) {

            return;
        }

        logMissingCorporateSpouse( repository );
    }


    private void logMissingCorporateKids( final RbpsRepository repository ) {

        List<CorporateDependentId>  corporateKids           = getCorporateDependentIdListforCorporateKids( repository );
        List<Child>                 missingCorporateKids    = getMissingCorporateKids( repository, corporateKids );

        logMissingCorporateKids( missingCorporateKids, repository );

    }


    private List<Child> getMissingCorporateKids( final RbpsRepository               repository,
                                                 final List<CorporateDependentId>   corporateKids ) {

        List<Child>  idList  = new ArrayList<Child>();

        for ( Child child : repository.getVeteran().getChildren() ) {

            if ( ! corporateKids.contains( new CorporateDependentId( child ) ) ) {

                idList.add( child );
            }
        }

        return idList;
    }


    private List<CorporateDependentId> getCorporateDependentIdListforCorporateKids( final RbpsRepository repository ) {

        List<CorporateDependentId>  idList  = new ArrayList<CorporateDependentId>();

        for ( CorporateDependent child   :   repository.getChildren() ) {

            idList.add( child.getId() );
        }

        return idList;
    }


    private static final boolean haveMismatchingSpouses( final RbpsRepository repository ) {

        CorporateDependentId    xomId = new CorporateDependentId( repository.getVeteran().getCurrentMarriage().getMarriedTo() );

        return ! xomId.equals( repository.getSpouse().getId() );
    }


    private static final List<CorporateDependentId> getMissingXomKids( final RbpsRepository                  repository,
                                                          final List<CorporateDependentId>      xomKids ) {

        List<CorporateDependentId>  idList  = new ArrayList<CorporateDependentId>();

        for ( CorporateDependent dependent : repository.getChildren() ) {

            if ( ! xomKids.contains( dependent.getId() ) ) {

                idList.add( dependent.getId() );
            }
        }

        return idList;
    }


    private static final List<CorporateDependentId> getXomCorporateDependentIdList( final RbpsRepository repository ) {

        List<CorporateDependentId>  idList  = new ArrayList<CorporateDependentId>();

        for ( Child child : repository.getVeteran().getChildren() ) {

            idList.add( new CorporateDependentId( child ) );
        }

        return idList;
    }


    private static final boolean missingXomSpouse( final RbpsRepository repository ) {

        return repository.getSpouse() != null
                && repository.getVeteran().getCurrentMarriage() == null;
    }


    private static final boolean hasBothSpouses( final RbpsRepository repository ) {

        return repository.getSpouse() != null
                && repository.getVeteran().getCurrentMarriage() != null;
    }


    private static final boolean missingCorporateSpouse( final RbpsRepository repository ) {

        return repository.getVeteran().getCurrentMarriage() != null
                && repository.getSpouse() == null;
    }


    private void logMissingCorporateKids( final List<Child> missingCorporateKids, RbpsRepository repo ) {

        for ( Child missingKid : missingCorporateKids ) {

            logMissingCorporateKid( missingKid, repo );
        }
    }


    private void logMissingXomKids( final RbpsRepository repository ) {

        List<CorporateDependentId> xomKids          = getXomCorporateDependentIdList( repository );
        List<CorporateDependentId> missingXomKids   = getMissingXomKids( repository, xomKids );

        logMissingXomKids( missingXomKids, repository );
    }


    private void logMissingXomKids( final List<CorporateDependentId> missingXomKids, RbpsRepository repo ) {

        for ( CorporateDependentId missingKid : missingXomKids ) {

            logMissingKid( missingKid, repo );
        }
    }


    private void logMissingCorporateKid( final Child kid, RbpsRepository repo ) {

        log( String.format( "                   missing corporate kid >%s, %s<",
                            kid.getLastName(),
                            kid.getFirstName() ), repo );
    }


    private void logMissingKid( final CorporateDependentId kid, RbpsRepository repo ) {

        log( String.format( "                   missing xom kid >%s<",
                            kid.getFirstName() ), repo );
    }


    private void logDifferentSizes( final RbpsRepository repository ) {

        log( String.format( "                   size difference >%d<",
                            Math.abs( repository.getChildren().size()
                                      - repository.getVeteran().getChildren().size() ) ), repository );
    }


    private void logNumberOfCorporateKids( final RbpsRepository repository ) {

        log( String.format( "                   number of corporate kids >%d<",
                            repository.getChildren().size() ), repository );
    }


    private void logNumberOfXomKids( final RbpsRepository repository ) {

        log( String.format( "                   number of xom kids >%d<",
                            repository.getVeteran().getChildren().size() ), repository );
    }


    private void logMissingCorporateSpouse( final RbpsRepository repository ) {

        Spouse spouse = repository.getVeteran().getCurrentMarriage().getMarriedTo();

        log( String.format( "                   missing corporate spouse >%s, %s<",
                            spouse.getLastName(),
                            spouse.getFirstName() ), repository );
    }


    private void logMissingXomSpouse( final RbpsRepository repository ) {

        log( String.format( "                   missing xom spouse >%s, %s<",
                            repository.getSpouse().getLastName(),
                            repository.getSpouse().getFirstName() ), repository );
    }


    private void logMismatchingSpouses( final RbpsRepository repository ) {

        Spouse              xomSpouse   = repository.getVeteran().getCurrentMarriage().getMarriedTo();
        CorporateDependent  spouse      = repository.getSpouse();

        log( "        the xom and corporate spouses do not match", repository );

        log( String.format( "                   xom spouse >%s, %s< %d/%s",
                            xomSpouse.getLastName(),
                            xomSpouse.getFirstName(),
                            xomSpouse.getCorpParticipantId(),
                            xomSpouse.getSsn() ), repository );
        log( String.format( "                   corporate spouse >%s, %s< %d/%s",
                            spouse.getLastName(),
                            spouse.getFirstName(),
                            spouse.getParticipantId(),
                            spouse.getSocialSecurityNumber() ), repository );
    }


    private void log( final String        msg, RbpsRepository repo ) {

        if ( logMessages != null ) {

            logMessages.add( msg );
        }

        logUtils.log( msg, LogUtils.PLUS_ONE + 1, repo);
    }


    public void setLogMessages( final List<String> logMessages ) {

        this.logMessages = logMessages;
    }
    
    /*
	public void setCommonUtils(CommonUtils utils) {
		
		this.utils = utils;
	}
	*/
}
