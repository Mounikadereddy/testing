package gov.va.vba.rbps.lettergeneration;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SavePdfFileForFtpTest {

    private SavePdfFileForFtp savePdfFileForFtp = new SavePdfFileForFtp();

    @Test
    public void getNumberOfPages() {
        int numPages = savePdfFileForFtp.getNumberOfPages("gov/va/vba/rbps/lettergeneration/resources/testLetter.pdf");
        assertEquals(6, numPages);
    }
}