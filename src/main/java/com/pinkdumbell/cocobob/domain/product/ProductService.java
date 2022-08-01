package com.pinkdumbell.cocobob.domain.product;

import com.pinkdumbell.cocobob.domain.product.dto.FindAllResponseDto;
import com.pinkdumbell.cocobob.domain.product.dto.PetPropertyResponseDto;
import com.pinkdumbell.cocobob.domain.product.dto.ProductDetailResponseDto;

import com.pinkdumbell.cocobob.domain.product.property.PetPropertyRepository;
import com.pinkdumbell.cocobob.exception.CustomException;
import com.pinkdumbell.cocobob.exception.ErrorCode;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;


import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    private final PetPropertyRepository petPropertyRepository;

    public List<FindAllResponseDto> findAll(Pageable pageable) {

        return productRepository.findAll(pageable).stream().map(
            FindAllResponseDto::new
        ).collect(Collectors.toList());
    }

    public ProductDetailResponseDto findProductDetailById(Long productId) {

        Product foundProduct = productRepository.findById(productId).orElseThrow(
            () -> {
                throw new CustomException(ErrorCode.PRODUCT_NOT_FOUND);
            });

        List<PetPropertyResponseDto> allProperty = petPropertyRepository.findAllByProduct(
                foundProduct).stream()
            .map(PetPropertyResponseDto::new).collect(
                Collectors.toList());

        return new ProductDetailResponseDto(foundProduct, allProperty);
    }


}
