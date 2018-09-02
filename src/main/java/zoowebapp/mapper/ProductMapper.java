package zoowebapp.mapper;

import org.modelmapper.ModelMapper;
import zoowebapp.dto.ProductDtoAdmin;
import zoowebapp.dto.ProductDtoOrder;
import zoowebapp.dto.ProductDtoSave;
import zoowebapp.dto.ProductDtoShop;
import zoowebapp.entity.Product;
import zoowebapp.entity.ProductSize;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface ProductMapper {

    static ProductDtoAdmin convertProductToProductDtoAdmin(Product product){
        ProductDtoAdmin productDtoAdmin = new ModelMapper().map(product, ProductDtoAdmin.class);
        productDtoAdmin.setCategory(product.getCategory().getName());
        List<ProductSize> sizes = product.getSizes();
        List<String> stringSizes = new ArrayList<>();
        sizes.forEach(size -> stringSizes.add(size.getName()));
        productDtoAdmin.setSizes(stringSizes);
        return productDtoAdmin;
    }

    static Product convertProductDtoAdminToProduct(ProductDtoAdmin productDtoAdmin){
        return new ModelMapper().map(productDtoAdmin, Product.class);
    }

    static Product convertProductDtoSaveToProduct(ProductDtoSave productDtoSave){
        return new ModelMapper().map(productDtoSave, Product.class);
    }

    static ProductDtoShop convertProductToProductDtoShop(Product product){
        ProductDtoShop productDtoShop = new ModelMapper().map(product, ProductDtoShop.class);
        productDtoShop.setCategory(product.getCategory().getName());
        List<String> sizes = new ArrayList<>();
        product.getSizes().forEach(size -> sizes.add(size.getName()));
        productDtoShop.setSizes(sizes);
        return productDtoShop;
    }

    static ProductDtoOrder convertProductToProductDtoOrder(Product product){
        ProductDtoOrder productDtoOrder = new ModelMapper().map(product, ProductDtoOrder.class);
        productDtoOrder.setCategory(product.getCategory().getName());
        List<String> sizes = new ArrayList<>();
        product.getSizes().forEach(size -> sizes.add(size.getName()));
        productDtoOrder.setSizes(sizes);
        return productDtoOrder;
    }
}