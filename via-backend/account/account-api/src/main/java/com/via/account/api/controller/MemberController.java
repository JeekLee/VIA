package com.via.account.api.controller;

import com.via.account.api.api.MemberApi;
import com.via.account.app.dto.MemberInfo;
import com.via.account.app.service.MemberService;
import com.via.account.domain.model.id.MemberId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController implements MemberApi {
    private final MemberService memberService;

    @Override
    public ResponseEntity<MemberInfo> getMyMemberInfo(Long userId) {
        return ResponseEntity.ok(memberService.getMemberInfo(new MemberId(userId)));
    }
}
