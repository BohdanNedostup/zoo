package zoowebapp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zoowebapp.dto.filter.ProductFilter;
import zoowebapp.dto.filter.ProductShopFilter;
import zoowebapp.entity.Category;
import zoowebapp.entity.Product;
import zoowebapp.entity.ProductSize;

import java.io.IOException;
import java.util.List;

public interface ProductService{

    void save(Product product);

    void update(Product product);

    void deleteById(Long id) throws IOException;

    List<Product> findAll();

    Product findById(Long id);

    Page<Product> findProductsByPage(Pageable pageable);

    Product findProductByName(String name);

    Page<Product> findProductsByPageByFilter(ProductFilter productFilter, Pageable pageable);

    Page<Product> findProductsByPageByFilter(ProductShopFilter productShopFilter, Pageable pageable);
}
