//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.04.05 at 09:43:49 AM CDT 
//


package gov.va.vba.rbps.services.ws.client.mapping.share.sojSignature;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for stnPrfilDetailDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="stnPrfilDetailDTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="letterSigntrTitleTxt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="letterSigntrTxt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "stnPrfilDetailDTO", propOrder = {
    "letterSigntrTitleTxt",
    "letterSigntrTxt"
})
public class StnPrfilDetailDTO {

    protected String letterSigntrTitleTxt;
    protected String letterSigntrTxt;

    /**
     * Gets the value of the letterSigntrTitleTxt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLetterSigntrTitleTxt() {
        return letterSigntrTitleTxt;
    }

    /**
     * Sets the value of the letterSigntrTitleTxt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLetterSigntrTitleTxt(String value) {
        this.letterSigntrTitleTxt = value;
    }

    /**
     * Gets the value of the letterSigntrTxt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLetterSigntrTxt() {
        return letterSigntrTxt;
    }

    /**
     * Sets the value of the letterSigntrTxt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLetterSigntrTxt(String value) {
        this.letterSigntrTxt = value;
    }

}
