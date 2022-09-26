package com.pinkdumbell.cocobob.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 계정입니다."),
    BREED_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 견종입니다."),
    PET_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 반려동물이 존재하지 않습니다."),
    HEALTH_RECORD_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 건강기록이 존재하지 않습니다."),
    MEAL_RECORD_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 식사기록이 존재하지 않습니다."),
    WALK_RECORD_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 산책 기록을 찾을 수 없습니다."),
    INVALID_PASSWORD(HttpStatus.FORBIDDEN, "비밀번호가 일치하지 않습니다."),
    INVALID_REFRESH_TOKEN(HttpStatus.LOCKED, "유효하지 않은 Refresh 토큰입니다."),
    EXPIRED_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "기한이 만료된 Refresh 토큰입니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    NOT_IMAGE(HttpStatus.BAD_REQUEST, "올바른 이미지 파일이 아닙니다."),
    DUPLICATED_EMAIL(HttpStatus.CONFLICT, "해당 이메일을 가진 사용자가 존재합니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL SERVER ERROR"),
    FAIL_TO_RESIZE_IMAGE(HttpStatus.INTERNAL_SERVER_ERROR, "이미지 리사이징을 실패했습니다."),
    FAIL_TO_UPLOAD_IMAGE(HttpStatus.INTERNAL_SERVER_ERROR, "s3 버킷에 이미지 업로드를 실패했습니다."),
    FAIL_TO_DELETE_IMAGE(HttpStatus.INTERNAL_SERVER_ERROR, "s3 버킷에서 이미지 삭제를 실패했습니다."),
    INVALID_LOGOUT_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 로그아웃 요청입니다."),
    INVALID_ACCESS_AFTER_LOGOUT(HttpStatus.BAD_REQUEST, "로그아웃된 유저 입니다."),
    PRODUCT_NOT_FOUND(HttpStatus.BAD_REQUEST, "없는 상품 입니다."),
    ALREADY_LIKED(HttpStatus.BAD_REQUEST, "이미 좋아요한 상품 입니다."),
    LIKE_NOT_FOUND(HttpStatus.BAD_REQUEST, "좋아요 한적 없는 상품입니다."),
    DAILY_NOT_FOUND(HttpStatus.BAD_REQUEST, "존재 하지 않는 데일리 기록입니다."),
    DAILY_IMAGE_NOT_FOUND(HttpStatus.BAD_REQUEST, "존재 하지 않는 데일리 이미지입니다."),
    FAIL_TO_DELETE_REPRESENTATIVE_PET(HttpStatus.BAD_REQUEST, "대표반려동물은 삭제할 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
