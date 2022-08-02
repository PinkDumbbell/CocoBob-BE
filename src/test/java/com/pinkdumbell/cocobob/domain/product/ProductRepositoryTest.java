package com.pinkdumbell.cocobob.domain.product;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@Transactional
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void init() {
        for (int dummyIndex = 1; dummyIndex < 30; dummyIndex++) {
            Product dummyProduct = new Product();
            productRepository.save(dummyProduct);
        }
    }

    @Test
    @DisplayName("사용자가 설정한 페이지 사이즈, 페이지 넘버, 정렬 기준으로 전체 상품을 페이지로 가져올 수 있다.")
    void findProductAll() {

        PageRequest pageRequest = PageRequest.of(5, 10, Sort.by("id").descending());

        Page<Product> result = productRepository.findAll(pageRequest);

        Assertions.assertThat(result.getNumber()).isEqualTo(pageRequest.getPageNumber());
        Assertions.assertThat(result.getSize()).isEqualTo(pageRequest.getPageSize());
    }

    @Test
    @DisplayName("사용자가 요청한 상품 코드로 상품의 상세 정보 조회가 가능해야 한다.")
    void findProductDetailById() {

        //ProductId 10을 가지고 있는 상품 조회
        Product result = productRepository.findById(10L).get();

        Assertions.assertThat(result.getId()).isEqualTo(10L);
    }
}