package com.pinkdumbell.cocobob.domain.product.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Getter
public class LoginUserInfo {
    private final String email;
}
