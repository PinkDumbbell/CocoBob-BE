package com.pinkdumbell.cocobob.domain.daily;

import com.pinkdumbell.cocobob.domain.pet.Pet;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DailyRepository extends JpaRepository<Daily,Long> {


    List<Daily> findAllByPetAndDateBetween(Pet pet, LocalDate startDate,LocalDate lastDate);
}
