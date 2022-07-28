package com.pinkdumbell.cocobob.domain.pet;

import com.pinkdumbell.cocobob.domain.pet.dto.BreedsInfoResponseDto;
import com.pinkdumbell.cocobob.common.dto.CommonResponseDto;
import com.pinkdumbell.cocobob.domain.pet.dto.PetCreateRequestDto;
import com.pinkdumbell.cocobob.domain.pet.dto.PetCreateResponseDto;
import com.pinkdumbell.cocobob.exception.ErrorResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
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

    @ApiOperation(value = "register pet", notes = "반려동물 등록")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "", response = PetCreateResponseDto.class),
            @ApiResponse(code = 400, message = "", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR", response = ErrorResponse.class)

    })
    @PostMapping("")
    public ResponseEntity<CommonResponseDto<PetCreateResponseDto>> register(@ModelAttribute @Valid PetCreateRequestDto requestDto) {
        return ResponseEntity.ok(CommonResponseDto.<PetCreateResponseDto>builder().
            status(200).
            code("SUCESS REGISTER").
            message("회원가입 정상처리").
            data(petService.register(requestDto)).
            build());
    }
    @ApiOperation(value = "provide breeds info", notes = "반려동물 견종 정보 제공")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "", response = BreedsInfoResponseDto.class),
            @ApiResponse(code = 400, message = "", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR", response = ErrorResponse.class)

    })
    @GetMapping("/breeds")
    public ResponseEntity<List<BreedsInfoResponseDto>> provideBreedsInfo(){

        return ResponseEntity.ok(petService.provideBreedsInfo());
    }
}
