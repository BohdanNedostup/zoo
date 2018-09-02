package zoowebapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import zoowebapp.controller.rest.AdminRestApiController;
import zoowebapp.dto.UserDtoRegister;
import zoowebapp.entity.User;
import zoowebapp.entity.enums.UserGender;
import zoowebapp.entity.enums.UserStatus;
import zoowebapp.mapper.UserMapper;
import zoowebapp.service.CloudinaryService;
import zoowebapp.service.UserService;


import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.Principal;
import java.util.Base64;


@Controller
@SessionAttributes("userGenders")
@RequestMapping("/")
public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    CloudinaryService cloudinaryService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("")
    public String showHome(Principal principal) throws IOException {
        if (principal != null) {
            User user = userService.findByEmail(principal.getName());
            if (user != null && user.getStatus() == UserStatus.OFFLINE) {
                user.setStatus(UserStatus.ONLINE);
                userService.update(user);
            }
        }
        return "home";
    }

    @GetMapping("login")
    public String showLogin(Principal principal){
        if (principal != null){
            return "redirect:/";
        }
        return "login";
    }

    @GetMapping("log_out")
    public String logout(Principal principal){
        User user = userService.findByEmail(principal.getName());
        user.setStatus(UserStatus.OFFLINE);
        userService.update(user);
        return "redirect:/logout";
    }

    @GetMapping("register")
    public String showRegister(Model model, Principal principal){
        if (principal != null){
            return "redirect:/";
        }
        UserDtoRegister userDtoRegister = new UserDtoRegister();
        userDtoRegister.setUserGender(UserGender.MALE);
        model.addAttribute("registerUser", userDtoRegister);
        model.addAttribute("userGenders", UserGender.values());
        return "register";
    }

    @PostMapping("register")
    public String registerUser(@ModelAttribute("registerUser") @Valid UserDtoRegister userDtoRegister,
                               BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "register";
        }
        User user = UserMapper.convertUserDtoRegisterToUser(userDtoRegister);
        userService.save(user);
        return "redirect:/";
    }

    @GetMapping("verify")
    public String verifyAccount(
            @RequestParam("token") String token,
            @RequestParam("id") String userIdStr
    ){
        userService.verifyAccount(token, userIdStr);
        return "redirect:/";
    }

    @GetMapping("403")
    public String showError(){
        return "403";
    }
}
