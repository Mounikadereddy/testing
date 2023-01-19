/*
 * GeneratePdf.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.lettergeneration;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.collections.MultiHashMap;
import org.apache.commons.collections.MultiMap;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.springframework.util.CollectionUtils;

import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.dto.DependentVO;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.util.CommonUtils;
import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.coreframework.util.NullSafeGetter;
import gov.va.vba.rbps.coreframework.util.RbpsUtil;
import gov.va.vba.rbps.coreframework.util.SimpleDateUtils;
import gov.va.vba.rbps.lettergeneration.docgen.genstore.service.RbpsDocGenGenstoreService;
//import gov.va.vba.rbps.lettergeneration.docgen.service.RbpsDocGenService;
import gov.va.vba.rbps.services.populators.AwardWebServicePopulator;
import gov.va.vba.rbps.services.ws.client.handler.awards.AwardWebServiceWSHandler;
import gov.va.vba.rbps.services.ws.client.handler.awards.ReadCurrentAndProposedAwardWSHandler;
import gov.va.vba.rbps.services.ws.client.mapping.awards.amend.AmendAwardServiceReturnVO;
import gov.va.vba.rbps.services.ws.client.mapping.awards.amend.AwardLineSummaryVO;
import gov.va.vba.rbps.services.ws.client.mapping.awards.amend.AwardReasonSeqNbrVO;
import gov.va.vba.rbps.services.ws.client.mapping.awards.amend.DependencyDecisionResultVO;
import gov.va.vba.rbps.services.ws.client.mapping.awards.amend.ProcessAwardDependentResponse;
import gov.va.vba.rbps.services.ws.client.mapping.awards.awardWebService.FindStationOfJurisdictionResponse;


/**
 *      Generate the pdf and csv files for a claim/veteran.
 *      The file names will contain the claim # and an abbreviated
 *      form of the date: yyyyMMdd.
 */
public class GeneratePdf {


    private static final int DENIAL_SEQUENCE_NUMBER = -1;

    private static Logger logger = Logger.getLogger(GeneratePdf.class);
    private LogUtils        logUtils                    = new LogUtils( logger, true );
    
    private String          pathToPdfOutputDirectory    = System.getProperty( "java.io.tmpdir" );
    private RbpsApplicationDetails 						  rbpsApplicationDetails;
    private ReadCurrentAndProposedAwardWSHandler		  readCurrentAndProposedAwardWSHandler;
    private AwardWebServiceWSHandler                      awardWebServiceWSHandler;
    private AwardWebServicePopulator					  awardWebServicePopulator;
	private RbpsDocGenGenstoreService 					  rbpsDocGenGenstoreService;

    public GeneratePdf(){}
    
    


    /**
     *      Generates a pdf notification letter of the appropriate type and a corresponding
     *      csv file.  The files are generated in the directory specified in the
     *      rbps.properties files.  If no destination directory is specified, the
     *      files will be generated in the directory specified by the java.io.tmpdir
     *      system property.
     */
    public void generateLetterAndCsv( final RbpsRepository                  		repository,
                                      final ProcessAwardDependentResponse   		awardResponse ) {
		GenericLetterGeneration genericLetterGeneration     = new GenericLetterGeneration(rbpsDocGenGenstoreService);
		int						countForWhatWeDecided	    = 0;
		List<AwardSummary>      awardSummaryList            = null;

		if ( awardResponse != null ) {

			AmendAwardServiceReturnVO amendAwardReturnVo = awardResponse.getReturn();
			BigDecimal netAmount = amendAwardReturnVo.getAwardSummary().getAwardEventSummary().getTotalNetAmountDue();
			repository.setTotalNetAmount(netAmount);

			countForWhatWeDecided	    = getCountForWhatWeDecided( repository, awardResponse  );
			logger.debug("countForWhatWeDecided: " + countForWhatWeDecided);

			repository.setCountForWhatWeDecided( countForWhatWeDecided );

			logger.debug("Veteran's File Number: " + repository.getVeteran().getFileNumber());

			awardSummaryList            = responseToSummary( repository, awardResponse );
			
			setToNoMilitaryPayIfAllAmountWithheldAreZeores( repository, awardSummaryList );
		}

		boolean	isInternational		= ( repository.getVeteran().getMailingAddress().getCountry().equalsIgnoreCase( "USA") ) ? false : true ;

		if ( repository.getVeteran().hasPOA() && !isInternational) {
			FindStationOfJurisdictionResponse response = awardWebServiceWSHandler.findStationOfJurisdiction(repository);
			awardWebServicePopulator.populateStationOfJurisdiction(response, repository);
		}
		
		//debug for PDF files 
        logUtils.log( "PDF pathToPdfOutputDirectory : " + pathToPdfOutputDirectory, repository );
        
		genericLetterGeneration.generateLetterAndCsv( repository,
														awardSummaryList,
														repository.getPoaOrganizationName(),
														pathToPdfOutputDirectory,
														rbpsApplicationDetails );
    }


    /**
     *      Populates AwardSummary objects from ProcessAwardDependentResponse.
     */
    public List<AwardSummary> responseToSummary( final RbpsRepository                   repository,
                                                 final ProcessAwardDependentResponse    awardResponse) {

    	if ( awardResponse == null ) {
    		
    		return null;
    	}
    		
        List<AwardSummary>              awardSummaryList        = new ArrayList<AwardSummary>();
        MultiMap                        dependentMap            = createDependentMap( awardResponse, repository );
        List<AwardLineSummaryVO>        lineSummaries           = getLineSummaries( awardResponse );
        Date                            firstChangeDate         = getFirstChangeDate( awardResponse );
        
        /** 647228/298341, Completed Claims with Reject Claim Labels (RTC  298341) - ESCP 545 
         * Getting AwardAuthorizedDate to populate in the message 
         * Ã¯Â¿Â½RBPS letter failed to generate, please create letter for RBPS award authorized xx/xx/xxxxÃ¯Â¿Â½
         **/ 
        getAwardAuthorizedDate(awardResponse, repository);
        List<DependencyDecisionResultVO> dependents=getDependentsList(awardResponse);
        LinkedHashSet<DependentVO> dependentsPriorToFirstChangeDate = new LinkedHashSet<DependentVO>();
        try {
	        addDenialSummary( repository, lineSummaries );
	
	        for ( AwardLineSummaryVO summary : lineSummaries ) {
	
	        	// removing check for first changeDate
	        	
	            if ( isPriorDecision( firstChangeDate, summary ) ) {
	
	                logUtils.log( "Skipping summary: " + summary.getAwardLineReasons().get(0).getReasonSequenceNumber(), repository );
	                List<DependencyDecisionResultVO> dependentsList = getDependentFromMap( dependentMap, summary );
	                for (DependencyDecisionResultVO dependencyDecisionResultVO : dependentsList) {
						dependentsPriorToFirstChangeDate.add(new DependentVO(dependencyDecisionResultVO.getPersonID(), dependencyDecisionResultVO.getFullName()));
					}
	                continue;
	            }
	
	            List<AwardSummary> summaries = createAwardSummary( dependentMap, summary );
	           /* try {
					//updateDependenentActiveStatusOnAwardReasons(summaries,dependents);
				} catch (Throwable ex) {
						// TODO Auto-generated catch block
						logUtils.log("Error in calculating number of dependent",ex,repository);
					}*/
				
	            awardSummaryList.addAll( summaries );
	        }
	        
	        repository.setDependentsPriorToFirstChangeDate(dependentsPriorToFirstChangeDate);
	        addToSummaryListForNoMatchingAwardLineReason(dependentMap, lineSummaries, awardSummaryList);
	        try {
				updateDependenentActiveStatusOnAwardReasons(awardSummaryList,dependents);
			} catch (Throwable ex) {
					// TODO Auto-generated catch block
					logUtils.log("Error in calculating number of dependent",ex,repository);
				}
        }
        finally {
        		dependentMap = null;
        		lineSummaries = null;
        }
        
        return awardSummaryList;
    }



    private static final void addDenialSummary( final RbpsRepository                 repository,
                                   				final List<AwardLineSummaryVO>       summaryList ) {

        AwardLineSummaryVO      denialSummary   = new AwardLineSummaryVO();
        AwardReasonSeqNbrVO     denialReason    = new AwardReasonSeqNbrVO();

        denialSummary.setEffectiveDate( RbpsUtil.dayToXMLGregorianCalendar( repository.getVeteran().getClaim().getReceivedDate() ) );
        denialSummary.getAwardLineReasons().add( denialReason );
        denialReason.setReasonSequenceNumber( DENIAL_SEQUENCE_NUMBER );
        denialSummary.setGrossAmount( new BigDecimal( -1 ) );

        summaryList.add( denialSummary );
    }


    private static final List<AwardSummary> createAwardSummary( final MultiMap  				dependentMap,
                                                   				final AwardLineSummaryVO       	summary ) {

        AwardSummaryBuilder                 builder         = new AwardSummaryBuilder();
        List<DependencyDecisionResultVO>    dependents      = getDependentFromMap( dependentMap, summary );
        List<AwardSummary>                  awardSummaries  = builder.buildSummaryList( summary, dependents );

        return awardSummaries;
    }



    
    private static final List<DependencyDecisionResultVO> getDependentFromMap( final Map<Integer, DependencyDecisionResultVO>    dependentMap,
                                                                  final AwardLineSummaryVO                          summary ) {

        if ( summary == null ) {

            return new ArrayList<DependencyDecisionResultVO>();
        }

        List<DependencyDecisionResultVO>    foundDependents = new ArrayList<DependencyDecisionResultVO>();

        for ( AwardReasonSeqNbrVO reason : summary.getAwardLineReasons() ) {

            List<DependencyDecisionResultVO>    dependents = (List<DependencyDecisionResultVO>) dependentMap.get( reason.getReasonSequenceNumber() );

            if ( CollectionUtils.isEmpty( dependents ) ) { 
            	
            	continue;
            }
            
            addToFoundDependentsWithoutNoMatchingAwardLineReason(foundDependents, dependents);
        }
        
        return foundDependents;
    }


	private static void addToFoundDependentsWithoutNoMatchingAwardLineReason( List<DependencyDecisionResultVO> foundDependents, List<DependencyDecisionResultVO> dependents) {
		
		
		for ( DependencyDecisionResultVO	dependencyDecisionResultVO	:	dependents ) {	
			
			if ( ! dependencyDecisionResultVO.getGrantDenial().toUpperCase().contains( "No matching Award Line/Reason".toUpperCase() ) ) {
				
				foundDependents.add( dependencyDecisionResultVO );
			}
		}
	}

	
	private static final void addToSummaryListForNoMatchingAwardLineReason( final MultiMap dependentMap, 
																			final List<AwardLineSummaryVO> lineSummaries, 
																			List<AwardSummary> awardSummaryList ) {
		for ( Object	dependentObject	:	dependentMap.values() )	{
			
			DependencyDecisionResultVO dependencyDecisionResultVO	=	(DependencyDecisionResultVO) dependentObject;
			
			if ( dependencyDecisionResultVO.getGrantDenial().toUpperCase().contains( "No matching Award Line/Reason".toUpperCase() ) ) {
				
				createAwardDependentSummary( dependencyDecisionResultVO, lineSummaries, awardSummaryList );
				int index = dependencyDecisionResultVO.getGrantDenial().indexOf("-");
				dependencyDecisionResultVO.setGrantDenial( dependencyDecisionResultVO.getGrantDenial().substring(0, index).trim() );
			}
		}
	}


    private static void createAwardDependentSummary( final DependencyDecisionResultVO  dependent, 
    												 final List<AwardLineSummaryVO>     lineSummaries,
                                                   	 List<AwardSummary> awardSummaryList) {

        AwardSummaryBuilder                 builder         = new AwardSummaryBuilder();
        List<DependencyDecisionResultVO>    dependents      = new ArrayList<DependencyDecisionResultVO>();
        dependents.add(dependent);
        List<AwardReasonSeqNbrVO>			awardLineReasonList =  new ArrayList<AwardReasonSeqNbrVO>();
        boolean								foundMatchingReason = false;
        
        for ( AwardLineSummaryVO summary : lineSummaries ) {
        	
        	awardLineReasonList.clear();
        	
        	for ( AwardReasonSeqNbrVO awardLineReason	:	summary.getAwardLineReasons() ) {
        	
        		if ( dependent.getAlReasonSequenceNumber().intValue() == awardLineReason.getReasonSequenceNumber().intValue() ) {
        			
        			summary.setEffectiveDate( dependent.getAwardEffectiveDate() );
        			foundMatchingReason = true;
        		}
        		else {
        			
        			awardLineReasonList.add( awardLineReason );
        		}
        	}
        	
        	if ( foundMatchingReason ) {

        		summary.getAwardLineReasons().removeAll( awardLineReasonList );
        		awardLineReasonList.clear();
   				awardSummaryList.addAll( builder.buildSummaryList( summary, dependents ) );
   				break;
        	}
        }
    }
    
    
    public static final void setToNoMilitaryPayIfAllAmountWithheldAreZeores( final RbpsRepository repository, final List<AwardSummary> awardSummaryList ) {
    	
    	boolean allZeroes	=	true;
    	
    	for ( AwardSummary summary : awardSummaryList ) {
    		
    		if (  getAmountwithheld( summary.getAmountWithheld() ) > 0 ) {
    			
    			allZeroes = false;
    			// ccr 1775 -- has Amount with held. so setting it to true
    			repository.getVeteran().setHasMilitaryPay( true );
    			break;
    		}
    	}
    	
    	if ( allZeroes ) {
    		
    		repository.getVeteran().setHasMilitaryPay( false );
    	}
    }
    
    
    private static final double getAmountwithheld( BigDecimal amountwithheld ) {
    	
    	if ( amountwithheld == null ) {
    		
    		return 0;
    	}
    	return amountwithheld.doubleValue();
    	
    }
    
    public MultiMap createDependentMap( final ProcessAwardDependentResponse awardResponse, RbpsRepository repository) {

        MultiMap                                dependentMap    = new MultiHashMap();
        List<DependencyDecisionResultVO>        dependents      = getDependents( awardResponse );

        for ( DependencyDecisionResultVO dependent : dependents ) {

            assignDenialSequenceNumber( dependent, repository );

            dependentMap.put( dependent.getAlReasonSequenceNumber(), dependent );
        }

//        logUtils.log( "dependent map: " + dependentMap );
        return dependentMap;
    }


    private void assignDenialSequenceNumber( final DependencyDecisionResultVO dependent, RbpsRepository repository) {

        if ( dependent.getAlReasonSequenceNumber() != null ) {

            return;
        }

        dependent.setAlReasonSequenceNumber( DENIAL_SEQUENCE_NUMBER );
        logUtils.log( "Assigned denial sequence number to " + CommonUtils.stringBuilder( dependent ), repository );
    }


    @SuppressWarnings( "unchecked" )
    private List<DependencyDecisionResultVO> getDependents( final ProcessAwardDependentResponse awardResponse ) {

        //NullSafeGetter  grabber = new NullSafeGetter();
        Object          summary = NullSafeGetter.getAttribute( awardResponse, "return.awardSummary.dependencySummary" );

        List<DependencyDecisionResultVO>    dependents = (List<DependencyDecisionResultVO>) summary;

        if ( dependents == null ) {

            dependents = new ArrayList<DependencyDecisionResultVO>();
        }

        List<DependencyDecisionResultVO> sorted = sortDependents( dependents );
        sorted = new DependentsFilter().filter( sorted );

//        logUtils.log( "sorted dependent list: " + utils.stringBuilder( sorted ) );
        return sorted;
    }


    @SuppressWarnings( "unchecked" )
    private List<AwardLineSummaryVO> getLineSummaries( final ProcessAwardDependentResponse awardResponse ) {

        //NullSafeGetter  grabber = new NullSafeGetter();
        Object          summary = NullSafeGetter.getAttribute( awardResponse, "return.awardSummary.awardEventSummary.awardLineSummary" );

        if ( summary == null ) {

            summary = new ArrayList<AwardLineSummaryVO>();
        }

        return (List<AwardLineSummaryVO>) summary;
    }


    private List<DependencyDecisionResultVO> sortDependents( final List<DependencyDecisionResultVO> dependents ) {

        Collections.sort( dependents, new DependentsComparatorByDate() );

        return dependents;
    }


    private boolean isPriorDecision( final Date                         firstChangeDate,
                                     final AwardLineSummaryVO           summary ) {

        if ( firstChangeDate == null ) {

            return false;
        }

        if ( isDenial( summary ) /* && isOnClaim( summary ) */ ) {

            return false;
        }

        Date        eventDate   = summary.getEffectiveDate().toGregorianCalendar().getTime();

//        logUtils.log(   "\n\teventDate is         " + dateUtils.standardLogDayFormat( eventDate )
//                      + "\n\tfirstChangeDate is   " + dateUtils.standardLogDayFormat( firstChangeDate )
//                      + "\n\tisPrior:             " + eventDate.before( firstChangeDate ) );

        return eventDate.before( firstChangeDate );
    }


    private boolean isOnClaim( final AwardLineSummaryVO summary ) {

        // TODO Auto-generated method stub
        return false;
    }


    private static final boolean isDenial( final AwardLineSummaryVO summary ) {

        return summary.getAwardLineReasons().get(0).getReasonSequenceNumber() == -1;
    }


    private static final Date getFirstChangeDate( final ProcessAwardDependentResponse response ) {

        XMLGregorianCalendar    propertyValue       = (XMLGregorianCalendar) NullSafeGetter.getAttribute( response,
                                                                "return.awardSummary.awardEventSummary.firstChangedDate" );

        if ( propertyValue == null ) {

            return null;
        }

        Date                    firstChangeDate     = SimpleDateUtils.xmlGregorianCalendarToDay( propertyValue );

        return firstChangeDate;
    }


    /** 647228/298341, Completed Claims with Reject Claim Labels (RTC  298341) - ESCP 545 **/ 
    private static final Date getAwardAuthorizedDate( final ProcessAwardDependentResponse response, final RbpsRepository repository ) {

        XMLGregorianCalendar    propertyValue       = (XMLGregorianCalendar) NullSafeGetter.getAttribute( response,
                                                                "return.awardSummary.awardEventSummary.authorizedDate" );

        if ( propertyValue == null ) {

            return null;
        }

        Date                    authorizedDate     = SimpleDateUtils.xmlGregorianCalendarToDay( propertyValue );

        repository.setAwardAuthorizedDate(authorizedDate);
        
        return authorizedDate;
    }

    
    @SuppressWarnings("unchecked")
	private int getCountForWhatWeDecided(final RbpsRepository repository,
			final ProcessAwardDependentResponse awardResponse) {

	/*	List<AwardLineSummaryVO> awardSummaryList = getAwardLineSummariesPriorToday(awardResponse);

		AwardLineSummaryVO summaryVO = awardSummaryList.get(0);

	//	return calculteNumberOfDependents(summaryVO);
		*/
		int dependentCount=0;
		  try {
			  List<DependencyDecisionResultVO> dependents=getDependentsList(awardResponse);
      
			 dependentCount= countDependents(dependents);
		}  catch (Throwable ex) {
			// TODO Auto-generated catch block
			logUtils.log("Error in calculating number of dependent",ex,repository);
		}
		  return dependentCount;
	}

    @SuppressWarnings( "unchecked" )
    private List<DependencyDecisionResultVO> getDependentsList( final ProcessAwardDependentResponse awardResponse ) {

        //NullSafeGetter  grabber = new NullSafeGetter();
        Object          summary = NullSafeGetter.getAttribute( awardResponse, "return.awardSummary.dependencySummary" );

        List<DependencyDecisionResultVO>    dependents = (List<DependencyDecisionResultVO>) summary;

        if ( dependents == null ) {

            dependents = new ArrayList<DependencyDecisionResultVO>();
        }
        return dependents;
    }

   
    private List<AwardLineSummaryVO>  getAwardLineSummariesPriorToday( final ProcessAwardDependentResponse    awardResponse  ) {    
    	
    	List<AwardLineSummaryVO>    filteredSummaryList = new ArrayList<AwardLineSummaryVO>(); 
    	filteredSummaryList.add( new AwardLineSummaryVO() );
 
   	 	Date	toDay				= new Date();	
   	 	Date	lastEffectiveDate 	= null;

    	List<AwardLineSummaryVO>	awardSummaryList	= getLineSummaries( awardResponse );
    	 
         for ( AwardLineSummaryVO summary : awardSummaryList ) {
        	 
        	 Date currentEffectiveDate	=	summary.getEffectiveDate().toGregorianCalendar().getTime();
        	 
        	 if ( lastEffectiveDate == null ) {
        		 
        		 addSummaryToList( filteredSummaryList, summary );
        		 lastEffectiveDate = currentEffectiveDate;
        	 }

             if ( SimpleDateUtils.isOnOrBefore( currentEffectiveDate, toDay ) )  {
            	
            	 if ( currentEffectiveDate.after( lastEffectiveDate ) ) {
            	 
            		 lastEffectiveDate = currentEffectiveDate;
            		 addSummaryToList( filteredSummaryList, summary );
            		 
            	 }
             }
         }
         
    	 return filteredSummaryList;
    }
    
    
    private int calculteNumberOfDependents( final AwardLineSummaryVO summaryVO ) {
    	
    	int	numberOfDependents	=	0; 
    	
    	Integer	intMinorChildren 	= summaryVO.getNumberOfMinorChildren();
    	Integer	intSchoolChildren	= summaryVO.getNumberOfSchoolChildren();
    	String  isSpouseInAward		= summaryVO.getSpouseIsPartOfAward();
    	
    	if ( null != intMinorChildren ) {
    		
    		numberOfDependents += intMinorChildren.intValue();
    	}
    	
    	if ( null != intSchoolChildren ) {
    		
    		numberOfDependents += intSchoolChildren.intValue();
    	}
    	if ( ! StringUtils.isBlank( isSpouseInAward )  && isSpouseInAward.equalsIgnoreCase( "Spouse" ) )	 {
    		
    		numberOfDependents++;
    	}
    
    	return numberOfDependents;
    }
    
    
    private void addSummaryToList( final List<AwardLineSummaryVO>    filteredSummaryList, 
    		                       final AwardLineSummaryVO summary ) {
    	
		 filteredSummaryList.remove( 0 );
		 filteredSummaryList.add( 0, summary );

    }
    


    
    
    public void setPathToPdfOutputDirectory( final String pathToPdfOutputDirectory ) {

        this.pathToPdfOutputDirectory = pathToPdfOutputDirectory;
    }
    
    public String getPathToPdfOutputDirectory() {

        return pathToPdfOutputDirectory;
    }

    public void setRbpsApplicationDetails( final RbpsApplicationDetails rbpsApplicationDetails ) {
    	
    	this.rbpsApplicationDetails = rbpsApplicationDetails;
    }

	public void setRbpsDocGenGenstoreService( final RbpsDocGenGenstoreService rbpsDocGenGenstoreService ) {
		this.rbpsDocGenGenstoreService = rbpsDocGenGenstoreService;
	}

	public RbpsApplicationDetails getRbpsApplicationDetails() {

        return rbpsApplicationDetails;
    }    
    
    public void setReadCurrentAndProposedAwardWSHandler( final ReadCurrentAndProposedAwardWSHandler readCurrentAndProposedAwardWSHandler ) {

        this.readCurrentAndProposedAwardWSHandler = readCurrentAndProposedAwardWSHandler;
    }  
    
    
	public void setAwardWebServiceWSHandler(final AwardWebServiceWSHandler awardWebServiceWSHandler) {
		this.awardWebServiceWSHandler = awardWebServiceWSHandler;
	}

	public void setAwardWebServicePopulator(final AwardWebServicePopulator awardWebServicePopulator) {
		this.awardWebServicePopulator = awardWebServicePopulator;
	}



    class DependentsComparatorByDate implements Comparator<DependencyDecisionResultVO> {

        @Override
        public int compare( final DependencyDecisionResultVO    lhs,
                            final DependencyDecisionResultVO    rhs ) {

            return new CompareToBuilder()
                .append( SimpleDateUtils.xmlGregorianCalendarToDay( lhs.getEventDate() ),
                         SimpleDateUtils.xmlGregorianCalendarToDay( rhs.getEventDate() ) )

                .append( lhs.getAlReasonSequenceNumber(), rhs.getAlReasonSequenceNumber() )

                .append( lhs.getFirstName(), rhs.getFirstName() )

                //      Reverse this comparison to achieve Grants before Denials.
                .append( grantDenialCode( lhs.getGrantDenial() ),
                         grantDenialCode( rhs.getGrantDenial() ) )
                .toComparison();
        }


        private int grantDenialCode( final String      grantDenial ) {

            if ( "Grant".equalsIgnoreCase( grantDenial ) ) {

                return 0;
            }

            if ( "Removal".equalsIgnoreCase( grantDenial ) ) {

                return 1;
            }

            return 2;
        }
    }
    
    private static int countDependents(
			List<DependencyDecisionResultVO> dependencyDecList)
			throws Exception {
		logger.debug("BEGIN countDependents");
		String errMsg;
		int count = 0;
		DependencyDecisionResultVO curDec = null;
		DependencyDecisionResultVO lastDec = null;

		HashMap<Long, List<DependencyDecisionResultVO>> myHashMap = new HashMap<Long, List<DependencyDecisionResultVO>>();
		List<DependencyDecisionResultVO> myDepList;
		for (int idx = 0; idx < dependencyDecList.size(); idx++) {
			curDec = dependencyDecList.get(idx);

			errMsg = "curDec =  " + curDec.getPersonID()
					+ " with decisionType = "
					+ curDec.getDependencyDecisionType() + ", statusType = "
					+ curDec.getDependencyStatusType();
			logger.debug(errMsg);
			myDepList = myHashMap.get(curDec.getPersonID());
			if (myDepList == null) {
				myDepList = new ArrayList<DependencyDecisionResultVO>();
				myDepList.add(curDec);
				myHashMap.put(curDec.getPersonID(), myDepList);
			} else
				myDepList.add(curDec);
		}

		for (Map.Entry<Long, List<DependencyDecisionResultVO>> entry : myHashMap
				.entrySet()) {
			myDepList = entry.getValue();
			if (isDependentActive(myDepList)){
				count++;
			}
		}

		logger.debug("END countDependents");
		return count;
	}
    
private static List<DependencyDecisionResultVO> getDepLIst(List<DependencyDecisionResultVO> dependencyDecList){
	HashMap<Long, List<DependencyDecisionResultVO>> myHashMap = new HashMap<Long, List<DependencyDecisionResultVO>>();
	List<DependencyDecisionResultVO> myDepList=null;
	DependencyDecisionResultVO curDec = null;
	DependencyDecisionResultVO lastDec = null;
	String errMsg;
	for (int idx = 0; idx < dependencyDecList.size(); idx++) {
		curDec = dependencyDecList.get(idx);

		errMsg = "curDec =  " + curDec.getPersonID()
				+ " with decisionType = "
				+ curDec.getDependencyDecisionType() + ", statusType = "
				+ curDec.getDependencyStatusType();
		logger.debug(errMsg);
		myDepList = myHashMap.get(curDec.getPersonID());
		if (myDepList == null) {
			myDepList = new ArrayList<DependencyDecisionResultVO>();
			myDepList.add(curDec);
			myHashMap.put(curDec.getPersonID(), myDepList);
		} else
			myDepList.add(curDec);
	}
	return myDepList;
}

	private static void updateDependenentActiveStatusOnAwardReasons(List<AwardSummary> summaries,
			List<DependencyDecisionResultVO> dependencyDecList) throws Exception {
		/*HashMap<Long, List<DependencyDecisionResultVO>> myHashMap = new HashMap<Long, List<DependencyDecisionResultVO>>();

		List<DependencyDecisionResultVO> myDepList1 = getDepLIst(dependents);

		for (AwardSummary summary : summaries) {

			List<AwardReason> awardReason = summary.getReasons();
			for (AwardReason reason : awardReason) {
				long dependentId = reason.getDepndentPersonID();
				for (Map.Entry<Long, List<DependencyDecisionResultVO>> entry : myHashMap.entrySet()) {
					List<DependencyDecisionResultVO> myDepList = entry.getValue();
					if (isDependentActive(myDepList)) {
						if (myDepList.get(0).getPersonID() == dependentId) {
							reason.setActiveDependent(true);
						}
					}

				}

			}

		}*/
		logger.debug("BEGIN updateDependenentActiveStatusOnAwardReasons");
		String errMsg;
		int count = 0;
		DependencyDecisionResultVO curDec = null;
		DependencyDecisionResultVO lastDec = null;

		HashMap<Long, List<DependencyDecisionResultVO>> myHashMap = new HashMap<Long, List<DependencyDecisionResultVO>>();
		List<DependencyDecisionResultVO> myDepList;
		for (int idx = 0; idx < dependencyDecList.size(); idx++) {
			curDec = dependencyDecList.get(idx);

			errMsg = "curDec =  " + curDec.getPersonID()
					+ " with decisionType = "
					+ curDec.getDependencyDecisionType() + ", statusType = "
					+ curDec.getDependencyStatusType();
			logger.debug(errMsg);
			myDepList = myHashMap.get(curDec.getPersonID());
			if (myDepList == null) {
				myDepList = new ArrayList<DependencyDecisionResultVO>();
				myDepList.add(curDec);
				myHashMap.put(curDec.getPersonID(), myDepList);
			} else
				myDepList.add(curDec);
		}

		for (Map.Entry<Long, List<DependencyDecisionResultVO>> entry : myHashMap
				.entrySet()) {
			myDepList = entry.getValue();
			if (isDependentActive(myDepList)){
				for (AwardSummary summary : summaries) {

					List<AwardReason> awardReason = summary.getReasons();
					for (AwardReason reason : awardReason) {
						long dependentId = reason.getDepndentPersonID();
						if (myDepList.get(0).getPersonID() == dependentId) {
							reason.setActiveDependent(true);
						}
					}
				}
				
			}
		}

		logger.debug("END updateDependenentActiveStatusOnAwardReasons");

	}

	private static boolean isDependentActive(
			List<DependencyDecisionResultVO> dependencyDecList)
			throws Exception {
		logger.debug("BEGIN isDependentActive");

		Collections.sort(dependencyDecList,
				new Comparator<DependencyDecisionResultVO>() {
					@Override
					public int compare(DependencyDecisionResultVO o1,
							DependencyDecisionResultVO o2) {
						return o1.getAwardEffectiveDate().compare(
								o2.getAwardEffectiveDate());
					}
				});

		DependencyDecisionResultVO curDec = null;
		DependencyDecisionResultVO pastDec = null;
		DependencyDecisionResultVO futureDec = null;
		String errMsg;
		Date today = new Date();

		int idx = 0;
		do {
			curDec = dependencyDecList.get(idx);
			idx++;
			errMsg = "curDec = " + curDec.getPersonID()
					+ " with decisionType = "
					+ curDec.getDependencyDecisionType() + ", statusType = "
					+ curDec.getDependencyStatusType() + ", effective date = "
					+ curDec.getAwardEffectiveDate();

			logger.debug(errMsg);

			if (curDec.getAwardEffectiveDate().toGregorianCalendar().getTime().compareTo(today) >= 0) {
				futureDec = curDec;
				break;
			} else
				pastDec = curDec;
		} while (idx < dependencyDecList.size());

		if (logger.isDebugEnabled()) {
			if (pastDec != null) {
				errMsg = "pastDec = " + pastDec.getPersonID()
						+ " with decisionType = "
						+ pastDec.getDependencyDecisionType()
						+ ", statusType = " + pastDec.getDependencyStatusType()
						+ ", effective date = "
						+ pastDec.getAwardEffectiveDate();
			}

			if (futureDec != null) {
				errMsg = "futureDec = " + futureDec.getPersonID()
						+ " with decisionType = "
						+ futureDec.getDependencyDecisionType()
						+ ", statusType = "
						+ futureDec.getDependencyStatusType()
						+ ", effective date = "
						+ futureDec.getAwardEffectiveDate();
			}
		}

		boolean isGrant = false;
		if (pastDec != null
				&& pastDec.getDependencyStatusType().compareTo("NAWDDEP") != 0) {
			logger.debug("pastDec is a grant");
			isGrant = true;
		} else if (futureDec != null
				&& futureDec.getDependencyStatusType().compareTo("NAWDDEP") != 0) {
			logger.debug("futureDec is a grant");
			isGrant = true;
		}

		logger.debug("END isDependentActive");
		return isGrant;
	}
}
