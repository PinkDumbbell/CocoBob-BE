package com.pinkdumbell.cocobob.domain.record.daily.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pinkdumbell.cocobob.domain.record.daily.Daily;
import com.pinkdumbell.cocobob.domain.record.daily.image.DailyImage;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class DailyDetailResponseDto {
    private final Long dailyId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDate date;
    private final String note;
    private final List<DailyImageResponseDto> images;

    public DailyDetailResponseDto(Daily entity, List<DailyImage> dailyImages) {
        this.dailyId = entity.getId();
        this.date = entity.getDate();
        this.note = entity.getNote();
        this.images = dailyImages.stream().map(DailyImageResponseDto::new).collect(Collectors.toList());
    }

    @Getter
    public static class DailyImageResponseDto {
        private final Long imageId;
        private final String path;

        public DailyImageResponseDto(DailyImage entity) {
            this.imageId = entity.getId();
            this.path = entity.getPath();
        }
    }
}
