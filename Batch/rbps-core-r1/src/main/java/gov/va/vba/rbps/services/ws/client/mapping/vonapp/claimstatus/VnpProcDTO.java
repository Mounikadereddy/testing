//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.3 in JDK 1.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.06.14 at 09:22:05 AM CDT 
//


package gov.va.vba.rbps.services.ws.client.mapping.vonapp.claimstatus;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for vnpProcDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="vnpProcDTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="creatdDt" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="jrnDt" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="jrnLctnId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="jrnObjId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="jrnStatusTypeCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="jrnUserId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lastModifdDt" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="vnpProcId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="vnpProcStateTypeCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="vnpProcTypeCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "vnpProcDTO", propOrder = {
    "creatdDt",
    "jrnDt",
    "jrnLctnId",
    "jrnObjId",
    "jrnStatusTypeCd",
    "jrnUserId",
    "lastModifdDt",
    "vnpProcId",
    "vnpProcStateTypeCd",
    "vnpProcTypeCd"
})
public class VnpProcDTO {

    protected XMLGregorianCalendar creatdDt;
    protected XMLGregorianCalendar jrnDt;
    protected String jrnLctnId;
    protected String jrnObjId;
    protected String jrnStatusTypeCd;
    protected String jrnUserId;
    protected XMLGregorianCalendar lastModifdDt;
    protected Long vnpProcId;
    protected String vnpProcStateTypeCd;
    protected String vnpProcTypeCd;

    /**
     * Gets the value of the creatdDt property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCreatdDt() {
        return creatdDt;
    }

    /**
     * Sets the value of the creatdDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCreatdDt(XMLGregorianCalendar value) {
        this.creatdDt = value;
    }

    /**
     * Gets the value of the jrnDt property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getJrnDt() {
        return jrnDt;
    }

    /**
     * Sets the value of the jrnDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setJrnDt(XMLGregorianCalendar value) {
        this.jrnDt = value;
    }

    /**
     * Gets the value of the jrnLctnId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJrnLctnId() {
        return jrnLctnId;
    }

    /**
     * Sets the value of the jrnLctnId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJrnLctnId(String value) {
        this.jrnLctnId = value;
    }

    /**
     * Gets the value of the jrnObjId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJrnObjId() {
        return jrnObjId;
    }

    /**
     * Sets the value of the jrnObjId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJrnObjId(String value) {
        this.jrnObjId = value;
    }

    /**
     * Gets the value of the jrnStatusTypeCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJrnStatusTypeCd() {
        return jrnStatusTypeCd;
    }

    /**
     * Sets the value of the jrnStatusTypeCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJrnStatusTypeCd(String value) {
        this.jrnStatusTypeCd = value;
    }

    /**
     * Gets the value of the jrnUserId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJrnUserId() {
        return jrnUserId;
    }

    /**
     * Sets the value of the jrnUserId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJrnUserId(String value) {
        this.jrnUserId = value;
    }

    /**
     * Gets the value of the lastModifdDt property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLastModifdDt() {
        return lastModifdDt;
    }

    /**
     * Sets the value of the lastModifdDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLastModifdDt(XMLGregorianCalendar value) {
        this.lastModifdDt = value;
    }

    /**
     * Gets the value of the vnpProcId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getVnpProcId() {
        return vnpProcId;
    }

    /**
     * Sets the value of the vnpProcId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setVnpProcId(Long value) {
        this.vnpProcId = value;
    }

    /**
     * Gets the value of the vnpProcStateTypeCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVnpProcStateTypeCd() {
        return vnpProcStateTypeCd;
    }

    /**
     * Sets the value of the vnpProcStateTypeCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVnpProcStateTypeCd(String value) {
        this.vnpProcStateTypeCd = value;
    }

    /**
     * Gets the value of the vnpProcTypeCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVnpProcTypeCd() {
        return vnpProcTypeCd;
    }

    /**
     * Sets the value of the vnpProcTypeCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVnpProcTypeCd(String value) {
        this.vnpProcTypeCd = value;
    }

}
