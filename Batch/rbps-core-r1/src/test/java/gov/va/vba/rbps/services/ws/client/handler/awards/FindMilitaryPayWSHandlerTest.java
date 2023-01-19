/*
 * FindMilitaryPayWSHandlerTest.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.services.ws.client.handler.awards;


import static org.junit.Assert.assertEquals;
import gov.va.vba.rbps.RbpsAbstractTest;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.util.CommonUtils;
import gov.va.vba.rbps.fixtures.CommonFactory;
import gov.va.vba.rbps.services.ws.client.mapping.awards.militarypay.FindMilitaryPayResponse;
import gov.va.vba.rbps.services.ws.client.mapping.awards.militarypay.RetirementPayVO;
import gov.va.vba.rbps.services.ws.client.util.SoapFaultPrinter;

import java.util.List;

import org.junit.Before;
import org.junit.Test;


public class FindMilitaryPayWSHandlerTest extends RbpsAbstractTest{

    FindMilitaryPayWSHandler findMilitaryPayWSHandler;


    @Override
    @Before
    public void setup() {
        super.setup();

        findMilitaryPayWSHandler = (FindMilitaryPayWSHandler)getBean( "awardsFindMilitaryPayWSHandler" );
    }


//  @RunTags(tags={"Spring", "Populator"} )
    @Test
    public void testFindMilitaryPay() throws Throwable {

        try {
            RbpsRepository repository = (RbpsRepository) getBean( "repository" );
            repository.setVeteran( CommonFactory.adamsVeteran() );

            int corpId = 30837971;
            repository.getVeteran().setCorpParticipantId( corpId );
            FindMilitaryPayResponse response = findMilitaryPayWSHandler.findMilitaryPay( repository );
            List<RetirementPayVO> retirementPayStuff = response.getReturn().getRtrmntPayVOList();

            if ( retirementPayStuff.isEmpty() ) {

                System.out.println( "No results found for " + corpId );
//                System.out.println( "message back is...." + response.getReturn().getMessageList() );
                System.out.println( "message back is...." + CommonUtils.stringBuilder( response.getReturn().getVnErrorsVOList() ) );
            }
            else {

                System.out.println( retirementPayStuff.get(0).getRetirementPayType() );

                assertEquals( retirementPayStuff.get(0).getRetirementPayType(), "U" );
            }
        }
        catch ( Throwable ex ) {

            System.out.println( new SoapFaultPrinter().printSoapFaultInfo( ex ) );

            throw ex;
        }
    }
}
