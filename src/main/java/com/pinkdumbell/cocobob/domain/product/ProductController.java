package com.pinkdumbell.cocobob.domain.product;

import com.pinkdumbell.cocobob.common.dto.CommonResponseDto;
import com.pinkdumbell.cocobob.config.annotation.loginuser.LoginUser;
import com.pinkdumbell.cocobob.domain.pet.PetService;
import com.pinkdumbell.cocobob.domain.product.dto.FindAllResponseDto;
import com.pinkdumbell.cocobob.domain.product.dto.ProductDetailResponseDto;
import com.pinkdumbell.cocobob.domain.product.dto.ProductSimpleResponseDto;
import com.pinkdumbell.cocobob.domain.product.dto.ProductSpecificSearchDto;
import com.pinkdumbell.cocobob.domain.user.dto.LoginUserInfo;
import com.pinkdumbell.cocobob.exception.ErrorResponse;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

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
    private final PetService petService;

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


    @ApiOperation(value = "productDetail", notes = "상품 정보 상세 조회")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "", response = ProductDetailResponseClass.class),
        @ApiResponse(code = 400, message = "", response = ErrorResponse.class),
        @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR", response = ErrorResponse.class)

    })
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDetailResponseClass> productDetail(@PathVariable Long productId,
        @LoginUser LoginUserInfo loginUserInfo) {

        return ResponseEntity.ok(
            new ProductDetailResponseClass(HttpStatus.OK.value(), "SUCCESS LOAD PROUDCT",
                "상품 가져오기 성공",
                productService.findProductDetailById(productId, loginUserInfo.getEmail())));
    }

    @ApiOperation(value = "productSpecificSearchDto", notes = "상품 정보 조회")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "", response = ProvideAllResponseClass.class),
        @ApiResponse(code = 400, message = "", response = ErrorResponse.class),
        @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR", response = ErrorResponse.class)

    })
    @ApiImplicitParams({
        @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
            value = "페이지 번호(0...N)"),
        @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
            value = "페이지 크기"),
        @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
            value = "정렬(사용법: 컬럼명,ASC|DESC)")
    })
    @GetMapping("/search")
    public ResponseEntity<ProvideAllResponseClass> searchAllProducts(
        ProductSpecificSearchDto productSpecificSearchDto, Pageable pageable) {

        return ResponseEntity.ok(
            new ProvideAllResponseClass(HttpStatus.OK.value(), "SUCCESS LOAD PRODUCT",
                "상품 검색 성공",
                productService.elasticSearchProducts(productSpecificSearchDto, pageable)));
    }

    @ApiOperation(value = "searchAllProductsWithLikes", notes = "상품 정보 조회(좋아요 갯수와 사용자가 좋아하는 것도 표시)")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "", response = ProvideAllResponseClass.class),
        @ApiResponse(code = 400, message = "", response = ErrorResponse.class),
        @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR", response = ErrorResponse.class)

    })
    @ApiImplicitParams({
        @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
            value = "페이지 번호(0...N)"),
        @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
            value = "페이지 크기"),
        @ApiImplicitParam(name = "sortCriteria", dataType = "string", paramType = "query",
            value = "정렬(사용법: 컬럼명,ASC|DESC)")
    })
    @GetMapping("/search/likes")
    public ResponseEntity<ProvideAllResponseClass> searchAllProductsWithLikes(
        ProductSpecificSearchDto productSpecificSearchDto, @LoginUser LoginUserInfo loginUserInfo,
        Pageable pageable) {

        return ResponseEntity.ok(
            new ProvideAllResponseClass(HttpStatus.OK.value(), "SUCCESS LOAD PRODUCT",
                "상품 검색 성공",
                productService.queryDslSearchProducts(productSpecificSearchDto,
                    loginUserInfo.getEmail(), pageable)));
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
            value = "페이지 번호(0...N)"),
        @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
            value = "페이지 크기"),
        @ApiImplicitParam(name = "petId", dataType = "integer", paramType = "query",
            value = "반려동물 Id"),
        @ApiImplicitParam(name = "type", value = "추천 기준(aged | pregnancy)", required = true, dataType = "string", paramType = "path", defaultValue = ""),
        @ApiImplicitParam(name = "sortCriteria", dataType = "string", paramType = "query",
            value = "정렬(사용법: 컬럼명,ASC|DESC)"),
    })
    @GetMapping("/recommendation/{type}")
    public ResponseEntity<ProvideAllResponseClass> recommendWithAge(
        Long petId, @LoginUser LoginUserInfo loginUserInfo, @PathVariable String type,
        Pageable pageable) {

        ProductSpecificSearchDto searchCondition = ProductSpecificSearchDto.builder().aafco(true)
            .build();

        if (type.contains("aged")) {
            searchCondition = petService.makeRecommendationWithAge(petId);
        } else if (type.contains("pregnancy")) {
            searchCondition = petService.makeRecommendationWithPregnancy(petId);
        }

        return ResponseEntity.ok(
            new ProvideAllResponseClass(HttpStatus.OK.value(),
                "SUCCESS LOAD RECOMMENDATION PRODUCT",
                "추천 상품 검색 성공",
                productService.queryDslSearchProducts(searchCondition,
                    loginUserInfo.getEmail(), pageable)));
    }

    @ApiOperation(value = "provideWishList", notes = "유저가 좋아요 누른 상품들 조회")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "", response = ProvideAllResponseClass.class),
        @ApiResponse(code = 400, message = "", response = ErrorResponse.class),
        @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR", response = ErrorResponse.class)

    })
    @ApiImplicitParams({
        @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
            value = "페이지 번호(0...N)"),
        @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
            value = "페이지 크기"),
        @ApiImplicitParam(name = "petId", dataType = "integer", paramType = "query",
            value = "반려동물 Id"),
        @ApiImplicitParam(name = "sortCriteria", dataType = "string", paramType = "query",
            value = "정렬(사용법: 컬럼명,ASC|DESC)"),
    })
    @GetMapping("/wishlist")
    public ResponseEntity<ProvideAllResponseClass> provideWishList(
        @LoginUser LoginUserInfo loginUserInfo, Pageable pageable) {

        return ResponseEntity.ok(
            new ProvideAllResponseClass(HttpStatus.OK.value(),
                "SUCCESS LOAD WISH LIST PRODUCT",
                "찜한 상품 불러오기 성공",
                productService.findAllWishList(loginUserInfo.getEmail(), pageable)));

    }

}
