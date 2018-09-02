package zoowebapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import zoowebapp.entity.Category;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT c FROM Category c WHERE c.name = :name")
    Category findByName(@Param("name") String name);

    @Query("SELECT c FROM Category c WHERE c.name IN :categories")
    List<Category> findAllByName(@Param("categories") List<String> categories);
}