package com.pinkdumbell.cocobob.domain.record.healthrecord.dto;

import com.pinkdumbell.cocobob.domain.record.healthrecord.HealthRecord;
import lombok.Getter;

@Getter
public class HealthRecordCreateResponseDto {
    private final Long healthRecordId;

    public HealthRecordCreateResponseDto(HealthRecord entity) {
        this.healthRecordId = entity.getId();
    }
}
