package zoowebapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import zoowebapp.mapper.ProductMapper;
import zoowebapp.service.ProductService;

@Controller
@RequestMapping("/")
public class ShopController {

    @Autowired
    private ProductService productService;

    @GetMapping("shop")
    public String showShop(){
        return "shop";
    }

    @GetMapping("shop/{id}")
    public String showProduct(@PathVariable Long id,
                              Model model){
        model.addAttribute("product", ProductMapper.convertProductToProductDtoOrder(productService.findById(id)));
        return "product";
    }
}