package com.pinkdumbell.cocobob.domain.record.healthrecord.meal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MealRepository extends JpaRepository<Meal, Long> {

    @Query("select m " +
            "from Meal m left join fetch m.product " +
            "where m.healthRecord.id=:healthRecordId")
    List<Meal> findAllByHealthRecordId(@Param("healthRecordId") Long healthRecordId);

    @Query("delete " +
            "from Meal m " +
            "where m.healthRecord.id = :healthRecordId")
    @Modifying
    void deleteAllByHealthRecordId(
            @Param("healthRecordId") Long healthRecordId
    );
}
