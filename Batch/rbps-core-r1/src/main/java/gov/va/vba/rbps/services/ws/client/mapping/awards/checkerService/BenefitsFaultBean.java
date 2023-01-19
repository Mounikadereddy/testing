//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.04.02 at 03:11:18 PM CDT 
//


package gov.va.vba.rbps.services.ws.client.mapping.awards.checkerService;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for benefitsFaultBean complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="benefitsFaultBean">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ws.acc.benefits.vba.va.gov/}faultInfoBean">
 *       &lt;sequence>
 *         &lt;element ref="{http://ws.acc.benefits.vba.va.gov/}Message"/>
 *         &lt;element name="databaseMessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="exceptionInfo" type="{http://ws.acc.benefits.vba.va.gov/}messageVO" minOccurs="0"/>
 *         &lt;element name="fieldName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="logIt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="messageID" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="modifiedAction" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="modifiedBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="modifiedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="modifiedLocation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="modifiedProcess" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="object" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rowIdentifier" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="source" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sourceErrorID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sourceMessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "benefitsFaultBean", propOrder = {
    "rest"
})
public class BenefitsFaultBean
    extends FaultInfoBean
{

    @XmlElementRefs({
        @XmlElementRef(name = "sourceMessage", type = JAXBElement.class),
        @XmlElementRef(name = "exceptionInfo", type = JAXBElement.class),
        @XmlElementRef(name = "modifiedLocation", type = JAXBElement.class),
        @XmlElementRef(name = "modifiedBy", type = JAXBElement.class),
        @XmlElementRef(name = "object", type = JAXBElement.class),
        @XmlElementRef(name = "Message", namespace = "http://ws.acc.benefits.vba.va.gov/", type = JAXBElement.class),
        @XmlElementRef(name = "source", type = JAXBElement.class),
        @XmlElementRef(name = "modifiedAction", type = JAXBElement.class),
        @XmlElementRef(name = "modifiedProcess", type = JAXBElement.class),
        @XmlElementRef(name = "messageID", type = JAXBElement.class),
        @XmlElementRef(name = "modifiedDate", type = JAXBElement.class),
        @XmlElementRef(name = "rowIdentifier", type = JAXBElement.class),
        @XmlElementRef(name = "fieldName", type = JAXBElement.class),
        @XmlElementRef(name = "logIt", type = JAXBElement.class),
        @XmlElementRef(name = "databaseMessage", type = JAXBElement.class),
        @XmlElementRef(name = "sourceErrorID", type = JAXBElement.class)
    })
    protected List<JAXBElement<?>> rest;

    /**
     * Gets the rest of the content model. 
     * 
     * <p>
     * You are getting this "catch-all" property because of the following reason: 
     * The field name "Message" is used by two different parts of a schema. See: 
     * line 63 of http://bepwebdevl.vba.va.gov:80/appcomp-checker/AppCompCheckerService?xsd=1
     * line 86 of http://bepwebdevl.vba.va.gov:80/appcomp-checker/AppCompCheckerService?xsd=1
     * <p>
     * To get rid of this property, apply a property customization to one 
     * of both of the following declarations to change their names: 
     * Gets the value of the rest property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the rest property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRest().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link MessageVO }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link MessageVO }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link Integer }{@code >}
     * {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     * {@link JAXBElement }{@code <}{@link Long }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * 
     */
    public List<JAXBElement<?>> getRest() {
        if (rest == null) {
            rest = new ArrayList<JAXBElement<?>>();
        }
        return this.rest;
    }

}
