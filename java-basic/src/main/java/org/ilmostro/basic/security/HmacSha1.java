package org.ilmostro.basic.security;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class HmacSha1 {

    public static void main(String[] args) throws Exception{
        final var sk = "XAUAOM3E3UOCHCWLZSLJ";
        final var context = "GET\n\n\nFri, 20 Sep 2024 16:40:20 GMT\n\n/stg-sjz-hw-closeli-settings-test?delimiter=/&max-keys=1&prefix=magik";
        final var bytes = sk.getBytes();
        final var hmacSHA1 = new SecretKeySpec(bytes, "HmacSHA1");
        final var mac = Mac.getInstance("HmacSHA1");
        mac.init(hmacSHA1);
        final var encrypt = mac.doFinal(context.getBytes());
        final var string = Base64.getEncoder().encodeToString(encrypt);
        System.out.println(string);
    }
}
