package com.pinkdumbell.cocobob.domain.pet.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PetCreateRequestAgeDto {
    @ApiModelProperty(notes = "반려동물 나이(개월 수)", example = "25", required = true)
//    @NotNull(message = "반려동물의 나이(개월 수)가 필요합니다.")
    private Integer months;
    @ApiModelProperty(notes = "반려동물 생일", example = "1999-12-31", required = false)
    @PastOrPresent(message = "생일은 과거 또는 현재의 날짜여야 합니다.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;
}
