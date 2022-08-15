package com.pinkdumbell.cocobob.domain.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AppleRedirectResponse {
    private String code;
    private String user;
}
