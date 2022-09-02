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
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
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
            images.stream().forEach(image -> {
                dailyImageRepository.save(DailyImage.builder()
                            .daily(daily)
                            .path(imageService.saveImage(
                                    image,
                                    DAILY_IMAGE_PREFIX + daily.getId() + "_" + indexHolder.getAndIncrement()
                            ))
                        .build());
            });
        }
    }

//    @Transactional
//    public void createDaily(TempDailyRequestDto requestDto, Long petId) {
//
//        dailyRepository.save(new Daily(requestDto, petRepository.findById(petId)
//                .orElseThrow(() -> new CustomException(ErrorCode.PET_NOT_FOUND))));
//    }
//
//    @Transactional(readOnly = true)
//    public TempDailyDatesResponseDto getDatesOfRecordedDailyOfMonth(YearMonth date, Long petId) {
//
//        return new TempDailyDatesResponseDto(dailyRepository.findAllByPetAndDateBetweenOrderByIdDesc(
//                petRepository.findById(petId)
//                        .orElseThrow(() -> new CustomException(ErrorCode.PET_NOT_FOUND)),
//                date.atDay(1),
//                date.atEndOfMonth()
//        ));
//    }
//
//    @Transactional(readOnly = true)
//    public TempDailyResponseDto getDaily(Long dailyId) {
//
//        return new TempDailyResponseDto(dailyRepository.findById(dailyId)
//                .orElseThrow(() -> new CustomException(ErrorCode.DAILY_NOT_FOUND)));
//    }
//
//    @Transactional
//    public void updateDaily(TempDailyRequestDto requestDto, Long dailyId) {
//
//        dailyRepository.findById(dailyId)
//                .orElseThrow(() -> new CustomException(ErrorCode.DAILY_NOT_FOUND))
//                .tempUpdateDaily(requestDto);
//    }
//
//    @Transactional
//    public void deleteDaily(Long dailyId) {
//
//        dailyRepository.delete(dailyRepository.findById(dailyId)
//                .orElseThrow(() -> new CustomException(ErrorCode.DAILY_NOT_FOUND)));
//    }

//    @Transactional
//    public DailyRecordRegisterResponseDto createDailyRecord(
//        DailyRecordRegisterRequestDto dailyRecordRegisterRequestDto, Long petId) {
//
//        Pet pet = petRepository.findById(petId).orElseThrow(() -> {
//            throw new CustomException(ErrorCode.PET_NOT_FOUND);
//        });
//
//        Daily savedDaily = dailyRepository.save(new Daily(dailyRecordRegisterRequestDto, pet));
//
//        saveDailyImages(dailyRecordRegisterRequestDto.getImages(), savedDaily);
//
//        return new DailyRecordRegisterResponseDto(savedDaily.getId());
//    }
//
//    @Transactional(readOnly = true)
//    public List<DailyRecordGetResponseDto> getDaily(Long petId,
//        DailyRecordGetRequestDto dailyRecordGetRequestDto) {
//
//        Pet pet = petRepository.findById(petId).orElseThrow(() -> {
//            throw new CustomException(ErrorCode.PET_NOT_FOUND);
//        });
//
//        List<Daily> petDailys = dailyRepository.findAllByPetAndDateBetweenOrderByIdDesc(pet,
//            dailyRecordGetRequestDto.getStartDate(), dailyRecordGetRequestDto.getLastDate());
//
//        return petDailys.stream().map(
//                daily -> new DailyRecordGetResponseDto(daily,
//                    dailyImageRepository.findAllByDaily(daily)))
//            .collect(Collectors.toList());
//
//    }
//
//    @Transactional(readOnly = true)
//    public List<DailySimpleResponseDto> getSimpleDaily(Long petId, YearMonth yearMonth) {
//
//        Pet pet = petRepository.findById(petId).orElseThrow(() -> {
//            throw new CustomException(ErrorCode.PET_NOT_FOUND);
//        });
//
//        LocalDate startDate = LocalDate.of(yearMonth.getYear(), yearMonth.getMonthValue(), 1);
//        LocalDate lastDate = LocalDate.of(yearMonth.getYear(), yearMonth.getMonthValue(), 31);
//
//        return dailyRepository.findAllByPetAndDateBetweenOrderByIdDesc(pet, startDate, lastDate).stream()
//            .map(DailySimpleResponseDto::new)
//            .collect(Collectors.toList());
//
//    }
//
//    @Transactional
//    public DailyRecordUpdateResponseDto updateDailyRecord(Long dailyId,
//        DailyRecordUpdateRequestDto dailyRecordUpdateRequestDto) {
//
//        Daily daily = dailyRepository.findById(dailyId).orElseThrow(() -> {
//            throw new CustomException(ErrorCode.DAILY_NOT_FOUND);
//        });
//
//        daily.updateDaily(dailyRecordUpdateRequestDto); // 요청 정보를 바탕으로 정보 변경
//
//        //새로 추가된 이미지 저장
//        saveDailyImages(dailyRecordUpdateRequestDto.getImages(), daily);
//
//        return new DailyRecordUpdateResponseDto(dailyId);
//    }
//
//    @Transactional
//    public void deleteDaily(Long dailyId) {
//
//        Daily daily = dailyRepository.findById(dailyId).orElseThrow(() -> {
//            throw new CustomException(ErrorCode.DAILY_NOT_FOUND);
//        });
//
//        //S3 이미지 전체 삭제
//        dailyImageRepository.findAllByDaily(daily).forEach(dailyImage -> {
//            String targetImage = dailyImage.getPath();
//            imageService.deleteImage(targetImage.substring(targetImage.lastIndexOf("daily/")));
//        });
//
//        //DailyImage Table 기록 삭제
//        dailyImageRepository.deleteAllByDaily(daily);
//
//        //daily 삭제
//        dailyRepository.delete(daily);
//
//    }
//
//    @Transactional(readOnly = true)
//    public DailyRecordDetailResponseDto getDailyDetailRecord(Long dailyId) {
//
//        Daily daily = dailyRepository.findById(dailyId).orElseThrow(() -> {
//            throw new CustomException(ErrorCode.DAILY_NOT_FOUND);
//        });
//
//        return new DailyRecordDetailResponseDto(daily, dailyImageRepository.findAllByDaily(daily));
//
//    }
//
//    @Transactional
//    public void deleteDailyImage(Long dailyId, Long dailyImageId) {
//
//        Daily daily = dailyRepository.findById(dailyId).orElseThrow(() -> {
//            throw new CustomException(ErrorCode.DAILY_NOT_FOUND);
//        });
//
//        DailyImage dailyImage = dailyImageRepository.findByIdAndDaily(dailyImageId, daily)
//            .orElseThrow(() -> {
//                throw new CustomException(ErrorCode.DAILY_IMAGE_NOT_FOUND);
//            });
//
//        String targetImage = dailyImage.getPath();
//        imageService.deleteImage(targetImage.substring(targetImage.lastIndexOf("daily/")));
//
//        dailyImageRepository.delete(dailyImage);
//    }
//
//    @Transactional(propagation = Propagation.MANDATORY)
//    public void saveDailyImages(List<MultipartFile> images, Daily daily) {
//        if (images != null) {
//            try {
//                IntStream.range(0, images.size())
//                    .forEach(index -> {
//                        String saveImagePath = imageService.saveImage(images.get(index),
//                            createDailyImageName(daily.getId(), daily.getDate(), index));
//
//                        DailyImage newDailyImage = DailyImage.builder()
//                            .daily(daily)
//                            .path(saveImagePath)
//                            .build();
//
//                        dailyImageRepository.save(newDailyImage);
//                    });
//            } catch (NullPointerException e) {
//                throw new CustomException(ErrorCode.NOT_IMAGE);
//            }
//        }
//
//    }
//
//    //PetId, 기록시간, 저장 시각, 사진 숫자로 저장
//    private String createDailyImageName(Long dailyId, LocalDate date, int index) {
//
//        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
//        return "daily/" + dailyId + "_" + date + "_" + timeStamp + "_" + index;
//    }

}
