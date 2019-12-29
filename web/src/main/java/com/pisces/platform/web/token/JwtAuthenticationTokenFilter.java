package com.pisces.platform.web.token;

import com.pisces.platform.core.utils.AppUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 从Request Header中读取Bearer Token并验证，
 * 如验证成功则将用户信息保存在SecurityContext中，用户则可以访问受限资源了。
 * 在每次请求结束后，SecurityContext会自动清空。
 *
 * @author Janson
 */
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    private String tokenHeader = "Authorization";
    private String tokenHead = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        //先从url中取token
        String authToken = request.getParameter("token");
        String authHeader = request.getHeader(this.tokenHeader);
        if (!StringUtils.isEmpty(authHeader) && authHeader.startsWith(tokenHead)) {
            //如果header中存在token，则覆盖掉url中的token
            authToken = authHeader.substring(tokenHead.length()); // "Bearer "之后的内容
        }

        if (!StringUtils.isEmpty(authToken)) {
            String username = JwtTokenHelper.getUsernameFromToken(authToken);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                //检查token是否有效
                if (!JwtTokenHelper.isTokenExpired(authToken) && AppUtils.switchUser(username)) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null, JwtTokenHelper.getAuthorities(authToken));
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    //设置用户登录状态
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        filterChain.doFilter(request, response);
    }

}
