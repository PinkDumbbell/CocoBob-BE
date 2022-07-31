package com.pinkdumbell.cocobob.domain.product;

import com.pinkdumbell.cocobob.common.dto.CommonResponseDto;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@ApiOperation("Product API")
@RequestMapping("/v1/products")
@RequiredArgsConstructor
@RestController
public class ProductController {


    @GetMapping("")
    public ResponseEntity<CommonResponseDto> provideAll(){

        return ResponseEntity.ok(CommonResponseDto.builder().
            status(HttpStatus.OK.value()).
            code("SUCCESS LOAD PRODUCTS").

            build());
    }
}
