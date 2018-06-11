package util;

import java.text.SimpleDateFormat;

public class DateUtil {
    public static String formatDateToStr(String format, Object date) {
        String str = "";
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            str = sdf.format(date);
        }
        return str;
    }
}