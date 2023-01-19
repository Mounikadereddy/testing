/*
 * CreateNoteWSHandlerTest.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.services.ws.client.handler.mapd;


import gov.va.vba.rbps.RbpsAbstractTest;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.fixtures.CommonFactory;
import gov.va.vba.rbps.services.ws.client.mapping.mapd.notes.CreateNoteResponse;

import org.junit.Before;
import org.junit.Test;


/**
 *      Test case for CreateNoteWSHandler class
 */
public class CreateNoteWSHandlerTest extends RbpsAbstractTest {

    private CreateNoteWSHandler         createNoteWSHandler;
    private RbpsRepository              repo;



    @Override
    @Before
    public void setup() {

        super.setup();
        createNoteWSHandler = (CreateNoteWSHandler)  getBean( "createNoteWSHandler" );

        repo = (RbpsRepository) getBean( "repository" );
        repo.setVeteran( CommonFactory.adamsVeteran() );
    }


    @Test
    public void testcreateNote() {

//        repo.getVeteran().setCorpParticipantId( 30843606 );
//        repo.getVeteran().getClaim().setClaimId( 9792369 );

        CreateNoteResponse response = createNoteWSHandler.createNote( "joe", repo );

        if ( response.getNote() == null ) {

            System.out.println( "Note came back empty." );
            throw new IllegalArgumentException( "Note came back empty." );
        }

        System.out.println( "txt       = " + response.getNote().getTxt());
        System.out.println( "node id   = " + response.getNote().getNoteId());
    }
}
