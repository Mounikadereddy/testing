/*
 * RbpsWS.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.services.ws;


import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.util.CommonUtils;
import gov.va.vba.rbps.coreframework.util.RbpsConstants;
import gov.va.vba.rbps.coreframework.util.SaiToStringStyle;
import gov.va.vba.rbps.services.impl.RbpsServiceFacade;
import gov.va.vba.rbps.services.interfaces.RbpsServiceInterface;
import gov.va.vba.rbps.services.populators.CheckerServicePopulator;
import gov.va.vba.rbps.services.ws.client.RbpsFaultBean;

import java.io.File;
import java.io.FileWriter;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import org.apache.commons.lang.builder.ToStringBuilder;


@WebService(
        serviceName     = "RbpsWS",
        portName        = "RbpsWSPort",
        targetNamespace = "http://ws.services.rbps.vba.va.gov/"
)
public class RbpsWS {


    private static Logger logger = Logger.getLogger(RbpsWS.class);
//	private static Logger logger;
    /**
     * Spring injected. Looks like there is no need for @Autowired
     */
    private RbpsServiceInterface    rbpsService;
    private Date                    startTime;
    private static AtomicBoolean    processing          = new AtomicBoolean( false );
    private static boolean			sbDefaultSet 		= false;
    private String					currentProcessFilePath;
    private CheckerServicePopulator checkerServicePopulator;
    
    /**
     * Needed by JAXB keep it public as per JAXB for now
     * move to private if needed will be fine with JAXB as well
     */
    public RbpsWS() {}



    @WebMethod(operationName = "processRbpsAmendDependency")
    public String processRbpsAmendDependency( @WebParam( name = "currentProcess" ) final String currentProcess, @WebParam( name = "totalProcessCount" ) final String totalProcessCount ) throws RbpsWsException{
    	
    	RbpsRepository repo = new RbpsRepository();
    	
    	Thread.currentThread().setName("currentProcess: " + currentProcess );
    	
        startTime   = new Date();
        String      retVal = "";
        String      className               = this.getClass().getSimpleName();
        String      methodName              = new Exception().getStackTrace()[0].getMethodName();
        boolean     currentlyProcessing     = false;
        CommonUtils.log( logger,  "\n" + RbpsConstants.BAR_FORMAT + "\n\n    *** Entering " + className + "." + methodName + " : " + RbpsConstants.VERSION + "/" + Thread.currentThread().getName() + "\n\n" + RbpsConstants.BAR_FORMAT + "\n");

        CommonUtils.log( logger, String.format("currentProcess %s, totalProcessCount %s ", currentProcess, totalProcessCount ) );
        boolean inDelete = false;
        try {
//            if ( ! safeToStartProcessing() ) {
//
//                utils.log( logger, "Some other thread is processing claims, returning" );
//                return "RBPS is already running, so exiting..";
//            }
        	if( ! checkIfAllServicesAreup( repo ) ) {
        		
        		CommonUtils.log( logger, "All services required to run RBPS are not up and running, returning" );
        		return "All services required to run RBPS are not up and running";
        	}
        	
        	
        	String fileWriteStatus = writeCurrentProcessFile( currentProcess, totalProcessCount);
            if ( fileWriteStatus.trim().startsWith("Error")  ) {
            	
            	return fileWriteStatus;
            }
            
            currentlyProcessing = true;

            // ToStringBuilder.setDefaultStyle not thread safe - call only once
            if (!isSbDefaultSet()){
            	synchronized (ToStringBuilder.class) {            	
            		setSbDefaultSet(true);
            		ToStringBuilder.setDefaultStyle( new SaiToStringStyle() );	
            	}
            }

            rbpsService.executeAll(currentProcess, totalProcessCount, repo);

            retVal = repo.getJournalStr();
            
            deleteCurrentProcessFile( currentProcess);
            
            return retVal;
        }
        catch (Exception ex) {
        	
        	deleteCurrentProcessFile( currentProcess);
            retVal = repo.getJournalStr();
            RbpsFaultBean faultInfo = new RbpsFaultBean();
            faultInfo.setMessage(retVal);
            throw new RbpsWsException( "RbpsWS runtime exception:", faultInfo, ex );
        }
        finally {
        	
            displayElapsed( className, methodName, null);
            markSafeToStartProcessing( currentlyProcessing);
        }
    }



    @WebMethod(operationName = "processRbpsByProcId")
    public String processRbpsByProcId( @WebParam( name = "procId" ) final String   procId ) throws RbpsWsException{

    	RbpsRepository repo = new RbpsRepository();
    	startTime   = new Date();
        String      retVal;
        String      className               = this.getClass().getSimpleName();
        String      methodName              = new Exception().getStackTrace()[0].getMethodName();
        boolean     currentlyProcessing     = false;

        CommonUtils.log( logger,  "\n" + RbpsConstants.BAR_FORMAT + "\n\n    *** Entering " + className + "." + methodName + "(" + RbpsConstants.VERSION + "/" + Thread.currentThread().getName() + ") : " + procId + "\n\n" + RbpsConstants.BAR_FORMAT + "\n");

        try {
            if ( ! safeToStartProcessing() ) {

                CommonUtils.log( logger, "Some other thread is processing claims, returning" );
                return "RBPS is already running, so exiting..";
            }
            
        	if( ! checkIfAllServicesAreup( repo ) ) {
        		
        		return "All services required to run RBPS are not up and running";
        	}
        	
            currentlyProcessing = true;
            ToStringBuilder.setDefaultStyle( new SaiToStringStyle() );

            ((RbpsServiceFacade) rbpsService).execute( procId, repo );

            retVal = repo.getJournalStr();

            return retVal;
        }
        catch (Exception ex) {

            retVal = repo.getJournalStr();
            RbpsFaultBean faultInfo = new RbpsFaultBean();
            faultInfo.setMessage(retVal);
            throw new RbpsWsException( "RbpsWS runtime exception:",
                                       faultInfo,
                                       ex );
        }
        finally {

            displayElapsed( className, methodName, procId );
            markSafeToStartProcessing( currentlyProcessing );
        }
    }

    
    private boolean checkIfAllServicesAreup( RbpsRepository repo ) {
    	
    	try {
    		
    		return checkerServicePopulator.ifAllServicesAreUp( repo );
    	}
    	catch( Exception ex ){
    		
    		return false;
    	}
    }
    
    private void displayElapsed( final String     className,
                                 final String     methodName,
                                 final String     procId) {

        Date                now             = new Date();
        float               secondsElapsed  = (now.getTime() - startTime.getTime()) / 1000f;
        DecimalFormat       df              = new DecimalFormat();
        String              procIdMsg       = procId;

        if ( procId == null ) {

            procIdMsg = "";
        }

        df.setMinimumFractionDigits(2);
        df.setMaximumFractionDigits(2);

        String string = "\n---------------------------- RbpsWS ----------------------------\n"
                + "--- Start Time: " + startTime + "\n"
                + "--- End Time:   " + now + "\n"
                + "--- Completed in [" + df.format(secondsElapsed) + "] seconds.\n"
                + "--- " + className + "." + methodName + " : " + procIdMsg + "\n"
                + "--------------------------- End RbpsWS -------------------------------\n";

        CommonUtils.log( logger, string );
    }


    private static final boolean safeToStartProcessing() {

        //
        //      If the value is false, then set it to true.
        //      It is only false if we aren't yet processing.
        //
        boolean safe = processing.compareAndSet( false, true );

        CommonUtils.log( logger, "Safe to start processing: " + safe );

        return safe;
    }


    private static final void markSafeToStartProcessing( final boolean currentlyProcessing) {

        //
        //      By setting this to false, other threads
        //      will know that we've finished processing
        //      and they can start.
        //
        if ( ! currentlyProcessing ) {

            return;
        }

        CommonUtils.log( logger, "Finishing processing, unlocking" );
        processing.set( false );
    }


    private String writeCurrentProcessFile( final String currentProcess, final String totalProcessCount) throws RbpsWsException{
    	
    	String fileWriteStatus = "";
    	
    	String fileName = currentProcessFilePath + currentProcess;
    	CommonUtils.log( logger, "Current Process File name " + fileName );
    	
		try {
			
			File file = new File(fileName);
			
			if ( file.exists() ) {
				
				fileWriteStatus = String.format( "Error: Current Process File name %s exists. Exiting from current process %s", fileName, currentProcess );
				CommonUtils.log( logger, fileWriteStatus );
				return fileWriteStatus;
			}
			
	    	CommonUtils.log( logger, "About to write Current Process File" );
			FileWriter writer = new FileWriter( fileName, true );
			
			String datText = String.format("Process %s of %s", currentProcess, totalProcessCount );
			writer.write(datText);
			writer.close();
			
			CommonUtils.log( logger, "Created Current Process File" );
			fileWriteStatus = "Created Current Process File";
			
		} catch (Exception ex) {
			RbpsFaultBean faultInfo = new RbpsFaultBean();
            faultInfo.setMessage("");
            fileWriteStatus = String.format( "Error writing to current Process file %s. Exiting from current process %s", fileName, currentProcess );
			//throw new RbpsWsException( String.format("Error writing to current Process file %s: ", fileName ), faultInfo, ex );
            CommonUtils.log( logger, fileWriteStatus );
            return fileWriteStatus;
        }
		
		return fileWriteStatus;
    }
    
    
    private void deleteCurrentProcessFile( final String currentProcess) throws RbpsWsException{
    	
    	String fileName = currentProcessFilePath + currentProcess;
    	
    	try{
    		 
    		File file = new File(fileName);
 
			if ( ! file.exists() ) {
				
				CommonUtils.log( logger, fileName + " does not exist for deletion." );
				return;
			}
			
    		if(file.delete()) {
    			
    			CommonUtils.log( logger, fileName + " is deleted." );
    		} 
    		else{
    			
    			CommonUtils.log( logger, fileName + "delete operation failed.");
    			return;
    		}
    	} catch(Exception ex ){
 
    		RbpsFaultBean faultInfo = new RbpsFaultBean();
            faultInfo.setMessage("");
            CommonUtils.log( logger,   String.format("Error deleting current Process file %s: ", fileName ) );
            return;
    	}
    	
    	return;
    }


    @WebMethod(exclude = true)
    public void setRbpsServiceFacade(final RbpsServiceInterface rbpsService) {
        this.rbpsService = rbpsService;
    }

    
    @WebMethod(exclude = true)
    public void setCurrentProcessFilePath(final String currentProcessFilePath) {
        this.currentProcessFilePath = currentProcessFilePath;
    }


    @WebMethod(exclude = true)
    public void setCheckerServicePopulator( final CheckerServicePopulator checkerServicePopulator ){
    	this.checkerServicePopulator = checkerServicePopulator;
    }
    

	/**
	 * @return the sbDefaultSet
	 */
	public static final boolean isSbDefaultSet() {
		return sbDefaultSet;
	}



	/**
	 * @param sbDefaultSet the sbDefaultSet to set
	 */
	public static final void setSbDefaultSet(boolean sbDefault) {
		sbDefaultSet = sbDefault;
	}

}
