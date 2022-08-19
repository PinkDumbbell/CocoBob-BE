package com.pinkdumbell.cocobob.domain.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEmailRequestDto {
    @ApiModelProperty(notes = "사용자 이메일", example = "test@test.com", required = true)
    String email;
}
