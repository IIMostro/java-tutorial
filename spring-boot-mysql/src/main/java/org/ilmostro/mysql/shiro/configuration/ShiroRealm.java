package org.ilmostro.mysql.shiro.configuration;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.ilmostro.mysql.shiro.entity.JWTToken;
import org.ilmostro.mysql.shiro.utils.JwtTokenUtils;

import org.springframework.stereotype.Component;

/**
 * @author li.bowei
 **/
@Component
public class ShiroRealm extends AuthorizingRealm {

//    private final StringRedisTemplate stringRedisTemplate;
//
//    public ShiroRealm(StringRedisTemplate stringRedisTemplate) {
//        this.stringRedisTemplate = stringRedisTemplate;
//    }

    /**
     * 大坑！，必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String token = principals.toString();
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        Long id = JwtTokenUtils.getId(token);
        List<String> roles = Optional.ofNullable(id)
                .map(var1 -> ShiroCacheConstant.ROLE_CACHE_KEY + id)
//                .map(stringRedisTemplate.opsForValue()::get)
//                .map(var1 -> var1.split(","))
                .map(Arrays::asList)
                .orElseGet(Collections::emptyList);
        simpleAuthorizationInfo.addRoles(roles);
        List<String> permissions = Optional.ofNullable(id)
                .map(var1 -> ShiroCacheConstant.PERMISSIONS_CACHE_KEY + id)
//                .map(stringRedisTemplate.opsForValue()::get)
//                .map(var1 -> var1.split(","))
                .map(Arrays::asList)
                .orElseGet(Collections::emptyList);
        simpleAuthorizationInfo.addStringPermissions(permissions);
        return simpleAuthorizationInfo;
    }

    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        String token = (String) auth.getCredentials();
        // 解密获得username，用于和数据库进行对比
        String username = JwtTokenUtils.getName(token);
        if (username == null) {
            throw new AuthenticationException("token invalid");
        }

        Long id = JwtTokenUtils.getId(token);
//        String signToken = stringRedisTemplate.opsForValue().get(ShiroCacheConstant.TOKEN_CACHE_KEY + id);
        String signToken = "";
        signToken = StringUtils.replace(signToken,JwtConstant.PREFIX, "");
        signToken = StringUtils.replace(signToken, " ", "");
        if (StringUtils.isBlank(signToken) || !StringUtils.equals(signToken, token)) {
            throw new AuthenticationException("Username or password error");
        }

        return new SimpleAuthenticationInfo(username, token, "my_realm");
    }
}
