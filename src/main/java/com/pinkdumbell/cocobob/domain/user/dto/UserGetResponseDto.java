package com.pinkdumbell.cocobob.domain.user.dto;

import com.pinkdumbell.cocobob.domain.pet.dto.SimplePetInfoDto;
import com.pinkdumbell.cocobob.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class UserGetResponseDto {
    private final String name;
    private final String email;
    private final List<SimplePetInfoDto> pets;
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
