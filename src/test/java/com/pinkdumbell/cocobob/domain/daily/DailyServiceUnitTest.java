package com.pinkdumbell.cocobob.domain.daily;


import com.pinkdumbell.cocobob.common.ImageService;
import com.pinkdumbell.cocobob.domain.record.daily.Daily;
import com.pinkdumbell.cocobob.domain.record.daily.DailyRepository;
import com.pinkdumbell.cocobob.domain.record.daily.DailyService;
import com.pinkdumbell.cocobob.domain.record.daily.dto.DailyCreateRequestDto;
import com.pinkdumbell.cocobob.domain.record.daily.dto.DailyDetailResponseDto;
import com.pinkdumbell.cocobob.domain.record.daily.dto.DailyUpdateRequestDto;
import com.pinkdumbell.cocobob.domain.record.daily.image.DailyImage;
import com.pinkdumbell.cocobob.domain.record.daily.image.DailyImageRepository;
import com.pinkdumbell.cocobob.domain.pet.Pet;
import com.pinkdumbell.cocobob.domain.pet.PetRepository;
import com.pinkdumbell.cocobob.domain.user.dto.LoginUserInfo;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.BDDMockito.*;
@ExtendWith(MockitoExtension.class)
class DailyServiceUnitTest {
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

    Daily daily;
    List<DailyImage> images;
    String amazonDomain = "amazonaws.com/";
    @BeforeEach
    void setup() {
        daily = Daily.builder()
                .id(1L)
                .note("test")
                .date(LocalDate.parse("2022-01-01"))
                .build();
        images = new ArrayList<>(List.of(
                DailyImage.builder()
                        .id(1L)
                        .path(amazonDomain + "test1")
                        .build(),
                DailyImage.builder()
                        .id(2L)
                        .path(amazonDomain + "test2")
                        .build()
        ));
    }

    @Test
    @DisplayName("데일리 생성 메서드를 호출하면 이미지 저장 메서드가 호출된다.")
    void testCallSaveImagesWhenCallCreateDaily() {
        DailyCreateRequestDto requestDto = DailyCreateRequestDto.builder()
                .date(LocalDate.parse("2022-01-01"))
                .note("test")
                .images(List.of(new MockMultipartFile[]{new MockMultipartFile("1", (byte[]) null), new MockMultipartFile("2", (byte[]) null)}))
                .build();
        given(petRepository.findById(anyLong())).willReturn(Optional.ofNullable(Pet.builder().build()));
        given(dailyRepository.save(any(Daily.class))).willReturn(Daily.builder().id(daily.getId()).build());
        given(imageService.saveImage(any(MockMultipartFile.class), anyString())).willReturn("test");
        given(dailyImageRepository.save(any(DailyImage.class))).willReturn(DailyImage.builder().build());
        dailyService.createDaily(requestDto, new LoginUserInfo("test@test.com"), 1L);
        verify(petRepository, times(1)).findById(anyLong());
        verify(dailyRepository, times(1)).save(any(Daily.class));
        verify(imageService, times(2)).saveImage(any(MockMultipartFile.class), anyString());
        verify(dailyImageRepository, times(2)).save(any(DailyImage.class));
    }

    @Test
    @DisplayName("데일리 조회 메서드 테스트")
    void testGetDaily() {
        given(dailyRepository.findById(daily.getId())).willReturn(Optional.of(daily));
        given(dailyImageRepository.findAllByDaily(daily)).willReturn(images);

        DailyDetailResponseDto result = dailyService.getDaily(daily.getId());

        assertThat(result.getDailyId()).isEqualTo(daily.getId());
        assertThat(result.getNote()).isEqualTo(daily.getNote());
        assertThat(result.getDate()).isEqualTo(daily.getDate());
        assertThat(result.getImages().size()).isEqualTo(images.size());
    }

    @Test
    @DisplayName("데일리 수정 메서드 테스트")
    void testUpdateDaily() {
        DailyUpdateRequestDto requestDto = new DailyUpdateRequestDto(
                List.of(new MockMultipartFile[]{
                        new MockMultipartFile("1", (byte[]) null),
                        new MockMultipartFile("2", (byte[]) null),
                        new MockMultipartFile("3", (byte[]) null)
                }),
                "changed",
                images.stream().map(DailyImage::getId).collect(Collectors.toList())
        );
        given(dailyRepository.findById(1L)).willReturn(Optional.ofNullable(daily));
        given(dailyImageRepository.save(any(DailyImage.class))).willReturn(DailyImage.builder().build());
        given(imageService.saveImage(any(MockMultipartFile.class), anyString())).willReturn("test");
        given(dailyImageRepository.findAllById(anyList())).willReturn(images);

        dailyService.updateDaily(requestDto, daily.getId());

        verify(imageService, times(requestDto.getNewImages().size())).saveImage(any(MockMultipartFile.class), anyString());
        verify(dailyImageRepository, times(requestDto.getNewImages().size())).save(any(DailyImage.class));
        verify(dailyImageRepository, times(1)).deleteAll(anyList());
        verify(imageService, times(requestDto.getImageIdsToDelete().size())).deleteImage(anyString());
        assertThat(daily.getNote()).isEqualTo(requestDto.getNote());
    }

    @Test
    @DisplayName("데일리 삭제 테스트")
    void testDeleteDaily() {
        given(dailyRepository.findById(daily.getId())).willReturn(Optional.ofNullable(daily));
        given(dailyImageRepository.findAllByDaily(any(Daily.class))).willReturn(images);

        dailyService.deleteDaily(daily.getId());

        verify(dailyImageRepository, times(1)).deleteAll(anyList());
        verify(imageService, times(images.size())).deleteImage(anyString());
        verify(dailyRepository, times(1)).delete(any(Daily.class));
    }
}