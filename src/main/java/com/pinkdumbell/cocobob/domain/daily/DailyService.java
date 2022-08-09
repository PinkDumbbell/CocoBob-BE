package com.pinkdumbell.cocobob.domain.daily;

import com.pinkdumbell.cocobob.common.ImageService;
import com.pinkdumbell.cocobob.domain.daily.dto.DailyRecordGetRequestDto;
import com.pinkdumbell.cocobob.domain.daily.dto.DailyRecordGetResponseDto;
import com.pinkdumbell.cocobob.domain.daily.dto.DailyRecordRegisterRequestDto;
import com.pinkdumbell.cocobob.domain.daily.dto.DailyRecordRegisterResponseDto;
import com.pinkdumbell.cocobob.domain.daily.dto.DailyRecordUpdateRequestDto;
import com.pinkdumbell.cocobob.domain.daily.dto.DailyRecordUpdateResponseDto;
import com.pinkdumbell.cocobob.domain.daily.dto.DailySimpleResponseDto;
import com.pinkdumbell.cocobob.domain.daily.image.DailyImage;
import com.pinkdumbell.cocobob.domain.daily.image.DailyImageRepository;
import com.pinkdumbell.cocobob.domain.pet.Pet;
import com.pinkdumbell.cocobob.domain.pet.PetRepository;
import com.pinkdumbell.cocobob.exception.CustomException;
import com.pinkdumbell.cocobob.exception.ErrorCode;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DailyService {

    private final DailyRepository dailyRepository;
    private final DailyImageRepository dailyImageRepository;
    private final PetRepository petRepository;
    private final ImageService imageService;

    @Transactional
    public DailyRecordRegisterResponseDto createDailyRecord(
        DailyRecordRegisterRequestDto dailyRecordRegisterRequestDto, Long petId) {

        Pet pet = petRepository.findById(petId).orElseThrow(() -> {
            throw new CustomException(ErrorCode.PET_NOT_FOUND);
        });

        Daily savedDaily = dailyRepository.save(new Daily(dailyRecordRegisterRequestDto, pet));

        if (dailyRecordRegisterRequestDto.getImages() != null) {
            try {
                IntStream.range(0, dailyRecordRegisterRequestDto.getImages().size())
                    .forEach(index -> {
                        String saveImagePath = imageService.saveImage(
                            dailyRecordRegisterRequestDto.getImages().get(index),
                            createDailyImageName(petId, dailyRecordRegisterRequestDto.getDate(),
                                index));

                        DailyImage newDailyImage = DailyImage.builder()
                            .daily(savedDaily)
                            .path(saveImagePath)
                            .build();

                        dailyImageRepository.save(newDailyImage);
                    });
            } catch (NullPointerException e) {
                throw new CustomException(ErrorCode.NOT_IMAGE);
            }
        }

        return new DailyRecordRegisterResponseDto(savedDaily.getId());
    }

    @Transactional(readOnly = true)
    public List<DailyRecordGetResponseDto> getDaily(Long petId,
        DailyRecordGetRequestDto dailyRecordGetRequestDto) {

        Pet pet = petRepository.findById(petId).orElseThrow(() -> {
            throw new CustomException(ErrorCode.PET_NOT_FOUND);
        });

        List<Daily> petDailys = dailyRepository.findAllByPetAndDateBetween(pet,
            dailyRecordGetRequestDto.getStartDate(), dailyRecordGetRequestDto.getLastDate());

        return petDailys.stream().map(
                daily -> new DailyRecordGetResponseDto(daily,
                    dailyImageRepository.findAllByDaily(daily)))
            .collect(Collectors.toList());

    }

    @Transactional(readOnly = true)
    public List<DailySimpleResponseDto> getSimpleDaily(Long petId, YearMonth yearMonth) {

        Pet pet = petRepository.findById(petId).orElseThrow(() -> {
            throw new CustomException(ErrorCode.PET_NOT_FOUND);
        });

        LocalDate startDate = LocalDate.of(yearMonth.getYear(), yearMonth.getMonthValue(), 1);
        LocalDate lastDate = LocalDate.of(yearMonth.getYear(), yearMonth.getMonthValue(), 31);

        return dailyRepository.findAllByPetAndDateBetween(pet, startDate, lastDate).stream()
            .map(DailySimpleResponseDto::new)
            .collect(Collectors.toList());

    }

    @Transactional
    public DailyRecordUpdateResponseDto updateDailyRecord(Long dailyId,
        DailyRecordUpdateRequestDto dailyRecordUpdateRequestDto) {

        Daily daily = dailyRepository.findById(dailyId).orElseThrow(() -> {
            throw new CustomException(ErrorCode.DAILY_NOT_FOUND);
        });

        daily.updateDaily(dailyRecordUpdateRequestDto); // 요청 정보를 바탕으로 정보 변경

        //새로 추가된 이미지 저장
        if (dailyRecordUpdateRequestDto.getImages() != null) {
            try {
                int existingImageCount = dailyImageRepository.countAllByDaily(daily).intValue();
                IntStream.range(existingImageCount,
                        existingImageCount + dailyRecordUpdateRequestDto.getImages().size())
                    .forEach(index -> {

                        String saveImagePath = imageService.saveImage(
                            dailyRecordUpdateRequestDto.getImages().get(index),
                            createDailyImageName(daily.getPet().getId(), daily.getDate(),
                                index));

                        DailyImage newDailyImage = DailyImage.builder()
                            .daily(daily)
                            .path(saveImagePath)
                            .build();

                        dailyImageRepository.save(newDailyImage);
                    });
            } catch (NullPointerException e) {
                throw new CustomException(ErrorCode.NOT_IMAGE);
            }
        }

        return new DailyRecordUpdateResponseDto(dailyId);
    }


    //PetId, 기록시간, 저장 시각, 사진 숫자로 저장
    private String createDailyImageName(Long petId, LocalDate date, int index) {
        return "daily/" + petId + "_" + date + "_" + index;
    }

}
