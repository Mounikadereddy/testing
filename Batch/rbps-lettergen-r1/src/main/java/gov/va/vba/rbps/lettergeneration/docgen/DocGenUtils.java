package gov.va.vba.rbps.lettergeneration.docgen;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.text.StrSubstitutor;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Map;

public class DocGenUtils {
	public String getJwt() throws IOException {
		// Fortify High issue fix to close the inputStream
		try (InputStream tokenIs = DocGenUtils.class.getResourceAsStream("token.csv")) {
			StringWriter writer = new StringWriter();
			IOUtils.copy(tokenIs, writer);
			return writer.toString();
		}
	}

    public static String getMessage(String strTemplate, Map<String, String> values) {
        StrSubstitutor sub = new StrSubstitutor(values, "%(", ")");
        return sub.replace(strTemplate);
    }

}
