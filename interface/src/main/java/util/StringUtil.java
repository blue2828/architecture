package util;

public class StringUtil {
    public static boolean isNotEmpty(String str) {
        if (null == str || "".equals(str)) {
            return false;
        } else return true;
    }
}
