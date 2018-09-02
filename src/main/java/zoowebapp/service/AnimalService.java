package zoowebapp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zoowebapp.dto.filter.AnimalFilter;
import zoowebapp.dto.filter.AnimalGalleryFilter;
import zoowebapp.entity.Animal;

import java.util.List;

public interface AnimalService {

    Page<Animal> findAnimalsByPage(Pageable pageable);

    Page<Animal> findAnimalsByPageByFilter(AnimalFilter animalFilter, Pageable pageable);

    Page<Animal> findAnimalsByPageByFilter(AnimalGalleryFilter animalGalleryFilter, Pageable pageable);

    Animal findByName(String name);

    void save(Animal animal);

    void update(Animal animal);

    void deleteById(Long id);

    List<Animal> findAll();

    Animal findById(Long id);
}
