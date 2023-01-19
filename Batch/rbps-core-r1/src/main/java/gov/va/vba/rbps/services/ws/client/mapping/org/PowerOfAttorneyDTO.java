
package gov.va.vba.rbps.services.ws.client.mapping.org;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for powerOfAttorneyDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="powerOfAttorneyDTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="authznChangeClmantAddrsInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="authznPoaAccessInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="legacyPoaCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="orgTypeNm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "powerOfAttorneyDTO", propOrder = {
    "authznChangeClmantAddrsInd",
    "authznPoaAccessInd",
    "legacyPoaCd",
    "nm",
    "orgTypeNm",
    "ptcpntId"
})
public class PowerOfAttorneyDTO {

    protected String authznChangeClmantAddrsInd;
    protected String authznPoaAccessInd;
    protected String legacyPoaCd;
    protected String nm;
    protected String orgTypeNm;
    protected Long ptcpntId;

    /**
     * Gets the value of the authznChangeClmantAddrsInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthznChangeClmantAddrsInd() {
        return authznChangeClmantAddrsInd;
    }

    /**
     * Sets the value of the authznChangeClmantAddrsInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthznChangeClmantAddrsInd(String value) {
        this.authznChangeClmantAddrsInd = value;
    }

    /**
     * Gets the value of the authznPoaAccessInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthznPoaAccessInd() {
        return authznPoaAccessInd;
    }

    /**
     * Sets the value of the authznPoaAccessInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthznPoaAccessInd(String value) {
        this.authznPoaAccessInd = value;
    }

    /**
     * Gets the value of the legacyPoaCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLegacyPoaCd() {
        return legacyPoaCd;
    }

    /**
     * Sets the value of the legacyPoaCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLegacyPoaCd(String value) {
        this.legacyPoaCd = value;
    }

    /**
     * Gets the value of the nm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNm() {
        return nm;
    }

    /**
     * Sets the value of the nm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNm(String value) {
        this.nm = value;
    }

    /**
     * Gets the value of the orgTypeNm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrgTypeNm() {
        return orgTypeNm;
    }

    /**
     * Sets the value of the orgTypeNm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrgTypeNm(String value) {
        this.orgTypeNm = value;
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
