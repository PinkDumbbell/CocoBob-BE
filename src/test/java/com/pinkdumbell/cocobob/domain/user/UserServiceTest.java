package com.pinkdumbell.cocobob.domain.user;

import com.pinkdumbell.cocobob.domain.user.dto.UserCreateRequestDto;
import com.pinkdumbell.cocobob.domain.user.dto.UserCreateResponseDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

@Transactional
@SpringBootTest
class UserServiceTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;

    @Test
    void signup() {
        UserCreateRequestDto requestDto = UserCreateRequestDto.builder()
                .username("테스터")
                .email("test@test.com")
                .password("password")
                .build();
        UserCreateResponseDto userSignedUp = userService.signup(requestDto);

        Assertions.assertThat(userSignedUp.getEmail()).isEqualTo(requestDto.getEmail());
        Assertions.assertThat(userSignedUp.getUsername()).isEqualTo(requestDto.getUsername());
    }
}