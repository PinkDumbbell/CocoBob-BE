package com.pinkdumbell.cocobob.domain.common.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class responseDto {

    private int status;
    private String code;
    private String message;

}
