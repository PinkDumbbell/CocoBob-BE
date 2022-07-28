package com.pinkdumbell.cocobob.domain.auth.filter;

import com.pinkdumbell.cocobob.domain.auth.JwtTokenProvider;
import com.pinkdumbell.cocobob.exception.CustomException;
import io.jsonwebtoken.JwtException;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.GenericFilter;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException, JwtException {
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
        try {
            String TOKEN_PREFIX = "Bearer ";
            if (token != null && jwtTokenProvider.validateTokenExpiration(token.replace(TOKEN_PREFIX, ""))) {
                Authentication auth = jwtTokenProvider.getAuthentication(token.replace(TOKEN_PREFIX, ""));
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (JwtException e) {
            throw e;
        }

        chain.doFilter(request, response);
    }
}
