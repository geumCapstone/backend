package com.geum.mvcServer.apis.animal.controller;

import com.geum.mvcServer.apis.animal.entity.Animal;
import com.geum.mvcServer.apis.animal.entity.AnimalRepository;
import com.geum.mvcServer.apis.animal.model.AnimalVO;
import com.geum.mvcServer.apis.animal.service.AnimalService;
import com.geum.mvcServer.apis.batch.entity.AnimalDeseaseInfo;
import com.geum.mvcServer.apis.batch.entity.AnimalDeseaseInfoRepository;
import com.geum.mvcServer.apis.batch.entity.AnimalMedicine;
import com.geum.mvcServer.apis.batch.entity.AnimalMedicineRepository;
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
    private final AnimalDeseaseInfoRepository animalDeseaseInfoRepository;
    private final AnimalMedicineRepository animalMedicineRepository;
    private final AnimalService animalService;

    @RequestMapping("v1/animals/{providerId}")
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
            List<Animal> data = animalService.addAnimal(animalData);
            if (data.isEmpty()) {
                return ResponseEntity.internalServerError().build();
            }
            return ResponseEntity.ok().body(new Result<>(data, data.size()));
        }
    }

    @DeleteMapping("v1/animals")
    public ResponseEntity<Animal> deleteAnimal(@RequestBody Animal animal) {
        int process = animalService.deleteAnimal(animal);

        if (process == 0) {
            return ResponseEntity.ok().build();
        } else if (process == 1) {
            return ResponseEntity.notFound().build();
        } else if (process == 2) {
            return ResponseEntity.accepted().build();
        } else {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("v1/animals/{id}")
    public ResponseEntity<Animal> updateAnimal(@PathVariable("id") Long id, @RequestBody Animal animal) {
        int process = animalService.updateAnimal(id, animal);

        if (process == 0) {
            return ResponseEntity.ok().build();
        } else if (process == 1) {
            return ResponseEntity.notFound().build();
        } else if (process == 2) {
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("v1/animals/desease")
    public ResponseEntity<Result<List<AnimalDeseaseInfo>>> deseaseInfo() {
        List<AnimalDeseaseInfo> deseaseList = animalService.animalDeseaseList();

        return ResponseEntity.ok().body(new Result<>(deseaseList, deseaseList.size()));
    }

    @GetMapping("v1/animals/medicine")
    public ResponseEntity<Result<List<AnimalMedicine>>> medicineInfo() {
        List<AnimalMedicine> medicineList = animalService.medicineList();

        return ResponseEntity.ok().body(new Result<>(medicineList, medicineList.size()));
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
