//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2022.02.11 at 09:35:49 AM EST 
//


package gov.va.vba.vbmsc.services.ws.client.mapping.awardData.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getRatingSummary complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getRatingSummary"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="RatingSumryId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getRatingSummary", propOrder = {
    "ratingSumryId"
})
public class GetRatingSummary {

    @XmlElement(name = "RatingSumryId")
    protected Long ratingSumryId;

    /**
     * Gets the value of the ratingSumryId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getRatingSumryId() {
        return ratingSumryId;
    }

    /**
     * Sets the value of the ratingSumryId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setRatingSumryId(Long value) {
        this.ratingSumryId = value;
    }

}
