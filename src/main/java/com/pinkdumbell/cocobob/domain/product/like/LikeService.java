package com.pinkdumbell.cocobob.domain.product.like;

import com.pinkdumbell.cocobob.domain.product.Product;
import com.pinkdumbell.cocobob.domain.product.ProductRepository;
import com.pinkdumbell.cocobob.domain.product.like.dto.LikeRequestDto;
import com.pinkdumbell.cocobob.domain.user.User;
import com.pinkdumbell.cocobob.domain.user.UserRepository;
import com.pinkdumbell.cocobob.exception.CustomException;
import com.pinkdumbell.cocobob.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LikeService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public void like(LikeRequestDto likeRequestDto) {

        LikeId target = new LikeId(likeRequestDto.getUserId(), likeRequestDto.getProductId());

        if (likeRepository.findByLikeId(target).isPresent()) {
            throw new CustomException(ErrorCode.ALREADY_LIKED);
        }

        User user = userRepository.findById(target.getUserId())
            .orElseThrow(() -> {
                throw new CustomException(ErrorCode.USER_NOT_FOUND);
            });

        Product product = productRepository.findById(target.getProductId()).orElseThrow(() -> {
            throw new CustomException(ErrorCode.PRODUCT_NOT_FOUND);
        });

        Like like = Like.builder().user(user).product(product).likeId(target).build();

        likeRepository.save(like);

    }

    public void unLike(LikeRequestDto likeRequestDto) {

        LikeId target = new LikeId(likeRequestDto.getUserId(), likeRequestDto.getProductId());

        if (likeRepository.findByLikeId(target).isEmpty()) {
            throw new CustomException(ErrorCode.LIKE_NOT_FOUND);
        }

        User user = userRepository.findById(target.getUserId())
            .orElseThrow(() -> {
                throw new CustomException(ErrorCode.USER_NOT_FOUND);
            });

        Product product = productRepository.findById(target.getProductId()).orElseThrow(() -> {
            throw new CustomException(ErrorCode.PRODUCT_NOT_FOUND);
        });

        Like like = Like.builder().user(user).product(product).likeId(target).build();

        likeRepository.delete(like);

    }

}