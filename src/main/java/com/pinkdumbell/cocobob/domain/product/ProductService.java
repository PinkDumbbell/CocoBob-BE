package com.pinkdumbell.cocobob.domain.product;

import com.pinkdumbell.cocobob.domain.product.dto.FindAllResponseDto;
import com.pinkdumbell.cocobob.domain.product.dto.PetPropertyResponseDto;
import com.pinkdumbell.cocobob.domain.product.dto.ProductDetailResponseDto;

import com.pinkdumbell.cocobob.domain.product.dto.ProductSpecificSearchDto;
import com.pinkdumbell.cocobob.domain.product.property.PetPropertyRepository;
import com.pinkdumbell.cocobob.exception.CustomException;
import com.pinkdumbell.cocobob.exception.ErrorCode;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    private final PetPropertyRepository petPropertyRepository;

    public FindAllResponseDto findProductAll(Pageable pageable) {

        return new FindAllResponseDto(productRepository.findAll(pageable));
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

    public FindAllResponseDto elasticSearchProducts(ProductSpecificSearchDto requestParameter,
        Pageable pageable) {

        Specification<Product> spec = (root, query, criteriaBuilder) -> null;

        if (requestParameter.getCode() != null) {
            spec = spec.and(
                ProductSearchSpecification.equalCode(requestParameter.getCode())); // 카테고리 코드
        }

        if (requestParameter.getName() != null) {
            spec = spec.and(ProductSearchSpecification.likeName(requestParameter.getName())); // 상품명
        }

        if (requestParameter.getDescription() != null) {
            spec = spec.and(ProductSearchSpecification.likeDescription(
                requestParameter.getDescription())); // 상품 내용
        }

        if (requestParameter.getBeef() != null) {
            spec = spec.and(
                ProductSearchSpecification.equalBeef(requestParameter.getBeef())); // 소고기 포함 유무
        }

        if (requestParameter.getMutton() != null) {
            spec = spec.and(
                ProductSearchSpecification.equalMutton(requestParameter.getMutton())); // 양고기 포함 유무
        }

        if (requestParameter.getChicken() != null) {
            spec = spec.and(ProductSearchSpecification.equalChicken(
                requestParameter.getChicken())); // 닭고기 포함 유무
        }

        if (requestParameter.getDuck() != null) {
            spec = spec.and(
                ProductSearchSpecification.equalDuck(requestParameter.getDuck())); // 오리고기 포함 유무
        }

        if (requestParameter.getTurkey() != null) {
            spec = spec.and(
                ProductSearchSpecification.equalTurkey(requestParameter.getTurkey())); // 칠면조 포함 유무
        }

        if (requestParameter.getMeat() != null) {
            spec = spec.and(
                ProductSearchSpecification.equalMeat(requestParameter.getMeat())); //  돼지고기 포함 유무
        }

        if (requestParameter.getSalmon() != null) {
            spec = spec.and(
                ProductSearchSpecification.equalSalmon(requestParameter.getSalmon())); // 연어 포함 유무
        }

        if (requestParameter.getHydrolyticBeef() != null) {
            spec = spec.and(ProductSearchSpecification.equalHydrolyticBeef(
                requestParameter.getHydrolyticBeef())); // 가수분해 소고기 포함 유무
        }

        if (requestParameter.getHydrolyticMutton() != null) {
            spec = spec.and(ProductSearchSpecification.equalHydrolyticMutton(
                requestParameter.getHydrolyticMutton())); // 가수분해 양고기 포함 유무
        }

        if (requestParameter.getHydrolyticChicken() != null) {
            spec = spec.and(ProductSearchSpecification.equalHydrolyticChicken(
                requestParameter.getHydrolyticChicken())); // 가수분해 닭고기 포함 유무
        }

        if (requestParameter.getHydrolyticDuck() != null) {
            spec = spec.and(ProductSearchSpecification.equalHydrolyticDuck(
                requestParameter.getHydrolyticDuck())); // 가수분해 오리고기 포함 유무
        }

        if (requestParameter.getHydrolyticTurkey() != null) {
            spec = spec.and(ProductSearchSpecification.equalHydrolyticTurkey(
                requestParameter.getHydrolyticTurkey())); // 가수분해 칠면조 포함 유무
        }

        if (requestParameter.getHydrolyticMeat() != null) {
            spec = spec.and(ProductSearchSpecification.equalHydrolyticMeat(
                requestParameter.getHydrolyticMeat())); // 가수분해 돼지고기 포함 유무
        }

        if (requestParameter.getHydrolyticSalmon() != null) {
            spec = spec.and(ProductSearchSpecification.equalHydrolyticSalmon(
                requestParameter.getHydrolyticSalmon())); // 가수분해 연어 포함 유무
        }

        if (requestParameter.getAAFCO() != null) {
            spec = spec.and(ProductSearchSpecification.equalAFFCO(requestParameter.getAAFCO()));
        }

        return new FindAllResponseDto(productRepository.findAll(spec, pageable));

    }
}
