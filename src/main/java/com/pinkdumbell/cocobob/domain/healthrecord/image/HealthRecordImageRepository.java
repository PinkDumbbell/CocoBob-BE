package com.pinkdumbell.cocobob.domain.healthrecord.image;

import com.pinkdumbell.cocobob.domain.healthrecord.HealthRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HealthRecordImageRepository extends JpaRepository<HealthRecordImage, Long> {
    List<HealthRecordImage> findAllByHealthRecord(HealthRecord healthRecord);
}
