package com.pinkdumbell.cocobob.domain.user;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pinkdumbell.cocobob.domain.auth.JwtTokenProvider;
import com.pinkdumbell.cocobob.domain.user.dto.UserCreateRequestDto;
import com.pinkdumbell.cocobob.domain.user.dto.UserCreateResponseDto;

import com.pinkdumbell.cocobob.domain.user.dto.UserLoginRequestDto;
import com.pinkdumbell.cocobob.domain.user.dto.UserLoginResponseDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import javax.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${spring.jwt.secretKey}")
    private String secretKey;


    @Autowired
    private WebApplicationContext context;
    MockMvc mvc;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
            .addFilter(new CharacterEncodingFilter("UTF-8", true))
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
            .andExpect(jsonPath("$.accessToken", notNullValue())) //토큰 값들이 정상적으로 전달되었는지
            .andExpect(jsonPath("$.refreshToken", notNullValue()))
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

    @Test
    @DisplayName("발급받은 access Token으로 다른 리소스에 접근할 수 있다.")
    void 유효한_토큰으로_접근이_하는_경우() throws Exception {
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

        UserLoginResponseDto userLoginResponseDto = userService.login(userLoginRequestDto);
        System.out.println(userLoginResponseDto.getAccessToken());
        //EXECUTE & EXPECT
        // 로그인 후 접근 가능한 페이지 접근
        mvc.perform(get("/hello")
                .header("Authorization", "Bearer " + userLoginResponseDto.getAccessToken()))
            .andExpect(status().is2xxSuccessful())
            .andDo(print());
    }

    @Test
    @DisplayName("만료된 access Token으로는 다른 리소스에 접근할 수 없다.")
    void 유효하지않은_토큰으로_접근이_하는_경우() throws Exception {
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

        UserLoginResponseDto userLoginResponseDto = userService.login(userLoginRequestDto);

        // 유효하지 않은 토큰 생성

        String invalidToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QHRlc3QuY29tIiwiaWF0IjoxNjU4ODIzMDY3LCJleHAiOjE2NTg4MjQ4Njd9.EzRbUv390S78fQbbAHE87bA-M5QOIpZWQTAFD3z2Zrk";

        //EXECUTE & EXPECT
        // 로그인 후 접근 가능한 페이지 접근
        mvc.perform(get("/hello")
                .header("Authorization", "Bearer " + invalidToken))
            .andExpect(status().is4xxClientError())
            .andDo(print());
    }

    @Test
    @DisplayName("refresh Token을 통해 새로운 access Token 발행")
    void refresh_Token을_통해_새로운_토큰_발행() throws Exception {
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

        UserLoginResponseDto userLoginResponseDto = userService.login(userLoginRequestDto);

        //EXECUTE & EXPECT
        // 토큰 재발행
        mvc.perform(get("/v1/users/token")
                .header("accessToken", "Bearer " + userLoginResponseDto.getAccessToken())
                .header("refreshToken", "Bearer " + userLoginResponseDto.getRefreshToken())
            ).andExpect(status().is2xxSuccessful())
            .andExpect(jsonPath("$.accessToken", notNullValue())) //토큰 값들이 정상적으로 전달되었는지
            .andExpect(jsonPath("$.refreshToken", notNullValue()))
            .andDo(print());
    }

    @Test
    @DisplayName("유효하지 않은 refresh Token에 의해 access Token 발행 방지")
    void 유효하지_않은_refresh_Token인_경우_발행_금지() throws Exception {
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

        UserLoginResponseDto userLoginResponseDto = userService.login(userLoginRequestDto);

        //유효하지 않은 리프레시 토큰 생성
        String invalidRefreshToken = "eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2NTg4Mjc0NTQsImV4cCI6MTY1OTQzMjI1NH0.jlFgztcUQ1gMAxQpdpaf6drx-JBUsSGHCgr1FNFkWos";

        //EXECUTE & EXPECT
        // 토큰 재발행
        mvc.perform(get("/v1/users/token")
                .header("accessToken", "Bearer " + userLoginResponseDto.getAccessToken())
                .header("refreshToken", "Bearer " + invalidRefreshToken)
            ).andExpect(status().is4xxClientError())
            .andDo(print());
    }

    @Test
    @DisplayName("만료된 refresh Token에 의해 access Token 발행 방지")
    void 만료된_refresh_Token인_경우_발행_금지() throws Exception {
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

        UserLoginResponseDto userLoginResponseDto = userService.login(userLoginRequestDto);

        //유효하지 않은 리프레시 토큰 생성
        Date now = new Date();
        String invalidRefreshToken = Jwts.builder()
            .setIssuedAt(now)
            .setExpiration(new Date(now.getTime() + 1000)) //만료된 토큰
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact();

        //EXECUTE & EXPECT
        // 토큰 재발행
        mvc.perform(get("/v1/users/token")
                .header("accessToken", "Bearer " + userLoginResponseDto.getAccessToken())
                .header("refreshToken", "Bearer " + invalidRefreshToken)
            ).andExpect(status().is4xxClientError())
            .andDo(print());
    }

    @Test
    @DisplayName("다른 환경에서 로그인 시 로그인후 기존 로그인 해제")
    void 중복_로그인_확인() throws Exception {
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
        // 1차 로그인 실행
        mvc.perform(post("/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(userLoginRequestDto)))
            .andExpect(status().is2xxSuccessful())
            .andExpect(jsonPath("$.accessToken", notNullValue())) //토큰 값들이 정상적으로 전달되었는지
            .andExpect(jsonPath("$.refreshToken", notNullValue()))
            .andDo(print());
        // 2차 로그인 실행
        mvc.perform(post("/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(userLoginRequestDto)))
            .andExpect(status().is2xxSuccessful())
            .andExpect(jsonPath("$.accessToken", notNullValue())) //토큰 값들이 정상적으로 전달되었는지
            .andExpect(jsonPath("$.refreshToken", notNullValue()))
            .andDo(print());
    }

    @Test
    @DisplayName("로그 아웃 후 access token으로 접근")
    void 로그아웃후_accessToken_접근() throws Exception {
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

        UserLoginResponseDto userLoginResponseDto = userService.login(userLoginRequestDto);

        // 로그인 후 접근 가능한 페이지 접근
        mvc.perform(get("/hello")
                .header("Authorization", "Bearer " + userLoginResponseDto.getAccessToken()))
            .andExpect(status().is2xxSuccessful())
            .andDo(print());

        //EXECUTE & EXPECT
        //로그 아웃
        mvc.perform(delete("/v1/users")
                .header("Authorization", "Bearer " + userLoginResponseDto.getAccessToken()))
            .andExpect(status().is2xxSuccessful())
            .andDo(print());

        //로그 아웃 후 재접근
        mvc.perform(get("/hello")
                .header("Authorization", "Bearer " + userLoginResponseDto.getAccessToken()))
            .andExpect(status().is4xxClientError())
            .andDo(print());
    }

    @Test
    @DisplayName("로그 아웃 후 재로그인")
    void 로그아웃후_재로그인() throws Exception {
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

        UserLoginResponseDto userLoginResponseDto = userService.login(userLoginRequestDto);

        // 로그인 후 접근 가능한 페이지 접근
        mvc.perform(get("/hello")
                .header("Authorization", "Bearer " + userLoginResponseDto.getAccessToken()))
            .andExpect(status().is2xxSuccessful())
            .andDo(print());

        //EXECUTE & EXPECT
        //로그 아웃
        mvc.perform(delete("/v1/users")
                .header("Authorization", "Bearer " + userLoginResponseDto.getAccessToken()))
            .andExpect(status().is2xxSuccessful())
            .andDo(print());

        UserLoginResponseDto userLoginResponseDto_retry = userService.login(userLoginRequestDto);
        //로그인 후 재접근
        mvc.perform(get("/hello")
                .header("Authorization", "Bearer " + userLoginResponseDto_retry.getAccessToken()))
            .andExpect(status().is2xxSuccessful())
            .andDo(print());
    }


}