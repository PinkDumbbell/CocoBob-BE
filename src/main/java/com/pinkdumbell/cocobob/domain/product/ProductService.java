package com.pinkdumbell.cocobob.domain.product;

import com.pinkdumbell.cocobob.domain.product.dto.FindAllResponseDto;
import com.pinkdumbell.cocobob.domain.product.dto.ProductDetailResponseDto;

import com.pinkdumbell.cocobob.domain.product.dto.ProductSpecificSearchDto;
import com.pinkdumbell.cocobob.domain.product.like.Like;
import com.pinkdumbell.cocobob.domain.product.like.LikeId;
import com.pinkdumbell.cocobob.domain.product.like.LikeRepository;
import com.pinkdumbell.cocobob.domain.user.User;
import com.pinkdumbell.cocobob.domain.user.UserRepository;
import com.pinkdumbell.cocobob.exception.CustomException;
import com.pinkdumbell.cocobob.exception.ErrorCode;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
            likeRepository.findByProduct(foundProduct).isPresent());
    }

    public FindAllResponseDto elasticSearchProducts(ProductSpecificSearchDto requestParameter,
        Pageable pageable) {

        return new FindAllResponseDto(productRepository.findAll(
            ProductSearchSpecification.makeProductSpecification(requestParameter), pageable));

    }

    public FindAllResponseDto queryDslSearchProducts(ProductSpecificSearchDto requestParameter,
        String email,
        Pageable pageable) {
        Long userId = userRepository.findByEmail(email).get().getId();
        return new FindAllResponseDto(
            productRepository.findAllWithLikes(requestParameter, userId, pageable));

    }
}
