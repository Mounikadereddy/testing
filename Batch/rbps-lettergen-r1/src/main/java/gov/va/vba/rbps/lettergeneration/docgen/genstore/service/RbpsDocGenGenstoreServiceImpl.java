package gov.va.vba.rbps.lettergeneration.docgen.genstore.service;

import gov.va.vba.rbps.lettergeneration.docgen.genstore.client.api.DocumentGenerationRequestClaimEvidenceMetadata;
import gov.va.vba.rbps.lettergeneration.docgen.genstore.client.api.DocumentGenerationRequestMetadata;
import gov.va.vba.rbps.lettergeneration.docgen.genstore.client.api.DocumentGenerationRequestWrapper;
import gov.va.vba.rbps.lettergeneration.docgen.genstore.client.api.DocumentGenerationResponseRef;
import gov.va.vba.rbps.lettergeneration.docgen.genstore.client.api.RetrieveDocumentResponse;
import gov.va.vba.rbps.lettergeneration.docgen.genstore.client.GenstoreClient;
import gov.va.vba.rbps.lettergeneration.docgen.genstore.client.GenstoreClientBuilder;
/*
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
*/
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.bip.docgen.service.api.model.v1.ContentBase;
import gov.va.bip.docgen.service.api.model.v1.DocumentGenerationRequest;
import gov.va.bip.docgen.service.plugin.awards.api.edoc.EdocDocument;
import gov.va.bip.docgen.service.plugin.rbps.api.*;
import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.lettergeneration.LetterFieldsMarshal;
import gov.va.vba.rbps.lettergeneration.docgen.DocGenProperties;
import gov.va.vba.rbps.lettergeneration.docgen.LetterType;
import gov.va.vba.rbps.lettergeneration.docgen.exception.DocGenException;
import gov.va.vba.rbps.lettergeneration.docgen.transformer.LetterFieldsMarshalTransformer;

import javax.activation.DataHandler;

import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import feign.Logger.Level;

/**
 * Generate RBPS Letters using DocGen Service
 */
public class RbpsDocGenGenstoreServiceImpl implements RbpsDocGenGenstoreService {
	private static Logger logger = Logger.getLogger(RbpsDocGenGenstoreServiceImpl.class);

	private final GenstoreClient client;
	private final DocGenProperties props;
    private final Long documentTypeId;
	private final Long awardPrintDocumentTypeId;

	/**
	 * @param client
	 *            RBPS DocGen client implementation
	 * @param props
	 *            configuration for docgen
	 */
	public RbpsDocGenGenstoreServiceImpl(DocGenProperties props) {
		/**
		 * Build a sample GenStore client.
		 *
		 * This client will utilize the 
		 * GenStore, and will generate JWT tokens issued by
		 * benefits-services-rbps and signed using "rbps-test-secret" - these
		 * tokens will encode the supplied system SAML token, User ID and
		 * Station ID for authorization purposes.the token,userId,etc are read from rbps_admin_property file
		 */
		logger.debug("props.getGentstoreUrl()"+props.getGentstoreUrl());
		//logger.debug("props.getGenstoreSamlTokenPath()"+props.getGenstoreSamlTokenPath());
		//System.out.println("props.getGenstoreSamlTokenPath()"+props.getGenstoreSamlTokenPath());
		logger.debug("props.getGenstoreUserId()"+props.getGenstoreUserId());
		logger.debug("props.getGenstoreStationId()"+props.getGenstoreStationId());
		logger.debug("props.getGenstoreApplicationName()"+props.getGenstoreApplicationName());
		logger.debug("props.getGenstoreSharedSecret()"+props.getGenstoreSharedSecret());
		//TODO Mounika
		logger.info("props.getDocumentTypeId() "+props.getDocumentTypeId());
		logger.info("props.getAwardPrintDocumentTypeId() "+props.getAwardPrintDocumentTypeId());
		
		// END
		client = new GenstoreClientBuilder().withBaseUrl(props.getGentstoreUrl())
			//	.withSystemSamlToken(getTokenFromXml(props.getGenstoreSamlTokenPath())) 
				.withUserId(props.getGenstoreUserId()) 
				.withStationId(props.getGenstoreStationId())
				.withApplicationName(props.getGenstoreApplicationName()) 
				.withSharedSecret(props.getGenstoreSharedSecret()) 
				.withLogLevel(Level.FULL) 
				.build();

		this.props = props;
		//TODO Mounika
		documentTypeId = props.getDocumentTypeId();
		awardPrintDocumentTypeId = props.getAwardPrintDocumentTypeId();
		//TODO END 
	}

	private String getTokenFromXml(String path) {
		
		String xmlString = null;
		logger.debug("path" + path);
		ClassPathResource resource = new ClassPathResource(path);
		try {
			xmlString = FileUtils.readFileToString(resource.getFile());
			//System.out.println("xmlstring" + xmlString);
			logger.debug("path" + path);
			logger.debug("xmlstring" + xmlString);
		} catch (IOException e) {
			logger.debug("got exception in getTokenFromXml" + e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return xmlString;
	}

	@Override
	public void generateDocumentAndSave(RbpsRepository repo, 
			LetterType letterType, LetterFieldsMarshal letterFields, String fileName) {

		DocumentGenerationResponseRef response;
		switch (letterType) {
		case RBPS_LETTER:
			logger.debug("DocGen: Generating RBPS Letter");
			response = createRbpsLetter(repo, letterFields, fileName);
			break;
		case MILITARY_APPROVAL_DENIAL:
			logger.debug("DocGen: Generating Military Approval/Denial Letter");
			response = createApprovalDenialMilitaryLetter(repo,letterFields, fileName);
			break;
		case APPROVAL:
			logger.debug("DocGen: Generating Approval Letter");
			response = createApprovalLetter(repo, letterFields, fileName);
			break;
		case DENIAL:
			logger.debug("DocGen: Generating Denial Letter");
			response = createDenialLetter(repo,letterFields, fileName);
			break;
		case MILITARY_APPROVAL:
			logger.debug("DocGen: Generating Military Approval Letter");
			response = createApprovalMilitaryLetter(repo,letterFields, fileName);
			break;
		default:
			throw new DocGenException("LetterType not supported");
		}

		//System.out.println("Document USI: " + response.getUsi());
		//System.out.println("Document Ref: " + response.getDocumentRef());
		logger.debug("Document USI: " + response.getUsi());
		logger.debug("Document Ref: " + response.getDocumentRef());
		// save letter to disk

		RetrieveDocumentResponse documentResponse = retrieveDocument(response.getDocumentRef());
		logger.debug("documentResponse: " + documentResponse.getContent());
		saveLetter(documentResponse.getContent(), fileName);

		// createLines(fileName);
	}

	@Override
	public void generateAwardPrintAndSave(RbpsRepository repo,EdocDocument edocDocumentRequest, String fileName, String fileNumber) {
		DocumentGenerationResponseRef response;
		logger.debug("DocGen: Generating Award Print Letter");
		//System.out.print("DocGen: Generating Award Print Letter" + edocDocumentRequest);
		response = createAwardPrintLetter(repo, edocDocumentRequest, fileName, fileNumber);
		logger.debug("Document USI: " + response.getUsi());
		logger.debug("Document Ref: " + response.getDocumentRef());
	
		// save letter to disk
		RetrieveDocumentResponse documentResponse = retrieveDocument(response.getDocumentRef());
		saveLetter(documentResponse.getContent(), fileName);
	}

	/**
	 * @param edocDocumentRequest
	 *            EdocDocument
	 * @return DocumentGenerationResponse containing generated pdf
	 */
	private DocumentGenerationResponseRef createAwardPrintLetter(RbpsRepository repo,EdocDocument edocDocumentRequest, String fileName,
			String fileNumber) {

		logger.debug("EdocDocument Request: " + edocDocumentRequest);
		DocumentGenerationRequest request = createDocGenRequest(edocDocumentRequest,
				DocumentGenerationRequest.DraftEnum.FINAL, LetterType.AWARD_PRINT.getLabel());

		request.setSubjectArea(edocDocumentRequest.getSubjectArea());

		// return client.generateAwardDocument(request);

		DocumentGenerationRequestClaimEvidenceMetadata metadata = createMetadata(repo,fileNumber, fileName,
				props.getGenstoreContentSource(),props.getGenstoresubjectArea());
		metadata.setDocumentTypeId(awardPrintDocumentTypeId);
	    DocumentGenerationRequestWrapper documentGenerationRequestWrapper = new DocumentGenerationRequestWrapper();
		documentGenerationRequestWrapper.setDocument(request);
		documentGenerationRequestWrapper.setMetadata(metadata);
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		//System.out.println(
		//	"gson.toJson(documentGenerationRequestWrapper:" + gson.toJson(documentGenerationRequestWrapper));
      
		logger.debug("gson.toJson(documentGenerationRequestWrapper:" + gson.toJson(documentGenerationRequestWrapper));
	  
		return client.generateDocument(documentGenerationRequestWrapper);
	}

	/**
	 * @param letterFields
	 *            LetterFieldsMarshal to transform
	 * @return DocumentGenerationResponse containing generated pdf
	 */
	private DocumentGenerationResponseRef createApprovalDenialMilitaryLetter(RbpsRepository repo,
				LetterFieldsMarshal letterFields,
			String fileName) {
		ApprovalDenialMilitaryLetterRequest approvalDenialMilitaryLetterRequest = new ApprovalDenialMilitaryLetterRequest();
		String fileNumber = letterFields.getFileNumber();
		LetterFieldsMarshalTransformer.transformDefaults(approvalDenialMilitaryLetterRequest, letterFields);
		approvalDenialMilitaryLetterRequest.setDenialText(letterFields.getDenialText());
		logger.debug("letterFields.getMetList()****************************: " + letterFields.getMetList());
		approvalDenialMilitaryLetterRequest.setMetList(letterFields.getMetList());
		approvalDenialMilitaryLetterRequest.setPartOrAll(letterFields.getPartOrAll());
		approvalDenialMilitaryLetterRequest.setToDate(letterFields.getToDate());
		logger.debug("ApprovalDenialMilitaryLetterRequest: " + approvalDenialMilitaryLetterRequest);
		DocumentGenerationRequest request = createDocGenRequest(approvalDenialMilitaryLetterRequest,
				DocumentGenerationRequest.DraftEnum.FINAL, LetterType.MILITARY_APPROVAL_DENIAL.getLabel());

		DocumentGenerationRequestClaimEvidenceMetadata metadata = createMetadata(repo,fileNumber, fileName,
				props.getGenstoreContentSource(),props.getGenstoresubjectArea());
	    DocumentGenerationRequestWrapper documentGenerationRequestWrapper = new DocumentGenerationRequestWrapper();
		documentGenerationRequestWrapper.setDocument(request);
	    documentGenerationRequestWrapper.setMetadata(metadata);
	    Gson gson = new GsonBuilder().setPrettyPrinting().create();
		System.out.println(
				"gson.toJson(documentGenerationRequestWrapper:" + gson.toJson(documentGenerationRequestWrapper));
      
		logger.debug("gson.toJson(documentGenerationRequestWrapper:" + gson.toJson(documentGenerationRequestWrapper));
	  
		return client.generateDocument(documentGenerationRequestWrapper);
	}

	/**
	 * @param letterFields
	 *            LetterFieldsMarshal to transform
	 * @return DocumentGenerationResponse containing generated pdf
	 */
	private DocumentGenerationResponseRef createRbpsLetter(RbpsRepository repo,LetterFieldsMarshal letterFields, String fileName) {
		LetterRequest letterRequest = new LetterRequest();
		String fileNumber = letterFields.getFileNumber();
		LetterFieldsMarshalTransformer.transformDefaults(letterRequest, letterFields);
		letterRequest.setDenialText(letterFields.getDenialText());
		logger.debug("letterFields.getMetList()****************************: " + letterFields.getMetList());
		letterRequest.setMetList(letterFields.getMetList());
		logger.debug("RbpsLetterRequest: " + letterRequest);
		//System.out.println("RbpsLetterRequest: " + letterRequest);
		DocumentGenerationRequest request = createDocGenRequest(letterRequest,
				DocumentGenerationRequest.DraftEnum.FINAL, LetterType.RBPS_LETTER.getLabel());

		DocumentGenerationRequestClaimEvidenceMetadata metadata = createMetadata(repo,fileNumber, fileName,
				props.getGenstoreContentSource(),props.getGenstoresubjectArea());
	   DocumentGenerationRequestWrapper documentGenerationRequestWrapper = new DocumentGenerationRequestWrapper();
		documentGenerationRequestWrapper.setDocument(request);
	    documentGenerationRequestWrapper.setMetadata(metadata);
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		/*System.out.println(
				"gson.toJson(documentGenerationRequestWrapper:" + gson.toJson(documentGenerationRequestWrapper));
      */
		logger.debug("gson.toJson(documentGenerationRequestWrapper:" + gson.toJson(documentGenerationRequestWrapper));
	      
		return client.generateDocument(documentGenerationRequestWrapper);
	}

	/**
	 * @param letterFields
	 *            LetterFieldsMarshal to transform
	 * @return DocumentGenerationResponse containing generated pdf
	 */
	private DocumentGenerationResponseRef createApprovalMilitaryLetter(RbpsRepository repo,
			LetterFieldsMarshal letterFields,String fileName) {
		ApprovalMilitaryLetterRequest approvalMilitaryLetterRequest = new ApprovalMilitaryLetterRequest();
		String fileNumber = letterFields.getFileNumber();
		LetterFieldsMarshalTransformer.transformDefaults(approvalMilitaryLetterRequest, letterFields);
		approvalMilitaryLetterRequest.setPartOrAll(letterFields.getPartOrAll());
		approvalMilitaryLetterRequest.setToDate(letterFields.getToDate());
		logger.debug("ApprovalMilitaryLetterRequest: " + approvalMilitaryLetterRequest);
		//System.out.println("ApprovalMilitaryLetterRequest: " + approvalMilitaryLetterRequest);
		DocumentGenerationRequest request = createDocGenRequest(approvalMilitaryLetterRequest,
				DocumentGenerationRequest.DraftEnum.FINAL, LetterType.MILITARY_APPROVAL.getLabel());

		DocumentGenerationRequestClaimEvidenceMetadata metadata = createMetadata(repo,fileNumber, fileName,
				props.getGenstoreContentSource(),props.getGenstoresubjectArea());
	DocumentGenerationRequestWrapper documentGenerationRequestWrapper = new DocumentGenerationRequestWrapper();
		documentGenerationRequestWrapper.setDocument(request);
		documentGenerationRequestWrapper.setMetadata(metadata);
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		//System.out.println(
		//		"gson.toJson(documentGenerationRequestWrapper:" + gson.toJson(documentGenerationRequestWrapper));

		logger.debug("gson.toJson(documentGenerationRequestWrapper:" + gson.toJson(documentGenerationRequestWrapper));
		//System.out.println(
		//		"gson.toJson(documentGenerationRequestWrapper:" + gson.toJson(documentGenerationRequestWrapper));
         
		return client.generateDocument(documentGenerationRequestWrapper);
	}

	/**
	 * @param letterFields
	 *            LetterFieldsMarshal to transform
	 * @return DocumentGenerationResponse containing generated pdf
	 */
	private DocumentGenerationResponseRef createDenialLetter(RbpsRepository repo,LetterFieldsMarshal letterFields, String fileName) {
		DenialLetterRequest denialLetterRequest = new DenialLetterRequest();
		String fileNumber = letterFields.getFileNumber();
		String curAmount = letterFields.getCurrentMonthlyAmount();
		denialLetterRequest.setCurrentMonthlyAmount(new BigDecimal(curAmount.replace("$", "")));
		denialLetterRequest.setDenialText(letterFields.getDenialText());
		logger.debug("letterFields.getMetList()****************************: " + letterFields.getMetList());
		denialLetterRequest.setMetList(letterFields.getMetList());
		denialLetterRequest.setLetterFields(LetterFieldsMarshalTransformer.transformLetterFields(letterFields));
		logger.debug("DenialLetterRequest: " + denialLetterRequest);
		//System.out.println("DenialLetterRequest: " + denialLetterRequest);

		DocumentGenerationRequest request = createDocGenRequest(denialLetterRequest,
				DocumentGenerationRequest.DraftEnum.FINAL, LetterType.DENIAL.getLabel());

		DocumentGenerationRequestClaimEvidenceMetadata metadata = createMetadata(repo,fileNumber, fileName,
				props.getGenstoreContentSource(),props.getGenstoresubjectArea());
		DocumentGenerationRequestWrapper documentGenerationRequestWrapper = new DocumentGenerationRequestWrapper();
		documentGenerationRequestWrapper.setDocument(request);
		documentGenerationRequestWrapper.setMetadata(metadata);
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		logger.debug("gson.toJson(documentGenerationRequestWrapper:" + gson.toJson(documentGenerationRequestWrapper));
		//System.out.println(
		//		"gson.toJson(documentGenerationRequestWrapper:" + gson.toJson(documentGenerationRequestWrapper));
         
		return client.generateDocument(documentGenerationRequestWrapper);
	}

	/**
	 * @param letterFields
	 *            LetterFieldsMarshal to transform
	 * @return DocumentGenerationResponse containing generated pdf
	 */
	private DocumentGenerationResponseRef createApprovalLetter(RbpsRepository repo,LetterFieldsMarshal letterFields, String fileName) {

		ApprovalLetterRequest approvalLetterRequest = new ApprovalLetterRequest();
		String fileNumber = letterFields.getFileNumber();
		LetterFieldsMarshalTransformer.transformDefaults(approvalLetterRequest, letterFields);
		logger.debug("ApprovalLetterRequest: " + approvalLetterRequest);
		//System.out.println("ApprovalLetterRequest: " + approvalLetterRequest);
		DocumentGenerationRequest request = createDocGenRequest(approvalLetterRequest,
				DocumentGenerationRequest.DraftEnum.FINAL, LetterType.APPROVAL.getLabel());
		DocumentGenerationRequestClaimEvidenceMetadata metadata = createMetadata(repo,fileNumber, fileName,
				props.getGenstoreContentSource(),props.getGenstoresubjectArea());
	
		DocumentGenerationRequestWrapper documentGenerationRequestWrapper = new DocumentGenerationRequestWrapper();
		documentGenerationRequestWrapper.setDocument(request);
	    documentGenerationRequestWrapper.setMetadata(metadata);
	    
	    Gson gson = new GsonBuilder().setPrettyPrinting().create();
	    logger.debug("gson.toJson(documentGenerationRequestWrapper:" + gson.toJson(documentGenerationRequestWrapper));
		
		//System.out.println(
		//		"gson.toJson(documentGenerationRequestWrapper:" + gson.toJson(documentGenerationRequestWrapper));
       
		return client.generateDocument(documentGenerationRequestWrapper);
	}

	/**
	 * @param content
	 *            request content
	 * @param draft
	 *            DraftEnum
	 * @param documentId
	 *            Document id of the letter
	 * @return DocumentGenerationRequest
	 */
	private DocumentGenerationRequest createDocGenRequest(ContentBase content,
			DocumentGenerationRequest.DraftEnum draft, String documentId) {
		DocumentGenerationRequest documentGenerationRequest = new DocumentGenerationRequest();
		documentGenerationRequest.setContent(content);
		documentGenerationRequest.setDraft(draft);
		documentGenerationRequest.setVisuallyImpaired(false);
		documentGenerationRequest.setPiiMaskingDisabled(false);
		documentGenerationRequest.setUsiGenerationDisabled(false);
		documentGenerationRequest.setSubjectArea(props.getSubjectArea());
		documentGenerationRequest.setDocumentId(documentId);
		return documentGenerationRequest;
	}

	private RetrieveDocumentResponse retrieveDocument(String documentRef) {

		return client.retrieveDocument(documentRef);
	}

	/**
	 * @param dh
	 *            dataHandler holding the generated document
	 * @param fileName
	 *            name of the file we will create
	 */
	private void saveLetter(DataHandler dh, String fileName) {
		logger.debug("DocGen: saving letter");

		// InputStream is = null;
		try (InputStream is = dh.getInputStream()) {
			// is = dh.getInputStream();
			byte[] buffer = new byte[is.available()];
			is.read(buffer);
			String target = props.getOutputDirectory() + "/" + fileName;
			File targetFile = new File(target);
			try (FileOutputStream outStream = new FileOutputStream(targetFile)) {
				logger.debug("DocGen: saving file to: " + target);
				outStream.write(buffer);
			} catch (Exception e) { // Fortify Unreleased Resources: Stream.
				if (is != null)
					is.close();
			}
		} catch (IOException e) {
			throw new DocGenException("Unable to save pdf");
		}
	}

	private DocumentGenerationRequestClaimEvidenceMetadata createMetadata(RbpsRepository repo,String fileNumber, String fileName,
			String contentSource,String subject) {

		DocumentGenerationRequestClaimEvidenceMetadata metadata = new DocumentGenerationRequestClaimEvidenceMetadata();

		LocalDate currentDate = LocalDate.now();
		String endProductCode=repo.getVeteran().getClaim().getClaimLabel().getCode();

		LocalDate dateVaReceivedDocument = currentDate;//.plusDays(1);
		metadata.setDateVaReceivedDocument(dateVaReceivedDocument);
		metadata.documentTypeId(documentTypeId);
		metadata.setFileNumber(fileNumber);
		metadata.setUploadFileName(fileName);
		metadata.setContentSource(contentSource);
		metadata.setSubject(subject);
		//metadata.setSubject("RBPS");
		//metadata.setEndProductCode("130DPNDCY");
		return metadata;
	}
	
}
