package com.orion.utils.crypto;

import com.orion.utils.Exceptions;
import com.orion.utils.Strings;
import com.orion.utils.crypto.enums.HashMessageDigest;
import com.orion.utils.crypto.enums.SecretKeySpecMode;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;

/**
 * 签名工具类
 *
 * @author Li
 * @version 1.0.0
 * @since 2019/9/10 9:46
 */
public class Signatures {

    private Signatures() {
    }

    // -------------------- hash sign --------------------

    /**
     * MD5签名
     *
     * @param s 明文
     * @return 签名
     */
    public static String md5(String s) {
        return hashSign(Strings.bytes(s), HashMessageDigest.MD5.getMessageDigest());
    }

    /**
     * MD5签名
     *
     * @param bs 明文
     * @return 签名
     */
    public static String md5(byte[] bs) {
        return hashSign(bs, HashMessageDigest.MD5.getMessageDigest());
    }

    /**
     * MD5签名
     *
     * @param s    明文
     * @param salt 盐
     * @return 签名
     */
    public static String md5(String s, String salt) {
        return md5(s, salt, 1);
    }

    /**
     * MD5签名
     *
     * @param bs   明文
     * @param salt 盐
     * @return 签名
     */
    public static String md5(byte[] bs, byte[] salt) {
        return md5(bs, salt, 1);
    }

    /**
     * MD5签名
     *
     * @param s     明文
     * @param salt  盐
     * @param times 签名次数
     * @return 签名
     */
    public static String md5(String s, String salt, int times) {
        return md5(Strings.bytes(s), Strings.bytes(salt), times);
    }

    /**
     * MD5签名
     *
     * @param bs    明文
     * @param salt  盐
     * @param times 签名次数
     * @return 签名
     */
    public static String md5(byte[] bs, byte[] salt, int times) {
        try {
            MessageDigest digest = HashMessageDigest.MD5.getMessageDigest();
            digest.update(bs);
            for (int i = 0; i < times; i++) {
                digest.update(salt);
            }
            return toHex(digest.digest(bs));
        } catch (Exception e) {
            Exceptions.printStacks(e);
            return null;
        }
    }

    /**
     * SHA1签名
     *
     * @param s 明文
     * @return 签名
     */
    public static String sha1(String s) {
        return hashSign(Strings.bytes(s), HashMessageDigest.SHA1.getMessageDigest());
    }

    /**
     * SHA1签名
     *
     * @param bs 明文
     * @return 签名
     */
    public static String sha1(byte[] bs) {
        return hashSign(bs, HashMessageDigest.SHA1.getMessageDigest());
    }

    /**
     * SHA224签名
     *
     * @param s 明文
     * @return 签名
     */
    public static String sha224(String s) {
        return hashSign(Strings.bytes(s), HashMessageDigest.SHA224.getMessageDigest());
    }

    /**
     * SHA224签名
     *
     * @param bs 明文
     * @return 签名
     */
    public static String sha224(byte[] bs) {
        return hashSign(bs, HashMessageDigest.SHA224.getMessageDigest());
    }

    /**
     * SHA256签名
     *
     * @param s 明文
     * @return 签名
     */
    public static String sha256(String s) {
        return hashSign(Strings.bytes(s), HashMessageDigest.SHA256.getMessageDigest());
    }

    /**
     * SHA256签名
     *
     * @param bs 明文
     * @return 签名
     */
    public static String sha256(byte[] bs) {
        return hashSign(bs, HashMessageDigest.SHA256.getMessageDigest());
    }

    /**
     * SHA384签名
     *
     * @param s 明文
     * @return 签名
     */
    public static String sha384(String s) {
        return hashSign(Strings.bytes(s), HashMessageDigest.SHA384.getMessageDigest());
    }

    /**
     * SHA384签名
     *
     * @param bs 明文
     * @return 签名
     */
    public static String sha384(byte[] bs) {
        return hashSign(bs, HashMessageDigest.SHA384.getMessageDigest());
    }

    /**
     * SHA512签名
     *
     * @param s 明文
     * @return 签名
     */
    public static String sha512(String s) {
        return hashSign(Strings.bytes(s), HashMessageDigest.SHA512.getMessageDigest());
    }

    /**
     * SHA512签名
     *
     * @param bs 明文
     * @return 签名
     */
    public static String sha512(byte[] bs) {
        return hashSign(bs, HashMessageDigest.SHA512.getMessageDigest());
    }

    /**
     * hash签名
     *
     * @param s    明文
     * @param type 签名类型 MD5 SHA-1 SHA-224 SHA-256 SHA-384 SHA-512
     * @return 签名
     */
    public static String sign(String s, String type) {
        return hashSign(Strings.bytes(s), HashMessageDigest.getMessageDigest(type));
    }

    /**
     * hash签名
     *
     * @param bs   明文
     * @param type 签名类型 MD5 SHA-1 SHA-224 SHA-256 SHA-384 SHA-512
     * @return 签名
     */
    public static String sign(byte[] bs, String type) {
        return hashSign(bs, HashMessageDigest.getMessageDigest(type));
    }

    /**
     * 散列签名的方法
     *
     * @param bs     明文
     * @param digest MessageDigest
     * @return 签名
     */
    private static String hashSign(byte[] bs, MessageDigest digest) {
        try {
            return toHex(digest.digest(bs));
        } catch (Exception e) {
            Exceptions.printStacks(e);
            return null;
        }
    }

    // -------------------- MAC hash sign --------------------

    /**
     * hmac + md5签名
     *
     * @param s   明文
     * @param key key
     * @return 密文
     */
    public static String hmacMd5(String s, String key) {
        return hmacHashSign(Strings.bytes(s), Strings.bytes(key), SecretKeySpecMode.HMAC_MD5);
    }

    /**
     * hmac + md5签名
     *
     * @param bs  明文
     * @param key key
     * @return 密文
     */
    public static String hmacMd5(byte[] bs, byte[] key) {
        return hmacHashSign(bs, key, SecretKeySpecMode.HMAC_MD5);
    }

    /**
     * hmac + sha1签名
     *
     * @param s   明文
     * @param key key
     * @return 密文
     */
    public static String hmacSha1(String s, String key) {
        return hmacHashSign(Strings.bytes(s), Strings.bytes(key), SecretKeySpecMode.HMAC_SHA1);
    }

    /**
     * hmac + sha1签名
     *
     * @param bs  明文
     * @param key key
     * @return 密文
     */
    public static String hmacSha1(byte[] bs, byte[] key) {
        return hmacHashSign(bs, key, SecretKeySpecMode.HMAC_SHA1);
    }

    /**
     * hmac + sha224签名
     *
     * @param s   明文
     * @param key key
     * @return 密文
     */
    public static String hmacSha224(String s, String key) {
        return hmacHashSign(Strings.bytes(s), Strings.bytes(key), SecretKeySpecMode.HMAC_SHA224);
    }

    /**
     * hmac + sha224签名
     *
     * @param bs  明文
     * @param key key
     * @return 密文
     */
    public static String hmacSha224(byte[] bs, byte[] key) {
        return hmacHashSign(bs, key, SecretKeySpecMode.HMAC_SHA224);
    }

    /**
     * hmac + sha256签名
     *
     * @param s   明文
     * @param key key
     * @return 密文
     */
    public static String hmacSha256(String s, String key) {
        return hmacHashSign(Strings.bytes(s), Strings.bytes(key), SecretKeySpecMode.HMAC_SHA256);
    }

    /**
     * hmac + sha256签名
     *
     * @param bs  明文
     * @param key key
     * @return 密文
     */
    public static String hmacSha256(byte[] bs, byte[] key) {
        return hmacHashSign(bs, key, SecretKeySpecMode.HMAC_SHA256);
    }

    /**
     * hmac + sha384签名
     *
     * @param s   明文
     * @param key key
     * @return 密文
     */
    public static String hmacSha384(String s, String key) {
        return hmacHashSign(Strings.bytes(s), Strings.bytes(key), SecretKeySpecMode.HMAC_SHA384);
    }

    /**
     * hmac + sha384签名
     *
     * @param bs  明文
     * @param key key
     * @return 密文
     */
    public static String hmacSha384(byte[] bs, byte[] key) {
        return hmacHashSign(bs, key, SecretKeySpecMode.HMAC_SHA384);
    }

    /**
     * hmac + sha512签名
     *
     * @param s   明文
     * @param key key
     * @return 密文
     */
    public static String hmacSha512(String s, String key) {
        return hmacHashSign(Strings.bytes(s), Strings.bytes(key), SecretKeySpecMode.HMAC_SHA512);
    }

    /**
     * hmac + sha512签名
     *
     * @param bs  明文
     * @param key key
     * @return 密文
     */
    public static String hmacSha512(byte[] bs, byte[] key) {
        return hmacHashSign(bs, key, SecretKeySpecMode.HMAC_SHA512);
    }

    /**
     * hmac + hash签名
     *
     * @param s    明文
     * @param key  key
     * @param mode SecretKeySpecMode
     * @return 密文
     */
    public static String hmacSign(String s, String key, SecretKeySpecMode mode) {
        return hmacHashSign(Strings.bytes(s), Strings.bytes(key), mode);
    }

    /**
     * hmac + hash签名
     *
     * @param bs   明文
     * @param key  key
     * @param mode SecretKeySpecMode
     * @return 密文
     */
    public static String hmacSign(byte[] bs, byte[] key, SecretKeySpecMode mode) {
        return hmacHashSign(bs, key, mode);
    }

    /**
     * mac + hash签名
     *
     * @param bs   明文
     * @param key  key
     * @param mode SecretKeySpecMode
     * @return 签名
     */
    public static String hmacHashSign(byte[] bs, byte[] key, SecretKeySpecMode mode) {
        try {
            SecretKeySpec secretKey = mode.getSecretKeySpec(key);
            Mac mac = Mac.getInstance(mode.getMode());
            mac.init(secretKey);
            return toHex(mac.doFinal(bs));
        } catch (Exception e) {
            return null;
        }
    }

    private static String toHex(byte[] bs) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bs) {
            sb.append(Integer.toHexString((0x000000FF & b) | 0xFFFFFF00).substring(6));
        }
        return sb.toString();
    }

}
