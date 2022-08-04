package com.pinkdumbell.cocobob.domain.pet;

import com.pinkdumbell.cocobob.common.ImageService;
import com.pinkdumbell.cocobob.domain.pet.breed.BreedRepository;
import com.pinkdumbell.cocobob.domain.pet.dto.*;
import com.pinkdumbell.cocobob.domain.pet.image.PetImage;
import com.pinkdumbell.cocobob.domain.pet.image.PetImageRepository;
import com.pinkdumbell.cocobob.domain.user.User;
import com.pinkdumbell.cocobob.domain.user.UserRepository;
import com.pinkdumbell.cocobob.domain.user.dto.LoginUserInfo;
import com.pinkdumbell.cocobob.exception.CustomException;
import com.pinkdumbell.cocobob.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PetService {

    private final PetRepository petRepository;
    private final PetImageRepository petImageRepository;
    private final BreedRepository breedRepository;
    private final UserRepository userRepository;
    private final ImageService imageService;
    final int RESIZE_TARGET_WIDTH = 300;

    @Transactional
    public PetCreateResponseDto register(LoginUserInfo loginUserInfo,
        PetCreateRequestDto requestDto) {
        User user = userRepository.findByEmail(loginUserInfo.getEmail())
            .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        Pet pet = petRepository.save(Pet.builder()
            .name(requestDto.getName())
            .sex(requestDto.getSex())
            .age(requestDto.getAge().getMonths())
            .birthday(requestDto.getAge().getBirthday())
            .isSpayed(requestDto.getIsSpayed())
            .isPregnant(requestDto.getIsPregnant())
            .bodyWeight(requestDto.getBodyWeight())
            .activityLevel(requestDto.getActivityLevel())
            .user(user)
            .breed(breedRepository.findById(requestDto.getBreedId())
                .orElseThrow(() -> new CustomException(ErrorCode.BREED_NOT_FOUND)))
            .build());
        user.addPets(pet);
        Optional<Pet> representativePet = Optional.ofNullable(user.getRepresentativePet());
        if (representativePet.isEmpty()) {
            user.updateRepresentativePet(pet);
        }
        if (requestDto.getPetImage() != null) {
            petImageRepository.save(new PetImage(
                imageService.saveImage(requestDto.getPetImage()), pet));
            pet.setThumbnailPath(imageService.saveImage(
                imageService.resizeImage(requestDto.getPetImage(), RESIZE_TARGET_WIDTH)));
        }
        return new PetCreateResponseDto(pet);
    }

    @Transactional(readOnly = true)
    public List<PetInfoResponseDto> getPets(LoginUserInfo loginUserInfo) {
        User user = userRepository.findUserByEmailWithPetDetail(loginUserInfo.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        return user.getPets().stream().map(PetInfoResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<BreedsInfoResponseDto> provideBreedsInfo() {

        List<BreedsInfoResponseDto> breedsList = breedRepository.findAll().stream()
            .map(breed ->
                new BreedsInfoResponseDto(breed.getId(), breed.getName(),
                    breed.getSize().toString())
            ).collect(Collectors.toList());

        return breedsList;
    }

    @Transactional(readOnly = true)
    public PetDetailResponseDto getPetDetail(Long petId, LoginUserInfo loginUserInfo) {
        return new PetDetailResponseDto(petRepository.findByIdAndUserEmail(petId, loginUserInfo.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.PET_NOT_FOUND)));
    }
}
