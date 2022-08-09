package com.pinkdumbell.cocobob.domain.daily.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class DailyRecordRegisterResponseDto {

    private final Long dailyId;

}
