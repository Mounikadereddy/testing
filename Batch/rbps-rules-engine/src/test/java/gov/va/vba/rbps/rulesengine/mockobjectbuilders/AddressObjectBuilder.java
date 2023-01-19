package gov.va.vba.rbps.rulesengine.mockobjectbuilders;

import gov.va.vba.rbps.coreframework.xom.Address;

public class AddressObjectBuilder {
    public static Address createAddress(String lineOne, String city, String country, String state, String zip) {
        Address address = new Address();

        address.setLine1(lineOne);
        address.setCity(city);
        address.setCountry(country);
        address.setState(state);
        address.setZipPrefix(zip);

        return address;
    }
}
