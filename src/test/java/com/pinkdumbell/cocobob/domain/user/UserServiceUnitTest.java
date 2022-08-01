package com.pinkdumbell.cocobob.domain.user;

import com.pinkdumbell.cocobob.domain.user.dto.LoginUserInfo;
import com.pinkdumbell.cocobob.domain.user.dto.UserGetResponseDto;
import org.assertj.core.api.Assertions;
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
}
