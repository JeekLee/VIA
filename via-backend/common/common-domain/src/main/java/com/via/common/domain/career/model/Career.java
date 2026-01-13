package com.via.common.domain.career.model;

import com.via.common.domain.career.model.id.CareerOwnerId;
import com.via.common.domain.career.model.vo.Address;
import com.via.common.domain.career.model.vo.CareerImage;
import com.via.common.domain.career.model.vo.Contact;
import com.via.common.domain.career.model.vo.Personal;
import lombok.Builder;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Builder
public record Career(
        CareerOwnerId careerOwnerId,

        CareerImage image,
        Personal personal,
        Contact contact,
        Address address,

        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static Career create(
            CareerOwnerId careerOwnerId, CareerImage image, Personal personal, Contact contact, Address address
    ) {
        return Career.builder()
                .careerOwnerId(careerOwnerId)
                .image(image)
                .personal(personal)
                .contact(contact)
                .address(address)
                .createdAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                .updatedAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                .build();
    }

    public Career updateCareerImage(CareerImage careerImage) {
        return Career.builder()
                .careerOwnerId(this.careerOwnerId)
                .image(careerImage)
                .personal(this.personal)
                .contact(this.contact)
                .address(this.address)
                .createdAt(this.createdAt)
                .updatedAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                .build();
    }

    public Career updateInfo(Personal personal, Contact contact, Address address) {
        return Career.builder()
                .careerOwnerId(this.careerOwnerId)
                .image(this.image)
                .personal(personal)
                .contact(contact)
                .address(address)
                .createdAt(this.createdAt)
                .updatedAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                .build();
    }
}
