package com.pinkdumbell.cocobob.domain.record.healthrecord.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Builder
@AllArgsConstructor
@Getter
public class RecentWeightsResponseDto {
    private final Map<LocalDate, Double> weightPerDate;

    public RecentWeightsResponseDto(List<RecentWeightsPerDatesDto> recentWeightsPerDatesDtos) {
        Map<LocalDate, Double> weightPerDate = new HashMap<>();
        recentWeightsPerDatesDtos.forEach(recentWeightsPerDatesDto -> weightPerDate.put(
                recentWeightsPerDatesDto.getDate(),
                recentWeightsPerDatesDto.getWeight()
            ));
        this.weightPerDate = weightPerDate;
    }
}
