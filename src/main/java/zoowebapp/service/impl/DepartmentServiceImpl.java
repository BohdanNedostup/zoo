package zoowebapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zoowebapp.entity.Department;
import zoowebapp.repository.DepartmentRepository;
import zoowebapp.service.DepartmentService;

import java.util.List;


@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public void save(Department department) {
        departmentRepository.save(department);
    }

    @Override
    public void update(Department department) {
        departmentRepository.save(department);
    }

    @Override
    public void deleteById(Long id) {
        departmentRepository.deleteById(id);
    }

    @Override
    public List<Department> findAll() {
        return departmentRepository.findAll();
    }

    @Override
    public Department findById(Long id) {
        return departmentRepository.getOne(id);
    }

    @Override
    public Department findByName(String name) {
        return departmentRepository.findByName(name);
    }

    @Override
    public List<Department> findByNameList(List<String> names) {
        return departmentRepository.findByNameList(names);
    }
}
