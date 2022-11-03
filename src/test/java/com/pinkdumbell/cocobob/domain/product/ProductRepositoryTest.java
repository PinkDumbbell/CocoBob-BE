package com.pinkdumbell.cocobob.domain.product;


import com.pinkdumbell.cocobob.config.TestConfig;
import com.pinkdumbell.cocobob.domain.product.dto.ProductKeywordDto;
import com.pinkdumbell.cocobob.domain.product.dto.ProductSimpleResponseDto;
import com.pinkdumbell.cocobob.domain.product.dto.ProductSpecificSearchDto;
import com.pinkdumbell.cocobob.domain.product.dto.ProductSpecificSearchWithLikeDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Arrays;
import java.util.List;

import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@DataJpaTest
@Import(TestConfig.class)
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    ProductSearchQueryDslImpl productSearchQueryDsl;

    @Autowired
    JPAQueryFactory jpaQueryFactory;

    @PersistenceContext
    EntityManager em;

    @BeforeEach
    void init() {

        for (Long dummyIndex = 1L; dummyIndex < 30L; dummyIndex++) {
            Product dummyProduct = Product
                .builder()
                .code("101101")
                .category("건식")
                .name("더리얼밀그레인프리닭고기60g6개")
                .brand("하림펫푸드")
                .description("하림 사료는 정말 맛있어요!")
                .price(13260)
                .thumbnail(
                    "https://cocobob-storage.s3.ap-northeast-2.amazonaws.com/product_img_resize/%5B2%2B1%5D%20%ED%95%98%EB%A6%BC%ED%8E%AB%ED%91%B8%EB%93%9C%20%EB%8D%94%EB%A6%AC%EC%96%BC%20%EB%B0%80%20%EA%B7%B8%EB%A0%88%EC%9D%B8%ED%94%84%EB%A6%AC%20%EB%8B%AD%EA%B3%A0%EA%B8%B0%2060g%206%EA%B0%9C.jpg")
                .productImage(
                    "https://cocobob-storage.s3.ap-northeast-2.amazonaws.com/product_img/%5B2%2B1%5D%20%ED%95%98%EB%A6%BC%ED%8E%AB%ED%91%B8%EB%93%9C%20%EB%8D%94%EB%A6%AC%EC%96%BC%20%EB%B0%80%20%EA%B7%B8%EB%A0%88%EC%9D%B8%ED%94%84%EB%A6%AC%20%EB%8B%AD%EA%B3%A0%EA%B8%B0%2060g%206%EA%B0%9C.jpg")
                .productDetailImage(
                    "https://cocobob-storage.s3.ap-northeast-2.amazonaws.com/product_detail/%5B2%2B1%5D%20%ED%95%98%EB%A6%BC%ED%8E%AB%ED%91%B8%EB%93%9C%20%EB%8D%94%EB%A6%AC%EC%96%BC%20%EB%B0%80%20%EA%B7%B8%EB%A0%88%EC%9D%B8%ED%94%84%EB%A6%AC%20%EB%8B%AD%EA%B3%A0%EA%B8%B0%2060g%206%EA%B0%9C.jpg")
                .brandImage(
                    "https://cocobob-storage.s3.ap-northeast-2.amazonaws.com/brand_image/%ED%95%98%EB%A6%BC%ED%8E%AB%ED%91%B8%EB%93%9C.jpg")
                .protein(30.93f)
                .amountOfProteinPerMcal(30.93)
                .fat(30.93f)
                .amountOfFatPerMcal(30.93)
                .fiber(30.93f)
                .amountOfFiberPerMcal(30.93)
                .mineral(30.93f)
                .amountOfFiberPerMcal(30.93)
                .calcium(30.93f)
                .amountOfCalciumPerMcal(30.93)
                .phosphorus(30.93f)
                .amountOfPhosphorusPerMcal(30.93)
                .moisture(30.93f)
                .kcalPerKg(30.93)
                .isAAFCOSatisfied(true)
                .beef(true)
                .mutton(true)
                .chicken(true)
                .duck(true)
                .turkey(true)
                .meat(true)
                .salmon(true)
                .hydrolyticBeef(true)
                .hydrolyticMutton(true)
                .hydrolyticChicken(true)
                .hydrolyticDuck(true)
                .hydrolyticTurkey(true)
                .hydrolyticMeat(true)
                .hydrolyticSalmon(true)
                .aged(true)
                .growing(true)
                .pregnant(true)
                .obesity(true)
                .build();
            productRepository.save(dummyProduct);
        }
    }

    @AfterEach
    public void teardown() {
        productRepository.deleteAll();
        em.createNativeQuery("ALTER TABLE product ALTER COLUMN `product_id` RESTART WITH 1")
            .executeUpdate();
    }

    @Test
    @DisplayName("사용자가 요청한 상품 코드로 상품의 상세 정보 조회가 가능해야 한다.")
    void findProductDetailById() {

        //ProductId 10을 가지고 있는 상품 조회
        Optional<Product> result = productRepository.findById(10L);
        Assertions.assertThat(result.isPresent()).isEqualTo(true);

    }

    @Test
    @DisplayName("모든 검색 조건과 모든 정렬을 통해서 동적 쿼리를 실행할 수 있다.")
    void search_condition_by_id_asc() {

        //when
        int page = 1;
        int size = 10;
        String[] sort_base = {"ID,ASC", "ID,DESC", "PRICE,ASC", "PRICE,DESC", "LIKE,ASC",
            "LIKE,DESC"};
        for (int i = 0; i < 6; i++) {

            ProductSpecificSearchWithLikeDto productSpecificSearchWithLikeDto = ProductSpecificSearchWithLikeDto.builder()
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
                .sort(sort_base[i])
                .page(page)
                .size(size)
                .build();

            //given
            Pageable pageable = PageRequest.of(productSpecificSearchWithLikeDto.getPage(),
                productSpecificSearchWithLikeDto.getSize());

            //EXECUTE
            PageImpl<ProductSimpleResponseDto> result = productRepository.findAllWithLikes(
                productSpecificSearchWithLikeDto, 1L);

            //EXPECTED
            Assertions.assertThat(result.getPageable()).isEqualTo(pageable);

        }
    }

    @Test
    @DisplayName("키워드를 통해서 상품을 검색할 수 있다.")
    void findProductNamesByKeyword() {

        //given
        String keyword = "로얄캐닌";

        //EXECUTE
        List<ProductKeywordDto> result = productRepository.findProductNamesByKeyword(
            keyword);

        //EXPECT
        Assertions.assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("여러 상품 id들을 얻었을 때 상품을 조회할 수 있다")
    void findAllRelatedProductsById() {
        //given
        List<Long> productIds = List.of(0L, 1L, 3L);
        Long userId = 0L;

        //EXECUTE
        PageImpl<ProductSimpleResponseDto> result = productRepository.findAllRelatedProductsById(
            productIds, userId);

        //EXPECT
        Assertions.assertThat(result).isNotNull();

    }

    @Test
    @DisplayName("JPA에서 제공하는 기본 specification으로 동적쿼리를 사용할 수 있다.")
    void jpa_specification_dynamic_query() {
        //given
        ProductSpecificSearchDto request = ProductSpecificSearchDto.builder()
            .code("101101")
            .name("더리얼밀그레인프리닭고기60g6개")
            .brand("하림펫푸드")
            .description("소고기 함유")
            .aafco(true)
            .beef(true)
            .mutton(true)
            .chicken(true)
            .duck(true)
            .turkey(true)
            .pork(true)
            .salmon(true)
            .hydrolyticBeef(true)
            .hydrolyticMutton(true)
            .hydrolyticChicken(true)
            .hydrolyticDuck(true)
            .hydrolyticTurkey(true)
            .hydrolyticPork(true)
            .hydrolyticSalmon(true)
            .aged(true)
            .growing(true)
            .pregnant(true)
            .obesity(true)
            .build();

        //EXECUTE
        List<Product> result = productRepository.findAll(
            ProductSearchSpecification.makeProductSpecification(request));

        //EXPECT
        Assertions.assertThat(result).isNotNull();

    }


}