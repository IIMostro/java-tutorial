package org.ilmostro.redis.security.filter;

import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.ilmostro.redis.configuration.SecurityProperties;
import org.ilmostro.redis.utils.JwtTokenUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author li.bowei
 */
@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final StringRedisTemplate stringRedisTemplate;
    private final SecurityProperties properties;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager,
                                  StringRedisTemplate stringRedisTemplate,
                                  SecurityProperties properties) {
        super(authenticationManager);
        this.stringRedisTemplate = stringRedisTemplate;
        this.properties = properties;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {

        SecurityProperties.JwtProperties jwt = properties.getJwt();
        String token = request.getHeader(jwt.getHeader());
        if (token == null || !token.startsWith(jwt.getPrefix())) {
            SecurityContextHolder.clearContext();
            chain.doFilter(request, response);
            return;
        }
        String tokenValue = token.replace(jwt.getPrefix(), "");
        UsernamePasswordAuthenticationToken authentication = null;
        try {
            String previousToken = stringRedisTemplate.opsForValue().get(JwtTokenUtils.getId(tokenValue));
            if (!token.equals(previousToken)) {
                SecurityContextHolder.clearContext();
                chain.doFilter(request, response);
                return;
            }
            authentication = JwtTokenUtils.getAuthentication(tokenValue);
        } catch (JwtException e) {
            logger.error("Invalid jwt : " + e.getMessage());
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }
}


