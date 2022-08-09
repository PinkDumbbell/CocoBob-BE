package com.pinkdumbell.cocobob.domain.daily.dto;

import com.pinkdumbell.cocobob.domain.daily.Daily;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class DailySimpleResponseDto {

    private final String date;
    private final Long dailyId;

    public DailySimpleResponseDto(Daily daily) {

        this.date = daily.getDate().toString();
        this.dailyId = daily.getId();
    }
}
