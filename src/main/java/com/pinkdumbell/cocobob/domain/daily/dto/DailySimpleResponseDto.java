package com.pinkdumbell.cocobob.domain.daily.dto;

import com.pinkdumbell.cocobob.domain.daily.Daily;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DailySimpleResponseDto {

    private String date;

    public DailySimpleResponseDto(Daily daily) {
        this.date = daily.getDate().toString();
    }
}
