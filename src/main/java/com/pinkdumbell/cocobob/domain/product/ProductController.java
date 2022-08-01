package com.pinkdumbell.cocobob.domain.product;

import com.pinkdumbell.cocobob.common.dto.CommonResponseDto;
import com.pinkdumbell.cocobob.domain.product.dto.FindAllResponseDto;
import com.pinkdumbell.cocobob.domain.product.dto.ProductDetailResponseDto;
import io.swagger.annotations.ApiOperation;

import java.util.List;

import lombok.RequiredArgsConstructor;


import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@ApiOperation("Product API")
@RequestMapping("/v1/products")
@RequiredArgsConstructor
@RestController
public class ProductController {

    private final ProductService productService;

    private static class ProvideAllResponseClass extends
        CommonResponseDto<FindAllResponseDto> {


        public ProvideAllResponseClass(int status, String code, String message,
            FindAllResponseDto data) {
            super(status, code, message, data);
        }
    }

    private static class ProductDetailResponseClass extends
        CommonResponseDto<ProductDetailResponseDto> {

        public ProductDetailResponseClass(int status, String code, String message,
            ProductDetailResponseDto data) {
            super(status, code, message, data);
        }
    }

    @GetMapping("")
    public ResponseEntity<ProvideAllResponseClass> productAll(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return ResponseEntity.ok(
            new ProvideAllResponseClass(HttpStatus.OK.value(), "SUCCESS LOAD PROUDCTS",
                "상품 가져오기 성공", productService.findProductAll(pageRequest)));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDetailResponseClass> productDetail(@PathVariable Long productId) {

        return ResponseEntity.ok(
            new ProductDetailResponseClass(HttpStatus.OK.value(), "SUCCESS LOAD PROUDCT",
                "상품 가져오기 성공", productService.findProductDetailById(productId)));
    }

}
