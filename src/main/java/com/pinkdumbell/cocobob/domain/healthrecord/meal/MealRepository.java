package com.pinkdumbell.cocobob.domain.healthrecord.meal;

import com.pinkdumbell.cocobob.domain.healthrecord.HealthRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MealRepository extends JpaRepository<Meal, Long> {
    List<Meal> findAllByHealthRecord(HealthRecord healthRecord);
}
