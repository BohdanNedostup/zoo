package zoowebapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import zoowebapp.entity.UserRole;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    @Query("SELECT ur.role.roleName FROM UserRole ur WHERE ur.user.id = :userId")
    List<String> findRolesByUserId(@Param("userId") Long userId);

    @Query("SELECT ur.user.id FROM UserRole ur WHERE ur.role.roleName IN :rolesList")
    List<Long> findUsersByRolesList(@Param("rolesList") List<String> roles);

    @Transactional
    @Modifying
    @Query("DELETE FROM UserRole ur WHERE ur.user.id = :userId")
    void deleteUserRolesByUserId(@Param("userId") Long id);
}