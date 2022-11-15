package com.pinkdumbell.cocobob.domain.user;

import com.pinkdumbell.cocobob.common.apilog.ApiLogInterceptor;
import com.pinkdumbell.cocobob.config.annotation.loginuser.LoginUserArgumentResolver;
import com.pinkdumbell.cocobob.domain.auth.AppleUtil;
import com.pinkdumbell.cocobob.domain.auth.GoogleOauthInfo;
import com.pinkdumbell.cocobob.domain.auth.JwtTokenProvider;
import com.pinkdumbell.cocobob.domain.auth.KakaoOauthInfo;
import com.pinkdumbell.cocobob.domain.pet.dto.SimplePetInfoDto;
import com.pinkdumbell.cocobob.domain.user.dto.LoginUserInfo;
import com.pinkdumbell.cocobob.domain.user.dto.UserGetResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.MethodParameter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerUnitTest {
    MockMvc mvc;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    UserController userController;
    @MockBean
    UserService userService;
    @MockBean
    JwtTokenProvider jwtTokenProvider;
    @MockBean
    LoginUserArgumentResolver loginUserArgumentResolver;
    @MockBean
    GoogleOauthInfo googleOauthInfo;
    @MockBean
    KakaoOauthInfo kakaoOauthInfo;
    @MockBean
    AppleUtil appleUtil;
    @MockBean
    ApiLogInterceptor apiLogInterceptor;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .apply(springSecurity())
                .build();
    }

    @Test
    void testGetUserInfo() throws Exception {
        String email = "test@test.com";
        LoginUserInfo loginUserInfo = new LoginUserInfo(email);
        given(userService.getUserInfo(any(LoginUserInfo.class))).willReturn(new UserGetResponseDto("name", email, new ArrayList<SimplePetInfoDto>(), null));
        given(loginUserArgumentResolver.resolveArgument(
                any(MethodParameter.class),
                any(ModelAndViewContainer.class),
                any(NativeWebRequest.class),
                any(WebDataBinderFactory.class)
        )).willReturn(loginUserInfo);
        given(apiLogInterceptor.preHandle(
                any(HttpServletRequest.class),
                any(HttpServletResponse.class),
                any(Object.class)
                )).willReturn(true);

        mvc.perform(get("/v1/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("name"))
                .andExpect(jsonPath("$.data.email").value(email))
                .andExpect(jsonPath("$.data.pets").value(new ArrayList<>()))
                .andExpect(jsonPath("$.data.representativeAnimalId").doesNotExist());
    }
}
