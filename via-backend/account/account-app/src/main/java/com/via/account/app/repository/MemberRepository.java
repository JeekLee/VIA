package com.via.account.app.repository;

import com.via.account.domain.model.Member;
import com.via.account.domain.model.id.MemberId;

import java.util.Optional;

public interface MemberRepository {
    Optional<Member> findByEmail(String email);
    Member save(Member member);
    Optional<Member> findById(MemberId memberId);
    Optional<Member> findByNickname(String nickname);
}
