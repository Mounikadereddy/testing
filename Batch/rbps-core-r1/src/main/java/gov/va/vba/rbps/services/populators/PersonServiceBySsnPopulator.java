package gov.va.vba.rbps.services.populators;

import java.util.List;

import org.apache.commons.collections.MultiHashMap;
import org.apache.commons.collections.MultiMap;
import org.springframework.util.CollectionUtils;



import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.exception.RbpsRuntimeException;
import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.coreframework.util.SimpleDateUtils;
import gov.va.vba.rbps.coreframework.xom.Address;
import gov.va.vba.rbps.coreframework.xom.Child;
import gov.va.vba.rbps.coreframework.xom.Dependent;
import gov.va.vba.rbps.coreframework.xom.Spouse;
import gov.va.vba.rbps.services.ws.client.mapping.awards.personService.FindPersonsBySsnsResponse;
import gov.va.vba.rbps.services.ws.client.mapping.awards.personService.PersonDTO;





public class PersonServiceBySsnPopulator {
	
	
	private static 	Logger 		logger 				= Logger.getLogger(PersonServiceBySsnPopulator.class);
	private     	LogUtils    logUtils        	= new LogUtils( logger, true );
	private 		MultiMap	corporatePersonMap;
	
	public void populateCorporateDependentsBySsns( final FindPersonsBySsnsResponse response, final RbpsRepository	repository ) {
		
		
		if ( response == null ) {
			
			return;
		}
		
		if ( CollectionUtils.isEmpty( response.getPersonDTO() ) ) {
			
			return;
		}
		
		corporatePersonMap = createMapForCorporateDependents( response );
		
		populateCorporateParticipantIdsForSpouseFromMap( repository, corporatePersonMap );
		populateCorporateParticipantIdsForLAtestPreviousMarriageFromMap( repository, corporatePersonMap );
		populateCorporateParticipantIdsForChildrenFromMap( repository, corporatePersonMap );
		
	}
	
	
	private MultiMap createMapForCorporateDependents( final FindPersonsBySsnsResponse response ) {
		
		MultiMap corporatePersonMap = new MultiHashMap();
		
		for ( PersonDTO person 	:	response.getPersonDTO() ) {
			
			corporatePersonMap.put( person.getSsnNbr(), person );
		}
		
		return corporatePersonMap;
	}
	
	
	@SuppressWarnings("unchecked")
	private void populateCorporateParticipantIdsForSpouseFromMap( final RbpsRepository	repository, final MultiMap corporatePersonMap ) {
		
		
		if ( repository.getVeteran().getCurrentMarriage() == null ) {
			return;
		}
		
		if ( repository.getVeteran().getCurrentMarriage().getMarriedTo() == null ) {
			return;
		}
		
		Spouse spouse 	= repository.getVeteran().getCurrentMarriage().getMarriedTo();
		String ssn 		= spouse.getSsn();
		List<PersonDTO> personList = (List<PersonDTO>) corporatePersonMap.get( ssn );
		
		if ( CollectionUtils.isEmpty( personList ) ) {
			return;
		}
		
		String msg = String.format( "Auto Dependency Processing Reject Reason - Submitted SSN/ First Name/ DOB does not match corporate record for dependent %s %s. Please review. ", 
																													spouse.getFirstName(), spouse.getLastName() );
		
		//If findPersonBySsns returned more than one row for a dependent matching First name and Date of Birth, 
		//claim will be sent for manual processing.
		if ( ifPersonListHasMoreThanOneMatchingRowForDependent( personList, spouse )  ){
			throw new RbpsRuntimeException( msg );
		}

		
		for ( PersonDTO	person	:	personList ) { 
			
			if ( ! isPersonMatchingWithDependent( person, spouse ) ) {
				throw new RbpsRuntimeException( msg );
			}
			logUtils.log( String.format( "Populating participant id from Person service for spouse %s %s, SSN: %s", 
										spouse.getFirstName(), spouse.getLastName(), spouse.getSsn() ), repository );
			
			spouse.setCorpParticipantId( person.getPtcpntId().longValue() );
		}
	}

	
	
	@SuppressWarnings("unchecked")
	private void populateCorporateParticipantIdsForLAtestPreviousMarriageFromMap( final RbpsRepository	repository, final MultiMap corporatePersonMap ) {
		
		
		if ( repository.getVeteran().getLatestPreviousMarriage() == null ) {
			return;
		}
		
		if ( repository.getVeteran().getLatestPreviousMarriage().getMarriedTo() == null ) {
			return;
		}
		
		Spouse spouse 	= repository.getVeteran().getLatestPreviousMarriage().getMarriedTo();
		String ssn 		= spouse.getSsn();
		List<PersonDTO> personList = (List<PersonDTO>) corporatePersonMap.get( ssn );
		
		if ( CollectionUtils.isEmpty( personList ) ) {
			return;
		}
		
		String msg = String.format( "Auto Dependency Processing Reject Reason - Submitted SSN/ First Name/ DOB does not match corporate record for dependent %s %s. Please review. ", 
																													spouse.getFirstName(), spouse.getLastName() );
		
		//If findPersonBySsns returned more than one row for a dependent matching First name and Date of Birth, 
		//claim will be sent for manual processing.
		if ( ifPersonListHasMoreThanOneMatchingRowForDependent( personList, spouse )  ){
			throw new RbpsRuntimeException( msg );
		}

		
		for ( PersonDTO	person	:	personList ) { 
			
			if ( ! isPersonMatchingWithDependent( person, spouse ) ) {
				throw new RbpsRuntimeException( msg );
			}
			logUtils.log( String.format( "Populating participant id from Person service for spouse %s %s, SSN: %s", 
										spouse.getFirstName(), spouse.getLastName(), spouse.getSsn() ), repository );
			
			spouse.setCorpParticipantId( person.getPtcpntId().longValue() );
		}
	}
	
	
	@SuppressWarnings("unchecked")
	private void populateCorporateParticipantIdsForChildrenFromMap( final RbpsRepository	repository, final MultiMap corporatePersonMap ) {
		
		for ( Child	child	:	repository.getVeteran().getChildren() ) { 
			
			if ( child.isEverOnAward() ) {
				continue;
			}
			
			String ssn 		= child.getSsn();
			List<PersonDTO> personList = (List<PersonDTO>) corporatePersonMap.get( ssn );
			
			if ( CollectionUtils.isEmpty( personList ) ) {
				continue;
			}
			
			String msg = String.format( "Auto Dependency Processing Reject Reason - Submitted SSN/ First Name/ DOB does not match corporate record for dependent %s %s. Please review. ", 
																														child.getFirstName(), child.getLastName() );
	
			//If findPersonBySsns returned more than one row for a dependent matching First name and Date of Birth, 
			//claim will be sent for manual processing.
			if ( ifPersonListHasMoreThanOneMatchingRowForDependent( personList, child )  ){
				throw new RbpsRuntimeException( msg );
			}
			
			for ( PersonDTO	person	:	personList ) {
				
				if ( ! isPersonMatchingWithDependent( person, child ) ) {
					throw new RbpsRuntimeException( msg );
				}
				//530998 add birth city state to log could be remove later
				String birthAddr = "";
				if ( child.getBirthAddress() != null) {
					birthAddr = child.getBirthAddress().getCity() + "," + child.getBirthAddress().getState();
				}
				logUtils.log( String.format( "Populating participant id from Person service for child %s %s, SSN: %s, birth address: %s", 
											child.getFirstName(), child.getLastName(), child.getSsn(), birthAddr ), repository );
				
				child.setCorpParticipantId( person.getPtcpntId().longValue() );
			}
		}
	}
	
	
	private boolean ifPersonListHasMoreThanOneMatchingRowForDependent( final List<PersonDTO> personList, final Dependent dependent ) {
		
		int count = 0;
		
		for ( PersonDTO	person	:	personList ) {
			
			if ( isPersonMatchingWithDependent( person, dependent ) ) {
				++ count;
			}
		}
		
		if ( count > 1) {
			return true;
		}
		
		return false;
	}
	
	private boolean isPersonMatchingWithDependent( final PersonDTO person, final Dependent dependent ){
		
		if ( person.getFirstNm()== null ) {
			
			return false;
		}

		
		if ( ! person.getFirstNm().equalsIgnoreCase( dependent.getFirstName() ) ) {
			
			return false;
		}
	
		if ( person.getBrthdyDt()== null ) {
			
			return false;
		}
		
		if ( ! SimpleDateUtils.xmlGregorianCalendarToDay( person.getBrthdyDt() ).equals( dependent.getBirthDate() ) ){
			
			return false;
		}
		
		return true;
	}

}
