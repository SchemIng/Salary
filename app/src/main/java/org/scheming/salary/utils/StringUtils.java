package org.scheming.salary.utils;

/**
 * Created by Scheming on 2015/10/21.
 */
public class StringUtils {
    public static Float toFloat(String string) {
        return Float.valueOf(string.equals("") ? "0" : string);
    }

    public static Integer toInteger(String string) {
        return Integer.valueOf(string.equals("") ? "0" : string);
    }
}
