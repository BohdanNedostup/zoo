package zoowebapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zoowebapp.entity.UploadFile;

@Repository
public interface UploadFileRepository extends JpaRepository<UploadFile, Long> {

}
