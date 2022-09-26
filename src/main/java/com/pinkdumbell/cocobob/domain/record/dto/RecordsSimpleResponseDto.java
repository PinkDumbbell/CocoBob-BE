package com.pinkdumbell.cocobob.domain.record.dto;

import lombok.Getter;

@Getter
public class RecordsSimpleResponseDto {
    private final Integer walkTotalTime;
    private final Double walkTotalDistance;
    private final Integer mealCount;
    private final Boolean isAbnormal;
    private final String dailyTitle;
    private final Long healthRecordId;
    private final Long dailyId;

    public RecordsSimpleResponseDto(
            Integer walkTotalTime,
            Double walkTotalDistance,
            Integer mealCount,
            Boolean isAbnormal,
            String dailyTitle,
            Long healthRecordId,
            Long dailyId
    ) {
        this.walkTotalTime = walkTotalTime;
        this.walkTotalDistance = walkTotalDistance;
        this.mealCount = mealCount;
        this.isAbnormal = isAbnormal;
        this.dailyTitle = dailyTitle;
        this.healthRecordId = healthRecordId;
        this.dailyId = dailyId;
    }
}
