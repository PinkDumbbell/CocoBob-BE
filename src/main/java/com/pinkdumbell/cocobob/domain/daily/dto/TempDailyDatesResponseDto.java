package com.pinkdumbell.cocobob.domain.daily.dto;

import com.pinkdumbell.cocobob.domain.daily.Daily;
import lombok.Getter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class TempDailyDatesResponseDto {
    List<String> dates;

    public TempDailyDatesResponseDto(List<Daily> entities) {
        dates = entities.stream().map((entity) -> entity.getDate().toString())
                .collect(Collectors.toList());
    }
}
