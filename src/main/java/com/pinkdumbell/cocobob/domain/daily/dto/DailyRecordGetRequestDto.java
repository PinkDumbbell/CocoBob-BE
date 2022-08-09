package com.pinkdumbell.cocobob.domain.daily.dto;

import java.time.LocalDate;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DailyRecordGetRequestDto {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "필수 입력 항목(시작 날짜)가 없습니다.")
    private LocalDate startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "필수 입력 항목(종료 날짜)가 없습니다.")
    private LocalDate lastDate;
}
