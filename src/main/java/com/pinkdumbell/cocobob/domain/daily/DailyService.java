package com.pinkdumbell.cocobob.domain.daily;

import com.pinkdumbell.cocobob.common.ImageService;
import com.pinkdumbell.cocobob.domain.daily.dto.DailyNoteGetResponseDto;
import com.pinkdumbell.cocobob.domain.daily.dto.DailyNoteRegisterRequestDto;
import com.pinkdumbell.cocobob.domain.daily.dto.DailyNoteRegisterResponseDto;
import com.pinkdumbell.cocobob.domain.daily.image.DailyImage;
import com.pinkdumbell.cocobob.domain.daily.image.DailyImageRepository;
import com.pinkdumbell.cocobob.domain.pet.Pet;
import com.pinkdumbell.cocobob.domain.pet.PetRepository;
import com.pinkdumbell.cocobob.exception.CustomException;
import com.pinkdumbell.cocobob.exception.ErrorCode;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.hibernate.service.NullServiceException;
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
    public DailyNoteRegisterResponseDto recordNote(
        DailyNoteRegisterRequestDto dailyNoteRegisterRequestDto, Long petId) {

        Pet pet = petRepository.findById(petId).orElseThrow(() -> {
            throw new CustomException(ErrorCode.PET_NOT_FOUND);
        });

        Daily newDaily = Daily.builder().date(dailyNoteRegisterRequestDto.getDate()).pet(pet)
            .note(dailyNoteRegisterRequestDto.getNote()).build();

        Daily savedDaily = dailyRepository.save(newDaily);

        if (dailyNoteRegisterRequestDto.getNoteImages() != null) {
            try {
                IntStream.range(0, dailyNoteRegisterRequestDto.getNoteImages().size())
                    .forEach(index -> {
                        String saveImagePath = imageService.saveImage(
                            dailyNoteRegisterRequestDto.getNoteImages().get(index),
                            createDailyImageName(petId, dailyNoteRegisterRequestDto.getDate(),
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

        return new DailyNoteRegisterResponseDto(savedDaily.getId());
    }

    @Transactional(readOnly = true)
    public List<DailyNoteGetResponseDto> getNotes(Long petId) {

        Pet pet = petRepository.findById(petId).orElseThrow(() -> {
            throw new CustomException(ErrorCode.PET_NOT_FOUND);
        });

        List<Daily> petDailys = dailyRepository.findAllByPet(pet);

        return petDailys.stream().map(
                daily -> new DailyNoteGetResponseDto(daily, dailyImageRepository.findAllByDaily(daily)))
            .collect(Collectors.toList());

    }

    //PetId, 기록시간, 저장 시각, 사진 숫자로 저장
    private String createDailyImageName(Long petId, LocalDate date, int index) {
        return "daily/" + petId + "_" + date + "_" + index;
    }

}
