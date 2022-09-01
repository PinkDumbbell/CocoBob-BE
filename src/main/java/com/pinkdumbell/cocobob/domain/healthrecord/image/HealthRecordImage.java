package com.pinkdumbell.cocobob.domain.healthrecord.image;

import com.pinkdumbell.cocobob.domain.healthrecord.HealthRecord;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class HealthRecordImage {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "health_record_image_id")
    private Long id;
    private String path;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "health_record_id")
    private HealthRecord healthRecord;
}
