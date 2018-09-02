package zoowebapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import zoowebapp.entity.Animal;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long>, JpaSpecificationExecutor<Animal> {

    Animal findByGlobalNumber(String number);

    @Query("SELECT a from Animal a WHERE a.name = :name")
    Animal findAnimalByAnimalName(@Param("name") String name);
}