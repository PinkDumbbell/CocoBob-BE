package com.pinkdumbell.cocobob.domain.record.daily.dto;

import com.pinkdumbell.cocobob.domain.record.daily.Daily;
import lombok.Getter;

@Getter
public class DailyCreateResponseDto {
    private final Long dailyId;

    public DailyCreateResponseDto(Daily entity) {
        dailyId = entity.getId();
    }
}
