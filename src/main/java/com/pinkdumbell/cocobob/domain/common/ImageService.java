package com.pinkdumbell.cocobob.domain.common;

import lombok.RequiredArgsConstructor;
import marvin.image.MarvinImage;
import org.marvinproject.image.transform.scale.Scale;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RequiredArgsConstructor
@Service
public class ImageService {

    private final AwsS3Service awsS3Service;

    public String saveImage(MultipartFile image) {
        isImage(image);
        return awsS3Service.saveImage(image);
    }

    private void isImage(MultipartFile image) {
        if(!image.getContentType().startsWith("image")) {
            throw new RuntimeException("not image");
        }
    }

    public MultipartFile resizeImage(MultipartFile originalImage, int targetWidth) {
        try {
            BufferedImage bufferedImage = ImageIO.read(originalImage.getInputStream());
            if (bufferedImage.getWidth() < targetWidth) {
                return originalImage;
            }
            MarvinImage marvinImage = processResizing(bufferedImage, targetWidth);
            return new MockMultipartFile(originalImage.getOriginalFilename(),
                    createResizedImageName(originalImage.getOriginalFilename()),
                    originalImage.getContentType(),
                    writeResizedImage(marvinImage, originalImage.getContentType()).toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("이미지 리사이징 실패");
        }
    }

    private MarvinImage processResizing(BufferedImage bufferedImage, int targetWidth) {
        MarvinImage marvinImage = new MarvinImage(bufferedImage);
        Scale scale = new Scale();
        scale.load();
        scale.setAttribute("newWidth", targetWidth);
        scale.setAttribute("newHeight", targetWidth * bufferedImage.getHeight() / bufferedImage.getWidth());
        scale.process(marvinImage.clone(), marvinImage, null, null, false);

        return marvinImage;
    }

    private ByteArrayOutputStream writeResizedImage(MarvinImage marvinImage, String contentType) throws IOException {
        BufferedImage bufferedImageNoAlpha = marvinImage.getBufferedImageNoAlpha();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImageNoAlpha,
                contentType.substring(contentType.lastIndexOf("/") + 1),
                byteArrayOutputStream);
        byteArrayOutputStream.flush();

        return byteArrayOutputStream;
    }

    private String createResizedImageName(String originalImageName) {
        return "resized_".concat(originalImageName);
    }
}
