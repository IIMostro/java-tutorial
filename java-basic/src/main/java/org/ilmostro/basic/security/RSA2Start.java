package org.ilmostro.basic.security;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author li.bowei
 **/
@Slf4j
public class RSA2Start {

    private static final String PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAI+3ERHoeRWBnoqnwO04zOlC+Fhf47d0hxNQalwhoLTXZfDe7pQZLMeWYb66odPCwswix79Xc2QyynFeG1vcABve48ITp9PDmbnnZoLbwuyEbHGecMuuSjS8J/Hntpx1ZIvxTLyMCndoOtlgBxJTfDtQ1Hz2OCSrDIkhbKlVBMz7AgMBAAECgYAdr/Fkw6MPoEEB33lpB4y8usrWZqiBox6sDLaL5Z5u/Pff9A1QpoqriBMU9TSsq9TVmnGswwHhNuS+2nPIW//hclBVbOanQpmRxjASe6a8iX1rWmK3YCT+rf4ugahgUntS3xGmj7N+AqPBQav3sSq+SBtNaj/RUf1nTPKafFFbwQJBANUWQX7lU08vwCA/BIBR8cIWJHMrDmZc8Z0xumzQKOPdREc/O3j2fDIb9fr+ErNGKwk8TUmVRFB5RWCd3FhFaOsCQQCsqFSmOxriwWhA0onSXEnpN2MR+EokaEX5/vGODtfPyTYlfFJ+QeZLdI0jqPL5AMlQc7y/3O/6FYdBF+DwRSgxAkEAg9P2ByyXItlW0lxKS5zmpX2PZVTQ3tFHhUm7nRAXDAna8P0UrRxI77NOtc40AYc6nscYxZo8HlE4c7KWHGIsqwJAIkD7Gkebm+UXE9UxNqTMnRI5+3Sw8OwnBeQIdFCJjFW1AlbZ3uagE9Q5tBtuqOv5BJcRmVbx3UGsJcPhAooV4QJAWSmA8LSNqCKE2zgIJa1wzOZMemUydOf7oao5El/pI4QXRaUMly+pvA5e4F65NEUI1mb8zA7a90aIrdGssNCixQ==";
    private static final String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCPtxER6HkVgZ6Kp8DtOMzpQvhYX+O3dIcTUGpcIaC012Xw3u6UGSzHlmG+uqHTwsLMIse/V3NkMspxXhtb3AAb3uPCE6fTw5m552aC28LshGxxnnDLrko0vCfx57acdWSL8Uy8jAp3aDrZYAcSU3w7UNR89jgkqwyJIWypVQTM+wIDAQAB";
    private static final int MAX_ENCRYPT_BLOCK = 117;
    private static final int MAX_DECRYPT_BLOCK = 128;
    private static final String SIGNATURE_INSTANCE = "MD5withRSA";
    private static final String KEY_TYPE = "RSA";
    private static final String CHAR_SET = "UTF-8";

    public static void main(String[] args) throws Exception {
        String name = "li.bowei";
        String encrypt = encrypt(name, getPublicKey(PUBLIC_KEY));
        String decrypt = decrypt(encrypt, getPrivateKey(PRIVATE_KEY));
        log.info("decrypt:{}", decrypt);
        String sign = sign(name, getPrivateKey(PRIVATE_KEY));
        boolean verify = verify(name, getPublicKey(PUBLIC_KEY), sign);
        log.info("sign:{}, verify:{}", sign, verify);
    }

    /**
     * 获取私钥
     *
     * @param privateKey 私钥字符串
     * @return 私钥
     */
    public static PrivateKey getPrivateKey(String privateKey) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_TYPE);
        byte[] decodedKey = Base64.decodeBase64(privateKey.getBytes());
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
        return keyFactory.generatePrivate(keySpec);
    }

    /**
     * 获取公钥
     *
     * @param publicKey 公钥字符串
     * @return 公钥
     */
    public static PublicKey getPublicKey(String publicKey) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_TYPE);
        byte[] decodedKey = Base64.decodeBase64(publicKey.getBytes());
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
        return keyFactory.generatePublic(keySpec);
    }

    /**
     * RSA加密
     *
     * @param data      待加密数据
     * @param publicKey 公钥
     * @return 密文
     */
    public static String encrypt(String data, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance(KEY_TYPE);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        int inputLen = data.getBytes().length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offset = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offset > 0) {
            if (inputLen - offset > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data.getBytes(), offset, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data.getBytes(), offset, inputLen - offset);
            }
            out.write(cache, 0, cache.length);
            i++;
            offset = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        // 获取加密内容使用base64进行编码,并以UTF-8为标准转化成字符串
        // 加密后的字符串
        return new String(Base64.encodeBase64(encryptedData));
    }

    /**
     * RSA解密
     *
     * @param data       待解密数据
     * @param privateKey 私钥
     * @return 明文
     */
    public static String decrypt(String data, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance(KEY_TYPE);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] dataBytes = Base64.decodeBase64(data.getBytes());
        int inputLen = dataBytes.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offset = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offset > 0) {
            if (inputLen - offset > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(dataBytes, offset, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(dataBytes, offset, inputLen - offset);
            }
            out.write(cache, 0, cache.length);
            i++;
            offset = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        // 解密后的内容
        return new String(decryptedData, CHAR_SET);
    }

    /**
     * 签名
     *
     * @param data       待签名数据
     * @param privateKey 私钥
     * @return 签名
     */
    public static String sign(String data, PrivateKey privateKey) throws Exception {
        byte[] keyBytes = privateKey.getEncoded();
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_TYPE);
        PrivateKey key = keyFactory.generatePrivate(keySpec);
        Signature signature = Signature.getInstance(SIGNATURE_INSTANCE);
        signature.initSign(key);
        signature.update(data.getBytes());
        return new String(Base64.encodeBase64(signature.sign()));
    }

    /**
     * 验签
     *
     * @param srcData   原始字符串
     * @param publicKey 公钥
     * @param sign      签名
     * @return 是否验签通过
     */
    public static boolean verify(String srcData, PublicKey publicKey, String sign) throws Exception {
        byte[] keyBytes = publicKey.getEncoded();
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_TYPE);
        PublicKey key = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance(SIGNATURE_INSTANCE);
        signature.initVerify(key);
        signature.update(srcData.getBytes());
        return signature.verify(Base64.decodeBase64(sign.getBytes()));
    }
}
