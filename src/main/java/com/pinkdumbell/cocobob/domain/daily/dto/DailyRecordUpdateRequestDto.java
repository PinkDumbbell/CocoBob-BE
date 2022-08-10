package com.pinkdumbell.cocobob.domain.daily.dto;

import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyRecordUpdateRequestDto {
    @ApiModelProperty(notes = "추가 업로드할 이미지", example = "1.png, 2.png...")
    private List<MultipartFile> images;
    @ApiModelProperty(notes = "데일리 기록", example = "건강하개 오늘은 맑음이냥~")
    private String note;
    @ApiModelProperty(notes = "급여 사료량", example = "430")
    private Integer feedAmount;
    @ApiModelProperty(notes = "총 산책시간", example = "3")
    private Integer walkTotalTime;
    @ApiModelProperty(notes = "산책 거리", example = "2.5")
    private Float walkDistance;
    @ApiModelProperty(notes = "산책 GPS", example = "[...]")
    private String walkGps;
}
