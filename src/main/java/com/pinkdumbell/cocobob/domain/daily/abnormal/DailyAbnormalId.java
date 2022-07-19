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
    @Column(name = "abnormal_id")
    private Long abnormal;
    @Column(name = "daily_id")
    private Long daily;
}
