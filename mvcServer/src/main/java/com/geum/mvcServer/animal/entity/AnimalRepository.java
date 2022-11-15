package com.geum.mvcServer.animal.entity;

import com.geum.mvcServer.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {
    List<Animal> findAll();

    List<Animal> findByProviderId(String providerId);

    List<Animal> findByProviderId(User providerId);
}
