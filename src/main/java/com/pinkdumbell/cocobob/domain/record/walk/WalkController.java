package com.pinkdumbell.cocobob.domain.record.walk;

import com.pinkdumbell.cocobob.common.dto.CommonResponseDto;
import com.pinkdumbell.cocobob.domain.record.walk.dto.WalkCreateRequestDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@ApiOperation("Walk API")
@RequestMapping("/v1/walks")
@RequiredArgsConstructor
@RestController
public class WalkController {
    private final WalkService walkService;

    @PostMapping("/pets/{petId}")
    public ResponseEntity<CommonResponseDto> createWalk(
            @PathVariable Long petId,
            @ModelAttribute WalkCreateRequestDto requestDto
    ) {
        walkService.createWalk(petId, requestDto);
        return ResponseEntity.ok(CommonResponseDto.builder()
                        .status(HttpStatus.OK.value())
                        .code("SUCCESS_TO_CREATE_WALK")
                        .message("산책 기록을 생성했습니다.")
                        .data(null)
                .build());
    }

    @GetMapping("/pets/{petId}")
    public ResponseEntity<WalkResponseClass.WalksBriefInfoClass> getWalksByDate(
            @PathVariable Long petId,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date
    ) {
        return ResponseEntity.ok(new WalkResponseClass.WalksBriefInfoClass(
                HttpStatus.OK.value(),
                "SUCCESS_TO_GET_WALKS",
                "산책기록 리스트를 불러왔습니다.",
                walkService.getWalks(petId, date)
        ));
    }
    @GetMapping("/{walkId}")
    public ResponseEntity<WalkResponseClass.WalkDetailResponseClass> getWalk(
            @PathVariable Long walkId
    ) {
        return ResponseEntity.ok(new WalkResponseClass.WalkDetailResponseClass(
                HttpStatus.OK.value(),
                "SUCCESS_TO_GET_WALK",
                "산책기록을 불러왔습니다",
                walkService.getWalk(walkId)
        ));
    }

    @DeleteMapping("/{walkId}")
    public ResponseEntity<CommonResponseDto> deleteWalk(
            @PathVariable Long walkId
    ) {
        walkService.deleteWalk(walkId);
        return ResponseEntity.ok(CommonResponseDto.builder()
                .status(HttpStatus.OK.value())
                .code("SUCCESS_TO_DELETE_WALK")
                .message("산책 기록을 삭제했습니다.")
                .data(null)
                .build());
    }
}
