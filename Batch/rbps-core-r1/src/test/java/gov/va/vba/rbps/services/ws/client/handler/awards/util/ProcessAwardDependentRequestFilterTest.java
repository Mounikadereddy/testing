/*
 * ProcessAwardDependentRequestFilterTest
 *
 * Copyright 2012 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.services.ws.client.handler.awards.util;



import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import gov.va.vba.rbps.RbpsAbstractTest;
import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.coreframework.util.RbpsUtil;
import gov.va.vba.rbps.coreframework.util.SimpleDateUtils;
import gov.va.vba.rbps.services.ws.client.mapping.awards.amend.AmendDependencyDecisionVO;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;




public class ProcessAwardDependentRequestFilterTest extends RbpsAbstractTest {


    private SimpleDateUtils                     dateUtils   = new SimpleDateUtils();
    private ProcessAwardDependentRequestFilter  filter      = new ProcessAwardDependentRequestFilter();



    @Override
    @Before
    public void setup() {

        super.setup();

        filter = (ProcessAwardDependentRequestFilter) getBean( "processAwardDependentRequestFilter" );
    }


    @Test
    public void sameDecisionDetailWithInDateRange() {

        LogUtils.setGlobalLogit( true );
        AmendDependencyDecisionVO   amendDecision       = populateAmendDecision( "SCHATTB", "SCHCHD", "10-15-2011" );
        Date                        startDate           = dateUtils.convertDate( "start date", "10-10-2011" );
        Date                        endDate             = dateUtils.convertDate( "end date", "11-11-2011" );
        DecisionDetails             decisionDetails     = populateDecisionDetails( "SCHATTB", "SCHCHD", startDate, endDate );

        boolean check = filter.doesSameDecisionDetailWithInDateRange( amendDecision, decisionDetails );

        assertThat( check, is(equalTo( true ) ) );
    }


    @Test
    public void sameDecisionDetailWithOutDateRange() {

        AmendDependencyDecisionVO   amendDecision       = populateAmendDecision( "SCHATTB", "SCHCHD", "11-12-2011" );
        Date                        startDate           = dateUtils.convertDate( "start date", "10-10-2011" );
        Date                        endDate             = dateUtils.convertDate( "end date", "11-11-2011" );
        DecisionDetails             decisionDetails     = populateDecisionDetails( "SCHATTB", "SCHCHD", startDate, endDate);

        boolean check = filter.doesSameDecisionDetailWithInDateRange( amendDecision, decisionDetails );

        assertThat( check, is(equalTo( false ) ) );
    }


    @Test
    public void differentDecisionDetailWithInDateRange() {

        AmendDependencyDecisionVO   amendDecision       = populateAmendDecision( "EMC", "MC", "10-15-2011" );
        Date                        startDate           = dateUtils.convertDate( "start date", "10-10-2011" );
        Date                        endDate             = dateUtils.convertDate( "end date", "11-11-2011" );
        DecisionDetails             decisionDetails     = populateDecisionDetails( "SCHATTB", "SCHCHD", startDate, endDate);

        boolean check = filter.doesSameDecisionDetailWithInDateRange( amendDecision, decisionDetails );

        assertThat( check, is(equalTo( false ) ) );
    }


    @Test
    public void differentDecisionDetailWithOutDateRange() {

        AmendDependencyDecisionVO   amendDecision       = populateAmendDecision( "EMC", "MC", "11-12-2011" );
        Date                        startDate           = dateUtils.convertDate( "start date", "10-10-2011" );
        Date                        endDate             = dateUtils.convertDate( "end date", "11-11-2011" );
        DecisionDetails             decisionDetails     = populateDecisionDetails( "SCHATTB", "SCHCHD", startDate, endDate);

        boolean check = filter.doesSameDecisionDetailWithInDateRange( amendDecision, decisionDetails );

        assertThat( check, is(equalTo( false ) ) );
    }


    @Test
    public void sameDecisionDetailEndDateEventDateSame() {

        AmendDependencyDecisionVO   amendDecision       = populateAmendDecision( "SCHATTB", "SCHCHD", "11-11-2011" );
        Date                        startDate           = dateUtils.convertDate( "start date", "10-10-2011" );
        Date                        endDate             = dateUtils.convertDate( "end date", "11-11-2011" );
        DecisionDetails             decisionDetails     = populateDecisionDetails( "SCHATTB", "SCHCHD", startDate, endDate);

        boolean check = filter.doesSameDecisionDetailWithInDateRange( amendDecision, decisionDetails );

        assertThat( check, is(equalTo( false ) ) );
    }








    private AmendDependencyDecisionVO populateAmendDecision( final String       decisionType,
                                                             final String       statusType,
                                                             final String       eventDate ) {

        SimpleDateUtils             dateUtils           = new SimpleDateUtils();
        AmendDependencyDecisionVO   dependencyDecision  = new AmendDependencyDecisionVO();

        dependencyDecision.setDependencyDecisionType( decisionType );
        dependencyDecision.setDependencyStatusType( statusType );
        dependencyDecision.setEventDate( RbpsUtil.dateToXMLGregorianCalendar( dateUtils.convertDate( "event date", eventDate ) ) );

        return dependencyDecision;
    }


    private DecisionDetails populateDecisionDetails( final String decisionType,
                                                     final String statusType,
                                                     final Date   startDate,
                                                     final Date   endDate ) {

        DecisionDetails decisionDetails = new DecisionDetails();

        decisionDetails.setStartDecisionType( decisionType );
        decisionDetails.setStartStatusType( statusType );
        decisionDetails.setEventStartDate( startDate );
        decisionDetails.setEventEndDate( endDate );

        return decisionDetails;
    }

}
