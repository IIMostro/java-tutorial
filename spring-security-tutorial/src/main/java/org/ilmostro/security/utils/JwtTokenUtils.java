package org.ilmostro.security.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.ilmostro.security.configuration.SecurityProperties;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.xml.bind.DatatypeConverter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author li.bowei
 */
@Component
public class JwtTokenUtils implements ApplicationContextAware, InitializingBean {

    private static SecurityProperties properties;
    private static SecretKey SECRET_KEY;
    private static ApplicationContext applicationContext;

    public static String createToken(String username, String id, List<String> roles, boolean isRememberMe) {
        SecurityProperties.JwtProperties jwt = properties.getJwt();
        long expiration = isRememberMe ? jwt.getExpirationRemember() : jwt.getExpiration();
        final Date createdDate = new Date();
        final Date expirationDate = new Date(createdDate.getTime() + expiration * 1000);
        String tokenPrefix = Jwts.builder()
                .setHeaderParam("type", "jwt")
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .claim("rol", String.join(",", roles))
                .setId(id)
                .setIssuer("SnailClimb")
                .setIssuedAt(createdDate)
                .setSubject(username)
                .setExpiration(expirationDate)
                .compact();
        return jwt.getPrefix() + " " + tokenPrefix; // 添加 token 前缀 "Bearer ";
    }

    public static String getId(String token) {
        Claims claims = getClaims(token);
        return claims.getId();
    }

    public static UsernamePasswordAuthenticationToken getAuthentication(String token) {
        Claims claims = getClaims(token);
        List<SimpleGrantedAuthority> authorities = getAuthorities(claims);
        String userName = claims.getSubject();
        return new UsernamePasswordAuthenticationToken(userName, token, authorities);
    }

    private static List<SimpleGrantedAuthority> getAuthorities(Claims claims) {
        String role = (String) claims.get("rol");
        return Arrays.stream(role.split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    private static Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    @Override
    public void afterPropertiesSet() {
        JwtTokenUtils.properties = applicationContext.getBean(SecurityProperties.class);
        byte[] bytes = DatatypeConverter.parseBase64Binary(properties.getJwt().getSecret());
        SECRET_KEY = Keys.hmacShaKeyFor(bytes);
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext context) throws BeansException {
        applicationContext = context;
    }
}
