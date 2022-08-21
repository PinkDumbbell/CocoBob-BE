package com.pinkdumbell.cocobob.domain.product.like;

import com.pinkdumbell.cocobob.domain.product.Product;
import com.pinkdumbell.cocobob.domain.product.ProductRepository;
import com.pinkdumbell.cocobob.domain.product.like.dto.LikeRequestDto;
import com.pinkdumbell.cocobob.domain.user.User;
import com.pinkdumbell.cocobob.domain.user.UserRepository;
import com.pinkdumbell.cocobob.domain.user.dto.LoginUserInfo;
import com.pinkdumbell.cocobob.exception.CustomException;
import com.pinkdumbell.cocobob.exception.ErrorCode;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class LikeService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Transactional
    public void like(LikeRequestDto likeRequestDto, LoginUserInfo loginUserInfo) {

        User user = userRepository.findByEmail(loginUserInfo.getEmail()).orElseThrow(() -> {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        });

        LikeId target = new LikeId(user.getId(), likeRequestDto.getProductId());

        if (likeRepository.findByLikeId(target).isPresent()) {
            throw new CustomException(ErrorCode.ALREADY_LIKED);
        }

        Like like = makeLikeByLikeId(target);

        likeRepository.save(like);
    }

    @Transactional
    public void unLike(LikeRequestDto likeRequestDto,LoginUserInfo loginUserInfo) {

        User user = userRepository.findByEmail(loginUserInfo.getEmail()).orElseThrow(() -> {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        });

        LikeId target = new LikeId(user.getId(), likeRequestDto.getProductId());

        if (likeRepository.findByLikeId(target).isEmpty()) {
            throw new CustomException(ErrorCode.LIKE_NOT_FOUND);
        }

        Like like = makeLikeByLikeId(target);
        likeRepository.delete(like);
    }

    @Transactional
    public Like makeLikeByLikeId(LikeId likeId) {

        User user = userRepository.findById(likeId.getUserId())
            .orElseThrow(() -> {
                throw new CustomException(ErrorCode.USER_NOT_FOUND);
            });

        Product product = productRepository.findById(likeId.getProductId()).orElseThrow(() -> {
            throw new CustomException(ErrorCode.PRODUCT_NOT_FOUND);
        });

        return Like.builder().user(user).product(product).likeId(likeId).build();
    }

}
