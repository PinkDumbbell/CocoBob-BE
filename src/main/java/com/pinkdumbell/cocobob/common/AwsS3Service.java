package com.pinkdumbell.cocobob.common;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.pinkdumbell.cocobob.exception.CustomException;
import com.pinkdumbell.cocobob.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AwsS3Service {
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;

    public String saveImage(MultipartFile image) {
        String imageName = createImageName(image.getOriginalFilename());
        ObjectMetadata objectMetadata = setObjectMetadata(image);

        try (InputStream inputStream = image.getInputStream()) {
            amazonS3.putObject(
                    new PutObjectRequest(bucket, imageName, inputStream, objectMetadata)
                            .withCannedAcl(CannedAccessControlList.PublicRead)
            );
        } catch (IOException e) {
            throw new CustomException(ErrorCode.FAIL_TO_UPLOAD_IMAGE);
        }

        return getImageUrl(imageName);
    }

    private String getImageUrl(String imageName) {
        return amazonS3.getUrl(bucket, imageName).toString();
    }

    public String createImageName(String originalImageName) {
        return UUID.randomUUID().toString().concat(originalImageName);
    }

    public ObjectMetadata setObjectMetadata(MultipartFile image) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(image.getSize());
        objectMetadata.setContentType(image.getContentType());

        return objectMetadata;
    }
}
