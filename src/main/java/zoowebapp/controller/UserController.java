package zoowebapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import zoowebapp.dto.UserDtoProfile;
import zoowebapp.entity.User;
import zoowebapp.entity.enums.UserGender;
import zoowebapp.mapper.UserMapper;
import zoowebapp.repository.UserRoleRepository;
import zoowebapp.service.CloudinaryService;
import zoowebapp.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.Arrays;


@Controller
@RequestMapping("/user/")
@SessionAttributes("image")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private UserRoleRepository userRoleRepository;

    private final String USER_PACKAGE = "user/";

    @GetMapping("profile")
    public String showProfile(Model model, Principal principal){
        User user = userService.findByEmail(principal.getName());
        model.addAttribute("user", UserMapper.convertUserToUserDtoProfile(user));
        model.addAttribute("userGenders", Arrays.asList(UserGender.values()));
        model.addAttribute("image", user.getImageUrl());
        return USER_PACKAGE + "profile";
    }

    @PostMapping("profile")
    public String updateProfile(@ModelAttribute("user") @Valid UserDtoProfile userDtoProfile,
                                BindingResult bindingResult, Principal principal){
        User user = userService.findByEmail(principal.getName());
        User validUser = userService.findByTelephone(userDtoProfile.getTelephone());
        if (validUser != user && validUser != null){
            bindingResult.addError(new ObjectError("", "Such telephone number is already exist"));
        }
        if (bindingResult.hasErrors()){
            return USER_PACKAGE + "profile";
        }
        user = UserMapper.convertUserDtoProfileToUser(userDtoProfile, user);
        userService.update(user);
        return "redirect:/user/profile";
    }

    @PostMapping("profile/photo")
    public String updateProfilePhoto(@RequestParam("profileImage")MultipartFile file, Principal principal) throws IOException {
        MultipartFile file1 = new MultipartFile() {
            @Override
            public String getName() {
                return "user" + userService.findByEmail(principal.getName()).getId();
            }

            @Override
            public String getOriginalFilename() {
                return "user" + userService.findByEmail(principal.getName()).getId() + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            }

            @Override
            public String getContentType() {
                return file.getContentType();
            }

            @Override
            public boolean isEmpty() {
                return file.isEmpty();
            }

            @Override
            public long getSize() {
                return file.getSize();
            }

            @Override
            public byte[] getBytes() throws IOException {
                return file.getBytes();
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return file.getInputStream();
            }

            @Override
            public void transferTo(File dest) throws IOException, IllegalStateException {

            }
        };
        String imageUrl = cloudinaryService.uploadFile(file1, "user/");
        User user = userService.findByEmail(principal.getName());
        user.setImageUrl(imageUrl);
        userService.update(user);
        return "redirect:/user/profile";
    }

    @PostMapping("delete")
    public String deleteUser(Principal principal, Authentication authentication, HttpServletRequest requ, HttpServletResponse resp) throws IOException {
        User user = userService.findByEmail(principal.getName());
        userRoleRepository.deleteUserRolesByUserId(user.getId());
        userService.deleteById(user.getId());
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(requ, resp, authentication);
        return "redirect:/";
    }

    @GetMapping("orders")
    public String showOrders(){
        return USER_PACKAGE + "orders";
    }
}
