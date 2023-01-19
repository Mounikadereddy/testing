/*
 * RbpsServiceFacade.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.services.impl;

import static gov.va.vba.rbps.coreframework.util.RbpsConstants.CLAIM_STATUS_PROCESS_STARTED;
import gov.va.vba.framework.logging.Logger;
import gov.va.vba.framework.services.mail.ContentType;
import gov.va.vba.rbps.claimprocessor.RbpsClaimProcessorFactory;
import gov.va.vba.rbps.claimprocessor.util.ClaimProcessorHelper;
import gov.va.vba.rbps.claimprocessor.util.ConnectionFactory;
import gov.va.vba.rbps.claimvalidator.GenericUserInformationValidatorImpl;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.exception.RbpsClaimDataException;
import gov.va.vba.rbps.coreframework.exception.RbpsRuntimeException;
import gov.va.vba.rbps.coreframework.exception.RbpsServiceException;
import gov.va.vba.rbps.coreframework.util.CommonUtils;
import gov.va.vba.rbps.coreframework.util.EmailSender;
import gov.va.vba.rbps.coreframework.util.EmailUtil;
import gov.va.vba.rbps.coreframework.util.IndentedXomToString;
import gov.va.vba.rbps.coreframework.util.RbpsConstants;
import gov.va.vba.rbps.services.interfaces.RbpsServiceInterface;
import gov.va.vba.rbps.services.populators.ClaimIdPopulator;
import gov.va.vba.rbps.services.populators.RbpsRepositoryPopulator;
import gov.va.vba.rbps.services.ws.client.handler.vonapp.FindBenefitClaimWSHandler;
import gov.va.vba.rbps.services.ws.client.handler.vonapp.UserInformationWSHandler;
import gov.va.vba.rbps.services.ws.client.handler.vonapp.UserInformationClaimWSHandler;
import gov.va.vba.rbps.services.ws.client.mapping.vonapp.claimlabel.FindBenefitClaimResponse;
import gov.va.vba.rbps.services.ws.client.mapping.vonapp.userinformation.FindByDataSuppliedResponse;
import gov.va.vba.rbps.services.ws.client.mapping.vonapp.userinformation.GetClaimInfoResponse;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Date;

import javax.naming.NamingException;

import org.springframework.ws.soap.client.SoapFaultClientException;

import gov.va.vba.rbps.coreframework.dao.MRPClaimsHoldForFDASDao;
import gov.va.vba.rbps.coreframework.dao.MRPClaimsHoldForFDASDaoImpl;
import gov.va.vba.rbps.coreframework.dao.VetsNetSystemServiceDao;
import gov.va.vba.rbps.coreframework.dao.VetsNetSystemServiceDaoImpl;
import gov.va.vba.rbps.coreframework.dao.exception.NoResultsException;

/**
 * Loops while getting, validating, and processing claims. Will be called
 * periodically by the ops group to get claims from a database queue.
 */
public class RbpsServiceFacade implements RbpsServiceInterface {

	private static Logger logger = Logger.getLogger(RbpsServiceFacade.class);

	private RbpsClaimProcessorFactory claimProcessorFactory;

	// Spring Injection
	private UserInformationWSHandler userInformationWSHandler;
	private UserInformationClaimWSHandler userInformationClaimWSHandler;
	private GenericUserInformationValidatorImpl genericUserInformationValidator;
	private RbpsRepositoryPopulator rbpsRepositoryPopulator;

	private ClaimProcessorHelper claimProcessorHelper;
//	private VdcClaimFilter vdcClaimFilter;

	private FindBenefitClaimWSHandler findBenefitClaimWSHandler;
	private ClaimIdPopulator claimIdPopulator;

	private long startTime;
	private long endTime;
	private ExceptionHandler exceptionHandler;

	private int numberOfClaimsPerBatch;
	private static final int						MINUS_ONE = -1;
	
	private String from;
	private String to;
	private String fromName;
	private String cc;
	private String bcc;
	private String body;
	private String subject;
	
	

	RbpsServiceFacade() {
		startTime = 0L;
		endTime = 0L;
	}

	/**
	 * This method will:
	 * 
	 * 1. Get a Claim object from VONAPP, using the FIFO based UserInformation
	 * web service
	 * 
	 * 2. Validate the Claim data
	 * 
	 * 3. Either forward the Claim to a Claim Processor or reject the Claim and
	 * log a note to MAP-D
	 * 
	 * 4. Trigger letter generation process if needed 5. Get the next Claim data
	 * from the Queue and start over
	 */
	@Override
    public void executeAll(final String currentProcess,
            final String totalProcesses, RbpsRepository repo)
            throws RbpsServiceException {
        
        
        /**
         * We will be getting data from the DB via the UserInformation web
         * service. We will be getting only one Claim object (a set of data
         * related to one Claim) at a time. This Claim should be entirely
         * processed and then RBPS updates on the DB this Claim's status and
         * label to remove it from the FIFO Queue.
         *
         * We will be looping this way until there is no more Claim on the Queue
         * or numberOfClaimsPerBatch has been processed. Exit condition will be
         * UserInformation web service returning no Claim or claimCount exceeds
         * numberOfClaimsPerBatch.
         */
        
        int claimCount = 0;
        boolean rbpsOff = true;
        startUp(repo);



       CommonUtils.log(logger, "In executeAll currentProcess: " + currentProcess);
        // change for SR 1205913
        //RBPS process modification to “hold” MRP claims for DFAS Processing time
        claimCount = findFDASHoldtime(currentProcess);



       //while there are still claims in the queue and RBPS is not OFF
        while ( claimCount >= 0 && !(rbpsOff = isRbpsOn().equalsIgnoreCase("N"))) {
                
            claimCount = getAndProcessClaim(currentProcess, totalProcesses, repo, claimCount );
            
        }
        
        if(rbpsOff){
             CommonUtils.log(logger, "In executeAll isRpsOn return with valuse 'N'. Fix that before starting rbps. " );
             return;
        }
    }
	/**
	 * This method is called from rbps-execute.jsp to process claims one by one
	 * clicking on 'Submit' button by the user.
	 * 
	 * @throws RbpsServiceException
	 * @return Journal information
	 */
	@Override
	public String execute(RbpsRepository repo) throws RbpsServiceException {
		
		int claimCount = findFDASHoldtime("1");
		startUp(repo);
		if (claimCount >= 0) {
			getAndProcessClaim(null, null, repo, 0);
		}
		return repo.getJournalStr();
		
	}

	public String execute(final String procId, RbpsRepository repo)
			throws RbpsServiceException {

		startUp(repo);
		getAndProcessSingleClaim(procId,repo);

		return repo.getJournalStr();
	}

	private void startUp(RbpsRepository repo) {
    	
    	CommonUtils.log( logger,  "*** RBPS starting..." );
        claimIdPopulator = new ClaimIdPopulator();
//        vdcClaimFilter = new VdcClaimFilter();
        
        
       repo.clearJournalStr();
    }

	private int getAndProcessClaim(final String currentProcess,
			final String totalProcesses, RbpsRepository repo, int claimCount) {

		startTime = System.currentTimeMillis();
		String exceptionInfo = "";

		claimCount++;

		if (claimCount > numberOfClaimsPerBatch) {
			repo.setVeteran(null);
			// decrement claimcount hidden in next line
			CommonUtils.log(logger, String.format("Processed %d claims for process %s.. exiting RBPS",	--claimCount, currentProcess));
			return MINUS_ONE;
		}

		CommonUtils.log(logger, String.format("Claim Count %d for process %s",	claimCount, currentProcess));

		try {
			
			String threadName = Thread.currentThread().getName();
			
			if ( threadName.contains("Claim") ) {
				
				int index  = threadName.indexOf( "Claim" );
				threadName = threadName.substring(0, index).trim();
			}
			Thread.currentThread().setName(threadName + " Claim Count: " + claimCount );
			// Get an eClaim Object from the VONAPP eClaims Queue
			FindByDataSuppliedResponse newVonnappClaim = getNewClaim(null, currentProcess, totalProcesses, repo);

			// Do we have an eClaim object?
			if (isUserInformationNull(newVonnappClaim)) {
				repo.setVeteran(null);
				CommonUtils.log(logger,"Reached end of Queue, no more eClaim objects");
				//RTC 976601 call send email before quit
				emailIfAnyError(currentProcess);
				return MINUS_ONE;
			}
			
			processClaim(newVonnappClaim, repo);
		} catch (SoapFaultClientException ex) {

			CommonUtils.log(logger, "SoapFaultClientException in getAndProcessClaim, message =" + ex.toString());
			CommonUtils.log(logger, "SoapFaultClientException in getAndProcessClaim, stack =" + ex.getStackTrace());

			exceptionHandler.handleSoapFaultException(ex, repo);
		} catch (Throwable ex) {
		 
	    
			CommonUtils.log(logger, "Exception in getAndProcessClaim, message =" + ex.toString());
			CommonUtils.log(logger, "Exception in getAndProcessClaim, stack =" + ex.getStackTrace());
	    				
			exceptionInfo = exceptionHandler.handleException(ex, repo);
		} finally {

			addJournalEntry(exceptionInfo, currentProcess, repo);
			cleanUp(repo);
		}

		return claimCount;
	}

	public FindByDataSuppliedResponse getNewClaim(final String procId,
			final String currentProcess, final String totalProcesses, RbpsRepository repo) {

		try {
			//userInformationWSHandler.setCommonUtils(utils);
			return userInformationWSHandler.getFindByDataSuppliedResponse(
					repo, procId, currentProcess,
					totalProcesses);
		} catch (Throwable ex) {

			//
			// Returning null so that it will exit the infinite loop we'd get
			// by calling user info for the same claim over and over. Null will
			// trigger the "isUserInformationNull" to return true, which will
			// cause
			// getAndProcess to return 0, which will cause the loop to exit.
			//
			new EmailUtil().sendEmail(fromName, from, to, null, null, subject, body, ContentType.TEXT, null, false);
			CommonUtils.log(logger,
					"Unable to get user information from web service", ex, false);
			return null;
		}
	}

	private int getAndProcessSingleClaim(final String procId, RbpsRepository repo) {

		String exceptionInfo = "";

		try {
			// Get an eClaim Object from the VONAPP eClaims Queue
			FindByDataSuppliedResponse newVonnappClaim = getNewClaim(procId,
					null, null, repo);

			// Do we have an eClaim object?
			if (isUserInformationNull(newVonnappClaim)) {
				repo.setVeteran(null);
				CommonUtils.log(logger,	"Reached end of Queue, no more eClaim objects");
				//RTC 976601 send email when web service is down
				emailIfAnyError("0");
				return 0;
			}

			processClaim(newVonnappClaim, repo);
		} catch (SoapFaultClientException ex) {

			CommonUtils.log(logger, "SoapFaultClientException in getAndProcessSingleClaim, message =" + ex.toString());
			CommonUtils.log(logger, "SoapFaultClientException in getAndProcessSingleClaim, stack =" + ex.getStackTrace());

			exceptionHandler.handleSoapFaultException(ex, repo);
		} catch (Throwable ex) {

			CommonUtils.log(logger, "Throwable Exception in getAndProcessSingleClaim, message =" + ex.toString());
			CommonUtils.log(logger, "Throwable Exception in getAndProcessSingleClaim, stack =" + ex.getStackTrace());

			
			exceptionInfo = exceptionHandler.handleException(ex, repo);
		} finally {

			addJournalEntry(exceptionInfo, "", repo);
			cleanUp(repo);
		}

		return 1;
	}

	private void processClaim(final FindByDataSuppliedResponse newVonnappClaim, RbpsRepository repo) throws RbpsClaimDataException {

		try {
			rbpsRepositoryPopulator.populateFromVonapp(newVonnappClaim.getReturn().getUserInformation().get(0), repo);
		} catch (Exception e) {
			//CCR 1890 - If there are some special characters in the FindByDataSuppliedResponse, the unmarshalling still happens but only a
			// part of the data is retrieved as the XML is incomplete. Before re-throwing the exception, we want to send an email.
			new EmailUtil().sendEmail(fromName, from, to, cc, bcc, subject, body, ContentType.TEXT, null, false);
			
			throw new RbpsClaimDataException(e);
		}
		
		String threadName = Thread.currentThread().getName();

		if ( threadName.contains("Proc") ) {
			
			int index  = threadName.indexOf( "Proc" );
			threadName = threadName.substring(0, index).trim();
		}
		Thread.currentThread().setName(threadName + " Proc Id: " + repo.getVnpProcId());
		//ccr 2074.
		checkPendingClaim(repo);
		grabCorporateClaimId(repo);

		// if ( filterVdc() ) {
		//
		// repository.setClaimProcessingState( "Rejecting VDC claim" );
		// repository.addValidationMessage(
		// "Auto Dependency Processing Reject Reason: This is a VDC/Ebenefits claim. RBPS cannot process this claim. Please Review."
		// );
		// claimProcessorHelper.sendClaimForManualProcessing();
		// return;
		// }

		/**
		 * This is an initial data validation: claim data only. The
		 * <code>genericUserInformationValidator.validate</code> method will run
		 * generic common data validation and decide whether Claim process will
		 * go through or log exception
		 * 
		 * Validation will be an on-going task throughout the a Claim process
		 * life cycle until RuleApp is called Additional validation will be done
		 * from Claim Processor
		 */
		genericUserInformationValidator.validate(repo);

		claimProcessorHelper.updateClaimStatus(CLAIM_STATUS_PROCESS_STARTED,
				repo);

		// Delegate the VONAPP eClaim processing to a Claim Processor
		claimProcessorFactory.getClaimProcessor(
				repo.getVeteran().getClaim().getEndProductCode()).processClaim(repo);

		CommonUtils.log(logger, "*** RBPS sucessfully processed ClaimID ["
				+ repo.getVeteran().getClaim().getClaimId()
				+ "] with ProcID [" + repo.getVnpProcId()
				+ "]");
	}

	// private boolean filterVdc() {
	//
	// return vdcClaimFilter.filter( repository );
	// }
// for ccr 2074. moved the logic to check pending claim to coracle.
	private void checkPendingClaim(RbpsRepository repo) {

		try {
				GetClaimInfoResponse response = userInformationClaimWSHandler.getCalaimInfoResponse(repo );
				claimIdPopulator.checkForNon130PendingClaims(response.getReturn(), repo);
			} catch (Throwable ex) {

			RuntimeException newError = new RbpsRuntimeException(
					"Rbps Failed to find the corporate benefit claim.", ex);
			repo.addValidationMessage(newError.getMessage());

			throw newError;
		}
	}
	/**
	 * Get the corporate claim id - VDC sends the vonapp claim id, so we have to
	 * convert.
	 */
	private void grabCorporateClaimId(RbpsRepository repo) {

		try {

			final FindBenefitClaimResponse response = findBenefitClaimWSHandler.findBenefitClaim( repo );
			claimIdPopulator.populateClaimId(response.getReturn(), repo);
		} catch (Throwable ex) {

			RuntimeException newError = new RbpsRuntimeException(
					"Rbps Failed to find the corporate benefit claim.", ex);

			repo.addValidationMessage(newError.getMessage());

			throw newError;
		}
	}

	private static final boolean isUserInformationNull(
			final FindByDataSuppliedResponse newVonnappClaim) {

		if (newVonnappClaim == null || newVonnappClaim.getReturn() == null
				|| newVonnappClaim.getReturn().getUserInformation() == null
				|| newVonnappClaim.getReturn().getUserInformation().size() == 0) {

			return true;
		}

		return false;
	}

	private static final void cleanUp(RbpsRepository repo) {

		repo.destroy();
	}

	private void addJournalEntry(final String exceptionInfo,
			final String currentProcess,RbpsRepository repo) {

		try {

			StringBuilder stringBuilder = new StringBuilder();
			
			if ( repo.getVeteran() != null ) {
				
				stringBuilder
						.append(RbpsConstants.BAR_FORMAT + "\n\nProc ID: ")
						.append(repo.getVnpProcId())
						.append("\nProcessing Status >: ")
						.append(repo.getClaimProcessingState())
						.append("<\nVeteran information :\n")
						.append(new IndentedXomToString().toString(repo.getVeteran(), 0))
						.append(exceptionInfo);
			} else {
				stringBuilder
				.append(RbpsConstants.BAR_FORMAT + "\n\nProc ID: ")
				.append(repo.getVnpProcId())
				.append("\nProcessing Status >: ")
				.append(repo.getClaimProcessingState())
				.append("<\nNo Veteran information :\n")
				.append(exceptionInfo);
				
			}

			displayJournal(stringBuilder.toString(), currentProcess, repo);
		} catch (Throwable ex) {
			CommonUtils.log(logger, "*** RBPS: Journal Entry unable to be created: "	+ ex.getMessage());
			cleanUp(repo);
		}
	}

	/**
	 * display the Journal at the end of each RBPS run
	 */
	private void displayJournal(final String journalEntry,
			final String currentProcess, RbpsRepository repo) {

		/**
		 * Implement here a journal that will summarize each RBPS run metrics. I
		 * am thinking of:
		 * 
		 * --- RBPS run log/summary/Journal? --- start time: end time: duration:
		 * Total number of Claims processed: [20] Number of Claims successfully
		 * processed: Number of Claims etc END RBPS journal
		 */
		endTime = System.currentTimeMillis();
		float secondsElapsed = (endTime - startTime) / 1000f;
		DecimalFormat df = new DecimalFormat();
		df.setMinimumFractionDigits(2);
		df.setMaximumFractionDigits(2);

		StringBuilder stringBuilder = new StringBuilder(
				"\n---------------------------- Begin RBPS Journal ----------------------------\n")
				.append("currentProcess : " + currentProcess)
				.append("--- ProcId: " + repo.getVnpProcId()
						+ "\n")
				.append("--- Start Time: " + new Date(startTime) + "\n")
				.append("--- End Time:   " + new Date(endTime) + "\n")
				.append("--- Completed in [" + df.format(secondsElapsed)
						+ "] seconds.\n")
				.append(journalEntry)
				.append("\n--------------------------- End RBPS Journal -------------------------------\n");

		CommonUtils.log(logger, stringBuilder.toString());
	}

	@Override
	public void executeAll() throws RbpsServiceException {

	}

	/**
	 * Removed EK the Spring setter methods
	 * 
	 * public void setRepository(final RbpsRepository repository) {
	 * 
	 * this.repository = repository; }
	 */

	public void setUserInformationWSHandler(
			final UserInformationWSHandler userInformationWSHandler) {

		this.userInformationWSHandler = userInformationWSHandler;
	}
	public void setUserInformationClaimWSHandler(
			final UserInformationClaimWSHandler userInformationClaimWSHandler) {

		this.userInformationClaimWSHandler = userInformationClaimWSHandler;
	}
	public void setClaimProcessorFactory(
			final RbpsClaimProcessorFactory claimProcessorFactory) {

		this.claimProcessorFactory = claimProcessorFactory;
	}

	public void setRbpsRepositoryPopulator(
			final RbpsRepositoryPopulator rbpsRepositoryPopulator) {

		this.rbpsRepositoryPopulator = rbpsRepositoryPopulator;
	}

	public void setGenericUserInformationValidator(
			final GenericUserInformationValidatorImpl genericUserInformationValidator) {

		this.genericUserInformationValidator = genericUserInformationValidator;
	}

	public void setClaimProcessorHelper(
			final ClaimProcessorHelper claimProcessorHelper) {

		this.claimProcessorHelper = claimProcessorHelper;
	}

	public void setExceptionHandler(final ExceptionHandler exceptionHandler) {

		this.exceptionHandler = exceptionHandler;
	}
	

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	 public String getFromName() {
		return fromName;
	}


	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public String getCc() {
		return cc;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

	public String getBcc() {
		return bcc;
	}

	public void setBcc(String bcc) {
		this.bcc = bcc;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setFindBenefitClaimWSHandler( final FindBenefitClaimWSHandler
			 findBenefitClaimWSHandler ) {
			
			 this.findBenefitClaimWSHandler = findBenefitClaimWSHandler;
	 }
	 
	// public void setVdcClaimFilter( final VdcClaimFilter vdcClaimFilter ) {
	//
	// this.vdcClaimFilter = vdcClaimFilter;
	// }
	//

	//
	// public void setClaimIdPopulator( final ClaimIdPopulator claimIdPopulator
	// ) {
	//
	// this.claimIdPopulator = claimIdPopulator;
	// }

	public void setNumberOfClaimsPerBatch(final int numberOfClaimsPerBatch) {

		this.numberOfClaimsPerBatch = numberOfClaimsPerBatch;
	}

	// RTC 976601
	/*
	 * send email
	 */
	private void emailIfAnyError(String processNum) {
		EmailSender es = new EmailSender();
		if (es.getErrCount() > 0) {
			try {
				es.rbpsEmailSender(processNum);
			} catch (Exception e) {
				CommonUtils.log(logger, "Error: Exception occured during sending email: " + e.getMessage());
			}
		}

	}
	
	/*
	 * 
	 */
	private int findFDASHoldtime(String currentProcess) {
		Connection conn = null;
		int cutOff = 0;
        try {
        	//task 009232 use new datasource jdbc/wbrbps/CorpDB
            //conn = ConnectionFactory.getConnection("jdbc/vbms/CorpDB");
            conn = ConnectionFactory.getConnection("jdbc/wbrbps/CorpDB");
            MRPClaimsHoldForFDASDao mrpClaimHoldForFDAS = new MRPClaimsHoldForFDASDaoImpl();
            cutOff = mrpClaimHoldForFDAS.findFDASForHoldtime(conn);
        } catch (NoResultsException e) {
			CommonUtils.log(logger, "Error: Exception occured to get cutoff time: " + e.getMessage()); 
			//when modify the input parameters, please make sure you know what it would impact
			EmailSender.addErrorMsg("Reporting Unexpected error in RBPS for cutoff: rbps.RbpsServiceFacade has an exception ", 
									  e.getMessage());
        } catch(Exception e) {
			CommonUtils.log(logger, "Error: Exception occured to get cutoff time: " + e.getMessage()); 
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) { /* ignored */}
            }
        }
        
        //to reduce the duplicates, send email only for process number is 4
        if (currentProcess.trim().equals("4")) {
        	emailIfAnyError(currentProcess);	
        }
        if ( cutOff == -1) {
			CommonUtils.log(logger, "Process " + currentProcess + " stopped to process RBPS claim due to cut off time"); 
        }
        
		return cutOff;
	}
	public String isRbpsOn() throws RbpsServiceException {
		
		
		String isRbpsOn="N";
		Connection conn = null;
		
		 try {
	        	conn = ConnectionFactory.getConnection("jdbc/wbrbps/CorpDB");
			// conn = ConnectionFactory.getConnection("jdbc/wbvnet/CorpDB");
	            VetsNetSystemServiceDao vetsNetSystemServiceDao = new VetsNetSystemServiceDaoImpl(conn);
	            isRbpsOn=vetsNetSystemServiceDao.isRbpsOn();
	           
	        } catch(Exception e) {
				CommonUtils.log(logger, "Error: Exception occured to get rbpsOn: " + e.getMessage()); 
	        } finally {
	            if (conn != null) {
	                try {
	                    conn.close();
	                } catch (SQLException e) { /* ignored */}
	            }
	        }
	        
	      //System.out.println("test.............................................................................isRbpsOn"+isRbpsOn);
		return isRbpsOn;
	}
	
    public String tunOnOffRbps(String onOff) throws RbpsServiceException {
		
		
		String status=null;
		Connection conn = null;
		
		 try {
	        	conn = ConnectionFactory.getConnection("jdbc/wbrbps/CorpDB");
	        	//conn = ConnectionFactory.getConnection("jdbc/wbvnet/CorpDB");
	            VetsNetSystemServiceDao vetsNetSystemServiceDao = new VetsNetSystemServiceDaoImpl(conn);
	            status=vetsNetSystemServiceDao.turnOnOffRbps(onOff);
	        	//EmailSender.addInfoMsg(status);
	        	emailIfAnyError("0");
	        } catch(Exception e) {
	        	System.out.println("from java tunOnOffRbps.Exception.........: " + e.getMessage());
	        	CommonUtils.log(logger, "Error: Exception occured to get rbpsOn: " + e.getMessage()); 
	        } finally {
	        	
	            if (conn != null) {
	                try {
	                    conn.close();
	                } catch (SQLException e) { /* ignored */}
	            }
	        }
	     //System.out.println("from java tunOnOffRbps.............................................................................tunOffRbps"+status);
		return status;
	}

}
