package com.pinkdumbell.cocobob.domain.record.walk;

import com.pinkdumbell.cocobob.domain.pet.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface WalkRepository extends JpaRepository<Walk, Long> {

    List<Walk> findAllByPetAndDateBetween(Pet pet, LocalDate startDay, LocalDate endDay);
}
