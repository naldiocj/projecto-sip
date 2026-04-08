package ao.gov.sic.sip.utils;

import org.springframework.util.StringUtils;

public class ProcessoUtils {

    public static String cvtToProcessoNumero(String numero) {
        boolean hasNumero = StringUtils.hasText(numero);
        String result = null;
        if (hasNumero) {
            result = numero.replaceFirst("-", "/");
        }

        return result;
    }
}
