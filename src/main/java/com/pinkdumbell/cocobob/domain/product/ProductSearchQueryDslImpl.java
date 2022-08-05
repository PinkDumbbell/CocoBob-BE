package com.pinkdumbell.cocobob.domain.product;


import com.pinkdumbell.cocobob.domain.product.dto.ProductSimpleResponseDto;
import com.pinkdumbell.cocobob.domain.product.dto.ProductSpecificSearchDto;
import com.pinkdumbell.cocobob.domain.product.like.QLike;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
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

        List<ProductSimpleResponseDto> result = jpaQueryFactory.select(
                Projections.constructor(ProductSimpleResponseDto.class,
                    qProduct.id, qProduct.code, qProduct.name, qProduct.category, qProduct.price,
                    qProduct.thumbnail, qProduct.description, qProduct.isAAFCOSatisfied, qProduct.aged,
                    qProduct.pregnant,
                    ExpressionUtils.as(JPAExpressions.select(qLike.likeId.count())
                        .from(qLike)
                        .where(qLike.product.eq(qProduct)), "likes"),
                    ExpressionUtils.as(
                        JPAExpressions.select(qLike.likeId.isNotNull())
                            .from(qLike)
                            .where(qLike.user.id.eq(userId)), "isUserLike")))
            .from(qProduct)
            .leftJoin(qLike).on(qProduct.id.eq(qLike.product.id))
            .where(ProductPedicate.makeProductBooleanBuilder(productSpecificSearchDto))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        return new PageImpl <>(result,pageable,result.size());
    }
}
