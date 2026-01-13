package com.via.account.app.dao;

import com.via.account.app.dto.TermsInfo;

import java.util.List;

public interface TermsInfoDao {
    List<TermsInfo> findAllLatestTerms();
}
