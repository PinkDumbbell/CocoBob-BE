package com.pinkdumbell.cocobob.domain.walk.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pinkdumbell.cocobob.domain.walk.Walk;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class WalkDetailResponseDto {
    private final Long walkId;
    @JsonFormat(pattern = "HH:mm:ss")
    private final LocalTime startedAt;
    @JsonFormat(pattern = "HH:mm:ss")
    private final LocalTime finishedAt;
    private final String photoPath;
    private final Double distance;
    private final Integer totalTime;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDate date;
    private final Double spentKcal;

    public WalkDetailResponseDto(Walk entity) {
        this.walkId = entity.getId();
        this.startedAt = entity.getStartedAt();
        this.finishedAt = entity.getFinishedAt();
        this.photoPath = entity.getPhotoPath();
        this.distance = entity.getDistance();
        this.totalTime = entity.getTotalTime();
        this.date = entity.getDate();
        this.spentKcal = entity.getSpentKcal();
    }
}
