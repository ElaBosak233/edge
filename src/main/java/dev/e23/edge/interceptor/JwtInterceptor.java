package dev.e23.edge.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.e23.edge.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.HashMap;
import java.util.Map;

public class JwtInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map<String, Object> map = new HashMap<>();
        String token = request.getHeader("Authorization");

        try {
            JwtUtil.verify(token);
            return true;
        } catch (Exception e) {
            map.put("msg", "token is invalid");
        }

        map.put("code", HttpStatus.UNAUTHORIZED.value());

        String json = new ObjectMapper().writeValueAsString(map);
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write(json);

        return false;
    }
}
