package com.pinkdumbell.cocobob.domain.daily.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@Builder
public class DailyRecordUpdateResponseDto {
    private final Long dailyId;
}
