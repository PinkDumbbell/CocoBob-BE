package com.pinkdumbell.cocobob.domain.product;

import com.pinkdumbell.cocobob.domain.product.dto.FindAllResponseDto;
import com.pinkdumbell.cocobob.domain.product.dto.ProductDetailResponseDto;

import com.pinkdumbell.cocobob.domain.product.dto.ProductKeywordDto;
import com.pinkdumbell.cocobob.domain.product.dto.ProductSpecificSearchDto;
import com.pinkdumbell.cocobob.domain.product.dto.ProductSpecificSearchWithLikeDto;
import com.pinkdumbell.cocobob.domain.product.dto.RelationProductDto;
import com.pinkdumbell.cocobob.domain.product.like.LikeRepository;
import com.pinkdumbell.cocobob.domain.user.User;
import com.pinkdumbell.cocobob.domain.user.UserRepository;
import com.pinkdumbell.cocobob.exception.CustomException;
import com.pinkdumbell.cocobob.exception.ErrorCode;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;

    public ProductDetailResponseDto findProductDetailById(Long productId, String userEmail) {

        Product foundProduct = productRepository.findById(productId).orElseThrow(
            () -> {
                throw new CustomException(ErrorCode.PRODUCT_NOT_FOUND);
            });

        User foundUser = userRepository.findByEmail(userEmail).orElseThrow(() -> {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        });

        return new ProductDetailResponseDto(foundProduct,
            likeRepository.countByProduct(foundProduct),
            likeRepository.findByProductAndUser(foundProduct, foundUser).isPresent());
    }

    public FindAllResponseDto elasticSearchProducts(ProductSpecificSearchDto requestParameter,
        Pageable pageable) {

        return new FindAllResponseDto(productRepository.findAll(
            ProductSearchSpecification.makeProductSpecification(requestParameter), pageable));

    }

    public FindAllResponseDto queryDslSearchProducts(
        ProductSpecificSearchWithLikeDto requestParameter,
        String email) {

        User user = userRepository.findByEmail(email).orElseThrow(() -> {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        });

        return new FindAllResponseDto(
            productRepository.findAllWithLikes(requestParameter, user.getId()));

    }

    public FindAllResponseDto findAllWishList(String userEmail, Pageable pageable) {
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        });

        return new FindAllResponseDto(likeRepository.findAllByUserLike(user, pageable));
    }

    @Cacheable(cacheNames = "productKeywords", key = "#keyword")
    public List<ProductKeywordDto> getKeyword(String keyword) {
        return productRepository.findProductNamesByKeyword(keyword);
    }

    public FindAllResponseDto getRelationProduct(Long productId, String userEmail) {

        RestTemplate restTemplate = new RestTemplate();
        List<RelationProductDto> relatedProducts = Arrays.asList(
            Objects.requireNonNull(restTemplate.getForObject(
                "http://localhost:8000/related?productId=" + productId,
                RelationProductDto[].class)));

        List<Long> productIds = relatedProducts.stream().map(RelationProductDto::getProductId)
            .collect(Collectors.toList());
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        });

        return new FindAllResponseDto(
            productRepository.findAllRelatedProductsById(productIds, user.getId()));
    }
}
