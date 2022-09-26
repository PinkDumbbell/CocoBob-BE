package com.pinkdumbell.cocobob.domain.record.walk;

import com.pinkdumbell.cocobob.domain.pet.Pet;
import com.pinkdumbell.cocobob.domain.record.walk.dto.WalkBriefInfoDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface WalkRepository extends JpaRepository<Walk, Long> {

    List<Walk> findAllByPetAndDateBetween(Pet pet, LocalDate startDay, LocalDate endDay);

    @Query("select new com.pinkdumbell.cocobob.domain.record.walk.dto.WalkBriefInfoDto(sum(w.totalTime), sum(w.distance)) " +
            "from Walk w " +
            "where w.pet.id = :petId and w.date = :date")
    WalkBriefInfoDto getTotalTimeAndTotalDistance(@Param("petId") Long petId, @Param("date") LocalDate date);
}
