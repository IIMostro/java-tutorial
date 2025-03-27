package org.ilmostro.basic.security;

import lombok.SneakyThrows;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public class JwtCertProvider {

    private final PrivateKey privateKey;

    public JwtCertProvider(String key) {
        this.privateKey = loadPrivateKeyFromPem(key);
    }



    @SneakyThrows
    private static PrivateKey loadPrivateKeyFromPem(String pem) {
        String privateKeyPEM = pem.replace("-----BEGIN PRIVATE KEY-----", "")
            .replace("-----END PRIVATE KEY-----", "")
            .replaceAll("\\s", "");  // 移除所有空白字符

        byte[] encoded = Base64.getDecoder().decode(privateKeyPEM);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");  // 使用 RSA 密钥
        return keyFactory.generatePrivate(keySpec);
    }
}
