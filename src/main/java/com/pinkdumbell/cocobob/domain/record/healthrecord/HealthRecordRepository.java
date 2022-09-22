package com.pinkdumbell.cocobob.domain.record.healthrecord;

import com.pinkdumbell.cocobob.domain.pet.Pet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;


public interface HealthRecordRepository extends JpaRepository<HealthRecord, Long> {

    @Query("select h " +
            "from HealthRecord h " +
            "where h.pet.id = :petId and h.date <= :date and h.bodyWeight is not null " +
            "order by h.date desc")
    Page<HealthRecord> findRecentHealthRecords(
            @Param("petId") Long petId,
            @Param("date")LocalDate date,
            Pageable pageable
    );

    List<HealthRecord> findAllByPetAndDateBetween(Pet pet, LocalDate startDay, LocalDate endDay);
}
