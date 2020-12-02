package org.ilmostro.webflux.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.support.DefaultServerCodecConfigurer;

/**
 * @author li.bowei
 * @date 2020/12/2 17:06
 */
@Configuration
public class ServerCodecConfiguration {

    @Bean
    public DefaultServerCodecConfigurer getDefaultServerCodecConfigurer(){
        return new DefaultServerCodecConfigurer();
    }
}
