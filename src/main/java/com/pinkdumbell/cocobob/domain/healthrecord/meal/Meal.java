package com.pinkdumbell.cocobob.domain.healthrecord.meal;

import com.pinkdumbell.cocobob.domain.healthrecord.HealthRecord;
import com.pinkdumbell.cocobob.domain.product.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Meal {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meal_id")
    private Long id;
    private String productName;
    private Double kcal;
    private Integer amount;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "health_record_id")
    private HealthRecord healthRecord;

    public void updateMeal(String productName, Double kcal, Integer amount, Product product) {
        this.productName = productName;
        this.kcal = kcal;
        this.amount = amount;
        this.product = product;
    }
}
