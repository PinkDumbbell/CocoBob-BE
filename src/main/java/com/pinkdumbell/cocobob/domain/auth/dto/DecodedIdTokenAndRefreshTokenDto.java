package com.pinkdumbell.cocobob.domain.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.json.simple.JSONObject;

@Getter
@Builder
@AllArgsConstructor
public class DecodedIdTokenAndRefreshTokenDto {
    private JSONObject decodedIdToken;
    private String refreshToken;
}
