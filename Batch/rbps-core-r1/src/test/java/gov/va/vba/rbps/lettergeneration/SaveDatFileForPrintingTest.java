package gov.va.vba.rbps.lettergeneration;

import gov.va.vba.rbps.coreframework.dto.RbpsRepository;

import java.util.Hashtable;

import org.junit.Ignore;
import org.junit.Test;

public class SaveDatFileForPrintingTest {
	
	
	private	SaveDatFileForPrinting	saveDatFileForPrinting = new SaveDatFileForPrinting();
    
	@Ignore
    @Test
    public void shouldReadDatFileExistingData() {
    	
    	try {

    		Hashtable<String, String>		hashTbl = saveDatFileForPrinting.getLocationIdHashTable( "/AdobeDoc/devl/RBPS/cFileName.dat", new RbpsRepository() );
    		
//    		saveDatFileForPrinting.addStationDetailsToDatFile( "/AdobeDoc/devl/RBPS/cFileName.dat", "345|3333 N Central Avenue||Phoenix AZ 85012|" );
//    		saveDatFileForPrinting.addStationDetailsToDatFile( "/AdobeDoc/devl/RBPS/cFileName.dat", "346|4444 N Central Avenue||Phoenix AZ 85021|" );
    		
    	} catch ( Exception ex ) {
    		
    		
    	}
    }
    
    @Test
    public void shouldReadDatFileNotExistingData() {
    	
    	try {

    		Hashtable<String, String>		hashTbl = saveDatFileForPrinting.getLocationIdHashTable( "/AdobeDoc/devl/RBPS/aFileName.dat", new RbpsRepository() );
    		
    		String statoinId = hashTbl.get("345");
    		System.out.println();
    		
    	} catch ( Exception ex ) {
    		
    		
    	}
    }
    
    
    @Test
    public void shouldWriteToDatFile() {
    	
    	try {

    		saveDatFileForPrinting.addStationDetailsToDatFile( "/AdobeDoc/devl/RBPS/cFileName.dat", "347|5555 N Central Avenue||Phoenix AZ 85012|" );
    		
    	} catch ( Exception ex ) {
    		
    		
    	}
    }
    
}
