package com.pisces.platform.web.authz;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.access.intercept.DefaultFilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

/** */

/**
 * 最核心的地方，就是提供某个资源对应的权限定义，即getAttributes方法返回的结果。 此类在初始化时，应该取到所有资源及其对应角色的定义。
 */
public class CustomInvocationSecurityMetadataSource extends DefaultFilterInvocationSecurityMetadataSource {
    private static LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> resourceMap = new LinkedHashMap<>();

    public CustomInvocationSecurityMetadataSource() {
        super(resourceMap);
        loadResourceDefine();
    }

    private void loadResourceDefine() {
        List<ConfigAttribute> authorities = new ArrayList<>();
        authorities.add(new SecurityConfig("ROLE_ROOT"));
        AntPathRequestMatcher matcher = new AntPathRequestMatcher("/**");
        resourceMap.put(matcher, authorities);
    }
}
