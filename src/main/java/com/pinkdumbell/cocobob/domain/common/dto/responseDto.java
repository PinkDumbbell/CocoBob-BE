package com.pinkdumbell.cocobob.domain.common.dto;

import lombok.Builder;

@Builder
public class responseDto {

    private int status;
    private String code;
    private String message;

}
