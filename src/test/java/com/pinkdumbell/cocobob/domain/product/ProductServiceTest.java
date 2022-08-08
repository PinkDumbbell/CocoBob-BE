package com.pinkdumbell.cocobob.domain.product;

import static org.mockito.BDDMockito.*;

import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.pinkdumbell.cocobob.domain.product.dto.ProductDetailResponseDto;
import com.pinkdumbell.cocobob.domain.product.like.Like;
import com.pinkdumbell.cocobob.domain.product.like.LikeRepository;
import com.pinkdumbell.cocobob.domain.user.User;
import com.pinkdumbell.cocobob.domain.user.UserRepository;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    ProductService productService;

    @Mock
    ProductRepository productRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    LikeRepository likeRepository;

    @Test
    @DisplayName("상품 아이디와 유저 이메일을 통해서 상품의 상세정보와 유저의 좋아요와 총 좋아요수를 파악할 수 있다.")
    void find_product_detail_with_user_email_and_product_id() {
        String userEmail = "test@test.com";
        Long productId = 1L;

        //when
        Product expectedProduct = Product.builder().id(productId).build();
        User expectedUser = User.builder().email(userEmail).build();
        Like expectedLike = Like.builder().user(expectedUser).product(expectedProduct).build();

        given(productRepository.findById(anyLong())).willReturn(
            Optional.ofNullable(expectedProduct));

        given(userRepository.findByEmail(userEmail)).willReturn(Optional.ofNullable(expectedUser));

        given(likeRepository.countByProduct(any(Product.class))).willReturn(100L);

        given(likeRepository.findByProductAndUser(any(Product.class),any(User.class))).willReturn(Optional.ofNullable(expectedLike));

        //Execute
        ProductDetailResponseDto result = productService.findProductDetailById(productId,
            userEmail);

        Assertions.assertThat(result.getLikes()).isEqualTo(100L);
        Assertions.assertThat(result.getIsUserLike()).isEqualTo(true);


    }

    @Test
    void queryDslSearchProducts() {
    }
}