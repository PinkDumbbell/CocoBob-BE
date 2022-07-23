package com.pinkdumbell.cocobob.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않은 계정입니다."),
    BREED_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 견종입니다."),
    INVALID_PASSWORD(HttpStatus.FORBIDDEN, "비밀번호가 일치하지 않습니다."),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED,"유효하지 않은 토큰입니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "BAD REQUEST"),
    NOT_IMAGE(HttpStatus.BAD_REQUEST, "올바른 이미지 파일이 아닙니다."),
    DUPLICATED_EMAIL(HttpStatus.CONFLICT, "해당 이메일을 가진 사용자가 존재합니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL SERVER ERROR"),
    FAIL_TO_RESIZE_IMAGE(HttpStatus.INTERNAL_SERVER_ERROR, "이미지 리사이징을 실패했습니다."),
    FAIL_TO_UPLOAD_IMAGE(HttpStatus.INTERNAL_SERVER_ERROR, "s3 버킷에 이미지 업로드를 실패했습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
