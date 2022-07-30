package com.pinkdumbell.cocobob.common.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ResponseDto {

    private int status;
    private String code;
    private String message;

}
