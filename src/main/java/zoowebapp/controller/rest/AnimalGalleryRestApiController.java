package zoowebapp.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zoowebapp.dto.AnimalDtoGallery;
import zoowebapp.dto.filter.AnimalGalleryFilter;
import zoowebapp.entity.Animal;
import zoowebapp.mapper.AnimalMapper;
import zoowebapp.service.AnimalService;
import zoowebapp.service.DepartmentService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/gallery")
public class AnimalGalleryRestApiController {

    @Autowired
    private AnimalService animalService;

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("")
    public Page<AnimalDtoGallery> getAnimals(@PageableDefault Pageable pageable,
                                             @RequestParam("name") String name,
                                             @RequestParam List<String> departments,
                                             @RequestParam String sortBy,
                                             @RequestParam String ascOrDesc){
        AnimalGalleryFilter animalGalleryFilter = new AnimalGalleryFilter(name, departments, sortBy, ascOrDesc);
        Page<Animal> animalsPage = animalService.findAnimalsByPageByFilter(animalGalleryFilter, pageable);
        List<AnimalDtoGallery> animals = new ArrayList<>();
        animalsPage.getContent().
                stream()
                .map(animal -> animals.add(AnimalMapper.convertAnimalToAnimalDtoGallery(animal)))
                .collect(Collectors.toList());
        return new PageImpl<>(animals, pageable, animalsPage.getTotalElements());
    }

    @GetMapping("/departments")
    public List<String> getDepartments(){
        List<String> departments = new ArrayList<>();
        departmentService.findAll().forEach(department -> departments.add(department.getName()));
        return departments;
    }
}