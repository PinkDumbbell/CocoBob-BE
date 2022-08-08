package com.pinkdumbell.cocobob.domain.product;

import com.pinkdumbell.cocobob.domain.product.dto.ProductSimpleResponseDto;
import com.pinkdumbell.cocobob.domain.product.dto.ProductSpecificSearchDto;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public interface ProductSearchQueryDsl {

    PageImpl<ProductSimpleResponseDto> findAllWithLikes(ProductSpecificSearchDto productSpecificSearchDto, Long userId,
        Pageable pageable);
}
