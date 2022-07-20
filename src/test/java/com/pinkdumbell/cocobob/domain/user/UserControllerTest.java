package com.pinkdumbell.cocobob.domain.user;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pinkdumbell.cocobob.domain.auth.JwtTokenProvider;
import com.pinkdumbell.cocobob.domain.user.dto.UserCreateRequestDto;
import com.pinkdumbell.cocobob.domain.user.dto.UserCreateResponseDto;

import com.pinkdumbell.cocobob.domain.user.dto.UserLoginRequestDto;
import javax.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;


@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private WebApplicationContext context;
    MockMvc mvc;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
            .addFilter( new CharacterEncodingFilter( "UTF-8", true ) )
            .apply(springSecurity())
            .build();
    }


    @Test
    @DisplayName("사용자가 회원가입 후 올바른 토큰을 얻을 수 있다.")
    void 회원가입후_로그인을_하였을_경우() throws Exception {
        //SET UP
        final String username = "TESTER";
        final String email = "test@test.com";
        final String password = "password";

        //회원가입
        UserCreateRequestDto requestDto = UserCreateRequestDto.builder()
            .username(username)
            .email(email)
            .password(password)
            .build();
        UserCreateResponseDto userCreateResponseDto = userService.signup(requestDto);

        //로그인 요청 정보 초기화
        UserLoginRequestDto userLoginRequestDto = UserLoginRequestDto.builder()
            .email(email)
            .password(password)
            .build();

        //EXECUTE & EXPECT
        // 로그인 실행
        mvc.perform(post("/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(userLoginRequestDto)))
            .andExpect(status().is2xxSuccessful())
            .andDo(print());
    }

    @Test
    @DisplayName("사용자가 회원가입을 하지 않고 로그인 할 수 없다.")
    void 회원가입_없이_로그인을_하였을_경우() throws Exception {
        //SET UP
        final String username = "TESTER";
        final String email = "test@test.com";
        final String password = "password";

        //로그인 요청 정보 초기화
        UserLoginRequestDto userLoginRequestDto = UserLoginRequestDto.builder()
                .email(email)
                .password(password)
                .build();

        //EXECUTE & EXPECT
        // 로그인 실행
        mvc.perform(post("/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userLoginRequestDto)))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }

}