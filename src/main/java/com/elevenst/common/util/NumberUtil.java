package com.elevenst.common.util;

import java.text.DecimalFormat;

public class NumberUtil {

    private static final String DOUBLE_FORMAT = "###,###,###,###,##0.###";
    private static final String LONG_FORMAT = "###,###,###,###,##0";

    public NumberUtil() {
    }

    public static String getCommaString(int value) {
        return formatNumber(value, "###,###,###,###,##0");
    }

    public static String getCommaString(long value) {
        return formatNumber(value, "###,###,###,###,##0");
    }

    public static String getCommaString(float value) {
        return formatNumber(value, "###,###,###,###,##0.###");
    }

    public static String getCommaString(double value) {
        return formatNumber(value, "###,###,###,###,##0.###");
    }

    public static String getCommaString(String value) {
        boolean isFloat = false;
        boolean isNumber = true;
        if (isNumber(value)) {
            try {
                Long.parseLong(value);
                isFloat = false;
            } catch (NumberFormatException var6) {
                try {
                    Double.parseDouble(value);
                    isFloat = true;
                } catch (NumberFormatException var5) {
                    isFloat = false;
                    isNumber = false;
                }
            }

            if (isNumber) {
                return isFloat ? formatNumber(Double.parseDouble(value), "###,###,###,###,##0.###") : formatNumber(Long.parseLong(value), "###,###,###,###,##0");
            } else {
                return value;
            }
        } else {
            return value;
        }
    }

    public static String formatNumber(int value, String format) {
        DecimalFormat formatValue = new DecimalFormat(format);
        return formatValue.format((long)value);
    }

    public static String formatNumber(long value, String format) {
        DecimalFormat formatValue = new DecimalFormat(format);
        return formatValue.format(value);
    }

    public static String formatNumber(double value, String format) {
        if (Double.isNaN(value)) {
            return "0";
        } else {
            DecimalFormat formatValue = new DecimalFormat(format);
            return formatValue.format(value);
        }
    }

    public static String formatNumber(float value, String format) {
        if (Float.isNaN(value)) {
            return "0";
        } else {
            DecimalFormat formatValue = new DecimalFormat(format);
            return formatValue.format((double)value);
        }
    }

    public static boolean isNumber(String str) {
        if (str != null && str.length() != 0) {
            char[] chars = str.toCharArray();
            int sz = chars.length;
            boolean hasExp = false;
            boolean hasDecPoint = false;
            boolean allowSigns = false;
            boolean foundDigit = false;
            int start = chars[0] == '-' ? 1 : 0;
            int i;
            if (sz > start + 1 && chars[start] == '0' && chars[start + 1] == 'x') {
                i = start + 2;
                if (i == sz) {
                    return false;
                } else {
                    while(i < chars.length) {
                        if ((chars[i] < '0' || chars[i] > '9') && (chars[i] < 'a' || chars[i] > 'f') && (chars[i] < 'A' || chars[i] > 'F')) {
                            return false;
                        }

                        ++i;
                    }

                    return true;
                }
            } else {
                --sz;

                for(i = start; i < sz || i < sz + 1 && allowSigns && !foundDigit; ++i) {
                    if (chars[i] >= '0' && chars[i] <= '9') {
                        foundDigit = true;
                        allowSigns = false;
                    } else if (chars[i] == '.') {
                        if (hasDecPoint || hasExp) {
                            return false;
                        }

                        hasDecPoint = true;
                    } else if (chars[i] != 'e' && chars[i] != 'E') {
                        if (chars[i] != '+' && chars[i] != '-') {
                            return false;
                        }

                        if (!allowSigns) {
                            return false;
                        }

                        allowSigns = false;
                        foundDigit = false;
                    } else {
                        if (hasExp) {
                            return false;
                        }

                        if (!foundDigit) {
                            return false;
                        }

                        hasExp = true;
                        allowSigns = true;
                    }
                }

                if (i < chars.length) {
                    if (chars[i] >= '0' && chars[i] <= '9') {
                        return true;
                    }

                    if (chars[i] == 'e' || chars[i] == 'E') {
                        return false;
                    }

                    if (!allowSigns && (chars[i] == 'd' || chars[i] == 'D' || chars[i] == 'f' || chars[i] == 'F')) {
                        return foundDigit;
                    }

                    if (chars[i] == 'l' || chars[i] == 'L') {
                        return foundDigit && !hasExp;
                    }
                }

                return !allowSigns && foundDigit;
            }
        } else {
            return false;
        }
    }

    /**
     * 10원단위 절삭
     */
    public static long change_1won(double amount, String mode) {
        double removeAmount = 0;
        if("".equals(mode)) mode = "R";

        removeAmount = amount / 10;

        if("F".equals(mode)) removeAmount = Math.floor(removeAmount);
        else if("R".equals(mode)) removeAmount = Math.round(removeAmount);
        else if("C".equals(mode)) removeAmount = Math.ceil(removeAmount);

        removeAmount = removeAmount * 10;

        return (long) removeAmount;
    }
}
