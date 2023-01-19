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
 * <p>Java class for awardVO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="awardVO"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://gov.va.vba.benefits.awards.ws/services}voBase"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="autoBurialIndicator" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="awardType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="beneficiaryID" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *         &lt;element name="convertedIndicator" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="eligibilityVerificationReportAdjustmentIndicator" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="generationControlDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="initialAwardNotificationDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="modifiedAction" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="modifiedBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="modifiedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="modifiedLocation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="modifiedProcess" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="originalClaimDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="retiredPayPropControlDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="temporaryWorkSpaceDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="temporaryWorkSpaceLocationID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="temporaryWorkSpaceStatusTypeCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="temporaryWorkSpaceStatusTypeDesc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="temporaryWorkSpaceUserID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="temporaryWorkspaceReductionInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="tenPercentOrMoreSCDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="totalWaiverDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
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
@XmlType(name = "awardVO", propOrder = {
    "autoBurialIndicator",
    "awardType",
    "beneficiaryID",
    "convertedIndicator",
    "eligibilityVerificationReportAdjustmentIndicator",
    "generationControlDate",
    "initialAwardNotificationDate",
    "modifiedAction",
    "modifiedBy",
    "modifiedDate",
    "modifiedLocation",
    "modifiedProcess",
    "originalClaimDate",
    "retiredPayPropControlDate",
    "temporaryWorkSpaceDate",
    "temporaryWorkSpaceLocationID",
    "temporaryWorkSpaceStatusTypeCode",
    "temporaryWorkSpaceStatusTypeDesc",
    "temporaryWorkSpaceUserID",
    "temporaryWorkspaceReductionInd",
    "tenPercentOrMoreSCDate",
    "totalWaiverDate",
    "veteranID"
})
public class AwardVO
    extends VoBase
{

    protected String autoBurialIndicator;
    protected String awardType;
    protected Long beneficiaryID;
    protected String convertedIndicator;
    protected String eligibilityVerificationReportAdjustmentIndicator;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar generationControlDate;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar initialAwardNotificationDate;
    protected String modifiedAction;
    protected String modifiedBy;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar modifiedDate;
    protected String modifiedLocation;
    protected String modifiedProcess;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar originalClaimDate;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar retiredPayPropControlDate;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar temporaryWorkSpaceDate;
    protected String temporaryWorkSpaceLocationID;
    protected String temporaryWorkSpaceStatusTypeCode;
    protected String temporaryWorkSpaceStatusTypeDesc;
    protected String temporaryWorkSpaceUserID;
    protected String temporaryWorkspaceReductionInd;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar tenPercentOrMoreSCDate;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar totalWaiverDate;
    protected Long veteranID;

    /**
     * Gets the value of the autoBurialIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAutoBurialIndicator() {
        return autoBurialIndicator;
    }

    /**
     * Sets the value of the autoBurialIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAutoBurialIndicator(String value) {
        this.autoBurialIndicator = value;
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
     * Gets the value of the beneficiaryID property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getBeneficiaryID() {
        return beneficiaryID;
    }

    /**
     * Sets the value of the beneficiaryID property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setBeneficiaryID(Long value) {
        this.beneficiaryID = value;
    }

    /**
     * Gets the value of the convertedIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConvertedIndicator() {
        return convertedIndicator;
    }

    /**
     * Sets the value of the convertedIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConvertedIndicator(String value) {
        this.convertedIndicator = value;
    }

    /**
     * Gets the value of the eligibilityVerificationReportAdjustmentIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEligibilityVerificationReportAdjustmentIndicator() {
        return eligibilityVerificationReportAdjustmentIndicator;
    }

    /**
     * Sets the value of the eligibilityVerificationReportAdjustmentIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEligibilityVerificationReportAdjustmentIndicator(String value) {
        this.eligibilityVerificationReportAdjustmentIndicator = value;
    }

    /**
     * Gets the value of the generationControlDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getGenerationControlDate() {
        return generationControlDate;
    }

    /**
     * Sets the value of the generationControlDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setGenerationControlDate(XMLGregorianCalendar value) {
        this.generationControlDate = value;
    }

    /**
     * Gets the value of the initialAwardNotificationDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getInitialAwardNotificationDate() {
        return initialAwardNotificationDate;
    }

    /**
     * Sets the value of the initialAwardNotificationDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setInitialAwardNotificationDate(XMLGregorianCalendar value) {
        this.initialAwardNotificationDate = value;
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
     * Gets the value of the originalClaimDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getOriginalClaimDate() {
        return originalClaimDate;
    }

    /**
     * Sets the value of the originalClaimDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setOriginalClaimDate(XMLGregorianCalendar value) {
        this.originalClaimDate = value;
    }

    /**
     * Gets the value of the retiredPayPropControlDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getRetiredPayPropControlDate() {
        return retiredPayPropControlDate;
    }

    /**
     * Sets the value of the retiredPayPropControlDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setRetiredPayPropControlDate(XMLGregorianCalendar value) {
        this.retiredPayPropControlDate = value;
    }

    /**
     * Gets the value of the temporaryWorkSpaceDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTemporaryWorkSpaceDate() {
        return temporaryWorkSpaceDate;
    }

    /**
     * Sets the value of the temporaryWorkSpaceDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTemporaryWorkSpaceDate(XMLGregorianCalendar value) {
        this.temporaryWorkSpaceDate = value;
    }

    /**
     * Gets the value of the temporaryWorkSpaceLocationID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTemporaryWorkSpaceLocationID() {
        return temporaryWorkSpaceLocationID;
    }

    /**
     * Sets the value of the temporaryWorkSpaceLocationID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTemporaryWorkSpaceLocationID(String value) {
        this.temporaryWorkSpaceLocationID = value;
    }

    /**
     * Gets the value of the temporaryWorkSpaceStatusTypeCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTemporaryWorkSpaceStatusTypeCode() {
        return temporaryWorkSpaceStatusTypeCode;
    }

    /**
     * Sets the value of the temporaryWorkSpaceStatusTypeCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTemporaryWorkSpaceStatusTypeCode(String value) {
        this.temporaryWorkSpaceStatusTypeCode = value;
    }

    /**
     * Gets the value of the temporaryWorkSpaceStatusTypeDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTemporaryWorkSpaceStatusTypeDesc() {
        return temporaryWorkSpaceStatusTypeDesc;
    }

    /**
     * Sets the value of the temporaryWorkSpaceStatusTypeDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTemporaryWorkSpaceStatusTypeDesc(String value) {
        this.temporaryWorkSpaceStatusTypeDesc = value;
    }

    /**
     * Gets the value of the temporaryWorkSpaceUserID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTemporaryWorkSpaceUserID() {
        return temporaryWorkSpaceUserID;
    }

    /**
     * Sets the value of the temporaryWorkSpaceUserID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTemporaryWorkSpaceUserID(String value) {
        this.temporaryWorkSpaceUserID = value;
    }

    /**
     * Gets the value of the temporaryWorkspaceReductionInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTemporaryWorkspaceReductionInd() {
        return temporaryWorkspaceReductionInd;
    }

    /**
     * Sets the value of the temporaryWorkspaceReductionInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTemporaryWorkspaceReductionInd(String value) {
        this.temporaryWorkspaceReductionInd = value;
    }

    /**
     * Gets the value of the tenPercentOrMoreSCDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTenPercentOrMoreSCDate() {
        return tenPercentOrMoreSCDate;
    }

    /**
     * Sets the value of the tenPercentOrMoreSCDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTenPercentOrMoreSCDate(XMLGregorianCalendar value) {
        this.tenPercentOrMoreSCDate = value;
    }

    /**
     * Gets the value of the totalWaiverDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTotalWaiverDate() {
        return totalWaiverDate;
    }

    /**
     * Sets the value of the totalWaiverDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTotalWaiverDate(XMLGregorianCalendar value) {
        this.totalWaiverDate = value;
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
