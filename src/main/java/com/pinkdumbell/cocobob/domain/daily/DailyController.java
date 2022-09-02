package com.pinkdumbell.cocobob.domain.daily;

import com.pinkdumbell.cocobob.common.dto.CommonResponseDto;
import com.pinkdumbell.cocobob.config.annotation.loginuser.LoginUser;
import com.pinkdumbell.cocobob.domain.daily.dto.*;
import com.pinkdumbell.cocobob.domain.user.dto.LoginUserInfo;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
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

    // 데일리 생성
    @PostMapping("/pets/{petId}")
    public ResponseEntity<CommonResponseDto> createDaily(
            @ModelAttribute @Valid DailyCreateRequestDto requestDto,
            @LoginUser LoginUserInfo loginUserInfo,
            @PathVariable Long petId
    ) {
        dailyService.createDaily(requestDto, loginUserInfo, petId);
        return ResponseEntity.ok(CommonResponseDto.builder()
                        .status(HttpStatus.OK.value())
                        .code("SUCCESS CREATE DAILY")
                        .message("데일리 기록 생성을 성공하였습니다.")
                        .data(null)
                .build());
    }

    // 특정 데일리 조회
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

    @PutMapping("/{dailyId}")
    public void updateDaily() {

    }
    // 특정 데일리 삭제
    @DeleteMapping("/{dailyId}")
    public void deleteDaily() {

    }
}
