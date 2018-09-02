package zoowebapp.service.impl;

import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import zoowebapp.dto.filter.AnimalFilter;
import zoowebapp.dto.filter.AnimalGalleryFilter;
import zoowebapp.entity.Animal;
import zoowebapp.entity.Department;
import zoowebapp.repository.AnimalRepository;
import zoowebapp.service.AnimalService;
import zoowebapp.service.CloudinaryService;
import zoowebapp.service.DepartmentService;
import zoowebapp.service.utils.DateUtils;
import zoowebapp.service.utils.StringUtils;

import javax.persistence.criteria.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class AnimalServiceImpl implements AnimalService {

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private CloudinaryService cloudinaryService;
    private final String DEFAULT_ANIMAL_IMAGE_URL = "http://res.cloudinary.com/dtn7opvqz/image/upload/v1529618431/animal/default-animal.png";


    @Override
    public Page<Animal> findAnimalsByPage(Pageable pageable) {
        return animalRepository.findAll(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()));
    }

    @Override
    public Page<Animal> findAnimalsByPageByFilter(AnimalFilter animalFilter, Pageable pageable) {

        return animalRepository.findAll(getSpecification(animalFilter), PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()));
    }

    private Specification getSpecification(AnimalFilter animalFilter) {
        return new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicateName = criteriaBuilder.like(root.get("name"), "%" + animalFilter.getName() + "%");
                Predicate predicateGenders = root.get("gender").in(animalFilter.getGenders());
                Predicate predicateStatuses = root.get("status").in(animalFilter.getStatuses());

                Predicate predicateDepartment = criteriaBuilder.and();
                if (animalFilter.getDepartments().size() != 0) {
                    List<Long> departmentsId = new ArrayList<>();
                    departmentService.findByNameList(animalFilter.getDepartments()).forEach(department -> departmentsId.add(department.getId()));
                    Join<Animal, Department> userDepartmentJoin = root.join("department");
                    predicateDepartment = userDepartmentJoin.get("id").in(departmentsId);
                }

                Calendar calendarMin = Calendar.getInstance();
                Calendar calendarMax = Calendar.getInstance();
                calendarMin.set(1900, 0, 1);
                calendarMax.set(3000, 0, 1);

                animalFilter.setCreatedAtTo(DateUtils.plusOneDay(animalFilter.getCreatedAtTo()));

                Predicate predicateCreatedAt =
                        animalFilter.getCreatedAtFrom() == null ?
                                animalFilter.getCreatedAtTo() == null ?
                                        criteriaBuilder.between(root.get("createdAt"), calendarMin.getTime(), calendarMax.getTime()) :
                                        criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), animalFilter.getCreatedAtTo()) :
                                animalFilter.getCreatedAtTo() == null ?
                                        criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), animalFilter.getCreatedAtFrom()) :
                                        criteriaBuilder.between(root.get("createdAt"), animalFilter.getCreatedAtFrom(), animalFilter.getCreatedAtTo());

                switch (animalFilter.getSortBy()){
                    case "id": {
                        criteriaQuery.orderBy(animalFilter.getAscOrDesc().equals("asc") ? criteriaBuilder.asc(root.get("id")) : criteriaBuilder.desc(root.get("id")));
                        break;
                    }
                    case "name": {
                        criteriaQuery.orderBy(animalFilter.getAscOrDesc().equals("asc") ? criteriaBuilder.asc(root.get("name")) : criteriaBuilder.desc(root.get("name")));
                        break;
                    }
                    case "createdAt": {
                        criteriaQuery.orderBy(animalFilter.getAscOrDesc().equals("asc") ? criteriaBuilder.asc(root.get("createdAt")) : criteriaBuilder.desc(root.get("createdAt")));
                        break;
                    }
                }

                return criteriaBuilder.and(predicateName, predicateGenders, predicateStatuses, predicateDepartment, predicateCreatedAt);
            }
        };
    }

    @Override
    public Page<Animal> findAnimalsByPageByFilter(AnimalGalleryFilter animalGalleryFilter, Pageable pageable) {

        return animalRepository.findAll(getSpecification(animalGalleryFilter), PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()));
    }

    private Specification getSpecification(AnimalGalleryFilter animalGalleryFilter){
        return new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {

                Predicate predicateName = criteriaBuilder.like(root.get("name"), "%" + animalGalleryFilter.getName() + "%");

                Predicate predicateDepartment = criteriaBuilder.and();
                if (animalGalleryFilter.getDepartments().size() != 0) {
                    List<Long> departmentsId = new ArrayList<>();
                    departmentService.findByNameList(animalGalleryFilter.getDepartments()).forEach(department -> departmentsId.add(department.getId()));
                    Join<Animal, Department> userDepartmentJoin = root.join("department");
                    predicateDepartment = userDepartmentJoin.get("id").in(departmentsId);
                }

                switch (animalGalleryFilter.getSortBy()) {
                    case "id": {
                        criteriaQuery.orderBy(animalGalleryFilter.getAscOrDesc().equals("asc") ? criteriaBuilder.asc(root.get("id")) : criteriaBuilder.desc(root.get("id")));
                        break;
                    }
                    case "name": {
                        criteriaQuery.orderBy(animalGalleryFilter.getAscOrDesc().equals("asc") ? criteriaBuilder.asc(root.get("name")) : criteriaBuilder.desc(root.get("name")));
                        break;
                    }
                }

                return criteriaBuilder.and(predicateName, predicateDepartment);
            }
        };
    }

    @Override
    public void save(Animal animal) {
        animal.setGlobalNumber(generateGlobalNumber());
        animalRepository.save(animal);
    }

    private String generateGlobalNumber() {
        String globalNumber = StringUtils.generateAlphaNumeric(20);
        if (animalRepository.findByGlobalNumber(globalNumber) != null) {
            globalNumber = generateGlobalNumber();
        }
        return globalNumber;
    }

    @Override
    public void update(Animal animal) {
        animalRepository.save(animal);
    }

    @Override
    public Animal findById(Long id) {
        return animalRepository.getOne(id);
    }

    @Override
    public List<Animal> findAll() {
        return animalRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        Animal animal = animalRepository.getOne(id);
        if (animal.getImageUrl() != null && !animal.getImageUrl().equals(DEFAULT_ANIMAL_IMAGE_URL)) {
            try {
                cloudinaryService.destroyFile("animal/animal" + id, ObjectUtils.emptyMap());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        animalRepository.deleteById(id);
    }

    @Override
    public Animal findByName(String name) {
        return animalRepository.findAnimalByAnimalName(name);
    }
}