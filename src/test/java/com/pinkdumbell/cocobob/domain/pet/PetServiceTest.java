package com.pinkdumbell.cocobob.domain.pet;

import com.pinkdumbell.cocobob.domain.pet.breed.Breed;
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
    PetRepository petRepository;
    @Mock
    UserRepository userRepository;

    @Test
    @DisplayName("사용자의 반려트물 가져오는 메서드 테스")
    void testGetPets() {
        String email = "test@test.com";
        User user = User.builder()
                .email(email)
                .build();
        Breed breed = Breed.builder()
                .name("진돗개")
                .build();
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(petRepository.findAllByUserIdWithBreed(any())).thenReturn(
                List.of(
                        Pet.builder()
                                .user(user)
                                .breed(breed)
                                .build(),
                        Pet.builder()
                                .user(user)
                                .breed(breed)
                                .build()
                )
        );
        List<PetInfoResponseDto> result = petService.getPets(new LoginUserInfo(email));
        Assertions.assertThat(result.size()).isEqualTo(2);
        Assertions.assertThat(result.stream().map(PetInfoResponseDto::getBreedName).collect(Collectors.toList()))
                .isEqualTo(Arrays.asList("진돗개", "진돗개"));
    }
}
