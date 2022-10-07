package com.pinkdumbell.cocobob.domain.auth.filter;

import com.pinkdumbell.cocobob.domain.auth.dto.JwtExceptionResponse;
import io.jsonwebtoken.JwtException;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtExceptionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        try {
            chain.doFilter(request, response); // go to 'JwtAuthenticationFilter'
        } catch (JwtException e) {
            setErrorResponse(HttpStatus.UNAUTHORIZED, response, e);
        }
    }

    public void setErrorResponse(HttpStatus status, HttpServletResponse response, Throwable ex)
            throws IOException {
        response.setStatus(status.value());
        response.setContentType("application/json; charset=UTF-8");

        JwtExceptionResponse jwtExceptionResponse = JwtExceptionResponse.builder().
                status(HttpStatus.UNAUTHORIZED.value()).
                httpStatus(HttpStatus.UNAUTHORIZED).
                code("INVALID ACCESS TOKEN").
                messages(ex.getMessage()).
                build();

        response.getWriter().write(jwtExceptionResponse.convertToJson());

    }
}
