//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.07.09 at 02:14:26 PM CDT 
//


package gov.va.vba.rbps.services.ws.client.mapping.share.ratingComparison;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for rbaPrfilPK complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="rbaPrfilPK">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="prfilDt" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="ptcpntVetId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "rbaPrfilPK", propOrder = {
    "prfilDt",
    "ptcpntVetId"
})
public class RbaPrfilPK {

    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar prfilDt;
    protected Long ptcpntVetId;

    /**
     * Gets the value of the prfilDt property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getPrfilDt() {
        return prfilDt;
    }

    /**
     * Sets the value of the prfilDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setPrfilDt(XMLGregorianCalendar value) {
        this.prfilDt = value;
    }

    /**
     * Gets the value of the ptcpntVetId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getPtcpntVetId() {
        return ptcpntVetId;
    }

    /**
     * Sets the value of the ptcpntVetId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setPtcpntVetId(Long value) {
        this.ptcpntVetId = value;
    }

}
