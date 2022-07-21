package com.pinkdumbell.cocobob.domain.pet.problem;

import com.pinkdumbell.cocobob.domain.pet.Pet;
import com.pinkdumbell.cocobob.domain.problem.Problem;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Getter
@NoArgsConstructor
@Entity
public class PetProblem {
    @EmbeddedId
    private PetProblemId petProblemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id")
    @MapsId("petId")
    private Pet pet;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id")
    @MapsId("problemId")
    private Problem problem;
}
