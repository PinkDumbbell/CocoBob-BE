package com.pinkdumbell.cocobob.domain.user.dto;

import com.pinkdumbell.cocobob.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserLoginResponseDto {

    private final Long userId;
    private final String email;
    private final String username;
    private final String role;
    private final String accessToken;
    private final String refreshToken;

    public UserLoginResponseDto(User entity, String  accessToken,String refreshToken) {
        this.userId = entity.getId();
        this.email = entity.getEmail();
        this.username = entity.getUsername();
        this.role = entity.getRole().toString();
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
