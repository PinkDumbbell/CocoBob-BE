package com.pinkdumbell.cocobob.domain.record.daily.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class DailyCreateRequestDto {
    @ApiModelProperty(notes = "데일리 기록 날짜", example = "2000-01-01", required = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private LocalDate date;
    @ApiModelProperty(notes = "데일리 제목", example = "오늘의 일기")
    @NotBlank(message = "제목을 작성해주세요.")
    private String title;
    @ApiModelProperty(notes = "텍스트 기록", example = "오늘 우리 코코는 ...")
    private String note;
    @ApiModelProperty(notes = "사진")
    private List<MultipartFile> images;
}
