package com.pinkdumbell.cocobob.common.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Builder
@Getter
public class CommonResponseDto<T> {

    private final int status;
    private final String code;
    private final String message;
    private final T data;


}
