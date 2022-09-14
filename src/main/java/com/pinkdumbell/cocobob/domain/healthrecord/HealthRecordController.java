package com.pinkdumbell.cocobob.domain.healthrecord;

import com.pinkdumbell.cocobob.common.dto.CommonResponseDto;
import com.pinkdumbell.cocobob.domain.healthrecord.dto.HealthRecordCreateRequestDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@ApiOperation("HealthRecord API")
@RequiredArgsConstructor
@RequestMapping("/v1/health-record")
@RestController
public class HealthRecordController {

    private final HealthRecordService healthRecordService;
    @PostMapping("/pets/{petId}")
    public ResponseEntity<CommonResponseDto> createHealthRecord(
            @PathVariable Long petId,
            @ModelAttribute HealthRecordCreateRequestDto requestDto
    ) {
        healthRecordService.createHealthRecord(petId, requestDto);
        return ResponseEntity.ok(CommonResponseDto.builder()
                .status(HttpStatus.OK.value())
                .code("SUCCESS_TO_CREATE_HEALTH_RECORD")
                .message("건강 기록 생성을 성공했습니다.")
                .data(null)
                .build());
    }

    @GetMapping("/{healthRecordId}")
    public ResponseEntity<HealthRecordResponseClass.HealthRecordDetailResponseClass> getHealthRecord(
            @PathVariable Long healthRecordId
    ) {
        return ResponseEntity.ok(new HealthRecordResponseClass.HealthRecordDetailResponseClass(
                HttpStatus.OK.value(),
                "SUCCESS_TO_GET_HEALTH_RECORD",
                "건강 기록 불러오기를 성공했습니다.",
                healthRecordService.getHealthRecord(healthRecordId)));
    }
}
