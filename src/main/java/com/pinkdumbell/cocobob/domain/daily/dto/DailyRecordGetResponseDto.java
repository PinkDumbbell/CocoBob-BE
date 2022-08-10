package com.pinkdumbell.cocobob.domain.daily.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pinkdumbell.cocobob.domain.daily.Daily;
import com.pinkdumbell.cocobob.domain.daily.image.DailyImage;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DailyRecordGetResponseDto {

    private final Long id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDate date;

    private final Integer feedAmount;

    private final Integer walkTotalTime;

    private final Float walkDistance;

    private final String walkGps;

    private final String note;

    private final List<DailyImageInfo> images = new ArrayList<>();

    @Getter
    private static class DailyImageInfo {

        private final String imagePath;
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
