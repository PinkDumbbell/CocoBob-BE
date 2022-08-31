package com.pinkdumbell.cocobob.domain.product;

import com.pinkdumbell.cocobob.domain.product.dto.ProductSpecificSearchDto;

import com.pinkdumbell.cocobob.domain.product.dto.ProductSpecificSearchWithLikeDto;
import com.querydsl.core.BooleanBuilder;

public class ProductPredicate {

    public static BooleanBuilder makeProductBooleanBuilder(
        ProductSpecificSearchWithLikeDto requestParameter) {
        QProduct qProduct = QProduct.product;
        BooleanBuilder builder = new BooleanBuilder();

        if (requestParameter.getCode() != null) {
            builder.and(qProduct.code.eq(requestParameter.getCode()));
        }

        if (requestParameter.getName() != null) {
            builder.and(qProduct.name.contains(requestParameter.getName())); // 상품명
        }

        if (requestParameter.getBrand() != null) {
            builder.and(qProduct.brand.contains(requestParameter.getBrand())); // 브랜드명
        }

        if (requestParameter.getDescription() != null) {
            builder.and(qProduct.description.contains(requestParameter.getDescription())); // 상품 내용
        }

        if (requestParameter.getBeef() != null) {
            builder.and(qProduct.beef.eq(requestParameter.getBeef())); // 소고기 포함 유무
        }

        if (requestParameter.getMutton() != null) {
            builder.and(qProduct.mutton.eq(requestParameter.getMutton())); // 양고기 포함 유무
        }

        if (requestParameter.getChicken() != null) {
            builder.and(qProduct.chicken.eq(requestParameter.getChicken())); // 닭고기 포함 유무
        }

        if (requestParameter.getDuck() != null) {
            builder.and(qProduct.duck.eq(requestParameter.getDuck())); // 오리고기 포함 유무
        }

        if (requestParameter.getTurkey() != null) {
            builder.and(qProduct.turkey.eq(requestParameter.getTurkey())); // 칠면조 포함 유무
        }

        if (requestParameter.getPork() != null) {
            builder.and(qProduct.meat.eq(requestParameter.getPork())); //  돼지고기 포함 유무
        }

        if (requestParameter.getSalmon() != null) {
            builder.and(qProduct.salmon.eq(requestParameter.getSalmon())); // 연어 포함 유무
        }

        if (requestParameter.getHydrolyticBeef() != null) {
            builder.and(
                qProduct.hydrolyticBeef.eq(requestParameter.getHydrolyticBeef())); // 가수분해 소고기 포함 유무
        }

        if (requestParameter.getHydrolyticMutton() != null) {
            builder.and(
                qProduct.hydrolyticMutton.eq(
                    requestParameter.getHydrolyticMutton())); // 가수분해 양고기 포함 유무
        }

        if (requestParameter.getHydrolyticChicken() != null) {
            builder.and(
                qProduct.hydrolyticChicken.eq(
                    requestParameter.getHydrolyticChicken())); // 가수분해 닭고기 포함 유무
        }

        if (requestParameter.getHydrolyticDuck() != null) {
            builder.and(
                qProduct.hydrolyticDuck.eq(
                    requestParameter.getHydrolyticDuck())); // 가수분해 오리고기 포함 유무
        }

        if (requestParameter.getHydrolyticTurkey() != null) {
            builder.and(
                qProduct.hydrolyticTurkey.eq(
                    requestParameter.getHydrolyticTurkey())); // 가수분해 칠면조 포함 유무
        }

        if (requestParameter.getHydrolyticPork() != null) {
            builder.and(
                qProduct.hydrolyticMeat.eq(
                    requestParameter.getHydrolyticPork())); // 가수분해 돼지고기 포함 유무
        }

        if (requestParameter.getHydrolyticSalmon() != null) {
            builder.and(
                qProduct.hydrolyticSalmon.eq(
                    requestParameter.getHydrolyticSalmon())); // 가수분해 연어 포함 유무
        }

        if (requestParameter.getAafco() != null) {
            builder.and(qProduct.isAAFCOSatisfied.eq(requestParameter.getAafco()));
        }

        if (requestParameter.getAged() != null) {
            builder.and(qProduct.aged.eq(requestParameter.getAged()));
        }

        if (requestParameter.getPregnant() != null) {
            builder.and(qProduct.pregnant.eq(requestParameter.getPregnant()));
        }

        if (requestParameter.getGrowing() != null) {
            builder.and(qProduct.growing.eq(requestParameter.getGrowing()));
        }

        if (requestParameter.getObesity() != null) {
            builder.and(qProduct.obesity.eq(requestParameter.getObesity()));
        }

        return builder;
    }

    public static BooleanBuilder makeKeywordBooleanBuilder(String keyword) {
        BooleanBuilder builder = new BooleanBuilder();
        QProduct qProduct = QProduct.product;

        builder.or(qProduct.brand.contains(keyword));
        builder.or(qProduct.name.contains(keyword));

        return builder;
    }

}
