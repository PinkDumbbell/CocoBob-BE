package com.pinkdumbell.cocobob.domain.user.dto;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPasswordRequestDto {
    private String email;
    private String password;
}
