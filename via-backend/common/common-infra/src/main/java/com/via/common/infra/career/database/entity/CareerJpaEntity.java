package com.via.common.infra.career.database.entity;

import com.via.common.domain.career.enums.Gender;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "career", catalog = "career")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CareerJpaEntity {
    @Id
    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "zip_code", nullable = false)
    private String zipCode;

    @Column(name = "road", nullable = false)
    private String road;

    @Column(name = "detail", nullable = false)
    private String detail;

    @Column(name = "image_path", nullable = false)
    private String imagePath;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Builder
    public CareerJpaEntity(Long memberId, String name, LocalDate birthDate, Gender gender, String phone, String email, String zipCode, String road, String detail, String imagePath, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.memberId = memberId;
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.zipCode = zipCode;
        this.road = road;
        this.detail = detail;
        this.imagePath = imagePath;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
