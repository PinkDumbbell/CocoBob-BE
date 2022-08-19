package com.pinkdumbell.cocobob.domain.daily.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class TempDailyRequestDto {
    private String data;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
}
