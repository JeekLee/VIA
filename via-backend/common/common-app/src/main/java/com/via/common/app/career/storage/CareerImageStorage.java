package com.via.common.app.career.storage;

import com.via.common.domain.career.model.vo.CareerImage;

import java.io.InputStream;

public interface CareerImageStorage {
    CareerImage save(InputStream inputStream, String fileName, String contentType, long size);
    void delete(CareerImage careerImage);
}
