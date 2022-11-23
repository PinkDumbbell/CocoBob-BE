package com.pinkdumbell.cocobob.exception;

import com.pinkdumbell.cocobob.common.SlackService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final SlackService slackService;

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        log.error(e.getMessage(), e);
        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        List<String> messages = new ArrayList<>();
        e.getBindingResult().getAllErrors().forEach(error -> messages.add(error.getDefaultMessage()));
        return ErrorResponse.toResponseEntity(ErrorCode.BAD_REQUEST, messages);
    }

    @ExceptionHandler(BindException.class)
    protected ResponseEntity<ErrorResponse> handleBindException(BindException e) {
        log.error(e.getMessage(), e);
        List<String> messages = new ArrayList<>();
        e.getBindingResult().getAllErrors().forEach(error -> messages.add(error.getDefaultMessage()));
        return ErrorResponse.toResponseEntity(ErrorCode.BAD_REQUEST, messages);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error(e.getMessage(), e);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(e.getClass()).append(":").append(e.getMessage()).append("\n");
        Arrays.stream(e.getStackTrace()).iterator().forEachRemaining(msg -> stringBuilder.append(msg).append("\n"));
        slackService.postSlackMessage(String.valueOf(stringBuilder));
        return ErrorResponse.toResponseEntity(ErrorCode.INTERNAL_SERVER_ERROR);
    }
}
