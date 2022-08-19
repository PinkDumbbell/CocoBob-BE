package com.pinkdumbell.cocobob.domain.auth.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenRequestDto {
    private String accessToken;
    private String refreshToken;
}
