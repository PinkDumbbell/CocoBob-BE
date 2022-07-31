package com.pinkdumbell.cocobob.domain.product;

import com.pinkdumbell.cocobob.common.dto.CommonResponseDto;
import com.pinkdumbell.cocobob.domain.product.dto.FindAllResponseDto;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@ApiOperation("Product API")
@RequestMapping("/v1/products")
@RequiredArgsConstructor
@RestController
@Transactional(readOnly = true)
public class ProductController {

    private final ProductService productService;

    private class ProvideAllResponseClass extends CommonResponseDto<List<FindAllResponseDto>> {

        public ProvideAllResponseClass(int status, String code, String message,
            List<FindAllResponseDto> data) {
            super(status, code, message, data);
        }
    }

    @GetMapping("")
    public ResponseEntity<ProvideAllResponseClass> provideAll() {

        List<FindAllResponseDto> allProducts = productService.findAll();

        return ResponseEntity.ok(
            new ProvideAllResponseClass(HttpStatus.OK.value(), "SUCCESS LOAD PROUDCTS",
                "상품 가져오기 성공", allProducts));
    }
}
