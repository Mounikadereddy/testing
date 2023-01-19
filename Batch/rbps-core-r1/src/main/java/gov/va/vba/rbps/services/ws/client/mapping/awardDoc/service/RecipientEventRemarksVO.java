//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.09.22 at 01:21:30 PM EDT 
//


package gov.va.vba.rbps.services.ws.client.mapping.awardDoc.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for recipientEventRemarksVO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="recipientEventRemarksVO"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://gov.va.vba.benefits.awards.ws/services}voBase"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="modifiedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="recipientEventID" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="remarksSuspendResumePrintText" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "recipientEventRemarksVO", propOrder = {
    "modifiedDate",
    "recipientEventID",
    "remarksSuspendResumePrintText"
})
public class RecipientEventRemarksVO
    extends VoBase
{

    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar modifiedDate;
    protected long recipientEventID;
    protected String remarksSuspendResumePrintText;

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
     * Gets the value of the recipientEventID property.
     * 
     */
    public long getRecipientEventID() {
        return recipientEventID;
    }

    /**
     * Sets the value of the recipientEventID property.
     * 
     */
    public void setRecipientEventID(long value) {
        this.recipientEventID = value;
    }

    /**
     * Gets the value of the remarksSuspendResumePrintText property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRemarksSuspendResumePrintText() {
        return remarksSuspendResumePrintText;
    }

    /**
     * Sets the value of the remarksSuspendResumePrintText property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRemarksSuspendResumePrintText(String value) {
        this.remarksSuspendResumePrintText = value;
    }

}
