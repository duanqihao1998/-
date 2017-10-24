package com.bwie.shang.util;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextUtil {

    public static boolean isJson(String content) {

        return content.length() > 8 && content.contains("{") && content.contains("}");

    }

    public static boolean isEmpty(String content) {

        return content == null || content.isEmpty() || content.length() == 0 || content.equals("");

    }

    public static boolean isNcJson(String content) {

        return content.length() > 8 && content.contains("{") && content.contains("}") && content.contains("\"code\":200") && !content.contains("\"error\"");

    }

    public static boolean isUrlAddress(String url) {
        if (url.contains("http") || url.contains("www.") || url.contains(".com") || url.contains(".cn")) {
            return true;
        }
        return false;
    }

    public static boolean isEmailAddress(String url) {
        String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(url);
        return matcher.matches();
    }

}