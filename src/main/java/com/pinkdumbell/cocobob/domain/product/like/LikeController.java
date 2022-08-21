package com.pinkdumbell.cocobob.domain.product.like;

import com.pinkdumbell.cocobob.common.dto.CommonResponseDto;
import com.pinkdumbell.cocobob.config.annotation.loginuser.LoginUser;
import com.pinkdumbell.cocobob.domain.product.dto.FindAllResponseDto;
import com.pinkdumbell.cocobob.domain.product.like.dto.LikeRequestDto;
import com.pinkdumbell.cocobob.domain.user.dto.LoginUserInfo;
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
    public ResponseEntity<CommonResponseDto> like(@LoginUser LoginUserInfo loginUserInfo,@RequestBody LikeRequestDto likeRequestDto) {

        likeService.like(likeRequestDto,loginUserInfo);

        return ResponseEntity.ok(CommonResponseDto.builder()
            .status(HttpStatus.OK.value()).
            code("Like Product Success").
            message("좋아요 성공").
            data(null).
            build());
    }

    @DeleteMapping("")
    public ResponseEntity<CommonResponseDto> unLike(@LoginUser LoginUserInfo loginUserInfo,@RequestBody LikeRequestDto likeRequestDto) {

        likeService.unLike(likeRequestDto,loginUserInfo);

        return ResponseEntity.ok(CommonResponseDto.builder()
            .status(HttpStatus.OK.value()).
            code("Unlike Product Success").
            message("좋아요 해제 성공").
            data(null).
            build());
    }

}
