package com.pinkdumbell.cocobob.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;


@AllArgsConstructor
@Getter
public enum ErrorCode {
    DUPLICATED_EMAIL(BAD_REQUEST, "해당 이메일을 가진 사용자가 존재합니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL SERVER ERROR")
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
