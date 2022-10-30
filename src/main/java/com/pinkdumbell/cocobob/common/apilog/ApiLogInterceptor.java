package com.pinkdumbell.cocobob.common.apilog;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ApiLogInterceptor implements HandlerInterceptor {

    private final ApiLogRepository apiLogRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String userEmail = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            try {
                userEmail = ((UserDetails) authentication.getPrincipal()).getUsername();
            } catch (Exception e) {
                userEmail = null;
            }
        }
        apiLogRepository.save(ApiLog.builder()
                        .userEmail(userEmail)
                        .method(request.getMethod())
                        .handlerName(handler.toString())
                        .requestUrl(String.valueOf(request.getRequestURL()))
                        .time(LocalDateTime.now())
                        .queryString(request.getQueryString())
                        .userAgent(request.getHeader("user-agent"))
                .build());
        return true;
    }

}
