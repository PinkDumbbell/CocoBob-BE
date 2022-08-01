package com.pinkdumbell.cocobob.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Getter
public class LoginUserInfo {
    private final String email;
}
