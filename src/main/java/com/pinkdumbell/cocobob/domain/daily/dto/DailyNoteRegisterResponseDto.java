package com.pinkdumbell.cocobob.domain.daily.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailyNoteRegisterResponseDto {

    private Long dailyId;

}
