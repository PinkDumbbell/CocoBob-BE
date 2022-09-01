package com.pinkdumbell.cocobob.domain.product;

import com.pinkdumbell.cocobob.domain.product.dto.ProductSpecificSearchWithLikeDto;
import com.querydsl.core.BooleanBuilder;
import java.util.List;

public class ProductBooleanBuilder {

    public static BooleanBuilder makeProductBooleanBuilder(
        ProductSpecificSearchWithLikeDto requestParameter) {
        BooleanBuilder builder = new BooleanBuilder();
        QProduct qProduct = QProduct.product;

        if (requestParameter.getAafco() != null) {
            builder.and(qProduct.isAAFCOSatisfied.eq(true));
        }

        if (requestParameter.getIngredient() != null) {
            builder.and(makeIngredientBooleanBuilder(requestParameter.getIngredient()));
        }

        return builder;
    }

    public static BooleanBuilder makeIngredientBooleanBuilder(List<String> ingredients) {
        BooleanBuilder builder = new BooleanBuilder();
        QProduct qproduct = QProduct.product;
        for (String ingredient : ingredients) {

            if ("beef".equals(ingredient)) {
                builder.or(qproduct.beef.eq(true));
            } else if ("mutton".equals(ingredient)) {
                builder.or(qproduct.mutton.eq(true));
            } else if ("chicken".equals(ingredient)) {
                builder.or(qproduct.chicken.eq(true));
            } else if ("duck".equals(ingredient)) {
                builder.or(qproduct.duck.eq(true));
            } else if ("turkey".equals(ingredient)) {
                builder.or(qproduct.turkey.eq(true));
            }

        }

        return builder;
    }

    public static BooleanBuilder makeKeywordBooleanBuilder(String keyword) {
        BooleanBuilder builder = new BooleanBuilder();
        QProduct qProduct = QProduct.product;

        builder.or(qProduct.brand.contains(keyword));
        builder.or(qProduct.name.contains(keyword));
        builder.or(qProduct.brand.concat(" ").concat(qProduct.name).contains(keyword));

        return builder;
    }

}
