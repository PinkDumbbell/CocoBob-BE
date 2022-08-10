package com.pinkdumbell.cocobob.domain.daily;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.pinkdumbell.cocobob.domain.auth.JwtTokenProvider;
import java.util.Arrays;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

@WebMvcTest(DailyController.class)
class DailyControllerTest {

    MockMvc mvc;

    @MockBean
    JwtTokenProvider jwtTokenProvider;

    @MockBean
    DailyService mockDailyService;

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
    @DisplayName("날짜가 없으면 데일리 기록을 생성 할 수 없다.")
    void 기록_날짜가_없으면_기록을_생성할_수_없다() throws Exception {

        // Execute
        MvcResult result = mvc.perform(post("/v1/dailys/pets/1")
                .contentType(MediaType.MULTIPART_FORM_DATA))
            .andReturn();

        //Expect
        Assertions.assertThat(result.getResponse().getStatus()).isEqualTo(400);
        Assertions.assertThat(result.getResponse().getContentAsString())
            .contains("필수 입력 항목(날짜)가 없습니다.");
    }

    @Test
    @WithMockUser("USER")
    @DisplayName("올바른 요청에는 데일리 기록을 정상적으로 생성 할 수 있다.")
    void 올바른_요청에는_데일리_기록을_정상적으로_생성할_수_있다() throws Exception {

        // Execute
        MvcResult result = mvc.perform(post("/v1/dailys/pets/1")
                .param("date", "2022-08-08")
                .param("note", "코코가 농사를 지으면 코코팜?")
                .contentType(MediaType.MULTIPART_FORM_DATA))
            .andReturn();

        //Expect
        Assertions.assertThat(result.getResponse().getStatus()).isEqualTo(200);
        Assertions.assertThat(result.getResponse().getContentAsString())
            .contains("데일리 기록을 생성 하는데 성공하였습니다.");
    }

    @Test
    @WithMockUser("USER")
    @DisplayName("올바른 요청에는 데일리 기록을 정상적으로 조회 할 수 있다.")
    void 올바른_요청에는_데일리_기록을_정상적으로_조회_가능하다() throws Exception {

        //Execute
        MvcResult result = mvc.perform(get("/v1/dailys/pets/1")
                .param("startDate", "2022-01-01")
                .param("lastDate", "2022-12-31"))
            .andReturn();

        //Expect
        Assertions.assertThat(result.getResponse().getStatus()).isEqualTo(200);
        Assertions.assertThat(result.getResponse().getContentAsString())
            .contains("데일리 기록을 불러오는데 성공하였습니다.");
    }

    @Test
    @WithMockUser("USER")
    @DisplayName("시작 날짜가 없으면 데일리 기록을 정상적으로 조회 할 수 없다.")
    void 시작날짜가_없으면_데일리_기록을_정상적으로_조회_불가하다() throws Exception {

        //Execute
        MvcResult result = mvc.perform(get("/v1/dailys/pets/1")
                .param("lastDate", "2022-12-31"))
            .andReturn();

        //Expect
        Assertions.assertThat(result.getResponse().getStatus()).isEqualTo(400);
        Assertions.assertThat(result.getResponse().getContentAsString())
            .contains("필수 입력 항목(시작 날짜)가 없습니다.");
    }
    @Test
    @WithMockUser("USER")
    @DisplayName("종료 날짜가 없으면 데일리 기록을 정상적으로 조회 할 수 없다.")
    void 종료날짜가_없으면_데일리_기록을_정상적으로_조회_불가하다() throws Exception {

        //Execute
        MvcResult result = mvc.perform(get("/v1/dailys/pets/1")
                .param("startDate", "2022-01-01"))
            .andReturn();

        //Expect
        Assertions.assertThat(result.getResponse().getStatus()).isEqualTo(400);
        Assertions.assertThat(result.getResponse().getContentAsString())
            .contains("필수 입력 항목(종료 날짜)가 없습니다.");
    }
    @Test
    @WithMockUser("USER")
    @DisplayName("날짜가 없으면 데일리 기록을 정상적으로 조회 할 수 없다.")
    void 날짜가_없으면_데일리_기록을_정상적으로_조회_불가하다() throws Exception {

        //Execute
        MvcResult result = mvc.perform(get("/v1/dailys/pets/1"))
            .andReturn();

        //Expect
        Assertions.assertThat(result.getResponse().getStatus()).isEqualTo(400);
        Assertions.assertThat(result.getResponse().getContentAsString())
            .contains(Arrays.asList("필수 입력 항목(시작 날짜)가 없습니다.","필수 입력 항목(종료 날짜)가 없습니다."));
    }

    @Test
    void getSimpleDaily() {
    }

    @Test
    void updateDailyRecord() {
    }

    @Test
    void deleteDailyRecord() {
    }

    @Test
    void getDailyDetailRecord() {
    }

    @Test
    void deleteDailyImage() {
    }
}