package com.pinkdumbell.cocobob.domain.pet.dto;

import com.pinkdumbell.cocobob.domain.pet.PetSex;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Builder
@AllArgsConstructor
@Getter
public class PetCreateRequestDto {
    @NotBlank(message = "반려동물의 이름이 필요합니다.")
    private String name;
    @NotBlank(message = "반려동물의 성별이 필요합니다.")
    private PetSex sex;
    @NotBlank(message = "반려동물의 나이(개월 수)가 필요합니다.")
    private int age;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;
    @NotBlank(message = "반려동물의 중성화 여부가 필요합니다.")
    private Boolean isSpayed;
    @NotBlank(message = "반려동물의 임신/수유 여부가 필요합니다.")
    private Boolean isPregnant;
    @NotBlank(message = "반려동물의 몸무게가 필요합니다.")
    private Float bodyWeight;
    @NotBlank(message = "반려동물의 활동수준이 필요합니다.")
    private int activityLevel;
    @NotBlank(message = "반려동물 견종에 대한 정보가 필요합니다.")
    private Long breedId;
    private MultipartFile petImage;
}
