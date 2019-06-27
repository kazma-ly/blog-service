package com.kazma233.common;

import javax.crypto.Cipher;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSATool {

    // 内部静态类
    private static class UtilityHandle {
        private static RSATool instance = new RSATool();
    }

    // 懒汉模式
    public static RSATool getInstance() {
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
        return keyPairGenerator.generateKeyPair();
    }

    // 加密
    public String encrypt(String source, Key publicKey) throws Exception {
        if (publicKey == null) {
            return null;
        }

        /* 得到Cipher对象来实现对源数据的RSA加密 */
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] b = source.getBytes(StandardCharsets.UTF_8);

        /* 执行加密操作 */
        byte[] b1 = cipher.doFinal(b);
        Base64.Encoder encoder = Base64.getEncoder();
        return new String(encoder.encode(b1), Charset.forName("UTF-8"));
    }

    // 解密
    public String decrypt(String source, Key privateKey) throws Exception {

        /* 得到Cipher对象对已用公钥加密的数据进行RSA解密 */
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        Base64.Decoder decoder = Base64.getDecoder();
        byte[] b1 = decoder.decode(source);
        /* 执行解密操作 */
        byte[] b = cipher.doFinal(b1);
        return new String(b, StandardCharsets.UTF_8);
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
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(byteKey);
        KeyFactory kf = KeyFactory.getInstance("RSA");

        return kf.generatePublic(x509EncodedKeySpec);
    }

}
