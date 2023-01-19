package gov.va.vba.rbps.lettergeneration.docgen.genstore.service;
import gov.va.bip.docgen.service.plugin.awards.api.edoc.EdocDocument;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.lettergeneration.LetterFieldsMarshal;
import gov.va.vba.rbps.lettergeneration.docgen.LetterType;

public interface RbpsDocGenGenstoreService {
    /**
     * @param letterFields LetterFieldsMarshal object
     * @param letterType type of letter to generate
     * @param fileName name of generated pdf
     */
    void generateDocumentAndSave(RbpsRepository repo, LetterType letterType,
    		LetterFieldsMarshal letterFields, String fileName);
    
   	void generateAwardPrintAndSave(RbpsRepository repo,EdocDocument edocDocumentRequest, String fileName, String fileNumber);
}
