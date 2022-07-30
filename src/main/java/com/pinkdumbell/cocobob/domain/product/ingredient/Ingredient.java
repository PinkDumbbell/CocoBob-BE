package com.pinkdumbell.cocobob.domain.product.ingredient;

import com.pinkdumbell.cocobob.domain.product.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ingredient_id")
    private Long id;

    private Boolean beef;

    private Boolean mutton;

    private Boolean chicken;

    private Boolean duck;

    private Boolean turkey;

    private Boolean meat;

    private Boolean salmon;

    private Boolean hydrolyticBeef;

    private Boolean hydrolyticMutton;

    private Boolean hydrolyticChicken;

    private Boolean hydrolyticDuck;

    private Boolean hydrolyticTurkey;

    private Boolean hydrolyticMeat;

    private Boolean hydrolyticSalmon;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
}
