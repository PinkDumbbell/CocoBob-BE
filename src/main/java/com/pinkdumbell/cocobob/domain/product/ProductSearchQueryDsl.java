package com.pinkdumbell.cocobob.domain.product;

import com.pinkdumbell.cocobob.domain.product.dto.ProductKeywordDto;
import com.pinkdumbell.cocobob.domain.product.dto.ProductSimpleResponseDto;
import com.pinkdumbell.cocobob.domain.product.dto.ProductSpecificSearchWithLikeDto;
import java.util.List;
import org.springframework.data.domain.PageImpl;

public interface ProductSearchQueryDsl {

    PageImpl<ProductSimpleResponseDto> findAllWithLikes(
        ProductSpecificSearchWithLikeDto productSpecificSearchDto, Long userId);

    List<ProductKeywordDto> findProductNamesByKeyword(String keyword);

    PageImpl<ProductSimpleResponseDto> findAllRelatedProductsById(List<Long> ids, Long userId);
}
