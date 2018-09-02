package zoowebapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import zoowebapp.entity.ProductSize;

import java.util.List;

@Repository
public interface ProductSizeRepository extends JpaRepository<ProductSize, Long> {

    @Query("SELECT ps FROM ProductSize ps WHERE ps.name IN :names")
    List<ProductSize> findByNameList(@Param("names") List<String> names);

    @Query("SELECT ps FROM ProductSize ps WHERE ps.name = :name")
    ProductSize findByName(@Param("name") String name);
}
