package zoowebapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final String ADMIN_PACKAGE = "admin/";

    @GetMapping("/manage")
    public String showAdminPage(){
        return ADMIN_PACKAGE + "admin-page";
    }

    @GetMapping("/manage/users")
    public String showUsers(){
        return ADMIN_PACKAGE + "users";
    }

    @GetMapping("/animals")
    public String showAnimals(){
        return ADMIN_PACKAGE + "animals";
    }

    @GetMapping("/manage/news")
    public String showNews(){
        return ADMIN_PACKAGE + "news";
    }

    @GetMapping("/manage/products")
    public String showProducts(){
        return ADMIN_PACKAGE + "products";
    }

    @GetMapping("/manage/support")
    public String showSupport(){
        return ADMIN_PACKAGE + "support";
    }

    @GetMapping("/manage/tickets")
    public String showTickets(){
        return ADMIN_PACKAGE + "tickets";
    }

    @GetMapping("/manage/orders")
    public String showOrders(){
        return ADMIN_PACKAGE + "orders";
    }

    @GetMapping("/manage/about")
    public String showAbout(){
        return ADMIN_PACKAGE + "about";
    }
}