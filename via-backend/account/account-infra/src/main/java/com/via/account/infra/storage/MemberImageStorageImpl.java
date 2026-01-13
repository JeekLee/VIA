package com.via.account.infra.storage;

import com.via.core.dto.ImageDownloadResult;
import com.via.core.error.ExceptionCreator;
import com.via.core.utils.ImageDownloader;
import com.via.account.app.storage.MemberImageStorage;
import com.via.account.domain.model.vo.MemberImage;
import io.awspring.cloud.s3.ObjectMetadata;
import io.awspring.cloud.s3.S3Template;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import static com.via.account.infra.exception.MemberImageStorageException.IMAGE_UPLOAD_FAILED;

@Component
@RequiredArgsConstructor
public class MemberImageStorageImpl implements MemberImageStorage {
    @Value("${spring.cloud.aws.s3.bucket}")
    private String BUCKET_NAME;
    private final static String DIRECTORY = "member/image";

    private final S3Template s3Template;

    private String generatePath(String fileName) {
        String extension = fileName.substring(fileName.lastIndexOf("."));
        String uniqueFileName = UUID.randomUUID() + extension;
        return DIRECTORY + "/" + uniqueFileName;
    }

    @Override
    public MemberImage save(String imageUrl) {
        try (ImageDownloadResult imageDownloadResult = ImageDownloader.download(imageUrl)) {
            String path = generatePath(imageDownloadResult.filename());
            ObjectMetadata metadata = ObjectMetadata.builder()
                    .contentType(imageDownloadResult.contentType())
                    .contentLength(imageDownloadResult.size())
                    .build();

            s3Template.upload(BUCKET_NAME, path, imageDownloadResult.inputStream(), metadata);
            return new MemberImage(path);
        } catch (IOException | InterruptedException e) {
            if (e instanceof InterruptedException) Thread.currentThread().interrupt();
            throw ExceptionCreator.create(IMAGE_UPLOAD_FAILED, e.getMessage());
        }
    }

    @Override
    public MemberImage save(InputStream inputStream, String fileName, String contentType, long size) {
        String path = generatePath(fileName);
        ObjectMetadata metadata = ObjectMetadata.builder()
                .contentType(contentType)
                .contentLength(size)
                .build();

        s3Template.upload(BUCKET_NAME, path, inputStream, metadata);
        return new MemberImage(path);
    }

    @Override
    public void delete(MemberImage memberImage) {
        s3Template.deleteObject(BUCKET_NAME, memberImage.path());
    }
}
