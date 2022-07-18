package com.pinkdumbell.cocobob.domain.user.dto;

import com.pinkdumbell.cocobob.domain.user.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class UserCreateResponseDto {
    @ApiModelProperty(notes = "사용자 아이디", example = "23")
    private final Long userId;
    @ApiModelProperty(notes = "사용자 이메일", example = "test@test.com")
    private final String email;
    @ApiModelProperty(notes = "사용자 이름", example = "이호용")
    private final String username;
    public UserCreateResponseDto(User entity) {
        this.userId = entity.getId();
        this.email = entity.getEmail();
        this.username = entity.getUsername();
    }
}
