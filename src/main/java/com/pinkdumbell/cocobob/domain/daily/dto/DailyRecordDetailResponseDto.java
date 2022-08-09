package com.pinkdumbell.cocobob.domain.daily.dto;

import com.pinkdumbell.cocobob.domain.daily.Daily;
import com.pinkdumbell.cocobob.domain.daily.image.DailyImage;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
public class DailyRecordDetailResponseDto {

    private final Long id;

    private final String date;

    private final Integer feedAmount;

    private final Integer walkTotalTime;

    private final Float walkDistance;

    private final String walkGps;

    private final String note;

    private List<DailyImageInfo> images = new ArrayList<>();

    @Data
    private static class DailyImageInfo {

        private String imagePath;
        private Long dailyImageId;

        public DailyImageInfo(DailyImage dailyImage) {

            this.imagePath = dailyImage.getPath();
            this.dailyImageId = dailyImage.getId();
        }
    }

    public DailyRecordDetailResponseDto(Daily daily, List<DailyImage> dailyImages) {
        this.id = daily.getId();
        this.date = daily.getDate().toString();
        this.feedAmount = daily.getFeedAmount();
        this.walkTotalTime = daily.getWalkTotalTime();
        this.walkDistance = daily.getWalkDistance();
        this.walkGps = daily.getWalkGps();
        this.note = daily.getNote();

        dailyImages.forEach((dailyImage) -> this.images.add(new DailyImageInfo(dailyImage)));

    }

}
