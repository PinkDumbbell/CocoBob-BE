package com.pinkdumbell.cocobob.domain.record;

import com.pinkdumbell.cocobob.domain.pet.Pet;
import com.pinkdumbell.cocobob.domain.pet.PetRepository;
import com.pinkdumbell.cocobob.domain.record.daily.DailyRepository;
import com.pinkdumbell.cocobob.domain.record.dto.RecordExistResponseDto;
import com.pinkdumbell.cocobob.domain.record.healthrecord.HealthRecordRepository;
import com.pinkdumbell.cocobob.domain.record.walk.WalkRepository;
import com.pinkdumbell.cocobob.exception.CustomException;
import com.pinkdumbell.cocobob.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class RecordService {

    private final HealthRecordRepository healthRecordRepository;
    private final WalkRepository walkRepository;
    private final DailyRepository dailyRepository;
    private final PetRepository petRepository;

    @Transactional(readOnly = true)
    public Map<LocalDate, RecordExistResponseDto> getIdsOfDaysOfMonth(Long petId, YearMonth yearMonth) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new CustomException(ErrorCode.PET_NOT_FOUND));
        LocalDate firstDayOfMonth = yearMonth.atDay(1);
        LocalDate lastDayOfMonth = yearMonth.atEndOfMonth();


        return makeIdsOfHealthRecord(pet, firstDayOfMonth, lastDayOfMonth);
    }

    @Transactional(readOnly = true)
    public Map<LocalDate, RecordExistResponseDto> makeIdsOfHealthRecord(Pet pet, LocalDate firstDayOfMonth, LocalDate lastDayOfMonth) {
        Map<LocalDate, RecordExistResponseDto> result = new HashMap<>();
        healthRecordRepository.findAllByPetAndDateBetween(pet, firstDayOfMonth, lastDayOfMonth)
                .forEach(healthRecord -> result.put(
                        healthRecord.getDate(),
                        RecordExistResponseDto.builder()
                                .healthRecordId(healthRecord.getId())
                                .build()
                ));
        makeIdsOfDaily(pet, firstDayOfMonth, lastDayOfMonth, result);
        makeIdsOfWalk(pet, firstDayOfMonth, lastDayOfMonth, result);
        return result;
    }

    @Transactional(readOnly = true)
    public void makeIdsOfWalk(
            Pet pet,
            LocalDate firstDayOfMonth,
            LocalDate lastDayOfMonth,
            Map<LocalDate, RecordExistResponseDto> result) {
        walkRepository.findAllByPetAndDateBetween(pet, firstDayOfMonth, lastDayOfMonth)
                .forEach(walk -> {
                    if (result.containsKey(walk.getDate())) {
                        result.get(walk.getDate()).addWalkId(walk.getId());
                    } else {
                        result.put(walk.getDate(), RecordExistResponseDto.builder()
                                        .walkIds(List.of(walk.getId()))
                                .build());
                    }
                });
    }

    @Transactional(readOnly = true)
    public void makeIdsOfDaily(
            Pet pet,
            LocalDate firstDayOfMonth,
            LocalDate lastDayOfMonth,
            Map<LocalDate, RecordExistResponseDto> result
    ) {
        dailyRepository.findAllByPetAndDateBetween(pet, firstDayOfMonth, lastDayOfMonth)
                .forEach(daily -> {
                    if (result.containsKey(daily.getDate())) {
                        result.get(daily.getDate()).setDailyId(daily.getId());
                    } else {
                        result.put(daily.getDate(), RecordExistResponseDto.builder()
                                        .dailyId(daily.getId())
                                .build());
                    }
                });
    }
}
