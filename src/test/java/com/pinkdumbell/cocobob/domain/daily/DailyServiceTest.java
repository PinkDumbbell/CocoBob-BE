package com.pinkdumbell.cocobob.domain.daily;

import static org.junit.jupiter.api.Assertions.*;

import com.pinkdumbell.cocobob.common.ImageService;
import com.pinkdumbell.cocobob.domain.daily.image.DailyImageRepository;
import com.pinkdumbell.cocobob.domain.pet.PetRepository;
import com.pinkdumbell.cocobob.domain.pet.PetService;
import com.pinkdumbell.cocobob.domain.pet.breed.BreedRepository;
import com.pinkdumbell.cocobob.domain.user.UserRepository;
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

    }

    @Test
    @DisplayName("데일리 기록을 조회할 수 있다.")
    void testGetDaily() {
    }

    @Test
    @DisplayName("새로운 단순 데일리 일자를 조회할 수 있다.")
    void testGetSimpleDaily() {
    }

    @Test
    @DisplayName("데일리 기록을 수정할 수 있다.")
    void testUpdateDailyRecord() {
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