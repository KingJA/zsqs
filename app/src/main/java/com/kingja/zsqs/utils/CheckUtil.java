package com.kingja.zsqs.utils;

import android.text.TextUtils;

import java.util.regex.Pattern;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/3/25 16:25
 * 修改备注：
 */
public class CheckUtil {
    /**
     * 非空验证
     *
     * @param s
     * @param tip
     * @return
     */
    public static boolean checkEmpty(String s, String tip) {
        if (TextUtils.isEmpty(s)) {
            ToastUtil.showText(tip);
            return false;
        }
        return true;
    }

    public static boolean checkSame(String s1, String s2, String tip) {
        if (!s1.equals(s2)) {
            ToastUtil.showText(tip);
            return false;
        }
        return true;
    }


    public static boolean checkLngLat(double lng, double lat, String tip) {
        if (lng == -1 && lat == -1) {
            ToastUtil.showText(tip);
            return false;
        }
        return true;
    }

    public static boolean checkDefined(int value, String tip) {
        if (value == -1) {
            ToastUtil.showText(tip);
            return false;
        }
        return true;
    }


    /**
     * 验证是否以0开始
     *
     * @param s
     * @return
     */
    public static boolean checkZero(String s) {
        if ("0".equals(s) || s.startsWith("0")) {
            ToastUtil.showText("请输入非零数字");
            return false;
        }
        return true;
    }

    public static boolean checkUrl(String url, String tip) {
        if (!TextUtils.isEmpty(url) && !url.startsWith("http")) {
            ToastUtil.showText(tip);
            return false;
        }
        return true;
    }

    /**
     * 验证是否以0开始
     *
     * @param
     * @return
     */
    public static boolean checkAlias(String alias) {
        // 判断非空
        if (TextUtils.isEmpty(alias)) {
            ToastUtil.showText("请输入别名");
            return false;
        }

        // 不能数字开头，只能包含数字，字母，下划线
        if (!Pattern.matches(
                "^[a-zA-Z_][A-Za-z0-9_]*$", alias)) {
            ToastUtil.showText("别名格式不对");
            return false;
        }
        return true;
    }


    /**
     * 手机号码验证
     *
     * @param phone
     * @return
     */
    public static boolean checkPhoneFormat(String phone) {
        // 判断非空
        if (TextUtils.isEmpty(phone)) {
            ToastUtil.showText("请输入手机号码");
            return false;
        }

        // 判断手机号格式
        if (!Pattern.matches(
                "^(13[0-9]|14[0-9]|15[0-9]|16[0-9]|17[0-9]|18[0-9]|19[0-9])\\d{8}$", phone)) {
            ToastUtil.showText("手机号码格式不对");
            return false;
        }
        return true;
    }

    public static boolean checkIdcard(String idcard) {
        // 判断非空
        if (TextUtils.isEmpty(idcard)) {
            ToastUtil.showText("请输入身份证号");
            return false;
        }

        // 判断身份证号格式
        if (!Pattern.matches(
                "(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)", idcard)) {
            ToastUtil.showText("身份证号格式不对");
            return false;
        }
        return true;
    }

    public static boolean checkPhoneFormatAllowEmpty(String phone) {
        if (!TextUtils.isEmpty(phone)) {
            return checkPhoneFormat(phone);
        }
        return true;
    }

    /**
     * 密码格式验证
     *
     * @param password
     * @return
     */
    public static boolean checkPasswordFormat(String password) {
        // 判断非空
        if (TextUtils.isEmpty(password)) {
            ToastUtil.showText("密码不能为空");
            return false;
        }
        if (!Pattern.matches("[a-zA-Z0-9]{1,12}", password)) {
            ToastUtil.showText("密码须为1-12位字母或数字组合");
            return false;
        } else {
            return true;
        }
    }

    /**
     * 检查字符串最大长度
     *
     * @param input
     * @param lenght
     * @param tip
     * @return
     */
    public static boolean checkLengthMax(String input, int lenght, String tip) {

        if (input.length() > lenght) {
            ToastUtil.showText(tip);
            return false;
        }
        return true;
    }

    public static boolean checkLengthMin(String input, int lenght, String tip) {

        if (input.length() < lenght) {
            ToastUtil.showText(tip);
            return false;
        }
        return true;
    }

    public static boolean checkBirthday(String input, String tip) {

        if (input.length() != 11) {
            ToastUtil.showText(tip);
            return false;
        }
        return true;
    }

    /**
     * 校验18位身份证号码
     *
     * @param value
     * @return
     */
    public static boolean checkIdCard(final String value, String tip) {
        if (value == null || value.length() != 18) {
            ToastUtil.showText(tip);
            return false;
        }

        if (!value.matches("[\\d]+[X]?")) {
            ToastUtil.showText(tip);
            return false;
        }
        String code = "10X98765432";
        int weight[] = new int[]{7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1};
        int nSum = 0;
        for (int i = 0; i < 17; ++i) {
            nSum += (value.charAt(i) - '0') * weight[i];
        }
        int nCheckNum = nSum % 11;
        char chrValue = value.charAt(17);
        char chrCode = code.charAt(nCheckNum);
        if (chrValue == chrCode) {
            return true;
        }

        if (nCheckNum == 2 && (chrValue + ('a' - 'A') == chrCode)) {
            return true;
        }
        ToastUtil.showText(tip);
        return false;
    }

    public static boolean checkIdCardAllowEmpty(final String value) {
        if (!TextUtils.isEmpty(value)) {
            return checkIdcard(value);
        }
        return true;
    }

    public static boolean checkLngLat(double lng, double lat) {
        if (lng == -1 || lat == -1) {
            ToastUtil.showText("请进行定位");
            return false;
        }
        return true;
    }
}
