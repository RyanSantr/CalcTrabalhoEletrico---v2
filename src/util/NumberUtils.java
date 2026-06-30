package util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public final class NumberUtils {

    private NumberUtils() {
    }

    public static double parseFlexibleDouble(String text, String fieldName) {
        if (text == null || text.isBlank()) {
            throw new IllegalArgumentException("Digite um valor valido para " + fieldName + ".");
        }

        try {
            double value = Double.parseDouble(text.trim().replace(",", "."));
            if (!Double.isFinite(value)) {
                throw new NumberFormatException("not finite");
            }
            return value;
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Digite um valor valido para " + fieldName + ".");
        }
    }

    public static String formatDecimal(double value) {
        DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance(Locale.forLanguageTag("pt-BR"));
        DecimalFormat format = new DecimalFormat("0.########", symbols);
        return format.format(value);
    }

    public static String formatFixed(double value, String pattern) {
        DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance(Locale.forLanguageTag("pt-BR"));
        DecimalFormat format = new DecimalFormat(pattern, symbols);
        return format.format(value);
    }

    public static String formatScientific(double value) {
        if (value == 0) {
            return "0";
        }

        int exponent = (int) Math.floor(Math.log10(Math.abs(value)));
        double mantissa = value / Math.pow(10, exponent);
        DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance(Locale.forLanguageTag("pt-BR"));
        DecimalFormat format = new DecimalFormat("0.00", symbols);
        return format.format(mantissa) + " × 10^" + exponent;
    }
}
