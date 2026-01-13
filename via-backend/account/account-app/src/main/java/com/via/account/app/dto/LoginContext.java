package com.via.account.app.dto;

import com.via.account.app.enums.LoginStatus;
import com.via.account.domain.enums.MemberAuthority;
import com.via.account.domain.model.Member;
import com.via.account.domain.model.id.MemberId;
import com.via.account.domain.model.vo.MemberImage;

import java.util.List;

public record LoginContext(
        MemberId memberId,
        String nickname,
        MemberImage image,
        String email,
        List<MemberAuthority> authorities,

        LoginStatus status,

        String redirectPath
) {
    public static LoginContext create(Member member, LoginStatus status, String redirectPath) {
        return new LoginContext(
                member.memberId(),
                member.nickname(),
                member.image(),
                member.email(),
                member.authorities(),
                status,
                redirectPath
        );
    }
}
