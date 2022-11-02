package com.pinkdumbell.cocobob.domain.product;

import static org.mockito.BDDMockito.*;

import com.pinkdumbell.cocobob.domain.product.dto.FindAllResponseDto;
import com.pinkdumbell.cocobob.domain.product.dto.ProductDetailResponseDto;
import com.pinkdumbell.cocobob.domain.product.dto.ProductKeywordDto;
import com.pinkdumbell.cocobob.domain.product.dto.ProductSimpleResponseDto;
import com.pinkdumbell.cocobob.domain.product.dto.ProductSpecificSearchWithLikeDto;
import com.pinkdumbell.cocobob.domain.product.like.Like;
import com.pinkdumbell.cocobob.domain.product.like.LikeRepository;
import com.pinkdumbell.cocobob.domain.user.User;
import com.pinkdumbell.cocobob.domain.user.UserRepository;
import com.pinkdumbell.cocobob.exception.CustomException;
import com.pinkdumbell.cocobob.exception.ErrorCode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;


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

        given(likeRepository.findByProductAndUser(any(Product.class), any(User.class))).willReturn(
            Optional.ofNullable(expectedLike));

        //Execute
        ProductDetailResponseDto result = productService.findProductDetailById(productId,
            userEmail);

        Assertions.assertThat(result.getLikes()).isEqualTo(100L);
        Assertions.assertThat(result.getIsLiked()).isEqualTo(true);
    }

    @Test
    @DisplayName("상품이 존재 하지 않는다면 상품이 없다고 알려주어야 한다.")
    void not_found_product_detail_By_product_id() {

        //given
        String userEmail = "test@test.com";
        Long productId = 10000L;

        given(productRepository.findById(anyLong())).willThrow(new CustomException(ErrorCode.PRODUCT_NOT_FOUND));

        //Execute
        try {
            productService.findProductDetailById(productId, userEmail);
        }
        catch (CustomException e) {
            Assertions.assertThat(e.getErrorCode()).isEqualTo(ErrorCode.PRODUCT_NOT_FOUND);
        }
    }

    @Test
    @DisplayName("유저가 존재 하지 않는다면 존재하지 않는 유저라고 알려주어야 한다.")
    void not_found_product_detail_By_user() {

        //given
        String userEmail = "nothing@test2.com";
        Long productId = 10000L;
        Product expectedProduct = Product.builder().id(productId).build();

        given(productRepository.findById(anyLong())).willReturn(
            Optional.ofNullable(expectedProduct));

        given(userRepository.findByEmail(anyString())).willThrow(new CustomException(ErrorCode.USER_NOT_FOUND));

        //Execute
        try {
            productService.findProductDetailById(productId, userEmail);
        }
        catch (CustomException e) {
            Assertions.assertThat(e.getErrorCode()).isEqualTo(ErrorCode.USER_NOT_FOUND);
        }
    }

    @Test
    @DisplayName("QueryDSL을 이용한 리포지토리를 정상적으로 사용할 수 있다.")
    void correct_queryDslSearchProducts() {
        String userEmail = "test@test.com";
        User expectedUser = User.builder().email(userEmail).build();
        List<ProductSimpleResponseDto> expectedResult = new ArrayList<>();
        PageRequest pageable = PageRequest.of(0, 10);
        PageImpl<ProductSimpleResponseDto> expectePageIml = new PageImpl<>(expectedResult, pageable,
            expectedResult.size());

        //When
        given(userRepository.findByEmail(userEmail)).willReturn(Optional.ofNullable(expectedUser));

        given(productRepository.findAllWithLikes(any(), any())).willReturn(expectePageIml);

        //Execute
        ProductSpecificSearchWithLikeDto request = new ProductSpecificSearchWithLikeDto();

        FindAllResponseDto result = productService.queryDslSearchProducts(request, userEmail);

        Assertions.assertThat(result.getPageSize()).isEqualTo(10);
        Assertions.assertThat(result.getPageNumber()).isEqualTo(0);


    }

    @Test
    @DisplayName("키워드들을 통해서 연관된 상품 정보들을 가져올 수 있다.")
    void get_product_name_by_keyword() {

        //when
        String keyword = "로얄캐닌";
        List<ProductKeywordDto> expected = new ArrayList<>();

        for(Long i = 0L; i<100L;i++){
            expected.add(new ProductKeywordDto(keyword,"강아지 사료",i));
        }

        given(productRepository.findProductNamesByKeyword(keyword)).willReturn(expected);

        //EXECUTE
        List<ProductKeywordDto> result = productService.getKeyword(keyword);

        //EXPECTED
        Assertions.assertThat(result.size()).isEqualTo(expected.size());
    }


}