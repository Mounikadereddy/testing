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
 * <p>Java class for updateBenefitClaimDependentsResponse complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="updateBenefitClaimDependentsResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" type="{http://services.share.benefits.vba.va.gov/}benefitClaimRecord" minOccurs="0"/>
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
@XmlType(name = "updateBenefitClaimDependentsResponse", propOrder = {
    "_return"
})
public class UpdateBenefitClaimDependentsResponse {

    @XmlElement(name = "return")
    protected BenefitClaimRecord _return;

    /**
     * Gets the value of the return property.
     *
     * @return
     *     possible object is
     *     {@link BenefitClaimRecord }
     *
     */
    public BenefitClaimRecord getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     *
     * @param value
     *     allowed object is
     *     {@link BenefitClaimRecord }
     *
     */
    public void setReturn(final BenefitClaimRecord value) {
        this._return = value;
    }

}
