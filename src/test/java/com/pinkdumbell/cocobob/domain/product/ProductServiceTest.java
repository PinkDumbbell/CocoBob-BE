package com.pinkdumbell.cocobob.domain.product;

import com.pinkdumbell.cocobob.common.EmailUtil;
import com.pinkdumbell.cocobob.config.MailConfig;
import org.assertj.core.api.Assertions;

import com.pinkdumbell.cocobob.domain.product.dto.FindAllResponseDto;
import javax.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Transactional
@SpringBootTest
class ProductServiceTest {

    @MockBean
    EmailUtil emailUtil;

    @MockBean
    MailConfig mailConfig;
    @Autowired
    ProductService productService;

    @Test
    @DisplayName("사용자가 설정한 페이지 사이즈, 페이지 넘버, 정렬 기준으로 전체 상품을 페이지로 가져올 수 있다.")
    void findProductAll() {

        PageRequest pageRequest = PageRequest.of(5,10, Sort.by("id").descending());

        FindAllResponseDto productAll = productService.findProductAll((Pageable) pageRequest);

        Assertions.assertThat(productAll.getPageNumber()).isEqualTo(pageRequest.getPageNumber());
        Assertions.assertThat(productAll.getPageSize()).isEqualTo(pageRequest.getPageSize());
    }

    @Test
    void findProductDetailById() {
    }
}