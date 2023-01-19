/*
 * FileNameGenerator.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.lettergeneration.batching.util;

import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.exception.RbpsLetterGenException;
import gov.va.vba.rbps.lettergeneration.RbpsApplicationDetails;
import gov.va.vba.rbps.lettergeneration.SavePdfFileForFtp;
import org.apache.commons.lang.time.DateFormatUtils;

import java.util.Date;



/**
 *      Save the award print data returned from the <code>AmendAward</code>
 *      web service to a pdf file and create the corresponding csv file.
 *      The file names will contain the claim # and an abbreviated
 *      form of the date: yyyyMMdd.
 *
 * @author Karma.Gopalakrishnan
 * @since 07/2010
 * @version 1.0
 *
 * Last change on 08/010/2011
 * Revision History
 * Date         Name                Description
 *  ------------------------------------------------------------
 *  08/01/2011  K.Gopalakrishnan    Created
 */
public class FileNameGenerator {
	
	
	private static 	Logger 	logger 						= Logger.getLogger(FileNameGenerator.class);
    private 		String	pathToPdfOutputDirectory    = System.getProperty( "java.io.tmpdir" );




    public FileNameGenerator() {
        //  do nothing
    }


    public FileNameGenerator( final String    pdfOutputDir ) {

        this.pathToPdfOutputDirectory = pdfOutputDir;
    }


    /**
     *         Given a file extension, build the appropriate file name.
     *         This will include parts such as the claim or file id
     *         and some form of the date.  The file name might look like
     *         <code>16155027_20110714.pdf</code>.
     *
     *         @param extension pdf or csv
     *         @return the full file name
     */
    public String generateFileName( final String    fileNumber,
                                    final String    extension ) {

        return generateFileName( fileNumber, "", extension );
    }

    
    
    
    public String generatePdfFileNameWithoutExtension( final String    fileNumber ) {
    	
    	return generateBaseFileName( fileNumber );
    }
    
    
    /**
     *         Given a file extension, build the appropriate file name.
     *         This will include parts such as the claim or file id
     *         and some form of the date.  The file name might look like
     *         <code>16155027_20110714.pdf</code>.
     *
     *         @param extension pdf or csv
     *         @return the full file name
     */
    public String generateFileName( final String    fileNumber,
                                    final String    suffix,
                                    final String    extension ) {

        String      baseName        = generateBaseFileName( fileNumber );
        String      pdfFileName     = String.format( "%s%s.%s",
                                                     baseName,
                                                     suffix,
                                                     extension );

        return pdfFileName;
    }


    /**
     *      Create the non-extension part of a file name.  This is built from
     *      the claim id and a representation of the date.
     *
     *      @return the desired file name w/o the extension
     */
    public String generateBaseFileName( final String      fileNumber ) {

        String         formattedDate   = getFormattedDate();
        String         baseName        = String.format( "%s_%s",
                                                        fileNumber,
                                                        formattedDate );

        baseName = PathUtils.concatenateToPath( pathToPdfOutputDirectory, baseName );

        return baseName;
    }
    
    
    public String generateCopyFileNameForVeteran( final RbpsRepository	repository, 
    											  final RbpsApplicationDetails rbpsApplicationDetails, 
    											  final String fileName,
    											  final boolean isInternational,
    											  final boolean isPhilippines ) {

    	SavePdfFileForFtp	savePdfFileForFtp	= new SavePdfFileForFtp();
    	StringBuffer   		fileNameBuffer		= new StringBuffer();
    		
    	//CQ 2200
        fileNameBuffer.append( getPrefix( rbpsApplicationDetails ) );
    	
    	if ( isInternational ) {
    		
    		internationalFileName( fileNameBuffer, isPhilippines);
    	}
    	else {
            fileNameBuffer.append( "M" );
    	}
    	
    	String fileNumberMachineBinStationId = getRemainingFileNameVeteran( repository );
    	
    	fileNameBuffer.append( fileNumberMachineBinStationId );
    	fileNameBuffer.append( ".pdf" );
    	
        return fileNameBuffer.toString();
    }
    
    
    public void internationalFileName( StringBuffer fileNameBuffer, boolean isPhilippines) {
    	
    	if ( isPhilippines ) {
    		
    		fileNameBuffer.append( "N" );
    	}
    	else {
    		
    		fileNameBuffer.append( "I" );
    	}
    	 
    }
    
    
    public String generateCopyFileNameForPOA( final RbpsRepository	repository, 
    										  final RbpsApplicationDetails rbpsApplicationDetails ) {
        
    	StringBuffer   		fileNameBuffer		= new StringBuffer();
    		
        //CQ 2200
    	fileNameBuffer.append( getPrefix( rbpsApplicationDetails ) );
    	fileNameBuffer.append( "P" );
    	
    	String fileNumberMachineBinStationId = getRemainingFileNamePOA( repository );
    	
    	fileNameBuffer.append( fileNumberMachineBinStationId );
    	fileNameBuffer.append( ".pdf" );

        return fileNameBuffer.toString();
    }  
    
   //CQ 2200
    public String getPrefix( final RbpsApplicationDetails rbpsApplicationDetails ) {
    	
       if (rbpsApplicationDetails.getPrefix().toUpperCase().contains("P")) {
     	   
    	   return "P";
       }

        return "T";
    }
    

	private String getRemainingFileName( final RbpsRepository repository ) {
		
		String 			fileNumber		= repository.getVeteran().getFileNumber();
    	
    	if ( fileNumber.length() < 9 ) {
    		
    		fileNumber = "_" + fileNumber;
    	}

    	return fileNumber + "#000006";
	}

	private String getRemainingFileNameVeteran(final RbpsRepository repository) {
		return getRemainingFileName(repository) + repository.getClaimStationAddress().getStationId();
	}


	private String getRemainingFileNamePOA(final RbpsRepository repository) {
		if (repository.getSjnId() == null) {
			throw new RbpsLetterGenException("Station Of Jurisdiction was not found for veteran corp participant id: " + repository.getVeteran().getCorpParticipantId()) ;
        }
        
        String sjnId = repository.getSjnId();

		if ( sjnId.length() != 3) {
			throw new RbpsLetterGenException("Station Of Jurisdiction Id " + sjnId  + " for Veteran Id is not 3 digits long.") ;
		}

		return getRemainingFileName(repository) + sjnId;
	}



	public String getDatFileName( String fileName ) {
		
		int 	index		= fileName.indexOf("_");
		String 	dateString 	= fileName.substring( index );
		String 	datFileName	= "RBPS" + 	dateString + ".dat";
		
		return datFileName;
	}

	   
    /**
     *      Get a date as a string that's formatted so that it can be used
     *      in a file name to help make it more unique.
     *
     *      @return the formatted date string.
     */
    public String getFormattedDate() {

        //return DateFormatUtils.format( new Date(), "yyyyMMdd" );
    	return DateFormatUtils.format( new Date(), "yyyyMMddHHmmss" );
    }



    public void setPathToPdfOutputDirectory( final String pathToPdfOutputDirectory ) {

        this.pathToPdfOutputDirectory = pathToPdfOutputDirectory;
    }
}
