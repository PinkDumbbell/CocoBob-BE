package com.pinkdumbell.cocobob.domain.user;

import com.pinkdumbell.cocobob.domain.auth.JwtTokenProvider;
import com.pinkdumbell.cocobob.domain.user.dto.UserCreateRequestDto;
import com.pinkdumbell.cocobob.domain.user.dto.UserCreateResponseDto;
import com.pinkdumbell.cocobob.domain.user.dto.UserPasswordRequestDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Transactional
@SpringBootTest
class UserServiceTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    @DisplayName("주어진 사용자 정보를 통해 회원가입을 할 수 있다.")
    void testSignup() {
        UserCreateRequestDto requestDto = UserCreateRequestDto.builder()
                .username("테스터")
                .email("test@test.com")
                .password("password")
                .build();
        UserCreateResponseDto userSignedUp = userService.signup(requestDto);

        Assertions.assertThat(userSignedUp.getEmail()).isEqualTo(requestDto.getEmail());
        Assertions.assertThat(userSignedUp.getUsername()).isEqualTo(requestDto.getUsername());
    }

    @Test
    @DisplayName("사용자 이메일로 새로 생성된 비밀번호를 전송하고 정상적으로 변경되었는지 확인한다.")
    void sendNewPassword() {
        UserCreateRequestDto requestDto = UserCreateRequestDto.builder()
                .username("장병희")
                .email("jmkabc31@ajou.ac.kr")
                .password("password")
                .build();
        UserCreateResponseDto userSignedUp = userService.signup(requestDto);

        UserPasswordRequestDto userPasswordRequestDto = UserPasswordRequestDto.builder().email(requestDto.getEmail()).build();
        String newPassword = userService.sendNewPassword(userPasswordRequestDto);

        User user = userRepository.findByEmail(requestDto.getEmail()).get();

        Assertions.assertThat(bCryptPasswordEncoder.encode(newPassword)).isEqualTo(user.getPassword());
    }

 
}