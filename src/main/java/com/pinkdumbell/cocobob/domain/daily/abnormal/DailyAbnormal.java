package com.pinkdumbell.cocobob.domain.daily.abnormal;

import com.pinkdumbell.cocobob.domain.abnormal.Abnormal;
import com.pinkdumbell.cocobob.domain.daily.Daily;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class DailyAbnormal {
    @EmbeddedId
    private DailyAbnormalId dailyAbnormalId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "daily_id")
    @MapsId("daily")
    private Daily daily;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "abnormal_id")
    @MapsId("abnormal")
    private Abnormal abnormal;
}
