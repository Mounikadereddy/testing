
package gov.va.vba.rbps.services.ws.client.mapping.awards.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for eftAddressVO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="eftAddressVO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://gov.va.vba.benefits.awards.ws/services}voBase">
 *       &lt;sequence>
 *         &lt;element name="awardType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="beginDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="depositAccountNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="depositAccountType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="endDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="fiduciaryName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="financialInstitutionID" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="modifiedAction" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="modifiedBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="modifiedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="modifiedLocation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="modifiedProcess" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="participantAddressTypeDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="participantDepositAccountID" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="participantID" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="participantMailingAddressID" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="participantName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="prepositionalPhraseTypeName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="routingTransitNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="treasurySequenceNumber" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "eftAddressVO", propOrder = {
    "awardType",
    "beginDate",
    "depositAccountNumber",
    "depositAccountType",
    "endDate",
    "fiduciaryName",
    "financialInstitutionID",
    "modifiedAction",
    "modifiedBy",
    "modifiedDate",
    "modifiedLocation",
    "modifiedProcess",
    "name",
    "participantAddressTypeDescription",
    "participantDepositAccountID",
    "participantID",
    "participantMailingAddressID",
    "participantName",
    "prepositionalPhraseTypeName",
    "routingTransitNumber",
    "treasurySequenceNumber"
})
public class EftAddressVO
    extends VoBase
{

    protected String awardType;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar beginDate;
    protected String depositAccountNumber;
    protected String depositAccountType;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar endDate;
    protected String fiduciaryName;
    protected Long financialInstitutionID;
    protected String modifiedAction;
    protected String modifiedBy;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar modifiedDate;
    protected String modifiedLocation;
    protected String modifiedProcess;
    protected String name;
    protected String participantAddressTypeDescription;
    protected Long participantDepositAccountID;
    protected Long participantID;
    protected Long participantMailingAddressID;
    protected String participantName;
    protected String prepositionalPhraseTypeName;
    protected String routingTransitNumber;
    protected Integer treasurySequenceNumber;

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
     * Gets the value of the depositAccountNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDepositAccountNumber() {
        return depositAccountNumber;
    }

    /**
     * Sets the value of the depositAccountNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDepositAccountNumber(String value) {
        this.depositAccountNumber = value;
    }

    /**
     * Gets the value of the depositAccountType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDepositAccountType() {
        return depositAccountType;
    }

    /**
     * Sets the value of the depositAccountType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDepositAccountType(String value) {
        this.depositAccountType = value;
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
     * Gets the value of the fiduciaryName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFiduciaryName() {
        return fiduciaryName;
    }

    /**
     * Sets the value of the fiduciaryName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFiduciaryName(String value) {
        this.fiduciaryName = value;
    }

    /**
     * Gets the value of the financialInstitutionID property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getFinancialInstitutionID() {
        return financialInstitutionID;
    }

    /**
     * Sets the value of the financialInstitutionID property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setFinancialInstitutionID(Long value) {
        this.financialInstitutionID = value;
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
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
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
     * Gets the value of the participantDepositAccountID property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getParticipantDepositAccountID() {
        return participantDepositAccountID;
    }

    /**
     * Sets the value of the participantDepositAccountID property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setParticipantDepositAccountID(Long value) {
        this.participantDepositAccountID = value;
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
     * Gets the value of the prepositionalPhraseTypeName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrepositionalPhraseTypeName() {
        return prepositionalPhraseTypeName;
    }

    /**
     * Sets the value of the prepositionalPhraseTypeName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrepositionalPhraseTypeName(String value) {
        this.prepositionalPhraseTypeName = value;
    }

    /**
     * Gets the value of the routingTransitNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRoutingTransitNumber() {
        return routingTransitNumber;
    }

    /**
     * Sets the value of the routingTransitNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRoutingTransitNumber(String value) {
        this.routingTransitNumber = value;
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

}
