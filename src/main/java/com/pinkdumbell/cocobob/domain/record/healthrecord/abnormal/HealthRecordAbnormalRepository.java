package com.pinkdumbell.cocobob.domain.record.healthrecord.abnormal;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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

    @Query("delete " +
            "from HealthRecordAbnormal ha " +
            "where ha.abnormal.id in :abnormalIds " +
            "and ha.healthRecord.id = :healthRecordId")
    @Modifying
    void deleteAllByHealthRecordIdAndAbnormalId(@Param("healthRecordId") Long healthRecordId,
                                                @Param("abnormalIds") List<Long> abnormalIds);

    @Query("delete " +
            "from HealthRecordAbnormal ha " +
            "where ha.healthRecord.id = :healthRecordId ")
    @Modifying
    void deleteAllByHealthRecordId(
            @Param("healthRecordId") Long healthRecordId
    );
}
