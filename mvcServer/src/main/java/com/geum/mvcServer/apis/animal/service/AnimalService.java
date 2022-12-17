package com.geum.mvcServer.apis.animal.service;

import com.geum.mvcServer.apis.animal.entity.Animal;
import com.geum.mvcServer.apis.animal.entity.AnimalRepository;
import com.geum.mvcServer.apis.animal.model.AnimalVO;
import com.geum.mvcServer.apis.batch.entity.AnimalDeseaseInfo;
import com.geum.mvcServer.apis.batch.entity.AnimalDeseaseInfoRepository;
import com.geum.mvcServer.apis.batch.entity.AnimalMedicine;
import com.geum.mvcServer.apis.batch.entity.AnimalMedicineRepository;
import com.geum.mvcServer.apis.user.entity.User;
import com.geum.mvcServer.config.SessionManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnimalService {

    private final AnimalRepository animalRepository;
    private final AnimalDeseaseInfoRepository animalDeseaseInfoRepository;
    private final AnimalMedicineRepository animalMedicineRepository;
    private final SessionManager sessionManager;

    public List<Animal> getAllAnimals(HttpServletRequest request) {
        User user = (User) sessionManager.getSession(request);
        List<Animal> animalList = animalRepository.findAllByUserId(user.getId());

        return animalList;
    }

    public List<Animal> addAnimal(AnimalVO animalData) {
        Animal animal = new Animal();
        animal.setUserId(animalData.getUserId());
        animal.setName(animalData.getName());
        animal.setRace(animalData.getRace());
        animal.setAge(animalData.getAge());
        animal.setBirthday(animalData.getBirthday());

        animalRepository.save(animal);
        List<Animal> data = animalRepository.findByUserId(animalData.getUserId());

        return data;
    }

    /** deleteAnimal
     * CODE 0 : SUCCESS
     * CODE 1 : FAILED
     * CODE 2 : PROGRESSING */
    public int deleteAnimal(Long id) {
        if(!animalRepository.existsById(id)) {
            return 1;
        }

        Optional<Animal> animal = animalRepository.findById(id);

        animalRepository.delete(animal);

        if(animalRepository.existsById(id)) {
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
