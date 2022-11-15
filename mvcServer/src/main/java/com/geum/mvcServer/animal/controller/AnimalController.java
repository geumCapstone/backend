package com.geum.mvcServer.animal.controller;

import com.geum.mvcServer.animal.entity.Animal;
import com.geum.mvcServer.animal.entity.AnimalRepository;
import com.geum.mvcServer.animal.model.AnimalVO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Transactional
public class AnimalController {

    private final AnimalRepository animalRepository;

    @GetMapping("v1/animals/{providerId}")
    public ResponseEntity<Result<List<Animal>>> getAllAnimals(@PathVariable("providerId") String providerId) {
        List<Animal> animalList = animalRepository.findByProviderId(providerId);

        if(animalList.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(new Result<>(animalList, animalList.size()));
        }
    }

    @PostMapping("v1/animals")
    public ResponseEntity<Result<List<Animal>>> addAnimal(@RequestBody AnimalVO animalData) {
        if (animalData.isEmpty()) {
            return ResponseEntity.badRequest().build();
        } else {
            Animal animal = new Animal();
            animal.setProviderId(animalData.getProviderId());
            animal.setName(animalData.getName());
            animal.setRace(animalData.getRace());
            animal.setAge(animalData.getAge());
            animal.setBirthday(animalData.getBirthday());

            animalRepository.save(animal);
            List<Animal> data = animalRepository.findByProviderId(animalData.getProviderId());

            return ResponseEntity.ok().body(new Result<>(data, data.size()));
        }
    }

    @DeleteMapping("v1/animals")
    public ResponseEntity<Animal> deleteAnimal(@RequestBody Animal animal) {
        if(!animalRepository.existsById(animal.getId())) {
            return ResponseEntity.notFound().build();
        }

        animalRepository.delete(animal);

        if(animalRepository.existsById(animal.getId())) {
            return ResponseEntity.accepted().build(); // 202, 작업 대기
        }

        return ResponseEntity.ok().build();
    }

    /** 제네릭 문법을 통한 ResponseEntity 데이터 전달 효율 증가 */
    @Getter
    @Setter
    static class Result<T> {
        private T data;
        private int count;

        public Result(T data, int count) {
            this.data = data;
            this.count = count;
        }
    }
}
