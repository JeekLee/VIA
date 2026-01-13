package com.via.common.app.career.repository;

import com.via.common.domain.career.model.Corporation;

import java.util.List;

public interface CorporationRepository {
    void saveAll(List<Corporation> corporations);
}
