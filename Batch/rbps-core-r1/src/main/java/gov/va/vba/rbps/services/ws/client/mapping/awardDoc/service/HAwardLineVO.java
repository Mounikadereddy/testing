//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.09.22 at 01:21:30 PM EDT 
//


package gov.va.vba.rbps.services.ws.client.mapping.awardDoc.service;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for hAwardLineVO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="hAwardLineVO"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://gov.va.vba.benefits.awards.ws/services}awardLineVO"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="awardLineRecipientList" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="awardLineRecipientVO" type="{http://gov.va.vba.benefits.awards.ws/services}awardLineRecipientVO" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="awardReasonList" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="awardReasonVO" type="{http://gov.va.vba.benefits.awards.ws/services}awardReasonVO" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "hAwardLineVO", propOrder = {
    "awardLineRecipientList",
    "awardReasonList"
})
public class HAwardLineVO
    extends AwardLineVO
{

    protected HAwardLineVO.AwardLineRecipientList awardLineRecipientList;
    protected HAwardLineVO.AwardReasonList awardReasonList;

    /**
     * Gets the value of the awardLineRecipientList property.
     * 
     * @return
     *     possible object is
     *     {@link HAwardLineVO.AwardLineRecipientList }
     *     
     */
    public HAwardLineVO.AwardLineRecipientList getAwardLineRecipientList() {
        return awardLineRecipientList;
    }

    /**
     * Sets the value of the awardLineRecipientList property.
     * 
     * @param value
     *     allowed object is
     *     {@link HAwardLineVO.AwardLineRecipientList }
     *     
     */
    public void setAwardLineRecipientList(HAwardLineVO.AwardLineRecipientList value) {
        this.awardLineRecipientList = value;
    }

    /**
     * Gets the value of the awardReasonList property.
     * 
     * @return
     *     possible object is
     *     {@link HAwardLineVO.AwardReasonList }
     *     
     */
    public HAwardLineVO.AwardReasonList getAwardReasonList() {
        return awardReasonList;
    }

    /**
     * Sets the value of the awardReasonList property.
     * 
     * @param value
     *     allowed object is
     *     {@link HAwardLineVO.AwardReasonList }
     *     
     */
    public void setAwardReasonList(HAwardLineVO.AwardReasonList value) {
        this.awardReasonList = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="awardLineRecipientVO" type="{http://gov.va.vba.benefits.awards.ws/services}awardLineRecipientVO" maxOccurs="unbounded" minOccurs="0"/&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "awardLineRecipientVO"
    })
    public static class AwardLineRecipientList {

        protected List<AwardLineRecipientVO> awardLineRecipientVO;

        /**
         * Gets the value of the awardLineRecipientVO property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the awardLineRecipientVO property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getAwardLineRecipientVO().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link AwardLineRecipientVO }
         * 
         * 
         */
        public List<AwardLineRecipientVO> getAwardLineRecipientVO() {
            if (awardLineRecipientVO == null) {
                awardLineRecipientVO = new ArrayList<AwardLineRecipientVO>();
            }
            return this.awardLineRecipientVO;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="awardReasonVO" type="{http://gov.va.vba.benefits.awards.ws/services}awardReasonVO" maxOccurs="unbounded" minOccurs="0"/&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "awardReasonVO"
    })
    public static class AwardReasonList {

        protected List<AwardReasonVO> awardReasonVO;

        /**
         * Gets the value of the awardReasonVO property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the awardReasonVO property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getAwardReasonVO().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link AwardReasonVO }
         * 
         * 
         */
        public List<AwardReasonVO> getAwardReasonVO() {
            if (awardReasonVO == null) {
                awardReasonVO = new ArrayList<AwardReasonVO>();
            }
            return this.awardReasonVO;
        }

    }

}
