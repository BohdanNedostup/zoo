package zoowebapp.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import zoowebapp.dto.ProductDtoAdmin;
import zoowebapp.dto.ProductDtoSave;
import zoowebapp.dto.filter.ProductFilter;
import zoowebapp.entity.Product;
import zoowebapp.mapper.ProductMapper;
import zoowebapp.service.CategoryService;
import zoowebapp.service.CloudinaryService;
import zoowebapp.service.ProductService;
import zoowebapp.service.ProductSizeService;
import zoowebapp.service.utils.DateUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/manage/products")
public class ProductRestApiController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CloudinaryService cloudinaryService;
    private final String DEFAULT_PRODUCT_IMAGE_URL = "http://res.cloudinary.com/dtn7opvqz/image/upload/v1530292716/product/default-product.jpg";

    @Autowired
    private ProductSizeService productSizeService;

    @GetMapping("")
    public Page<ProductDtoAdmin> getAllProducts(@PageableDefault Pageable pageable,
                                                @RequestParam String name,
                                                @RequestParam BigDecimal priceFrom,
                                                @RequestParam BigDecimal priceTo,
                                                @RequestParam String createdAtFrom,
                                                @RequestParam String createdAtTo,
                                                @RequestParam List<String> categories,
                                                @RequestParam List<String> sizes,
                                                @RequestParam String sortBy,
                                                @RequestParam String ascOrDesc){
        ProductFilter productFilter = new ProductFilter(name, priceFrom, validatePriceTo(priceTo),
                DateUtils.convertStringToDate(createdAtFrom), DateUtils.convertStringToDate(createdAtTo),
                validateEmptyCategoryNamesList(categories), validateEmptySizeNamesList(sizes), sortBy, ascOrDesc);
        Page<Product> productsPage = productService.findProductsByPageByFilter(productFilter, pageable);
        List<ProductDtoAdmin> productDtoAdmins = new ArrayList<>();
        productsPage.forEach(product -> productDtoAdmins.add(ProductMapper.convertProductToProductDtoAdmin(product)));
        return new PageImpl<>(productDtoAdmins, pageable, productsPage.getTotalElements());
    }

    @PostMapping("/image/{id}")
    private ResponseEntity<Void> uploadImage(@PathVariable Long id,
                                             @RequestParam("image") MultipartFile multipartFile) throws IOException {
        System.out.println("INSIDE UPLOADING WITH ID " + id);
        String imageUrl;
        if (multipartFile == null)
            imageUrl = DEFAULT_PRODUCT_IMAGE_URL;
        else
            imageUrl = cloudinaryService.uploadFile(multipartFile, "product/");
        Product product = productService.findById(id);
        product.setImageUrl(imageUrl);
        productService.update(product);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("")
    private ResponseEntity<Void> updateProduct(@RequestBody ProductDtoAdmin productDtoAdmin) {
        Product product = productService.findById(productDtoAdmin.getId());
        if (product != null) {
            product = ProductMapper.convertProductDtoAdminToProduct(productDtoAdmin);
            product.setCategory(categoryService.findByName(productDtoAdmin.getCategory()));
            product.setSizes(productSizeService.findByNameList(productDtoAdmin.getSizes()));
            productService.update(product);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        try {
            System.out.println(id);
            productService.deleteById(id);
        }catch (Exception e){
            System.out.println(e);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("")
    public Long saveProduct(@RequestBody ProductDtoSave productDtoSave){
        Product product = productService.findProductByName(productDtoSave.getName());
        if (product != null){
            return -1L;
        }
        product = ProductMapper.convertProductDtoSaveToProduct(productDtoSave);
        product.setCategory(categoryService.findByName(productDtoSave.getCategory()));
        product.setSizes(productSizeService.findByNameList(productDtoSave.getSizes()));
        productService.save(product);
        return productService.findProductByName(productDtoSave.getName()).getId();
    }


    private List<String> validateEmptyCategoryNamesList(List<String> names){
        if (names.size() == 0){
            categoryService.findAll().forEach(category -> names.add(category.getName()));
        }
        return names;
    }

    private BigDecimal validatePriceTo(BigDecimal priceTo){
        if (priceTo.equals(new BigDecimal(0))){
            priceTo = new BigDecimal(Integer.MAX_VALUE);
        }
        return priceTo;
    }

    private List<String> validateEmptySizeNamesList(List<String> sizes){
        if (sizes.size() == 0){
            productSizeService.findAll().forEach(size -> sizes.add(size.getName()));
        }
        return sizes;
    }
}