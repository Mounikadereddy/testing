//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-7
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2012.01.09 at 03:22:17 PM CST
//


package gov.va.vba.rbps.services.ws.client.mapping.awards.dependencydecision;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for vnErrorsVO complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="vnErrorsVO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="datasource" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fieldNm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="jrnDt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="jrnLctnId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="jrnObjId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="jrnStatusTypeCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="jrnUserId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="log" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="message" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="object" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="oraMsg" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rowNum" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="vnError" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="vnMsg" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "vnErrorsVO", propOrder = {
    "code",
    "datasource",
    "fieldNm",
    "jrnDt",
    "jrnLctnId",
    "jrnObjId",
    "jrnStatusTypeCd",
    "jrnUserId",
    "log",
    "message",
    "object",
    "oraMsg",
    "rowNum",
    "vnError",
    "vnMsg"
})
public class VnErrorsVO {

    protected String code;
    protected String datasource;
    protected String fieldNm;
    protected String jrnDt;
    protected String jrnLctnId;
    protected String jrnObjId;
    protected String jrnStatusTypeCd;
    protected String jrnUserId;
    protected String log;
    protected String message;
    protected String object;
    protected String oraMsg;
    protected Integer rowNum;
    protected Integer vnError;
    protected String vnMsg;

    /**
     * Gets the value of the code property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the value of the code property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setCode(final String value) {
        this.code = value;
    }

    /**
     * Gets the value of the datasource property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getDatasource() {
        return datasource;
    }

    /**
     * Sets the value of the datasource property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setDatasource(final String value) {
        this.datasource = value;
    }

    /**
     * Gets the value of the fieldNm property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getFieldNm() {
        return fieldNm;
    }

    /**
     * Sets the value of the fieldNm property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setFieldNm(final String value) {
        this.fieldNm = value;
    }

    /**
     * Gets the value of the jrnDt property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getJrnDt() {
        return jrnDt;
    }

    /**
     * Sets the value of the jrnDt property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setJrnDt(final String value) {
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
    public void setJrnLctnId(final String value) {
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
    public void setJrnObjId(final String value) {
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
    public void setJrnStatusTypeCd(final String value) {
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
    public void setJrnUserId(final String value) {
        this.jrnUserId = value;
    }

    /**
     * Gets the value of the log property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getLog() {
        return log;
    }

    /**
     * Sets the value of the log property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setLog(final String value) {
        this.log = value;
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
    public void setMessage(final String value) {
        this.message = value;
    }

    /**
     * Gets the value of the object property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getObject() {
        return object;
    }

    /**
     * Sets the value of the object property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setObject(final String value) {
        this.object = value;
    }

    /**
     * Gets the value of the oraMsg property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getOraMsg() {
        return oraMsg;
    }

    /**
     * Sets the value of the oraMsg property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setOraMsg(final String value) {
        this.oraMsg = value;
    }

    /**
     * Gets the value of the rowNum property.
     *
     * @return
     *     possible object is
     *     {@link Integer }
     *
     */
    public Integer getRowNum() {
        return rowNum;
    }

    /**
     * Sets the value of the rowNum property.
     *
     * @param value
     *     allowed object is
     *     {@link Integer }
     *
     */
    public void setRowNum(final Integer value) {
        this.rowNum = value;
    }

    /**
     * Gets the value of the vnError property.
     *
     * @return
     *     possible object is
     *     {@link Integer }
     *
     */
    public Integer getVnError() {
        return vnError;
    }

    /**
     * Sets the value of the vnError property.
     *
     * @param value
     *     allowed object is
     *     {@link Integer }
     *
     */
    public void setVnError(final Integer value) {
        this.vnError = value;
    }

    /**
     * Gets the value of the vnMsg property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getVnMsg() {
        return vnMsg;
    }

    /**
     * Sets the value of the vnMsg property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setVnMsg(final String value) {
        this.vnMsg = value;
    }

}
