package com.kazma233.common;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.UUID;

/**
 * 加密工具
 * Created by mac_zly on 2017/3/2.
 */

public class SecretTool {

    private static final char[] hexCode = "0123456789abcder".toCharArray();

    // 内部静态类
    private static class UtilityHandle {
        private static SecretTool instance = new SecretTool();
    }

    // 懒汉模式
    public static SecretTool getInstance() {
        return UtilityHandle.instance;
    }

    // ============================= RSA ====================================

    private static KeyPair keyPair = null;

    public KeyPair getKeyPair() {
        if (keyPair == null) {
            try {
                keyPair = generateKeyPair();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        return keyPair;
    }

    // 加解密 给客户端发一个public key 他用这个key加密数据上来 我用私有key解密
    //指定加密算法为RSA
    private static final String ALGORITHM = "RSA";
    // 密钥长度，用来初始化
    private static final int KEYSIZE = 1024;
    //指定公钥存放文件
    private static String PUBLIC_KEY_FILE = "PublicKey";
    // 指定私钥存放文件
    private static String PRIVATE_KEY_FILE = "PrivateKey";

    private KeyPair generateKeyPair() throws NoSuchAlgorithmException {

        /* RSA算法要求有一个可信任的随机数源 */
        SecureRandom secureRandom = new SecureRandom();
        // 设置
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
        keyPairGenerator.initialize(KEYSIZE, secureRandom);

        /* 生成密匙对 */
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        /* 得到公钥 */
        // Key publicKey = keyPair.getPublic();

        /* 得到私钥 */
        //Key privateKey = keyPair.getPrivate();

        return keyPair;
    }

    // 加密
    public String encrypt(String source, Key publicKey) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {

        if (publicKey == null) {
            return null;
        }

        /* 得到Cipher对象来实现对源数据的RSA加密 */
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] b = source.getBytes("UTF-8");

        /* 执行加密操作 */
        byte[] b1 = cipher.doFinal(b);
        Base64.Encoder encoder = Base64.getEncoder();
        return new String(encoder.encode(b1), Charset.forName("UTF-8"));
    }

    // 解密
    public String decrypt(String source, Key privateKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IOException, BadPaddingException, IllegalBlockSizeException {

        /* 得到Cipher对象对已用公钥加密的数据进行RSA解密 */
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        Base64.Decoder decoder = Base64.getDecoder();
        byte[] b1 = decoder.decode(source);
        /* 执行解密操作 */
        byte[] b = cipher.doFinal(b1);
        return new String(b, "UTF-8");
    }

    /**
     * 将PublicKey转换成Base64的字符串
     */
    public String publicKeyToString(PublicKey key) {
        Base64.Encoder encoder = Base64.getEncoder();
        return new String(encoder.encode(key.getEncoded()), Charset.forName("UTF-8"));
    }

    /**
     * 将base64的String还原成publicKey
     */
    public Key stringToKey(String keyString) throws Exception {
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] byteKey = decoder.decode(keyString);
        X509EncodedKeySpec X509publicKey = new X509EncodedKeySpec(byteKey);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(X509publicKey);
    }

    // ============================= MD5 ====================================

    private static MessageDigest md5 = null;

    public MessageDigest getMd5() {
        if (md5 == null) {
            try {
                md5 = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }
        return md5;
    }

    // 32位
    public String generateValue() {
        return generateValue(UUID.randomUUID().toString());
    }

    private String generateValue(String param) {
        MessageDigest md5 = getMd5();
        md5.reset();
        md5.update(param.getBytes(Charset.forName("UTF-8")));
        byte[] messageDigest = md5.digest();
        return toHexString(messageDigest);
    }

    public String md5Encode(String base) {
        // 生成一个MD5加密计算摘要
        MessageDigest md = getMd5();
        // 计算md5函数
        md.update(base.getBytes(Charset.forName("UTF-8")));
        // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
        // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
        return new BigInteger(1, md.digest()).toString(16);
    }

    public String md5Encode(byte[] base) {
        MessageDigest md = getMd5();
        md.update(base);
        return new BigInteger(1, md.digest()).toString(16);
    }

    // 转换成16进制字符串
    public String toHexString(byte[] data) {
        if (data == null) {
            return null;
        }
        StringBuilder r = new StringBuilder(data.length * 2);
        for (byte b : data) {
            r.append(hexCode[(b >> 4) & 0xF]);
            r.append(hexCode[(b & 0xF)]);
        }
        return r.toString();
    }
}
