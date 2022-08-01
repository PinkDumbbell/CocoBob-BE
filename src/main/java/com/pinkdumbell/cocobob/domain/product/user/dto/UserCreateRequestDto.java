package com.pinkdumbell.cocobob.domain.product.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserCreateRequestDto {

    @ApiModelProperty(notes = "사용자 이름", example = "이호용", required = true)
    @NotBlank(message = "사용자 이름이 필요합니다.")
    private String username;
    @ApiModelProperty(notes = "사용자 이메일", example = "test@test.com", required = true)
    @NotBlank(message = "이메일이 필요합니다.")
    @Email(message = "잘못된 이메일 입니다.")
    private String email;
    @ApiModelProperty(notes = "사용자 비밀번호", example = "password", required = true)
    @NotBlank(message = "비밀번호가 필요합니다.")
    private String password;
}
