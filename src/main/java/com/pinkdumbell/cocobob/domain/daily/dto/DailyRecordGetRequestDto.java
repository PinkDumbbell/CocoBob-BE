package com.pinkdumbell.cocobob.domain.daily.dto;

import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
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
public class DailyRecordGetRequestDto {

    @ApiModelProperty(notes = "시작 날짜", example = "2022-01-01")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "필수 입력 항목(시작 날짜)가 없습니다.")
    private LocalDate startDate;

    @ApiModelProperty(notes = "종료 날짜", example = "2022-12-31")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "필수 입력 항목(종료 날짜)가 없습니다.")
    private LocalDate lastDate;
}
