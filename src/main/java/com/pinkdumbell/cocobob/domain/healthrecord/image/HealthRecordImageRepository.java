package com.pinkdumbell.cocobob.domain.healthrecord.image;

import com.pinkdumbell.cocobob.domain.healthrecord.HealthRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HealthRecordImageRepository extends JpaRepository<HealthRecordImage, Long> {
    List<HealthRecordImage> findAllByHealthRecord(HealthRecord healthRecord);

    @Query("delete " +
            "from HealthRecordImage hi " +
            "where hi.healthRecord.id = :healthRecordId")
    @Modifying
    void deleteAllByHealthRecordId(
            @Param("healthRecordId") Long healthRecordId
    );
}
