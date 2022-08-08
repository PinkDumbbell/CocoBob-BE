package com.pinkdumbell.cocobob.domain.daily.dto;

import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DailyNoteGetRequestDto {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotBlank(message = "필수 입력 항목(시작 날짜)가 없습니다.")
    private LocalDate startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotBlank(message = "필수 입력 항목(종료 날짜)가 없습니다.")
    private LocalDate lastDate;
}
