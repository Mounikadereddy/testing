//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2022.02.11 at 09:35:49 AM EST 
//


package gov.va.vba.vbmsc.services.ws.client.mapping.awardData.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for poaDetailVO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="poaDetailVO"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="authorizationChangeClaimantAddressIndicator" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="authorizationPoaAccessIndicator" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="effectiveDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="endDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="participantRelationshipTypeName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="phoneNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="poaName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="poaParticipantID" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *         &lt;element name="poaTypeDesc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="veteranID" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "poaDetailVO", propOrder = {
    "authorizationChangeClaimantAddressIndicator",
    "authorizationPoaAccessIndicator",
    "effectiveDate",
    "endDate",
    "participantRelationshipTypeName",
    "phoneNumber",
    "poaName",
    "poaParticipantID",
    "poaTypeDesc",
    "veteranID"
})
@XmlSeeAlso({
    HPoaDetailVO.class
})
public class PoaDetailVO {

    protected String authorizationChangeClaimantAddressIndicator;
    protected String authorizationPoaAccessIndicator;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar effectiveDate;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar endDate;
    protected String participantRelationshipTypeName;
    protected String phoneNumber;
    protected String poaName;
    protected Long poaParticipantID;
    protected String poaTypeDesc;
    protected Long veteranID;

    /**
     * Gets the value of the authorizationChangeClaimantAddressIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthorizationChangeClaimantAddressIndicator() {
        return authorizationChangeClaimantAddressIndicator;
    }

    /**
     * Sets the value of the authorizationChangeClaimantAddressIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthorizationChangeClaimantAddressIndicator(String value) {
        this.authorizationChangeClaimantAddressIndicator = value;
    }

    /**
     * Gets the value of the authorizationPoaAccessIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthorizationPoaAccessIndicator() {
        return authorizationPoaAccessIndicator;
    }

    /**
     * Sets the value of the authorizationPoaAccessIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthorizationPoaAccessIndicator(String value) {
        this.authorizationPoaAccessIndicator = value;
    }

    /**
     * Gets the value of the effectiveDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEffectiveDate() {
        return effectiveDate;
    }

    /**
     * Sets the value of the effectiveDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEffectiveDate(XMLGregorianCalendar value) {
        this.effectiveDate = value;
    }

    /**
     * Gets the value of the endDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEndDate() {
        return endDate;
    }

    /**
     * Sets the value of the endDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEndDate(XMLGregorianCalendar value) {
        this.endDate = value;
    }

    /**
     * Gets the value of the participantRelationshipTypeName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParticipantRelationshipTypeName() {
        return participantRelationshipTypeName;
    }

    /**
     * Sets the value of the participantRelationshipTypeName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParticipantRelationshipTypeName(String value) {
        this.participantRelationshipTypeName = value;
    }

    /**
     * Gets the value of the phoneNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the value of the phoneNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPhoneNumber(String value) {
        this.phoneNumber = value;
    }

    /**
     * Gets the value of the poaName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPoaName() {
        return poaName;
    }

    /**
     * Sets the value of the poaName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPoaName(String value) {
        this.poaName = value;
    }

    /**
     * Gets the value of the poaParticipantID property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getPoaParticipantID() {
        return poaParticipantID;
    }

    /**
     * Sets the value of the poaParticipantID property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setPoaParticipantID(Long value) {
        this.poaParticipantID = value;
    }

    /**
     * Gets the value of the poaTypeDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPoaTypeDesc() {
        return poaTypeDesc;
    }

    /**
     * Sets the value of the poaTypeDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPoaTypeDesc(String value) {
        this.poaTypeDesc = value;
    }

    /**
     * Gets the value of the veteranID property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getVeteranID() {
        return veteranID;
    }

    /**
     * Sets the value of the veteranID property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setVeteranID(Long value) {
        this.veteranID = value;
    }

}
