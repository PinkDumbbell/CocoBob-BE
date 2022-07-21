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
    private Long petId;
    private Long allergyId;
}
