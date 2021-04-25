package tm.controller.utility;

import java.util.regex.Pattern;

public abstract class Utils {

    public static boolean isInteger(String s) {
        final String digitsRegex = "\\d+";
        if (Pattern.matches(digitsRegex, s)){
            return true;
        }
        else return false;
    }
}
