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

    private Float fat;

    private Float fiber;

    private Float mineral;

    private Float calcium;

    private Float phosphorus;

    private Float moisture;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
}
