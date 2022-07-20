package com.pinkdumbell.cocobob.domain.product.problem;

import com.pinkdumbell.cocobob.domain.problem.Problem;
import com.pinkdumbell.cocobob.domain.product.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class ProductProblem {
    @EmbeddedId
    private ProductProblemId productProblemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("product")
    @JoinColumn(name = "product_id")
    private Product product;
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("problem")
    @JoinColumn(name = "problem_id")
    private Problem problem;
}
