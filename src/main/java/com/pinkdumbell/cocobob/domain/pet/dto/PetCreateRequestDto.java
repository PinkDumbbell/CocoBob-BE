package com.pinkdumbell.cocobob.domain.pet.dto;

import com.pinkdumbell.cocobob.domain.pet.PetSex;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Builder
@AllArgsConstructor
@Getter
public class PetCreateRequestDto {
    @ApiModelProperty(notes = "반려동물 이름", example = "코코", required = true)
    @NotBlank(message = "반려동물의 이름이 필요합니다.")
    private String name;
    @ApiModelProperty(notes = "반려동물 성별", example = "FEMALE", required = true, allowableValues = "MALE, FEMALE")
    @NotNull(message = "반려동물의 성별이 필요합니다.")
    private PetSex sex;
    @ApiModelProperty(notes = "반려동물 나이(개월 수)", example = "25", required = true)
    @NotNull(message = "반려동물의 나이(개월 수)가 필요합니다.")
    @Positive(message = "나이(개월 수)는 0보다 커야합니다.")
    private Integer age;
    @ApiModelProperty(notes = "반려동물 생일", example = "1999-12-31", required = false)
    @PastOrPresent(message = "생일은 과거 또는 현재의 날짜여야 합니다.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;
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
