package com.pinkdumbell.cocobob.domain.daily;

import com.pinkdumbell.cocobob.domain.pet.Pet;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyRepository extends JpaRepository<Daily,Long> {
    List<Daily> findAllByPet(Pet pet);
}
