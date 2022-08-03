package com.pinkdumbell.cocobob.domain.pet.breed;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
public class Breed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "breed_id")
    private Long id;

    private String name;

    private String createdBy;

    @Enumerated(EnumType.STRING)
    private BreedSize size;
}
