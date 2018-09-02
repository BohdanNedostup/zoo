package zoowebapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zoowebapp.entity.ProductSize;
import zoowebapp.repository.ProductSizeRepository;
import zoowebapp.service.ProductSizeService;

import java.util.List;

@Service
public class ProductSizeServiceImpl implements ProductSizeService {

    @Autowired
    private ProductSizeRepository productSizeRepository;

    @Override
    public void save(ProductSize productSize) {
        productSizeRepository.save(productSize);
    }

    @Override
    public void update(ProductSize productSize) {
        productSizeRepository.save(productSize);
    }

    @Override
    public void deleteById(Long id){
        productSizeRepository.deleteById(id);
    }

    @Override
    public List<ProductSize> findAll() {
        return productSizeRepository.findAll();
    }

    @Override
    public ProductSize findById(Long id) {
        return productSizeRepository.getOne(id);
    }

    @Override
    public List<ProductSize> findByNameList(List<String> names) {
        return productSizeRepository.findByNameList(names);
    }

    @Override
    public ProductSize findByName(String name) {
        return productSizeRepository.findByName(name);
    }
}
