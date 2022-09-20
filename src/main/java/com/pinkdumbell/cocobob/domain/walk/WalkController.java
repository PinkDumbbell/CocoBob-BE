package com.pinkdumbell.cocobob.domain.walk;

import com.pinkdumbell.cocobob.common.dto.CommonResponseDto;
import com.pinkdumbell.cocobob.domain.walk.dto.WalkCreateRequestDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
