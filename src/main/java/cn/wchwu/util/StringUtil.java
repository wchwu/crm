package cn.wchwu.util;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author jack.liu 对字符串操作.
 */
public class StringUtil {
    private StringUtil() {
        // util class, prevent from new instance
    }

    public static String trim(String param) {
        return null == param ? null : param.trim();
    }

    public static String toLowerCase(String param) {
        return null == param ? null : param.toLowerCase();
    }

    /**
     * 判断一个对象是否是空. 如果是空返回true 如果对象是string类型，调用isEmpty()
     * 
     * @param object
     *            Object
     * @return boolean
     */
    public static boolean isNull(final Object object) {
        if (object instanceof String) {
            return StringUtil.isEmpty(object.toString());
        }
        return object == null;
    }

    /**
     * Checks if string is null or empty.
     * 
     * @param value
     *            The string to be checked
     * @return True if string is null or empty, otherwise false.
     */
    public static boolean isEmpty(final String value) {
        return null == value || "".equals(value) || "null".equalsIgnoreCase(value);
    }

    /**
     * 把一个参数变成固定程度，长度不足用0补充. 如果原长度超过要求长度，将原长度直接返回.
     * 
     * @param value
     *            原参数值
     * @param len
     *            参数需要的长度.
     * @return String
     */
    public static String fillZero(final String value, int len) {
        if (StringUtil.isNull(value) || len <= 0) {
            throw new IllegalArgumentException("参数不正确");
        }
        StringBuffer zero = new StringBuffer();
        StringBuffer sb = new StringBuffer(len);
        int paramLen = value.trim().length();
        if (paramLen < len) {
            for (int i = 0; i < len - paramLen; i++) {
                zero.append("0");
            }
        }
        return sb.append(zero).append(value).toString();
    }

    /**
     * 返回对数据位的校验.
     * 
     * @param sourceValue
     *            String
     * @return int
     */
    public static int getValiateCode(final String sourceValue) {
        int validateCode = 0;
        assert sourceValue != null;
        for (int i = 0, len = sourceValue.length(); i < len; i++) {
            int value = validateCode + (sourceValue.charAt(i) - 48);
            validateCode = value - value / 10 * 10;
        }
        return validateCode;
    }

    /**
     * Converts <code>null</code> to empty string, otherwise returns it
     * directly.
     * 
     * @param string
     *            The nullable string
     * @return empty string if passed in string is null, or original string
     *         without any change
     */
    public static String null2String(Object obj) {
        return obj == null ? "" : obj.toString();
    }

    public static String arrayToSQLString(Object[] params) {
        StringBuffer sb = new StringBuffer();
        if (null == params) {
            return null;
        }
        for (int i = 0, len = params.length; i < len; i++) {
            if (i == 0) {
                sb.append("(");
                sb.append("'").append(params[i]).append("'");
            } else {
                sb.append(",'").append(params[i]).append("'");
            }
            if (i == len - 1) {
                sb.append(")");
            }
        }
        return sb.toString();
    }

    public static String arrayToSQLParamStr(Object[] params) {
        StringBuilder sb = new StringBuilder();
        if (null == params) {
            return null;
        }
        for (int i = 0, len = params.length; i < len; i++) {
            if (i == 0) {
                sb.append("(");
                sb.append("?");
            } else {
                sb.append(",?");
            }
            if (i == len - 1) {
                sb.append(")");
            }
        }
        return sb.toString();
    }

    public static String arrayToString(Object[] params) {
        StringBuffer sb = new StringBuffer();
        if (null == params) {
            return null;
        }
        for (int i = 0, len = params.length; i < len; i++) {
            if (i == 0) {
                sb.append(params[i]);
            } else {
                sb.append(",").append(params[i]);
            }

        }
        return sb.toString();
    }

    public static String arrayToSumString(Object[] params) {
        StringBuffer sb = new StringBuffer();
        if (null == params) {
            return null;
        }
        for (int i = 0, len = params.length; i < len; i++) {
            if (i == 0) {
                sb.append("sum(").append(params[i]).append(")");
            } else {
                sb.append(",sum(").append(params[i]).append(")");
            }

        }
        return sb.toString();
    }

    /**
     * 比较两个字符串是否相等. 如果都是空相等.
     * 
     * @param value1
     * @param value2
     * @return
     */
    public static Boolean isEqual(String value1, String value2) {
        if (isEmpty(value1) && isEmpty(value2)) {
            return true;
        }
        if (!isEmpty(value1)) {
            return value1.equals(value2);
        }
        if (!isEmpty(value2)) {
            return value2.equals(value1);
        }

        return false;
    }

    public static int compareTo(String value1, String value2) {
        if (isEmpty(value1) && isEmpty(value2)) {
            return 0;
        }
        if (isEmpty(value1)) {
            return -1;
        }
        if (isEmpty(value2)) {
            return 1;
        }

        return value1.compareTo(value2);
    }

    public static String getFixLenString(String s, int width) {
        byte[] by = s.getBytes();

        if (by.length > width) {
            return new String(by, 0, width);
        } else if (by.length == width) {
            return s;
        } else {
            byte[] dest = new byte[width];

            for (int i = 0; i < by.length; i++) {
                dest[i] = by[i];
            }

            for (int i = by.length; i < width; i++) {
                dest[i] = 0x20;
            }

            return new String(dest);
        }
    }

    /***
     * 
     * @param s
     * @param width
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String getFixLenGBKString(byte[] gbkByte, int width) {
        try {
            String content = new String(gbkByte, "gbk");
            if (gbkByte.length < width) {

                byte[] result = new byte[width - gbkByte.length];
                for (int i = 0; i < width - gbkByte.length; i++) {
                    result[i] = 0x20;
                }
                String fixStr = new String(result, "gbk");
                return content + fixStr;
            }
            return content;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /***
     * 定长数字类型处理
     * 
     * @param num
     * @param width
     * @return
     */
    public static String getFixLenInteger(String num, int width) {
        if (isEmpty(num)) {
            byte[] dest = new byte[width];
            for (int i = 0; i < width; i++) {
                dest[i] = '0';
            }
            return new String(dest);
        }

        byte[] by = num.getBytes();
        if (by.length > width) {
            return new String(by, 0, width);
        } else if (by.length == width) {
            return num;
        } else {
            byte[] dest = new byte[width - by.length];

            for (int i = 0; i < width - by.length; i++) {
                dest[i] = '0';
            }
            return new String(dest) + num;
        }
    }

    /**
     * 检查字符串s是否在字符串数组array中
     * 
     * @param s
     * @param array
     * @return
     */
    public static boolean isContained(String s, String[] array) {
        if (s == null) {
            return false;
        } else {
            for (int i = 0; i < array.length; i++) {
                if (array[i] == null) {
                    continue;
                } else if (s.compareTo(array[i]) == 0) {
                    return true;
                }
            }

            return false;
        }
    }

    /**
     * 判断一个字符串是不是数字
     * 
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]+");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 检查数字n是否在数字数组array中
     * 
     * @param n
     * @param array
     * @return
     */
    public static boolean isContained(int n, int[] array) {
        for (int i = 0; i < array.length; i++) {
            if (n == array[i]) {
                return true;
            }
        }

        return false;
    }

    public static String byte2string(byte[] b) {
        String str = "";
        try {
            str = new String(b, "utf8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        return str;
    }

    /**
     * @param regex
     *            正则表达式字符串
     * @param str
     *            要匹配的字符串
     * @return 如果str 符合 regex的正则表达式格式,返回true, 否则返回 false;
     */
    public static boolean match(String regex, String str) {
        if (StringUtil.isEmpty(str) || StringUtil.isEmpty(regex)) {
            return false;
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    /**
     * 去除字符串中的空格、回车、换行符、制表符
     * 
     * @param str
     * @return
     */
    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    public static void main(String[] args) throws Exception {
        // // List tt = StringUtil.tokenizeToStringList("ddd||ss|", "|", false,
        // false);
        // // System.out.println(tt);
        // System.out.println(isNumeric(""));
        // String ss = StringUtil.getFixLenString("122", 2);
        // System.out.println(ss);
        String result = StringUtil.getFixLenGBKString(StringUtil.utf8ToGbkBytes("风刀"), 12);
        System.out.println(result);
        // // System.out.println(ss);
        // // System.out.println(isContained("3", new String[] {"1", "2", null,
        // "4"}));
        // // System.out.println(isContained(5, new int[] {1, 2, 3, 4}));
        // System.out.println(replaceBlank("111 222 333 "));
    }

    /** 空字符串。 */
    public static final String EMPTY_STRING = "";

    /**
     * 比较两个字符串（大小写敏感）。
     * 
     * <pre>
     * StringUtil.equals(null, null)   = true
     * StringUtil.equals(null, "abc")  = false
     * StringUtil.equals("abc", null)  = false
     * StringUtil.equals("abc", "abc") = true
     * StringUtil.equals("abc", "ABC") = false
     * </pre>
     * 
     * @param str1
     *            要比较的字符串1
     * @param str2
     *            要比较的字符串2
     * 
     * @return 如果两个字符串相同，或者都是<code>null</code>，则返回<code>true</code>
     */
    public static boolean equals(String str1, String str2) {
        if (str1 == null) {
            return str2 == null;
        }

        return str1.equals(str2);
    }

    /**
     * 比较两个字符串（大小写不敏感）。
     * 
     * <pre>
     * StringUtil.equalsIgnoreCase(null, null)   = true
     * StringUtil.equalsIgnoreCase(null, "abc")  = false
     * StringUtil.equalsIgnoreCase("abc", null)  = false
     * StringUtil.equalsIgnoreCase("abc", "abc") = true
     * StringUtil.equalsIgnoreCase("abc", "ABC") = true
     * </pre>
     * 
     * @param str1
     *            要比较的字符串1
     * @param str2
     *            要比较的字符串2
     * 
     * @return 如果两个字符串相同，或者都是<code>null</code>，则返回<code>true</code>
     */
    public static boolean equalsIgnoreCase(String str1, String str2) {
        if (str1 == null) {
            return str2 == null;
        }

        return str1.equalsIgnoreCase(str2);
    }

    /**
     * 检查字符串是否是空白：<code>null</code>、空字符串<code>""</code>或只有空白字符。
     * 
     * <pre>
     * StringUtil.isBlank(null)      = true
     * StringUtil.isBlank("")        = true
     * StringUtil.isBlank(" ")       = true
     * StringUtil.isBlank("bob")     = false
     * StringUtil.isBlank("  bob  ") = false
     * </pre>
     * 
     * @param str
     *            要检查的字符串
     * 
     * @return 如果为空白, 则返回<code>true</code>
     */
    public static boolean isBlank(String str) {
        int length;

        if ((str == null) || ((length = str.length()) == 0)) {
            return true;
        }

        for (int i = 0; i < length; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    /**
     * 检查字符串是否不是空白：<code>null</code>、空字符串<code>""</code>或只有空白字符。
     * 
     * <pre>
     * StringUtil.isBlank(null)      = false
     * StringUtil.isBlank("")        = false
     * StringUtil.isBlank(" ")       = false
     * StringUtil.isBlank("bob")     = true
     * StringUtil.isBlank("  bob  ") = true
     * </pre>
     * 
     * @param str
     *            要检查的字符串
     * 
     * @return 如果为空白, 则返回<code>true</code>
     */
    public static boolean isNotBlank(String str) {
        int length;

        if ((str == null) || ((length = str.length()) == 0)) {
            return false;
        }

        for (int i = 0; i < length; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }

        return false;
    }

    /**
     * 检查字符串是否不是<code>null</code>和空字符串<code>""</code>。
     * 
     * <pre>
     * StringUtil.isEmpty(null)      = false
     * StringUtil.isEmpty("")        = false
     * StringUtil.isEmpty(" ")       = true
     * StringUtil.isEmpty("bob")     = true
     * StringUtil.isEmpty("  bob  ") = true
     * </pre>
     * 
     * @param str
     *            要检查的字符串
     * 
     * @return 如果不为空, 则返回<code>true</code>
     */
    public static boolean isNotEmpty(String str) {
        return ((str != null) && (str.length() > 0));
    }

    /**
     * 在字符串中查找指定字符串，并返回第一个匹配的索引值。如果字符串为<code>null</code>或未找到，则返回<code>-1</code>。
     * 
     * <pre>
     * StringUtil.indexOf(null, *)          = -1
     * StringUtil.indexOf(*, null)          = -1
     * StringUtil.indexOf("", "")           = 0
     * StringUtil.indexOf("aabaabaa", "a")  = 0
     * StringUtil.indexOf("aabaabaa", "b")  = 2
     * StringUtil.indexOf("aabaabaa", "ab") = 1
     * StringUtil.indexOf("aabaabaa", "")   = 0
     * </pre>
     * 
     * @param str
     *            要扫描的字符串
     * @param searchStr
     *            要查找的字符串
     * 
     * @return 第一个匹配的索引值。如果字符串为<code>null</code>或未找到，则返回<code>-1</code>
     */
    public static int indexOf(String str, String searchStr) {
        if ((str == null) || (searchStr == null)) {
            return -1;
        }

        return str.indexOf(searchStr);
    }

    /**
     * 在字符串中查找指定字符串，并返回第一个匹配的索引值。如果字符串为<code>null</code>或未找到，则返回<code>-1</code>。
     * 
     * <pre>
     * StringUtil.indexOf(null, *, *)          = -1
     * StringUtil.indexOf(*, null, *)          = -1
     * StringUtil.indexOf("", "", 0)           = 0
     * StringUtil.indexOf("aabaabaa", "a", 0)  = 0
     * StringUtil.indexOf("aabaabaa", "b", 0)  = 2
     * StringUtil.indexOf("aabaabaa", "ab", 0) = 1
     * StringUtil.indexOf("aabaabaa", "b", 3)  = 5
     * StringUtil.indexOf("aabaabaa", "b", 9)  = -1
     * StringUtil.indexOf("aabaabaa", "b", -1) = 2
     * StringUtil.indexOf("aabaabaa", "", 2)   = 2
     * StringUtil.indexOf("abc", "", 9)        = 3
     * </pre>
     * 
     * @param str
     *            要扫描的字符串
     * @param searchStr
     *            要查找的字符串
     * @param startPos
     *            开始搜索的索引值，如果小于0，则看作0
     * 
     * @return 第一个匹配的索引值。如果字符串为<code>null</code>或未找到，则返回<code>-1</code>
     */
    public static int indexOf(String str, String searchStr, int startPos) {
        if ((str == null) || (searchStr == null)) {
            return -1;
        }

        // JDK1.3及以下版本的bug：不能正确处理下面的情况
        if ((searchStr.length() == 0) && (startPos >= str.length())) {
            return str.length();
        }

        return str.indexOf(searchStr, startPos);
    }

    /**
     * 取指定字符串的子串。
     * 
     * <p>
     * 负的索引代表从尾部开始计算。如果字符串为<code>null</code>，则返回<code>null</code>。
     * 
     * <pre>
     * StringUtil.substring(null, *, *)    = null
     * StringUtil.substring("", * ,  *)    = "";
     * StringUtil.substring("abc", 0, 2)   = "ab"
     * StringUtil.substring("abc", 2, 0)   = ""
     * StringUtil.substring("abc", 2, 4)   = "c"
     * StringUtil.substring("abc", 4, 6)   = ""
     * StringUtil.substring("abc", 2, 2)   = ""
     * StringUtil.substring("abc", -2, -1) = "b"
     * StringUtil.substring("abc", -4, 2)  = "ab"
     * </pre>
     * 
     * </p>
     * 
     * @param str
     *            字符串
     * @param start
     *            起始索引，如果为负数，表示从尾部计算
     * @param end
     *            结束索引（不含），如果为负数，表示从尾部计算
     * 
     * @return 子串，如果原始串为<code>null</code>，则返回<code>null</code>
     */
    public static String substring(String str, int start, int end) {
        if (str == null) {
            return null;
        }

        if (end < 0) {
            end = str.length() + end;
        }

        if (start < 0) {
            start = str.length() + start;
        }

        if (end > str.length()) {
            end = str.length();
        }

        if (start > end) {
            return EMPTY_STRING;
        }

        if (start < 0) {
            start = 0;
        }

        if (end < 0) {
            end = 0;
        }

        return str.substring(start, end);
    }

    /**
     * 检查字符串中是否包含指定的字符串。如果字符串为<code>null</code>，将返回<code>false</code>。
     * 
     * <pre>
     * StringUtil.contains(null, *)     = false
     * StringUtil.contains(*, null)     = false
     * StringUtil.contains("", "")      = true
     * StringUtil.contains("abc", "")   = true
     * StringUtil.contains("abc", "a")  = true
     * StringUtil.contains("abc", "z")  = false
     * </pre>
     * 
     * @param str
     *            要扫描的字符串
     * @param searchStr
     *            要查找的字符串
     * 
     * @return 如果找到，则返回<code>true</code>
     */
    public static boolean contains(String str, String searchStr) {
        if ((str == null) || (searchStr == null)) {
            return false;
        }

        return str.indexOf(searchStr) >= 0;
    }

    /**
     * 解析字符串<br>
     * 将字符串中的'{}'符号替换成具体内容
     * 
     * @param str
     * @param params
     * @return
     */
    public static String parseString(String str, String... params) {
        int i = 0;
        int length = params.length;

        while (str.indexOf("{}") > -1 && i < length) {
            str = str.replaceFirst("\\{}", String.valueOf(params[i]));
            i++;
        }

        return str;
    }

    /**
     * 将字符串首字母大写
     * 
     * @param s
     *            字符串
     * @return 首字母大写后的新字符串
     */
    public static String firstUpperCase(CharSequence s) {
        if (null == s)
            return null;
        int len = s.length();
        if (len == 0)
            return "";
        char char0 = s.charAt(0);
        if (Character.isUpperCase(char0))
            return s.toString();
        return new StringBuilder(len).append(Character.toUpperCase(char0)).append(s.subSequence(1, len)).toString();
    }

    /**
     * 将字符串首字母小写
     * 
     * @param s
     *            字符串
     * @return 首字母小写后的新字符串
     */
    public static String firstLowerCase(CharSequence s) {
        if (null == s)
            return null;
        int len = s.length();
        if (len == 0)
            return "";
        char char0 = s.charAt(0);
        if (Character.isLowerCase(char0))
            return s.toString();
        return new StringBuilder(len).append(Character.toLowerCase(char0)).append(s.subSequence(1, len)).toString();
    }

    /**
     * 将数字格式化成一个固定长度的字符串
     * <p>
     * 不足位由pattern决定补充,例如：<code>i</code>=123，<code>pattern</code>=
     * "000000",则返回"000123"
     * <p>
     * 超长由pattern决定截取,例如：<code>i</code>=123456789，<code>pattern</code>=
     * "000000",则返回"456789"
     * 
     * @param i
     *            数字
     * @param pattern
     *            模式
     * @return
     */
    public static String formatNumber(long i, String pattern) {

        return formatNumber(String.valueOf(i), pattern);

    }

    /**
     * 将仅包含数字的字符串格式化成一个固定长度的字符串
     * <p>
     * 不足位由pattern决定补充,例如：<code>num</code>=123，<code>pattern</code>=
     * "000000",则返回"000123"
     * <p>
     * 超长由pattern决定截取,例如：<code>num</code>=123456789，<code>pattern</code>=
     * "000000",则返回"456789"
     * 
     * @param num
     *            数字字符串
     * @param pattern
     *            模式
     * @return
     */
    public static String formatNumber(String num, String pattern) {

        // 如果模式为空，则返回null;
        if (pattern == null) {
            return null;
        }
        // 如果模式为长度小于数字位数，则截取后面的位数返回;
        if (pattern.length() < num.length()) {
            return num.substring(num.length() - pattern.length());
        }
        if (pattern.length() == num.length()) {
            return num;
        }
        // 高位不足的补足
        return pattern.substring(0, pattern.length() - num.length()) + num;
    }

    /**
     * 驼峰标示字符串转为下划线分割的大写字符串
     * <p>
     * 例: "custName"-->"CUST_NAME"
     * 
     * @param source
     * @return
     */
    public static String formatFieldToColumn(String source) {

        String regexStr = "[A-Z]";
        Matcher matcher = Pattern.compile(regexStr).matcher(source);
        StringBuffer sb = new StringBuffer();

        while (matcher.find()) {
            String g = matcher.group();
            matcher.appendReplacement(sb, "_" + g);
        }
        matcher.appendTail(sb);
        if (sb.charAt(0) == '_') {
            sb.delete(0, 1);
        }
        return sb.toString().toUpperCase();

    }

    /**
     * 下划线分割的字符串转为驼峰标示字符串
     * <p>
     * 例: "CUST_NAME"-->"custName","cust_name"-->"custName"
     * 
     * @param source
     * @return
     */
    public static String formatColmunToFiled(String source) {

        source = source.toLowerCase();
        String regexStr = "_";
        StringBuffer sb = new StringBuffer(source);

        int i = -1;
        while ((i = sb.indexOf(regexStr)) != -1) {
            sb.replace(i, i + 2, sb.substring(i + 1, i + 2).toUpperCase());
        }

        return sb.toString();

    }

    /**
     * UT8字符串转换成GBK字符串
     * 
     * @param utf8String
     * @return
     * @throws Exception
     */
    public static String utf8ToGbk(String utf8String) throws Exception {
        String unicode = new String(utf8String.getBytes("UTF-8"), "UTF-8");
        String gbk = new String(unicode.getBytes("GBK"));
        return gbk;
    }

    /**
     * GBK字符串转换成 UTF-8字符串
     * 
     * @param gbkString
     * @return
     * @throws Exception
     */
    public static String gbkToUTF8(String gbkString) {
        try {
            String unicode = new String(gbkString.getBytes("GBK"), "GBK");
            return StringUtil.replaceBlank(new String(unicode.getBytes("UTF-8")));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 截取GBK字符串
     * 
     * @param gbkString
     * @return
     * @throws Exception
     */
    public static String toUTF8String(byte[] gbkBytes, int startIndex, int endIndex) {
        try {
            byte[] tempGBKBytes = new byte[endIndex - startIndex];
            for (int i = 0; i < tempGBKBytes.length; i++) {
                tempGBKBytes[i] = gbkBytes[startIndex + i];
            }
            String unicode = new String(tempGBKBytes, "GBK");
            return StringUtil.replaceBlank(new String(unicode.getBytes("UTF-8")));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * UT8字符串转换成GBK字符串
     * 
     * @param utf8String
     * @return
     * @throws Exception
     */
    public static byte[] utf8ToGbkBytes(String utf8String) throws Exception {
        String unicode = new String(utf8String.getBytes("UTF-8"), "UTF-8");
        return unicode.getBytes("GBK");
    }

    /**
     * LF 字符
     */
    public static final String LF = new String(new byte[] { 0x0A });

    /**
     * CR 字符
     */
    public static final String CR = new String(new byte[] { 0x0D });

    /**
     * 替换回车符和换行符
     * 
     * @param str
     * @return
     */
    public static String replaceCRLF(String str) {
        if (isEmpty(str)) {
            return "";
        }
        String temp = str.replace(CR, "");
        temp = temp.replace(LF, "");
        return temp;
    }

    /**
     * 
     * formatAmount:金额格式化输出
     * 
     * @param amount
     * @return
     * @author jiong.peng
     * @date 2017年7月23日 下午10:16:50
     */
    public static String formatAmount(BigDecimal amount) {
        DecimalFormat format = new DecimalFormat(",##0.00");
        return format.format(amount.doubleValue());
    }

    /**
     * 
     * formatVol:份额格式化输出
     * 
     * @param vol
     * @return
     * @author jiong.peng
     * @date 2017年7月23日 下午10:17:13
     */
    public static String formatVol(BigDecimal vol) {
        return formatAmount(vol);
    }

    /**
     * join:合并字符串数组为字符串
     * 
     * @param arr
     *            待合并的字符串数组
     * @param reg
     *            连接符
     * @return
     * @author Chaowu.Wang
     * @date 2018年2月28日 下午6:06:00
     */
    public static String join(String[] arr, String reg) {
        if (null == arr || arr.length == 0) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < arr.length; i++) {
            if (i != arr.length - 1) {
                sb.append(arr[i]).append(reg);
            } else {
                sb.append(arr[i]);
            }
        }
        return sb.toString();
    }
}
