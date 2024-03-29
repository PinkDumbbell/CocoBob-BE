package com.pinkdumbell.cocobob.domain.pet.problem;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class PetProblemId implements Serializable {
    private Long petId;
    private Long problemId;
}
