package com.pisces.platform.web.config;

import com.pisces.platform.web.authc.CustomBasicAuthenticationFilter;
import com.pisces.platform.web.authz.AuthenticationAccessDeniedHandler;
import com.pisces.platform.web.authz.CustomFilterSecurityInterceptor;
import com.pisces.platform.web.authz.JwtAuthenticationEntryPoint;
import com.pisces.platform.web.token.JwtAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userService;
    @Autowired
    private AuthenticationSuccessHandler successHandler;
    @Autowired
    private AuthenticationFailureHandler failureHandler;
    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;
    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    // 没有权限处理
    @Autowired
    private AuthenticationAccessDeniedHandler accessDeniedHandler;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**", "/about", "/favicon.ico");
        web.ignoring().antMatchers("/user/Account/register");
        web.ignoring().antMatchers("/api-docs", "/swagger-resources/**", "/swagger-ui.html**", "/webjars/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable() // 使用jwt，这里不需要csrf
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).accessDeniedHandler(accessDeniedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and() // 基于token，所以不需要session
                .httpBasic().and()
                .authorizeRequests()
                //.requestMatchers(EndpointRequest.to("health", "info")).permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/**").hasRole("ROOT")
                .antMatchers("/dba/**").access("hasRole('ADMIN') and hasRole('DBA')")
                .anyRequest().authenticated().and()
                .formLogin().loginProcessingUrl("/user/login").successHandler(successHandler).failureHandler(failureHandler).and()
                .logout().logoutUrl("/logout").permitAll().logoutSuccessHandler(logoutSuccessHandler)
                .invalidateHttpSession(true)  //定义登出时是否invalidate HttpSession，默认为true
                .deleteCookies("usernameCookie", "urlCookie").and() //在登出同时清除cookies
                .rememberMe()
                .rememberMeParameter("remember-me")
                .tokenValiditySeconds(360000);

        // 禁用缓存
        http.headers().cacheControl();
        // 添加JWT filter
        http.addFilterBefore(new JwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(new CustomFilterSecurityInterceptor(), FilterSecurityInterceptor.class);
        http.addFilterAt(new CustomBasicAuthenticationFilter(authenticationManager()), BasicAuthenticationFilter.class);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
    }

	/*@Bean
	public SpringAuthenticationProvider springAuthenticationProvider() {
		return new SpringAuthenticationProvider();
	}*/
}
