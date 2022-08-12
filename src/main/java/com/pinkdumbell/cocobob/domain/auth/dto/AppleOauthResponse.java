package com.pinkdumbell.cocobob.domain.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppleOauthResponse {
    private String access_token;
    private String token_type;
    private Long expires_in;
    private String refresh_token;
    private String id_token;
}
