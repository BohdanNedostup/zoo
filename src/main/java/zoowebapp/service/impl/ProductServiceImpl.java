package zoowebapp.service.impl;

import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import zoowebapp.dto.filter.ProductFilter;
import zoowebapp.dto.filter.ProductShopFilter;
import zoowebapp.entity.Category;
import zoowebapp.entity.Product;
import zoowebapp.entity.ProductSize;
import zoowebapp.repository.ProductRepository;
import zoowebapp.service.CategoryService;
import zoowebapp.service.CloudinaryService;
import zoowebapp.service.ProductService;
import zoowebapp.service.ProductSizeService;
import zoowebapp.service.utils.DateUtils;
import zoowebapp.service.utils.StringUtils;

import javax.persistence.criteria.*;
import java.io.IOException;
import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CloudinaryService cloudinaryService;
    private final String DEFAULT_PRODUCT_IMAGE_URL = "http://res.cloudinary.com/dtn7opvqz/image/upload/v1530292716/product/default-product.jpg";

    @Autowired
    private ProductSizeService productSizeService;

    @Override
    public void save(Product product) {
        product.setGlobalNumber(generateGlobalNumber());
        product.setImageUrl(DEFAULT_PRODUCT_IMAGE_URL);
        productRepository.save(product);
    }

    @Override
    public void update(Product product) {
        productRepository.save(product);
    }

    @Override
    public void deleteById(Long id) throws IOException {
        Product product = productRepository.getOne(id);
        System.out.println(product);
        if (product.getImageUrl() != null && !product.getImageUrl().equals(DEFAULT_PRODUCT_IMAGE_URL)) {
            cloudinaryService.destroyFile("product/product" + id, ObjectUtils.emptyMap());
        }
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product findById(Long id) {
        return productRepository.getOne(id);
    }

    @Override
    public Page<Product> findProductsByPage(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Product findProductByName(String name) {
        return productRepository.findProductByName(name);
    }

    private String generateGlobalNumber() {
        String globalNumber = StringUtils.generateAlphaNumeric(20);
        if (productRepository.findByGlobalNumber(globalNumber) != null) {
            generateGlobalNumber();
        }

        return globalNumber;
    }

    @Override
    public Page<Product> findProductsByPageByFilter(ProductFilter productFilter, Pageable pageable) {
        return productRepository.findAll(getSpecification(productFilter), PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()));
    }

    private Specification getSpecification(ProductFilter productFilter){
        return new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {

                Predicate predicateName = criteriaBuilder.like(root.get("name"), "%" + productFilter.getName() + "%");
                Predicate predicatePrice = criteriaBuilder.between(root.get("price"), productFilter.getPriceFrom(), productFilter.getPriceTo());
                List<Long> sizesId = new ArrayList<>();
                productSizeService.findByNameList(productFilter.getSizes()).forEach(productSize -> sizesId.add(productSize.getId()));
                Join<Product, ProductSize> productSizeJoin = root.join("sizes");
                Predicate predicateSize = productSizeJoin.get("id").in(sizesId);
                List<Category> categories = categoryService.findAllByName(productFilter.getCategories());
                Predicate predicateCategory = root.get("category").in(categories);

                Calendar calendarMin = Calendar.getInstance();
                Calendar calendarMax = Calendar.getInstance();
                calendarMin.set(1900, 0, 1);
                calendarMax.set(3000, 0, 1);

                productFilter.setCreatedAtTo(DateUtils.plusOneDay(productFilter.getCreatedAtTo()));

                Predicate predicateCreatedAt =
                        productFilter.getCreatedAtFrom() == null ?
                                productFilter.getCreatedAtTo() == null ?
                                        criteriaBuilder.between(root.get("createdAt"), calendarMin.getTime(), calendarMax.getTime()) :
                                        criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), productFilter.getCreatedAtTo()) :
                                productFilter.getCreatedAtTo() == null ?
                                        criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), productFilter.getCreatedAtFrom()) :
                                        criteriaBuilder.between(root.get("createdAt"), productFilter.getCreatedAtFrom(), productFilter.getCreatedAtTo());

                switch(productFilter.getSortBy()) {
                    case "id": {
                        criteriaQuery.orderBy(productFilter.getAscOrDesc().equals("asc") ? criteriaBuilder.asc(root.get("id")) : criteriaBuilder.desc(root.get("id")));
                        break;
                    }
                    case "name": {
                        criteriaQuery.orderBy(productFilter.getAscOrDesc().equals("asc") ? criteriaBuilder.asc(root.get("name")) : criteriaBuilder.desc(root.get("name")));
                        break;
                    }
                    case "price": {
                        criteriaQuery.orderBy(productFilter.getAscOrDesc().equals("asc") ? criteriaBuilder.asc(root.get("price")) : criteriaBuilder.desc(root.get("price")));
                        break;
                    }
                    case "createdAt": {
                        criteriaQuery.orderBy(productFilter.getAscOrDesc().equals("asc") ? criteriaBuilder.asc(root.get("createdAt")) : criteriaBuilder.desc(root.get("createdAt")));
                        break;
                    }
                }

                criteriaQuery.distinct(true);
                return criteriaBuilder.and(predicateName, predicatePrice, predicateCreatedAt, predicateCategory, predicateSize);
            }
        };
    }

    @Override
    public Page<Product> findProductsByPageByFilter(ProductShopFilter productShopFilter, Pageable pageable) {
        return productRepository.findAll(getSpecification(productShopFilter), PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()));
    }

    private Specification getSpecification(ProductShopFilter productShopFilter){
        return new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {

                Predicate predicateName = criteriaBuilder.like(root.get("name"), "%" + productShopFilter.getName() + "%");
                Predicate predicatePrice = criteriaBuilder.between(root.get("price"), productShopFilter.getPriceFrom(), productShopFilter.getPriceTo());
                List<Category> categories = categoryService.findAllByName(productShopFilter.getCategories());
                Predicate predicateCategory = root.get("category").in(categories);
                List<Long> sizesId = new ArrayList<>();
                productSizeService.findByNameList(productShopFilter.getSizes()).forEach(productSize -> sizesId.add(productSize.getId()));
                Join<Product, ProductSize> productSizeJoin = root.join("sizes");
                Predicate predicateSize = productSizeJoin.get("id").in(sizesId);

                switch(productShopFilter.getSortBy()) {
                    case "id": {
                        criteriaQuery.orderBy(productShopFilter.getAscOrDesc().equals("asc") ? criteriaBuilder.asc(root.get("id")) : criteriaBuilder.desc(root.get("id")));
                        break;
                    }
                    case "name": {
                        criteriaQuery.orderBy(productShopFilter.getAscOrDesc().equals("asc") ? criteriaBuilder.asc(root.get("name")) : criteriaBuilder.desc(root.get("name")));
                        break;
                    }
                    case "price": {
                        criteriaQuery.orderBy(productShopFilter.getAscOrDesc().equals("asc") ? criteriaBuilder.asc(root.get("price")) : criteriaBuilder.desc(root.get("price")));
                        break;
                    }
                    case "createdAt": {
                        criteriaQuery.orderBy(productShopFilter.getAscOrDesc().equals("asc") ? criteriaBuilder.asc(root.get("createdAt")) : criteriaBuilder.desc(root.get("createdAt")));
                        break;
                    }
                }

                criteriaQuery.distinct(true);
                return criteriaBuilder.and(predicateName, predicatePrice, predicateCategory, predicateSize);
            }
        };
    }
}