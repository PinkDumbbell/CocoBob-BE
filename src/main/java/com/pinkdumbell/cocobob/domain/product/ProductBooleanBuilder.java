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

        if (requestParameter.getAllergyIngredient() != null) {
            builder.and(
                makeAllergyIngredientBooleanBuilder(requestParameter.getAllergyIngredient()));
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
            } else if ("pork".equals(ingredient)) {
                builder.or(qproduct.meat.eq(true));
                //meat - > pork 데이터 베이스 스키마에는 meat로 되어 그대로 사용
            } else if ("salmon".equals(ingredient)) {
                builder.or(qproduct.salmon.eq(true));
            } else if ("hydrolyticBeef".equals(ingredient)) {
                builder.or(qproduct.hydrolyticBeef.eq(true));
            } else if ("hydrolyticMutton".equals(ingredient)) {
                builder.or(qproduct.hydrolyticMutton.eq(true));
            } else if ("hydrolyticChicken".equals(ingredient)) {
                builder.or(qproduct.hydrolyticChicken.eq(true));
            } else if ("hydrolyticDuck".equals(ingredient)) {
                builder.or(qproduct.hydrolyticDuck.eq(true));
            } else if ("hydrolyticTurkey".equals(ingredient)) {
                builder.or(qproduct.hydrolyticTurkey.eq(true));
            } else if ("hydrolyticPork".equals(ingredient)) {
                builder.or(qproduct.hydrolyticMeat.eq(true));
                //meat - > pork 데이터 베이스 스키마에는 meat로 되어 그대로 사용
            } else if ("hydrolyticSalmon".equals(ingredient)) {
                builder.or(qproduct.hydrolyticSalmon.eq(true));
            }
        }

        return builder;
    }

    public static BooleanBuilder makeAllergyIngredientBooleanBuilder(List<String> ingredients) {
        BooleanBuilder builder = new BooleanBuilder();
        QProduct qproduct = QProduct.product;
        for (String ingredient : ingredients) {

            if ("beef".equals(ingredient)) {
                builder.and(qproduct.beef.eq(false));
            } else if ("mutton".equals(ingredient)) {
                builder.and(qproduct.mutton.eq(false));
            } else if ("chicken".equals(ingredient)) {
                builder.and(qproduct.chicken.eq(false));
            } else if ("duck".equals(ingredient)) {
                builder.and(qproduct.duck.eq(false));
            } else if ("turkey".equals(ingredient)) {
                builder.and(qproduct.turkey.eq(false));
            } else if ("pork".equals(ingredient)) {
                builder.and(qproduct.meat.eq(false));
                //meat - > pork 데이터 베이스 스키마에는 meat로 되어 그대로 사용
            } else if ("salmon".equals(ingredient)) {
                builder.and(qproduct.salmon.eq(false));
            } else if ("hydrolyticBeef".equals(ingredient)) {
                builder.and(qproduct.hydrolyticBeef.eq(false));
            } else if ("hydrolyticMutton".equals(ingredient)) {
                builder.and(qproduct.hydrolyticMutton.eq(false));
            } else if ("hydrolyticChicken".equals(ingredient)) {
                builder.and(qproduct.hydrolyticChicken.eq(false));
            } else if ("hydrolyticDuck".equals(ingredient)) {
                builder.and(qproduct.hydrolyticDuck.eq(false));
            } else if ("hydrolyticTurkey".equals(ingredient)) {
                builder.and(qproduct.hydrolyticTurkey.eq(false));
            } else if ("hydrolyticPork".equals(ingredient)) {
                builder.and(qproduct.hydrolyticMeat.eq(false));
                //meat - > pork 데이터 베이스 스키마에는 meat로 되어 그대로 사용
            } else if ("hydrolyticSalmon".equals(ingredient)) {
                builder.and(qproduct.hydrolyticSalmon.eq(false));
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
