package com.via.common.infra.career.impl;

import com.via.common.app.career.storage.CareerImageStorage;
import com.via.common.domain.career.model.vo.CareerImage;
import io.awspring.cloud.s3.ObjectMetadata;
import io.awspring.cloud.s3.S3Template;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CareerImageStorageImpl implements CareerImageStorage {
    @Value("${spring.cloud.aws.s3.bucket}")
    private String BUCKET_NAME;
    private final static String DIRECTORY = "career/image";

    private final S3Template s3Template;

    private String generatePath(String fileName) {
        String extension = fileName.substring(fileName.lastIndexOf("."));
        String uniqueFileName = UUID.randomUUID() + extension;
        return DIRECTORY + "/" + uniqueFileName;
    }

    @Override
    public CareerImage save(InputStream inputStream, String fileName, String contentType, long size) {
        String path = generatePath(fileName);
        ObjectMetadata metadata = ObjectMetadata.builder()
                .contentType(contentType)
                .contentLength(size)
                .build();

        s3Template.upload(BUCKET_NAME, path, inputStream, metadata);
        return new CareerImage(path);
    }

    @Override
    public void delete(CareerImage careerImage) {
        s3Template.deleteObject(BUCKET_NAME, careerImage.path());
    }
}
