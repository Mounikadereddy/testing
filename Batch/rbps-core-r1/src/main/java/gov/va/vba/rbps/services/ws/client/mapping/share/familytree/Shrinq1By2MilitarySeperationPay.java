//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.10.24 at 04:39:54 PM CDT 
//


package gov.va.vba.rbps.services.ws.client.mapping.share.familytree;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for shrinq1By2MilitarySeperationPay complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="shrinq1By2MilitarySeperationPay">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="grossAmount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lessFedTaxAmount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lineItemNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ptcpntId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="receiptDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "shrinq1By2MilitarySeperationPay", propOrder = {
    "grossAmount",
    "lessFedTaxAmount",
    "lineItemNbr",
    "ptcpntId",
    "receiptDate"
})
public class Shrinq1By2MilitarySeperationPay {

    protected String grossAmount;
    protected String lessFedTaxAmount;
    protected String lineItemNbr;
    protected String ptcpntId;
    protected String receiptDate;

    /**
     * Gets the value of the grossAmount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGrossAmount() {
        return grossAmount;
    }

    /**
     * Sets the value of the grossAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGrossAmount(String value) {
        this.grossAmount = value;
    }

    /**
     * Gets the value of the lessFedTaxAmount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLessFedTaxAmount() {
        return lessFedTaxAmount;
    }

    /**
     * Sets the value of the lessFedTaxAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLessFedTaxAmount(String value) {
        this.lessFedTaxAmount = value;
    }

    /**
     * Gets the value of the lineItemNbr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLineItemNbr() {
        return lineItemNbr;
    }

    /**
     * Sets the value of the lineItemNbr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLineItemNbr(String value) {
        this.lineItemNbr = value;
    }

    /**
     * Gets the value of the ptcpntId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPtcpntId() {
        return ptcpntId;
    }

    /**
     * Sets the value of the ptcpntId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPtcpntId(String value) {
        this.ptcpntId = value;
    }

    /**
     * Gets the value of the receiptDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReceiptDate() {
        return receiptDate;
    }

    /**
     * Sets the value of the receiptDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReceiptDate(String value) {
        this.receiptDate = value;
    }

}
