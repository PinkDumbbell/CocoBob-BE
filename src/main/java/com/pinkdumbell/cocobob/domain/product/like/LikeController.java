package com.pinkdumbell.cocobob.domain.product.like;

import com.pinkdumbell.cocobob.common.dto.CommonResponseDto;
import com.pinkdumbell.cocobob.domain.product.dto.FindAllResponseDto;
import com.pinkdumbell.cocobob.domain.product.like.dto.LikeRequestDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@ApiOperation("Product API")
@RequestMapping("/v1/likes")
@RequiredArgsConstructor
@RestController
public class LikeController {

    private final LikeService likeService;


    @PostMapping("")
    public ResponseEntity<CommonResponseDto> like(@RequestBody LikeRequestDto likeRequestDto) {

        return ResponseEntity.ok(CommonResponseDto.builder()
            .status(HttpStatus.OK.value()).
            code("Update Password Success").
            message("비밀번호가 성공적으로 변경되었습니다.").
            data(null).
            build());
    }

    @DeleteMapping("")
    public ResponseEntity<CommonResponseDto> unLike(@RequestBody LikeRequestDto likeRequestDto) {

        return ResponseEntity.ok(CommonResponseDto.builder()
            .status(HttpStatus.OK.value()).
            code("Update Password Success").
            message("비밀번호가 성공적으로 변경되었습니다.").
            data(null).
            build());
    }

}
