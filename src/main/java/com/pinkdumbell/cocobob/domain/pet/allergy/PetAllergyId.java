package com.pinkdumbell.cocobob.domain.pet.allergy;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@Getter
@NoArgsConstructor
public class PetAllergyId implements Serializable {
    @Column(name = "pet_id")
    private Long pet;
    @Column(name = "allergy_id")
    private Long allergy;
}
