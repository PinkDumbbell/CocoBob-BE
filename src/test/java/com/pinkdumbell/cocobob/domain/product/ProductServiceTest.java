package com.pinkdumbell.cocobob.domain.product;

import static org.mockito.BDDMockito.*;

import com.pinkdumbell.cocobob.domain.product.dto.FindAllResponseDto;
import com.pinkdumbell.cocobob.domain.product.dto.ProductDetailResponseDto;
import com.pinkdumbell.cocobob.domain.product.dto.ProductKeywordDto;
import com.pinkdumbell.cocobob.domain.product.dto.ProductSimpleResponseDto;
import com.pinkdumbell.cocobob.domain.product.dto.ProductSpecificSearchDto;
import com.pinkdumbell.cocobob.domain.product.dto.ProductSpecificSearchWithLikeDto;
import com.pinkdumbell.cocobob.domain.product.like.Like;
import com.pinkdumbell.cocobob.domain.product.like.LikeRepository;
import com.pinkdumbell.cocobob.domain.user.AccountType;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;


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

        given(productRepository.findById(anyLong())).willThrow(
            new CustomException(ErrorCode.PRODUCT_NOT_FOUND));

        //Execute
        Assertions.assertThatThrownBy(() -> {
            productService.findProductDetailById(productId, userEmail);
        }).isInstanceOf(CustomException.class);
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

        given(userRepository.findByEmail(anyString())).willThrow(
            new CustomException(ErrorCode.USER_NOT_FOUND));

        //Execute
        Assertions.assertThatThrownBy(
                () -> productService.findProductDetailById(productId, userEmail))
            .isInstanceOf(CustomException.class);
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

        for (Long i = 0L; i < 100L; i++) {
            expected.add(new ProductKeywordDto(keyword, "강아지 사료", i));
        }

        given(productRepository.findProductNamesByKeyword(keyword)).willReturn(expected);

        //EXECUTE
        List<ProductKeywordDto> result = productService.getKeyword(keyword);

        //EXPECTED
        Assertions.assertThat(result.size()).isEqualTo(expected.size());
    }

    @Test
    @DisplayName("연관된 상품들을 정보들을 가져올 수 있다")
    void get_relation_product_info() {

        //given
        int page = 1;
        int size = 10;
        User testUser = User.builder()
            .username("tester")
            .email("test@test.com")
            .accountType(AccountType.OWN)
            .password("password")
            .build();

        List<ProductSimpleResponseDto> dummySimpleResponse = new ArrayList<>();
        for (Long i = 0L; i < 10L; i++) {

            dummySimpleResponse.add(
                ProductSimpleResponseDto
                    .builder()
                    .productId(i)
                    .code("101201")
                    .name("하림")
                    .brand("로얄캐닌")
                    .category("건식")
                    .description("맛있어요")
                    .isAAFCOSatisfied(true)
                    .aged(true)
                    .growing(false)
                    .pregnant(false)
                    .obesity(true)
                    .likes(300L)
                    .isLiked(false)
                    .thumbnail("URL:PATH")
                    .build()
            );
        }
        Pageable pageable = PageRequest.of(page, size);
        PageImpl<ProductSimpleResponseDto> dummyData = new PageImpl<>(dummySimpleResponse, pageable,
            size);

        given(userRepository.findByEmail(anyString())).willReturn(Optional.of(testUser));

        given(productRepository.findAllRelatedProductsById(any(), any())).willReturn(dummyData);

        //EXECUTE
        FindAllResponseDto result = productService.getRelationProduct(1L, "test@test.com");

        //EXPECT
        System.out.println(result.getPageSize());
        Assertions.assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("사용자가 좋아한 상품 정보들 가져올 수 있다.")
    void get_info_user_wish_list() {

        //given
        int page = 1;
        int size = 10;
        User testUser = User.builder()
            .username("tester")
            .email("test@test.com")
            .accountType(AccountType.OWN)
            .password("password")
            .build();
        List<Product> dummySimpleResponse = new ArrayList<>();
        for (Long i = 0L; i < 10L; i++) {

            dummySimpleResponse.add(
                Product
                    .builder()
                    .id(i)
                    .code("101201")
                    .name("하림")
                    .brand("로얄캐닌")
                    .category("건식")
                    .description("맛있어요")
                    .isAAFCOSatisfied(true)
                    .aged(true)
                    .growing(false)
                    .pregnant(false)
                    .obesity(true)
                    .thumbnail("URL:PATH")
                    .build()
            );
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> dummyData = new PageImpl<>(dummySimpleResponse, pageable,
            size);

        given(userRepository.findByEmail(anyString())).willReturn(Optional.of(testUser));
        given(likeRepository.findAllByUserLike(any(User.class), any(Pageable.class))).willReturn(
            dummyData);

        //EXECUTE
        FindAllResponseDto result = productService.findAllWishList("test@test.com", pageable);

        //EXPECT
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getPageSize()).isEqualTo(size);
        Assertions.assertThat(result.getPageNumber()).isEqualTo(page);

    }

    @Test
    @DisplayName("조건에 맞추어 상품을 가져올 수 있다.")
    void correct_working_elastic_search_products() {
        //given
        int page = 1;
        int size = 10;
        ProductSpecificSearchDto request = ProductSpecificSearchDto.builder()
            .code("101101")
            .name("더리얼밀그레인프리닭고기60g6개")
            .brand("하림펫푸드")
            .description("소고기 함유")
            .aafco(true)
            .beef(true)
            .mutton(true)
            .chicken(true)
            .duck(true)
            .turkey(true)
            .pork(true)
            .salmon(true)
            .hydrolyticBeef(true)
            .hydrolyticMutton(true)
            .hydrolyticChicken(true)
            .hydrolyticDuck(true)
            .hydrolyticTurkey(true)
            .hydrolyticPork(true)
            .hydrolyticSalmon(true)
            .aged(true)
            .growing(true)
            .pregnant(true)
            .obesity(true)
            .build();

        List<Product> dummyResponse = new ArrayList<>();
        for (Long i = 0L; i < 10L; i++) {

            dummyResponse.add(
                Product
                    .builder()
                    .code("101201")
                    .name("하림")
                    .brand("로얄캐닌")
                    .category("건식")
                    .description("맛있어요")
                    .isAAFCOSatisfied(true)
                    .aged(true)
                    .growing(false)
                    .pregnant(false)
                    .obesity(true)
                    .thumbnail("URL:PATH")
                    .build()
            );
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> dummyData = new PageImpl<>(dummyResponse, pageable, size);

        given(productRepository.findAll(any(Specification.class), any(Pageable.class))).willReturn(
            dummyData);

        //EXECUTE
        FindAllResponseDto result = productService.elasticSearchProducts(request, pageable);

        //EXPECT
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getPageSize()).isEqualTo(size);
        Assertions.assertThat(result.getPageNumber()).isEqualTo(page);
    }


}