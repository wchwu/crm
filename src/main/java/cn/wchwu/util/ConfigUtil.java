package cn.wchwu.util;

import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class ConfigUtil {

    private static ResourceBundle bundle = ResourceBundle.getBundle("config");

    public static String getPropertyValue(String string) {
        return bundle.getString(string);
    }

    public static String isNumeric(String str) {
        byte[] bytes = str.getBytes();
        int i = bytes.length;// i为字节长度
        int j = str.length();// j为字符长度
        if (Pattern.compile("[0-9]*").matcher(str).matches()) {
            // System.out.println("你输入的是数字") ;
            return "1";
        } else if (i == j) {
            // System.out.println("你输入的是英文") ;
            return "3";
        } else {
            // System.out.println("你输入的是中文");
            return "3";
        }
    }
}
