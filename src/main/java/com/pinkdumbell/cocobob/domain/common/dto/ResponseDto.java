package com.pinkdumbell.cocobob.domain.common.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ResponseDto {

    private int status;
    private String code;
    private String message;

}
