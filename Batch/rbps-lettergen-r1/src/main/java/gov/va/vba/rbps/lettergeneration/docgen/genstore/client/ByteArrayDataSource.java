package gov.va.vba.rbps.lettergeneration.docgen.genstore.client;

import javax.activation.DataSource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ByteArrayDataSource implements DataSource {
    private final byte[]  byteArray;
    private final String contentType;
    private final String name;

    public ByteArrayDataSource(byte[] byteArray, String contentType, String name) {
        this.byteArray = byteArray;
        this.contentType = contentType;
        this.name = name;
    }

    @Override
    public InputStream getInputStream() {
        return new ByteArrayInputStream(this.byteArray == null ? new byte[0] : this.byteArray);
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        throw new UnsupportedOperationException("Operation not supported: getOutputStream");
    }

    @Override
    public String getContentType() {
        return this.contentType;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
