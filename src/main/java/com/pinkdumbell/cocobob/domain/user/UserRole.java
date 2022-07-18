package com.pinkdumbell.cocobob.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole {
    USER("ROLE_USER", "user"),
    ADMIN("ROLE_ADMIN", "administrator")
    ;
    private final String key;
    private final String title;
}
