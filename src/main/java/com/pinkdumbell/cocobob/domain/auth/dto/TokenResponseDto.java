package com.pinkdumbell.cocobob.domain.auth.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenResponseDto {

    private String accessToken;
    private String refreshToken;
}