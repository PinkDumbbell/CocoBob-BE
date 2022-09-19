package com.pinkdumbell.cocobob.domain.healthrecord;

import com.pinkdumbell.cocobob.common.dto.CommonResponseDto;
import com.pinkdumbell.cocobob.domain.healthrecord.dto.HealthRecordCreateRequestDto;
import com.pinkdumbell.cocobob.domain.healthrecord.dto.HealthRecordUpdateRequestDto;
import com.pinkdumbell.cocobob.domain.healthrecord.meal.dto.MealCreateRequestDto;
import com.pinkdumbell.cocobob.domain.healthrecord.meal.dto.MealUpdateRequestDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@ApiOperation("HealthRecord API")
@RequiredArgsConstructor
@RequestMapping("/v1/health-records")
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

    @PostMapping("/{healthRecordId}/meals")
    public ResponseEntity<CommonResponseDto> createMeal(
            @PathVariable Long healthRecordId,
            @RequestBody MealCreateRequestDto requestDto
    ) {
        healthRecordService.createMeal(healthRecordId, requestDto);
        return ResponseEntity.ok(CommonResponseDto.builder()
                        .status(HttpStatus.OK.value())
                        .code("SUCCESS_TO_CREATE_MEAL")
                        .message("식사 기록 생성을 성공했습니다.")
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

    @PutMapping("/{healthRecordId}")
    public ResponseEntity<CommonResponseDto> updateHealthRecord(
            @PathVariable Long healthRecordId,
            @ModelAttribute HealthRecordUpdateRequestDto requestDto
    ) {
        healthRecordService.updateHealthRecord(healthRecordId, requestDto);
        return ResponseEntity.ok(CommonResponseDto.builder()
                        .status(HttpStatus.OK.value())
                        .code("SUCCESS_TO_UPDATE_HEALTH_RECORD")
                        .message("건강 기록 수정을 성공했습니다.")
                        .data(null)
                .build());
    }

    @DeleteMapping("/{healthRecordId}")
    public ResponseEntity<CommonResponseDto> deleteHealthRecord(
            @PathVariable Long healthRecordId
    ) {
        healthRecordService.deleteHealthRecord(healthRecordId);
        return ResponseEntity.ok(CommonResponseDto.builder()
                        .status(HttpStatus.OK.value())
                        .code("SUCCESS_TO_DELETE_HEALTH_RECORD")
                        .message("건강 기록 삭제를 성공했습니다.")
                        .data(null)
                .build());
    }

    @PutMapping("/meals/{mealId}")
    public ResponseEntity<CommonResponseDto> updateMeal(
            @PathVariable Long mealId,
            @RequestBody MealUpdateRequestDto requestDto
    ) {
        healthRecordService.updateMeal(mealId, requestDto);
        return ResponseEntity.ok(CommonResponseDto.builder()
                        .status(HttpStatus.OK.value())
                        .code("SUCCESS_TO_UPDATE_MEAL")
                        .message("식사 기록 수정을 성공했습니다.")
                        .data(null)
                .build());
    }

    @DeleteMapping("/meals/{mealId}")
    public ResponseEntity<CommonResponseDto> deleteMeal(
            @PathVariable Long mealId
    ) {
        healthRecordService.deleteMeal(mealId);
        return ResponseEntity.ok(CommonResponseDto.builder()
                .status(HttpStatus.OK.value())
                .code("SUCCESS_TO_DELETE_MEAL")
                .message("식사 기록 삭제를 성공했습니다.")
                .data(null)
                .build());
    }
}
