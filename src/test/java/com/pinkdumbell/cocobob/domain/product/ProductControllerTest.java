package com.pinkdumbell.cocobob.domain.product;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.pinkdumbell.cocobob.common.SlackService;
import com.pinkdumbell.cocobob.common.apilog.ApiLogInterceptor;
import com.pinkdumbell.cocobob.domain.auth.JwtTokenProvider;
import com.pinkdumbell.cocobob.domain.pet.PetService;
import com.pinkdumbell.cocobob.domain.product.dto.ProductSpecificSearchWithLikeDto;
import com.pinkdumbell.cocobob.exception.ErrorCode;
import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    MockMvc mvc;

    @MockBean
    JwtTokenProvider jwtTokenProvider;
    @MockBean
    ProductService productService;
    @MockBean
    PetService petService;
    @Autowired
    private WebApplicationContext context;
    @MockBean
    ApiLogInterceptor apiLogInterceptor;
    @MockBean
    SlackService slackService;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
            .addFilter(new CharacterEncodingFilter("UTF-8", true))
            .apply(springSecurity())
            .build();
        given(apiLogInterceptor.preHandle(
            any(HttpServletRequest.class),
            any(HttpServletResponse.class),
            any(Object.class)
        )).willReturn(true);
    }

    @Test
    @WithMockUser("USER")
    @DisplayName("사용자가 검색한 조건에 맞춰 올바른 결과들을 가져올 수 있다(size:10, page:1)")
    void AAFCO_PAGE_SIZE_10_PAGE_NUMBER_1() throws Exception {
        //EXECUTE
        MvcResult result = mvc.perform(get("/v1/products/search")
                .param("size", "10")
                .param("page", "1")
                .param("aaffco", "false"))
            .andReturn();

        //EXPECT
        Assertions.assertThat(result.getResponse().getStatus()).isEqualTo(200);
        Assertions.assertThat(result.getResponse().getContentAsString()).contains("상품 검색 성공");
    }

    @Test
    @WithMockUser("USER")
    @DisplayName("사용자가 검색한 조건에 맞춰 올바른 결과들을 가져올 수 있다(size:3, page:2)")
    void AAFCO_PAGE_SIZE_3_PAGE_NUMBER_2() throws Exception {

        //EXECUTE
        MvcResult result = mvc.perform(get("/v1/products/search")
                .param("size", "3")
                .param("page", "2")
                .param("aaffco", "true"))
            .andReturn();

        //EXPECT
        Assertions.assertThat(result.getResponse().getStatus()).isEqualTo(200);
        Assertions.assertThat(result.getResponse().getContentAsString()).contains("상품 검색 성공");
    }

    @Test
    @WithMockUser("USER")
    @DisplayName("사용자가 좋아요한 상품들을 가져왔습니다.")
    void get_user_liked_product_information() throws Exception {

        //EXECUTE
        MvcResult result = mvc.perform(get("/v1/products/wishlist"))
            .andReturn();

        //EXPECT
        Assertions.assertThat(result.getResponse().getStatus()).isEqualTo(200);
        Assertions.assertThat(result.getResponse().getContentAsString()).contains("찜한 상품 불러오기 성공");

    }

    @Test
    @WithMockUser("USER")
    @DisplayName("선택한 상품과 연관된 상품 정보들을 제공할 수 있다.")
    void get_relation_products() throws Exception {

        //given
        String productId = "1";

        //EXECUTE
        MvcResult result = mvc.perform(get("/v2/related-product")
                .param("productId", productId))
            .andReturn();

        //EXPECT
        Assertions.assertThat(result.getResponse().getStatus()).isEqualTo(200);
        Assertions.assertThat(result.getResponse().getContentAsString()).contains("연관 상품 가져오기 성공");

    }

    @Test
    @WithMockUser("USER")
    @DisplayName("사용자가 조건을 통해서 상품을 검색할 수 있고 또한, 좋아요 상태도 파악할 수 있다.")
    void search_product_by_condition_with_likes() throws Exception {

        //EXECUTE
        MvcResult result = mvc.perform(get("/v2/products/search")
            .param("ingredient", "beef", "mutton", "chicken", "duck", "turkey", "pork", "salmon",
                "hydrolyticBeef", "hydrolyticMutton", "hydrolyticChicken", "hydrolyticDuck",
                "hydrolyticTurkey", "hydrolyticPork", "hydrolyticSalmon")
            .param("allergyIngredient", "beef", "mutton", "chicken", "duck", "turkey", "pork",
                "salmon",
                "hydrolyticBeef", "hydrolyticMutton", "hydrolyticChicken", "hydrolyticDuck",
                "hydrolyticTurkey", "hydrolyticPork", "hydrolyticSalmon")
            .param("brands", "로얄캐닌")
            .param("types", "aged", "growing", "obesity", "pregnant")
            .param("codes", "101101")
            .param("keyword", "로얄캐닌")
            .param("aafco", "true")
            .param("sort", "ID,ASC")
            .param("page", "1")
            .param("size", "10")
        ).andReturn();

        //EXPECT
        Assertions.assertThat(result.getResponse().getStatus()).isEqualTo(200);
        Assertions.assertThat(result.getResponse().getContentAsString()).contains("상품 검색 성공");
    }

    @Test
    @WithMockUser("USER")
    @DisplayName("키워드를 통해서 상품 정보를 검색할 수 있다.")
    void correct_keyword_search() throws Exception {

        //EXECUTE
        MvcResult result = mvc.perform(get("/v1/products/keyword")
                .param("keyword", "로얄캐닌"))
            .andReturn();

        //EXPECT

        Assertions.assertThat(result.getResponse().getStatus()).isEqualTo(200);
        Assertions.assertThat(result.getResponse().getContentAsString()).contains("연관 검색어 불러오기 성공");
    }

    @Test
    @WithMockUser("USER")
    @DisplayName("키워드가 없으면 상품 정보를 없을 수 없고 에러 값을 얻게 된다.")
    void incorrect_keyword_search() throws Exception {

        //EXECUTE
        MvcResult result = mvc.perform(get("/v1/products/keyword"))
            .andReturn();

        //EXPECT
        Assertions.assertThat(result.getResponse().getStatus()).isEqualTo(400);
        Assertions.assertThat(result.getResponse().getContentAsString())
            .contains(ErrorCode.BAD_REQUEST.getMessage());
    }

    @Test
    @WithMockUser("USER")
    @DisplayName("상품 아이디를 통해서 상품에 대한 정보를 얻을 수 있다.")
    void find_product_info_by_product_id() throws Exception {

        //EXECUTE
        MvcResult result = mvc.perform(get("/v1/products/1")).andReturn();

        //EXPECT
        Assertions.assertThat(result.getResponse().getStatus()).isEqualTo(200);
        Assertions.assertThat(result.getResponse().getContentAsString()).contains("상품 가져오기 성공");

    }

    @Test
    @WithMockUser("USER")
    @DisplayName("반려동물 정보에 맞추어 추천된 상품을 얻을 수 있다.")
    void get_recommend_product_with_age() throws Exception {

        //given
        String[] types = {"aged", "pregnancy"};
        int page = 1;
        int size = 10;
        ProductSpecificSearchWithLikeDto testRequest = ProductSpecificSearchWithLikeDto.builder()
            .ingredient(
                Arrays.asList("beef", "mutton", "chicken", "duck", "turkey", "pork", "salmon",
                    "hydrolyticBeef", "hydrolyticMutton", "hydrolyticChicken", "hydrolyticDuck",
                    "hydrolyticTurkey", "hydrolyticPork", "hydrolyticSalmon"))
            .allergyIngredient(
                Arrays.asList("beef", "mutton", "chicken", "duck", "turkey", "pork", "salmon",
                    "hydrolyticBeef", "hydrolyticMutton", "hydrolyticChicken", "hydrolyticDuck",
                    "hydrolyticTurkey", "hydrolyticPork", "hydrolyticSalmon"))
            .brands(Arrays.asList("로얄캐닌"))
            .types(Arrays.asList("aged", "growing", "obesity", "pregnant"))
            .codes(Arrays.asList("101202"))
            .keyword("로얄캐닌")
            .aafco(true)
            .sort("ID,ASC")
            .page(page)
            .size(size)
            .build();

        given(petService.makeRecommendationWithAge(anyLong())).willReturn(testRequest);
        given(petService.makeRecommendationWithPregnancy(anyLong())).willReturn(testRequest);

        //EXECUTE & EXPECT
        for (int i = 0; i < 2; i++) {

            //EXECUTE
            MvcResult result = mvc.perform(
                    get(String.format("/v1/products/recommendation/%s", types[i]))
                        .param("petId", "1")
                        .param("size", String.format("%d", size))
                        .param("page", String.format("%d", page)))
                .andReturn();

            //EXPECT
            Assertions.assertThat(result.getResponse().getStatus()).isEqualTo(200);
            Assertions.assertThat(result.getResponse().getContentAsString())
                .contains("추천 상품 검색 성공");

        }

    }

    @Test
    @WithMockUser("USER")
    @DisplayName("반려동물 정보가 없으면 BAD_REQUEST_EROOR으로 응답할 수 있다.")
    void incorrect_request_recommend_product_with_age() throws Exception {
        //given
        String[] types = {"aged", "pregnancy"};
        int page = 1;
        int size = 10;
        ProductSpecificSearchWithLikeDto testRequest = ProductSpecificSearchWithLikeDto.builder()
            .ingredient(
                Arrays.asList("beef", "mutton", "chicken", "duck", "turkey", "pork", "salmon",
                    "hydrolyticBeef", "hydrolyticMutton", "hydrolyticChicken", "hydrolyticDuck",
                    "hydrolyticTurkey", "hydrolyticPork", "hydrolyticSalmon"))
            .allergyIngredient(
                Arrays.asList("beef", "mutton", "chicken", "duck", "turkey", "pork", "salmon",
                    "hydrolyticBeef", "hydrolyticMutton", "hydrolyticChicken", "hydrolyticDuck",
                    "hydrolyticTurkey", "hydrolyticPork", "hydrolyticSalmon"))
            .brands(Arrays.asList("로얄캐닌"))
            .types(Arrays.asList("aged", "growing", "obesity", "pregnant"))
            .codes(Arrays.asList("101202"))
            .keyword("로얄캐닌")
            .aafco(true)
            .sort("ID,ASC")
            .page(page)
            .size(size)
            .build();

        given(petService.makeRecommendationWithAge(anyLong())).willReturn(testRequest);
        given(petService.makeRecommendationWithPregnancy(anyLong())).willReturn(testRequest);

        //EXECUTE & EXPECT
        for (int i = 0; i < 2; i++) {

            //EXECUTE
            MvcResult result = mvc.perform(
                    get(String.format("/v1/products/recommendation/%s", types[i]))
                        .param("size", String.format("%d", size))
                        .param("page", String.format("%d", page)))
                .andReturn();

            //EXPECT
            Assertions.assertThat(result.getResponse().getStatus()).isEqualTo(400);
            Assertions.assertThat(result.getResponse().getContentAsString())
                .contains(ErrorCode.BAD_REQUEST.getMessage());

        }
       
    }


}