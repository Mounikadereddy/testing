
package gov.va.vba.rbps.services.ws.client.mapping.awards.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for participantAddressVO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="participantAddressVO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://gov.va.vba.benefits.awards.ws/services}voBase">
 *       &lt;sequence>
 *         &lt;element name="OFACIndicator" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="addressLineOne" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="addressLineThree" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="addressLineTwo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="awardType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="badAddressIndicator" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cityName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="countryName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="countyName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="createdByApplicationName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="effectiveDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="electronicFundsTransferWaiver" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="emailAddress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="endDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="fiduciaryPtcpntID" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="fiduciaryPtcpntName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="foreignPostalCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mailStop" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="militaryPostOfficeType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="militaryPostalType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="modifiedAction" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="modifiedBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="modifiedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="modifiedLocation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="modifiedProcess" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="participantAddressID" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="participantAddressTypeDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="participantID" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="participantMailingAddressID" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="participantName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="postalCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="prepositionalTypeDesc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="provinceName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ptcpntFirstLastName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="territoryName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="treasuryAddressLineFive" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="treasuryAddressLineFour" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="treasuryAddressLineOne" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="treasuryAddressLineSix" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="treasuryAddressLineThree" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="treasuryAddressLineTwo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="treasuryForeignCountryTypeDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="treasurySequenceNumber" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="zipFirstSuffix" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="zipPrefix" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="zipSecondSuffix" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "participantAddressVO", propOrder = {
    "ofacIndicator",
    "addressLineOne",
    "addressLineThree",
    "addressLineTwo",
    "awardType",
    "badAddressIndicator",
    "cityName",
    "countryName",
    "countyName",
    "createdByApplicationName",
    "effectiveDate",
    "electronicFundsTransferWaiver",
    "emailAddress",
    "endDate",
    "fiduciaryPtcpntID",
    "fiduciaryPtcpntName",
    "foreignPostalCode",
    "mailStop",
    "militaryPostOfficeType",
    "militaryPostalType",
    "modifiedAction",
    "modifiedBy",
    "modifiedDate",
    "modifiedLocation",
    "modifiedProcess",
    "participantAddressID",
    "participantAddressTypeDescription",
    "participantID",
    "participantMailingAddressID",
    "participantName",
    "postalCode",
    "prepositionalTypeDesc",
    "provinceName",
    "ptcpntFirstLastName",
    "territoryName",
    "treasuryAddressLineFive",
    "treasuryAddressLineFour",
    "treasuryAddressLineOne",
    "treasuryAddressLineSix",
    "treasuryAddressLineThree",
    "treasuryAddressLineTwo",
    "treasuryForeignCountryTypeDescription",
    "treasurySequenceNumber",
    "zipFirstSuffix",
    "zipPrefix",
    "zipSecondSuffix"
})
public class ParticipantAddressVO
    extends VoBase
{

    @XmlElement(name = "OFACIndicator")
    protected String ofacIndicator;
    protected String addressLineOne;
    protected String addressLineThree;
    protected String addressLineTwo;
    protected String awardType;
    protected String badAddressIndicator;
    protected String cityName;
    protected String countryName;
    protected String countyName;
    protected String createdByApplicationName;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar effectiveDate;
    protected String electronicFundsTransferWaiver;
    protected String emailAddress;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar endDate;
    protected Long fiduciaryPtcpntID;
    protected String fiduciaryPtcpntName;
    protected String foreignPostalCode;
    protected String mailStop;
    protected String militaryPostOfficeType;
    protected String militaryPostalType;
    protected String modifiedAction;
    protected String modifiedBy;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar modifiedDate;
    protected String modifiedLocation;
    protected String modifiedProcess;
    protected Long participantAddressID;
    protected String participantAddressTypeDescription;
    protected Long participantID;
    protected Long participantMailingAddressID;
    protected String participantName;
    protected String postalCode;
    protected String prepositionalTypeDesc;
    protected String provinceName;
    protected String ptcpntFirstLastName;
    protected String territoryName;
    protected String treasuryAddressLineFive;
    protected String treasuryAddressLineFour;
    protected String treasuryAddressLineOne;
    protected String treasuryAddressLineSix;
    protected String treasuryAddressLineThree;
    protected String treasuryAddressLineTwo;
    protected String treasuryForeignCountryTypeDescription;
    protected Integer treasurySequenceNumber;
    protected String zipFirstSuffix;
    protected String zipPrefix;
    protected String zipSecondSuffix;

    /**
     * Gets the value of the ofacIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOFACIndicator() {
        return ofacIndicator;
    }

    /**
     * Sets the value of the ofacIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOFACIndicator(String value) {
        this.ofacIndicator = value;
    }

    /**
     * Gets the value of the addressLineOne property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddressLineOne() {
        return addressLineOne;
    }

    /**
     * Sets the value of the addressLineOne property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddressLineOne(String value) {
        this.addressLineOne = value;
    }

    /**
     * Gets the value of the addressLineThree property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddressLineThree() {
        return addressLineThree;
    }

    /**
     * Sets the value of the addressLineThree property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddressLineThree(String value) {
        this.addressLineThree = value;
    }

    /**
     * Gets the value of the addressLineTwo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddressLineTwo() {
        return addressLineTwo;
    }

    /**
     * Sets the value of the addressLineTwo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddressLineTwo(String value) {
        this.addressLineTwo = value;
    }

    /**
     * Gets the value of the awardType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAwardType() {
        return awardType;
    }

    /**
     * Sets the value of the awardType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAwardType(String value) {
        this.awardType = value;
    }

    /**
     * Gets the value of the badAddressIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBadAddressIndicator() {
        return badAddressIndicator;
    }

    /**
     * Sets the value of the badAddressIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBadAddressIndicator(String value) {
        this.badAddressIndicator = value;
    }

    /**
     * Gets the value of the cityName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCityName() {
        return cityName;
    }

    /**
     * Sets the value of the cityName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCityName(String value) {
        this.cityName = value;
    }

    /**
     * Gets the value of the countryName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * Sets the value of the countryName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCountryName(String value) {
        this.countryName = value;
    }

    /**
     * Gets the value of the countyName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCountyName() {
        return countyName;
    }

    /**
     * Sets the value of the countyName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCountyName(String value) {
        this.countyName = value;
    }

    /**
     * Gets the value of the createdByApplicationName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreatedByApplicationName() {
        return createdByApplicationName;
    }

    /**
     * Sets the value of the createdByApplicationName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreatedByApplicationName(String value) {
        this.createdByApplicationName = value;
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
     * Gets the value of the electronicFundsTransferWaiver property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getElectronicFundsTransferWaiver() {
        return electronicFundsTransferWaiver;
    }

    /**
     * Sets the value of the electronicFundsTransferWaiver property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setElectronicFundsTransferWaiver(String value) {
        this.electronicFundsTransferWaiver = value;
    }

    /**
     * Gets the value of the emailAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * Sets the value of the emailAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmailAddress(String value) {
        this.emailAddress = value;
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
     * Gets the value of the fiduciaryPtcpntID property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getFiduciaryPtcpntID() {
        return fiduciaryPtcpntID;
    }

    /**
     * Sets the value of the fiduciaryPtcpntID property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setFiduciaryPtcpntID(Long value) {
        this.fiduciaryPtcpntID = value;
    }

    /**
     * Gets the value of the fiduciaryPtcpntName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFiduciaryPtcpntName() {
        return fiduciaryPtcpntName;
    }

    /**
     * Sets the value of the fiduciaryPtcpntName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFiduciaryPtcpntName(String value) {
        this.fiduciaryPtcpntName = value;
    }

    /**
     * Gets the value of the foreignPostalCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getForeignPostalCode() {
        return foreignPostalCode;
    }

    /**
     * Sets the value of the foreignPostalCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setForeignPostalCode(String value) {
        this.foreignPostalCode = value;
    }

    /**
     * Gets the value of the mailStop property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMailStop() {
        return mailStop;
    }

    /**
     * Sets the value of the mailStop property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMailStop(String value) {
        this.mailStop = value;
    }

    /**
     * Gets the value of the militaryPostOfficeType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMilitaryPostOfficeType() {
        return militaryPostOfficeType;
    }

    /**
     * Sets the value of the militaryPostOfficeType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMilitaryPostOfficeType(String value) {
        this.militaryPostOfficeType = value;
    }

    /**
     * Gets the value of the militaryPostalType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMilitaryPostalType() {
        return militaryPostalType;
    }

    /**
     * Sets the value of the militaryPostalType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMilitaryPostalType(String value) {
        this.militaryPostalType = value;
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
     * Gets the value of the participantAddressID property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getParticipantAddressID() {
        return participantAddressID;
    }

    /**
     * Sets the value of the participantAddressID property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setParticipantAddressID(Long value) {
        this.participantAddressID = value;
    }

    /**
     * Gets the value of the participantAddressTypeDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParticipantAddressTypeDescription() {
        return participantAddressTypeDescription;
    }

    /**
     * Sets the value of the participantAddressTypeDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParticipantAddressTypeDescription(String value) {
        this.participantAddressTypeDescription = value;
    }

    /**
     * Gets the value of the participantID property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getParticipantID() {
        return participantID;
    }

    /**
     * Sets the value of the participantID property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setParticipantID(Long value) {
        this.participantID = value;
    }

    /**
     * Gets the value of the participantMailingAddressID property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getParticipantMailingAddressID() {
        return participantMailingAddressID;
    }

    /**
     * Sets the value of the participantMailingAddressID property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setParticipantMailingAddressID(Long value) {
        this.participantMailingAddressID = value;
    }

    /**
     * Gets the value of the participantName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParticipantName() {
        return participantName;
    }

    /**
     * Sets the value of the participantName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParticipantName(String value) {
        this.participantName = value;
    }

    /**
     * Gets the value of the postalCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Sets the value of the postalCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPostalCode(String value) {
        this.postalCode = value;
    }

    /**
     * Gets the value of the prepositionalTypeDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrepositionalTypeDesc() {
        return prepositionalTypeDesc;
    }

    /**
     * Sets the value of the prepositionalTypeDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrepositionalTypeDesc(String value) {
        this.prepositionalTypeDesc = value;
    }

    /**
     * Gets the value of the provinceName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProvinceName() {
        return provinceName;
    }

    /**
     * Sets the value of the provinceName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProvinceName(String value) {
        this.provinceName = value;
    }

    /**
     * Gets the value of the ptcpntFirstLastName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPtcpntFirstLastName() {
        return ptcpntFirstLastName;
    }

    /**
     * Sets the value of the ptcpntFirstLastName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPtcpntFirstLastName(String value) {
        this.ptcpntFirstLastName = value;
    }

    /**
     * Gets the value of the territoryName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTerritoryName() {
        return territoryName;
    }

    /**
     * Sets the value of the territoryName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTerritoryName(String value) {
        this.territoryName = value;
    }

    /**
     * Gets the value of the treasuryAddressLineFive property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTreasuryAddressLineFive() {
        return treasuryAddressLineFive;
    }

    /**
     * Sets the value of the treasuryAddressLineFive property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTreasuryAddressLineFive(String value) {
        this.treasuryAddressLineFive = value;
    }

    /**
     * Gets the value of the treasuryAddressLineFour property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTreasuryAddressLineFour() {
        return treasuryAddressLineFour;
    }

    /**
     * Sets the value of the treasuryAddressLineFour property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTreasuryAddressLineFour(String value) {
        this.treasuryAddressLineFour = value;
    }

    /**
     * Gets the value of the treasuryAddressLineOne property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTreasuryAddressLineOne() {
        return treasuryAddressLineOne;
    }

    /**
     * Sets the value of the treasuryAddressLineOne property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTreasuryAddressLineOne(String value) {
        this.treasuryAddressLineOne = value;
    }

    /**
     * Gets the value of the treasuryAddressLineSix property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTreasuryAddressLineSix() {
        return treasuryAddressLineSix;
    }

    /**
     * Sets the value of the treasuryAddressLineSix property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTreasuryAddressLineSix(String value) {
        this.treasuryAddressLineSix = value;
    }

    /**
     * Gets the value of the treasuryAddressLineThree property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTreasuryAddressLineThree() {
        return treasuryAddressLineThree;
    }

    /**
     * Sets the value of the treasuryAddressLineThree property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTreasuryAddressLineThree(String value) {
        this.treasuryAddressLineThree = value;
    }

    /**
     * Gets the value of the treasuryAddressLineTwo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTreasuryAddressLineTwo() {
        return treasuryAddressLineTwo;
    }

    /**
     * Sets the value of the treasuryAddressLineTwo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTreasuryAddressLineTwo(String value) {
        this.treasuryAddressLineTwo = value;
    }

    /**
     * Gets the value of the treasuryForeignCountryTypeDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTreasuryForeignCountryTypeDescription() {
        return treasuryForeignCountryTypeDescription;
    }

    /**
     * Sets the value of the treasuryForeignCountryTypeDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTreasuryForeignCountryTypeDescription(String value) {
        this.treasuryForeignCountryTypeDescription = value;
    }

    /**
     * Gets the value of the treasurySequenceNumber property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTreasurySequenceNumber() {
        return treasurySequenceNumber;
    }

    /**
     * Sets the value of the treasurySequenceNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTreasurySequenceNumber(Integer value) {
        this.treasurySequenceNumber = value;
    }

    /**
     * Gets the value of the zipFirstSuffix property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZipFirstSuffix() {
        return zipFirstSuffix;
    }

    /**
     * Sets the value of the zipFirstSuffix property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZipFirstSuffix(String value) {
        this.zipFirstSuffix = value;
    }

    /**
     * Gets the value of the zipPrefix property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZipPrefix() {
        return zipPrefix;
    }

    /**
     * Sets the value of the zipPrefix property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZipPrefix(String value) {
        this.zipPrefix = value;
    }

    /**
     * Gets the value of the zipSecondSuffix property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZipSecondSuffix() {
        return zipSecondSuffix;
    }

    /**
     * Sets the value of the zipSecondSuffix property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZipSecondSuffix(String value) {
        this.zipSecondSuffix = value;
    }

}
