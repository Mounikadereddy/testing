/*
 * CreateNoteWSHandler.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.services.ws.client.handler.mapd;


import static gov.va.vba.rbps.coreframework.util.RbpsUtil.stringListToStringBuilder;
import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.exception.RbpsWebServiceClientException;
import gov.va.vba.rbps.coreframework.util.CommonUtils;
import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.coreframework.util.RbpsConstants;
import gov.va.vba.rbps.coreframework.util.RbpsUtil;
import gov.va.vba.rbps.coreframework.util.SimpleDateUtils;
import gov.va.vba.rbps.services.ws.client.mapping.mapd.notes.CreateNote;
import gov.va.vba.rbps.services.ws.client.mapping.mapd.notes.CreateNoteResponse;
import gov.va.vba.rbps.services.ws.client.mapping.mapd.notes.Note;
import gov.va.vba.rbps.services.ws.client.util.HeaderSetter;

import java.util.Date;

import org.springframework.ws.client.core.WebServiceTemplate;


/**
 *      It is a web service client interface to the CreateNote operation
 *      in the DevelopmentNotes web service provided by the MAPD team.
 */
public class CreateNoteWSHandler {

    private static Logger logger = Logger.getLogger(CreateNoteWSHandler.class);

//    private CommonUtils             utils               = new CommonUtils();
//    private SimpleDateUtils			dateUtils			= new SimpleDateUtils();
    private LogUtils                logUtils            = new LogUtils( logger, true );

    // Spring beans references/injection
    //private RbpsRepository          repository;
    private String                  developmentNotesUri;
    private WebServiceTemplate      webServiceTemplate;
    private long                    lastClaimId                 =   -1;
    private String                  lastNote                    =   "";




    public CreateNoteResponse createNote(final String text, RbpsRepository repository) throws RbpsWebServiceClientException {

        return createNote(buildCreateNoteRequest( text, repository), repository);
    }


    /**
     *      This method calls the MAPD service
     *      DevelopmentNotes.createNote
     *      @return createNoteResponse
     *      @throws RbpsWebServiceClientException
     */
    private CreateNoteResponse createNote(final CreateNote request,RbpsRepository repository) throws RbpsWebServiceClientException {

        logUtils.debugEnter( repository );

        // if the request only contains an empty string, don't add the note TODO: does this belong here?
        if (request.getNote() == null || request.getNote().getTxt() == null || request.getNote().getTxt().trim().isEmpty()) {

            logUtils.log( "note is empty, aborting adding to MAP-D", repository );

            return new CreateNoteResponse();
        }

        //      Make the call
        Object response;
        try {
            response = webServiceTemplate.marshalSendAndReceive( this.developmentNotesUri,
                                                                 request,
                                                                 new HeaderSetter( this.developmentNotesUri,
                                                                                   repository.getClaimStationAddress() ) );
        }
        catch (Throwable rootCause) {
            String detailMessage = "Call to WS DevelopmentNotes.createNote failed >> ";

            repository.addValidationMessage( CommonUtils.getValidationMessage(developmentNotesUri, request.getClass().getSimpleName(), rootCause.getMessage()) );

            logger.error( detailMessage );
            logger.error( rootCause.getMessage() );

            throw new RbpsWebServiceClientException(detailMessage, rootCause);
        }
        finally {

            logUtils.debugExit( repository );
        }

        return (CreateNoteResponse)response;
    }


    /**
     * Build a CreateNote request object from repository data
     * <p>
     * -- Note from Anil Mathai on CreateNote usage 8/3/2011 --
     * <p>
     * These are the required fields:
     * PTCPNT_ID, CREATE_DT, TXT, PTCPNT_NOTE_TYPE_NM, BNFT_CLAIM_NOTE_TYPE_CD, BNFT_CLAIM_ID
     * </p>
     * These are sample values MAPD populates
     * <ul>
     * <li>CLM_ID = 9662785 </li>
     * <li>PTCPNT_ID = 363414</li>
     * <li>CREATE_DT = 08/03/2011 10:56:16 AM</li>
     * <li>PTCPNT_NOTE_TC = CLMNTCONTACT </li>
     * <li>NOTE_OUT_TN   = Contact with Claimant</li>
     * <li>TXT = "some text" (text is limited to 2000 chars)</li>
     * <li>USER_ID = 329056 (ptcpnt id of the  user who created the note)</li>
     * <li>USER_NM = name of the user who created the note</li>
     * </ul>
     * <p>
     * -- Note from Joe Peterson 8/3/2011 --
     * <p>
     * Only 3 fields are required: claim id, the claim note type, and the note text itself.
     * <p>
     * Oracle database schema: - http://vbacoda.vba.va.gov/plsql/desc_util.startup
     * <p>
     * Table CORPPROD.BNFT_CLAIM_NOTE
     * <ul>
     * <li>BNFT_CLAIM_ID - NUMBER(15)</li>
     * <li>BNFT_CLAIM_NOTE_TYPE_CD - VARCHAR2(12)</li>
     * <li>NOTE_TXT - VARCHAR2(2000)</li>
     * </ul>
     * <p>
     * Table CORPPROD.BNFT_CLAIM_NOTE_TYPE - Name/Code
     * <ul>
     * <li>ClaimDevelopmentNote - CLMDVLNOTE</li>
     * </ul>
     *
     * @return CreateNote
     */
    private CreateNote buildCreateNoteRequest( final String text,RbpsRepository repository ) {

        logUtils.debugEnter( repository );

        CreateNote      createNote  = new CreateNote();
        Note            note        = new Note();
        StringBuilder   noteText    = new StringBuilder("");

        if ( repository.getVeteran() == null ) {
        	
        	note.setClmId(Long.toString(repository.getBnftClaimId()));
        }
        else {
        	note.setClmId(Long.toString(repository.getVeteran().getClaim().getClaimId()));
        }
        
        note.setBnftClmNoteTc(RbpsConstants.NOTE_TYPE);
        note.setCreateDt( RbpsUtil.dateToXMLGregorianCalendar( new Date() ) );

        if ( ! repository.getRuleExceptionMessages().getMessages().isEmpty() ) {

            noteText.append(stringListToStringBuilder(repository.getRuleExceptionMessages().getMessages()));
        }

        if ((text != null) && (text.trim().length() != 0)) {
            noteText.append(text);
        }

        if ( sameClaimIdAndNote( noteText, repository ) ) {

            logUtils.log( "New Note same as Old Note, not sending again.", repository);
            return new CreateNote();
        }

//        lastClaimId =  repository.getVeteran().getClaim().getClaimId();
        lastNote    =  noteText.toString();

        int endIndex = (noteText.length() < 2000) ? noteText.length()  : 1999;
        note.setTxt(noteText.substring(0, endIndex));
        
        //Linda's email - 10/31/2012 - Release 2 CR - RBPS00000383 
        note.setModifdDt( SimpleDateUtils.dateToXMLGregorianCalendar( new Date() ) );
        createNote.setNote(note);

        return createNote;
    }


    private boolean sameClaimIdAndNote( final StringBuilder     noteText,RbpsRepository repository) {

    	if ( repository.getVeteran() == null ) {
    		logUtils.log("Veteran object is null", repository );
    		return false;
    	}
    	
        logUtils.log( String.format( "new  ClaimId : %d,\n\t       New  Note : %s",
                                     repository.getVeteran().getClaim().getClaimId(),
                                     noteText.toString() ), repository );

        return ( repository.isSameClaimId( lastClaimId ) && lastNote.equals( noteText.toString() )  );
    }


/*
    public void setRepository(final RbpsRepository repository) {
        this.repository = repository;
    }
*/

    public void setDevelopmentNotesUri(final String endpoint) {
	String clusterAddr = CommonUtils.getClusterAddress();
        this.developmentNotesUri = clusterAddr + "/" + endpoint;
    }

    public void setWebServiceTemplate(final WebServiceTemplate webServiceTemplate) {
        this.webServiceTemplate = webServiceTemplate;
    }
}
