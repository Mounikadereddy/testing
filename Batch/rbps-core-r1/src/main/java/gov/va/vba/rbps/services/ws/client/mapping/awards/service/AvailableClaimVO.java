
package gov.va.vba.rbps.services.ws.client.mapping.awards.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for availableClaimVO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="availableClaimVO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://gov.va.vba.benefits.awards.ws/services}voBase">
 *       &lt;sequence>
 *         &lt;element name="benefitClaimID" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="benefitClaimType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="benefitClaimTypeDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="brokerJurisdicationLocationID" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="claimDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="claimantID" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="firstName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="jurisdicationID" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="lastName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="middleName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="modifiedAction" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="modifiedBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="modifiedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="modifiedLocation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="modifiedProcess" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="organizationName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="payeeType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="programType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="suffix" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="veteranID" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "availableClaimVO", propOrder = {
    "benefitClaimID",
    "benefitClaimType",
    "benefitClaimTypeDescription",
    "brokerJurisdicationLocationID",
    "claimDate",
    "claimantID",
    "firstName",
    "jurisdicationID",
    "lastName",
    "middleName",
    "modifiedAction",
    "modifiedBy",
    "modifiedDate",
    "modifiedLocation",
    "modifiedProcess",
    "organizationName",
    "payeeType",
    "programType",
    "suffix",
    "veteranID"
})
public class AvailableClaimVO
    extends VoBase
{

    protected Long benefitClaimID;
    protected String benefitClaimType;
    protected String benefitClaimTypeDescription;
    protected Long brokerJurisdicationLocationID;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar claimDate;
    protected Long claimantID;
    protected String firstName;
    protected Long jurisdicationID;
    protected String lastName;
    protected String middleName;
    protected String modifiedAction;
    protected String modifiedBy;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar modifiedDate;
    protected String modifiedLocation;
    protected String modifiedProcess;
    protected String organizationName;
    protected String payeeType;
    protected String programType;
    protected String suffix;
    protected Long veteranID;

    /**
     * Gets the value of the benefitClaimID property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getBenefitClaimID() {
        return benefitClaimID;
    }

    /**
     * Sets the value of the benefitClaimID property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setBenefitClaimID(Long value) {
        this.benefitClaimID = value;
    }

    /**
     * Gets the value of the benefitClaimType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBenefitClaimType() {
        return benefitClaimType;
    }

    /**
     * Sets the value of the benefitClaimType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBenefitClaimType(String value) {
        this.benefitClaimType = value;
    }

    /**
     * Gets the value of the benefitClaimTypeDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBenefitClaimTypeDescription() {
        return benefitClaimTypeDescription;
    }

    /**
     * Sets the value of the benefitClaimTypeDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBenefitClaimTypeDescription(String value) {
        this.benefitClaimTypeDescription = value;
    }

    /**
     * Gets the value of the brokerJurisdicationLocationID property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getBrokerJurisdicationLocationID() {
        return brokerJurisdicationLocationID;
    }

    /**
     * Sets the value of the brokerJurisdicationLocationID property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setBrokerJurisdicationLocationID(Long value) {
        this.brokerJurisdicationLocationID = value;
    }

    /**
     * Gets the value of the claimDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getClaimDate() {
        return claimDate;
    }

    /**
     * Sets the value of the claimDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setClaimDate(XMLGregorianCalendar value) {
        this.claimDate = value;
    }

    /**
     * Gets the value of the claimantID property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getClaimantID() {
        return claimantID;
    }

    /**
     * Sets the value of the claimantID property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setClaimantID(Long value) {
        this.claimantID = value;
    }

    /**
     * Gets the value of the firstName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the value of the firstName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFirstName(String value) {
        this.firstName = value;
    }

    /**
     * Gets the value of the jurisdicationID property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getJurisdicationID() {
        return jurisdicationID;
    }

    /**
     * Sets the value of the jurisdicationID property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setJurisdicationID(Long value) {
        this.jurisdicationID = value;
    }

    /**
     * Gets the value of the lastName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the value of the lastName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastName(String value) {
        this.lastName = value;
    }

    /**
     * Gets the value of the middleName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     * Sets the value of the middleName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMiddleName(String value) {
        this.middleName = value;
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
     * Gets the value of the payeeType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPayeeType() {
        return payeeType;
    }

    /**
     * Sets the value of the payeeType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPayeeType(String value) {
        this.payeeType = value;
    }

    /**
     * Gets the value of the programType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProgramType() {
        return programType;
    }

    /**
     * Sets the value of the programType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProgramType(String value) {
        this.programType = value;
    }

    /**
     * Gets the value of the suffix property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSuffix() {
        return suffix;
    }

    /**
     * Sets the value of the suffix property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSuffix(String value) {
        this.suffix = value;
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
