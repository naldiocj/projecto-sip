package ao.gov.sic.sip.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidarDocumento {

    private static final String REGEX_PGR = "(\\d{3})/(\\d{4})-([A-Z]+)-([A-Z]+)";
    private static final Pattern PATTERN = Pattern.compile(REGEX_PGR);

    public static boolean isValidNumeroProcesso(String input) {
        if (input == null) {
            return false;
        }
        Matcher matcher = PATTERN.matcher(input);
        return matcher.matches();
    }
}