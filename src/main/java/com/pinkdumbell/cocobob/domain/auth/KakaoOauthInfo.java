package com.pinkdumbell.cocobob.domain.auth;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class KakaoOauthInfo {
    @Value("${kakao.url.login}")
    private String kakaoLoginUrl;
    @Value("${kakao.url.token}")
    private String kakaoTokenUrl;
    @Value("${kakao.url.profile}")
    private String kakaoProfileUrl;
    @Value("${kakao.client_id}")
    private String kakaoClientId;
    @Value("${kakao.redirect}")
    private String kakaoRedirectUrl;
}
