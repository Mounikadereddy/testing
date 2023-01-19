package gov.va.vba.rbps.coreframework.dto;


import java.math.BigDecimal;
import java.util.Date;

public class VetsNetSystem {
    private String fieldName;
    private Date valueDate;
    private String valueText;
    private BigDecimal valueNumber;
    private Date jrnDt;
    private String jrnLctnId;
    private String jrnObjId;
    private String jrnStatusTypeCd;
    private String jrnUserId;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Date getValueDate() {
        return valueDate;
    }

    public void setValueDate(Date valueDate) {
        this.valueDate = valueDate;
    }

    public String getValueText() {
        return valueText;
    }

    public void setValueText(String valueText) {
        this.valueText = valueText;
    }

    public BigDecimal getValueNumber() {
        return valueNumber;
    }

    public void setValueNumber(BigDecimal valueNumber) {
        this.valueNumber = valueNumber;
    }

    public Date getJrnDt() {
        return jrnDt;
    }

    public void setJrnDt(Date jrnDt) {
        this.jrnDt = jrnDt;
    }

    public String getJrnLctnId() {
        return jrnLctnId;
    }

    public void setJrnLctnId(String jrnLctnId) {
        this.jrnLctnId = jrnLctnId;
    }

    public String getJrnObjId() {
        return jrnObjId;
    }

    public void setJrnObjId(String jrnObjId) {
        this.jrnObjId = jrnObjId;
    }

    public String getJrnStatusTypeCd() {
        return jrnStatusTypeCd;
    }

    public void setJrnStatusTypeCd(String jrnStatusTypeCd) {
        this.jrnStatusTypeCd = jrnStatusTypeCd;
    }

    public String getJrnUserId() {
        return jrnUserId;
    }

    public void setJrnUserId(String jrnUserId) {
        this.jrnUserId = jrnUserId;
    }
}
