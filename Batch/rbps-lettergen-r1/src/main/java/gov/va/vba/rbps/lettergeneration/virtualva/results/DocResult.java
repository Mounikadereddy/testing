/*
 * DocResult.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.lettergeneration.virtualva.results;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;


/*
<originalFilename>459_2661772911016419-00GEN_S.pdf</originalFilename>
<hpiiBatchNm>110507081443731</hpiiBatchNm>
<documentId>15080004</documentId>
<fnDcmntNbr>287105001</fnDcmntNbr>
<rcvdDt>2011-05-06 00:00:00.0</rcvdDt>
<phasecode>I</phasecode>
<stepcode>I</stepcode>
<statuscode>S</statuscode>
*/
@XmlRootElement( name="doc" )
public class DocResult {

    private String      originalFileName;

    private String      hpiiBatchNm;
    private String      documentId;
    private String      fnDcmntNbr;
    private Date        rcvdDt;
    private String      phasecode;
    private String      stepcode;
    private String      statuscode;












    @XmlElement( name = "originalFilename" )
    public String getOriginalFileName() {

        return originalFileName;
    }

    public void setOriginalFileName( final String originalFileName ) {

        this.originalFileName = originalFileName;
    }

    public String getHpiiBatchNm() {

        return hpiiBatchNm;
    }

    public void setHpiiBatchNm( final String hpiiBatchNm ) {

        this.hpiiBatchNm = hpiiBatchNm;
    }

    public String getDocumentId() {

        return documentId;
    }

    public void setDocumentId( final String documentId ) {

        this.documentId = documentId;
    }

    public String getFnDcmntNbr() {

        return fnDcmntNbr;
    }

    public void setFnDcmntNbr( final String fnDcmntNbr ) {

        this.fnDcmntNbr = fnDcmntNbr;
    }

    public Date getRcvdDt() {

        return rcvdDt;
    }

    public void setRcvdDt( final Date rcvdDt ) {

        this.rcvdDt = rcvdDt;
    }

    public String getPhasecode() {

        return phasecode;
    }

    public void setPhasecode( final String phasecode ) {

        this.phasecode = phasecode;
    }

    public String getStepcode() {

        return stepcode;
    }

    public void setStepcode( final String stepcode ) {

        this.stepcode = stepcode;
    }

    public String getStatuscode() {

        return statuscode;
    }

    public void setStatuscode( final String statuscode ) {

        this.statuscode = statuscode;
    }


    @Override
    public String toString() {
        return new ToStringBuilder( this )
            .append( "originalFilename",        originalFileName )
            .append( "hpiiBatchNm",             hpiiBatchNm )
            .append( "documentId",              documentId )
            .append( "fnDcmntNbr",              fnDcmntNbr )
            .append( "rcvdDt",                  rcvdDt )
            .append( "phasecode",               phasecode )
            .append( "stepcode",                stepcode )
            .append( "statuscode",              statuscode )
            .toString();
    }
}
