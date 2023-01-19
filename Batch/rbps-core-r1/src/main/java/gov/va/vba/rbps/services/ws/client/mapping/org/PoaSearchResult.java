
package gov.va.vba.rbps.services.ws.client.mapping.org;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for poaSearchResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="poaSearchResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="bnftClaimId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="fileNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="message" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="powerOfAttorney" type="{http://org.services.vetsnet.vba.va.gov/}powerOfAttorneyDTO" minOccurs="0"/>
 *         &lt;element name="ptcpntId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "poaSearchResult", propOrder = {
    "bnftClaimId",
    "fileNumber",
    "message",
    "powerOfAttorney",
    "ptcpntId"
})
public class PoaSearchResult {

    protected Long bnftClaimId;
    protected String fileNumber;
    protected String message;
    protected PowerOfAttorneyDTO powerOfAttorney;
    protected Long ptcpntId;

    /**
     * Gets the value of the bnftClaimId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getBnftClaimId() {
        return bnftClaimId;
    }

    /**
     * Sets the value of the bnftClaimId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setBnftClaimId(Long value) {
        this.bnftClaimId = value;
    }

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
     * Gets the value of the message property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the value of the message property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessage(String value) {
        this.message = value;
    }

    /**
     * Gets the value of the powerOfAttorney property.
     * 
     * @return
     *     possible object is
     *     {@link PowerOfAttorneyDTO }
     *     
     */
    public PowerOfAttorneyDTO getPowerOfAttorney() {
        return powerOfAttorney;
    }

    /**
     * Sets the value of the powerOfAttorney property.
     * 
     * @param value
     *     allowed object is
     *     {@link PowerOfAttorneyDTO }
     *     
     */
    public void setPowerOfAttorney(PowerOfAttorneyDTO value) {
        this.powerOfAttorney = value;
    }

    /**
     * Gets the value of the ptcpntId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getPtcpntId() {
        return ptcpntId;
    }

    /**
     * Sets the value of the ptcpntId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setPtcpntId(Long value) {
        this.ptcpntId = value;
    }

}
