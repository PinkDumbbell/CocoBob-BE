package com.pinkdumbell.cocobob.domain.user.dto;

import lombok.*;

@Getter
@RequiredArgsConstructor
@Builder
public class UserPasswordRequestDto {
    private final String password;
}
