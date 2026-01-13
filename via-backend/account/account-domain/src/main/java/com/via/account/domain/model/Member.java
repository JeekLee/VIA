package com.via.account.domain.model;

import com.via.account.domain.model.vo.MemberImage;
import com.via.account.domain.utils.NicknameGenerator;
import com.via.account.domain.enums.MemberAuthority;
import com.via.account.domain.model.id.MemberId;
import lombok.Builder;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;

@Builder
public record Member(
        MemberId memberId,

        String nickname,
        String email,
        MemberImage image,

        List<MemberAuthority> authorities,
        LocalDateTime resignRequestedAt,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static Member create(String email, MemberImage image) {
        return Member.builder()
                .nickname(NicknameGenerator.generate())
                .email(email)
                .image(image)
                .authorities(Collections.singletonList(MemberAuthority.USER))
                .resignRequestedAt(null)
                .createdAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                .updatedAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                .build();
    }

    public Member updateNickname(String nickname) {
        return Member.builder()
                .memberId(this.memberId)
                .nickname(nickname)
                .email(this.email)
                .image(this.image)
                .authorities(this.authorities)
                .resignRequestedAt(this.resignRequestedAt)
                .createdAt(this.createdAt)
                .updatedAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                .build();
    }

    public Member updateProfileImage(MemberImage memberImage) {
        return Member.builder()
                .memberId(this.memberId)
                .nickname(this.nickname)
                .email(this.email)
                .image(memberImage)
                .authorities(this.authorities)
                .resignRequestedAt(this.resignRequestedAt)
                .createdAt(this.createdAt)
                .updatedAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                .build();
    }

    public Member resign() {
        return Member.builder()
                .memberId(this.memberId)
                .nickname(this.nickname)
                .email(this.email)
                .image(this.image)
                .authorities(this.authorities)
                .resignRequestedAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                .createdAt(this.createdAt)
                .updatedAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                .build();
    }
}
