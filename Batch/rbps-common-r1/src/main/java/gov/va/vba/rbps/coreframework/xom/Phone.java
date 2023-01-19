package gov.va.vba.rbps.coreframework.xom;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Phone implements Serializable {

    private static final long serialVersionUID = 1956030009346542348L;
    private long        areaCode;
    private long        countryCode;
    private long        phoneNumber;
    private long        extension;
    private PhoneType   phoneType;

    public long getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(final long areaCode) {
        this.areaCode = areaCode;
    }

    public long getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(final long countryCode) {
        this.countryCode = countryCode;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(final long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public long getExtension() {
        return extension;
    }

    public void setExtension(final long extension) {
        this.extension = extension;
    }

    public void setPhoneType(final PhoneType phoneType) {
        this.phoneType = phoneType;
    }

    public PhoneType getPhoneType() {
        return phoneType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("Area Code",    areaCode)
                .append("Country Code", countryCode)
                .append("Phone Number", phoneNumber)
                .append("Extension",    extension)
                .append("Phone Type",   phoneType)
                .toString();
    }
}
