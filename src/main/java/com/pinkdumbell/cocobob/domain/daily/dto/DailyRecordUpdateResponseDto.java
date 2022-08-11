package com.pinkdumbell.cocobob.domain.daily.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Getter
@AllArgsConstructor
@Builder
public class DailyRecordUpdateResponseDto {

    @ApiModelProperty(notes = "daily id", example = "수정된 데일리 기록 id")
    private final Long dailyId;
}
