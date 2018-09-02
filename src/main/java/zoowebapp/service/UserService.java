package zoowebapp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zoowebapp.dto.UserDtoAdmin;
import zoowebapp.dto.filter.UserFilter;
import zoowebapp.entity.User;

import java.util.List;

public interface UserService {

    void save(User user);

    void update(User user);

    void deleteById(Long id);

    List<User> findAll();

    User findById(Long id);

    User findByEmail(String email);

    void verifyAccount(String token, String id);

    User findByTelephone(String telephone);

    Page<User> findUsersByPage(Pageable pageable);

    Page<User> findUsersByPageByFilter(UserFilter userFilter, Pageable pageable);
}