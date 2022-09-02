package com.pinkdumbell.cocobob.domain.product;


import com.pinkdumbell.cocobob.config.TestConfig;
import com.pinkdumbell.cocobob.domain.product.dto.ProductSimpleResponseDto;
import com.pinkdumbell.cocobob.domain.product.like.QLike;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@Transactional
@Import(TestConfig.class)
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @MockBean
    ProductSearchQueryDslImpl productSearchQueryDsl;

    @Autowired
    JPAQueryFactory jpaQueryFactory;

    @BeforeEach
    void init() {
        for (int dummyIndex = 1; dummyIndex < 30; dummyIndex++) {
            Product dummyProduct = new Product();
            productRepository.save(dummyProduct);
        }
    }

    @Test
    @DisplayName("사용자가 요청한 상품 코드로 상품의 상세 정보 조회가 가능해야 한다.")
    void findProductDetailById() {

        //ProductId 10을 가지고 있는 상품 조회
        Product result = productRepository.findById(10L).get();
        Assertions.assertThat(result.getId()).isEqualTo(10L);
    }

    @Test
    @DisplayName("keyword를 통한 연관 상품 정보를 제공할 수 있어야 한다.")
    void provideProductListByKeyword() {
        QProduct qProduct = QProduct.product;

        List<Tuple> result = jpaQueryFactory.select(qProduct.brand, qProduct.name)
            .from(qProduct)
            .where(ProductBooleanBuilder.makeKeywordBooleanBuilder("test"))
            .fetch();
        Assertions.assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("동적 조건을 통해서 상품을 필터링을 할 수 있다.")
    void productFilteringByDynamicQuery() {
        QProduct qProduct = QProduct.product;
        QLike qLike = QLike.like;
        NumberPath<Long> likes = Expressions.numberPath(Long.class, "likes");

        List<ProductSimpleResponseDto> result = jpaQueryFactory.select(
                Projections.constructor(ProductSimpleResponseDto.class,
                    qProduct.id.as("productId"), qProduct.code.as("code"), qProduct.name.as("name"),
                    qProduct.brand.as("brand"), qProduct.category.as("category"),
                    qProduct.price.as("price"), qProduct.thumbnail.as("thumbnail"),
                    qProduct.description.as("description"),
                    qProduct.isAAFCOSatisfied.as("isAAFCOSatisfied"), qProduct.aged.as("aged"),
                    qProduct.growing.as("growing"), qProduct.pregnant.as("pregnant"),
                    qProduct.obesity.as("obesity"),
                    ExpressionUtils.as(JPAExpressions.select(qLike.count())
                        .from(qLike)
                        .where(qLike.product.eq(qProduct)), likes),
                    ExpressionUtils.as(
                        JPAExpressions.select(qLike.isNotNull())
                            .from(qLike)
                            .where(qLike.user.id.eq(1L), qLike.product.id.eq(qProduct.id)),
                        "isUserLike")))
            .from(qProduct)
            .leftJoin(qLike).on(qProduct.id.eq(qLike.product.id))
            .where(new BooleanBuilder())
            .fetch();
        Assertions.assertThat(result).isNotNull();
    }

}