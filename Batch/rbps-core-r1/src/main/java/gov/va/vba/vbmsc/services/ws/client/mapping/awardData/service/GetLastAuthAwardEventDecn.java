//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2022.02.11 at 09:35:49 AM EST 
//


package gov.va.vba.vbmsc.services.ws.client.mapping.awardData.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getLastAuthAwardEventDecn complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getLastAuthAwardEventDecn"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="vetId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *         &lt;element name="beneId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *         &lt;element name="awardTypeCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="debug" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getLastAuthAwardEventDecn", propOrder = {
    "vetId",
    "beneId",
    "awardTypeCd",
    "debug"
})
public class GetLastAuthAwardEventDecn {

    protected Long vetId;
    protected Long beneId;
    protected String awardTypeCd;
    protected String debug;

    /**
     * Gets the value of the vetId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getVetId() {
        return vetId;
    }

    /**
     * Sets the value of the vetId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setVetId(Long value) {
        this.vetId = value;
    }

    /**
     * Gets the value of the beneId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getBeneId() {
        return beneId;
    }

    /**
     * Sets the value of the beneId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setBeneId(Long value) {
        this.beneId = value;
    }

    /**
     * Gets the value of the awardTypeCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAwardTypeCd() {
        return awardTypeCd;
    }

    /**
     * Sets the value of the awardTypeCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAwardTypeCd(String value) {
        this.awardTypeCd = value;
    }

    /**
     * Gets the value of the debug property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDebug() {
        return debug;
    }

    /**
     * Sets the value of the debug property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDebug(String value) {
        this.debug = value;
    }

}
