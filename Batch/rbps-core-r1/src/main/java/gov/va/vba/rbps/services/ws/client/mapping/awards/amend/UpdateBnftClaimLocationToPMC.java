package gov.va.vba.rbps.services.ws.client.mapping.awards.amend;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for updateBnftClaimLocationToPMC complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="updateBnftClaimLocationToPMC">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="claimID" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="ssn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "updateBnftClaimLocationToPMC", propOrder = {
    "claimID",
    "ssn",
    "processingPensions"
})
public class UpdateBnftClaimLocationToPMC {

    protected Long claimID;
    protected String ssn;
    protected boolean processingPensions;

    /**
     * Gets the value of the claimID property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getClaimID() {
        return claimID;
    }

    /**
     * Sets the value of the claimID property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setClaimID(Long value) {
        this.claimID = value;
    }

    /**
     * Gets the value of the ssn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSsn() {
        return ssn;
    }

    /**
     * Sets the value of the ssn property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setSsn(String value) {
        this.ssn = value;
    }

    public boolean getProcessingPensions() {
        return processingPensions;
    }

    /**
     * Sets the value of the processingPensions property.
     *
     * @param processingPensions
     *     allowed object is
     *     {@link boolean }
     *
     */
    public void setProcessingPensions(boolean processingPensions) {
        this.processingPensions = processingPensions;
    }

}
