package gov.va.vba.rbps.rulesengine.mockobjectbuilders;

import gov.va.vba.rbps.coreframework.xom.util.VeteranCommonDates;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class VeteranCommonDatesObjectBuilder {
    public static VeteranCommonDates createCommonDates() throws ParseException {
        VeteranCommonDates commonDates = new VeteranCommonDates();

        commonDates.setRatingDate(new SimpleDateFormat("MM/dd/yyyy").parse("01/01/2016"));
        commonDates.setClaimDate(new SimpleDateFormat("MM/dd/yyyy").parse("01/03/2017"));
        commonDates.setRatingEffectiveDate(new SimpleDateFormat("MM/dd/yyyy").parse("03/01/2019"));
        commonDates.setAllowableDate(new SimpleDateFormat("MM/dd/yyyy").parse("01/01/2019"));
        commonDates.setFirstChangedDateofRating(new SimpleDateFormat("MM/dd/yyyy").parse("05/01/2019"));

        return commonDates;
    }
}
