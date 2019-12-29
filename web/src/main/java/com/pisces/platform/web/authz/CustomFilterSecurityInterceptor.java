package com.pisces.platform.web.authz;

import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

public class CustomFilterSecurityInterceptor extends FilterSecurityInterceptor {

    public CustomFilterSecurityInterceptor() {
        setSecurityMetadataSource(new CustomInvocationSecurityMetadataSource());
        setAccessDecisionManager(new CustomAccessDecisionManager());
    }
}
