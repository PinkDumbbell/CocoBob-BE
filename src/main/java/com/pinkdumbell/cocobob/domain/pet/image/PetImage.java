package com.pinkdumbell.cocobob.domain.pet.image;

import com.pinkdumbell.cocobob.domain.pet.Pet;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class PetImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    @Column(length = 1000)
    private String path;

    @OneToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;
}