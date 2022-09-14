package com.pinkdumbell.cocobob.domain.healthrecord.abnormal;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HealthRecordAbnormalRepository extends JpaRepository<HealthRecordAbnormal, HealthRecordAbnormalId> {

    @Query("select distinct ha " +
            "from HealthRecordAbnormal ha join fetch ha.healthRecord " +
            "join fetch ha.abnormal " +
            "where ha.healthRecord.id=:healthRecordId")
    List<HealthRecordAbnormal> findAllAbnormalByHealthRecord(
            @Param("healthRecordId") Long healthRecordId
    );
}
