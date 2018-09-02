package zoowebapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import zoowebapp.entity.Category;
import zoowebapp.repository.CategoryRepository;

import java.util.List;

public interface CategoryService{

    void save(Category category);

    void update(Category category);

    void deleteById(Long id);

    List<Category> findAll();

    Category findById(Long id);

    Category findByName(String name);

    List<Category> findAllByName(List<String> names);
}
