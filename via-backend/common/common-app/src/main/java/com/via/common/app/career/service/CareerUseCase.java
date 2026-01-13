package com.via.common.app.career.service;

import com.via.common.app.career.dao.CareerInfoDao;
import com.via.common.app.career.dto.CareerInfo;
import com.via.common.app.career.repository.CareerRepository;
import com.via.common.app.career.storage.CareerImageStorage;
import com.via.common.domain.career.enums.Gender;
import com.via.common.domain.career.model.Career;
import com.via.common.domain.career.model.id.CareerOwnerId;
import com.via.common.domain.career.model.vo.Address;
import com.via.common.domain.career.model.vo.CareerImage;
import com.via.common.domain.career.model.vo.Contact;
import com.via.common.domain.career.model.vo.Personal;
import com.via.core.error.ExceptionCreator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.time.LocalDate;

import static com.via.common.app.career.exception.CareerException.CAREER_NOT_FOUND;

@Service
@Slf4j
@RequiredArgsConstructor
public class CareerUseCase {
    private final CareerImageStorage careerImageStorage;
    private final CareerRepository careerRepository;
    private final CareerInfoDao careerInfoDao;

    @Transactional
    public void createAndSave(
            CareerOwnerId careerOwnerId,
            InputStream inputStream, String fileName, String contentType, long size,
            String name, LocalDate birthDate, Gender gender,
            String phone, String email,
            String zipCode, String roadAddress, String detailAddress
    ) {
        CareerImage careerImage = careerImageStorage.save(inputStream, fileName, contentType, size);
        Personal personal = new Personal(name, birthDate, gender);
        Contact contact = new Contact(phone, email);
        Address address = new Address(zipCode, roadAddress, detailAddress);

        Career career = Career.create(careerOwnerId, careerImage, personal, contact, address);
        careerRepository.save(career);
    }

    @Transactional
    public void update(
            CareerOwnerId careerOwnerId,
            String name, LocalDate birthDate, Gender gender,
            String phone, String email,
            String zipCode, String roadAddress, String detailAddress
    ) {
        Career career = careerRepository.findByMemberId(careerOwnerId)
                .orElseThrow(() -> ExceptionCreator.create(CAREER_NOT_FOUND, "memberId: " + careerOwnerId.id()));

        Personal personal = career.personal().update(name, birthDate, gender);
        Contact contact = career.contact().update(phone, email);
        Address address = career.address().update(zipCode, roadAddress, detailAddress);

        careerRepository.save(career.updateInfo(personal, contact, address));
    }

    @Transactional
    public void updateImage(CareerOwnerId careerOwnerId, InputStream inputStream, String fileName, String contentType, long size) {
        Career career = careerRepository.findByMemberId(careerOwnerId)
                .orElseThrow(() -> ExceptionCreator.create(CAREER_NOT_FOUND, "memberId: " + careerOwnerId.id()));
        careerImageStorage.delete(career.image());
        CareerImage careerImage = careerImageStorage.save(inputStream, fileName, contentType, size);
        careerRepository.save(career.updateCareerImage(careerImage));
    }

    @Transactional(readOnly = true)
    public CareerInfo getCareerInfo(CareerOwnerId careerOwnerId) {
        return careerInfoDao.findByMemberId(careerOwnerId)
                .orElseThrow(() -> ExceptionCreator.create(CAREER_NOT_FOUND, "memberId: " + careerOwnerId.id()));
    }
}
