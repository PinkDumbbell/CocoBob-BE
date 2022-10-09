package com.pinkdumbell.cocobob.domain.record.walk.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pinkdumbell.cocobob.domain.record.walk.Walk;
import lombok.Getter;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class WalksBriefInfoDto {

    private final List<WalkSimpleInfoDto> walks;

    public WalksBriefInfoDto(List<Walk> walks) {
        this.walks = walks.stream().map(WalkSimpleInfoDto::new).collect(Collectors.toList());
    }

    @Getter
    public static class WalkSimpleInfoDto {
        private final Long walkId;
        @JsonFormat(pattern = "HH:mm:ss")
        private final LocalTime startedAt;
        @JsonFormat(pattern = "HH:mm:ss")
        private final LocalTime finishedAt;
        private final Integer totalTime;
        private final Double distance;

        public WalkSimpleInfoDto(Walk entity) {
            this.walkId = entity.getId();
            this.startedAt = entity.getStartedAt();
            this.finishedAt = entity.getFinishedAt();
            this.totalTime = entity.getTotalTime();
            this.distance = entity.getDistance();
        }
    }
}
