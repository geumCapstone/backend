package com.geum.mvcServer.apis.animal.entity;

import com.geum.mvcServer.apis.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {
    List<Animal> findAll();
    List<Animal> findAllByUserId(Long id);
    List<Animal> findByUserId(User userId);
    Optional<Animal> findById(Long id);

    void delete(Optional<Animal> animal);
}
