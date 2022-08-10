package com.pinkdumbell.cocobob.domain.daily.dto;

import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyRecordRegisterRequestDto {

    @ApiModelProperty(notes = "데일리 기록 날짜", example = "2022-08-15")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "필수 입력 항목(날짜)가 없습니다.")
    private LocalDate date;
    @ApiModelProperty(notes = "업로드할 이미지들", example = "1.png,2.png...")
    private List<MultipartFile> images;
    @ApiModelProperty(notes = "데일리 기록", example = "행복한 코코의 하루")
    private String note;
    @ApiModelProperty(notes = "급여 사료량", example = "520")
    private Integer feedAmount;
    @ApiModelProperty(notes = "총 산책 시간", example = "3")
    private Integer walkTotalTime;
    @ApiModelProperty(notes = "산책 거리", example = "1.5")
    private Float walkDistance;
    @ApiModelProperty(notes = "산책 GPS", example = "[...]")
    private String walkGps;

}
