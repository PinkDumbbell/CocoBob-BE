package com.pinkdumbell.cocobob.domain.daily.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pinkdumbell.cocobob.domain.daily.Daily;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class DailySimpleResponseDto {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDate date;
    private final Long dailyId;

    public DailySimpleResponseDto(Daily daily) {

        this.date = daily.getDate();
        this.dailyId = daily.getId();
    }
}
