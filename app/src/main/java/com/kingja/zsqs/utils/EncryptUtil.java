package com.kingja.zsqs.utils;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.UUID;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Description:TODO
 * Create Time:2018/7/6 15:18
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class EncryptUtil {

    public static String getDoubleMd5(String text) {
        return getMd5(getMd5(text));
    }

    public static String getMd5UUID() {
        return getMd5(UUID.randomUUID().toString());
    }

    public static String getMd5(String text) {
        String re_md5 = new String();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(text.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
            re_md5 = buf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return re_md5;
    }

    public static String getSignature(long timeStamp, String token) {
        String signature = timeStamp + token;
        char[] chars = signature.toCharArray();
        Arrays.sort(chars);
        signature = getMd5(String.valueOf(chars)).toUpperCase();
        return signature;
    }

    public static void main(String[] args) {
//        System.out.println("排序后:" + GetSignature(1533692048653L, "98f8a2d67d5e454f85c2469696fa66ee"));
//        String content = "123456";
//        String password = "jdphls";
//        System.out.println("加密前：" + content);
//        String encrypt = encrypt(content, password);
//        System.out.println("加密后：" + encrypt.toString());
//        System.out.println("解密后：" +decrypt(encrypt, password));
    }

    public static void test() {
        String content = "test";
        String password = "123456";
        System.out.println("加密前：" + content);
        String encrypt = encrypt(content, password);
        System.out.println("加密后：" + encrypt.toString());
        System.out.println("解密后：" +decrypt(encrypt, password));
    }

    /**
     * 加密
     *
     * @param content  需要加密的内容
     * @param password 加密密码
     * @return
     */
    public static String encrypt(String content, String password) {
        KeyGenerator kgen = null;
        try {
            kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, new SecureRandom(password.getBytes()));
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
            byte[] byteContent = content.getBytes("utf-8");
            byte[] result = cipher.doFinal(byteContent);
            return result.toString();//加密
        } catch (NoSuchAlgorithmException | InvalidKeyException
                | NoSuchPaddingException | BadPaddingException
                | UnsupportedEncodingException | IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密
     *
     * @param content  待解密内容
     * @param password 解密密钥
     * @return
     */
    public static String decrypt(String content, String password) {
        KeyGenerator kgen = null;
        try {
            kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, new SecureRandom(password.getBytes()));
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(Base64.decode(content, Base64.DEFAULT));
            String resultStr = new String(result);
            return resultStr; // 解密
        } catch (NoSuchAlgorithmException | BadPaddingException
                | IllegalBlockSizeException | NoSuchPaddingException
                | InvalidKeyException e) {
            e.printStackTrace();
        }
        return null;

    }

}
