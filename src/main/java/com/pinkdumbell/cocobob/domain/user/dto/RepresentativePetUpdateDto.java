package com.pinkdumbell.cocobob.domain.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class RepresentativePetUpdateDto {
    @ApiModelProperty(notes = "대표반려동물로 설정할 반려동물의 아이디", example = "1")
    private Long representativePetId;
}
