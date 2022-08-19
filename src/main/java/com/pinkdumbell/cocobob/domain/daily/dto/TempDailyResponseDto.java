package com.pinkdumbell.cocobob.domain.daily.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pinkdumbell.cocobob.domain.daily.Daily;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class TempDailyResponseDto {
    private Long dailyId;
    private String data;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    public TempDailyResponseDto(Daily entity) {
        this.dailyId = entity.getId();
        this.data = entity.getData();
        this.date = entity.getDate();
    }
}
