package com.pinkdumbell.cocobob.domain.daily.dto;

import io.swagger.annotations.ApiModelProperty;
import java.time.YearMonth;
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

    @ApiModelProperty(notes = "찾고 싶은 연월", example = "2022-08")
    @DateTimeFormat(pattern = "yyyy-MM")
    @NotNull(message = "필수 입력 항목(년 월)이 없습니다.")
    private YearMonth yearMonth;
}
