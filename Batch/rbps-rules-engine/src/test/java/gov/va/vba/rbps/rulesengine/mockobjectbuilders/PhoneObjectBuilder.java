package gov.va.vba.rbps.rulesengine.mockobjectbuilders;

import gov.va.vba.rbps.coreframework.xom.Phone;
import gov.va.vba.rbps.coreframework.xom.PhoneType;

public class PhoneObjectBuilder {

    public static Phone createPhone() {
        Phone phone = new Phone();

        phone.setAreaCode(999);
        phone.setCountryCode(1);
        phone.setPhoneNumber(999999);
        phone.setPhoneType(PhoneType.DAY);

        return phone;
    }
}
