package com.pinkdumbell.cocobob.domain.pet.dto;

import com.pinkdumbell.cocobob.domain.pet.PetSex;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@Getter
public class PetCreateRequestDto {
    private String name;
    private PetSex sex;
    private int age;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;
    private MultipartFile petImage;
}
