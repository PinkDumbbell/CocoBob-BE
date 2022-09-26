package com.pinkdumbell.cocobob.domain.record.walk;

import com.pinkdumbell.cocobob.common.ImageService;
import com.pinkdumbell.cocobob.domain.pet.Pet;
import com.pinkdumbell.cocobob.domain.pet.PetRepository;
import com.pinkdumbell.cocobob.domain.record.walk.dto.WalkCreateRequestDto;
import com.pinkdumbell.cocobob.domain.record.walk.dto.WalkDetailResponseDto;
import com.pinkdumbell.cocobob.exception.CustomException;
import com.pinkdumbell.cocobob.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class WalkService {
    private final WalkRepository walkRepository;
    private final ImageService imageService;
    private final PetRepository petRepository;
    private static final String WALK_RECORD_IMAGE_PREFIX = "walk-record/";

    @Transactional
    public void createWalk(Long petId, WalkCreateRequestDto requestDto) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new CustomException(ErrorCode.PET_NOT_FOUND));
        Walk walk = walkRepository.save(Walk.builder()
                        .startedAt(requestDto.getStartedAt())
                        .finishedAt(requestDto.getFinishedAt())
                        .distance(requestDto.getDistance())
                        .totalTime(requestDto.getTotalTime())
                        .pet(pet)
                        .date(requestDto.getDate())
                        .spentKcal(requestDto.getSpentKcal())
                .build());
        if (requestDto.getPhoto() == null) {
            return;
        }
        savePhoto(walk, requestDto.getPhoto());
    }

    @Transactional
    public void savePhoto(Walk walk, MultipartFile photo) {
        walk.setPhotoPath(
                imageService.saveImage(
                        photo,
                        WALK_RECORD_IMAGE_PREFIX + walk.getId() + "_" + UUID.randomUUID()
                )
        );
    }

    @Transactional(readOnly = true)
    public WalkDetailResponseDto getWalk(Long walkId) {
        return new WalkDetailResponseDto(walkRepository.findById(walkId)
                .orElseThrow(() -> new CustomException(ErrorCode.WALK_RECORD_NOT_FOUND)));

    }

    @Transactional
    public void deleteWalk(Long walkId) {
        Walk walk = walkRepository.findById(walkId)
                .orElseThrow(() -> new CustomException(ErrorCode.WALK_RECORD_NOT_FOUND));
        walkRepository.delete(walk);
        if (walk.getPhotoPath() == null)
            return;
        deleteImage(walk.getPhotoPath());
    }

    private void deleteImage(String photoPath) {
        final String amazonPattern = "amazonaws.com/";
        imageService.deleteImage(photoPath.split(amazonPattern)[1]);
    }
}
