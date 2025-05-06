package com.edwin.emsp.global;

import com.edwin.emsp.domain.model.message.Message;
import com.edwin.emsp.domain.model.message.RstStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Author: jiucheng
 * @Description: CustomAccessDeniedHandler
 * @Date: 2025/4/19
 */
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        String json = new ObjectMapper().writeValueAsString(Message.error(RstStatus.forbidden));
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(json);
    }
}
