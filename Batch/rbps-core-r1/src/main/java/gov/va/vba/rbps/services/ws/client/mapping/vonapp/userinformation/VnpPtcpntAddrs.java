//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.01.31 at 10:36:09 AM CST 
//


package gov.va.vba.rbps.services.ws.client.mapping.vonapp.userinformation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for vnpPtcpntAddrs complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="vnpPtcpntAddrs">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="addrsOneTxt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="addrsThreeTxt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="addrsTwoTxt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="badAddrsInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cityNm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cntryNm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="countyNm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="efctvDt" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="eftWaiverTypeNm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="emailAddrsTxt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="endDt" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="fmsAddrsCodeTxt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="frgnPostalCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="group1VerifdTypeCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="jrnDt" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="jrnLctnId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="jrnObjId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="jrnStatusTypeCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="jrnUserId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lctnNm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mltyPostOfficeTypeCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mltyPostalTypeCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="postalCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="prvncNm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ptcpntAddrsTypeNm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sharedAddrsInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="trsuryAddrsFiveTxt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="trsuryAddrsFourTxt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="trsuryAddrsOneTxt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="trsuryAddrsSixTxt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="trsuryAddrsThreeTxt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="trsuryAddrsTwoTxt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="trsurySeqNbr" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="trtryNm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="vnpProcId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="vnpPtcpntAddrsId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="vnpPtcpntId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="zipFirstSuffixNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="zipPrefixNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="zipSecondSuffixNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "vnpPtcpntAddrs", propOrder = {
    "addrsOneTxt",
    "addrsThreeTxt",
    "addrsTwoTxt",
    "badAddrsInd",
    "cityNm",
    "cntryNm",
    "countyNm",
    "efctvDt",
    "eftWaiverTypeNm",
    "emailAddrsTxt",
    "endDt",
    "fmsAddrsCodeTxt",
    "frgnPostalCd",
    "group1VerifdTypeCd",
    "jrnDt",
    "jrnLctnId",
    "jrnObjId",
    "jrnStatusTypeCd",
    "jrnUserId",
    "lctnNm",
    "mltyPostOfficeTypeCd",
    "mltyPostalTypeCd",
    "postalCd",
    "prvncNm",
    "ptcpntAddrsTypeNm",
    "sharedAddrsInd",
    "trsuryAddrsFiveTxt",
    "trsuryAddrsFourTxt",
    "trsuryAddrsOneTxt",
    "trsuryAddrsSixTxt",
    "trsuryAddrsThreeTxt",
    "trsuryAddrsTwoTxt",
    "trsurySeqNbr",
    "trtryNm",
    "vnpProcId",
    "vnpPtcpntAddrsId",
    "vnpPtcpntId",
    "zipFirstSuffixNbr",
    "zipPrefixNbr",
    "zipSecondSuffixNbr"
})
public class VnpPtcpntAddrs {

    protected String addrsOneTxt;
    protected String addrsThreeTxt;
    protected String addrsTwoTxt;
    protected String badAddrsInd;
    protected String cityNm;
    protected String cntryNm;
    protected String countyNm;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar efctvDt;
    protected String eftWaiverTypeNm;
    protected String emailAddrsTxt;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar endDt;
    protected String fmsAddrsCodeTxt;
    protected String frgnPostalCd;
    protected String group1VerifdTypeCd;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar jrnDt;
    protected String jrnLctnId;
    protected String jrnObjId;
    protected String jrnStatusTypeCd;
    protected String jrnUserId;
    protected String lctnNm;
    protected String mltyPostOfficeTypeCd;
    protected String mltyPostalTypeCd;
    protected String postalCd;
    protected String prvncNm;
    protected String ptcpntAddrsTypeNm;
    protected String sharedAddrsInd;
    protected String trsuryAddrsFiveTxt;
    protected String trsuryAddrsFourTxt;
    protected String trsuryAddrsOneTxt;
    protected String trsuryAddrsSixTxt;
    protected String trsuryAddrsThreeTxt;
    protected String trsuryAddrsTwoTxt;
    protected Integer trsurySeqNbr;
    protected String trtryNm;
    protected Long vnpProcId;
    protected long vnpPtcpntAddrsId;
    protected Long vnpPtcpntId;
    protected String zipFirstSuffixNbr;
    protected String zipPrefixNbr;
    protected String zipSecondSuffixNbr;

    /**
     * Gets the value of the addrsOneTxt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddrsOneTxt() {
        return addrsOneTxt;
    }

    /**
     * Sets the value of the addrsOneTxt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddrsOneTxt(String value) {
        this.addrsOneTxt = value;
    }

    /**
     * Gets the value of the addrsThreeTxt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddrsThreeTxt() {
        return addrsThreeTxt;
    }

    /**
     * Sets the value of the addrsThreeTxt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddrsThreeTxt(String value) {
        this.addrsThreeTxt = value;
    }

    /**
     * Gets the value of the addrsTwoTxt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddrsTwoTxt() {
        return addrsTwoTxt;
    }

    /**
     * Sets the value of the addrsTwoTxt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddrsTwoTxt(String value) {
        this.addrsTwoTxt = value;
    }

    /**
     * Gets the value of the badAddrsInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBadAddrsInd() {
        return badAddrsInd;
    }

    /**
     * Sets the value of the badAddrsInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBadAddrsInd(String value) {
        this.badAddrsInd = value;
    }

    /**
     * Gets the value of the cityNm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCityNm() {
        return cityNm;
    }

    /**
     * Sets the value of the cityNm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCityNm(String value) {
        this.cityNm = value;
    }

    /**
     * Gets the value of the cntryNm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCntryNm() {
        return cntryNm;
    }

    /**
     * Sets the value of the cntryNm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCntryNm(String value) {
        this.cntryNm = value;
    }

    /**
     * Gets the value of the countyNm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCountyNm() {
        return countyNm;
    }

    /**
     * Sets the value of the countyNm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCountyNm(String value) {
        this.countyNm = value;
    }

    /**
     * Gets the value of the efctvDt property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEfctvDt() {
        return efctvDt;
    }

    /**
     * Sets the value of the efctvDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEfctvDt(XMLGregorianCalendar value) {
        this.efctvDt = value;
    }

    /**
     * Gets the value of the eftWaiverTypeNm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEftWaiverTypeNm() {
        return eftWaiverTypeNm;
    }

    /**
     * Sets the value of the eftWaiverTypeNm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEftWaiverTypeNm(String value) {
        this.eftWaiverTypeNm = value;
    }

    /**
     * Gets the value of the emailAddrsTxt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmailAddrsTxt() {
        return emailAddrsTxt;
    }

    /**
     * Sets the value of the emailAddrsTxt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmailAddrsTxt(String value) {
        this.emailAddrsTxt = value;
    }

    /**
     * Gets the value of the endDt property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEndDt() {
        return endDt;
    }

    /**
     * Sets the value of the endDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEndDt(XMLGregorianCalendar value) {
        this.endDt = value;
    }

    /**
     * Gets the value of the fmsAddrsCodeTxt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFmsAddrsCodeTxt() {
        return fmsAddrsCodeTxt;
    }

    /**
     * Sets the value of the fmsAddrsCodeTxt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFmsAddrsCodeTxt(String value) {
        this.fmsAddrsCodeTxt = value;
    }

    /**
     * Gets the value of the frgnPostalCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFrgnPostalCd() {
        return frgnPostalCd;
    }

    /**
     * Sets the value of the frgnPostalCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFrgnPostalCd(String value) {
        this.frgnPostalCd = value;
    }

    /**
     * Gets the value of the group1VerifdTypeCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGroup1VerifdTypeCd() {
        return group1VerifdTypeCd;
    }

    /**
     * Sets the value of the group1VerifdTypeCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGroup1VerifdTypeCd(String value) {
        this.group1VerifdTypeCd = value;
    }

    /**
     * Gets the value of the jrnDt property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getJrnDt() {
        return jrnDt;
    }

    /**
     * Sets the value of the jrnDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setJrnDt(XMLGregorianCalendar value) {
        this.jrnDt = value;
    }

    /**
     * Gets the value of the jrnLctnId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJrnLctnId() {
        return jrnLctnId;
    }

    /**
     * Sets the value of the jrnLctnId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJrnLctnId(String value) {
        this.jrnLctnId = value;
    }

    /**
     * Gets the value of the jrnObjId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJrnObjId() {
        return jrnObjId;
    }

    /**
     * Sets the value of the jrnObjId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJrnObjId(String value) {
        this.jrnObjId = value;
    }

    /**
     * Gets the value of the jrnStatusTypeCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJrnStatusTypeCd() {
        return jrnStatusTypeCd;
    }

    /**
     * Sets the value of the jrnStatusTypeCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJrnStatusTypeCd(String value) {
        this.jrnStatusTypeCd = value;
    }

    /**
     * Gets the value of the jrnUserId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJrnUserId() {
        return jrnUserId;
    }

    /**
     * Sets the value of the jrnUserId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJrnUserId(String value) {
        this.jrnUserId = value;
    }

    /**
     * Gets the value of the lctnNm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLctnNm() {
        return lctnNm;
    }

    /**
     * Sets the value of the lctnNm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLctnNm(String value) {
        this.lctnNm = value;
    }

    /**
     * Gets the value of the mltyPostOfficeTypeCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMltyPostOfficeTypeCd() {
        return mltyPostOfficeTypeCd;
    }

    /**
     * Sets the value of the mltyPostOfficeTypeCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMltyPostOfficeTypeCd(String value) {
        this.mltyPostOfficeTypeCd = value;
    }

    /**
     * Gets the value of the mltyPostalTypeCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMltyPostalTypeCd() {
        return mltyPostalTypeCd;
    }

    /**
     * Sets the value of the mltyPostalTypeCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMltyPostalTypeCd(String value) {
        this.mltyPostalTypeCd = value;
    }

    /**
     * Gets the value of the postalCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPostalCd() {
        return postalCd;
    }

    /**
     * Sets the value of the postalCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPostalCd(String value) {
        this.postalCd = value;
    }

    /**
     * Gets the value of the prvncNm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrvncNm() {
        return prvncNm;
    }

    /**
     * Sets the value of the prvncNm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrvncNm(String value) {
        this.prvncNm = value;
    }

    /**
     * Gets the value of the ptcpntAddrsTypeNm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPtcpntAddrsTypeNm() {
        return ptcpntAddrsTypeNm;
    }

    /**
     * Sets the value of the ptcpntAddrsTypeNm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPtcpntAddrsTypeNm(String value) {
        this.ptcpntAddrsTypeNm = value;
    }

    /**
     * Gets the value of the sharedAddrsInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSharedAddrsInd() {
        return sharedAddrsInd;
    }

    /**
     * Sets the value of the sharedAddrsInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSharedAddrsInd(String value) {
        this.sharedAddrsInd = value;
    }

    /**
     * Gets the value of the trsuryAddrsFiveTxt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTrsuryAddrsFiveTxt() {
        return trsuryAddrsFiveTxt;
    }

    /**
     * Sets the value of the trsuryAddrsFiveTxt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTrsuryAddrsFiveTxt(String value) {
        this.trsuryAddrsFiveTxt = value;
    }

    /**
     * Gets the value of the trsuryAddrsFourTxt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTrsuryAddrsFourTxt() {
        return trsuryAddrsFourTxt;
    }

    /**
     * Sets the value of the trsuryAddrsFourTxt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTrsuryAddrsFourTxt(String value) {
        this.trsuryAddrsFourTxt = value;
    }

    /**
     * Gets the value of the trsuryAddrsOneTxt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTrsuryAddrsOneTxt() {
        return trsuryAddrsOneTxt;
    }

    /**
     * Sets the value of the trsuryAddrsOneTxt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTrsuryAddrsOneTxt(String value) {
        this.trsuryAddrsOneTxt = value;
    }

    /**
     * Gets the value of the trsuryAddrsSixTxt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTrsuryAddrsSixTxt() {
        return trsuryAddrsSixTxt;
    }

    /**
     * Sets the value of the trsuryAddrsSixTxt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTrsuryAddrsSixTxt(String value) {
        this.trsuryAddrsSixTxt = value;
    }

    /**
     * Gets the value of the trsuryAddrsThreeTxt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTrsuryAddrsThreeTxt() {
        return trsuryAddrsThreeTxt;
    }

    /**
     * Sets the value of the trsuryAddrsThreeTxt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTrsuryAddrsThreeTxt(String value) {
        this.trsuryAddrsThreeTxt = value;
    }

    /**
     * Gets the value of the trsuryAddrsTwoTxt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTrsuryAddrsTwoTxt() {
        return trsuryAddrsTwoTxt;
    }

    /**
     * Sets the value of the trsuryAddrsTwoTxt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTrsuryAddrsTwoTxt(String value) {
        this.trsuryAddrsTwoTxt = value;
    }

    /**
     * Gets the value of the trsurySeqNbr property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTrsurySeqNbr() {
        return trsurySeqNbr;
    }

    /**
     * Sets the value of the trsurySeqNbr property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTrsurySeqNbr(Integer value) {
        this.trsurySeqNbr = value;
    }

    /**
     * Gets the value of the trtryNm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTrtryNm() {
        return trtryNm;
    }

    /**
     * Sets the value of the trtryNm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTrtryNm(String value) {
        this.trtryNm = value;
    }

    /**
     * Gets the value of the vnpProcId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getVnpProcId() {
        return vnpProcId;
    }

    /**
     * Sets the value of the vnpProcId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setVnpProcId(Long value) {
        this.vnpProcId = value;
    }

    /**
     * Gets the value of the vnpPtcpntAddrsId property.
     * 
     */
    public long getVnpPtcpntAddrsId() {
        return vnpPtcpntAddrsId;
    }

    /**
     * Sets the value of the vnpPtcpntAddrsId property.
     * 
     */
    public void setVnpPtcpntAddrsId(long value) {
        this.vnpPtcpntAddrsId = value;
    }

    /**
     * Gets the value of the vnpPtcpntId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getVnpPtcpntId() {
        return vnpPtcpntId;
    }

    /**
     * Sets the value of the vnpPtcpntId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setVnpPtcpntId(Long value) {
        this.vnpPtcpntId = value;
    }

    /**
     * Gets the value of the zipFirstSuffixNbr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZipFirstSuffixNbr() {
        return zipFirstSuffixNbr;
    }

    /**
     * Sets the value of the zipFirstSuffixNbr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZipFirstSuffixNbr(String value) {
        this.zipFirstSuffixNbr = value;
    }

    /**
     * Gets the value of the zipPrefixNbr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZipPrefixNbr() {
        return zipPrefixNbr;
    }

    /**
     * Sets the value of the zipPrefixNbr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZipPrefixNbr(String value) {
        this.zipPrefixNbr = value;
    }

    /**
     * Gets the value of the zipSecondSuffixNbr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZipSecondSuffixNbr() {
        return zipSecondSuffixNbr;
    }

    /**
     * Sets the value of the zipSecondSuffixNbr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZipSecondSuffixNbr(String value) {
        this.zipSecondSuffixNbr = value;
    }

}
