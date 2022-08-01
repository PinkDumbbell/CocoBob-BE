package com.pinkdumbell.cocobob.domain.user.dto;

import com.pinkdumbell.cocobob.domain.pet.dto.SimplePetInfoDto;
import com.pinkdumbell.cocobob.domain.user.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class UserGetResponseDto {
    @ApiModelProperty(notes = "사용자 이름", example = "은승균")
    private final String name;
    @ApiModelProperty(notes = "사용자 이메일", example = "test@test.com")
    private final String email;
    @ApiModelProperty(notes = "반려동물 간단 정보 목록", example = "[ { petId: 1, name: coco, thumbnailPath: https://... }, {...}, ...]")
    private final List<SimplePetInfoDto> pets;
    @ApiModelProperty(notes = "대표 반려동물 아이디", example = "1")
    private final Long representativeAnimalId;

    public UserGetResponseDto(User entity) {
        this.name = entity.getUsername();
        this.email = entity.getEmail();
        this.pets = entity.getPets().stream().map(SimplePetInfoDto::new).collect(Collectors.toList());
        if (entity.getRepresentativePet() != null) {
            this.representativeAnimalId = entity.getRepresentativePet().getId();
        } else {
            this.representativeAnimalId = null;
        }

    }
}
