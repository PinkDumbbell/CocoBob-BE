package com.pinkdumbell.cocobob.domain.daily.image;

import com.pinkdumbell.cocobob.domain.daily.Daily;
import com.pinkdumbell.cocobob.domain.pet.Pet;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyImageRepository extends JpaRepository<DailyImage,Long> {
    List<DailyImage> findAllByDaily(Daily daily);
    Long countAllByDaily(Daily daily);
    void deleteAllByDaily(Daily daily);
}
