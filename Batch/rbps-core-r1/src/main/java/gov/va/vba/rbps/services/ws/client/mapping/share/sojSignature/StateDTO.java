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
 * <p>Java class for stateDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="stateDTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="commonLawStateInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="legacyFipsStateCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="postalCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="stateInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "stateDTO", propOrder = {
    "commonLawStateInd",
    "legacyFipsStateCd",
    "nm",
    "postalCd",
    "stateInd"
})
public class StateDTO {

    protected String commonLawStateInd;
    protected String legacyFipsStateCd;
    protected String nm;
    protected String postalCd;
    protected String stateInd;

    /**
     * Gets the value of the commonLawStateInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCommonLawStateInd() {
        return commonLawStateInd;
    }

    /**
     * Sets the value of the commonLawStateInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCommonLawStateInd(String value) {
        this.commonLawStateInd = value;
    }

    /**
     * Gets the value of the legacyFipsStateCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLegacyFipsStateCd() {
        return legacyFipsStateCd;
    }

    /**
     * Sets the value of the legacyFipsStateCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLegacyFipsStateCd(String value) {
        this.legacyFipsStateCd = value;
    }

    /**
     * Gets the value of the nm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNm() {
        return nm;
    }

    /**
     * Sets the value of the nm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNm(String value) {
        this.nm = value;
    }

    /**
     * Gets the value of the postalCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPostalCd() {
        return postalCd;
    }

    /**
     * Sets the value of the postalCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPostalCd(String value) {
        this.postalCd = value;
    }

    /**
     * Gets the value of the stateInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStateInd() {
        return stateInd;
    }

    /**
     * Sets the value of the stateInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStateInd(String value) {
        this.stateInd = value;
    }

}
