package com.pinkdumbell.cocobob.config;

import com.pinkdumbell.cocobob.domain.auth.JwtTokenProvider;
import com.pinkdumbell.cocobob.domain.auth.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .httpBasic().disable() // rest api이므로 기본설정 미사용
            .csrf().disable() // rest api이므로 csrf 보안 미사용
            .formLogin().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // jwt로 인증하므로 세션 미사용
            .and()
            .authorizeRequests()
            .antMatchers("/v1/users/**").permitAll()
            .antMatchers("/social/**").permitAll()
            .antMatchers("/exception/**").permitAll()
            .antMatchers("/admin").hasRole("ADMIN")
            .antMatchers("/manager").hasRole("USER")
            .anyRequest().authenticated()
            .and()
//            .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint())
//            .and()
//            .exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler())
//            .and()
            .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class); // jwt 필터 추가
    }
}
