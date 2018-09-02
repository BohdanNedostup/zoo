package zoowebapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import zoowebapp.entity.News;

public interface NewsRepository extends JpaRepository<News, Long>, JpaSpecificationExecutor<News> {

    @Query("SELECT n FROM News n WHERE n.title = :title")
    News findByTitle(@Param("title") String title);

}
