package com.via.account.infra.entity;

import com.via.account.domain.enums.MemberAuthority;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "member", catalog = "account")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "image_path")
    private String imagePath;

    @ElementCollection(targetClass = MemberAuthority.class, fetch = FetchType.LAZY)
    @CollectionTable(
            name = "member_authority",
            catalog = "account",
            joinColumns = @JoinColumn(name = "member_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"member_id", "authority"})
    )
    @Enumerated(EnumType.STRING)
    @Column(
            name = "authority",
            nullable = false,
            columnDefinition = "VARCHAR(50) CHECK (authority IN ('USER', 'EXPERT', 'MANAGER'))"
    )
    private List<MemberAuthority> authorities = new ArrayList<>();

    @Column(name = "resign_requested_at")
    private LocalDateTime resignRequestedAt;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Builder
    public MemberJpaEntity(Long id, String nickname, String email, String imagePath, List<MemberAuthority> authorities, LocalDateTime resignRequestedAt, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.imagePath = imagePath;
        this.authorities = authorities;
        this.resignRequestedAt = resignRequestedAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
