package com.via.account.app.service;

import com.via.account.app.dto.MemberInfo;
import com.via.account.app.repository.MemberRepository;
import com.via.account.domain.model.Member;
import com.via.account.domain.model.id.MemberId;
import com.via.core.error.ExceptionCreator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.via.account.app.exception.MemberException.MEMBER_NOT_FOUND;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public MemberInfo getMemberInfo(MemberId id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> ExceptionCreator.create(MEMBER_NOT_FOUND, "MemberId: " + id.id()));
        return new MemberInfo(member.memberId().id(), member.nickname(), member.image().path());
    }
}
