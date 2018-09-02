package zoowebapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import zoowebapp.entity.Department;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    @Query("SELECT d FROM Department d WHERE d.name = :name")
    Department findByName(@Param("name") String name);

    @Query("SELECT d FROM Department d WHERE d.name IN :names")
    List<Department> findByNameList(@Param("names") List<String> names);
}
