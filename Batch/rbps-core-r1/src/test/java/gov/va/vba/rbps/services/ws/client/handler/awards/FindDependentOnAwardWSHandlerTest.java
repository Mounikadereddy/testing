/*
 * FindDependentOnAwardWSHandlerTest.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.services.ws.client.handler.awards;


import gov.va.vba.rbps.RbpsAbstractTest;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.xom.Child;
import gov.va.vba.rbps.fixtures.CommonFactory;
import gov.va.vba.rbps.services.ws.client.mapping.awards.dep.FindDependentOnAwardResponse;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.Before;
import org.junit.Test;


/**
 *      Test case of FindDependentOnAwardWSHandler
 */
public class FindDependentOnAwardWSHandlerTest extends RbpsAbstractTest  {

    private FindDependentOnAwardWSHandler  awardsFindDependentOnAwardWSHandler;


    @Override
    @Before
    public void setup() {

        super.setup();
        awardsFindDependentOnAwardWSHandler =  (FindDependentOnAwardWSHandler) getBean("awardsFindDependentOnAwardWSHandler");
    }


	   @Test
	    public void testFindDependentOnAward() {

	        RbpsRepository repository = (RbpsRepository) getBean( "repository" );
	        repository.setVeteran( CommonFactory.georgeVeteran() );
	        repository.getVeteran().setFileNumber( "263819301" );

	        FindDependentOnAwardResponse response = awardsFindDependentOnAwardWSHandler
	                .findDependentOnAward( repository.getVeteran().getChildren().get(0).getVnpParticipantId(), repository );

	        System.out.println( "award type:            " + response.getReturn().getAwardType());
	        System.out.println( "is dependent on award: " + response.getReturn().getIsDependentOnAward());
	    }


//    @RunTags(tags={"Spring", "Populator"} )
    @Test
    public void testFindDependentOnAwardOne() {

        RbpsRepository repository = (RbpsRepository) getBean( "repository" );
        repository.setVeteran( CommonFactory.georgeVeteran() );

        List <Child>  children    =   new ArrayList<Child>();
        Child         child       =   CommonFactory.getRandomChild( repository.getVeteran() );

        repository.getVeteran().setCorpParticipantId(9100776412L);
        child.setCorpParticipantId(9100778207L);
        children.add( child );
        repository.getVeteran().setChildren(children);
        repository.getVeteran().setCorpParticipantId(9100776412L);

        FindDependentOnAwardResponse response = awardsFindDependentOnAwardWSHandler
                .findDependentOnAward( child.getCorpParticipantId(), repository );

        System.out.println( "Response:            " + ToStringBuilder.reflectionToString(response.getReturn(),ToStringStyle.MULTI_LINE_STYLE));
    }
}
