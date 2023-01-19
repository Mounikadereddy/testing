
package gov.va.vba.rbps.services.ws.client.mapping.awards.service;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CurrAndPropAwardResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CurrAndPropAwardResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://gov.va.vba.benefits.awards.ws/services}voReturnBase">
 *       &lt;sequence>
 *         &lt;element name="AuthorizationEventList" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="AuthorizationEvent" type="{http://gov.va.vba.benefits.awards.ws/services}authorizationEventVO" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="AwardEventList" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="AwardEvent" type="{http://gov.va.vba.benefits.awards.ws/services}hAwardEventVO" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="AwardList" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Award" type="{http://gov.va.vba.benefits.awards.ws/services}awardVO" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="AwardRecipientList" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="AwardRecipient" type="{http://gov.va.vba.benefits.awards.ws/services}awardRecipientVO" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CurrAndPropAwardResponse", propOrder = {
    "authorizationEventList",
    "awardEventList",
    "awardList",
    "awardRecipientList"
})
public class CurrAndPropAwardResponse
    extends VoReturnBase
{

    @XmlElement(name = "AuthorizationEventList")
    protected CurrAndPropAwardResponse.AuthorizationEventList authorizationEventList;
    @XmlElement(name = "AwardEventList")
    protected CurrAndPropAwardResponse.AwardEventList awardEventList;
    @XmlElement(name = "AwardList")
    protected CurrAndPropAwardResponse.AwardList awardList;
    @XmlElement(name = "AwardRecipientList")
    protected CurrAndPropAwardResponse.AwardRecipientList awardRecipientList;

    /**
     * Gets the value of the authorizationEventList property.
     * 
     * @return
     *     possible object is
     *     {@link CurrAndPropAwardResponse.AuthorizationEventList }
     *     
     */
    public CurrAndPropAwardResponse.AuthorizationEventList getAuthorizationEventList() {
        return authorizationEventList;
    }

    /**
     * Sets the value of the authorizationEventList property.
     * 
     * @param value
     *     allowed object is
     *     {@link CurrAndPropAwardResponse.AuthorizationEventList }
     *     
     */
    public void setAuthorizationEventList(CurrAndPropAwardResponse.AuthorizationEventList value) {
        this.authorizationEventList = value;
    }

    /**
     * Gets the value of the awardEventList property.
     * 
     * @return
     *     possible object is
     *     {@link CurrAndPropAwardResponse.AwardEventList }
     *     
     */
    public CurrAndPropAwardResponse.AwardEventList getAwardEventList() {
        return awardEventList;
    }

    /**
     * Sets the value of the awardEventList property.
     * 
     * @param value
     *     allowed object is
     *     {@link CurrAndPropAwardResponse.AwardEventList }
     *     
     */
    public void setAwardEventList(CurrAndPropAwardResponse.AwardEventList value) {
        this.awardEventList = value;
    }

    /**
     * Gets the value of the awardList property.
     * 
     * @return
     *     possible object is
     *     {@link CurrAndPropAwardResponse.AwardList }
     *     
     */
    public CurrAndPropAwardResponse.AwardList getAwardList() {
        return awardList;
    }

    /**
     * Sets the value of the awardList property.
     * 
     * @param value
     *     allowed object is
     *     {@link CurrAndPropAwardResponse.AwardList }
     *     
     */
    public void setAwardList(CurrAndPropAwardResponse.AwardList value) {
        this.awardList = value;
    }

    /**
     * Gets the value of the awardRecipientList property.
     * 
     * @return
     *     possible object is
     *     {@link CurrAndPropAwardResponse.AwardRecipientList }
     *     
     */
    public CurrAndPropAwardResponse.AwardRecipientList getAwardRecipientList() {
        return awardRecipientList;
    }

    /**
     * Sets the value of the awardRecipientList property.
     * 
     * @param value
     *     allowed object is
     *     {@link CurrAndPropAwardResponse.AwardRecipientList }
     *     
     */
    public void setAwardRecipientList(CurrAndPropAwardResponse.AwardRecipientList value) {
        this.awardRecipientList = value;
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
     *         &lt;element name="AuthorizationEvent" type="{http://gov.va.vba.benefits.awards.ws/services}authorizationEventVO" maxOccurs="unbounded" minOccurs="0"/>
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
        "authorizationEvent"
    })
    public static class AuthorizationEventList {

        @XmlElement(name = "AuthorizationEvent")
        protected List<AuthorizationEventVO> authorizationEvent;

        /**
         * Gets the value of the authorizationEvent property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the authorizationEvent property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getAuthorizationEvent().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link AuthorizationEventVO }
         * 
         * 
         */
        public List<AuthorizationEventVO> getAuthorizationEvent() {
            if (authorizationEvent == null) {
                authorizationEvent = new ArrayList<AuthorizationEventVO>();
            }
            return this.authorizationEvent;
        }

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
     *         &lt;element name="AwardEvent" type="{http://gov.va.vba.benefits.awards.ws/services}hAwardEventVO" maxOccurs="unbounded" minOccurs="0"/>
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
        "awardEvent"
    })
    public static class AwardEventList {

        @XmlElement(name = "AwardEvent")
        protected List<HAwardEventVO> awardEvent;

        /**
         * Gets the value of the awardEvent property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the awardEvent property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getAwardEvent().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link HAwardEventVO }
         * 
         * 
         */
        public List<HAwardEventVO> getAwardEvent() {
            if (awardEvent == null) {
                awardEvent = new ArrayList<HAwardEventVO>();
            }
            return this.awardEvent;
        }

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
     *         &lt;element name="Award" type="{http://gov.va.vba.benefits.awards.ws/services}awardVO" maxOccurs="unbounded" minOccurs="0"/>
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
        "award"
    })
    public static class AwardList {

        @XmlElement(name = "Award")
        protected List<AwardVO> award;

        /**
         * Gets the value of the award property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the award property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getAward().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link AwardVO }
         * 
         * 
         */
        public List<AwardVO> getAward() {
            if (award == null) {
                award = new ArrayList<AwardVO>();
            }
            return this.award;
        }

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
     *         &lt;element name="AwardRecipient" type="{http://gov.va.vba.benefits.awards.ws/services}awardRecipientVO" maxOccurs="unbounded" minOccurs="0"/>
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
        "awardRecipient"
    })
    public static class AwardRecipientList {

        @XmlElement(name = "AwardRecipient")
        protected List<AwardRecipientVO> awardRecipient;

        /**
         * Gets the value of the awardRecipient property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the awardRecipient property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getAwardRecipient().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link AwardRecipientVO }
         * 
         * 
         */
        public List<AwardRecipientVO> getAwardRecipient() {
            if (awardRecipient == null) {
                awardRecipient = new ArrayList<AwardRecipientVO>();
            }
            return this.awardRecipient;
        }

    }

}
