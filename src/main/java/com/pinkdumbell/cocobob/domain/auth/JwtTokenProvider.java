package com.pinkdumbell.cocobob.domain.auth;

import com.pinkdumbell.cocobob.domain.user.User;
import com.pinkdumbell.cocobob.domain.user.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import java.util.Base64;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MissingRequestHeaderException;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final UserDetailService userDetailService;
    private final UserRepository userRepository;
    @Value("${spring.jwt.secretKey}")
    private String secretKey;

    @Value("${spring.jwt.accessTokenValidTime}")
    private long accessTokenValidTime;

    @Value("${spring.jwt.refreshTokenValidTime}")
    private long refreshTokenValidTime;


    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(String email) {
        Claims claims = Jwts.claims().setSubject(email);
        Date now = new Date();

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(new Date(now.getTime() + accessTokenValidTime))
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact();
    }

    public String createRefreshToken() {
        Date now = new Date();

        return Jwts.builder()
            .setIssuedAt(now)
            .setExpiration(new Date(now.getTime() + refreshTokenValidTime))
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailService.loadUserByUsername(getUserEmail(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "",
            userDetails.getAuthorities());
    }

    public String getUserEmail(String token) {
        try {
            String userEmail = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                .getBody()
                .getSubject();

            User user = userRepository.findByEmail(userEmail).orElseThrow(() -> {
                throw new JwtException("회원가입 되지 않은 유저 입니다.");
            });

            if (user.getRefreshToken() != null) {
                return userEmail; //토큰 속 이메일 claim
            } else {
                throw new JwtException("로그아웃된 계정입니다.");
            }


        } catch (ExpiredJwtException e) {
            return e.getClaims().getSubject();
        }
    }

    public String resolveToken(HttpServletRequest req) throws MissingRequestHeaderException {

        return req.getHeader("Authorization");
    }

    public boolean validateTokenExpiration(String token) throws JwtException {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (IllegalArgumentException e) {
            throw new JwtException("유효하지 않은 토큰");
        } catch (ExpiredJwtException e) {
            throw new JwtException("토큰 기한 만료");
        } catch (SignatureException e) {
            throw new JwtException("잘못 서명된 토큰");
        } catch (Exception e) {
            throw new JwtException("유효하지 않은 토큰");
        }


    }

}
