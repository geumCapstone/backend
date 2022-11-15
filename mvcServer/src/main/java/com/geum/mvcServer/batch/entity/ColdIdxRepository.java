package com.geum.mvcServer.batch.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColdIdxRepository extends JpaRepository<ColdIdx, Long> {
}
