package zoowebapp.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zoowebapp.dto.ProductDtoShop;
import zoowebapp.dto.filter.ProductShopFilter;
import zoowebapp.entity.Order;
import zoowebapp.entity.Product;
import zoowebapp.entity.enums.OrderStatus;
import zoowebapp.mapper.ProductMapper;
import zoowebapp.service.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/shop")
public class ProductShopRestApiController {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductSizeService productSizeService;

    @GetMapping("")
    public Page<ProductDtoShop> showProducts(@PageableDefault Pageable pageable,
                                             @RequestParam String name,
                                             @RequestParam BigDecimal priceFrom,
                                             @RequestParam BigDecimal priceTo,
                                             @RequestParam List<String> categories,
                                             @RequestParam List<String> sizes,
                                             @RequestParam String sortBy,
                                             @RequestParam String ascOrDesc){
        ProductShopFilter productShopFilter = new ProductShopFilter(name, priceFrom, validatePriceTo(priceTo),
                validateEmptyCategoryNamesList(categories), validateEmptySizeNamesList(sizes), sortBy, ascOrDesc);
        Page<Product> productsPage = productService.findProductsByPageByFilter(productShopFilter, pageable);
        List<ProductDtoShop> shopList = new ArrayList<>();
        productsPage.getContent().stream()
                .map(product -> shopList.add(ProductMapper.convertProductToProductDtoShop(product)))
                .collect(Collectors.toList());
        return  new PageImpl<>(shopList, pageable, productsPage.getTotalElements());
    }

    @PutMapping("/buy")
    public ResponseEntity<Void> makeOrder(Principal principal,
                                          @RequestParam(name = "id") Long productId,
                                          @RequestParam(name = "size") String size,
                                          @RequestParam(name = "qty") Integer qty){
        Product product = productService.findById(productId);
        Order order = Order.builder()
                .orderStatus(OrderStatus.NOT_CONFIRMED)
                .user(userService.findByEmail(principal.getName()))
                .product(productService.findById(productId))
                .productQty(qty)
                .summaryPrice(product.getPrice().multiply(new BigDecimal(qty)))
                .productSize(productSizeService.findByName(size))
                .createdAt(new Date())
                .build();
        orderService.save(order);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/orders")
    public Integer getNotConfirmedOrdersCount(Principal principal) {
        if (principal == null) {
            return -1;
        } else {
            return orderService.findNotConfirmedOrdersCountByUserId(userService.findByEmail(principal.getName()).getId());
        }
    }

    @GetMapping("/sizes")
    public List<String> getProductSizes() {
        List<String> sizes = new ArrayList<>();
        productSizeService.findAll().forEach(size -> sizes.add(size.getName()));
        return sizes;
    }

    @GetMapping("/categories")
    public List<String> getCategories(){
        List<String> categories = new ArrayList<>();
        categoryService.findAll().forEach(category -> categories.add(category.getName()));
        return categories;
    }

    private BigDecimal validatePriceTo(BigDecimal priceTo){
        if (priceTo.equals(new BigDecimal(0))){
            priceTo = new BigDecimal(Integer.MAX_VALUE);
        }
        return priceTo;
    }
    private List<String> validateEmptyCategoryNamesList(List<String> names){
        if (names.size() == 0){
            categoryService.findAll().forEach(category -> names.add(category.getName()));
        }
        return names;
    }

    private List<String> validateEmptySizeNamesList(List<String> sizes){
        if (sizes.size() == 0){
            productSizeService.findAll().forEach(size -> sizes.add(size.getName()));
        }
        return sizes;
    }
}
