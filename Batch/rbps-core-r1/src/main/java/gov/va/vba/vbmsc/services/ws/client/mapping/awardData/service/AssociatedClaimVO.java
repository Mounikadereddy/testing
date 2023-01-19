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
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for associatedClaimVO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="associatedClaimVO"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="awardEventID" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *         &lt;element name="claimID" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *         &lt;element name="claimReceivedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="claimType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="claimTypeDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="endProductType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="endProductTypeDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="payeeType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="payeeTypeDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="statusType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="statusTypeDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "associatedClaimVO", propOrder = {
    "awardEventID",
    "claimID",
    "claimReceivedDate",
    "claimType",
    "claimTypeDescription",
    "endProductType",
    "endProductTypeDescription",
    "payeeType",
    "payeeTypeDescription",
    "statusType",
    "statusTypeDescription"
})
public class AssociatedClaimVO {

    protected Long awardEventID;
    protected Long claimID;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar claimReceivedDate;
    protected String claimType;
    protected String claimTypeDescription;
    protected String endProductType;
    protected String endProductTypeDescription;
    protected String payeeType;
    protected String payeeTypeDescription;
    protected String statusType;
    protected String statusTypeDescription;

    /**
     * Gets the value of the awardEventID property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getAwardEventID() {
        return awardEventID;
    }

    /**
     * Sets the value of the awardEventID property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setAwardEventID(Long value) {
        this.awardEventID = value;
    }

    /**
     * Gets the value of the claimID property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getClaimID() {
        return claimID;
    }

    /**
     * Sets the value of the claimID property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setClaimID(Long value) {
        this.claimID = value;
    }

    /**
     * Gets the value of the claimReceivedDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getClaimReceivedDate() {
        return claimReceivedDate;
    }

    /**
     * Sets the value of the claimReceivedDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setClaimReceivedDate(XMLGregorianCalendar value) {
        this.claimReceivedDate = value;
    }

    /**
     * Gets the value of the claimType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClaimType() {
        return claimType;
    }

    /**
     * Sets the value of the claimType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClaimType(String value) {
        this.claimType = value;
    }

    /**
     * Gets the value of the claimTypeDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClaimTypeDescription() {
        return claimTypeDescription;
    }

    /**
     * Sets the value of the claimTypeDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClaimTypeDescription(String value) {
        this.claimTypeDescription = value;
    }

    /**
     * Gets the value of the endProductType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEndProductType() {
        return endProductType;
    }

    /**
     * Sets the value of the endProductType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndProductType(String value) {
        this.endProductType = value;
    }

    /**
     * Gets the value of the endProductTypeDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEndProductTypeDescription() {
        return endProductTypeDescription;
    }

    /**
     * Sets the value of the endProductTypeDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndProductTypeDescription(String value) {
        this.endProductTypeDescription = value;
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
     * Gets the value of the payeeTypeDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPayeeTypeDescription() {
        return payeeTypeDescription;
    }

    /**
     * Sets the value of the payeeTypeDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPayeeTypeDescription(String value) {
        this.payeeTypeDescription = value;
    }

    /**
     * Gets the value of the statusType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatusType() {
        return statusType;
    }

    /**
     * Sets the value of the statusType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatusType(String value) {
        this.statusType = value;
    }

    /**
     * Gets the value of the statusTypeDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatusTypeDescription() {
        return statusTypeDescription;
    }

    /**
     * Sets the value of the statusTypeDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatusTypeDescription(String value) {
        this.statusTypeDescription = value;
    }

}
