package zoowebapp.service;

import zoowebapp.entity.ProductSize;

import java.io.IOException;
import java.util.List;

public interface ProductSizeService {

    void save(ProductSize productSize);

    void update(ProductSize productSize);

    void deleteById(Long id) throws IOException;

    List<ProductSize> findAll();

    ProductSize findById(Long id);

    List<ProductSize> findByNameList(List<String> names);

    ProductSize findByName(String name);
}
