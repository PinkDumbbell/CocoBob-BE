package com.pinkdumbell.cocobob.domain.user;

import com.pinkdumbell.cocobob.common.EmailUtil;
import com.pinkdumbell.cocobob.common.dto.EmailSendResultDto;
import com.pinkdumbell.cocobob.config.MailConfig;
import com.pinkdumbell.cocobob.domain.auth.JwtTokenProvider;
import com.pinkdumbell.cocobob.domain.user.dto.UserCreateRequestDto;
import com.pinkdumbell.cocobob.domain.user.dto.UserCreateResponseDto;
import com.pinkdumbell.cocobob.domain.user.dto.UserEmailRequestDto;
import com.pinkdumbell.cocobob.domain.user.dto.UserPasswordRequestDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.mockito.BDDMockito.*;

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

    @MockBean
    EmailUtil emailUtil;

    @MockBean
    MailConfig mailConfig;

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
        userService.signup(requestDto);

        given(emailUtil.sendEmail(any(String.class), any(String.class), any(String.class)))
                .willReturn(EmailSendResultDto.builder().status(HttpStatus.OK.value()).build());

        UserEmailRequestDto userEmailRequestDto = UserEmailRequestDto.builder().email(requestDto.getEmail()).build();
        String newPassword = userService.sendNewPassword(userEmailRequestDto);

        User user = userRepository.findByEmail(requestDto.getEmail()).get();

        Assertions.assertThat(bCryptPasswordEncoder.matches(newPassword, user.getPassword()));
    }

}