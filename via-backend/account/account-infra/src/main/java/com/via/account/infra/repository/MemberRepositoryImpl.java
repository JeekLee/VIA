package com.via.account.infra.repository;

import com.via.account.infra.entity.MemberJpaEntity;
import com.via.account.infra.mapper.MemberEntityMapper;
import com.via.account.infra.repository.jpa.JpaMemberRepository;
import com.via.account.app.repository.MemberRepository;
import com.via.account.domain.model.Member;
import com.via.account.domain.model.id.MemberId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {
    private final JpaMemberRepository jpaMemberRepository;
    private final MemberEntityMapper mapper;

    @Override
    public Optional<Member> findByEmail(String email) {
        return jpaMemberRepository.findByEmail(email)
                .map(mapper::toDomain);
    }

    @Override
    public Member save(Member member) {
        MemberJpaEntity entity = mapper.toEntity(member);
        MemberJpaEntity saved = jpaMemberRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Member> findById(MemberId memberId) {
        return jpaMemberRepository.findById(memberId.id())
                .map(mapper::toDomain);
    }

    @Override
    public Optional<Member> findByNickname(String nickname) {
        return jpaMemberRepository.findByNickname(nickname)
                .map(mapper::toDomain);
    }
}
