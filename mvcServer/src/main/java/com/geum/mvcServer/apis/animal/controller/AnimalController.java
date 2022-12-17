package com.geum.mvcServer.apis.animal.controller;

import com.geum.mvcServer.apis.animal.entity.Animal;
import com.geum.mvcServer.apis.animal.model.AnimalVO;
import com.geum.mvcServer.apis.animal.service.AnimalService;
import com.geum.mvcServer.apis.batch.entity.AnimalDeseaseInfo;
import com.geum.mvcServer.apis.batch.entity.AnimalMedicine;
import com.geum.mvcServer.apis.user.entity.User;
import com.geum.mvcServer.config.SessionManager;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Transactional
public class AnimalController {

    private final AnimalService animalService;
    private final SessionManager sessionManager;

    @GetMapping("v1/animals")
    public ResponseEntity<Result<List<Animal>>> getAllAnimals(HttpServletRequest request) {
        List<Animal> animalList = animalService.getAllAnimals(request);

        if(animalList.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(new Result<>(animalList, animalList.size()));
        }
    }

    @PostMapping("v1/animals")
    public ResponseEntity<Result<List<Animal>>> addAnimal(@RequestBody AnimalVO animalData, HttpServletRequest request) {
        User user = (User) sessionManager.getSession(request);
        animalData.setUserId(user);

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

    @DeleteMapping("v1/animals/{id}")
    public ResponseEntity<Animal> deleteAnimal(@PathVariable("id") Long id) {
        int process = animalService.deleteAnimal(id);

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
    public ResponseEntity<Animal> updateAnimal(@PathVariable("id") Long id, @RequestBody Animal animal, HttpServletRequest request) {
        User user = (User) sessionManager.getSession(request);
        animal.setUserId(user);
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
