//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.07.09 at 02:14:26 PM CDT 
//


package gov.va.vba.rbps.services.ws.client.mapping.share.ratingComparison;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for compareByDateRange complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="compareByDateRange">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RatingDateRange" type="{http://session.ejb.awards.benefits.vba.va.gov/}ratingDateRangeVO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "compareByDateRange", propOrder = {
    "ratingDateRange"
})
public class CompareByDateRange {

    @XmlElement(name = "RatingDateRange")
    protected RatingDateRangeVO ratingDateRange;

    /**
     * Gets the value of the ratingDateRange property.
     * 
     * @return
     *     possible object is
     *     {@link RatingDateRangeVO }
     *     
     */
    public RatingDateRangeVO getRatingDateRange() {
        return ratingDateRange;
    }

    /**
     * Sets the value of the ratingDateRange property.
     * 
     * @param value
     *     allowed object is
     *     {@link RatingDateRangeVO }
     *     
     */
    public void setRatingDateRange(RatingDateRangeVO value) {
        this.ratingDateRange = value;
    }

}
