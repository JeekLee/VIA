package com.via.account.infra.mapper;

import com.via.account.infra.entity.MemberJpaEntity;
import com.via.account.domain.model.Member;
import com.via.account.domain.model.id.MemberId;
import com.via.account.domain.model.vo.MemberImage;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class MemberEntityMapper {

    public Member toDomain(MemberJpaEntity entity) {
        return Member.builder()
                .memberId(new MemberId(entity.getId()))
                .nickname(entity.getNickname())
                .email(entity.getEmail())
                .image(new MemberImage(entity.getImagePath()))
                .authorities(new ArrayList<>(entity.getAuthorities()))
                .resignRequestedAt(entity.getResignRequestedAt())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public MemberJpaEntity toEntity(Member domain) {
        return MemberJpaEntity.builder()
                .id(domain.memberId() != null ? domain.memberId().id() : null)
                .nickname(domain.nickname())
                .email(domain.email())
                .imagePath(domain.image() != null ? domain.image().path() : null)
                .authorities(domain.authorities())
                .resignRequestedAt(domain.resignRequestedAt())
                .createdAt(domain.createdAt())
                .updatedAt(domain.updatedAt())
                .build();
    }
}
