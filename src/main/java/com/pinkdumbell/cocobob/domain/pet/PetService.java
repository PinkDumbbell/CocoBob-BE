package com.pinkdumbell.cocobob.domain.pet;

import com.pinkdumbell.cocobob.common.ImageService;
import com.pinkdumbell.cocobob.domain.pet.breed.BreedRepository;
import com.pinkdumbell.cocobob.domain.pet.breed.BreedSize;
import com.pinkdumbell.cocobob.domain.pet.dto.*;
import com.pinkdumbell.cocobob.domain.pet.image.PetImage;
import com.pinkdumbell.cocobob.domain.pet.image.PetImageRepository;
import com.pinkdumbell.cocobob.domain.product.dto.ProductSpecificSearchDto;
import com.pinkdumbell.cocobob.domain.user.User;
import com.pinkdumbell.cocobob.domain.user.UserRepository;
import com.pinkdumbell.cocobob.domain.user.dto.LoginUserInfo;
import com.pinkdumbell.cocobob.exception.CustomException;
import com.pinkdumbell.cocobob.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
    private final String PET_IMAGE_DIR = "petImage";
    private final String PET_THUMBNAIL_DIR = "petImage/thumbnail";

    @Transactional
    public PetCreateResponseDto register(LoginUserInfo loginUserInfo,
        PetCreateRequestDto requestDto) {
        User user = userRepository.findByEmail(loginUserInfo.getEmail())
            .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        Pet pet = savePet(requestDto, user);
        Optional<Pet> representativePet = Optional.ofNullable(user.getRepresentativePet());
        if (representativePet.isEmpty()) {
            user.updateRepresentativePet(pet);
        }
        saveImage(requestDto.getPetImage(), pet);
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
        return new PetDetailResponseDto(
            petRepository.findByIdAndUserEmail(petId, loginUserInfo.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.PET_NOT_FOUND)));
    }

    @Transactional
    public PetCreateResponseDto updatePet(Long petId, LoginUserInfo loginUserInfo,
        PetUpdateRequestDto requestDto) {
        Pet pet = petRepository.findByIdAndUserEmail(petId, loginUserInfo.getEmail())
            .orElseThrow(() -> new CustomException(ErrorCode.PET_NOT_FOUND));

        updatePetInfo(pet, requestDto);

        Optional<PetImage> petImage = petImageRepository.findPetImageByPet(pet);
        if (requestDto.getPetImage() != null) {
            updateImage(requestDto.getPetImage(), petImage, pet);
        } else {
            if (requestDto.getIsImageJustDeleted()) {
                deleteImage(petImage, pet);
            }
        }

        return new PetCreateResponseDto(pet);
    }

    @Transactional
    public ProductSpecificSearchDto makeRecommendationWithAge(Long petId) {

        Pet pet = petRepository.findById(petId).orElseThrow(() -> {
            throw new CustomException(ErrorCode.PET_NOT_FOUND);
        });
        ProductSpecificSearchDto productSpecificSearchDto = ProductSpecificSearchDto.builder()
            .aafco(true).build();

        //초소형 성장기
        if (pet.getBreed().getSize().equals(BreedSize.초소형) && pet.getAge() <= 7) {
            productSpecificSearchDto.setGrowing(true);
            //소형
        } else if (pet.getBreed().getSize().equals(BreedSize.소형) && pet.getAge() <= 10) {
            productSpecificSearchDto.setGrowing(true);
            //중형 성장기
        } else if (pet.getBreed().getSize().equals(BreedSize.중형) && pet.getAge() <= 12) {
            productSpecificSearchDto.setGrowing(true);
            //대형 성장기
        } else if (pet.getBreed().getSize().equals(BreedSize.대형) && pet.getAge() <= 18) {
            productSpecificSearchDto.setGrowing(true);
            //초대형 성장기
        } else if (pet.getBreed().getSize().equals(BreedSize.초대형) && pet.getAge() <= 24) {
            productSpecificSearchDto.setGrowing(true);
        } //견종 노년기
        else if (pet.getAge() >= 72) {
            productSpecificSearchDto.setAged(true);
        }
        return productSpecificSearchDto;
    }

    private String createImageName(String prefix, Long petId) {
        return prefix + "/" + petId;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public Pet savePet(PetCreateRequestDto requestDto, User user) {
        Pet pet = petRepository.save(Pet.builder().build().save(requestDto, user,
            breedRepository.findById(requestDto.getBreedId())
                .orElseThrow(() -> new CustomException(ErrorCode.BREED_NOT_FOUND))
        ));
        user.addPets(pet);
        return pet;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void saveImage(MultipartFile image, Pet pet) {
        if (image != null) {
            petImageRepository.save(new PetImage(
                imageService.saveImage(
                    image,
                    createImageName(PET_IMAGE_DIR, pet.getId())),
                pet
            ));
            pet.setThumbnailPath(imageService.saveImage(
                imageService.resizeImage(
                    image, RESIZE_TARGET_WIDTH),
                createImageName(PET_THUMBNAIL_DIR, pet.getId())));
        }
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void updatePetInfo(Pet pet, PetUpdateRequestDto requestDto) {
        if (pet.getBreed().getId() == requestDto.getBreedId()) {
            pet.update(requestDto, pet.getBreed());
        } else {
            pet.update(
                requestDto,
                breedRepository.findById(requestDto.getBreedId())
                    .orElseThrow(() -> new CustomException(ErrorCode.BREED_NOT_FOUND))
            );
        }
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void updateImage(MultipartFile newImage, Optional<PetImage> petImage, Pet pet) {
        Long petId = pet.getId();
        if (petImage.isPresent()) {
            String postfix = "/" + petId;
            imageService.deleteImage(PET_IMAGE_DIR + postfix);
            imageService.deleteImage(PET_THUMBNAIL_DIR + postfix);
            imageService.saveImage(newImage, PET_IMAGE_DIR + postfix);
            imageService.saveImage(
                imageService.resizeImage(newImage, RESIZE_TARGET_WIDTH),
                createImageName(PET_THUMBNAIL_DIR, petId));
        } else {
            String imageName = createImageName(PET_IMAGE_DIR, petId);
            petImageRepository.save(new PetImage(
                imageService.saveImage(newImage, imageName),
                pet
            ));
            pet.setThumbnailPath(createImageName(PET_THUMBNAIL_DIR, petId));
        }
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void deleteImage(Optional<PetImage> petImage, Pet pet) {
        Long petId = pet.getId();
        imageService.deleteImage(PET_IMAGE_DIR + "/" + petId);
        imageService.deleteImage(PET_THUMBNAIL_DIR + "/" + petId);
        petImageRepository.delete(petImage.get());
        pet.setThumbnailPath(null);
    }
}
