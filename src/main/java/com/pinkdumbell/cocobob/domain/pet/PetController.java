package com.pinkdumbell.cocobob.domain.pet;

import com.pinkdumbell.cocobob.config.annotation.loginuser.LoginUser;
import com.pinkdumbell.cocobob.domain.pet.dto.PetCreateRequestDto;
import com.pinkdumbell.cocobob.domain.pet.dto.PetCreateResponseDto;
import com.pinkdumbell.cocobob.domain.user.dto.LoginUserInfo;
import com.pinkdumbell.cocobob.exception.ErrorResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@ApiOperation("Pet API")
@RequestMapping("v1/pets")
@RequiredArgsConstructor
@RestController
public class PetController {

    private final PetService petService;

    @ApiOperation(value = "register pet", notes = "반려동물 등록")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "", response = PetCreateResponseDto.class),
            @ApiResponse(code = 400, message = "", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR", response = ErrorResponse.class)

    })
    @PostMapping("")
    public ResponseEntity<PetCreateResponseDto> register(@LoginUser LoginUserInfo loginUserInfo, @ModelAttribute @Valid PetCreateRequestDto requestDto) {
        return ResponseEntity.ok(petService.register(loginUserInfo, requestDto));
    }
}
