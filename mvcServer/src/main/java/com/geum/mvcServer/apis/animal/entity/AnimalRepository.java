package com.geum.mvcServer.apis.animal.entity;

import com.geum.mvcServer.apis.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {
    List<Animal> findAll();

    List<Animal> findByUserId(String userId);
    List<Animal> findByUserId(User userId);
}
