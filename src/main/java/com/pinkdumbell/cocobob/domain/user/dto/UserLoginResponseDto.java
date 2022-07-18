package com.pinkdumbell.cocobob.domain.user.dto;

import com.pinkdumbell.cocobob.domain.user.User;
import lombok.Getter;

@Getter
public class UserLoginResponseDto {
    private final Long userId;
    private final String email;
    private final String username;
    private final String role;
    private final String accessToken;

    public UserLoginResponseDto(User entity,String Token) {
        this.userId = entity.getId();
        this.email = entity.getEmail();
        this.username = entity.getUsername();
        this.role = entity.getRole().toString();
        this.accessToken = Token;
    }
}
