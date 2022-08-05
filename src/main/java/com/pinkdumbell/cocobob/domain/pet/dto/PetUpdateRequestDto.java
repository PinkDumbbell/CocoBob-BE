package com.pinkdumbell.cocobob.domain.pet.dto;


import com.pinkdumbell.cocobob.domain.pet.PetSex;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.*;

@Getter
public class PetUpdateRequestDto extends PetCreateRequestDto {
    @NotNull(message = "사진 단순 삭제여부가 필요합니다.")
    private Boolean isImageJustDeleted;

    public PetUpdateRequestDto(
            @NotBlank(message = "반려동물의 이름이 필요합니다.") String name,
            @NotNull(message = "반려동물의 성별이 필요합니다.") PetSex sex,
            @Valid PetCreateRequestAgeDto age,
            @NotNull(message = "반려동물의 중성화 여부가 필요합니다.") Boolean isSpayed,
            @NotNull(message = "반려동물의 임신/수유 여부가 필요합니다.") Boolean isPregnant,
            @NotNull(message = "반려동물의 몸무게가 필요합니다.")
            @Positive(message = "몸무게는 0보다 커야 합니다.") Float bodyWeight,
            @NotNull(message = "반려동물의 활동수준이 필요합니다.")
            @Min(value = 1, message = "활동 수준은 1~5의 값이어야 합니다.")
            @Max(value = 5, message = "활동 수준은 1~5의 값이어야 합니다.")
            Integer activityLevel,
            @NotNull(message = "반려동물 견종에 대한 정보가 필요합니다.")
            @Positive(message = "견종 아이디는 0보다 커야합니다.")
            Long breedId,
            MultipartFile petImage,
            Boolean isImageJustDeleted) {
        super(name, sex, age, isSpayed, isPregnant, bodyWeight, activityLevel, breedId, petImage);
        this.isImageJustDeleted = isImageJustDeleted;
    }
}
