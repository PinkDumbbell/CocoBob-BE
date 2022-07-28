package com.pinkdumbell.cocobob.domain.pet;

import com.pinkdumbell.cocobob.common.ImageService;
import com.pinkdumbell.cocobob.domain.pet.breed.BreedRepository;
import com.pinkdumbell.cocobob.domain.pet.dto.BreedsInfoResponseDto;
import com.pinkdumbell.cocobob.domain.pet.dto.PetCreateRequestDto;
import com.pinkdumbell.cocobob.domain.pet.dto.PetCreateResponseDto;
import com.pinkdumbell.cocobob.domain.pet.image.PetImage;
import com.pinkdumbell.cocobob.domain.pet.image.PetImageRepository;
import com.pinkdumbell.cocobob.exception.CustomException;
import com.pinkdumbell.cocobob.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PetService {
    private final PetRepository petRepository;
    private final PetImageRepository petImageRepository;
    private final BreedRepository breedRepository;
    private final ImageService imageService;
    final int RESIZE_TARGET_WIDTH = 300;

    @Transactional
    public PetCreateResponseDto register(PetCreateRequestDto requestDto) {
        Pet pet = petRepository.save(Pet.builder()
                .name(requestDto.getName())
                .sex(requestDto.getSex())
                .age(requestDto.getAge())
                .birthday(requestDto.getBirthday())
                .isSpayed(requestDto.getIsSpayed())
                .isPregnant(requestDto.getIsPregnant())
                .bodyWeight(requestDto.getBodyWeight())
                .activityLevel(requestDto.getActivityLevel())
                .breed(breedRepository.findById(requestDto.getBreedId())
                        .orElseThrow(() -> new CustomException(ErrorCode.BREED_NOT_FOUND)))
                .build());
        if (requestDto.getPetImage() != null) {
            petImageRepository.save(new PetImage(
                    imageService.saveImage(requestDto.getPetImage()), pet));
            pet.setThumbnailPath(imageService.saveImage(
                    imageService.resizeImage(requestDto.getPetImage(), RESIZE_TARGET_WIDTH)));
        }
        return new PetCreateResponseDto(pet);
    }

    @Transactional
    public List<BreedsInfoResponseDto> provideBreedsInfo(){

        List<BreedsInfoResponseDto> breedsList = breedRepository.findAll().stream()
                .map(breed ->
                    new BreedsInfoResponseDto(breed.getId(), breed.getName(), breed.getSize().toString())
                ).collect(Collectors.toList());

        return breedsList;
    }
}
