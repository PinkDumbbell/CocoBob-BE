package com.pinkdumbell.cocobob.domain.daily.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class DailyRecordRegisterResponseDto {

    @ApiModelProperty(notes = "daily id", example = "생성된 데일리 id")
    private final Long dailyId;
}
