package com.pinkdumbell.cocobob.domain.daily;

import com.pinkdumbell.cocobob.common.dto.CommonResponseDto;
import com.pinkdumbell.cocobob.config.annotation.loginuser.LoginUser;
import com.pinkdumbell.cocobob.domain.daily.dto.*;
import com.pinkdumbell.cocobob.domain.user.dto.LoginUserInfo;
import com.pinkdumbell.cocobob.exception.ErrorResponse;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@ApiOperation("Daily API")
@RequestMapping("/v1/dailys")
@RequiredArgsConstructor
@RestController
public class DailyController {

    private final DailyService dailyService;

    @ApiOperation(value = "데일리 기록 생성")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS_CREATE_DAILY", response = ResponseEntity.class),
            @ApiResponse(code = 400, message = "NOT_IMAGE", response = ErrorResponse.class)
    })
    @PostMapping("/pets/{petId}")
    public ResponseEntity<CommonResponseDto> createDaily(
            @ModelAttribute @Valid DailyCreateRequestDto requestDto,
            @LoginUser LoginUserInfo loginUserInfo,
            @PathVariable Long petId
    ) {
        dailyService.createDaily(requestDto, loginUserInfo, petId);
        return ResponseEntity.ok(CommonResponseDto.builder()
                        .status(HttpStatus.OK.value())
                        .code("SUCCESS_CREATE_DAILY")
                        .message("데일리 기록 생성을 성공하였습니다.")
                        .data(null)
                .build());
    }

    @ApiOperation(value = "데일리 아이디로 데일리 기록 단건 조회")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS_TO_GET_DAILY_RECORD", response = ResponseEntity.class),
            @ApiResponse(code = 400, message = "DAILY_NOT_FOUND", response = ErrorResponse.class)
    })
    @GetMapping("/{dailyId}")
    public ResponseEntity<DailyResponseClass.DailyDetailResponseClass> getDaily(
            @PathVariable Long dailyId
    ) {
        return ResponseEntity.ok(new DailyResponseClass.DailyDetailResponseClass(
                HttpStatus.OK.value(),
                "SUCCESS_TO_GET_DAILY_RECORD",
                "데일리 기록 조회를 성공했습니다",
                dailyService.getDaily(dailyId)
        ));
    }

    @ApiOperation(value = "데일리 기록 수정")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS_TO_UPDATE_DAILY_RECORD", response = ResponseEntity.class),
            @ApiResponse(code = 400, message = "DAILY_NOT_FOUND", response = ErrorResponse.class)
    })
    @PutMapping("/{dailyId}")
    public ResponseEntity<CommonResponseDto> updateDaily(
            @ModelAttribute DailyUpdateRequestDto requestDto,
            @PathVariable Long dailyId
    ) {
        dailyService.updateDaily(requestDto, dailyId);
        return ResponseEntity.ok(CommonResponseDto.builder()
                        .status(HttpStatus.OK.value())
                        .code("SUCCESS_TO_UPDATE_DAILY_RECORD")
                        .message("데일리 기록 수정을 성공했습니다")
                        .data(null)
                .build());
    }

    @ApiOperation(value = "데일리 기록 삭제")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS_TO_DELETE_DAILY_RECORD", response = ResponseEntity.class),
            @ApiResponse(code = 400, message = "DAILY_NOT_FOUND", response = ErrorResponse.class)
    })
    @DeleteMapping("/{dailyId}")
    public ResponseEntity<CommonResponseDto> deleteDaily(
            @PathVariable Long dailyId
    ) {
        dailyService.deleteDaily(dailyId);
        return ResponseEntity.ok(CommonResponseDto.builder()
                .status(HttpStatus.OK.value())
                .code("SUCCESS_TO_DELETE_DAILY_RECORD")
                .message("데일리 기록 삭제를 성공했습니다")
                .data(null)
                .build()
        );
    }
}
