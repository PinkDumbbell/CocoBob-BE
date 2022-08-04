package com.pinkdumbell.cocobob.domain.product;

import com.pinkdumbell.cocobob.domain.product.dto.ProductSpecificSearchDto;
import org.springframework.data.jpa.domain.Specification;

public class ProductSearchSpecification {

    public static Specification<Product> equalCode(String code) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("code"), code);
    }

    public static Specification<Product> likeName(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"),
            "%" + name + "%");
    }

    public static Specification<Product> likeDescription(String description) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("description"),
            "%" + description + "%");
    }

    public static Specification<Product> equalBeef(boolean beef) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("beef"), beef);
    }

    public static Specification<Product> equalMutton(boolean mutton) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("mutton"), mutton);
    }

    public static Specification<Product> equalChicken(boolean chicken) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("chicken"),
            chicken);
    }

    public static Specification<Product> equalDuck(boolean duck) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("duck"), duck);
    }

    public static Specification<Product> equalTurkey(boolean turkey) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("turkey"), turkey);
    }

    public static Specification<Product> equalMeat(boolean meat) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("meat"), meat);
    }

    public static Specification<Product> equalSalmon(boolean salmon) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("salmon"), salmon);
    }

    public static Specification<Product> equalHydrolyticBeef(boolean hydrolyticBeef) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("hydrolyticBeef"),
            hydrolyticBeef);
    }

    public static Specification<Product> equalHydrolyticMutton(boolean hydrolyticMutton) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("hydrolyticMutton"),
            hydrolyticMutton);
    }

    public static Specification<Product> equalHydrolyticChicken(boolean hydrolyticChicken) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(
            root.get("hydrolyticChicken"), hydrolyticChicken);
    }

    public static Specification<Product> equalHydrolyticDuck(boolean hydrolyticDuck) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("hydrolyticDuck"),
            hydrolyticDuck);
    }

    public static Specification<Product> equalHydrolyticTurkey(boolean hydrolyticTurkey) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("hydrolyticTurkey"),
            hydrolyticTurkey);
    }

    public static Specification<Product> equalHydrolyticMeat(boolean hydrolyticMeat) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("hydrolyticMeat"),
            hydrolyticMeat);
    }

    public static Specification<Product> equalHydrolyticSalmon(boolean hydrolyticSalmon) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("hydrolyticSalmon"),
            hydrolyticSalmon);
    }

    public static Specification<Product> equalAFFCO(boolean affco) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isAAFCOSatisfied"), affco);
    }

    public static Specification<Product> equalAged(boolean aged) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("aged"), aged);
    }

    public static Specification<Product> equalGrowing(boolean growing) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("growing"),
            growing);
    }

    public static Specification<Product> equalPregnant(boolean pregnant) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("pregnant"),
            pregnant);
    }

    public static Specification<Product> equalObesity(boolean obesity) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("obesity"),
            obesity);
    }


    public static Specification<Product> makeProductSpecification(
        ProductSpecificSearchDto requestParameter) {

        Specification<Product> spec = (root, query, criteriaBuilder) -> null;

        if (requestParameter.getCode() != null) {
            spec = spec.and(equalCode(requestParameter.getCode())); // 카테고리 코드
        }

        if (requestParameter.getName() != null) {
            spec = spec.and(likeName(requestParameter.getName())); // 상품명
        }

        if (requestParameter.getDescription() != null) {
            spec = spec.and(likeDescription(requestParameter.getDescription())); // 상품 내용
        }

        if (requestParameter.getBeef() != null) {
            spec = spec.and(equalBeef(requestParameter.getBeef())); // 소고기 포함 유무
        }

        if (requestParameter.getMutton() != null) {
            spec = spec.and(equalMutton(requestParameter.getMutton())); // 양고기 포함 유무
        }

        if (requestParameter.getChicken() != null) {
            spec = spec.and(equalChicken(requestParameter.getChicken())); // 닭고기 포함 유무
        }

        if (requestParameter.getDuck() != null) {
            spec = spec.and(equalDuck(requestParameter.getDuck())); // 오리고기 포함 유무
        }

        if (requestParameter.getTurkey() != null) {
            spec = spec.and(equalTurkey(requestParameter.getTurkey())); // 칠면조 포함 유무
        }

        if (requestParameter.getMeat() != null) {
            spec = spec.and(equalMeat(requestParameter.getMeat())); //  돼지고기 포함 유무
        }

        if (requestParameter.getSalmon() != null) {
            spec = spec.and(equalSalmon(requestParameter.getSalmon())); // 연어 포함 유무
        }

        if (requestParameter.getHydrolyticBeef() != null) {
            spec = spec.and(
                equalHydrolyticBeef(requestParameter.getHydrolyticBeef())); // 가수분해 소고기 포함 유무
        }

        if (requestParameter.getHydrolyticMutton() != null) {
            spec = spec.and(
                equalHydrolyticMutton(requestParameter.getHydrolyticMutton())); // 가수분해 양고기 포함 유무
        }

        if (requestParameter.getHydrolyticChicken() != null) {
            spec = spec.and(
                equalHydrolyticChicken(requestParameter.getHydrolyticChicken())); // 가수분해 닭고기 포함 유무
        }

        if (requestParameter.getHydrolyticDuck() != null) {
            spec = spec.and(
                equalHydrolyticDuck(requestParameter.getHydrolyticDuck())); // 가수분해 오리고기 포함 유무
        }

        if (requestParameter.getHydrolyticTurkey() != null) {
            spec = spec.and(
                equalHydrolyticTurkey(requestParameter.getHydrolyticTurkey())); // 가수분해 칠면조 포함 유무
        }

        if (requestParameter.getHydrolyticMeat() != null) {
            spec = spec.and(
                equalHydrolyticMeat(requestParameter.getHydrolyticMeat())); // 가수분해 돼지고기 포함 유무
        }

        if (requestParameter.getHydrolyticSalmon() != null) {
            spec = spec.and(
                equalHydrolyticSalmon(requestParameter.getHydrolyticSalmon())); // 가수분해 연어 포함 유무
        }

        if (requestParameter.getAafco() != null) {
            spec = spec.and(equalAFFCO(requestParameter.getAafco()));
        }

        if (requestParameter.getAged() != null) {
            spec = spec.and(equalAged(requestParameter.getAged()));
        }

        if (requestParameter.getPregnant() != null) {
            spec = spec.and(equalPregnant(requestParameter.getPregnant()));
        }

        if (requestParameter.getGrowing() != null) {
            spec = spec.and(equalGrowing(requestParameter.getGrowing()));
        }

        if (requestParameter.getObesity() != null) {
            spec = spec.and(equalObesity(requestParameter.getObesity()));
        }

        return spec;
    }


}
