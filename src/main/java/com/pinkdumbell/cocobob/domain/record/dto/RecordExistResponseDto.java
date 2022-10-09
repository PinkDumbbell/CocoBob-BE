package com.pinkdumbell.cocobob.domain.record.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class RecordExistResponseDto {

    private Long healthRecordId;
    private List<Long> walkIds;
    private Long dailyId;

    public void setHealthRecordId(Long healthRecordId) {
        this.healthRecordId = healthRecordId;
    }

    public void setDailyId(Long dailyId) {
        this.dailyId = dailyId;
    }

    public void addWalkId(Long walkId) {
        if (walkIds == null) {
            walkIds = new ArrayList<>();
        }
        walkIds.add(walkId);
    }

}
