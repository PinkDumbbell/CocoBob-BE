package com.pinkdumbell.cocobob.domain.daily.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class TempDailyRequestDto {
    @ApiModelProperty(notes = "데일리 기록 JSON", example = "{ ... }")
    private String data;
    @ApiModelProperty(notes = "데일리 기록 날짜(생성 시 에만 필수, 수정 시에는 선택", example = "2022-08-18")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
}
