package com.pinkdumbell.cocobob.domain.daily;

import com.pinkdumbell.cocobob.common.ImageService;
import com.pinkdumbell.cocobob.domain.daily.dto.*;
import com.pinkdumbell.cocobob.domain.daily.image.DailyImage;
import com.pinkdumbell.cocobob.domain.daily.image.DailyImageRepository;
import com.pinkdumbell.cocobob.domain.pet.Pet;
import com.pinkdumbell.cocobob.domain.pet.PetRepository;
import com.pinkdumbell.cocobob.domain.user.dto.LoginUserInfo;
import com.pinkdumbell.cocobob.exception.CustomException;
import com.pinkdumbell.cocobob.exception.ErrorCode;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class DailyService {

    private final DailyRepository dailyRepository;
    private final DailyImageRepository dailyImageRepository;
    private final PetRepository petRepository;
    private final ImageService imageService;
    private static final String DAILY_IMAGE_PREFIX = "daily/";

    @Transactional
    public void createDaily(DailyCreateRequestDto requestDto, LoginUserInfo loginUserInfo, Long petId) {
        Daily daily = dailyRepository.save(Daily.builder()
                .date(requestDto.getDate())
                .note(requestDto.getNote())
                .pet(petRepository.findById(petId)
                        .orElseThrow(() -> new CustomException(ErrorCode.PET_NOT_FOUND))
                )
                .build());
        saveImages(daily, requestDto.getImages());
    }

    @Transactional
    public void saveImages(Daily daily, List<MultipartFile> images) {
        if (images != null) {
            AtomicInteger indexHolder = new AtomicInteger();
            images.forEach(image -> dailyImageRepository.save(DailyImage.builder()
                        .daily(daily)
                        .path(imageService.saveImage(
                                image,
                                DAILY_IMAGE_PREFIX + daily.getId() + "_" + indexHolder.getAndIncrement()
                        ))
                    .build()));
        }
    }

    @Transactional(readOnly = true)
    public DailyDetailResponseDto getDaily(Long dailyId) {
        Daily daily = dailyRepository.findById(dailyId)
                .orElseThrow(() -> new CustomException(ErrorCode.DAILY_NOT_FOUND));
        List<DailyImage> images = dailyImageRepository.findAllByDaily(daily);

        return new DailyDetailResponseDto(daily, images);
    }
}
