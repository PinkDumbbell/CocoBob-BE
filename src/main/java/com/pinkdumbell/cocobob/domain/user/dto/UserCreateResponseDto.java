package com.pinkdumbell.cocobob.domain.user.dto;

import com.pinkdumbell.cocobob.domain.user.User;
import lombok.Getter;

@Getter
public class UserCreateResponseDto {
    private final Long userId;
    private final String email;
    private final String username;
    public UserCreateResponseDto(User entity) {
        this.userId = entity.getId();
        this.email = entity.getEmail();
        this.username = entity.getUsername();
    }
}
