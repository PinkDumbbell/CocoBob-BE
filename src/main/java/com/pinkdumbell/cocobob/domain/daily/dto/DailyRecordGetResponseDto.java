package com.pinkdumbell.cocobob.domain.daily.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pinkdumbell.cocobob.domain.daily.Daily;
import com.pinkdumbell.cocobob.domain.daily.image.DailyImage;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DailyRecordGetResponseDto {

    @ApiModelProperty(notes = "daily Id", example = "1")
    private final Long id;
    @ApiModelProperty(notes = "기록 날짜", example = "2022-08-15")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDate date;
    @ApiModelProperty(notes = "급여 사료량", example = "530")
    private final Integer feedAmount;
    @ApiModelProperty(notes = "총 산책시간", example = "3")
    private final Integer walkTotalTime;
    @ApiModelProperty(notes = "산책 거리", example = "2.5")
    private final Float walkDistance;
    @ApiModelProperty(notes = "산책 GPS", example = "[...]")
    private final String walkGps;
    @ApiModelProperty(notes = "데일리 기록", example = "코코가 오늘은 밥을 맛있게 먹어 기특했다.")
    private final String note;
    @ApiModelProperty(notes = "이미지들", example = "{}")
    private final List<DailyImageInfo> images = new ArrayList<>();

    @Getter
    private static class DailyImageInfo {
        @ApiModelProperty(notes = "이미지 경로", example = "이미지 URL")
        private final String imagePath;
        @ApiModelProperty(notes = "daily image id", example = "데일리 이미지 Id")
        private final Long dailyImageId;

        public DailyImageInfo(DailyImage dailyImage) {

            this.imagePath = dailyImage.getPath();
            this.dailyImageId = dailyImage.getId();
        }
    }

    public DailyRecordGetResponseDto(Daily daily, List<DailyImage> dailyImages) {
        this.id = daily.getId();
        this.date = daily.getDate();
        this.feedAmount = daily.getFeedAmount();
        this.walkTotalTime = daily.getWalkTotalTime();
        this.walkDistance = daily.getWalkDistance();
        this.walkGps = daily.getWalkGps();
        this.note = daily.getNote();

        dailyImages.forEach((dailyImage) -> this.images.add(new DailyImageInfo(dailyImage)));

    }


}
