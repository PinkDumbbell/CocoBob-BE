package com.pinkdumbell.cocobob.domain.product;


import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.pinkdumbell.cocobob.common.EmailUtil;
import com.pinkdumbell.cocobob.config.MailConfig;
import javax.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductService productService;

    @MockBean
    EmailUtil emailUtil;

    @MockBean
    MailConfig mailConfig;

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
    @DisplayName("사용자가 검색한 조건에 맞춰 올바른 결과들을 가져올 수 있다(size:10, page:1)")
    @WithMockUser("USER")
    void AAFCO_와_PAGE_SIZE_10_PAGE_NUMBER_1() throws Exception {
        //EXECUTE & EXPECT
        // 로그인 실행
        mvc.perform(get("/v1/products/search")
                .param("size", "10")
                .param("page", "1")
            .param("AAFCO", "true"))
            .andExpect(status().is2xxSuccessful())
            .andExpect(jsonPath("$.data.pageSize").value(10))
            .andExpect(jsonPath("$.data.pageNumber").value(1));
    }

    @Test
    @DisplayName("사용자가 검색한 조건에 맞춰 올바른 결과들을 가져올 수 있다(size:3, page:2)")
    @WithMockUser("USER")
    void AAFCO_와_PAGE_SIZE_3_PAGE_NUMBER_2() throws Exception {
        //EXECUTE & EXPECT
        // 로그인 실행
        mvc.perform(get("/v1/products/search")
                .param("size", "3")
                .param("page", "2")
                .param("AAFCO", "true"))
            .andExpect(status().is2xxSuccessful())
            .andExpect(jsonPath("$.data.pageSize").value(3))
            .andExpect(jsonPath("$.data.pageNumber").value(2));
    }
}