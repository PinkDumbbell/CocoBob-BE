package com.pinkdumbell.cocobob.domain.pet;

import com.pinkdumbell.cocobob.common.ImageService;
import com.pinkdumbell.cocobob.domain.pet.breed.BreedRepository;
import com.pinkdumbell.cocobob.domain.pet.breed.BreedSize;
import com.pinkdumbell.cocobob.domain.pet.dto.*;
import com.pinkdumbell.cocobob.domain.pet.image.PetImage;
import com.pinkdumbell.cocobob.domain.pet.image.PetImageRepository;
import com.pinkdumbell.cocobob.domain.product.dto.ProductSpecificSearchWithLikeDto;
import com.pinkdumbell.cocobob.domain.user.User;
import com.pinkdumbell.cocobob.domain.user.UserRepository;
import com.pinkdumbell.cocobob.domain.user.dto.LoginUserInfo;
import com.pinkdumbell.cocobob.exception.CustomException;
import com.pinkdumbell.cocobob.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
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

        if (user.getRepresentativePetId() == null) {
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

    @Cacheable(cacheNames = "breedsInfo")
    @Transactional(readOnly = true)
    public List<BreedsInfoResponseDto> provideBreedsInfo() {

        return breedRepository.findAll().stream()
            .map(breed ->
                new BreedsInfoResponseDto(breed.getId(), breed.getName(),
                    breed.getSize().toString())
            ).collect(Collectors.toList());

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
    public ProductSpecificSearchWithLikeDto makeRecommendationWithAge(Long petId) {

        Pet pet = petRepository.findById(petId).orElseThrow(() -> {
            throw new CustomException(ErrorCode.PET_NOT_FOUND);
        });
        ProductSpecificSearchWithLikeDto productSpecificSearchDto = ProductSpecificSearchWithLikeDto.builder()
            .aafco(true).build();

        productSpecificSearchDto.initTypes();

        //초소형 성장기
        if (pet.getBreed().getSize().equals(BreedSize.초소형) && pet.getAge() <= 7) {
            productSpecificSearchDto.addType("growing");
            //소형
        } else if (pet.getBreed().getSize().equals(BreedSize.소형) && pet.getAge() <= 10) {
            productSpecificSearchDto.addType("growing");
            //중형 성장기
        } else if (pet.getBreed().getSize().equals(BreedSize.중형) && pet.getAge() <= 12) {
            productSpecificSearchDto.addType("growing");
            //대형 성장기
        } else if (pet.getBreed().getSize().equals(BreedSize.대형) && pet.getAge() <= 18) {
            productSpecificSearchDto.addType("growing");
            //초대형 성장기
        } else if (pet.getBreed().getSize().equals(BreedSize.초대형) && pet.getAge() <= 24) {
            productSpecificSearchDto.addType("growing");
        } //견종 노년기
        else if (pet.getAge() >= 72) {
            productSpecificSearchDto.addType("aged");
        }
        return productSpecificSearchDto;
    }

    @Transactional
    public ProductSpecificSearchWithLikeDto makeRecommendationWithPregnancy(Long petId) {
        Pet pet = petRepository.findById(petId).orElseThrow(() -> {
            throw new CustomException(ErrorCode.PET_NOT_FOUND);
        });

        ProductSpecificSearchWithLikeDto productSpecificSearchDto = ProductSpecificSearchWithLikeDto.builder()
            .aafco(true).build();

        productSpecificSearchDto.initTypes();

        if (pet.getIsPregnant()) {
            productSpecificSearchDto.addType("pregnant");
        }

        return productSpecificSearchDto;
    }

    @Transactional
    public void deletePet(Long petId, LoginUserInfo loginUserInfo) {

        removePet(setBeforeDeletePet(petId, loginUserInfo));
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public Pet setBeforeDeletePet(Long petId, LoginUserInfo loginUserInfo) {
        User user = userRepository.findUserByEmailWithPet(loginUserInfo.getEmail())
            .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (user.getRepresentativePetId().equals(petId)) {
            throw new CustomException(ErrorCode.FAIL_TO_DELETE_REPRESENTATIVE_PET);
        }

        Pet pet = getPetOfUserByPetId(petId, user);
        Optional<PetImage> petImage = petImageRepository.findPetImageByPet(pet);
        if (petImage.isPresent()) {
            deleteImage(petImage, pet);
        }
        petRepository.flush();
        return pet;
    }

    @Transactional
    public void removePet(Pet pet) {

        petRepository.delete(pet);
    }

    private Pet getPetOfUserByPetId(Long petId, User user) {
        List<Pet> collect = user.getPets().stream().filter(
            (pet) -> petId.equals(pet.getId())
        ).collect(Collectors.toList());

        if (collect.size() == 0) {
            throw new CustomException(ErrorCode.PET_NOT_FOUND);
        }
        return collect.get(0);
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
        if (pet.getBreed().getId().equals(requestDto.getBreedId())) {
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
            imageService.deleteImage(createImageName(PET_IMAGE_DIR, petId));
            imageService.deleteImage(createImageName(PET_THUMBNAIL_DIR, petId));
            petImage.get().updatePath(
                imageService.saveImage(
                    newImage,
                    createImageName(PET_IMAGE_DIR, petId)
                )
            );
        } else {
            String imageName = createImageName(PET_IMAGE_DIR, petId);
            petImageRepository.save(new PetImage(
                imageService.saveImage(newImage, imageName),
                pet
            ));
        }
        pet.setThumbnailPath(imageService.saveImage(
            imageService.resizeImage(newImage, RESIZE_TARGET_WIDTH),
            createImageName(PET_THUMBNAIL_DIR, petId)
        ));
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
