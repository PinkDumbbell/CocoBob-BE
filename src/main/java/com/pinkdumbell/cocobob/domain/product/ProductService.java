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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    private final PetPropertyRepository petPropertyRepository;

    public List<FindAllResponseDto> findAll() {

        return productRepository.findAll().stream().map(
            FindAllResponseDto::new
        ).collect(Collectors.toList());
    }

    public ProductDetailResponseDto findProductDetailById(Long product_id) {

        Product foundProduct = productRepository.findById(product_id).orElseThrow(
            () -> {
                throw new CustomException(ErrorCode.NOT_FOUND_PRODUCT);
            });

        List<PetPropertyResponseDto> allProperty = petPropertyRepository.findAllByProduct(
                foundProduct).stream()
            .map(PetPropertyResponseDto::new).collect(
                Collectors.toList());

        return new ProductDetailResponseDto(foundProduct, allProperty);
    }


}
