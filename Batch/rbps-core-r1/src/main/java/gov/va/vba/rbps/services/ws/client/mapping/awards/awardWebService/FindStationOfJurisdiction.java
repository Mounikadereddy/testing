
package gov.va.vba.rbps.services.ws.client.mapping.awards.awardWebService;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for findStationOfJurisdiction complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="findStationOfJurisdiction">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ptcpntVetId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="ptcpntBeneId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
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
@XmlType(name = "findStationOfJurisdiction", propOrder = {
    "ptcpntVetId",
    "ptcpntBeneId"
})
public class FindStationOfJurisdiction {

    protected Long ptcpntVetId;
    protected Long ptcpntBeneId;

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

    /**
     * Gets the value of the ptcpntBeneId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getPtcpntBeneId() {
        return ptcpntBeneId;
    }

    /**
     * Sets the value of the ptcpntBeneId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setPtcpntBeneId(Long value) {
        this.ptcpntBeneId = value;
    }

}
