//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.03.09 at 11:40:32 AM CST 
//


package gov.va.vba.rbps.services.ws.client.mapping.awards.amend;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for documentVO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="documentVO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="awardEventID" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="documentType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="letterText" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "documentVO", propOrder = {
    "awardEventID",
    "documentType",
    "letterText"
})
public class DocumentVO {

    protected Long awardEventID;
    protected String documentType;
    protected byte[] letterText;

    /**
     * Gets the value of the awardEventID property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getAwardEventID() {
        return awardEventID;
    }

    /**
     * Sets the value of the awardEventID property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setAwardEventID(Long value) {
        this.awardEventID = value;
    }

    /**
     * Gets the value of the documentType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocumentType() {
        return documentType;
    }

    /**
     * Sets the value of the documentType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocumentType(String value) {
        this.documentType = value;
    }

    /**
     * Gets the value of the letterText property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getLetterText() {
        return letterText;
    }

    /**
     * Sets the value of the letterText property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setLetterText(byte[] value) {
        this.letterText = ((byte[]) value);
    }

}
