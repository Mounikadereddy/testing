package gov.va.vba.rbps.rulesengine.mockobjectbuilders;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    final public static String DATE_FORMAT = "MM/dd/yyyy";

    public static Date convertDate(String date) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        Date convertedDate = null;
        try {
            convertedDate = formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return convertedDate;
    }
}
