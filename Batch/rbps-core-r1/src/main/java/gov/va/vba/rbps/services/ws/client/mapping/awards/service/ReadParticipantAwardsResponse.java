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
 * <p>Java class for readParticipantAwardsResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="readParticipantAwardsResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://gov.va.vba.benefits.awards.ws/data}ParticipantAwardsResponse" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "readParticipantAwardsResponse", propOrder = {
    "participantAwardsResponse"
})
public class ReadParticipantAwardsResponse {

    @XmlElement(name = "ParticipantAwardsResponse", namespace = "http://gov.va.vba.benefits.awards.ws/data")
    protected ParticipantAwardsResponse participantAwardsResponse;

    /**
     * Gets the value of the participantAwardsResponse property.
     * 
     * @return
     *     possible object is
     *     {@link ParticipantAwardsResponse }
     *     
     */
    public ParticipantAwardsResponse getParticipantAwardsResponse() {
        return participantAwardsResponse;
    }

    /**
     * Sets the value of the participantAwardsResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link ParticipantAwardsResponse }
     *     
     */
    public void setParticipantAwardsResponse(ParticipantAwardsResponse value) {
        this.participantAwardsResponse = value;
    }

}
