package com.pinkdumbell.cocobob.domain.pet.dto;

import com.pinkdumbell.cocobob.config.annotation.properbirthinfo.ProperBirthInfo;
import com.pinkdumbell.cocobob.domain.pet.PetSex;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.*;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class PetCreateRequestDto {
    @ApiModelProperty(notes = "반려동물 이름", example = "코코", required = true)
    @NotBlank(message = "반려동물의 이름이 필요합니다.")
    private String name;
    @ApiModelProperty(notes = "반려동물 성별", example = "FEMALE", required = true, allowableValues = "MALE, FEMALE")
    @NotNull(message = "반려동물의 성별이 필요합니다.")
    private PetSex sex;
    @ApiModelProperty(notes = "나이(개월 수)와 생일을 담는 DTO", required = true)
    @ProperBirthInfo
    @Valid
    private PetCreateRequestAgeDto age;
    @ApiModelProperty(notes = "중성화 여부", example = "true", required = true)
    @NotNull(message = "반려동물의 중성화 여부가 필요합니다.")
    private Boolean isSpayed;
    @ApiModelProperty(notes = "임신/수유 여부", example = "true", required = true)
    @NotNull(message = "반려동물의 임신/수유 여부가 필요합니다.")
    private Boolean isPregnant;
    @ApiModelProperty(notes = "몸무게", example = "4.6", required = true)
    @NotNull(message = "반려동물의 몸무게가 필요합니다.")
    @Positive(message = "몸무게는 0보다 커야 합니다.")
    private Float bodyWeight;
    @ApiModelProperty(notes = "활동수준", example = "3", required = true, allowableValues = "1, 2, 3, 4, 5")
    @NotNull(message = "반려동물의 활동수준이 필요합니다.")
    @Min(value = 1, message = "활동 수준은 1~5의 값이어야 합니다.")
    @Max(value = 5, message = "활동 수준은 1~5의 값이어야 합니다.")
    private Integer activityLevel;
    @ApiModelProperty(notes = "견종 아이디", example = "2", required = true)
    @NotNull(message = "반려동물 견종에 대한 정보가 필요합니다.")
    @Positive(message = "견종 아이디는 0보다 커야합니다.")
    private Long breedId;
    @ApiModelProperty(notes = "반려동물 프로필 사진", required = false)
    private MultipartFile petImage;
}
