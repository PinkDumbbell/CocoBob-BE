package com.pinkdumbell.cocobob.domain.product;

import com.pinkdumbell.cocobob.domain.product.dto.ProductSpecificSearchWithLikeDto;
import com.querydsl.core.BooleanBuilder;
import java.util.List;

public class ProductBooleanBuilder {

    public static BooleanBuilder makeProductBooleanBuilder(
        ProductSpecificSearchWithLikeDto requestParameter) {
        BooleanBuilder builder = new BooleanBuilder();
        QProduct qProduct = QProduct.product;

        // keyword로 검색
        if (requestParameter.getKeyword() != null) {
            builder.and(makeKeywordBooleanBuilder(requestParameter.getKeyword()));
        }
        // AAFCO 기준
        if (requestParameter.getAafco() != null && requestParameter.getAafco() == true) {
            builder.and(qProduct.isAAFCOSatisfied.eq(true));
        }
        // 브랜드
        if (requestParameter.getBrands() != null) {
            builder.and(makeBrandBooleanBuilder(requestParameter.getBrands()));
        }
        // 카테고리
        if (requestParameter.getCodes() != null) {
            builder.and(makeCodeBooleanBuilder(requestParameter.getCodes()));
        }
        // 원하는 포함 재료
        if (requestParameter.getIngredient() != null) {
            builder.and(makeIngredientBooleanBuilder(requestParameter.getIngredient()));
        }
        // 알러지 유발 재료
        if (requestParameter.getAllergyIngredient() != null) {
            builder.and(
                makeAllergyIngredientBooleanBuilder(requestParameter.getAllergyIngredient()));
        }
        // 유형별
        if (requestParameter.getTypes() != null) {
            builder.and(makeTypeBooleanBuilder(requestParameter.getTypes()));
        }

        return builder;
    }

    private static BooleanBuilder makeIngredientBooleanBuilder(List<String> ingredients) {
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

    private static BooleanBuilder makeAllergyIngredientBooleanBuilder(List<String> ingredients) {
        BooleanBuilder builder = new BooleanBuilder();
        QProduct qproduct = QProduct.product;
        for (String ingredient : ingredients) {
            System.out.println(ingredient);
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

    private static BooleanBuilder makeBrandBooleanBuilder(List<String> brands) {
        BooleanBuilder builder = new BooleanBuilder();
        QProduct qProduct = QProduct.product;

        for (String brand : brands) {
            builder.or(qProduct.brand.contains(brand));
        }

        return builder;
    }

    private static BooleanBuilder makeCodeBooleanBuilder(List<String> codes) {
        BooleanBuilder builder = new BooleanBuilder();
        QProduct qProduct = QProduct.product;

        for (String code : codes) {
            builder.or(qProduct.code.contains(code));
        }

        return builder;
    }

    private static BooleanBuilder makeTypeBooleanBuilder(List<String> types) {
        BooleanBuilder builder = new BooleanBuilder();
        QProduct qProduct = QProduct.product;

        for (String type : types) {
            if ("aged".equals(type)) {
                builder.or(qProduct.aged.eq(true));
            } else if ("growing".equals(type)) {
                builder.or(qProduct.growing.eq(true));

            } else if ("obesity".equals(type)) {
                builder.or(qProduct.obesity.eq(true));
            } else if ("pregnant".equals(type)) {
                builder.or(qProduct.pregnant.eq(true));
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
