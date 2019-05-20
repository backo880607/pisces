package com.pisces.web.authc;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import com.pisces.core.utils.AppUtils;

public class CustomBasicAuthenticationFilter extends BasicAuthenticationFilter {

	public CustomBasicAuthenticationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}
	
	public CustomBasicAuthenticationFilter(AuthenticationManager authenticationManager,
			AuthenticationEntryPoint authenticationEntryPoint) {
		super(authenticationManager, authenticationEntryPoint);
	}

	@Override
	protected void onSuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			Authentication authResult) throws IOException {
		User user = (User)authResult.getPrincipal();
		if (user == null) {
			return;
		}
		String username = user.getUsername();
		if (StringUtils.isEmpty(username)) {
			return;
		}
		AppUtils.login(username);
		super.onSuccessfulAuthentication(request, response, authResult);
	}
}
