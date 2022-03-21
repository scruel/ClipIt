package pers.scruel.util;

/**
 * @author Scruel Tao <scruelt@hotmail.com>
 */
public class StringUtils {
    private StringUtils() {
    }

    public static boolean isBlank(final CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
