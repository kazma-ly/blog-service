package com.kazma233.common;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.SecureRandom;

public class EncryptUtils {

    public static final String DEFAULT_URL_ENCODING = "UTF-8";
    public static final String SHA1 = "SHA-1";
    public static final String MD5 = "MD5";

    private static SecureRandom random = new SecureRandom();

    /**
     * Hex编码.
     */
    public static String encodeHex(byte[] input) {
        return new String(Hex.encodeHex(input));
    }

    /**
     * Hex解码.
     */
    public static byte[] decodeHex(String input) {
        try {
            return Hex.decodeHex(input.toCharArray());
        } catch (DecoderException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Base64编码.
     */
    public static String encodeBase64(byte[] input) {
        return new String(Base64.encodeBase64(input), Charset.forName("UTF-8"));
    }

    /**
     * Base64编码.
     */
    public static String encodeBase64(String input) {
        try {
            return new String(Base64.encodeBase64(input.getBytes(DEFAULT_URL_ENCODING)), Charset.forName("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    /**
     * Base64解码.
     */
//    public static byte[] decodeBase64(String input) {
//        return Base64.decodeBase64(input.getBytes());
//    }

    /**
     * Base64解码.
     */
//    public static String decodeBase64String(String input) {
//        try {
//            return new String(Base64.decodeBase64(input.getBytes()), DEFAULT_URL_ENCODING);
//        } catch (UnsupportedEncodingException e) {
//            return "";
//        }
//    }

    /**
     * Html 转码.
     */
//    public static String escapeHtml(String html) {
//        return StringEscapeUtils.escapeHtml4(html);
//    }

    /**
     * Html 解码. 代码过时，需使用commons-text的StringEscapeUtils
     */
//    public static String unescapeHtml(String htmlEscaped) {
//        return StringEscapeUtils.unescapeHtml4(htmlEscaped);
//    }

    /**
     * 对输入字符串进行md5散列.
     */
//    public static byte[] md5(byte[] input) {
//        return digest(input, MD5, null, 1);
//    }
//
//    public static byte[] md5(byte[] input, int iterations) {
//        return digest(input, MD5, null, iterations);
//    }

    /**
     * 对输入字符串进行sha1散列.
     */
    public static byte[] sha1(byte[] input) {
        return digest(input, SHA1, null, 1);
    }

    public static byte[] sha1(byte[] input, byte[] salt) {
        return digest(input, SHA1, salt, 1);
    }
//
//    public static byte[] sha1(byte[] input, byte[] salt, int iterations) {
//        return digest(input, SHA1, salt, iterations);
//    }

    /**
     * 对字符串进行散列, 支持md5与sha1算法.
     */
    private static byte[] digest(byte[] input, String algorithm, byte[] salt, int iterations) {
        try {
            //初始化sha1的加密算法
            MessageDigest digest = MessageDigest.getInstance(algorithm);

            //加入盐
            if (salt != null) {
                digest.update(salt);
            }
            //加密
            byte[] result = digest.digest(input);

            //进行多少次散列
            for (int i = 1; i < iterations; i++) {
                digest.reset();
                result = digest.digest(result);
            }
            return result;
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        return null;
    }
//
//    /**
//     * 生成随机的Byte[]作为salt.
//     *
//     * @param numBytes byte数组的大小
//     */
//    public static byte[] generateSalt(int numBytes) {
//        Validate.isTrue(numBytes > 0, "numBytes argument must be a positive integer (1 or larger)", numBytes);
//
//        byte[] bytes = new byte[numBytes];
//        random.nextBytes(bytes);
//        return bytes;
//    }
//
//    /**
//     * 对文件进行md5散列.
//     */
//    public static byte[] md5(InputStream input) throws IOException {
//        return digest(input, MD5);
//    }
//
//    /**
//     * 对文件进行sha1散列.
//     */
//    public static byte[] sha1(InputStream input) throws IOException {
//        return digest(input, SHA1);
//    }
//
    private static byte[] digest(InputStream input, String algorithm) throws IOException {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            int bufferLength = 8 * 1024;
            byte[] buffer = new byte[bufferLength];
            int read = input.read(buffer, 0, bufferLength);

            while (read > -1) {
                messageDigest.update(buffer, 0, read);
                read = input.read(buffer, 0, bufferLength);
            }

            return messageDigest.digest();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        return null;
    }
}
