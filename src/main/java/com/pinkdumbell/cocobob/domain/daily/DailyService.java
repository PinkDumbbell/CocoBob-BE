package com.pinkdumbell.cocobob.domain.daily;

import com.pinkdumbell.cocobob.common.ImageService;
import com.pinkdumbell.cocobob.domain.daily.dto.*;
import com.pinkdumbell.cocobob.domain.daily.image.DailyImage;
import com.pinkdumbell.cocobob.domain.daily.image.DailyImageRepository;
import com.pinkdumbell.cocobob.domain.pet.PetRepository;
import com.pinkdumbell.cocobob.domain.user.dto.LoginUserInfo;
import com.pinkdumbell.cocobob.exception.CustomException;
import com.pinkdumbell.cocobob.exception.ErrorCode;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
            images.forEach(image -> dailyImageRepository.save(DailyImage.builder()
                        .daily(daily)
                        .path(imageService.saveImage(
                                image,
                                DAILY_IMAGE_PREFIX + daily.getId() + "_" + UUID.randomUUID()
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

    @Transactional
    public void deleteDaily(Long dailyId) {
        Daily daily = dailyRepository.findById(dailyId)
                .orElseThrow(() -> new CustomException(ErrorCode.DAILY_NOT_FOUND));
        List<DailyImage> images = dailyImageRepository.findAllByDaily(daily);
        dailyImageRepository.deleteAll(images);
        getImageNames(images).forEach(imageService::deleteImage);
        dailyRepository.delete(daily);
    }

    private List<String> getImageNames(List<DailyImage> images) {
        final String amazonDomain = "amazonaws.com/";
        return images.stream().map(dailyImage -> dailyImage.getPath().substring(
                dailyImage.getPath().indexOf(amazonDomain) + amazonDomain.length()
        )).collect(Collectors.toList());
    }
}
