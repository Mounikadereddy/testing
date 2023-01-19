//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.10.17 at 03:34:03 PM CDT 
//


package gov.va.vba.rbps.services.ws.client.mapping.awards.adjustmentDecision;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RetiredPayDecnResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RetiredPayDecnResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://gov.va.vba.benefits.awards.ws/services}voReturnBase">
 *       &lt;sequence>
 *         &lt;element name="RetiredPayDecns" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="RetiredPayDecn" type="{http://gov.va.vba.benefits.awards.ws/services}retiredPayDecnVO" maxOccurs="unbounded" form="qualified"/>
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
@XmlType(name = "RetiredPayDecnResponse", namespace = "http://gov.va.vba.benefits.awards.ws/data", propOrder = {
    "retiredPayDecns"
})
public class RetiredPayDecnResponse
    extends VoReturnBase
{

    @XmlElement(name = "RetiredPayDecns")
    protected RetiredPayDecnResponse.RetiredPayDecns retiredPayDecns;

    /**
     * Gets the value of the retiredPayDecns property.
     * 
     * @return
     *     possible object is
     *     {@link RetiredPayDecnResponse.RetiredPayDecns }
     *     
     */
    public RetiredPayDecnResponse.RetiredPayDecns getRetiredPayDecns() {
        return retiredPayDecns;
    }

    /**
     * Sets the value of the retiredPayDecns property.
     * 
     * @param value
     *     allowed object is
     *     {@link RetiredPayDecnResponse.RetiredPayDecns }
     *     
     */
    public void setRetiredPayDecns(RetiredPayDecnResponse.RetiredPayDecns value) {
        this.retiredPayDecns = value;
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
     *         &lt;element name="RetiredPayDecn" type="{http://gov.va.vba.benefits.awards.ws/services}retiredPayDecnVO" maxOccurs="unbounded" form="qualified"/>
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
        "retiredPayDecn"
    })
    public static class RetiredPayDecns {

        @XmlElement(name = "RetiredPayDecn", namespace = "http://gov.va.vba.benefits.awards.ws/data", required = true)
        protected List<RetiredPayDecnVO> retiredPayDecn;

        /**
         * Gets the value of the retiredPayDecn property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the retiredPayDecn property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getRetiredPayDecn().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RetiredPayDecnVO }
         * 
         * 
         */
        public List<RetiredPayDecnVO> getRetiredPayDecn() {
            if (retiredPayDecn == null) {
                retiredPayDecn = new ArrayList<RetiredPayDecnVO>();
            }
            return this.retiredPayDecn;
        }

    }

}
