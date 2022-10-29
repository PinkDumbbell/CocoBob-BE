package com.pinkdumbell.cocobob.domain.record.healthrecord.dto;


import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public class RecentWeightsPerDatesDto {
    private final LocalDate date;
    private final Double weight;

    public RecentWeightsPerDatesDto(LocalDate date, Double weight) {
        this.date = date;
        this.weight = weight;
    }
}
