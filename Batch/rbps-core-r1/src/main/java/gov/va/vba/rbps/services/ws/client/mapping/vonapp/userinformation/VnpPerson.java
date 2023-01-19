//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.01.31 at 10:36:09 AM CST 
//


package gov.va.vba.rbps.services.ws.client.mapping.vonapp.userinformation;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for vnpPerson complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="vnpPerson">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="birthCityNm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="birthCntryNm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="birthStateCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="brthdyDt" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="cmptnyDecnTypeCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="deathDt" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="depNbr" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="empInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="entlmtTypeCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ethnicTypeCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="everMariedInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fidDecnCategyTypeCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fileNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="firstNm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="firstNmKey" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="frgnSvcNbr" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="genderCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="jrnDt" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="jrnLctnId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="jrnObjId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="jrnStatusTypeCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="jrnUserId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lastNm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lastNmKey" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="lgyEntlmtTypeCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="martlStatusTypeCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="middleNm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="middleNmKey" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="mltyPersonInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="monthsPresntEmplyrNbr" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="netWorthAmt" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="noSsnReasonTypeCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ocptnTxt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="personDeathCauseTypeNm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="personTypeNm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="potntlDngrsInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="raceTypeNm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sbstncAmt" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="serousEmplmtHndcapInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="slttnTypeNm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="spinaBifidaInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="spouseNum" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="ssnNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ssnVrfctnStatusTypeCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="suffixNm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="suffixNmKey" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="taxAbtmntCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="termnlDigitNbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="titleTxt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="vetInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="vetTypeNm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="vnpProcId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="vnpPtcpntId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="vnpSchoolChildInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="vnpSruslyDsabldInd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="yearsPresntEmplyrNbr" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "vnpPerson", propOrder = {
    "birthCityNm",
    "birthCntryNm",
    "birthStateCd",
    "brthdyDt",
    "cmptnyDecnTypeCd",
    "deathDt",
    "depNbr",
    "empInd",
    "entlmtTypeCd",
    "ethnicTypeCd",
    "everMariedInd",
    "fidDecnCategyTypeCd",
    "fileNbr",
    "firstNm",
    "firstNmKey",
    "frgnSvcNbr",
    "genderCd",
    "jrnDt",
    "jrnLctnId",
    "jrnObjId",
    "jrnStatusTypeCd",
    "jrnUserId",
    "lastNm",
    "lastNmKey",
    "lgyEntlmtTypeCd",
    "martlStatusTypeCd",
    "middleNm",
    "middleNmKey",
    "mltyPersonInd",
    "monthsPresntEmplyrNbr",
    "netWorthAmt",
    "noSsnReasonTypeCd",
    "ocptnTxt",
    "personDeathCauseTypeNm",
    "personTypeNm",
    "potntlDngrsInd",
    "raceTypeNm",
    "sbstncAmt",
    "serousEmplmtHndcapInd",
    "slttnTypeNm",
    "spinaBifidaInd",
    "spouseNum",
    "ssnNbr",
    "ssnVrfctnStatusTypeCd",
    "suffixNm",
    "suffixNmKey",
    "taxAbtmntCd",
    "termnlDigitNbr",
    "titleTxt",
    "vetInd",
    "vetTypeNm",
    "vnpProcId",
    "vnpPtcpntId",
    "vnpSchoolChildInd",
    "vnpSruslyDsabldInd",
    "yearsPresntEmplyrNbr"
})
public class VnpPerson {

    protected String birthCityNm;
    protected String birthCntryNm;
    protected String birthStateCd;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar brthdyDt;
    protected String cmptnyDecnTypeCd;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar deathDt;
    protected BigDecimal depNbr;
    protected String empInd;
    protected String entlmtTypeCd;
    protected String ethnicTypeCd;
    protected String everMariedInd;
    protected String fidDecnCategyTypeCd;
    protected String fileNbr;
    protected String firstNm;
    protected BigDecimal firstNmKey;
    protected BigDecimal frgnSvcNbr;
    protected String genderCd;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar jrnDt;
    protected String jrnLctnId;
    protected String jrnObjId;
    protected String jrnStatusTypeCd;
    protected String jrnUserId;
    protected String lastNm;
    protected BigDecimal lastNmKey;
    protected String lgyEntlmtTypeCd;
    protected String martlStatusTypeCd;
    protected String middleNm;
    protected BigDecimal middleNmKey;
    protected String mltyPersonInd;
    protected BigDecimal monthsPresntEmplyrNbr;
    protected BigDecimal netWorthAmt;
    protected String noSsnReasonTypeCd;
    protected String ocptnTxt;
    protected String personDeathCauseTypeNm;
    protected String personTypeNm;
    protected String potntlDngrsInd;
    protected String raceTypeNm;
    protected BigDecimal sbstncAmt;
    protected String serousEmplmtHndcapInd;
    protected String slttnTypeNm;
    protected String spinaBifidaInd;
    protected BigDecimal spouseNum;
    protected String ssnNbr;
    protected String ssnVrfctnStatusTypeCd;
    protected String suffixNm;
    protected BigDecimal suffixNmKey;
    protected String taxAbtmntCd;
    protected String termnlDigitNbr;
    protected String titleTxt;
    protected String vetInd;
    protected String vetTypeNm;
    protected long vnpProcId;
    protected long vnpPtcpntId;
    protected String vnpSchoolChildInd;
    protected String vnpSruslyDsabldInd;
    protected BigDecimal yearsPresntEmplyrNbr;

    /**
     * Gets the value of the birthCityNm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBirthCityNm() {
        return birthCityNm;
    }

    /**
     * Sets the value of the birthCityNm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBirthCityNm(String value) {
        this.birthCityNm = value;
    }

    /**
     * Gets the value of the birthCntryNm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBirthCntryNm() {
        return birthCntryNm;
    }

    /**
     * Sets the value of the birthCntryNm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBirthCntryNm(String value) {
        this.birthCntryNm = value;
    }

    /**
     * Gets the value of the birthStateCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBirthStateCd() {
        return birthStateCd;
    }

    /**
     * Sets the value of the birthStateCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBirthStateCd(String value) {
        this.birthStateCd = value;
    }

    /**
     * Gets the value of the brthdyDt property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getBrthdyDt() {
        return brthdyDt;
    }

    /**
     * Sets the value of the brthdyDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setBrthdyDt(XMLGregorianCalendar value) {
        this.brthdyDt = value;
    }

    /**
     * Gets the value of the cmptnyDecnTypeCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCmptnyDecnTypeCd() {
        return cmptnyDecnTypeCd;
    }

    /**
     * Sets the value of the cmptnyDecnTypeCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCmptnyDecnTypeCd(String value) {
        this.cmptnyDecnTypeCd = value;
    }

    /**
     * Gets the value of the deathDt property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDeathDt() {
        return deathDt;
    }

    /**
     * Sets the value of the deathDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDeathDt(XMLGregorianCalendar value) {
        this.deathDt = value;
    }

    /**
     * Gets the value of the depNbr property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDepNbr() {
        return depNbr;
    }

    /**
     * Sets the value of the depNbr property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDepNbr(BigDecimal value) {
        this.depNbr = value;
    }

    /**
     * Gets the value of the empInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmpInd() {
        return empInd;
    }

    /**
     * Sets the value of the empInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmpInd(String value) {
        this.empInd = value;
    }

    /**
     * Gets the value of the entlmtTypeCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEntlmtTypeCd() {
        return entlmtTypeCd;
    }

    /**
     * Sets the value of the entlmtTypeCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEntlmtTypeCd(String value) {
        this.entlmtTypeCd = value;
    }

    /**
     * Gets the value of the ethnicTypeCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEthnicTypeCd() {
        return ethnicTypeCd;
    }

    /**
     * Sets the value of the ethnicTypeCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEthnicTypeCd(String value) {
        this.ethnicTypeCd = value;
    }

    /**
     * Gets the value of the everMariedInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEverMariedInd() {
        return everMariedInd;
    }

    /**
     * Sets the value of the everMariedInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEverMariedInd(String value) {
        this.everMariedInd = value;
    }

    /**
     * Gets the value of the fidDecnCategyTypeCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFidDecnCategyTypeCd() {
        return fidDecnCategyTypeCd;
    }

    /**
     * Sets the value of the fidDecnCategyTypeCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFidDecnCategyTypeCd(String value) {
        this.fidDecnCategyTypeCd = value;
    }

    /**
     * Gets the value of the fileNbr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFileNbr() {
        return fileNbr;
    }

    /**
     * Sets the value of the fileNbr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFileNbr(String value) {
        this.fileNbr = value;
    }

    /**
     * Gets the value of the firstNm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFirstNm() {
        return firstNm;
    }

    /**
     * Sets the value of the firstNm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFirstNm(String value) {
        this.firstNm = value;
    }

    /**
     * Gets the value of the firstNmKey property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getFirstNmKey() {
        return firstNmKey;
    }

    /**
     * Sets the value of the firstNmKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setFirstNmKey(BigDecimal value) {
        this.firstNmKey = value;
    }

    /**
     * Gets the value of the frgnSvcNbr property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getFrgnSvcNbr() {
        return frgnSvcNbr;
    }

    /**
     * Sets the value of the frgnSvcNbr property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setFrgnSvcNbr(BigDecimal value) {
        this.frgnSvcNbr = value;
    }

    /**
     * Gets the value of the genderCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGenderCd() {
        return genderCd;
    }

    /**
     * Sets the value of the genderCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGenderCd(String value) {
        this.genderCd = value;
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
     * Gets the value of the lastNm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastNm() {
        return lastNm;
    }

    /**
     * Sets the value of the lastNm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastNm(String value) {
        this.lastNm = value;
    }

    /**
     * Gets the value of the lastNmKey property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getLastNmKey() {
        return lastNmKey;
    }

    /**
     * Sets the value of the lastNmKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setLastNmKey(BigDecimal value) {
        this.lastNmKey = value;
    }

    /**
     * Gets the value of the lgyEntlmtTypeCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLgyEntlmtTypeCd() {
        return lgyEntlmtTypeCd;
    }

    /**
     * Sets the value of the lgyEntlmtTypeCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLgyEntlmtTypeCd(String value) {
        this.lgyEntlmtTypeCd = value;
    }

    /**
     * Gets the value of the martlStatusTypeCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMartlStatusTypeCd() {
        return martlStatusTypeCd;
    }

    /**
     * Sets the value of the martlStatusTypeCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMartlStatusTypeCd(String value) {
        this.martlStatusTypeCd = value;
    }

    /**
     * Gets the value of the middleNm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMiddleNm() {
        return middleNm;
    }

    /**
     * Sets the value of the middleNm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMiddleNm(String value) {
        this.middleNm = value;
    }

    /**
     * Gets the value of the middleNmKey property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMiddleNmKey() {
        return middleNmKey;
    }

    /**
     * Sets the value of the middleNmKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMiddleNmKey(BigDecimal value) {
        this.middleNmKey = value;
    }

    /**
     * Gets the value of the mltyPersonInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMltyPersonInd() {
        return mltyPersonInd;
    }

    /**
     * Sets the value of the mltyPersonInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMltyPersonInd(String value) {
        this.mltyPersonInd = value;
    }

    /**
     * Gets the value of the monthsPresntEmplyrNbr property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMonthsPresntEmplyrNbr() {
        return monthsPresntEmplyrNbr;
    }

    /**
     * Sets the value of the monthsPresntEmplyrNbr property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMonthsPresntEmplyrNbr(BigDecimal value) {
        this.monthsPresntEmplyrNbr = value;
    }

    /**
     * Gets the value of the netWorthAmt property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getNetWorthAmt() {
        return netWorthAmt;
    }

    /**
     * Sets the value of the netWorthAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setNetWorthAmt(BigDecimal value) {
        this.netWorthAmt = value;
    }

    /**
     * Gets the value of the noSsnReasonTypeCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNoSsnReasonTypeCd() {
        return noSsnReasonTypeCd;
    }

    /**
     * Sets the value of the noSsnReasonTypeCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNoSsnReasonTypeCd(String value) {
        this.noSsnReasonTypeCd = value;
    }

    /**
     * Gets the value of the ocptnTxt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOcptnTxt() {
        return ocptnTxt;
    }

    /**
     * Sets the value of the ocptnTxt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOcptnTxt(String value) {
        this.ocptnTxt = value;
    }

    /**
     * Gets the value of the personDeathCauseTypeNm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPersonDeathCauseTypeNm() {
        return personDeathCauseTypeNm;
    }

    /**
     * Sets the value of the personDeathCauseTypeNm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPersonDeathCauseTypeNm(String value) {
        this.personDeathCauseTypeNm = value;
    }

    /**
     * Gets the value of the personTypeNm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPersonTypeNm() {
        return personTypeNm;
    }

    /**
     * Sets the value of the personTypeNm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPersonTypeNm(String value) {
        this.personTypeNm = value;
    }

    /**
     * Gets the value of the potntlDngrsInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPotntlDngrsInd() {
        return potntlDngrsInd;
    }

    /**
     * Sets the value of the potntlDngrsInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPotntlDngrsInd(String value) {
        this.potntlDngrsInd = value;
    }

    /**
     * Gets the value of the raceTypeNm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRaceTypeNm() {
        return raceTypeNm;
    }

    /**
     * Sets the value of the raceTypeNm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRaceTypeNm(String value) {
        this.raceTypeNm = value;
    }

    /**
     * Gets the value of the sbstncAmt property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSbstncAmt() {
        return sbstncAmt;
    }

    /**
     * Sets the value of the sbstncAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSbstncAmt(BigDecimal value) {
        this.sbstncAmt = value;
    }

    /**
     * Gets the value of the serousEmplmtHndcapInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSerousEmplmtHndcapInd() {
        return serousEmplmtHndcapInd;
    }

    /**
     * Sets the value of the serousEmplmtHndcapInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSerousEmplmtHndcapInd(String value) {
        this.serousEmplmtHndcapInd = value;
    }

    /**
     * Gets the value of the slttnTypeNm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSlttnTypeNm() {
        return slttnTypeNm;
    }

    /**
     * Sets the value of the slttnTypeNm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSlttnTypeNm(String value) {
        this.slttnTypeNm = value;
    }

    /**
     * Gets the value of the spinaBifidaInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpinaBifidaInd() {
        return spinaBifidaInd;
    }

    /**
     * Sets the value of the spinaBifidaInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpinaBifidaInd(String value) {
        this.spinaBifidaInd = value;
    }

    /**
     * Gets the value of the spouseNum property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSpouseNum() {
        return spouseNum;
    }

    /**
     * Sets the value of the spouseNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSpouseNum(BigDecimal value) {
        this.spouseNum = value;
    }

    /**
     * Gets the value of the ssnNbr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSsnNbr() {
        return ssnNbr;
    }

    /**
     * Sets the value of the ssnNbr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSsnNbr(String value) {
        this.ssnNbr = value;
    }

    /**
     * Gets the value of the ssnVrfctnStatusTypeCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSsnVrfctnStatusTypeCd() {
        return ssnVrfctnStatusTypeCd;
    }

    /**
     * Sets the value of the ssnVrfctnStatusTypeCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSsnVrfctnStatusTypeCd(String value) {
        this.ssnVrfctnStatusTypeCd = value;
    }

    /**
     * Gets the value of the suffixNm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSuffixNm() {
        return suffixNm;
    }

    /**
     * Sets the value of the suffixNm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSuffixNm(String value) {
        this.suffixNm = value;
    }

    /**
     * Gets the value of the suffixNmKey property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSuffixNmKey() {
        return suffixNmKey;
    }

    /**
     * Sets the value of the suffixNmKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSuffixNmKey(BigDecimal value) {
        this.suffixNmKey = value;
    }

    /**
     * Gets the value of the taxAbtmntCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTaxAbtmntCd() {
        return taxAbtmntCd;
    }

    /**
     * Sets the value of the taxAbtmntCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTaxAbtmntCd(String value) {
        this.taxAbtmntCd = value;
    }

    /**
     * Gets the value of the termnlDigitNbr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTermnlDigitNbr() {
        return termnlDigitNbr;
    }

    /**
     * Sets the value of the termnlDigitNbr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTermnlDigitNbr(String value) {
        this.termnlDigitNbr = value;
    }

    /**
     * Gets the value of the titleTxt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitleTxt() {
        return titleTxt;
    }

    /**
     * Sets the value of the titleTxt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitleTxt(String value) {
        this.titleTxt = value;
    }

    /**
     * Gets the value of the vetInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVetInd() {
        return vetInd;
    }

    /**
     * Sets the value of the vetInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVetInd(String value) {
        this.vetInd = value;
    }

    /**
     * Gets the value of the vetTypeNm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVetTypeNm() {
        return vetTypeNm;
    }

    /**
     * Sets the value of the vetTypeNm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVetTypeNm(String value) {
        this.vetTypeNm = value;
    }

    /**
     * Gets the value of the vnpProcId property.
     * 
     */
    public long getVnpProcId() {
        return vnpProcId;
    }

    /**
     * Sets the value of the vnpProcId property.
     * 
     */
    public void setVnpProcId(long value) {
        this.vnpProcId = value;
    }

    /**
     * Gets the value of the vnpPtcpntId property.
     * 
     */
    public long getVnpPtcpntId() {
        return vnpPtcpntId;
    }

    /**
     * Sets the value of the vnpPtcpntId property.
     * 
     */
    public void setVnpPtcpntId(long value) {
        this.vnpPtcpntId = value;
    }

    /**
     * Gets the value of the vnpSchoolChildInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVnpSchoolChildInd() {
        return vnpSchoolChildInd;
    }

    /**
     * Sets the value of the vnpSchoolChildInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVnpSchoolChildInd(String value) {
        this.vnpSchoolChildInd = value;
    }

    /**
     * Gets the value of the vnpSruslyDsabldInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVnpSruslyDsabldInd() {
        return vnpSruslyDsabldInd;
    }

    /**
     * Sets the value of the vnpSruslyDsabldInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVnpSruslyDsabldInd(String value) {
        this.vnpSruslyDsabldInd = value;
    }

    /**
     * Gets the value of the yearsPresntEmplyrNbr property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getYearsPresntEmplyrNbr() {
        return yearsPresntEmplyrNbr;
    }

    /**
     * Sets the value of the yearsPresntEmplyrNbr property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setYearsPresntEmplyrNbr(BigDecimal value) {
        this.yearsPresntEmplyrNbr = value;
    }

}
