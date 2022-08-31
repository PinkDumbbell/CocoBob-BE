package com.pinkdumbell.cocobob.domain.user;

import com.pinkdumbell.cocobob.domain.pet.Pet;
import com.pinkdumbell.cocobob.domain.pet.PetRepository;
import com.pinkdumbell.cocobob.domain.user.dto.LoginUserInfo;
import com.pinkdumbell.cocobob.domain.user.dto.RepresentativePetUpdateDto;
import com.pinkdumbell.cocobob.domain.user.dto.UserGetResponseDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceUnitTest {
    @InjectMocks
    UserService userService;
    @Mock
    UserRepository userRepository;
    @Mock
    PetRepository petRepository;

    @Test
    void testGetUserInfo() {
        String email = "test@test.com";
        given(userRepository.findUserByEmailWithPet(email))
                .willReturn(Optional.ofNullable(User.builder()
                        .username("name")
                        .email(email)
                        .build()));

        UserGetResponseDto result = userService.getUserInfo(new LoginUserInfo(email));
        Assertions.assertThat(result.getEmail()).isEqualTo(email);
        Assertions.assertThat(result.getName()).isEqualTo("name");
        Assertions.assertThat(result.getPets().size()).isEqualTo(0);
        Assertions.assertThat(result.getRepresentativeAnimalId()).isNull();
    }

    @Test
    @DisplayName("대표반려동물 변경 테스트")
    void testUpdateRepresentativePet() {
        String email = "test@test.com";
        User user = User.builder()
                .email(email)
                .build();
        RepresentativePetUpdateDto requestDto = RepresentativePetUpdateDto.builder()
                .representativePetId(1L)
                .build();
        given(userRepository.findByEmail(email))
                .willReturn(Optional.ofNullable(user));
        given(petRepository.findById(any()))
                .willReturn(Optional.ofNullable(Pet.builder()
                                .id(requestDto.getRepresentativePetId())
                        .build()));

        userService.updateRepresentativePet(requestDto, new LoginUserInfo(email));

        Assertions.assertThat(user.getRepresentativePetId()).isEqualTo(requestDto.getRepresentativePetId());
    }
}
