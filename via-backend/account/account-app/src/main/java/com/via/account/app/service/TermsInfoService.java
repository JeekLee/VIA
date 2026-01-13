package com.via.account.app.service;


import com.via.account.app.dao.TermsInfoDao;
import com.via.account.app.dto.TermsInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TermsInfoService {
    private final TermsInfoDao termsInfoDao;

    @Transactional(readOnly = true)
    public List<TermsInfo> getLatestTerms() {
        return termsInfoDao.findAllLatestTerms();
    }
}
