package zoowebapp.mapper;

import org.modelmapper.ModelMapper;
import zoowebapp.dto.AnimalDtoAdmin;
import zoowebapp.dto.AnimalDtoGallery;
import zoowebapp.entity.Animal;

public interface AnimalMapper {

    static AnimalDtoAdmin convertAnimalToAnimalDtoAdmin(Animal animal){
        AnimalDtoAdmin animalDtoAdmin = new ModelMapper().map(animal, AnimalDtoAdmin.class);
        animalDtoAdmin.setDepartment(animal.getDepartment() == null ? "" : animal.getDepartment().getName());
        return animalDtoAdmin;
    }

    static Animal convertAnimalDtoAdminToAnimal(AnimalDtoAdmin animalDtoAdmin){
        return new ModelMapper().map(animalDtoAdmin, Animal.class);
    }

    static AnimalDtoGallery convertAnimalToAnimalDtoGallery(Animal animal){
        return new ModelMapper().map(animal, AnimalDtoGallery.class);
    }

}
