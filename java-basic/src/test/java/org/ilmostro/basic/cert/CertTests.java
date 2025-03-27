package org.ilmostro.basic.cert;

import org.apache.commons.codec.binary.Hex;
import org.junit.jupiter.api.Test;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Key;
import java.util.Base64;

public class CertTests {


    @Test
    void test_cert_general_hmac_key_should_work() throws Exception{
        // 1. 读取私钥文件内容
        byte[] keyBytes = Files.readAllBytes(Paths.get("D:\\private_key.pem"));

        // 2. 移除 PEM 格式中的非密钥部分（如 “-----BEGIN PRIVATE KEY-----” 和 “-----END PRIVATE KEY-----” 部分）
        String keyString = new String(keyBytes);
        keyString = keyString.replace("-----BEGIN PRIVATE KEY-----", "")
            .replace("-----END PRIVATE KEY-----", "")
            .replaceAll("\\s", ""); // 移除所有空格

        // 3. 将 PEM 格式的 Base64 编码密钥转换为字节数组
        byte[] decodedKey = Base64.getDecoder().decode(keyString);

        // 4. 使用密钥生成 HMAC
        String message = "your_message_here";
        Mac mac = Mac.getInstance("HmacSHA256"); // 使用 HMAC-SHA256 算法
        Key secretKey = new SecretKeySpec(decodedKey, "HmacSHA256");
        mac.init(secretKey);

        // 5. 计算 HMAC 值
        byte[] hmacBytes = mac.doFinal(message.getBytes("UTF-8"));

        // 6. 将 HMAC 值转换为 Hex 或 Base64 格式的字符串
        String hmacHex = Hex.encodeHexString(hmacBytes);
        System.out.println("HMAC (Hex): " + hmacHex);
    }
}
