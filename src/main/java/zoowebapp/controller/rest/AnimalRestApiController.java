package zoowebapp.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import zoowebapp.dto.AnimalDtoAdmin;
import zoowebapp.dto.AnimalDtoSave;
import zoowebapp.dto.filter.AnimalFilter;
import zoowebapp.entity.Animal;
import zoowebapp.entity.enums.AnimalGender;
import zoowebapp.entity.enums.AnimalStatus;
import zoowebapp.mapper.AnimalMapper;
import zoowebapp.service.AnimalService;
import zoowebapp.service.CloudinaryService;
import zoowebapp.service.DepartmentService;
import zoowebapp.service.utils.DateUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/animals")
public class AnimalRestApiController {

    @Autowired
    private AnimalService animalService;

    @Autowired
    CloudinaryService cloudinaryService;
    private final String DEFAULT_ANIMAL_IMAGE_URL = "http://res.cloudinary.com/dtn7opvqz/image/upload/v1530181411/animal/default-animal.png";

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("")
    public Page<AnimalDtoAdmin> getAllAnimals(@PageableDefault Pageable pageable,
                                              @RequestParam String name,
                                              @RequestParam String createdAtFrom,
                                              @RequestParam String createdAtTo,
                                              @RequestParam List<String> statuses,
                                              @RequestParam List<String> genders,
                                              @RequestParam List<String> departments,
                                              @RequestParam String sortBy,
                                              @RequestParam String ascOrDesc) {
        AnimalFilter animalFilter = new AnimalFilter(name, DateUtils.convertStringToDate(createdAtFrom),
                DateUtils.convertStringToDate(createdAtTo), convertStringListToUserStatusList(statuses),
                convertStringListToUserGenderList(genders), departments, sortBy, ascOrDesc);
        Page<Animal> animalsPage = animalService.findAnimalsByPageByFilter(animalFilter, pageable);
        List<AnimalDtoAdmin> animals = new ArrayList<>();
        animalsPage.getContent().
                stream()
                .map(animal -> animals.add(AnimalMapper.convertAnimalToAnimalDtoAdmin(animal)))
                .collect(Collectors.toList());

        return new PageImpl<>(animals, pageable, animalsPage.getTotalElements());
    }

    @GetMapping("/statuses")
    public List<String> getAnimalStatuses() {
        List<String> statuses = new ArrayList<>();
        Arrays.asList(AnimalStatus.values()).forEach(animalStatus -> statuses.add(animalStatus.getStatus()));
        return statuses;
    }

    @GetMapping("/genders")
    public List<String> getAnimalGenders() {
        List<String> genders = new ArrayList<>();
        Arrays.asList(AnimalGender.values()).forEach(gender -> genders.add(gender.getGender()));
        return genders;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnimal(@PathVariable Long id) {
        animalService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PostMapping("/image/{id}")
    public ResponseEntity<Void> uploadImage(@PathVariable Long id,
                                             @RequestParam("image") MultipartFile multipartFile) throws IOException {
        String imageUrl;
        if (multipartFile == null)
            imageUrl = DEFAULT_ANIMAL_IMAGE_URL;
        else
            imageUrl = cloudinaryService.uploadFile(multipartFile, "animal/");
        Animal animal = animalService.findById(id);
        animal.setImageUrl(imageUrl);
        animalService.update(animal);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("")
    public Long saveAnimal(@RequestBody AnimalDtoSave animalDtoSave){
        if (animalService.findByName(animalDtoSave.getName()) != null){
            return -1L;
        }
        Animal animal = new Animal();
        animal.setName(animalDtoSave.getName());
        animal.setStatus(AnimalStatus.valueOf(animalDtoSave.getStatus()));
        animal.setWikiUrl(animalDtoSave.getWikiUrl());
        animal.setGender(AnimalGender.valueOf(animalDtoSave.getGender()));
        animal.setDescription(animalDtoSave.getDescription());
        animal.setIllnessesHistory(animalDtoSave.getIllnessesHistory());
        animal.setImageUrl(DEFAULT_ANIMAL_IMAGE_URL);
        animal.setDepartment(departmentService.findByName(animalDtoSave.getDepartment()));
        animalService.save(animal);
        return animalService.findByName(animalDtoSave.getName()).getId();
    }

    @PutMapping("")
    public ResponseEntity<Void> updateAnimal(@RequestBody AnimalDtoAdmin animalDtoAdmin) {
        Animal animal = animalService.findById(animalDtoAdmin.getId());
        if (animal != null) {
            animal = AnimalMapper.convertAnimalDtoAdminToAnimal(animalDtoAdmin);
            animal.setDepartment(departmentService.findByName(animalDtoAdmin.getDepartment()));
            animalService.update(animal);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private List<AnimalStatus> convertStringListToUserStatusList(List<String> statuses) {
        if (statuses.size() == 0) {
            return Arrays.asList(AnimalStatus.values());
        } else {
            AnimalStatus animalStatuses[] = AnimalStatus.values();
            ArrayList<AnimalStatus> resultStatusList = new ArrayList<>();
            for (int i = 0; i < animalStatuses.length; i++) {
                for (int j = 0; j < statuses.size(); j++) {
                    if (statuses.get(j).equals(animalStatuses[i].getStatus())) {
                        resultStatusList.add(animalStatuses[i]);
                    }
                }
            }
            return resultStatusList;
        }
    }

    private List<AnimalGender> convertStringListToUserGenderList(List<String> genders) {
        if (genders.size() == 0) {
            return Arrays.asList(AnimalGender.values());
        } else {
            AnimalGender animalGenders[] = AnimalGender.values();
            List<AnimalGender> resultGenderList = new ArrayList<>();
            for (int i = 0; i < animalGenders.length; i++) {
                for (int j = 0; j < genders.size(); j++) {
                    if (genders.get(j).equals(animalGenders[i].getGender())) {
                        resultGenderList.add(animalGenders[i]);
                    }
                }
            }
            return resultGenderList;
        }
    }

}
