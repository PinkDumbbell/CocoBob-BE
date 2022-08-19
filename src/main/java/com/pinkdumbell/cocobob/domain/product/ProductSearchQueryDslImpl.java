package com.pinkdumbell.cocobob.domain.product;


import com.pinkdumbell.cocobob.domain.product.dto.ProductSimpleResponseDto;
import com.pinkdumbell.cocobob.domain.product.dto.ProductSpecificSearchDto;
import com.pinkdumbell.cocobob.domain.product.like.QLike;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductSearchQueryDslImpl implements ProductSearchQueryDsl {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public PageImpl<ProductSimpleResponseDto> findAllWithLikes(
        ProductSpecificSearchDto productSpecificSearchDto,
        Long userId,
        Pageable pageable) {
        QProduct qProduct = QProduct.product;
        QLike qLike = QLike.like;
        NumberPath<Long> likes = Expressions.numberPath(Long.class, "likes");

        JPAQuery<ProductSimpleResponseDto> query = jpaQueryFactory.select(
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
                            .where(qLike.user.id.eq(userId), qLike.product.id.eq(qProduct.id)),
                        "isUserLike")))
            .from(qProduct)
            .leftJoin(qLike).on(qProduct.id.eq(qLike.product.id))
            .where(ProductPredicate.makeProductBooleanBuilder(productSpecificSearchDto));

        if (productSpecificSearchDto.getSortCriteria() != null) {
            String sortCriteria = productSpecificSearchDto.getSortCriteria();

            if (Objects.equals(sortCriteria, "ID,ASC")) {
                query = query.orderBy(qProduct.id.asc());
            } else if (Objects.equals(sortCriteria, "ID,DESC")) {
                query = query.orderBy(qProduct.id.desc());
            } else if (Objects.equals(sortCriteria, "PRICE,ASC")) {
                query = query.orderBy(qProduct.price.asc());
            } else if (Objects.equals(sortCriteria, "PRICE,DESC")) {
                query = query.orderBy(qProduct.price.desc());
            } else if (Objects.equals(sortCriteria, "LIKE,ASC")) {
                query = query.orderBy(likes.asc());
            } else if (Objects.equals(sortCriteria, "LIKE,DESC")) {
                query = query.orderBy(likes.desc());
            }
        }

        List<ProductSimpleResponseDto> result = query
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        return new PageImpl<>(result, pageable, result.size());
    }
}
