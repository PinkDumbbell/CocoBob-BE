package com.pinkdumbell.cocobob.domain.daily.dto;

import java.time.LocalDate;
import java.time.YearMonth;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DailySimpleRequestDto {

    @NotBlank(message = "필수 입력 항목(년 월)이 없습니다.")
    @DateTimeFormat(pattern = "yyyy-MM")
    private YearMonth yearMonth;

}
