/*
 * GenericLetterGeneration.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.lettergeneration;


import gov.va.vba.framework.esb.documentmanagement.PdfDocument;
import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.exception.RbpsLetterGenException;
import gov.va.vba.rbps.coreframework.exception.RbpsRuntimeException;
import gov.va.vba.rbps.coreframework.exception.RbpsServiceException;
import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.lettergeneration.batching.util.FileNameGenerator;
import gov.va.vba.rbps.lettergeneration.batching.util.PathUtils;
import gov.va.vba.rbps.lettergeneration.docgen.LetterType;
import gov.va.vba.rbps.lettergeneration.docgen.exception.DocGenException;
import gov.va.vba.rbps.lettergeneration.docgen.genstore.service.RbpsDocGenGenstoreService;
import gov.va.vba.rbps.lettergeneration.util.LetterConstant;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
//import org.xhtmlrenderer.pdf.ITextRenderer;


/**
 *      Generate the pdf and csv files for a claim/veteran.
 *      The file names will contain the claim # and an abbreviated
 *      form of the date: yyyyMMdd.
 */
public class GenericLetterGeneration {

    private static Logger logger = Logger.getLogger(GenericLetterGeneration.class);

    public static final String PDF_EXTENSION = "pdf";
    public static final String CSV_EXTENSION = "csv";

    private final RbpsDocGenGenstoreService docGenGenstoreService;

    private LogUtils        				logUtils                    = new LogUtils( logger, true );
    private boolean                         reportMissingMethods        = false;
    private LetterFields                    letterFields;
    private List<AwardSummary>              awardSummaries;
    private String                          poaOrganizationName;
    private String                          pathToPdfOutputDirectory    = System.getProperty( "java.io.tmpdir" );
    private boolean                         logit                       = true;
    private RbpsApplicationDetails			rbpsApplicationDetails;


    public GenericLetterGeneration(RbpsDocGenGenstoreService docGenGenstoreService) {
        this.docGenGenstoreService = docGenGenstoreService;
    }

    /**
     *      Generates a pdf notification letter of the appropriate type and a corresponding
     *      csv file.  The files are generated in the directory specified in the
     *      rbps.properties files.  If no destination directory is specified, the
     *      files will be generated in the directory specified by the java.io.tmpdir
     *      system property.
     */
    public void generateLetterAndCsv( final RbpsRepository                  repo,
                                      final List<AwardSummary>              awardSummary,
                                      final String                          poaOrganizationName,
                                      final String                          pathToOutputDirectory,
                                      final RbpsApplicationDetails			rbpsApplicationDetails ) {

        this.pathToPdfOutputDirectory   = pathToOutputDirectory;
        this.poaOrganizationName        = poaOrganizationName;
        this.rbpsApplicationDetails		= rbpsApplicationDetails;

        generateLetterAndCsv( repo, awardSummary );
    }


    /**
     *      Generates a pdf notification letter of the appropriate type and a corresponding
     *      csv file.  The files are generated in the directory specified in the
     *      rbps.properties files.  If no destination directory is specified, the
     *      files will be generated in the directory specified by the java.io.tmpdir
     *      system property.
     */
    public void generateLetterAndCsv( final RbpsRepository                  repo,
                                      final List<AwardSummary>              awardSummaries ) {


        this.awardSummaries = awardSummaries;
        try {
            createLetterFields(repo);
            String pdfFileName = generateFileName("");
            String letterName = String.format("%s.pdf", pdfFileName);

            LetterFieldsMarshal letterFieldsMarshal = marshalLetterFields(repo);
            LetterType letterType = getLetterType(letterFieldsMarshal.getHasApprovals(), letterFieldsMarshal.getHasDenials(), letterFieldsMarshal.getHasMilitaryPay());
            docGenGenstoreService.generateDocumentAndSave(repo, letterType, letterFieldsMarshal, letterName);
            logger.debug("DocGen Service Successfully Generated Letter.");
//		        generateRbpsLettter( repo, rbpsApplicationDetails,
//		        						   baoStream, pdfFileName,
//		        					 	   letterFields.getHasApprovals(),
//		        					 	   letterFields.getHasDenials(),
//		        					 	   letterFields.getHasMilitaryPay() );
            String csvFileName = generateFileNameForCsv(pathToPdfOutputDirectory);
           // generateCsvFile(csvFileName);
            uploadFile(repo, letterName);
            boolean isInternational = !repo.getVeteran().getMailingAddress().getCountry().equalsIgnoreCase("USA");
            saveStationDetailsInDatFile(repo, pdfFileName, isInternational);
        } catch (Exception e) {
            throw new RbpsLetterGenException(e);
        }

    }


    /**
     * Determine the letter type based on approvals, denials, and military pay
     * @param hasApprovals approvals check
     * @param hasDenials denials check
     * @param hasMilitaryPay military pay check
     * @return LetterType to generate
     */
    private LetterType getLetterType(boolean hasApprovals, boolean hasDenials, boolean hasMilitaryPay) {
        if ( hasApprovals && hasDenials && !hasMilitaryPay ) {
            return LetterType.RBPS_LETTER;
        }

        if ( hasApprovals && !hasMilitaryPay ) {
            return LetterType.APPROVAL;
        }

        if (hasApprovals && hasDenials) {
            return LetterType.MILITARY_APPROVAL_DENIAL;
        }

        if (hasApprovals) {
            return LetterType.MILITARY_APPROVAL;
        }

        if (hasDenials ) {
            return LetterType.DENIAL;
        }

        throw new DocGenException("unable to determine letter to generate");

    }

    /**
     *      Generates a notification letter of the appropriate type.
     *      The output pdf file specified will need to specify the full
     *      path.  If only a file name is given, it'll be put in the
     *      program's current directory.
     */
    public void generateLetter( final String       templateName,
                                final String       outputFileName ) {

        try {
            String interpolatedTemplate = interpolateTemplate( templateName );

            outputProcessedTemplate( interpolatedTemplate, outputFileName );
        }
        catch ( Throwable ex ) {

            throw new UnableToGenerateLetterException( templateName, ex );
        }
    }


    /**
     *     Given a template, build up the param map needed to feed values
     *     to velocity that velocity uses to fill in variables in the veclocity
     *     template.
     *
     * @param templateName - name of the template velocity will use to produce output.
     * @return the processed template, the final output (html)
     */
    public String interpolateTemplate( final String         templateName ) {

        Map<String,Object>  param                   = new HashMap<String,Object>();

        param.put( LetterConstant.LETTER_FIELDS_KEY,      letterFields );
        param.put( LetterConstant.CSS_PATH_KEY,           readFileFromClassPath( LetterConstant.CSS_PATH ) );
        param.put( LetterConstant.LOGO_PATH_KEY,          generateUrlForLogoImage(LetterConstant.LOGO_PATH ) );

        return mergeTemplate( templateName, param );
    }


    /**
     *     Given the param map and the name of the template, do the actual
     *     merge of the variables in the param map into the velocity template.
     *
     * @param templateName
     * @param param
     * @return
     */
    public String mergeTemplate( final String                 templateName,
                                 final Map<String,Object>     param ) {

        Markup      markup = new Markup();

        return markup.mergeTemplate( templateName, param, reportMissingMethods );
    }


    /**
     *     Given the content of a notification letter (html) and the path to
     *     a file, convert the html input into a pdf file in the given path.
     *
     * @param content      html content that will be converted to pdf
     * @param outputFileName   the path to the pdf file that will be generated
     */
    public void outputProcessedTemplate( final String       content,
                                         final String       outputFileName ) {

        OutputStream    outputStream = null;

        try {
//            ITextRenderer   renderer        = new ITextRenderer();
//
//            outputStream    = new FileOutputStream( outputFileName );
//            renderer.setDocumentFromString( content );
//            renderer.getFontResolver().addFont( getFontPath(), true);
//            renderer.layout();
//            renderer.createPDF( outputStream );
        }
        catch ( Throwable ex ) {

            throw new UnableToOutputPdfFileException( outputFileName, ex );
        }
        finally {

            IOUtils.closeQuietly( outputStream );
        }
    }


    /**
     *         Given a path to a template file, read the text into memory.
     *         The path should be a classpath construct, such as
     *         <code>com/mycompany/foo/approval.html</code>.
     *         Assumes the file is a text file.
     *
     *         @param templateName - a path to the template file
     *         @return contents of the template file as a string.
     */
    public String readTemplate( final String    templateName )
    {
        return readFileFromClassPath( templateName );
    }


    /**
     *         Given a path of the form <code>com/mycompany/foo/myfile</code>,
     *         read the contents of the file into memory.  Assumes the file
     *         is a text file.
     *
     *         @param classPath - the path to file to be read
     *         @return contents of the file as a string
     */
    public String readFileFromClassPath( final String classPath ) {

        try {

            ClassPathResource   templateResource    = new ClassPathResource( classPath );
//          File                templateFile        = throwExceptionIfFileNotThere( templateResource );

            String              content             = readFromUrl( templateResource.getURL() );

            return content;
        }
        catch ( Throwable ex ) {

            throw new UnableToReadFileException( classPath, ex );
        }
    }


    private String readFromUrl( final URL url ) {

        try {

            InputStream    inputStream     = url.openStream();
            BufferedReader bufferedReader  = new BufferedReader(new InputStreamReader( inputStream ));

            StringBuffer   buffer = new StringBuffer();
            String         data;

            while ((data = bufferedReader.readLine()) != null) {

                buffer.append( data );
             }

            return buffer.toString();
        }
        catch ( Throwable ex ) {

            throw new RbpsServiceException( String.format( "Unable to read from url >%s<", url ) );
        }
    }


    /**
     *         Given a path of the form <code>com/mycompany/foo/logo.png</code>,
     *         turn it into a url so that we can feed it into the velocity template.
     *
     *         @param logoPath
     *         @return
     */
    public String generateUrlForLogoImage( final String logoPath ) {

        try {
            ClassPathResource   templateResource    = new ClassPathResource( logoPath );
//          throwExceptionIfFileNotThere( templateResource );

            return templateResource.getURL().toString();
        }
        catch (IOException ex) {

            throw new IllegalArgumentException( "Unable to find logo file >" + logoPath + "<.", ex );
        }
    }


    /**
     *         Given a path to a pdf file, generate the corresponding csv file.
     *         The contents of the file are agreed upon with the Virtual VA team.
     *         The contents are used by Virtual VA to place the file into the
     *         Virtual VA system with the right attributes and in the right place.
     *         The csv file will be placed in the file system next to the pdf file.
     *         Though the csv file is a csv file, it uses tilde to separate fields.
     *
     *         @param pdfFileName the path to the pdf file.
     */
    public void generateCsvFile( final String fileName ) {

        String                 csvFileName = PathUtils.csvPathFor( fileName );
        CsvFileGenerator       generator   = new CsvFileGenerator();

        generator.generateCsvFile( letterFields.getClaimNumber(),
        						   fileName,
                                   csvFileName,
                                   CsvFileGenerator.VIRTUAL_VA_DOC_TYPE_NOTIFICATION_LETTER );
    }


    /**
     *         Generates file name for Adobe LiveCycle template.
     *         This will include parts such as the claim or file id
     *         and some form of the date.  The file name might look like
     *         <code>16155027_20110714</code>.
     *
     *         @param pathToPdfOutputDirectory
     *         @return the full file name without extension
     */
    public String generateFileName(String path ) {
        
        return new FileNameGenerator(path).generatePdfFileNameWithoutExtension( letterFields.getClaimNumber() );
    }

    
    public String generateFileNameForCsv(String path ) {
        
        return new FileNameGenerator(path).generateFileName( letterFields.getClaimNumber(), "pdf" );
    }
    
    public String generateCsvFileName(){
    	
      return new FileNameGenerator( pathToPdfOutputDirectory)
      .generateFileName( letterFields.getClaimNumber(),
                         "" );
    }
    
    
    public String generateFileNameOnly( final String     extension ) {

        return new FileNameGenerator()
                    .generateFileName( letterFields.getClaimNumber(),
                                       extension );
    }
    
    
    public String generateCopyFileNameForVeteran( final RbpsRepository	repository, 
    											  final RbpsApplicationDetails rbpsApplicationDetails, 
    											  final String fileName ,
    											  final boolean isInternational,
    											  final boolean isPhilippines ) {

        return new FileNameGenerator()
                    .generateCopyFileNameForVeteran( repository, rbpsApplicationDetails, fileName, isInternational, isPhilippines  );
    }   
    
    
    public String generateCopyFileNameForPOA( final RbpsRepository	repository, final RbpsApplicationDetails rbpsApplicationDetails ) {

        return new FileNameGenerator()
                    .generateCopyFileNameForPOA( repository, rbpsApplicationDetails );
    }   
    
    
    /**
     *      Get a date as a string that's formatted so that it can be used
     *      in a file name to help make it more unique.
     *
     *      @return the formatted date string.
     */
    public String getFormattedDate() {

        return DateFormatUtils.format( new Date(), "yyyyMMdd" );
    }


  private void uploadFile( final RbpsRepository repository, final String  fileName ) {
    
            SavePdfFileForFtp uploader = new SavePdfFileForFtp();
            //uploader.setCommonUtils(utils);
            uploader.setLogit( logit );
            
            String 	localFilePath		= String.format( "%s/%s", pathToPdfOutputDirectory, fileName );
            boolean	isInternational		= ( repository.getVeteran().getMailingAddress().getCountry().equalsIgnoreCase( "USA") ) ? false : true ;
            boolean isPhilippines		= ( repository.getVeteran().getMailingAddress().getCountry().toUpperCase().startsWith("Philippines".toUpperCase() ) ) ? true : false;
            String 	veteranFileName 	= generateCopyFileNameForVeteran( repository, rbpsApplicationDetails, localFilePath, isInternational, isPhilippines  );
            String 	poaFileName 		= "";

            
            if ( repository.getVeteran().hasPOA() && ! isInternational ) {
            	
            	poaFileName = generateCopyFileNameForPOA( repository, rbpsApplicationDetails );
            }
    
            uploader.saveFileForPrinting( pathToPdfOutputDirectory, fileName, veteranFileName, poaFileName, isInternational, repository );
  }

  
  
  private void saveStationDetailsInDatFile( RbpsRepository repository, String fileName, boolean isInternational ) {
	  
	  String 					datFileName 			= "ro_addrs.dat";
	  String 					datFilePath				= String.format( "%s/POA/%s", pathToPdfOutputDirectory, datFileName );
	  SaveDatFileForPrinting	saveDatFileForPrinting  = new SaveDatFileForPrinting();
	  
	  try {
		  
		  if ( repository.getVeteran().hasPOA() && ! isInternational) {
		  
			  //saveDatFileForPrinting.setCommonUtils(utils);
			  Hashtable<String, String>		stationTable	= saveDatFileForPrinting.getLocationIdHashTable( datFilePath, repository );
			  String						stationId		= repository.getClaimStationAddress().getStationId();
			  String				        idInDatFile		= stationTable.get( stationId );
			  
			  if ( idInDatFile == null  ) {
				  
				  String addressLine2	= repository.getClaimStationAddress().getAddressLine2();
				  String addressLine3	= repository.getClaimStationAddress().getAddressLine3();
				  String city			= repository.getClaimStationAddress().getCity();
				  String state			= repository.getClaimStationAddress().getState();
				  String zip			= repository.getClaimStationAddress().getZipCode();
				  
				  String datFileText 	= String.format( "%s|%s|%s|%s %s %s|", stationId, addressLine2, addressLine3, city, state, zip );
				  
				  logUtils.log( String.format("Adding data %s in %s", datFileText, datFilePath ), repository );
				  
				  saveDatFileForPrinting.addStationDetailsToDatFile( datFilePath, datFileText );
			  }
			  else {
				  
				  logUtils.log( String.format("%s already in %s",  stationId, datFilePath ), repository );
			  }
		  }
		
	} catch ( Exception ex ) {
		
		throw new RbpsRuntimeException( String.format("Error saving station details to dat file %s: %s", fileName, ex.getMessage() ) );
	}
  }


//  private File throwExceptionIfFileNotThere( final ClassPathResource     templateResource )
//          throws IOException {
//
//      return templateResource.getFile();
//  }
    public String getFontPath() {

        try {

            ClassPathResource   templateResource    = new ClassPathResource( LetterConstant.FONT_PATH );
            String              path                = templateResource.getPath().toString();

            return path;
        }
        catch ( Throwable ex ) {

            throw new UnableToReadFileException( LetterConstant.FONT_PATH, ex );
        }
    }


    public void createLetterFields( final RbpsRepository    repo ) {

        if ( letterFields != null ) {
            return;
        }
        letterFields = new LetterFields( repo, awardSummaries );
        letterFields.setLogit( logit );
        letterFields.init();
    }


    public LetterFieldsMarshal marshalLetterFields( RbpsRepository repository ) {

		LetterFieldsMarshaller 			marshaller 			= new LetterFieldsMarshaller();
		LetterFieldsMarshal   			letterFieldsMarshal	= new LetterFieldsMarshal();
		LetterFieldsMarshalPopulator	populator			= new LetterFieldsMarshalPopulator();

		logger.debug("Before populateLetterFieldsMarshalObject");
		populator.populateLetterFieldsMarshalObject( repository, letterFields, letterFieldsMarshal );
		logger.debug("After populateLetterFieldsMarshalObject");

    	return letterFieldsMarshal;
    }

    
    
    public PdfDocument generateRbpsLettter( final RbpsRepository 			repository, 
    								  final RbpsApplicationDetails 	rbpsApplicationDetails,
    								  final ByteArrayOutputStream 	baoStream,  
    								  final String 					pdfFileName,
    								  final boolean					hasApprovals,
    								  final boolean					hasDenials,
    								  final boolean					hasMilitaryPay ) {
    	
    	
    	if( ! hasApprovals && ! hasDenials ) {
    		
    		throw new RbpsRuntimeException( String.format( "Error determining Letter template type with data provided by Awards for Letter >%S<", pdfFileName ) );
    	}
    	
    	GenerateLetterFromLiveCycle	generator	= new GenerateLetterFromLiveCycle();
    	
    	return generator.generateLetter( repository, rbpsApplicationDetails, baoStream, pdfFileName, hasApprovals, hasDenials, hasMilitaryPay );
    	
    }
    
    
    
    public void setReportMissingMethods( final boolean reportMissingMethods ) {

        this.reportMissingMethods = reportMissingMethods;
    }
//    public void setFoo( final RbpsPropertyPlaceholderConfigurer prop ) {
//
//        for ( Map.Entry entry : prop.getProperties().entrySet() ) {
//            log.info( String.format( "%s: %s", entry.getKey(), entry.getValue() ) );
//        }
//    }
    public LetterFields getLetterFields() {

        return letterFields;
    }
    
    public void setAwardSummary( final List<AwardSummary> awardSummary ) {

        this.awardSummaries = awardSummary;
    }
    
    public void setLogit( final boolean   logit ) {

        this.logit = logit;
    }
    

/*
    public void setCommonUtils( final CommonUtils utils ) {

        this.utils = utils;
    }
  */  
}
