package com.pinkdumbell.cocobob.domain.product;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    private String code;

    private String name;

    private String category;

    private Integer price;

    @Column(length = 1000)
    private String thumbnail;

    @Column(columnDefinition = "TEXT")
    private String description;
}
