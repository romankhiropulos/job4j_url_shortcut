package ru.job4j.urlshortcut.util;

public class Encryptor {

    private static final String BASE_DIGITS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * @param decimalNumber number to convert to the base62
     * @return string representation of the decimal number
     */
    public static String toBase62(long decimalNumber) {
        return fromDecimalToOtherBase(62, decimalNumber);
    }

    private static String fromDecimalToOtherBase(int base, long decimalNumber) {
        String result = decimalNumber == 0 ? "0" : "";
        while (decimalNumber != 0) {
            long mod = decimalNumber % base;
            result = String.valueOf(BASE_DIGITS.charAt((int) mod)).concat(result);
            decimalNumber = decimalNumber / base;
        }
        return result;
    }
}
