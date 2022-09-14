package com.pinkdumbell.cocobob.domain.healthrecord.abnormal;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

@AllArgsConstructor
@Embeddable
@EqualsAndHashCode
@Getter
@NoArgsConstructor
public class HealthRecordAbnormalId implements Serializable {
    private Long healthRecordId;
    private Long abnormalId;
}
