//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.10.04 at 04:43:13 PM CDT 
//


package gov.va.vba.rbps.services.ws.client.mapping.awards.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for findCurrentAndProposedAward complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="findCurrentAndProposedAward">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://gov.va.vba.benefits.awards.ws/data}AwardKeyInput" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "findCurrentAndProposedAward", propOrder = {
    "awardKeyInput"
})
public class FindCurrentAndProposedAward {

    @XmlElement(name = "AwardKeyInput", namespace = "http://gov.va.vba.benefits.awards.ws/data")
    protected AwardKeyInputVO awardKeyInput;

    /**
     * Gets the value of the awardKeyInput property.
     * 
     * @return
     *     possible object is
     *     {@link AwardKeyInputVO }
     *     
     */
    public AwardKeyInputVO getAwardKeyInput() {
        return awardKeyInput;
    }

    /**
     * Sets the value of the awardKeyInput property.
     * 
     * @param value
     *     allowed object is
     *     {@link AwardKeyInputVO }
     *     
     */
    public void setAwardKeyInput(AwardKeyInputVO value) {
        this.awardKeyInput = value;
    }

}
