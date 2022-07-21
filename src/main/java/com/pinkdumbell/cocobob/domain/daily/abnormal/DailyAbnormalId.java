package com.pinkdumbell.cocobob.domain.daily.abnormal;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@Getter
@NoArgsConstructor
public class DailyAbnormalId implements Serializable {
    private Long abnormalId;
    private Long dailyId;
}
