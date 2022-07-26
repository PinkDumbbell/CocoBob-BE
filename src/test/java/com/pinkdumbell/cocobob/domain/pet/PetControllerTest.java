package com.pinkdumbell.cocobob.domain.pet;


import com.pinkdumbell.cocobob.domain.auth.JwtTokenProvider;
import com.pinkdumbell.cocobob.domain.pet.dto.PetCreateRequestDto;
import com.pinkdumbell.cocobob.domain.pet.dto.PetCreateResponseDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;


import java.time.LocalDate;
import java.util.Arrays;

import static org.mockito.BDDMockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@WebMvcTest(PetController.class)
class PetControllerTest {
    MockMvc mvc;

    @MockBean
    JwtTokenProvider jwtTokenProvider;
    @MockBean
    PetService petService;
    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser("USER")
    @DisplayName("PetCreateRequestDto에 필수적인 값이 입력되지 않은 상황에 대한 Validation 테스트를 진행한다.")
    void testValidationForPetCreateRequestDtoAboutNull() throws Exception {
        MvcResult result = mvc.perform(post("/v1/pets")
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andReturn();

        Assertions.assertThat(result.getResponse().getStatus()).isEqualTo(400);
        Assertions.assertThat(result.getResponse().getContentAsString())
                .contains(Arrays.asList(
                        "반려동물의 이름이 필요합니다.",
                        "반려동물의 성별이 필요합니다.",
                        "반려동물의 나이(개월 수)가 필요합니다.",
                        "반려동물의 중성화 여부가 필요합니다.",
                        "반려동물의 임신/수유 여부가 필요합니다.",
                        "반려동물의 몸무게가 필요합니다.",
                        "반려동물의 활동수준이 필요합니다.",
                        "반려동물 견종에 대한 정보가 필요합니다."
                        ));
    }

    @Test
    @WithMockUser("USER")
    @DisplayName("PetCreateRequestDto에 부적절한 값이 입력된 상황에 대한 Validation 테스트를 진행한다.")
    void testValidationForPetCreateRequestDtoWithInvalidValue() throws Exception {
        LocalDate birthday = LocalDate.parse("3000-12-31");
        Float bodyWeight = -1.4F;
        Integer age = 0;
        Integer activityLevel = 10;
        Long breedId = -1L;

        MvcResult result = mvc.perform(post("/v1/pets")
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .param("birthday", String.valueOf(birthday))
                        .param("age", String.valueOf(age))
                        .param("bodyWeight", String.valueOf(bodyWeight))
                        .param("activityLevel", String.valueOf(activityLevel))
                        .param("breedId", String.valueOf(breedId))
                        .flashAttr("PetCreateRequestDto", PetCreateRequestDto.builder()
                                .birthday(birthday)
                                .bodyWeight(bodyWeight)
                                .activityLevel(activityLevel)
                                .breedId(breedId)
                                .build())
                )
                .andReturn();

        Assertions.assertThat(result.getResponse().getStatus()).isEqualTo(400);
        Assertions.assertThat(result.getResponse().getContentAsString())
                .contains(Arrays.asList(
                        "나이(개월 수)는 0보다 커야합니다.",
                        "생일은 과거 또는 현재의 날짜여야 합니다.",
                        "몸무게는 0보다 커야 합니다.",
                        "활동 수준은 1~5의 값이어야 합니다.",
                        "견종 아이디는 0보다 커야합니다."
                ));
    }

    @Test
    @WithMockUser("USER")
    @DisplayName("올바른 값이 요청을 통해 들어왔을 때 컨트롤러의 반환값을 테스트")
    void testReturnOfControllerWithValidInputValues() throws Exception {
        Float bodyWeight = 5.4F;
        Integer age = 20;
        Integer activityLevel = 3;
        Long breedId = 1L;
        Boolean isSpayed = true;
        Boolean isPregnant = false;
        PetSex sex = PetSex.FEMALE;
        String name = "코코";

        given(petService.register(any(PetCreateRequestDto.class)))
                .willReturn(PetCreateResponseDto.builder()
                        .petId(1L)
                        .build());

        mvc.perform(post("/v1/pets")
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .param("name", name)
                        .param("sex", String.valueOf(sex))
                        .param("isPregnant", String.valueOf(isPregnant))
                        .param("isSpayed", String.valueOf(isSpayed))
                        .param("breedId", String.valueOf(breedId))
                        .param("activityLevel", String.valueOf(activityLevel))
                        .param("age", String.valueOf(age))
                        .param("bodyWeight", String.valueOf(bodyWeight))
                )
                .andExpect(jsonPath("$.petId").value(1L))
                .andExpect(status().isOk());
    }
}