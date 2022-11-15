package com.pinkdumbell.cocobob.domain.record.healthrecord;

import com.pinkdumbell.cocobob.domain.pet.Pet;
import com.pinkdumbell.cocobob.domain.record.healthrecord.dto.RecentWeightsPerDatesDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


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

    @Query("select distinct h " +
            "from HealthRecord h left join fetch h.meals m " +
            "where h.date = :date and h.pet.id = :petId")
    Optional<HealthRecord> findAllByDateAndPetWithMeals(@Param("petId") Long petId, @Param("date") LocalDate date);

    @Query("select new com.pinkdumbell.cocobob.domain.record.healthrecord.dto.RecentWeightsPerDatesDto(h.date, h.bodyWeight) " +
            "from HealthRecord h " +
            "where h.pet.id = :petId and h.bodyWeight is not null " +
            "order by h.date desc, h.id desc ")
    Page<RecentWeightsPerDatesDto> findRecentWeightsWithDatesByPetId(
            @Param("petId") Long petId,
            Pageable pageable
    );
}
