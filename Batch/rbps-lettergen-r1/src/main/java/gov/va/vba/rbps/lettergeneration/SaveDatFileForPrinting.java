package gov.va.vba.rbps.lettergeneration;



import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.exception.RbpsRuntimeException;
//import gov.va.vba.rbps.coreframework.util.CommonUtils;
import gov.va.vba.rbps.coreframework.util.LogUtils;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Hashtable;
import java.util.Scanner;



public class SaveDatFileForPrinting {
	
	
    private static Logger logger = Logger.getLogger(SaveDatFileForPrinting.class);

    //private CommonUtils utils;
    private LogUtils    logUtils        = new LogUtils( logger, true );
    private String 		NL 				= System.getProperty("line.separator");
	
	
	public Hashtable<String, String> getLocationIdHashTable( String fileName, RbpsRepository repo ) throws IOException {
		
	    Scanner 						scanner = openDatFile( fileName, repo );
	    Hashtable<String, String>		hashTbl = new Hashtable<String, String>();
	    
	    String datText	= "";
	    
		try {
		    	
			while (scanner.hasNextLine()){
		    	  
				datText				=	scanner.nextLine();
		    	int index 			= 	datText.indexOf( "|");
		    	String locationId 	= 	datText.substring( 0, index );
		    	hashTbl.put( locationId, locationId );
		    	
		    }
		    	
		} catch ( Exception ex ) {
		    	
		    	logUtils.log( String.format("Exception %s: ", ex.getMessage() ), repo );
		    		
		} finally{
		    	
		    	scanner.close();
		}
		
		return hashTbl;
	}
	
	
	private Scanner openDatFile( String fileName, RbpsRepository repo ) {
		//Fortify Unreleased Resource: Stream fix
	    try (FileInputStream is = new FileInputStream( fileName)){
		    
	    	Scanner 		scanner = new Scanner( is );
	    	return scanner;
	    	
		  } catch ( Exception ex ) {
		    	
		    	logUtils.log( String.format("Error opening dat file %s: ", ex.getMessage() ),repo );
		    	return createAndOpenDatFile( fileName, repo );
		 }
	}
	
	
	private Scanner createAndOpenDatFile( String fileName, RbpsRepository repo) {
		//Fortify Unreleased Resource: Streams. Fix
	    try (FileOutputStream os = new FileOutputStream( fileName )){
	    	
	    	Writer out = new OutputStreamWriter( os );
	    	out.close();
	    	//Fortify Unreleased Resource: Streams. Fix
	    	try (FileInputStream is = new FileInputStream( fileName )) {
	    	Scanner scanner = new Scanner( is );
	    	return scanner;
	    	}
	    	
	      } catch ( Exception ex ) {
	    	
	    	logUtils.log( String.format("Error creating dat file %s: ", ex.getMessage() ), repo );
	    	throw new RbpsRuntimeException( String.format("Error creating dat file %s: ", fileName ) );
	    }	
	}
	
	public void addStationDetailsToDatFile( String filePath, String datText ) {
		//Fortify Unreleased Resource: Stream. Fix
		try (FileWriter writer = new FileWriter( filePath, true )){
			
			//FileWriter writer = new FileWriter( filePath, true );
			writer.write(datText + NL);
			writer.close();
			
		} catch (IOException e) {
			
			throw new RbpsRuntimeException( String.format("Error writing to dat file %s: ", filePath ) );
		}

	}
	
	

   /* public void setCommonUtils( final CommonUtils utils ) {

        this.utils = utils;
    }
    */
}
