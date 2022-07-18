package com.pinkdumbell.cocobob.domain.user.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserLoginRequestDto {

    @NotBlank(message = "사용자 이름이 필요합니다.")
    private String username;
    @NotBlank(message = "이메일이 필요합니다.")
    @Email(message = "잘못된 이메일 입니다.")
    private String email;
    @NotBlank(message = "비밀번호가 필요합니다.")
    private String password;

}