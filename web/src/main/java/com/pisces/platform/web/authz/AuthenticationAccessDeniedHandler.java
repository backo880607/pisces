package com.pisces.platform.web.authz;

import com.pisces.platform.core.locale.LocaleManager;
import com.pisces.platform.core.utils.EntityUtils;
import com.pisces.platform.web.config.WebMessage;
import com.pisces.platform.web.controller.ResponseData;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class AuthenticationAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "application/json");

        ResponseData data = new ResponseData();
        data.setSuccess(false);
        data.setStatus(WebMessage.NOT_ACCESS.ordinal());
        data.setName(WebMessage.NOT_ACCESS.name());
        data.setMessage(LocaleManager.getLanguage(WebMessage.NOT_ACCESS, accessDeniedException.getMessage()));
        PrintWriter out = response.getWriter();
        out.write(EntityUtils.defaultObjectMapper().writeValueAsString(data));
        out.flush();
        out.close();
    }

}
