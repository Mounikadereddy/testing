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
 * <p>Java class for findBenefitClaimTypeIncrement complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="findBenefitClaimTypeIncrement">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ptcpntId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bnftClaimTypeCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="pgmTypeCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "findBenefitClaimTypeIncrement", propOrder = {
    "ptcpntId",
    "bnftClaimTypeCd",
    "pgmTypeCd"
})
public class FindBenefitClaimTypeIncrement {

    protected String ptcpntId;
    protected String bnftClaimTypeCd;
    protected String pgmTypeCd;

    /**
     * Gets the value of the ptcpntId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPtcpntId() {
        return ptcpntId;
    }

    /**
     * Sets the value of the ptcpntId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPtcpntId(String value) {
        this.ptcpntId = value;
    }

    /**
     * Gets the value of the bnftClaimTypeCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBnftClaimTypeCd() {
        return bnftClaimTypeCd;
    }

    /**
     * Sets the value of the bnftClaimTypeCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBnftClaimTypeCd(String value) {
        this.bnftClaimTypeCd = value;
    }

    /**
     * Gets the value of the pgmTypeCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPgmTypeCd() {
        return pgmTypeCd;
    }

    /**
     * Sets the value of the pgmTypeCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPgmTypeCd(String value) {
        this.pgmTypeCd = value;
    }

}
