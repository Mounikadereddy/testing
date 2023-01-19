//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.09.22 at 01:21:30 PM EDT 
//


package gov.va.vba.rbps.services.ws.client.mapping.awardDoc.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ancillaryRatingDecnVO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ancillaryRatingDecnVO"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://gov.va.vba.benefits.awards.ws/services}voBase"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ancillaryDecisionType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ancillaryDecisionTypeDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="beginDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="convertedBeginDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="convertedEndDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="deprecatedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="endDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="modifiedAction" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="modifiedBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="modifiedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="modifiedLocation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="modifiedProcess" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="participantDecisionID" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *         &lt;element name="prevRatingSequenceNumber" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *         &lt;element name="profileDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="ratingDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="ratingSequenceNumber" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *         &lt;element name="rbaIssueID" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *         &lt;element name="supplementalDecisionType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="supplementalDecisionTypeDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="veteranID" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ancillaryRatingDecnVO", propOrder = {
    "ancillaryDecisionType",
    "ancillaryDecisionTypeDescription",
    "beginDate",
    "convertedBeginDate",
    "convertedEndDate",
    "deprecatedDate",
    "endDate",
    "modifiedAction",
    "modifiedBy",
    "modifiedDate",
    "modifiedLocation",
    "modifiedProcess",
    "participantDecisionID",
    "prevRatingSequenceNumber",
    "profileDate",
    "ratingDate",
    "ratingSequenceNumber",
    "rbaIssueID",
    "supplementalDecisionType",
    "supplementalDecisionTypeDescription",
    "veteranID"
})
public class AncillaryRatingDecnVO
    extends VoBase
{

    protected String ancillaryDecisionType;
    protected String ancillaryDecisionTypeDescription;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar beginDate;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar convertedBeginDate;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar convertedEndDate;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar deprecatedDate;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar endDate;
    protected String modifiedAction;
    protected String modifiedBy;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar modifiedDate;
    protected String modifiedLocation;
    protected String modifiedProcess;
    protected Long participantDecisionID;
    protected Long prevRatingSequenceNumber;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar profileDate;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar ratingDate;
    protected Long ratingSequenceNumber;
    protected Long rbaIssueID;
    protected String supplementalDecisionType;
    protected String supplementalDecisionTypeDescription;
    protected Long veteranID;

    /**
     * Gets the value of the ancillaryDecisionType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAncillaryDecisionType() {
        return ancillaryDecisionType;
    }

    /**
     * Sets the value of the ancillaryDecisionType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAncillaryDecisionType(String value) {
        this.ancillaryDecisionType = value;
    }

    /**
     * Gets the value of the ancillaryDecisionTypeDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAncillaryDecisionTypeDescription() {
        return ancillaryDecisionTypeDescription;
    }

    /**
     * Sets the value of the ancillaryDecisionTypeDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAncillaryDecisionTypeDescription(String value) {
        this.ancillaryDecisionTypeDescription = value;
    }

    /**
     * Gets the value of the beginDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getBeginDate() {
        return beginDate;
    }

    /**
     * Sets the value of the beginDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setBeginDate(XMLGregorianCalendar value) {
        this.beginDate = value;
    }

    /**
     * Gets the value of the convertedBeginDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getConvertedBeginDate() {
        return convertedBeginDate;
    }

    /**
     * Sets the value of the convertedBeginDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setConvertedBeginDate(XMLGregorianCalendar value) {
        this.convertedBeginDate = value;
    }

    /**
     * Gets the value of the convertedEndDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getConvertedEndDate() {
        return convertedEndDate;
    }

    /**
     * Sets the value of the convertedEndDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setConvertedEndDate(XMLGregorianCalendar value) {
        this.convertedEndDate = value;
    }

    /**
     * Gets the value of the deprecatedDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDeprecatedDate() {
        return deprecatedDate;
    }

    /**
     * Sets the value of the deprecatedDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDeprecatedDate(XMLGregorianCalendar value) {
        this.deprecatedDate = value;
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
     * Gets the value of the modifiedAction property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModifiedAction() {
        return modifiedAction;
    }

    /**
     * Sets the value of the modifiedAction property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModifiedAction(String value) {
        this.modifiedAction = value;
    }

    /**
     * Gets the value of the modifiedBy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModifiedBy() {
        return modifiedBy;
    }

    /**
     * Sets the value of the modifiedBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModifiedBy(String value) {
        this.modifiedBy = value;
    }

    /**
     * Gets the value of the modifiedDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getModifiedDate() {
        return modifiedDate;
    }

    /**
     * Sets the value of the modifiedDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setModifiedDate(XMLGregorianCalendar value) {
        this.modifiedDate = value;
    }

    /**
     * Gets the value of the modifiedLocation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModifiedLocation() {
        return modifiedLocation;
    }

    /**
     * Sets the value of the modifiedLocation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModifiedLocation(String value) {
        this.modifiedLocation = value;
    }

    /**
     * Gets the value of the modifiedProcess property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModifiedProcess() {
        return modifiedProcess;
    }

    /**
     * Sets the value of the modifiedProcess property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModifiedProcess(String value) {
        this.modifiedProcess = value;
    }

    /**
     * Gets the value of the participantDecisionID property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getParticipantDecisionID() {
        return participantDecisionID;
    }

    /**
     * Sets the value of the participantDecisionID property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setParticipantDecisionID(Long value) {
        this.participantDecisionID = value;
    }

    /**
     * Gets the value of the prevRatingSequenceNumber property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getPrevRatingSequenceNumber() {
        return prevRatingSequenceNumber;
    }

    /**
     * Sets the value of the prevRatingSequenceNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setPrevRatingSequenceNumber(Long value) {
        this.prevRatingSequenceNumber = value;
    }

    /**
     * Gets the value of the profileDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getProfileDate() {
        return profileDate;
    }

    /**
     * Sets the value of the profileDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setProfileDate(XMLGregorianCalendar value) {
        this.profileDate = value;
    }

    /**
     * Gets the value of the ratingDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getRatingDate() {
        return ratingDate;
    }

    /**
     * Sets the value of the ratingDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setRatingDate(XMLGregorianCalendar value) {
        this.ratingDate = value;
    }

    /**
     * Gets the value of the ratingSequenceNumber property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getRatingSequenceNumber() {
        return ratingSequenceNumber;
    }

    /**
     * Sets the value of the ratingSequenceNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setRatingSequenceNumber(Long value) {
        this.ratingSequenceNumber = value;
    }

    /**
     * Gets the value of the rbaIssueID property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getRbaIssueID() {
        return rbaIssueID;
    }

    /**
     * Sets the value of the rbaIssueID property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setRbaIssueID(Long value) {
        this.rbaIssueID = value;
    }

    /**
     * Gets the value of the supplementalDecisionType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSupplementalDecisionType() {
        return supplementalDecisionType;
    }

    /**
     * Sets the value of the supplementalDecisionType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSupplementalDecisionType(String value) {
        this.supplementalDecisionType = value;
    }

    /**
     * Gets the value of the supplementalDecisionTypeDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSupplementalDecisionTypeDescription() {
        return supplementalDecisionTypeDescription;
    }

    /**
     * Sets the value of the supplementalDecisionTypeDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSupplementalDecisionTypeDescription(String value) {
        this.supplementalDecisionTypeDescription = value;
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
