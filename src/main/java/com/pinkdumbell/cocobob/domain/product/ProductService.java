package com.pinkdumbell.cocobob.domain.product;

import com.pinkdumbell.cocobob.domain.product.dto.FindAllResponseDto;
import com.pinkdumbell.cocobob.domain.product.dto.ProductDetailResponseDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public List<FindAllResponseDto> findAll() {

        List<FindAllResponseDto> findAllResponseDtoList = productRepository.findAll().stream().map(
            product -> FindAllResponseDto.builder().
                productId(product.getId()).
                name(product.getName()).
                price(product.getPrice()).
                code(product.getCode()).
                category(product.getCategory()).
                thumbnail(product.getThumbnail()).
                description(product.getDescription()).
                build()
        ).collect(Collectors.toList());

        return findAllResponseDtoList;
    }


}
