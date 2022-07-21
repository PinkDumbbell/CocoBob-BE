package com.pinkdumbell.cocobob.domain.pet.dto;

import com.pinkdumbell.cocobob.domain.pet.PetSex;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@Getter
@NoArgsConstructor
public class PetCreateRequestDto {
    private String name;
    private PetSex sex;
    private int age;
    private LocalDate birthday;
    private MultipartFile petImage;
}
