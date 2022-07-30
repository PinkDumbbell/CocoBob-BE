package com.pinkdumbell.cocobob.domain.product.nutrition;

import com.pinkdumbell.cocobob.domain.product.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Nutrition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nutrition_id")
    private Long id;

    private Float protein;

    private Double amountOfProteinPerMcal;

    private Float fat;

    private Double amountOfFatPerMcal;

    private Float fiber;

    private Double amountOfFiberPerMcal;

    private Float mineral;

    private Double amountOfMineralPerMcal;

    private Float calcium;

    private Double amountOfCalciumPerMcal;

    private Float phosphorus;

    private Double amountOfPhosphorusPerMcal;

    private Float moisture;

    private Double kcalPerKg;

    @Column(name = "is_aafco_satisfied")
    private Boolean isAAFCOSatisfied;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
}
