package com.pinkdumbell.cocobob.domain.daily.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class DailyRecordRegisterResponseDto {

    private final Long dailyId;

}
