package io.github.wrobezin.framework.common.check.string;

import lombok.AllArgsConstructor;

/**
 * 字符串类型枚举
 *
 * @author yuan
 * date: 2019/12/16
 */
@AllArgsConstructor
public enum StringTypeEnum {
    /** 普通字符串 */
    ORDINARY(0),
    /** ASCII字符串 */
    ASCII(1),
    /** 无空白符的ASCII字符串 */
    ASCII_WITHOUT_BLANK(1),
    /** 字母数字字符串 */
    ALPHANUMERIC(3),
    /** 数字字符串 */
    NUMERIC(4),
    /** 字母字符串 */
    ALPHABEITC(5);

    int type;

    public static boolean isType(StringTypeEnum type, String string) {
        switch (type) {
            case ORDINARY:
                return true;
            case ASCII:
                return StringTypeEnum.isAscii(string);
            case ASCII_WITHOUT_BLANK:
                return StringTypeEnum.isAsciiWithoutBlank(string);
            case ALPHANUMERIC:
                return StringTypeEnum.isAlphanumeric(string);
            case NUMERIC:
                return StringTypeEnum.isNumeric(string);
            case ALPHABEITC:
                return StringTypeEnum.isAlphabeitc(string);
            default:
                throw new IllegalArgumentException("未定义的字符串类型" + type);
        }
    }

    public static boolean isAscii(String string) {
        return string.getBytes().length == string.length();
    }

    public static boolean isAsciiWithoutBlank(String string) {
        return string.getBytes().length == string.replaceAll("\\s", "").length();
    }

    public static boolean isAlphanumeric(String strig) {
        return strig.matches("[0-9a-zA-Z]*");
    }

    public static boolean isNumeric(String strig) {
        return strig.matches("[0-9]*");
    }

    public static boolean isAlphabeitc(String strig) {
        return strig.matches("[a-zA-Z]*");
    }
}
