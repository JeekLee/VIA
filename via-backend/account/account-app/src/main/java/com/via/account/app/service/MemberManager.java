package com.via.account.app.service;

import com.via.account.app.storage.MemberImageStorage;
import com.via.core.error.ExceptionCreator;
import com.via.account.app.repository.MemberRepository;
import com.via.account.domain.model.Member;
import com.via.account.domain.model.id.MemberId;
import com.via.account.domain.model.vo.MemberImage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;

import static com.via.account.domain.exception.MemberException.MEMBER_NOT_FOUND;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberManager {
    private final MemberImageStorage memberImageStorage;
    private final MemberRepository memberRepository;

    @Transactional
    public Member createAndSave(String email, String imageUrl) {
        MemberImage memberImage = (imageUrl != null)
                ? memberImageStorage.save(imageUrl)
                : MemberImage.empty();
        return memberRepository.save(Member.create(email, memberImage));
    }

    @Transactional
    public Member updateNickname(MemberId memberId, String nickname) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> ExceptionCreator.create(MEMBER_NOT_FOUND, "memberId: " + memberId.id()));
        return memberRepository.save(member.updateNickname(nickname));
    }

    @Transactional
    public Member updateProfileImage(MemberId memberId, InputStream inputStream, String fileName, String contentType, long size) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> ExceptionCreator.create(MEMBER_NOT_FOUND, "memberId: " + memberId.id()));

        MemberImage memberImage = memberImageStorage.save(inputStream, fileName, contentType, size);
        memberImageStorage.delete(member.image());

        return memberRepository.save(member.updateProfileImage(memberImage));
    }
}