package com.svw.dealerapp.util;

import android.text.TextUtils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

/**
 * Created by qinshi on 5/19/2017.
 */

public class StringUtils {

    /**
     * 匹配正则表达式
     *
     * @param pattern
     * @param content
     * @return
     */
    public static boolean isMatchPattern(String pattern, String content) {
        if (TextUtils.isEmpty(pattern) || TextUtils.isEmpty(content)) {
            return false;
        }
        return Pattern.matches(pattern, content);
    }

    /**
     * 计算MD5
     *
     * @param string
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String md5Encode(String string) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(string.getBytes());
        return new BigInteger(1, md5.digest()).toString(16);
    }

    /**
     * 获取字符串中某个字符出现指定次数的位置
     *
     * @param string
     * @param charStr
     * @param times
     * @return
     */
    public static int getCharPosition(String string, String charStr, int times) {
        int j = 0;
        for (int i = 0; i < string.length(); i++) {
            if (charStr.equals(string.substring(i, i + 1))) {
                j++;
                if (j == times) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * 是否为合法的手机号码
     *
     * @return true合法，反之为false
     */
    public static boolean isValidMobile(String mobile) {
        if (TextUtils.isEmpty(mobile)) {
            return false;
        }
        if (mobile.length() != 11) {
            return false;
        }
        if (!mobile.startsWith("13") && !mobile.startsWith("14") && !mobile.startsWith("15")
                && !mobile.startsWith("17") && !mobile.startsWith("18")) {
            return false;
        }
        return true;
    }

    /**
     * 是否为合法的email地址
     *
     * @return true合法，反之为false
     */
    public static boolean isValidEmail(String email) {
        if (TextUtils.isEmpty(email)) {
            return false;
        }
        if (email.length() <= 2) {
            return false;
        }
        if (!email.contains("@") || !email.contains(".")) {
            return false;
        }
        return true;
    }

    /**
     * 身份证号是否合法
     *
     * @param id 身份证号
     * @return true:合法
     */
    public static boolean isValidID(String id) {
        if (TextUtils.isEmpty(id)) {
            return false;
        }
        String ID = "^(\\d{14}|\\d{17})(\\d|[xX])$";
        String ID15 = "/^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$/";
        String ID18 = "/^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$/";
//        return id.matches(ID15) || id.matches(ID18);
        return id.matches(ID);
    }
}
