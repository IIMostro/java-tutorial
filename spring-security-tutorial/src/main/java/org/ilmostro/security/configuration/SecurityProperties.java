package org.ilmostro.security.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author li.bowei
 **/
@ConfigurationProperties(prefix = "org.ilmostro.security")
@Getter
@Setter
public class SecurityProperties {

    private JwtProperties jwt = new JwtProperties();
    private String[] access = new String[]{};

    @Getter
    @Setter
    public static class JwtProperties{

        private String header = "Authorization";
        private String prefix = "Bearer";
        private String secret = "df3sdfaDdfadf@$t1#adfag1GasdfGdadfat13thasadsffgat4t%^12eas14tq454adsfys";
        private Long expiration = 60 * 60L;
        private Long expirationRemember = 60 * 60 * 24 * 7L;
    }
}
