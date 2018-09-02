package zoowebapp.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zoowebapp.dto.UserDtoAdmin;
import zoowebapp.entity.enums.Country;
import zoowebapp.entity.enums.UserGender;
import zoowebapp.entity.enums.UserStatus;
import zoowebapp.mapper.UserMapper;
import zoowebapp.repository.RoleRepository;
import zoowebapp.repository.UserRoleRepository;
import zoowebapp.service.DepartmentService;
import zoowebapp.service.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/manage/get/")
public class AdminRestApiController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("admin")
    private UserDtoAdmin getAdmin(Principal principal){
        UserDtoAdmin admin = UserMapper.convertUserToUserDtoAdmin(userService.findByEmail(principal.getName()));
        admin.setUserRoles(userRoleRepository.findRolesByUserId(admin.getId()));
        return admin;
    }

    @GetMapping("countries")
    private List<String> getCountries(){
        List<String> countries = new ArrayList<>();
        countries.add("");
        Arrays.asList(Country.values()).forEach(c -> countries.add(c.getCountry()));
        countries.remove(Country.SELECT_COUNTRY.getCountry());
        return countries;
    }

    @GetMapping("roles")
    private List<String> getRoles(){
        List<String> roles = new ArrayList<>();
        roleRepository.findAll().forEach(role -> roles.add(role.getRoleName()));
        return roles;
    }

    @GetMapping("statuses")
    private List<String> getStatuses(){
        List<String> statuses = new ArrayList<>();
        Arrays.asList(UserStatus.values()).forEach(userStatus -> statuses.add(userStatus.getStatus()));
        return statuses;
    }

    @GetMapping("genders")
    private List<String> getGenders(){
        List<String> genders = new ArrayList<>();
        Arrays.asList(UserGender.values()).forEach(gender -> genders.add(gender.getGenderName()));
        return genders;
    }

    @GetMapping("/departments")
    public List<String> getDepartments(){
        List<String> departments = new ArrayList<>();
        departmentService.findAll().forEach(department -> departments.add(department.getName()));
        return departments;
    }
}