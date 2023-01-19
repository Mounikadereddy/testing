//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.10.24 at 04:51:38 PM CDT 
//


package gov.va.vba.rbps.services.ws.client.mapping.vonapp.claimlabel;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for shrinqbcParticipantRecord complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="shrinqbcParticipantRecord">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="bddSiteName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="benefitClaimID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="benefitClaimReturnLabel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="claimPriorityIndicator" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="claimReceiveDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="claimStationOfJurisdiction" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="claimTemporaryStationOfJurisdiction" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="claimTypeCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="claimTypeName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="claimantFirstName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="claimantLastName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="claimantMiddleName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="claimantPersonOrOrganizationIndicator" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="claimantSuffix" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cpBenefitClaimID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cpClaimID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cpClaimReturnLabel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cpLocationID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="directDepositAccountID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="endProductTypeCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="informalIndicator" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="journalDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="journalObjectID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="journalStation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="journalStatusTypeCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="journalUserId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lastPaidDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mailingAddressID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numberOfBenefitClaimRecords" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numberOfCPClaimRecords" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numberOfRecords" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="organizationName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="organizationTitleTypeName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="participantClaimantID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="participantVetID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="payeeTypeCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="paymentAddressID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="programTypeCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="returnCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="returnMessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="selection" type="{http://services.share.benefits.vba.va.gov/}shrinqbcSelection" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="serviceTypeCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="statusTypeCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="vetFirstName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="vetLastName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="vetMiddleName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="vetSuffix" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "shrinqbcParticipantRecord", propOrder = {
    "bddSiteName",
    "benefitClaimID",
    "benefitClaimReturnLabel",
    "claimPriorityIndicator",
    "claimReceiveDate",
    "claimStationOfJurisdiction",
    "claimTemporaryStationOfJurisdiction",
    "claimTypeCode",
    "claimTypeName",
    "claimantFirstName",
    "claimantLastName",
    "claimantMiddleName",
    "claimantPersonOrOrganizationIndicator",
    "claimantSuffix",
    "cpBenefitClaimID",
    "cpClaimID",
    "cpClaimReturnLabel",
    "cpLocationID",
    "directDepositAccountID",
    "endProductTypeCode",
    "informalIndicator",
    "journalDate",
    "journalObjectID",
    "journalStation",
    "journalStatusTypeCode",
    "journalUserId",
    "lastPaidDate",
    "mailingAddressID",
    "numberOfBenefitClaimRecords",
    "numberOfCPClaimRecords",
    "numberOfRecords",
    "organizationName",
    "organizationTitleTypeName",
    "participantClaimantID",
    "participantVetID",
    "payeeTypeCode",
    "paymentAddressID",
    "programTypeCode",
    "returnCode",
    "returnMessage",
    "selection",
    "serviceTypeCode",
    "statusTypeCode",
    "vetFirstName",
    "vetLastName",
    "vetMiddleName",
    "vetSuffix"
})
public class ShrinqbcParticipantRecord {

    protected String bddSiteName;
    protected String benefitClaimID;
    protected String benefitClaimReturnLabel;
    protected String claimPriorityIndicator;
    protected String claimReceiveDate;
    protected String claimStationOfJurisdiction;
    protected String claimTemporaryStationOfJurisdiction;
    protected String claimTypeCode;
    protected String claimTypeName;
    protected String claimantFirstName;
    protected String claimantLastName;
    protected String claimantMiddleName;
    protected String claimantPersonOrOrganizationIndicator;
    protected String claimantSuffix;
    protected String cpBenefitClaimID;
    protected String cpClaimID;
    protected String cpClaimReturnLabel;
    protected String cpLocationID;
    protected String directDepositAccountID;
    protected String endProductTypeCode;
    protected String informalIndicator;
    protected String journalDate;
    protected String journalObjectID;
    protected String journalStation;
    protected String journalStatusTypeCode;
    protected String journalUserId;
    protected String lastPaidDate;
    protected String mailingAddressID;
    protected String numberOfBenefitClaimRecords;
    protected String numberOfCPClaimRecords;
    protected String numberOfRecords;
    protected String organizationName;
    protected String organizationTitleTypeName;
    protected String participantClaimantID;
    protected String participantVetID;
    protected String payeeTypeCode;
    protected String paymentAddressID;
    protected String programTypeCode;
    protected String returnCode;
    protected String returnMessage;
    @XmlElement(nillable = true)
    protected List<ShrinqbcSelection> selection;
    protected String serviceTypeCode;
    protected String statusTypeCode;
    protected String vetFirstName;
    protected String vetLastName;
    protected String vetMiddleName;
    protected String vetSuffix;

    /**
     * Gets the value of the bddSiteName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBddSiteName() {
        return bddSiteName;
    }

    /**
     * Sets the value of the bddSiteName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBddSiteName(String value) {
        this.bddSiteName = value;
    }

    /**
     * Gets the value of the benefitClaimID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBenefitClaimID() {
        return benefitClaimID;
    }

    /**
     * Sets the value of the benefitClaimID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBenefitClaimID(String value) {
        this.benefitClaimID = value;
    }

    /**
     * Gets the value of the benefitClaimReturnLabel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBenefitClaimReturnLabel() {
        return benefitClaimReturnLabel;
    }

    /**
     * Sets the value of the benefitClaimReturnLabel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBenefitClaimReturnLabel(String value) {
        this.benefitClaimReturnLabel = value;
    }

    /**
     * Gets the value of the claimPriorityIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClaimPriorityIndicator() {
        return claimPriorityIndicator;
    }

    /**
     * Sets the value of the claimPriorityIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClaimPriorityIndicator(String value) {
        this.claimPriorityIndicator = value;
    }

    /**
     * Gets the value of the claimReceiveDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClaimReceiveDate() {
        return claimReceiveDate;
    }

    /**
     * Sets the value of the claimReceiveDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClaimReceiveDate(String value) {
        this.claimReceiveDate = value;
    }

    /**
     * Gets the value of the claimStationOfJurisdiction property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClaimStationOfJurisdiction() {
        return claimStationOfJurisdiction;
    }

    /**
     * Sets the value of the claimStationOfJurisdiction property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClaimStationOfJurisdiction(String value) {
        this.claimStationOfJurisdiction = value;
    }

    /**
     * Gets the value of the claimTemporaryStationOfJurisdiction property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClaimTemporaryStationOfJurisdiction() {
        return claimTemporaryStationOfJurisdiction;
    }

    /**
     * Sets the value of the claimTemporaryStationOfJurisdiction property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClaimTemporaryStationOfJurisdiction(String value) {
        this.claimTemporaryStationOfJurisdiction = value;
    }

    /**
     * Gets the value of the claimTypeCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClaimTypeCode() {
        return claimTypeCode;
    }

    /**
     * Sets the value of the claimTypeCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClaimTypeCode(String value) {
        this.claimTypeCode = value;
    }

    /**
     * Gets the value of the claimTypeName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClaimTypeName() {
        return claimTypeName;
    }

    /**
     * Sets the value of the claimTypeName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClaimTypeName(String value) {
        this.claimTypeName = value;
    }

    /**
     * Gets the value of the claimantFirstName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClaimantFirstName() {
        return claimantFirstName;
    }

    /**
     * Sets the value of the claimantFirstName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClaimantFirstName(String value) {
        this.claimantFirstName = value;
    }

    /**
     * Gets the value of the claimantLastName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClaimantLastName() {
        return claimantLastName;
    }

    /**
     * Sets the value of the claimantLastName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClaimantLastName(String value) {
        this.claimantLastName = value;
    }

    /**
     * Gets the value of the claimantMiddleName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClaimantMiddleName() {
        return claimantMiddleName;
    }

    /**
     * Sets the value of the claimantMiddleName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClaimantMiddleName(String value) {
        this.claimantMiddleName = value;
    }

    /**
     * Gets the value of the claimantPersonOrOrganizationIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClaimantPersonOrOrganizationIndicator() {
        return claimantPersonOrOrganizationIndicator;
    }

    /**
     * Sets the value of the claimantPersonOrOrganizationIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClaimantPersonOrOrganizationIndicator(String value) {
        this.claimantPersonOrOrganizationIndicator = value;
    }

    /**
     * Gets the value of the claimantSuffix property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClaimantSuffix() {
        return claimantSuffix;
    }

    /**
     * Sets the value of the claimantSuffix property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClaimantSuffix(String value) {
        this.claimantSuffix = value;
    }

    /**
     * Gets the value of the cpBenefitClaimID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCpBenefitClaimID() {
        return cpBenefitClaimID;
    }

    /**
     * Sets the value of the cpBenefitClaimID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCpBenefitClaimID(String value) {
        this.cpBenefitClaimID = value;
    }

    /**
     * Gets the value of the cpClaimID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCpClaimID() {
        return cpClaimID;
    }

    /**
     * Sets the value of the cpClaimID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCpClaimID(String value) {
        this.cpClaimID = value;
    }

    /**
     * Gets the value of the cpClaimReturnLabel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCpClaimReturnLabel() {
        return cpClaimReturnLabel;
    }

    /**
     * Sets the value of the cpClaimReturnLabel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCpClaimReturnLabel(String value) {
        this.cpClaimReturnLabel = value;
    }

    /**
     * Gets the value of the cpLocationID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCpLocationID() {
        return cpLocationID;
    }

    /**
     * Sets the value of the cpLocationID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCpLocationID(String value) {
        this.cpLocationID = value;
    }

    /**
     * Gets the value of the directDepositAccountID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDirectDepositAccountID() {
        return directDepositAccountID;
    }

    /**
     * Sets the value of the directDepositAccountID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDirectDepositAccountID(String value) {
        this.directDepositAccountID = value;
    }

    /**
     * Gets the value of the endProductTypeCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEndProductTypeCode() {
        return endProductTypeCode;
    }

    /**
     * Sets the value of the endProductTypeCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndProductTypeCode(String value) {
        this.endProductTypeCode = value;
    }

    /**
     * Gets the value of the informalIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInformalIndicator() {
        return informalIndicator;
    }

    /**
     * Sets the value of the informalIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInformalIndicator(String value) {
        this.informalIndicator = value;
    }

    /**
     * Gets the value of the journalDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJournalDate() {
        return journalDate;
    }

    /**
     * Sets the value of the journalDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJournalDate(String value) {
        this.journalDate = value;
    }

    /**
     * Gets the value of the journalObjectID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJournalObjectID() {
        return journalObjectID;
    }

    /**
     * Sets the value of the journalObjectID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJournalObjectID(String value) {
        this.journalObjectID = value;
    }

    /**
     * Gets the value of the journalStation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJournalStation() {
        return journalStation;
    }

    /**
     * Sets the value of the journalStation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJournalStation(String value) {
        this.journalStation = value;
    }

    /**
     * Gets the value of the journalStatusTypeCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJournalStatusTypeCode() {
        return journalStatusTypeCode;
    }

    /**
     * Sets the value of the journalStatusTypeCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJournalStatusTypeCode(String value) {
        this.journalStatusTypeCode = value;
    }

    /**
     * Gets the value of the journalUserId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJournalUserId() {
        return journalUserId;
    }

    /**
     * Sets the value of the journalUserId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJournalUserId(String value) {
        this.journalUserId = value;
    }

    /**
     * Gets the value of the lastPaidDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastPaidDate() {
        return lastPaidDate;
    }

    /**
     * Sets the value of the lastPaidDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastPaidDate(String value) {
        this.lastPaidDate = value;
    }

    /**
     * Gets the value of the mailingAddressID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMailingAddressID() {
        return mailingAddressID;
    }

    /**
     * Sets the value of the mailingAddressID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMailingAddressID(String value) {
        this.mailingAddressID = value;
    }

    /**
     * Gets the value of the numberOfBenefitClaimRecords property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumberOfBenefitClaimRecords() {
        return numberOfBenefitClaimRecords;
    }

    /**
     * Sets the value of the numberOfBenefitClaimRecords property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumberOfBenefitClaimRecords(String value) {
        this.numberOfBenefitClaimRecords = value;
    }

    /**
     * Gets the value of the numberOfCPClaimRecords property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumberOfCPClaimRecords() {
        return numberOfCPClaimRecords;
    }

    /**
     * Sets the value of the numberOfCPClaimRecords property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumberOfCPClaimRecords(String value) {
        this.numberOfCPClaimRecords = value;
    }

    /**
     * Gets the value of the numberOfRecords property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumberOfRecords() {
        return numberOfRecords;
    }

    /**
     * Sets the value of the numberOfRecords property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumberOfRecords(String value) {
        this.numberOfRecords = value;
    }

    /**
     * Gets the value of the organizationName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrganizationName() {
        return organizationName;
    }

    /**
     * Sets the value of the organizationName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrganizationName(String value) {
        this.organizationName = value;
    }

    /**
     * Gets the value of the organizationTitleTypeName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrganizationTitleTypeName() {
        return organizationTitleTypeName;
    }

    /**
     * Sets the value of the organizationTitleTypeName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrganizationTitleTypeName(String value) {
        this.organizationTitleTypeName = value;
    }

    /**
     * Gets the value of the participantClaimantID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParticipantClaimantID() {
        return participantClaimantID;
    }

    /**
     * Sets the value of the participantClaimantID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParticipantClaimantID(String value) {
        this.participantClaimantID = value;
    }

    /**
     * Gets the value of the participantVetID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParticipantVetID() {
        return participantVetID;
    }

    /**
     * Sets the value of the participantVetID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParticipantVetID(String value) {
        this.participantVetID = value;
    }

    /**
     * Gets the value of the payeeTypeCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPayeeTypeCode() {
        return payeeTypeCode;
    }

    /**
     * Sets the value of the payeeTypeCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPayeeTypeCode(String value) {
        this.payeeTypeCode = value;
    }

    /**
     * Gets the value of the paymentAddressID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaymentAddressID() {
        return paymentAddressID;
    }

    /**
     * Sets the value of the paymentAddressID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaymentAddressID(String value) {
        this.paymentAddressID = value;
    }

    /**
     * Gets the value of the programTypeCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProgramTypeCode() {
        return programTypeCode;
    }

    /**
     * Sets the value of the programTypeCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProgramTypeCode(String value) {
        this.programTypeCode = value;
    }

    /**
     * Gets the value of the returnCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReturnCode() {
        return returnCode;
    }

    /**
     * Sets the value of the returnCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReturnCode(String value) {
        this.returnCode = value;
    }

    /**
     * Gets the value of the returnMessage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReturnMessage() {
        return returnMessage;
    }

    /**
     * Sets the value of the returnMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReturnMessage(String value) {
        this.returnMessage = value;
    }

    /**
     * Gets the value of the selection property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the selection property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSelection().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ShrinqbcSelection }
     * 
     * 
     */
    public List<ShrinqbcSelection> getSelection() {
        if (selection == null) {
            selection = new ArrayList<ShrinqbcSelection>();
        }
        return this.selection;
    }

    /**
     * Gets the value of the serviceTypeCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServiceTypeCode() {
        return serviceTypeCode;
    }

    /**
     * Sets the value of the serviceTypeCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServiceTypeCode(String value) {
        this.serviceTypeCode = value;
    }

    /**
     * Gets the value of the statusTypeCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatusTypeCode() {
        return statusTypeCode;
    }

    /**
     * Sets the value of the statusTypeCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatusTypeCode(String value) {
        this.statusTypeCode = value;
    }

    /**
     * Gets the value of the vetFirstName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVetFirstName() {
        return vetFirstName;
    }

    /**
     * Sets the value of the vetFirstName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVetFirstName(String value) {
        this.vetFirstName = value;
    }

    /**
     * Gets the value of the vetLastName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVetLastName() {
        return vetLastName;
    }

    /**
     * Sets the value of the vetLastName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVetLastName(String value) {
        this.vetLastName = value;
    }

    /**
     * Gets the value of the vetMiddleName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVetMiddleName() {
        return vetMiddleName;
    }

    /**
     * Sets the value of the vetMiddleName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVetMiddleName(String value) {
        this.vetMiddleName = value;
    }

    /**
     * Gets the value of the vetSuffix property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVetSuffix() {
        return vetSuffix;
    }

    /**
     * Sets the value of the vetSuffix property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVetSuffix(String value) {
        this.vetSuffix = value;
    }

}
