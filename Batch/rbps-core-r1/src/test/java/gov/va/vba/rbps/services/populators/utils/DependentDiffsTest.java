/*
 * DependentDiffsTest.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.services.populators.utils;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.containsString;
import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.dto.CorporateDependent;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.util.CommonUtils;
import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.coreframework.util.RbpsConstants;
import gov.va.vba.rbps.coreframework.xom.Child;
import gov.va.vba.rbps.coreframework.xom.Spouse;
import gov.va.vba.rbps.coreframework.xom.Veteran;
import gov.va.vba.rbps.fixtures.CommonFactory;
import gov.va.vba.rbps.fixtures.CoreFactory;
import gov.va.vba.rbps.services.populators.UpdateBenefitClaimDependentsPopulator;
import gov.va.vba.rbps.services.ws.client.util.TestUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.junit.Before;
import org.junit.Test;


/**
 *      Test case for the <code>DependentDiffs</code> class
 */
public class DependentDiffsTest {

    private static Logger logger = Logger.getLogger(DependentDiffsTest.class);

    private boolean     logit           = false;
    private LogUtils    logUtils        = new LogUtils( logger, logit );
//    private CommonUtils utils;

    @Before
    public void setup() {

        LogUtils.setGlobalLogit( logit );
        ToStringBuilder.setDefaultStyle( RbpsConstants.RBPS_TO_STRING_STYLE );
    }


    @Test
    public void shouldJoinCorrectly() {

        List<String> names = Arrays.asList( "joe", "keya", "karma" );

        String result = CommonUtils.join( names, ", " );

        assertThat( result, is(equalTo( "joe, keya, karma" ) ) );
    }


    @Test
    public void shouldReportNoDiffs() {

        RbpsRepository  repository  = new RbpsRepository();
        Veteran         veteran     = CommonFactory.adamsVeteran();

        repository.setVeteran( veteran );
        CommonFactory.addChildren( veteran );
        List<Child> children = veteran.getChildren();

        List<CorporateDependent> corporateKids = CoreFactory.getCorporateChildren( repository, children );
        repository.setChildren( corporateKids );

        String output = callDiffs( repository );

        assertThat( output, containsString( "" + children.size() ) );
    }


    @Test
    public void shouldReportDifferentListSizesRemoveSomeCorporate() {

        RbpsRepository  repository  = new RbpsRepository();
        Veteran         veteran     = CommonFactory.adamsVeteran();

        repository.setVeteran( veteran );
        CommonFactory.addChildren( veteran, 6 );
        List<Child> children = veteran.getChildren();

        List<CorporateDependent> corporateKids = CoreFactory.getCorporateChildren( repository, children );
        repository.setChildren( corporateKids );
        new Remover<CorporateDependent>().removeKidsFromList( 3, corporateKids );


        String output = callDiffs( repository );

        assertThat( output, containsString( "" + children.size() ) );
        assertThat( output, containsString( "" + corporateKids.size() ) );
    }


    @Test
    public void shouldReportDifferentListSizesRemoveSomeXom() {

        RbpsRepository  repository  = new RbpsRepository();
        Veteran         veteran     = CommonFactory.adamsVeteran();

        repository.setVeteran( veteran );
        CommonFactory.addChildren( veteran, 6 );

        List<Child>                 children    = veteran.getChildren();
        List<CorporateDependent>    corporateKids = CoreFactory.getCorporateChildren( repository, children );
        repository.setChildren( corporateKids );

        new Remover<Child>().removeKidsFromList( 3, children );


        String output = callDiffs( repository );

        assertThat( output, containsString( "" + children.size() ) );
        assertThat( output, containsString( "" + corporateKids.size() ) );
    }


    @Test
    public void shouldReportMissingKidsRemoveSomeCorporate() {

        RbpsRepository  repository  = new RbpsRepository();
        Veteran         veteran     = CommonFactory.adamsVeteran();

        repository.setVeteran( veteran );
        CommonFactory.addChildren( veteran, 6 );
        List<Child> children = veteran.getChildren();

        List<CorporateDependent> corporateKids  = CoreFactory.getCorporateChildren( repository, children );
        List<CorporateDependent> removed        = new Remover<CorporateDependent>().removeKidsFromList( 2, corporateKids );
        repository.setChildren( corporateKids );

        DependentDiffs  dependentDiffs  = new DependentDiffs();
        List<String>    messages        = new ArrayList<String>();
        dependentDiffs.setLogMessages( messages );
        dependentDiffs.diff( repository );

        String output = CommonUtils.join( messages, "\n" );
//        CommonUtils  utils           = new CommonUtils();

        out( "corporate kids: " );
//        utils.logCorporateChildren( null, repository );
//        utils.logKids( null, repository );

        out( output );

        for ( CorporateDependent kid : removed ) {

            assertThat( output,
                        containsString( kid.getFirstName() ) );
        }
    }


    @Test
    public void shouldReportMissingKidsRemoveSomeXom() {

        RbpsRepository  repository  = new RbpsRepository();
        Veteran         veteran     = CommonFactory.adamsVeteran();

        repository.setVeteran( veteran );
        CommonFactory.addChildren( veteran, 6 );
        List<Child> children = veteran.getChildren();

        List<CorporateDependent> corporateKids = CoreFactory.getCorporateChildren( repository, children );
        repository.setChildren( corporateKids );

        List<Child>     removed         = new Remover<Child>().removeKidsFromList( 2, children );
        String          output          = callDiffs( repository );

        for ( Child kid : removed ) {

            assertThat( output.toLowerCase(),
                        containsString( kid.getFirstName().toLowerCase() ) );
        }
    }


    @Test
    public void shouldReportMissingKidsRemoveSomeCorporateAndSomeXom() {

        RbpsRepository  repository  = new RbpsRepository();
        Veteran         veteran     = CommonFactory.adamsVeteran();

        repository.setVeteran( veteran );
        CommonFactory.addChildren( veteran, 6 );

        List<Child>                 children        = veteran.getChildren();
//        CommonUtils                 utils           = new CommonUtils();
        List<CorporateDependent>    corporateKids   = CoreFactory.getCorporateChildren( repository, children );
        repository.setChildren( corporateKids );

        logUtils.logCorporateChildren( repository );
        logUtils.logKids( repository );

        List<CorporateDependent>    removed         = new Remover<CorporateDependent>().removeKidsFromList( 4, 2, corporateKids );
        List<Child>                 removedKids     = new Remover<Child>().removeKidsFromList( 0, 2, children );
        DependentDiffs              dependentDiffs  = new DependentDiffs();
        List<String>                messages        = new ArrayList<String>();
        dependentDiffs.setLogMessages( messages );

        dependentDiffs.diff( repository );
        String output = CommonUtils.join( messages, "\n" );

        logKids( repository, output );

        for ( CorporateDependent kid : removed ) {

            assertThat( output,
                        containsString( kid.getFirstName() ) );
        }

        for ( Child kid : removedKids ) {

            assertThat( output.toLowerCase(),
                        containsString( kid.getFirstName().toLowerCase() ) );
        }
    }


    @Test
    public void shouldReportMissingCorporateSpouse() {

        RbpsRepository  repository  = new RbpsRepository();
        Veteran         veteran     = CommonFactory.adamsVeteran();

        repository.setVeteran( veteran );
        CommonFactory.addChildren( veteran, 6 );
        List<Child> children = veteran.getChildren();

        List<CorporateDependent> corporateKids = CoreFactory.getCorporateChildren( repository, children );
        repository.setChildren( corporateKids );
        repository.setSpouse( null );

        String output = callDiffs( repository );

        assertThat( output, containsString( repository.getVeteran().getCurrentMarriage().getMarriedTo().getFirstName() ) );
    }


    @Test
    public void shouldReportMissingXomSpouse() {

        RbpsRepository  repository  = new RbpsRepository();
        Veteran         veteran     = CommonFactory.adamsVeteran();

        repository.setVeteran( veteran );
        CommonFactory.addChildren( veteran, 6 );
        List<Child> children = veteran.getChildren();

        Spouse                      spouse              = veteran.getCurrentMarriage().getMarriedTo();
        CorporateDependent          corporateSpouse     = CoreFactory.getCorporateSpouse( repository, spouse );
        List<CorporateDependent>    corporateKids   = CoreFactory.getCorporateChildren( repository, children );

        repository.setChildren( corporateKids );
        repository.setSpouse( corporateSpouse );
        veteran.setCurrentMarriage( null );

        String output = callDiffs( repository );

        assertThat( output, containsString( repository.getSpouse().getFirstName() ) );
    }


    @Test
    public void shouldReportMismatchingSpouse() {

        RbpsRepository  repository  = new RbpsRepository();
        Veteran         veteran     = CommonFactory.adamsVeteran();

        repository.setVeteran( veteran );
        CommonFactory.addChildren( veteran, 6 );
        List<Child> children = veteran.getChildren();

        Spouse                      spouse              = veteran.getCurrentMarriage().getMarriedTo();
        CorporateDependent          corporateSpouse     = CommonFactory.getCorporateSpouse( repository, spouse );
        List<CorporateDependent>    corporateKids       = CoreFactory.getCorporateChildren( repository, children );

        repository.setChildren( corporateKids );
        repository.setSpouse( corporateSpouse );
        veteran.setCurrentMarriage( CommonFactory.getRandomMarriage( veteran ) );

        String output = callDiffs( repository );

        assertThat( output, containsString( repository.getSpouse().getFirstName() ) );
        assertThat( output, containsString( repository.getVeteran().getCurrentMarriage().getMarriedTo().getFirstName() ) );
    }


    @Test
    public void shouldHandlePamsCase() {

        String              dependentsPath  = "gov/va/vba/rbps/services/populators/999507853.findDep.response.xml";
        String              userInfoPath    = "gov/va/vba/rbps/services/populators/999507853.userinfo.xml";
        RbpsRepository      repo            = new RbpsRepository();
        List<String>        messages        = new ArrayList<String>();

        TestUtils                               testUtils               = new TestUtils();
        DependentDiffs                          diff                    = new DependentDiffs();
        UpdateBenefitClaimDependentsPopulator   updateDependents        = new UpdateBenefitClaimDependentsPopulator();

//        testUtils.populateUserInfo( repo, userInfoPath );
        testUtils.populateCorporateDependents( repo, dependentsPath );

        diff.setLogMessages( messages );
        diff.diff( repo );

//        updateDependents.setRepository( repo );
        updateDependents.buildAddDependentRequest( repo );
    }


    private String callDiffs( final RbpsRepository repository ) {

        DependentDiffs dependentDiffs   = new DependentDiffs();
        List<String>    messages        = new ArrayList<String>();
        dependentDiffs.setLogMessages( messages );

        dependentDiffs.diff( repository );
        String output = CommonUtils.join( messages, "\n" );

        out( output );
        return output;
    }


    private void out( final String        msg ) {

        if ( ! logit ) {

            return;
        }

        System.out.println( msg );
    }


    public void logKids( final RbpsRepository           repository,
                         final String                   output ) {

        out( "========================================" );
        logUtils.logCorporateChildren( repository );
        logUtils.logKids( repository );
        out( output );
    }


    class Remover<T> {


        private List<T> removeKidsFromList( final int numToBeRemoved, final List<T> kids ) {

            int size = kids.size();
            return removeKidsFromList( (size - numToBeRemoved), numToBeRemoved, kids );
        }


        private List<T> removeKidsFromList( final int       startingPosition,
                                            final int       numToBeRemoved,
                                            final List<T>   kids ) {

            int size = kids.size();
            List<T> removed = new ArrayList<T>(kids.subList( startingPosition, startingPosition + numToBeRemoved ));

            for ( T child : removed ) {

                kids.remove( child );
            }

            return removed;
        }
    }
}
