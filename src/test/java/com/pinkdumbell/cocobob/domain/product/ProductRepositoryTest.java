package com.pinkdumbell.cocobob.domain.product;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@Transactional
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @MockBean
    ProductSearchQueryDslImpl productSearchQueryDsl;

    @BeforeEach
    void init() {
        for (int dummyIndex = 1; dummyIndex < 30; dummyIndex++) {
            Product dummyProduct = new Product();
            productRepository.save(dummyProduct);
        }
    }

    @Test
    @DisplayName("사용자가 요청한 상품 코드로 상품의 상세 정보 조회가 가능해야 한다.")
    void
    findProductDetailById() {

        //ProductId 10을 가지고 있는 상품 조회
        Product result = productRepository.findById(10L).get();

        Assertions.assertThat(result.getId()).isEqualTo(10L);
    }
}