package com.via.account.infra.repository.jpa;

import com.via.account.infra.entity.MemberJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaMemberRepository extends JpaRepository<MemberJpaEntity, Long> {
    Optional<MemberJpaEntity> findByEmail(String email);
    Optional<MemberJpaEntity> findByNickname(String nickname);
}
