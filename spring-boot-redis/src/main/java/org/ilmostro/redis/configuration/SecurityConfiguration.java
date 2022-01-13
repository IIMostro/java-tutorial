package org.ilmostro.redis.configuration;

import org.ilmostro.redis.security.filter.JwtAuthorizationFilter;
import org.ilmostro.redis.security.handler.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityConfiguration {

    private final SecurityProperties properties;
    private final StringRedisTemplate stringRedisTemplate;
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
    private final CustomLogoutSuccessHandler customLogoutSuccessHandler;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    public SecurityConfiguration(SecurityProperties properties,
                                 StringRedisTemplate stringRedisTemplate,
                                 CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler,
                                 CustomAuthenticationFailureHandler customAuthenticationFailureHandler,
                                 CustomLogoutSuccessHandler customLogoutSuccessHandler,
                                 CustomAccessDeniedHandler customAccessDeniedHandler,
                                 CustomAuthenticationEntryPoint customAuthenticationEntryPoint) {
        this.properties = properties;
        this.stringRedisTemplate = stringRedisTemplate;
        this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
        this.customAuthenticationFailureHandler = customAuthenticationFailureHandler;
        this.customLogoutSuccessHandler = customLogoutSuccessHandler;
        this.customAccessDeniedHandler = customAccessDeniedHandler;
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
    }

    @ConditionalOnProperty(prefix = "security", name = "model", havingValue = "interceptor")
    @Order(-1)
    @Configuration
    public static class DisableSecurityConfiguration extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable().authorizeRequests().anyRequest().permitAll().and().logout().permitAll();
        }
    }

    @Configuration
    @EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true)
    @ConditionalOnProperty(prefix = "security", name = "model", havingValue = "spring", matchIfMissing = true)
    @Order(0)
    public class EnableSecurityConfiguration extends WebSecurityConfigurerAdapter {
        @Bean
        public BCryptPasswordEncoder bCryptPasswordEncoder() {
            return new BCryptPasswordEncoder();
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                    .antMatchers(properties.getAccess()).permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .csrf()
                    .disable()
                    .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessHandler(customLogoutSuccessHandler)
                    .permitAll()
                    .and()
                    .addFilter(new JwtAuthorizationFilter(authenticationManager(), stringRedisTemplate, properties))
                    .exceptionHandling().authenticationEntryPoint(customAuthenticationEntryPoint)
                    .accessDeniedHandler(customAccessDeniedHandler);
            http.addFilterAt(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        }

        @Bean
        public CustomUsernamePasswordAuthenticationFilter customAuthenticationFilter() throws Exception {
            CustomUsernamePasswordAuthenticationFilter filter = new CustomUsernamePasswordAuthenticationFilter();
            filter.setAuthenticationSuccessHandler(customAuthenticationSuccessHandler);
            filter.setAuthenticationFailureHandler(customAuthenticationFailureHandler);
            filter.setFilterProcessesUrl("/login");
            //这句很关键，重用WebSecurityConfigurerAdapter配置的AuthenticationManager，不然要自己组装AuthenticationManager
            filter.setAuthenticationManager(authenticationManagerBean());
            return filter;
        }
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
