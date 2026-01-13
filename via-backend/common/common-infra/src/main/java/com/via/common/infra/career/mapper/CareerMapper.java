package com.via.common.infra.career.mapper;

import com.via.common.domain.career.model.Career;
import com.via.common.domain.career.model.id.CareerOwnerId;
import com.via.common.domain.career.model.vo.Address;
import com.via.common.domain.career.model.vo.CareerImage;
import com.via.common.domain.career.model.vo.Contact;
import com.via.common.domain.career.model.vo.Personal;
import com.via.common.infra.career.database.entity.CareerJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class CareerMapper {
    public Career toDomain(CareerJpaEntity entity) {
        return new Career(
                new CareerOwnerId(entity.getMemberId()),
                new CareerImage(entity.getImagePath()),
                new Personal(
                        entity.getName(),
                        entity.getBirthDate(),
                        entity.getGender()
                ),
                new Contact(
                        entity.getPhone(),
                        entity.getEmail()
                ),
                new Address(
                        entity.getZipCode(),
                        entity.getRoad(),
                        entity.getDetail()
                ),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public CareerJpaEntity fromDomain(Career career) {
        return CareerJpaEntity.builder()
                .memberId(career.careerOwnerId().id())
                .name(career.personal().name())
                .birthDate(career.personal().birthDate())
                .gender(career.personal().gender())
                .phone(career.contact().phone())
                .email(career.contact().email())
                .zipCode(career.address().zipCode())
                .road(career.address().road())
                .detail(career.address().detail())
                .imagePath(career.image().path())
                .createdAt(career.createdAt())
                .updatedAt(career.updatedAt())
                .build();
    }
}