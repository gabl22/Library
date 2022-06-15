package me.gabl.library.util.text;

import java.util.ArrayList;
import java.util.List;

public final class StringUtils {

    private StringUtils() {
        throw new UnsupportedOperationException();
    }

    public static String[] dropFirst(String[] array) {
        String[] value = new String[array.length - 1];
        System.arraycopy(array, 1, value, 0, array.length - 1);
        return value;
    }

    public static List<Integer> indexesOf(String str, char indexChar) {
        List<Integer> l = new ArrayList<>();
        char[] chars = str.toCharArray();
        for(int i = 0; i < chars.length; i++)
            if(chars[i] == indexChar)
                l.add(i);
        return l;
    }

    public static boolean stringIsNumber(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean stringIsNumber(char s) {
        return stringIsNumber(String.valueOf(s));
    }

    public static String format(String str, boolean colorize, String... strings) {
        str = TextFormat.format(str, colorize);
        for(int i = 0; i < strings.length; i++)
            str = str.replace("" + (char) 123 + i + (char) 125, strings[i]);
        return str;
    }
}