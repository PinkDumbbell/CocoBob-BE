package com.pinkdumbell.cocobob.domain.daily;

import com.pinkdumbell.cocobob.common.ImageService;
import com.pinkdumbell.cocobob.domain.daily.dto.DailyNoteRequestDto;
import com.pinkdumbell.cocobob.domain.daily.dto.DailyNoteResponseDto;
import com.pinkdumbell.cocobob.domain.daily.image.DailyImage;
import com.pinkdumbell.cocobob.domain.daily.image.DailyImageRepository;
import com.pinkdumbell.cocobob.domain.pet.Pet;
import com.pinkdumbell.cocobob.domain.pet.PetRepository;
import com.pinkdumbell.cocobob.exception.CustomException;
import com.pinkdumbell.cocobob.exception.ErrorCode;
import java.time.LocalDate;
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
    public DailyNoteResponseDto recordNote(DailyNoteRequestDto dailyNoteRequestDto, Long petId) {

        Pet pet = petRepository.findById(petId).orElseThrow(() -> {
            throw new CustomException(ErrorCode.PET_NOT_FOUND);
        });

        Daily newDaily = Daily.builder().date(dailyNoteRequestDto.getDate()).pet(pet)
            .note(dailyNoteRequestDto.getNote()).build();

        Daily savedDaily = dailyRepository.save(newDaily);

        if (dailyNoteRequestDto.getNoteImages()!=null) {
            IntStream.range(0, dailyNoteRequestDto.getNoteImages().size()).forEach(index -> {
                String saveImagePath = imageService.saveImage(
                    dailyNoteRequestDto.getNoteImages().get(index),
                    createDailyImageName(petId, dailyNoteRequestDto.getDate(), index));

                DailyImage newDailyImage = DailyImage.builder()
                    .daily(savedDaily)
                    .path(saveImagePath)
                    .build();

                dailyImageRepository.save(newDailyImage);
            });
        }

        return new DailyNoteResponseDto(savedDaily.getId());
    }

    //PetId, 기록시간, 저장 시각, 사진 숫자로 저장
    private String createDailyImageName(Long petId, LocalDate date, int index) {
        return "daily/" + petId + "_" + date + "_"  + index;
    }

}
