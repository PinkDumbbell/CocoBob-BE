package com.pinkdumbell.cocobob.common.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Builder
@Getter
@AllArgsConstructor
public class CommonResponseDto<T> {
    @ApiModelProperty(notes = "요청 status", example = "200")
    private final int status;
    @ApiModelProperty(notes = "요청 결과", example = "SUCCESS_CODE | ERROR_CODE")
    private final String code;
    @ApiModelProperty(notes = "요청 메시지", example = "요청을 정상 처리 하였습니다.")
    private final String message;
    @ApiModelProperty(notes = "요청 data", example = "{} | []")
    private final T data;


}
