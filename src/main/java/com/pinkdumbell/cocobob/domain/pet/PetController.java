package com.pinkdumbell.cocobob.domain.pet;

import com.pinkdumbell.cocobob.config.annotation.loginuser.LoginUser;
import com.pinkdumbell.cocobob.domain.pet.dto.*;
import com.pinkdumbell.cocobob.common.dto.CommonResponseDto;
import com.pinkdumbell.cocobob.domain.user.dto.LoginUserInfo;
import com.pinkdumbell.cocobob.exception.ErrorResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@ApiOperation("Pet API")
@RequestMapping("v1/pets")
@RequiredArgsConstructor
@RestController
public class PetController {

    private final PetService petService;

    private static class RegisterResponsClass extends CommonResponseDto<PetCreateResponseDto> {

        public RegisterResponsClass(int status, String code, String message,
            PetCreateResponseDto data) {
            super(status, code, message, data);
        }
    }

    private static class ProvideBreedsResponsClass extends
        CommonResponseDto<List<BreedsInfoResponseDto>> {

        public ProvideBreedsResponsClass(int status, String code, String message,
            List<BreedsInfoResponseDto> data) {
            super(status, code, message, data);
        }
    }

    private static class PetDetailResponseClass extends CommonResponseDto<PetDetailResponseDto> {
        public PetDetailResponseClass(int status, String code, String message, PetDetailResponseDto data) {
            super(status, code, message, data);
        }
    }

    private static class PetInfoResponseClass extends CommonResponseDto<List<PetInfoResponseDto>> {
        public PetInfoResponseClass(int status, String code, String message, List<PetInfoResponseDto> data) {
            super(status, code, message, data);
        }
    }

    @ApiOperation(value = "register pet", notes = "반려동물 등록")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "", response = RegisterResponsClass.class),
        @ApiResponse(code = 400, message = "", response = ErrorResponse.class),
        @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR", response = ErrorResponse.class)

    })
    @PostMapping("")
    public ResponseEntity<RegisterResponsClass> register(@LoginUser LoginUserInfo loginUserInfo,
        @ModelAttribute @Valid PetCreateRequestDto requestDto) {

        return ResponseEntity.ok(new RegisterResponsClass(HttpStatus.OK.value(),
            "SUCCESS REGISTER",
            "반려동물 등록 정상처리",
            petService.register(loginUserInfo, requestDto)));
    }

    @ApiOperation(value = "get all pets related to user", notes = "사용자가 등록한 모든 반려동물 불러오기")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "", response = PetInfoResponseClass.class),
            @ApiResponse(code = 404, message = "USER_NOT_FOUND", response = ErrorResponse.class)
    })
    @GetMapping("")
    public ResponseEntity<PetInfoResponseClass> getPets(@LoginUser LoginUserInfo loginUserInfo) {
        return ResponseEntity.ok(new PetInfoResponseClass(HttpStatus.OK.value(),
        "SUCCESS TO GET PETS",
        "모든 반려동물 불러오기 성공",
        petService.getPets(loginUserInfo)));
    }

    @ApiOperation(value = "provide breeds info", notes = "반려동물 견종 정보 제공")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "", response = BreedsInfoResponseDto.class),
        @ApiResponse(code = 400, message = "", response = ErrorResponse.class),
        @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR", response = ErrorResponse.class)

    })
    @GetMapping("/breeds")
    public ResponseEntity<ProvideBreedsResponsClass> provideBreedsInfo() {

        return ResponseEntity.ok(new ProvideBreedsResponsClass(HttpStatus.OK.value(),
            "SUCCESS PROVIDE BREEDS",
            "반려 견종 가져오기 처리 성공",
            petService.provideBreedsInfo()));
    }

    @ApiOperation(value = "get pet detail", notes = "반려동물 상세정보 불러오기")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "", response = PetDetailResponseDto.class),
            @ApiResponse(code = 404, message = "PET_NOT_FOUND", response = ErrorResponse.class)
    })
    @GetMapping("/{petId}")
    public ResponseEntity<PetDetailResponseClass> getPetDetail(
            @PathVariable("petId") Long petId,
            @LoginUser LoginUserInfo loginUserInfo) {
        return ResponseEntity.ok(new PetDetailResponseClass(
                HttpStatus.OK.value(),
                "SUCCESS TO GET PET DETAIL",
                "반려동물 상세정보 가져오기 성공",
                petService.getPetDetail(petId, loginUserInfo)
        ));
    }
}
