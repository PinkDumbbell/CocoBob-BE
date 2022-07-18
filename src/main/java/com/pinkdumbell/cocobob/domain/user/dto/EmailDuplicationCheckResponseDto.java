package com.pinkdumbell.cocobob.domain.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class EmailDuplicationCheckResponseDto {

    @ApiModelProperty(notes = "이메일 중복 확인 결과에 대한 메시지")
    private final String message;
}
