package com.pinkdumbell.cocobob.domain.pet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pinkdumbell.cocobob.domain.pet.breed.Breed;
import com.pinkdumbell.cocobob.domain.pet.breed.BreedSize;
import com.pinkdumbell.cocobob.domain.pet.dto.BreedsInfoResponseDto;
import com.pinkdumbell.cocobob.domain.pet.dto.PetDetailResponseDto;
import com.pinkdumbell.cocobob.domain.pet.dto.PetInfoResponseDto;
import com.pinkdumbell.cocobob.domain.user.User;
import com.pinkdumbell.cocobob.domain.user.UserRepository;
import com.pinkdumbell.cocobob.domain.user.dto.LoginUserInfo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class PetServiceTest {
    @InjectMocks
    PetService petService;
    @Mock
    UserRepository userRepository;
    @Mock
    PetRepository petRepository;

    @Test
    @DisplayName("사용자의 반려동물을 불러오는 메서드 테스트")
    void testGetPets() {
        String email = "test@test.com";
        User user = User.builder()
                .email(email)
                .build();
        Breed breed = Breed.builder()
                .name("진돗개")
                .build();
        user.addPets(Pet.builder()
                .breed(breed)
                .build());
        user.addPets(Pet.builder()
                .breed(breed)
                .build());
        given(userRepository.findUserByEmailWithPetDetail(user.getEmail())).willReturn(Optional.ofNullable(user));
        List<PetInfoResponseDto> result = petService.getPets(new LoginUserInfo(email));
        Assertions.assertThat(result.size()).isEqualTo(2);
        Assertions.assertThat(result.stream().map(PetInfoResponseDto::getBreedName).collect(Collectors.toList()))
                .isEqualTo(Arrays.asList("진돗개", "진돗개"));
    }

    @Test
    @DisplayName("반려동물 아이디와 사용자 이메일을 통해 반려동물 상세정보를 조회할 수 있다.")
    void testGetPetDetail() throws JsonProcessingException {
        Breed breed = Breed.builder()
                .id(1L)
                .name("진돗개")
                .size(BreedSize.대형)
                .build();
        Pet pet = Pet.builder()
                .name("코코")
                .breed(breed)
                .build();
        given(petRepository.findByIdAndUserEmail(anyLong(), anyString()))
                .willReturn(Optional.ofNullable(pet));

        PetDetailResponseDto result = petService.getPetDetail(1L, new LoginUserInfo("test@test.com"));
        ObjectMapper objectMapper = new ObjectMapper();

        Assertions.assertThat(result.getName()).isEqualTo("코코");
        Assertions.assertThat(objectMapper.writeValueAsString(result.getBreedInfo())).isEqualTo(objectMapper.writeValueAsString(new BreedsInfoResponseDto(breed)));
    }
}
