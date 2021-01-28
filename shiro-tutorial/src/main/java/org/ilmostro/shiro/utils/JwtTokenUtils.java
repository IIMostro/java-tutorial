package org.ilmostro.shiro.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.ilmostro.shiro.configuration.JwtConstant;
import org.ilmostro.shiro.configuration.ShiroProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import javax.xml.bind.DatatypeConverter;
import java.util.*;

/**
 * @author li.bowei
 */
@Component
public class JwtTokenUtils {

    private static ShiroProperties properties;
    private static SecretKey SECRET_KEY;
//
//    public static final String ROLES_KEY = "roles";
//    public static final String PERMISSIONS_KEY = "permissions";

    @Autowired
    public void setProperties(ShiroProperties properties) {
        JwtTokenUtils.properties = properties;
    }

    @PostConstruct
    public void init(){
        byte[] bytes = DatatypeConverter.parseBase64Binary(properties.getJwt().getSecret());
        SECRET_KEY = Keys.hmacShaKeyFor(bytes);
    }

    public static String sign(String username,
                                     Long id,
                                     boolean isRememberMe) {
        ShiroProperties.JwtProperties jwt = properties.getJwt();
        long expiration = isRememberMe ? jwt.getExpirationRemember() : jwt.getExpiration();
        final Date createdDate = new Date();
        final Date expirationDate = new Date(createdDate.getTime() + expiration * 1000);
        String tokenPrefix = Jwts.builder()
                .setHeaderParam("type", "jwt")
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
//                .claim(ROLES_KEY, String.join(",", roles))
//                .claim(PERMISSIONS_KEY, String.join(",", permissions))
                .setId(String.valueOf(id))
                .setIssuer("SnailClimb")
                .setIssuedAt(createdDate)
                .setSubject(username)
                .setExpiration(expirationDate)
                .compact();
        return JwtConstant.PREFIX + " " + tokenPrefix; // 添加 token 前缀 "Bearer ";
    }

    public static Long getId(String token) {
        return Optional.of(token)
                .map(JwtTokenUtils::getClaimsProperties)
                .map(Claims::getId)
                .map(Long::valueOf)
                .orElseThrow(IllegalArgumentException::new);
    }

    public static String getName(String token){
        return getClaimsProperties(token).getSubject();
    }

//    public static List<String> getRoles(String token){
//        return getClaimsProperties(token, ROLES_KEY);
//    }
//
//    public static List<String> getPermissions(String token){
//        return getClaimsProperties(token, PERMISSIONS_KEY);
//    }
//
//    private static List<String> getClaimsProperties(String token, String key){
//        return Optional.of(token)
//                .map(JwtTokenUtils::getClaimsProperties)
//                .map(var1 -> var1.get(key))
//                .map(Objects::toString)
//                .map(var1 -> var1.split(","))
//                .map(Arrays::asList)
//                .orElseGet(Collections::emptyList);
//    }

    private static Claims getClaimsProperties(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

}
