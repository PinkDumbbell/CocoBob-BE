package com.pinkdumbell.cocobob.domain.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class AppleOauthRequest {
    private String client_id;
    private String client_secret;
    private String code;
    private String grant_type;
    private String redirect_uri;
}
