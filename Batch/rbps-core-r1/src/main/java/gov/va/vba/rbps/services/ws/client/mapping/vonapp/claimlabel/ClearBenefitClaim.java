//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.10.24 at 04:51:38 PM CDT 
//


package gov.va.vba.rbps.services.ws.client.mapping.vonapp.claimlabel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for clearBenefitClaim complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="clearBenefitClaim">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="clearBenefitClaimInput" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="fileNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="payeeCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="benefitClaimType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="endProductCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="incremental" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="pclrReasonCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="pclrReasonText" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="bypassIndicator" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="user" type="{http://services.share.benefits.vba.va.gov/}userContext" minOccurs="0"/>
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
@XmlType(name = "clearBenefitClaim", propOrder = {
    "clearBenefitClaimInput",
    "user"
})
public class ClearBenefitClaim {

    protected ClearBenefitClaim.ClearBenefitClaimInput clearBenefitClaimInput;
    protected UserContext user;

    /**
     * Gets the value of the clearBenefitClaimInput property.
     * 
     * @return
     *     possible object is
     *     {@link ClearBenefitClaim.ClearBenefitClaimInput }
     *     
     */
    public ClearBenefitClaim.ClearBenefitClaimInput getClearBenefitClaimInput() {
        return clearBenefitClaimInput;
    }

    /**
     * Sets the value of the clearBenefitClaimInput property.
     * 
     * @param value
     *     allowed object is
     *     {@link ClearBenefitClaim.ClearBenefitClaimInput }
     *     
     */
    public void setClearBenefitClaimInput(ClearBenefitClaim.ClearBenefitClaimInput value) {
        this.clearBenefitClaimInput = value;
    }

    /**
     * Gets the value of the user property.
     * 
     * @return
     *     possible object is
     *     {@link UserContext }
     *     
     */
    public UserContext getUser() {
        return user;
    }

    /**
     * Sets the value of the user property.
     * 
     * @param value
     *     allowed object is
     *     {@link UserContext }
     *     
     */
    public void setUser(UserContext value) {
        this.user = value;
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
     *         &lt;element name="fileNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="payeeCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="benefitClaimType" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="endProductCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="incremental" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="pclrReasonCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="pclrReasonText" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="bypassIndicator" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
        "fileNumber",
        "payeeCode",
        "benefitClaimType",
        "endProductCode",
        "incremental",
        "pclrReasonCode",
        "pclrReasonText",
        "bypassIndicator"
    })
    public static class ClearBenefitClaimInput {

        @XmlElement(required = true)
        protected String fileNumber;
        @XmlElement(required = true, defaultValue = "00")
        protected String payeeCode;
        @XmlElement(required = true, defaultValue = "1")
        protected String benefitClaimType;
        @XmlElement(required = true)
        protected String endProductCode;
        @XmlElement(required = true)
        protected String incremental;
        @XmlElement(required = true)
        protected String pclrReasonCode;
        protected String pclrReasonText;
        protected String bypassIndicator;

        /**
         * Gets the value of the fileNumber property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getFileNumber() {
            return fileNumber;
        }

        /**
         * Sets the value of the fileNumber property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setFileNumber(String value) {
            this.fileNumber = value;
        }

        /**
         * Gets the value of the payeeCode property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPayeeCode() {
            return payeeCode;
        }

        /**
         * Sets the value of the payeeCode property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPayeeCode(String value) {
            this.payeeCode = value;
        }

        /**
         * Gets the value of the benefitClaimType property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getBenefitClaimType() {
            return benefitClaimType;
        }

        /**
         * Sets the value of the benefitClaimType property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setBenefitClaimType(String value) {
            this.benefitClaimType = value;
        }

        /**
         * Gets the value of the endProductCode property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getEndProductCode() {
            return endProductCode;
        }

        /**
         * Sets the value of the endProductCode property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setEndProductCode(String value) {
            this.endProductCode = value;
        }

        /**
         * Gets the value of the incremental property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getIncremental() {
            return incremental;
        }

        /**
         * Sets the value of the incremental property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setIncremental(String value) {
            this.incremental = value;
        }

        /**
         * Gets the value of the pclrReasonCode property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPclrReasonCode() {
            return pclrReasonCode;
        }

        /**
         * Sets the value of the pclrReasonCode property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPclrReasonCode(String value) {
            this.pclrReasonCode = value;
        }

        /**
         * Gets the value of the pclrReasonText property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPclrReasonText() {
            return pclrReasonText;
        }

        /**
         * Sets the value of the pclrReasonText property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPclrReasonText(String value) {
            this.pclrReasonText = value;
        }

        /**
         * Gets the value of the bypassIndicator property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getBypassIndicator() {
            return bypassIndicator;
        }

        /**
         * Sets the value of the bypassIndicator property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setBypassIndicator(String value) {
            this.bypassIndicator = value;
        }

    }

}
