package com.pinkdumbell.cocobob.domain.pet;

import com.pinkdumbell.cocobob.config.annotation.loginuser.LoginUser;
import com.pinkdumbell.cocobob.domain.pet.dto.BreedsInfoResponseDto;
import com.pinkdumbell.cocobob.common.dto.CommonResponseDto;
import com.pinkdumbell.cocobob.domain.pet.dto.PetCreateRequestDto;
import com.pinkdumbell.cocobob.domain.pet.dto.PetCreateResponseDto;
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

    private class RegisterResponsClass extends CommonResponseDto<PetCreateResponseDto> {

        public RegisterResponsClass(int status, String code, String message,
            PetCreateResponseDto data) {
            super(status, code, message, data);
        }
    }

    private class ProvideBreedsResponsClass extends CommonResponseDto<List<BreedsInfoResponseDto>> {

        public ProvideBreedsResponsClass(int status, String code, String message,
            List<BreedsInfoResponseDto> data) {
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
}
