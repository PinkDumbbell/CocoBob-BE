package com.pinkdumbell.cocobob.domain.auth;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class AppleOauthInfo {
    @Value("${apple.auth.url}")
    private String appleAuthUrl;
    @Value("${apple.client.id}")
    private String clientId;
    @Value("${apple.redirect.uri}")
    private String redirectUri;
    @Value("${apple.team.id}")
    private String teamId;
    @Value("${apple.key.id}")
    private String keyId;
    @Value("${apple.key.path}")
    private String keyPath;
}
