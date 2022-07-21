package com.pinkdumbell.cocobob.domain.pet.allergy;

import com.pinkdumbell.cocobob.domain.allergy.Allergy;
import com.pinkdumbell.cocobob.domain.pet.Pet;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class PetAllergy {
    @EmbeddedId
    private PetAllergyId petAllergyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id")
    @MapsId("petId")
    private Pet pet;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "allergy_id")
    @MapsId("allergyId")
    private Allergy allergy;
}
