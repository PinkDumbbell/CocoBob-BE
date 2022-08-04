package com.pinkdumbell.cocobob.domain.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginRequestDto {
    @ApiModelProperty(notes = "사용자 이메일", example = "test@test.com", required = true)
    private String email;
    @ApiModelProperty(notes = "사용자 비밀번호", example = "password", required = true)
    private String password;

}
