/*
 * FindDependencyDecisionByAwardTest
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.services.populators;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import gov.va.vba.rbps.RbpsAbstractTest;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.util.SimpleDateUtils;
import gov.va.vba.rbps.coreframework.xom.Award;
import gov.va.vba.rbps.coreframework.xom.DependentDecisionType;
import gov.va.vba.rbps.coreframework.xom.DependentStatusType;
import gov.va.vba.rbps.services.ws.client.handler.awards.util.DependencyDecisionByAwardProducer;
import gov.va.vba.rbps.services.ws.client.handler.awards.util.ProcessAwardDependentRequestFilter;
import gov.va.vba.rbps.services.ws.client.mapping.awards.amend.ProcessAwardDependent;
import gov.va.vba.rbps.services.ws.client.mapping.awards.dependencydecision.FindDependencyDecisionByAwardResponse;
import gov.va.vba.rbps.services.ws.client.util.TestUtils;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;


public class FindDependencyDecisionByAwardTest  extends RbpsAbstractTest {

    private ProcessAwardDependentRequestFilter          filter;
    private DependencyDecisionByAwardProducer           producer;
    private RbpsRepository                              repository;
    private FindDependencyDecisionByAwardResponse       response;
    private SimpleDateUtils                             dateUtils   = new SimpleDateUtils();

    @Override
    @Before
    public void setup() {

        super.setup();

        repository      = (RbpsRepository) getBean( "repository" );
        producer        = (DependencyDecisionByAwardProducer) getBean( "dependencyDecisionByAwardProducer" );
        filter          = (ProcessAwardDependentRequestFilter) getBean( "processAwardDependentRequestFilter" );
    }


    @Test
    public void shouldAddFrom796066634XmlResponse() {

        ProcessAwardDependent   awardDependent  = null;

        try {
            awardDependent  =   setupAwardDependent( "9998076101",
                                                     9100780270L,
                                                     DependentDecisionType.SCHOOL_ATTENDANCE_BEGIN_DATE,
                                                     DependentStatusType.SCHOOL_CHILD,
                                                     "12-20-2011",
                                                     "06-02-2016" );
        }
        catch (Throwable rootCause) {

        }
        assertThat( awardDependent.getAmendAwardDependencyInput().getDependencyList().size(),
                    is(equalTo( 1 )) );
    }


    @Test
    public void shouldAddFrom999807610XmlResponseForSameOmnibussedEventDateAndEndDate() {

        ProcessAwardDependent awardDependent = setupAwardDependent( "9998076101",
                                                                    9100780270L,
                                                                    DependentDecisionType.SCHOOL_ATTENDANCE_BEGIN_DATE,
                                                                    DependentStatusType.SCHOOL_CHILD,
                                                                    "12-20-2011",
                                                                    "06-02-2016" );

        assertThat( "same omnibussed event date and end date add to request",
                    awardDependent.getAmendAwardDependencyInput().getDependencyList().size(),
                    is(equalTo( 1 )));
    }


    @Test
    public void shouldAddFrom999807610XmlResponseForOmnibussedEventDate() {

        ProcessAwardDependent awardDependent = setupAwardDependent( "999807610",
                                                                    9100780270L,
                                                                    DependentDecisionType.SCHOOL_ATTENDANCE_BEGIN_DATE,
                                                                    DependentStatusType.SCHOOL_CHILD,
                                                                    "12-20-2011",
                                                                    "06-02-2016" );

         assertThat( awardDependent.getAmendAwardDependencyInput().getDependencyList().size(),
                     is(equalTo( 1 )));
    }


    @Test
    public void shouldThrowExceptionForNoDependentRequest() {

        boolean         exceptionFlag   = false;

        try {
            setupAwardDependent( "999807609",
                                 9100780231L,
                                 DependentDecisionType.SCHOOL_ATTENDANCE_BEGIN_DATE,
                                 DependentStatusType.SCHOOL_CHILD,
                                 "09-30- 2012",
                                 null );
        }
        catch (Throwable rootCause) {

            exceptionFlag = true;
        }

        assertThat( exceptionFlag, is(equalTo( true )));
    }


    @Test
     public void shouldAddFrom796093718XmlResponse() {

        ProcessAwardDependent awardDependent = setupAwardDependent( "796093718",
                                                                    0L,
                                                                    DependentDecisionType.ELIGIBLE_MINOR_CHILD,
                                                                    DependentStatusType.MINOR_CHILD,
                                                                    "09-01- 2011",
                                                                    null );

        assertThat( awardDependent.getAmendAwardDependencyInput().getDependencyList().size(), is(equalTo( 1 )));
     }


   @Test
    public void shouldAddFrom7960666341XmlResponse() {

        ProcessAwardDependent awardDependent = setupAwardDependent( "796066634-1",
                                                                    0L,
                                                                    DependentDecisionType.SCHOOL_ATTENDANCE_BEGIN_DATE,
                                                                    DependentStatusType.SCHOOL_CHILD,
                                                                    "09-01- 2011",
                                                                    null );
        assertThat( awardDependent.getAmendAwardDependencyInput().getDependencyList().size(), is(equalTo( 1 )));
    }


//    @RunTags(tags={"InputXml", "Populator", "Release"} )
    @Test
    public void shouldAddFrom898255565XmlResponse() {

        ProcessAwardDependent awardDependent = setupAwardDependent( "898255565",
                                                                    0L,
                                                                    DependentDecisionType.ELIGIBLE_MINOR_CHILD,
                                                                    DependentStatusType.MINOR_CHILD,
                                                                    "04-11- 2011",
                                                                    null );

        assertThat( awardDependent.getAmendAwardDependencyInput().getDependencyList().size(), is(equalTo( 1 )));
    }


//    @RunTags(tags={"InputXml", "Populator", "Release"} )
    @Test
    public void shouldAddFrom9100777160XmlResponse() {

        TestUtils   testUtils = new TestUtils();
        String          xmlPath         = "gov/va/vba/rbps/services/populators/9100777160/dependencyDecision.response.xml";

        response       = testUtils.getDependencyDecisionResponseFromXml( xmlPath );

        testUtils.populateViaClaimResponses( getContext(), repository, "9100777160" );

        repository.getVeteran().getChildren().get(0).setAward(
                             getAward(
                                     DependentDecisionType.SCHOOL_ATTENDANCE_BEGIN_DATE,
                                     DependentStatusType.SCHOOL_CHILD,
                                     dateUtils.convertDate("", "12-01- 2010"),
                                     dateUtils.convertDate("", "06-02- 2012")
                                     ) );
       repository.getVeteran().getChildren().get(1).setAward(
                getAward(
                        DependentDecisionType.SCHOOL_ATTENDANCE_BEGIN_DATE,
                        DependentStatusType.SCHOOL_CHILD,
                        dateUtils.convertDate("", "09-15- 2011"),
                        dateUtils.convertDate("", "05-04- 2012")
                        ) );
       producer.addFakeResponse(repository.getVeteran().getClaim().getClaimId(), response);

        ProcessAwardDependent awardDependent = filter.getFilteredProcessAwardDependentRequest( repository );
        assertThat( awardDependent.getAmendAwardDependencyInput().getDependencyList().size(), is(equalTo( 2 )));
    }


    private ProcessAwardDependent setupAwardDependent( final String                       claimId,
                                                       final Long                         childParticipantId,
                                                       final DependentDecisionType        decisionType,
                                                       final DependentStatusType          statusType,
                                                       final String                       beginDate,
                                                       final String                       endDate ) {

        TestUtils   testUtils = new TestUtils();

        testUtils.populateViaClaimResponses( getContext(), repository, claimId );

        setParticipantIdOnFirstChild( childParticipantId );
        setupAwardForChild( decisionType, statusType, beginDate, endDate );

        return filter.getFilteredProcessAwardDependentRequest( repository );
    }


    public void setParticipantIdOnFirstChild( final Long childParticipantId ) {

        repository.getVeteran().getChildren().get(0).setCorpParticipantId( childParticipantId );
    }


    public void setupAwardForChild( final DependentDecisionType     decisionType,
                                    final DependentStatusType       statusType,
                                    final String                    beginDate,
                                    final String                    endDate ) {

        repository.getVeteran().getChildren().get(0).setAward(
                             getAward( decisionType,
                                       statusType,
                                       dateUtils.convertDate("", beginDate ),
                                       dateUtils.convertDate("", endDate )
                                         ) );
    }


    private Award getAward( final DependentDecisionType         decisionType,
                            final DependentStatusType           statusType,
                            final Date                          eventDate,
                            final Date                          endDate) {

        Award award = new Award();
        award.setDependencyDecisionType(decisionType);
        award.setDependencyStatusType(statusType);
        award.setEventDate( eventDate );
        award.setEndDate( endDate );

        return award;
    }
}
