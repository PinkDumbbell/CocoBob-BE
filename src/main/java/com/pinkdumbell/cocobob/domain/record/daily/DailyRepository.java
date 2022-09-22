package com.pinkdumbell.cocobob.domain.record.daily;

import com.pinkdumbell.cocobob.domain.pet.Pet;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyRepository extends JpaRepository<Daily,Long> {

    List<Daily> findAllByPetAndDateBetweenOrderByIdDesc(Pet pet, LocalDate startDate,LocalDate lastDate);
    List<Daily> findAllByPetAndDateBetween(Pet pet, LocalDate startDay, LocalDate endDay);
}
