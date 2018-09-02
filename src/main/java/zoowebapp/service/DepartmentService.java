package zoowebapp.service;

import zoowebapp.entity.Department;

import java.util.List;

public interface DepartmentService{

    void save(Department department);

    void update(Department department);

    void deleteById(Long id);

    List<Department> findAll();

    Department findById(Long id);

    Department findByName(String name);

    List<Department> findByNameList(List<String> names);
}
