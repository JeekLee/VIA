package com.via.common.api.controller;

import com.via.common.api.api.MyCareerApi;
import com.via.common.api.request.CreateCareerRequest;
import com.via.common.api.request.UpdateCareerImageRequest;
import com.via.common.api.request.UpdateCareerRequest;
import com.via.common.app.career.dto.CareerInfo;
import com.via.common.app.career.service.CareerUseCase;
import com.via.common.domain.career.model.id.CareerOwnerId;
import com.via.core.error.ExceptionCreator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import static com.via.common.api.exception.MyCareerException.FAILED_TO_READ_IMAGE;

@RestController
@RequiredArgsConstructor
public class MyCareerController implements MyCareerApi {
    private final CareerUseCase careerUseCase;

    @Override
    public ResponseEntity<Void> createCareer(Long userId, CreateCareerRequest request) {
        try {
            careerUseCase.createAndSave(
                    new CareerOwnerId(userId),
                    request.getImage().getInputStream(),
                    request.getImage().getOriginalFilename(),
                    request.getImage().getContentType(),
                    request.getImage().getSize(),
                    request.getName(),
                    request.getBirthDate(),
                    request.getGender(),
                    request.getPhone(),
                    request.getEmail(),
                    request.getZipCode(),
                    request.getRoadAddress(),
                    request.getDetailAddress()
            );
        } catch (IOException e) {
            throw ExceptionCreator.create(FAILED_TO_READ_IMAGE, "userId: " + userId);
        }
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<CareerInfo> getCareerInfo(Long userId) {
        return ResponseEntity.ok().body(careerUseCase.getCareerInfo(new CareerOwnerId(userId)));
    }

    @Override
    public ResponseEntity<Void> updateCareer(Long userId, UpdateCareerRequest request) {
        careerUseCase.update(
                new CareerOwnerId(userId),
                request.getName(),
                request.getBirthDate(),
                request.getGender(),
                request.getPhone(),
                request.getEmail(),
                request.getZipCode(),
                request.getRoadAddress(),
                request.getDetailAddress()
        );
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> updateCareerImage(Long userId, UpdateCareerImageRequest request) {
        try {
            careerUseCase.updateImage(
                    new CareerOwnerId(userId),
                    request.getImage().getInputStream(),
                    request.getImage().getOriginalFilename(),
                    request.getImage().getContentType(),
                    request.getImage().getSize()
            );
        } catch (IOException e) {
            throw ExceptionCreator.create(FAILED_TO_READ_IMAGE, "userId: " + userId);
        }
        return ResponseEntity.ok().build();
    }
}
