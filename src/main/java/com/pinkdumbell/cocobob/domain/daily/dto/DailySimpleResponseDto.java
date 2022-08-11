package com.pinkdumbell.cocobob.domain.daily.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pinkdumbell.cocobob.domain.daily.Daily;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class DailySimpleResponseDto {

    @ApiModelProperty(notes = "기록된 날짜", example = "2022-08-01")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDate date;
    @ApiModelProperty(notes = "daily id", example = "데일리 기록 id")
    private final Long dailyId;

    public DailySimpleResponseDto(Daily daily) {

        this.date = daily.getDate();
        this.dailyId = daily.getId();
    }
}
