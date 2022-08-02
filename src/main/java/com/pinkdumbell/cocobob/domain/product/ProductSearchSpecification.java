package com.pinkdumbell.cocobob.domain.product;

import org.springframework.data.jpa.domain.Specification;

public class ProductSearchSpecification {

    public static Specification<Product> equalCode(String code) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("code"),
            code);
    }

    public static Specification<Product> likeName(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"),
            "%"+name+"%");
    }

    public static Specification<Product> likeDescription(String description) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("description"),
            "%"+description+"%");
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

    public static Specification<Product> equalAFFCO(boolean aafco) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isAAFCOSatisfied"), aafco);
    }


}
