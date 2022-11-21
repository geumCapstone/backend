package com.geum.mvcServer.animal.service;

import com.geum.mvcServer.animal.entity.Animal;
import com.geum.mvcServer.animal.entity.AnimalRepository;
import com.geum.mvcServer.animal.model.AnimalVO;
import com.geum.mvcServer.batch.entity.AnimalDeseaseInfo;
import com.geum.mvcServer.batch.entity.AnimalDeseaseInfoRepository;
import com.geum.mvcServer.batch.entity.AnimalMedicine;
import com.geum.mvcServer.batch.entity.AnimalMedicineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnimalService {

    private final AnimalRepository animalRepository;
    private final AnimalDeseaseInfoRepository animalDeseaseInfoRepository;
    private final AnimalMedicineRepository animalMedicineRepository;

    public List<Animal> addAnimal(AnimalVO animalData) {
        Animal animal = new Animal();
        animal.setProviderId(animalData.getProviderId());
        animal.setName(animalData.getName());
        animal.setRace(animalData.getRace());
        animal.setAge(animalData.getAge());
        animal.setBirthday(animalData.getBirthday());

        animalRepository.save(animal);
        List<Animal> data = animalRepository.findByProviderId(animalData.getProviderId());

        return data;
    }

    /** deleteAnimal
     * CODE 0 : SUCCESS
     * CODE 1 : FAILED
     * CODE 2 : PROGRESSING */
    public int deleteAnimal(Animal animal) {
        if(!animalRepository.existsById(animal.getId())) {
            return 1;
        }

        animalRepository.delete(animal);

        if(animalRepository.existsById(animal.getId())) {
            return 2; // 작업 대기
        }

        return 0;
    }

    /** updateAnimal
     * CODE 0 : SUCCESS
     * CODE 1 : NOT FOUND
     * CODE 2 : BAD REQUEST(ID 불일치) */
    public int updateAnimal(Long id, Animal animal) {
        if (!animalRepository.existsById(animal.getId())) {
            return 1;
        } else if (animal.getId() != id) {
            return 2;
        }

        animalRepository.save(animal);

        return 0;
    }

    public List<AnimalDeseaseInfo> animalDeseaseList() {
        List<AnimalDeseaseInfo> deseaseList = animalDeseaseInfoRepository.findAll();
        return deseaseList;
    }

    public List<AnimalMedicine> medicineList() {
        List<AnimalMedicine> medicineList = animalMedicineRepository.findAll();
        return medicineList;
    }
}
