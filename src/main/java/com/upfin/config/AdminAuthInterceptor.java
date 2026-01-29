package com.upfin.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AdminAuthInterceptor implements HandlerInterceptor {

    private static final String HEADER = "X-ADMIN-TOKEN";

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) throws Exception {

        String tokenEnviado = request.getHeader(HEADER);
        String tokenCorreto = System.getenv("ADMIN_TOKEN");

        if (tokenCorreto == null || tokenEnviado == null || !tokenCorreto.equals(tokenEnviado)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        return true;
    }
}