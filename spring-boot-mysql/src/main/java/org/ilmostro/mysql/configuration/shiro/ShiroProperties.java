package org.ilmostro.mysql.configuration.shiro;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author li.bowei
 **/
@Getter
@Setter
@ConfigurationProperties(prefix = "org.ilmostro.shiro")
public class ShiroProperties {

    private String[] allow = new String[]{};
    private JwtProperties jwt = new JwtProperties();

    @Getter
    @Setter
    public static class JwtProperties{
        private String secret = "df3sdfaDdfadf@$t1#adfag1GasdfGdadfat13thasadsffgat4t%^12eas14tq454adsfys";
        private Long expiration = 60 * 60L;
        private Long expirationRemember = 60 * 60 * 24 * 7L;
    }
}
