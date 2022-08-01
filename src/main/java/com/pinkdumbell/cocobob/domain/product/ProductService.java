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

        List<FindAllResponseDto> findAllResponseDtoList = productRepository.findAll().stream().map(
            FindAllResponseDto::new
        ).collect(Collectors.toList());

        return findAllResponseDtoList;
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

        return ProductDetailResponseDto.builder()
            .productId(foundProduct.getId())
            .code(foundProduct.getCode())
            .name(foundProduct.getName())
            .price(foundProduct.getPrice())
            .productImage(foundProduct.getProductImage())
            .productDetailImage(foundProduct.getProductDetailImage())
            .description(foundProduct.getDescription())
            .protein(foundProduct.getProtein())
            .amountOfProteinPerMcal(foundProduct.getAmountOfProteinPerMcal())
            .fat(foundProduct.getFat())
            .amountOfFatPerMcal(foundProduct.getAmountOfFatPerMcal())
            .fiber(foundProduct.getFiber())
            .amountOfFiberPerMcal(foundProduct.getAmountOfFiberPerMcal())
            .mineral(foundProduct.getMineral())
            .amountOfMineralPerMcal(foundProduct.getAmountOfMineralPerMcal())
            .calcium(foundProduct.getCalcium())
            .amountOfCalciumPerMcal(foundProduct.getAmountOfCalciumPerMcal())
            .phosphorus(foundProduct.getPhosphorus())
            .amountOfPhosphorusPerMcal(foundProduct.getAmountOfPhosphorusPerMcal())
            .moisture(foundProduct.getMoisture())
            .kcalPerKg(foundProduct.getKcalPerKg())
            .isAAFCOSatisfied(foundProduct.getIsAAFCOSatisfied())
            .beef(foundProduct.getBeef())
            .mutton(foundProduct.getMutton())
            .duck(foundProduct.getDuck())
            .turkey(foundProduct.getTurkey())
            .meat(foundProduct.getMeat())
            .salmon(foundProduct.getSalmon())
            .hydrolyticBeef(foundProduct.getHydrolyticBeef())
            .hydrolyticMutton(foundProduct.getHydrolyticMutton())
            .hydrolyticChicken(foundProduct.getHydrolyticChicken())
            .hydrolyticTurkey(foundProduct.getHydrolyticTurkey())
            .hydrolyticMeat(foundProduct.getHydrolyticMeat())
            .hydrolyticSalmon(foundProduct.getHydrolyticSalmon())
            .petProperties(allProperty)
            .build();
    }


}
