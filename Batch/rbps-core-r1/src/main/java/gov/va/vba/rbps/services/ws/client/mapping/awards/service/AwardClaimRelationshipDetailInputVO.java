//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.10.04 at 04:43:13 PM CDT 
//


package gov.va.vba.rbps.services.ws.client.mapping.awards.service;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for awardClaimRelationshipDetailInputVO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="awardClaimRelationshipDetailInputVO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AwardClaimRelationshipDetails" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="AwardClaimRelationshipDetail" type="{http://gov.va.vba.benefits.awards.ws/services}awardClaimRelationshipDetailVO" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "awardClaimRelationshipDetailInputVO", propOrder = {
    "awardClaimRelationshipDetails"
})
public class AwardClaimRelationshipDetailInputVO {

    @XmlElement(name = "AwardClaimRelationshipDetails")
    protected AwardClaimRelationshipDetailInputVO.AwardClaimRelationshipDetails awardClaimRelationshipDetails;

    /**
     * Gets the value of the awardClaimRelationshipDetails property.
     * 
     * @return
     *     possible object is
     *     {@link AwardClaimRelationshipDetailInputVO.AwardClaimRelationshipDetails }
     *     
     */
    public AwardClaimRelationshipDetailInputVO.AwardClaimRelationshipDetails getAwardClaimRelationshipDetails() {
        return awardClaimRelationshipDetails;
    }

    /**
     * Sets the value of the awardClaimRelationshipDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link AwardClaimRelationshipDetailInputVO.AwardClaimRelationshipDetails }
     *     
     */
    public void setAwardClaimRelationshipDetails(AwardClaimRelationshipDetailInputVO.AwardClaimRelationshipDetails value) {
        this.awardClaimRelationshipDetails = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="AwardClaimRelationshipDetail" type="{http://gov.va.vba.benefits.awards.ws/services}awardClaimRelationshipDetailVO" maxOccurs="unbounded" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "awardClaimRelationshipDetail"
    })
    public static class AwardClaimRelationshipDetails {

        @XmlElement(name = "AwardClaimRelationshipDetail")
        protected List<AwardClaimRelationshipDetailVO> awardClaimRelationshipDetail;

        /**
         * Gets the value of the awardClaimRelationshipDetail property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the awardClaimRelationshipDetail property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getAwardClaimRelationshipDetail().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link AwardClaimRelationshipDetailVO }
         * 
         * 
         */
        public List<AwardClaimRelationshipDetailVO> getAwardClaimRelationshipDetail() {
            if (awardClaimRelationshipDetail == null) {
                awardClaimRelationshipDetail = new ArrayList<AwardClaimRelationshipDetailVO>();
            }
            return this.awardClaimRelationshipDetail;
        }

    }

}
