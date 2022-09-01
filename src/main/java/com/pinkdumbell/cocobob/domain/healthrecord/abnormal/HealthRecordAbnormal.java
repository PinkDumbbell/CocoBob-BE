package com.pinkdumbell.cocobob.domain.healthrecord.abnormal;

import com.pinkdumbell.cocobob.domain.abnormal.Abnormal;
import com.pinkdumbell.cocobob.domain.healthrecord.HealthRecord;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@Getter
@NoArgsConstructor
@Entity
public class HealthRecordAbnormal {
    @EmbeddedId
    private HealthRecordAbnormalId healthRecordAbnormalId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "health_record_id")
    @MapsId("healthRecordId")
    private HealthRecord healthRecord;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "abnormal_id")
    @MapsId("abnormalId")
    private Abnormal abnormal;
}
