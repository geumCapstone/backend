package com.geum.mvcServer.apis.batch.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalMedicineRepository extends JpaRepository<AnimalMedicine, Long> {
}
