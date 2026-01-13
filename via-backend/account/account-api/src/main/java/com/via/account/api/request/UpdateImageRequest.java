package com.via.account.api.request;

import com.via.account.api.request.validator.ValidImageFile;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateImageRequest {
    @ValidImageFile(
            allowedTypes = {"image/jpeg", "image/jpg", "image/png"},
            maxSizeBytes = 5 * 1024 * 1024
    )
    private MultipartFile imageFile;
}
