package com.pinkdumbell.cocobob.domain.daily;


import static org.mockito.BDDMockito.*;

import com.pinkdumbell.cocobob.common.ImageService;
import com.pinkdumbell.cocobob.domain.daily.dto.DailyRecordGetRequestDto;
import com.pinkdumbell.cocobob.domain.daily.dto.DailyRecordGetResponseDto;
import com.pinkdumbell.cocobob.domain.daily.dto.DailyRecordRegisterRequestDto;
import com.pinkdumbell.cocobob.domain.daily.dto.DailyRecordRegisterResponseDto;
import com.pinkdumbell.cocobob.domain.daily.dto.DailyRecordUpdateRequestDto;
import com.pinkdumbell.cocobob.domain.daily.dto.DailyRecordUpdateResponseDto;
import com.pinkdumbell.cocobob.domain.daily.image.DailyImageRepository;
import com.pinkdumbell.cocobob.domain.pet.Pet;
import com.pinkdumbell.cocobob.domain.pet.PetRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DailyServiceTest {

    @InjectMocks
    DailyService dailyService;
    @Mock
    DailyRepository dailyRepository;
    @Mock
    DailyImageRepository dailyImageRepository;
    @Mock
    PetRepository petRepository;
    @Mock
    ImageService imageService;

    @Test
    @DisplayName("새로운 데일리 기록을 생성할 수 있다.")
    void testCreateDailyRecord() {

        //Given
        DailyRecordRegisterRequestDto dailyRecordRegisterRequestDto = new DailyRecordRegisterRequestDto();
        Long petId = 1L;
        given(petRepository.findById(anyLong())).willReturn(Optional.of(new Pet()));

        given(dailyRepository.save(any(Daily.class))).willReturn(Daily.builder().id(13L).build());

        //Execute
        DailyRecordRegisterResponseDto result = dailyService.createDailyRecord(
            dailyRecordRegisterRequestDto, petId);

        //Expect
        Assertions.assertThat(result.getDailyId()).isEqualTo(13L);

    }

    @Test
    @DisplayName("데일리 기록을 수정할 수 있다.")
    void testUpdateDailyRecord() {

        //Given
        Long dailyId = 1L;
        DailyRecordUpdateRequestDto dailyRecordUpdateRequestDto = new DailyRecordUpdateRequestDto(
            new ArrayList<>(), "행복하게", 434, 3, 4.4f, "{latitude:}");

        given(dailyRepository.findById(anyLong())).willReturn(
            Optional.of(Daily.builder().note("Dog Bow").build()));

        //Execute
        DailyRecordUpdateResponseDto result = dailyService.updateDailyRecord(
            dailyId, dailyRecordUpdateRequestDto);

        //Expect
        Assertions.assertThat(result.getDailyId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("데일리 기록을 삭제할 수 있다.")
    void testDeleteDaily() {
    }

    @Test
    @DisplayName("데일리 기록 상세 정보를 조회할 수 있다.")
    void testGetDailyDetailRecord() {
    }

    @Test
    @DisplayName("데일리 기록내에 이미지를 개별적으로 삭제할 수 있다.")
    void testDeleteDailyImage() {
    }

    @Test
    @DisplayName("데일리 기록 이미지를 저장할 수 있다.")
    void testSaveDailyImages() {
    }
}