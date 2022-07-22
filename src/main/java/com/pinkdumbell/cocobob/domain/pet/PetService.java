package com.pinkdumbell.cocobob.domain.pet;

import com.pinkdumbell.cocobob.domain.common.ImageService;
import com.pinkdumbell.cocobob.domain.pet.dto.PetCreateRequestDto;
import com.pinkdumbell.cocobob.domain.pet.dto.PetCreateResponseDto;
import com.pinkdumbell.cocobob.domain.pet.image.PetImage;
import com.pinkdumbell.cocobob.domain.pet.image.PetImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PetService {
    private final PetRepository petRepository;
    private final PetImageRepository petImageRepository;
    private final ImageService imageService;
    final int RESIZE_TARGET_WIDTH = 300;

    @Transactional
    public PetCreateResponseDto register(PetCreateRequestDto requestDto) {
        Pet pet = petRepository.save(Pet.builder()
                .name(requestDto.getName())
                .sex(requestDto.getSex())
                .age(requestDto.getAge())
                .birthday(requestDto.getBirthday())
                .build());
        if (requestDto.getPetImage() != null) {
            petImageRepository.save(new PetImage(
                    imageService.saveImage(requestDto.getPetImage()), pet));
            pet.setThumbnailPath(imageService.saveImage(
                    imageService.resizeImage(requestDto.getPetImage(), RESIZE_TARGET_WIDTH)));
        }
        return new PetCreateResponseDto(pet);
    }
}
