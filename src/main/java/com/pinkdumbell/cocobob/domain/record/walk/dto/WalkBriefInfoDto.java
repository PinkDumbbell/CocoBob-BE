package com.pinkdumbell.cocobob.domain.record.walk.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class WalkBriefInfoDto {
    private final Long totalTime;
    private final Double totalDistance;

    public WalkBriefInfoDto(Long totalTime, Double totalDistance) {
        this.totalTime = Objects.requireNonNullElse(totalTime, 0L);
        this.totalDistance = Objects.requireNonNullElse(totalDistance, 0.0);
    }
}
