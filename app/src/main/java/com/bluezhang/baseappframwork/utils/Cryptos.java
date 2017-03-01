package com.bluezhang.baseappframwork.utils;

import android.util.Base64;

import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by blueZhang on 2017/2/28.
 *
 * @Author: BlueZhang
 * @date: 2017/2/28
 */

public class Cryptos {

    private static final String AES = "AES";
    private static final String HMACSHA1 = "HmacSHA1";

    private static final int DEFAULT_HMACSHA1_KEYSIZE = 160; // RFC2401
    private static final int DEFAULT_AES_KEYSIZE = 256;
    private static final int DEFAULT_IVSIZE = 16;

    private static SecureRandom random = new SecureRandom();
    private static BLEncrypt encrypt;

    static {
        encrypt = new BLEncrypt();
    }

    public static byte[] hmacSha1(byte[] input, byte[] key) throws GeneralSecurityException {
        try {
            SecretKey secretKey = new SecretKeySpec(key, HMACSHA1);
            Mac mac = Mac.getInstance(HMACSHA1);
            mac.init(secretKey);
            return mac.doFinal(input);
        } catch (GeneralSecurityException e) {
            throw e;
        }
    }

    public static boolean isMacValid(byte[] expected, byte[] input, byte[] key) throws GeneralSecurityException {
        byte[] actual = hmacSha1(input, key);
        return Arrays.equals(expected, actual);
    }


    public static byte[] generateHmacSha1Key() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(HMACSHA1);
            keyGenerator.init(DEFAULT_HMACSHA1_KEYSIZE);
            SecretKey secretKey = keyGenerator.generateKey();
            return secretKey.getEncoded();
        } catch (GeneralSecurityException e) {
            //throw e;
        }
        return null;
    }


    public static byte[] aesEncrypt(byte[] input, byte[] key)
            throws Exception {
        return aes(input, key, Cipher.ENCRYPT_MODE);
    }

    public static String aesEncrypt(String value)
            throws Exception {
        if (encrypt == null) {
            encrypt = new BLEncrypt();
        }        BLNetLogUtil.d(encrypt.getEncryptFirstIv() + " = " + encrypt.getEncryptFirstKey()
                + " = " + encrypt.getEncryptLastIv() + " = " + encrypt.getEncryptLastKey() + " = "
                + encrypt.getEncryptMode() + " = " + encrypt.getEncryptSecondIv() + " = " + encrypt.getEncryptSecondKey()
                + " = " + encrypt.getSignFirstKey() + " = " + encrypt.getSignLastKey() + " = " + encrypt.getSignSecondKey());
        byte[] b = aesEncrypt(value.getBytes(), BLStringUtil.concatString(encrypt.getEncryptFirstKey(),
                encrypt.getEncryptSecondKey(), encrypt.getEncryptLastKey()).getBytes(), BLStringUtil.concatString(
                encrypt.getEncryptFirstIv(), encrypt.getEncryptSecondIv(), encrypt.getEncryptLastIv()
        ).getBytes());
        return new String(Base64.encode(b, Base64.DEFAULT));
    }

    public static String aesDecrypt(String value) throws Exception {
        if (encrypt == null) {
            encrypt = new BLEncrypt();
        }
        byte[] bytes = Base64.decode(value.getBytes("utf-8"), Base64.DEFAULT);
        return aesDecrypt(bytes, BLStringUtil.concatString(encrypt.getEncryptFirstKey(),
                encrypt.getEncryptSecondKey(), encrypt.getEncryptLastKey()).getBytes(), BLStringUtil.concatString(
                encrypt.getEncryptFirstIv(), encrypt.getEncryptSecondIv(), encrypt.getEncryptLastIv()).getBytes());

    }

    private static byte[] aesEncrypt(byte[] input, byte[] key, byte[] iv)
            throws Exception {
        return aes(input, key, iv, Cipher.ENCRYPT_MODE);
    }


    public static String aesDecrypt(byte[] input, byte[] key) throws Exception {
        byte[] decryptResult = aes(input, key, Cipher.DECRYPT_MODE);
        return new String(decryptResult);
    }


    public static String aesDecrypt(byte[] input, byte[] key, byte[] iv) throws Exception {
        byte[] decryptResult = aes(input, key, iv, Cipher.DECRYPT_MODE);
        return new String(decryptResult);
    }


    private static byte[] aes(byte[] input, byte[] key, int mode)
            throws Exception {
        try {
            SecretKey secretKey = new SecretKeySpec(key, AES);
            Cipher cipher = Cipher.getInstance(AES);
            cipher.init(mode, secretKey);
            return cipher.doFinal(input);
        } catch (GeneralSecurityException e) {
            throw e;
        }
    }


    private static byte[] aes(byte[] input, byte[] key, byte[] iv, int mode) throws Exception {
        try {
            SecretKey secretKey = new SecretKeySpec(key, AES);
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            Cipher cipher = Cipher.getInstance(encrypt.getEncryptMode());
            cipher.init(mode, secretKey, ivSpec);
            return cipher.doFinal(input);
        } catch (GeneralSecurityException e) {
            throw e;
        }
    }


    public static byte[] generateAesKey() throws NoSuchAlgorithmException {
        return generateAesKey(DEFAULT_AES_KEYSIZE);
    }


    public static byte[] generateAesKey(int keysize) throws NoSuchAlgorithmException {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(AES);
            keyGenerator.init(keysize);
            SecretKey secretKey = keyGenerator.generateKey();
            return secretKey.getEncoded();
        } catch (GeneralSecurityException e) {
        }
        return null;
    }


    public static byte[] generateIV() {
        byte[] bytes = new byte[DEFAULT_IVSIZE];
        random.nextBytes(bytes);
        return bytes;
    }
}
