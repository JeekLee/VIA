package com.via.common.infra.career.database.repository.jpa;

import com.via.common.infra.career.database.entity.CareerJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CareerJpaRepository extends JpaRepository<CareerJpaEntity, Long> {

}
