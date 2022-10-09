package com.pinkdumbell.cocobob.domain.record.daily.image;

import com.pinkdumbell.cocobob.domain.record.daily.Daily;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyImageRepository extends JpaRepository<DailyImage,Long> {
    List<DailyImage> findAllByDaily(Daily daily);
    void deleteAllByDaily(Daily daily);
    Optional<DailyImage> findByIdAndDaily(Long dailyImageId,Daily daily);
}
