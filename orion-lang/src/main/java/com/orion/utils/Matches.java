package com.orion.utils;

import com.orion.lang.cache.SoftCache;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则相关工具类
 *
 * @author ljh15
 * @version 1.0.0
 * @since 2020/1/5 16:13
 */
public class Matches {

    private Matches() {
    }

    private static final SoftCache<String, Pattern> CACHE = new SoftCache<>();

    /**
     * 空白行
     */
    public static final Pattern SPACE_LINE = Pattern.compile("\\n\\s*\\r");

    /**
     * 首尾空格
     */
    public static final Pattern SPACE_POINT = Pattern.compile("^\\s*|\\s*$");

    /**
     * 手机号
     */
    public static final Pattern PHONE = Pattern.compile("^[1](([3][0-9])|([4][5-9])|([5][0-3,5-9])|([6][5,6])|([7][0-8])|([8][0-9])|([9][1,8,9]))[0-9]{8}$");

    /**
     * 邮箱正则
     */
    // public static final Pattern EMAIL = Pattern.compile("^[A-Za-z0-9]+([_.][A-Za-z0-9]+)*@([A-Za-z0-9\\-]+\\.)+[A-Za-z]{2,6}$", Pattern.CASE_INSENSITIVE);
    public static final Pattern EMAIL = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])", Pattern.CASE_INSENSITIVE);

    /**
     * http正则
     */
    public static final Pattern HTTP = Pattern.compile("^(http|https)://([\\w.]+/?)\\S*$");

    /**
     * uri正则
     */
    public static final Pattern URI = Pattern.compile("^[a-zA-z]+://([\\w.]+/?)\\S*$");

    /**
     * integer正则
     */
    public static final Pattern INTEGER = Pattern.compile("^[-+]?[\\d]*$");

    /**
     * double正则
     */
    public static final Pattern DOUBLE = Pattern.compile("^[-+]?\\d*[.]\\d+$");

    /**
     * IPV4正则
     */
    public static final Pattern IPV4 = Pattern.compile("^(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}$");

    /**
     * IPV6正则
     */
    public static final Pattern IPV6 = Pattern.compile("^((([0-9A-Fa-f]{1,4}:){7}[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){1,7}:)|(([0-9A-Fa-f]{1,4}:){6}:[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){5}(:[0-9A-Fa-f]{1,4}){1,2})|(([0-9A-Fa-f]{1,4}:){4}(:[0-9A-Fa-f]{1,4}){1,3})|(([0-9A-Fa-f]{1,4}:){3}(:[0-9A-Fa-f]{1,4}){1,4})|(([0-9A-Fa-f]{1,4}:){2}(:[0-9A-Fa-f]{1,4}){1,5})|([0-9A-Fa-f]{1,4}:(:[0-9A-Fa-f]{1,4}){1,6})|(:(:[0-9A-Fa-f]{1,4}){1,7})|(([0-9A-Fa-f]{1,4}:){6}(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3})|(([0-9A-Fa-f]{1,4}:){5}:(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3})|(([0-9A-Fa-f]{1,4}:){4}(:[0-9A-Fa-f]{1,4}){0,1}:(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3})|(([0-9A-Fa-f]{1,4}:){3}(:[0-9A-Fa-f]{1,4}){0,2}:(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3})|(([0-9A-Fa-f]{1,4}:){2}(:[0-9A-Fa-f]{1,4}){0,3}:(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3})|([0-9A-Fa-f]{1,4}:(:[0-9A-Fa-f]{1,4}){0,4}:(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3})|(:(:[0-9A-Fa-f]{1,4}){0,5}:(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}))$");

    /**
     * MD5 正则
     */
    public static final Pattern MD5 = Pattern.compile("^[a-f0-9]{32}|[A-F0-9]{32}$");

    /**
     * windows文件路径 正则
     */
    public static final Pattern WINDOWS_PATH = Pattern.compile("^[A-z]:\\\\([^|><?*\":/]*\\\\)*([^|><?*\":/]*)?$|^[A-z]:/([^|><?*\":/]*/)*([^|><?*\":/]*)?$");

    /**
     * linux文件路径 正则
     */
    public static final Pattern LINUX_PATH = Pattern.compile("^/([^|><?*\":/]*/)*([^|><?*\":/]*)?$");

    /**
     * 邮编
     */
    public static final Pattern ZIP_CODE = Pattern.compile("[1-9]\\d{5}(?!\\d)");

    /**
     * 中文字、英文字母、数字和下划线
     */
    public static final Pattern UTF = Pattern.compile("^[\u4E00-\u9FFF\\w]+$");

    /**
     * UUID 包含-
     */
    public static final Pattern UUID = Pattern.compile("^[0-9a-z]{8}-[0-9a-z]{4}-[0-9a-z]{4}-[0-9a-z]{4}-[0-9a-z]{12}$");

    /**
     * MAC地址
     */
    public static final Pattern MAC = Pattern.compile("((?:[A-F0-9]{1,2}[:-]){5}[A-F0-9]{1,2})|(?:0x)(\\d{12})(?:.+ETHER)", Pattern.CASE_INSENSITIVE);

    /**
     * 16进制字符串
     */
    public static final Pattern HEX = Pattern.compile("^[a-f0-9]+$", Pattern.CASE_INSENSITIVE);

    /**
     * 社会统一信用代码
     */
    public static final Pattern CREDIT_CODE = Pattern.compile("^[0-9A-HJ-NPQRTUWXY]{2}\\d{6}[0-9A-HJ-NPQRTUWXY]{10}$");

    /**
     * 18位身份证号码
     */
    public static final Pattern ID_CARD = Pattern.compile("[1-9]\\d{5}[1-2]\\d{3}((0\\d)|(1[0-2]))(([012]\\d)|3[0-1])\\d{3}(\\d|X|x)");

    /**
     * 中国车牌号码
     */
    public static final Pattern PLATE_NUMBER = Pattern.compile(
            "^(([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领][A-Z](([0-9]{5}[ABCDEFGHJK])|([ABCDEFGHJK]([A-HJ-NP-Z0-9])[0-9]{4})))|" +
                    "([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领]\\d{3}\\d{1,3}[领])|" +
                    "([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领][A-Z][A-HJ-NP-Z0-9]{4}[A-HJ-NP-Z0-9挂学警港澳使领]))$");

    /**
     * 获取正则对象
     *
     * @param pattern 表达式
     * @return 正则
     */
    public static Pattern getPattern(String pattern) {
        Pattern p = CACHE.get(pattern);
        if (p == null) {
            p = Pattern.compile(pattern);
            CACHE.put(pattern, p);
        }
        return p;
    }

    /**
     * 获取正则对象 忽略首尾 ^ $
     * 用于字符串提取
     *
     * @param pattern 表达式
     * @return 正则
     */
    public static Pattern getPatternExt(Pattern pattern) {
        return getPatternExt(pattern.pattern());
    }

    /**
     * 获取正则对象 忽略首尾 ^ $
     * 用于字符串提取
     *
     * @param pattern 表达式
     * @return 正则
     */
    public static Pattern getPatternExt(String pattern) {
        if (pattern.startsWith("^")) {
            pattern = pattern.substring(1);
        }
        if (pattern.endsWith("$")) {
            pattern = pattern.substring(0, pattern.length() - 1);
        }
        Pattern p = CACHE.get(pattern);
        if (p == null) {
            p = Pattern.compile(pattern);
            CACHE.put(pattern, p);
        }
        return p;
    }

    /**
     * 匹配字符出现次数
     *
     * @param s    源数据
     * @param find 匹配数据
     * @return 次数
     */
    public static int findNum(String s, String find) {
        int count = 0;
        Pattern p = Pattern.compile(find);
        Matcher m = p.matcher(s);
        while (m.find()) {
            count++;
        }
        return count;
    }

    // --------------- test ---------------

    /**
     * 是否匹配
     *
     * @param s       字符
     * @param pattern 模式
     * @return true 匹配
     */
    public static boolean test(String s, String pattern) {
        return getPattern(pattern).matcher(s).matches();
    }

    /**
     * 是否匹配
     *
     * @param s       字符
     * @param pattern 模式
     * @return true 匹配
     */
    public static boolean test(String s, Pattern pattern) {
        return pattern.matcher(s).matches();
    }

    /**
     * 匹配是否为空行
     *
     * @param str 待匹配的字符
     * @return true空行
     */
    public static boolean isSpaceLine(String str) {
        return SPACE_LINE.matcher(str).matches();
    }

    /**
     * 匹配是否为首尾空格
     *
     * @param str 待匹配的字符
     * @return true首尾空格
     */
    public static boolean isSpacePoint(String str) {
        return SPACE_POINT.matcher(str).matches();
    }

    /**
     * 匹配是否为浮点数
     *
     * @param str 待匹配的字符
     * @return true浮点数
     */
    public static boolean isDouble(String str) {
        return DOUBLE.matcher(str).matches();
    }

    /**
     * 匹配是否为整数
     *
     * @param str 待匹配的字符
     * @return true整数
     */
    public static boolean isInteger(String str) {
        return INTEGER.matcher(str).matches();
    }

    /**
     * 匹配是否为IPV4
     *
     * @param ip ip
     * @return true IPV4
     */
    public static boolean isIpv4(String ip) {
        return IPV4.matcher(ip).matches();
    }

    /**
     * 匹配是否为IPV6
     *
     * @param ip ip
     * @return true IPV6
     */
    public static boolean isIpv6(String ip) {
        return IPV6.matcher(ip).matches();
    }

    /**
     * 匹配是否为手机号
     *
     * @param phone 手机号
     * @return true 手机号
     */
    public static boolean isPhone(String phone) {
        return PHONE.matcher(phone).matches();
    }

    /**
     * 匹配是否为邮箱
     *
     * @param email 邮箱
     * @return true 邮箱
     */
    public static boolean isEmail(String email) {
        return EMAIL.matcher(email).matches();
    }

    /**
     * 匹配是否为http url
     *
     * @param http url
     * @return true HTTP url
     */
    public static boolean isHttp(String http) {
        return HTTP.matcher(http).matches();
    }

    /**
     * 匹配是否为 uri
     *
     * @param uri uri
     * @return true uri
     */
    public static boolean isUri(String uri) {
        return URI.matcher(uri).matches();
    }

    /**
     * 匹配是否为 MD5
     *
     * @param s s
     * @return true MD5
     */
    public static boolean isMd5(String s) {
        return MD5.matcher(s).matches();
    }

    /**
     * 匹配是否为 windows文件路径
     *
     * @param path 路径
     * @return true windows文件路径
     */
    public static boolean isWindowsPath(String path) {
        return WINDOWS_PATH.matcher(path).matches();
    }

    /**
     * 匹配是否为 linux文件路径
     *
     * @param path 路径
     * @return true linux文件路径
     */
    public static boolean isLinuxPath(String path) {
        return LINUX_PATH.matcher(path).matches();
    }

    /**
     * 匹配是否为 操作系统文件路径
     *
     * @param path 路径
     * @return true 文件路径
     */
    public static boolean isPath(String path) {
        return Matches.isWindowsPath(path) || Matches.isLinuxPath(path);
    }

    /**
     * 匹配是否为 邮编
     *
     * @param s str
     * @return true 邮编
     */
    public static boolean isZipCode(String s) {
        return ZIP_CODE.matcher(s).matches();
    }

    /**
     * 匹配是否为 中文字、英文字母、数字和下划线
     *
     * @param s str
     * @return true 中文字、英文字母、数字和下划线
     */
    public static boolean isUtf(String s) {
        return UTF.matcher(s).matches();
    }

    /**
     * 匹配是否为 邮编
     *
     * @param s str
     * @return true 邮编
     */
    public static boolean isUuid(String s) {
        return UUID.matcher(s).matches();
    }

    /**
     * 匹配是否为 MAC地址
     *
     * @param s str
     * @return true MAC地址
     */
    public static boolean isMac(String s) {
        return MAC.matcher(s).matches();
    }

    /**
     * 匹配是否为 邮编
     *
     * @param s str
     * @return true 邮编
     */
    public static boolean isHex(String s) {
        return HEX.matcher(s).matches();
    }

    /**
     * 匹配是否为 社会统一信用代码
     * <p>
     * 第一部分: 登记管理部门代码1位 (数字或大写英文字母)
     * 第二部分: 机构类别代码1位 (数字或大写英文字母)
     * 第三部分: 登记管理机关行政区划码6位 (数字)
     * 第四部分: 主体标识码（组织机构代码）9位 (数字或大写英文字母)
     * 第五部分: 校验码1位 (数字或大写英文字母)
     *
     * @param s 社会统一信用代码
     * @return true 社会统一信用代码
     */
    public static boolean isCreditCode(String s) {
        return CREDIT_CODE.matcher(s).matches();
    }

    /**
     * 匹配是否为 18位身份证号码
     *
     * @param s str
     * @return true 18位身份证号码
     */
    public static boolean isIdCard(String s) {
        return ID_CARD.matcher(s).matches();
    }

    /**
     * 匹配是否为 中国车牌号码
     *
     * @param s str
     * @return true 中国车牌号码
     */
    public static boolean isPlateNumber(String s) {
        return PLATE_NUMBER.matcher(s).matches();
    }

    // --------------- ext ---------------

    /**
     * 组提取
     *
     * @param s       字符
     * @param pattern 模式
     * @return 匹配到的组
     */
    public static String extGroup(String s, String pattern) {
        Matcher matcher = getPattern(pattern).matcher(s);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

    /**
     * 组提取
     *
     * @param s       字符
     * @param pattern 模式
     * @return 匹配到的组
     */
    public static String extGroup(String s, Pattern pattern) {
        Matcher matcher = pattern.matcher(s);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

    /**
     * 组提取
     *
     * @param s       字符
     * @param pattern 模式
     * @return 匹配到的组
     */
    public static List<String> extGroups(String s, String pattern) {
        List<String> groups = new ArrayList<>();
        Matcher matcher = getPattern(pattern).matcher(s);
        while (matcher.find()) {
            groups.add(matcher.group());
        }
        return groups;
    }

    /**
     * 组提取
     *
     * @param s       字符
     * @param pattern 模式
     * @return 匹配到的组
     */
    public static List<String> extGroups(String s, Pattern pattern) {
        List<String> groups = new ArrayList<>();
        Matcher matcher = pattern.matcher(s);
        while (matcher.find()) {
            groups.add(matcher.group());
        }
        return groups;
    }

    /**
     * 提取手机号
     *
     * @param s 字符
     * @return 提取到的第一个手机号
     */
    public static String extPhone(String s) {
        return extGroup(s, getPatternExt(PHONE));
    }

    /**
     * 提取手机号
     *
     * @param s 字符
     * @return 提取到的所有手机号
     */
    public static List<String> extPhoneList(String s) {
        return extGroups(s, getPatternExt(PHONE));
    }

    /**
     * 提取邮箱
     *
     * @param s 字符
     * @return 提取到的第一个邮箱
     */
    public static String extEmail(String s) {
        return extGroup(s, getPatternExt(EMAIL));
    }

    /**
     * 提取邮箱
     *
     * @param s 字符
     * @return 提取到的所有邮箱
     */
    public static List<String> extEmailList(String s) {
        return extGroups(s, getPatternExt(EMAIL));
    }

    /**
     * 提取HTTP url
     *
     * @param s 字符
     * @return 提取到的第一个HTTP url
     */
    public static String extHttp(String s) {
        return extGroup(s, getPatternExt(HTTP));
    }

    /**
     * 提取HTTP url
     *
     * @param s 字符
     * @return 提取到的所有HTTP url
     */
    public static List<String> extHttpList(String s) {
        return extGroups(s, getPatternExt(HTTP));
    }

    /**
     * 提取Uri
     *
     * @param s 字符
     * @return 提取到的第一个Uri
     */
    public static String extUri(String s) {
        return extGroup(s, getPatternExt(URI));
    }

    /**
     * 提取Uri
     *
     * @param s 字符
     * @return 提取到的所有Uri
     */
    public static List<String> extUriList(String s) {
        return extGroups(s, getPatternExt(URI));
    }

    /**
     * 提取整数
     *
     * @param s 字符
     * @return 提取到的第一个整数
     */
    public static String extInteger(String s) {
        return extGroup(s, getPatternExt(INTEGER));
    }

    /**
     * 提取整数
     *
     * @param s 字符
     * @return 提取到的所有整数
     */
    public static List<String> extIntegerList(String s) {
        return extGroups(s, getPatternExt(INTEGER));
    }

    /**
     * 提取浮点数
     *
     * @param s 字符
     * @return 提取到的第一个浮点数
     */
    public static String extDouble(String s) {
        return extGroup(s, getPatternExt(DOUBLE));
    }

    /**
     * 提取浮点数
     *
     * @param s 字符
     * @return 提取到的所有浮点数
     */
    public static List<String> extDoubleList(String s) {
        return extGroups(s, getPatternExt(DOUBLE));
    }

    /**
     * 提取ipv4
     *
     * @param s 字符
     * @return 提取到的第一个ip
     */
    public static String extIpv4(String s) {
        return extGroup(s, getPatternExt(IPV4));
    }

    /**
     * 提取ipv4
     *
     * @param s 字符
     * @return 提取到的所有ip
     */
    public static List<String> extIpv4List(String s) {
        return extGroups(s, getPatternExt(IPV4));
    }

    /**
     * 提取ipv6
     *
     * @param s 字符
     * @return 提取到的第一个ip
     */
    public static String extIpv6(String s) {
        return extGroup(s, getPatternExt(IPV6));
    }

    /**
     * 提取ipv6
     *
     * @param s 字符
     * @return 提取到的所有ip
     */
    public static List<String> extIpv6List(String s) {
        return extGroups(s, getPatternExt(IPV6));
    }

    /**
     * 提取mac
     *
     * @param s 字符
     * @return 提取到的第一个mac
     */
    public static String extMac(String s) {
        return extGroup(s, getPatternExt(MAC));
    }

    /**
     * 提取mac
     *
     * @param s 字符
     * @return 提取到的所有mac
     */
    public static List<String> extMacList(String s) {
        return extGroups(s, getPatternExt(MAC));
    }

    /**
     * 提取社会统一信用代码
     *
     * @param s 字符
     * @return 提取到的第一个社会统一信用代码
     */
    public static String extCreditCode(String s) {
        return extGroup(s, getPatternExt(CREDIT_CODE));
    }

    /**
     * 提取社会统一信用代码
     *
     * @param s 字符
     * @return 提取到的所有社会统一信用代码
     */
    public static List<String> extCreditCodeList(String s) {
        return extGroups(s, getPatternExt(CREDIT_CODE));
    }

    /**
     * 提取18位身份证号码
     *
     * @param s 字符
     * @return 提取到的第一个18位身份证号码
     */
    public static String extIdCard(String s) {
        return extGroup(s, getPatternExt(ID_CARD));
    }

    /**
     * 提取18位身份证号码
     *
     * @param s 字符
     * @return 提取到的所有18位身份证号码
     */
    public static List<String> extIdCardList(String s) {
        return extGroups(s, getPatternExt(ID_CARD));
    }

    /**
     * 提取中国车牌号码
     *
     * @param s 字符
     * @return 提取到的第一个中国车牌号码
     */
    public static String extPlateNumber(String s) {
        return extGroup(s, getPatternExt(PLATE_NUMBER));
    }

    /**
     * 提取中国车牌号码
     *
     * @param s 字符
     * @return 提取到的所有身份证号码
     */
    public static List<String> extPlateNumberList(String s) {
        return extGroups(s, getPatternExt(PLATE_NUMBER));
    }

}
