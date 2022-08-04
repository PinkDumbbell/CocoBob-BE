package com.pinkdumbell.cocobob.common.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class EmailSendResultDto {
    @ApiModelProperty(notes = "요청 status", example = "2xx | 4xx | 5xx")
    private int status;
}
