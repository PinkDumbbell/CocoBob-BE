package com.pinkdumbell.cocobob.domain.daily.dto;

import java.time.YearMonth;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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

    @DateTimeFormat(pattern = "yyyy-MM")
    @NotNull(message = "필수 입력 항목(년 월)이 없습니다.")
    private YearMonth yearMonth;

}
