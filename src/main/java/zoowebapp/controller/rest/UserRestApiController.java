package zoowebapp.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zoowebapp.dto.UserDtoAdmin;
import zoowebapp.dto.filter.UserFilter;
import zoowebapp.entity.User;
import zoowebapp.entity.UserRole;
import zoowebapp.entity.enums.Country;
import zoowebapp.entity.enums.UserGender;
import zoowebapp.entity.enums.UserStatus;
import zoowebapp.mapper.UserMapper;
import zoowebapp.repository.RoleRepository;
import zoowebapp.repository.UserRoleRepository;
import zoowebapp.service.DepartmentService;
import zoowebapp.service.UserService;
import zoowebapp.service.utils.DateUtils;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/manage/users")
public class UserRestApiController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("")
    private Page<UserDtoAdmin> showAllUsers(@PageableDefault Pageable pageable,
                                            @RequestParam String firstName,
                                            @RequestParam String lastName,
                                            @RequestParam String email,
                                            @RequestParam String telephone,
                                            @RequestParam String country,
                                            @RequestParam String birthDateFrom,
                                            @RequestParam String birthDateTo,
                                            @RequestParam String createdAtFrom,
                                            @RequestParam String createdAtTo,
                                            @RequestParam List<String> statuses,
                                            @RequestParam List<String> roles,
                                            @RequestParam List<String> genders,
                                            @RequestParam List<String> departments,
                                            @RequestParam String sortBy,
                                            @RequestParam String ascOrDesc) {

        UserFilter userFilter = new UserFilter(firstName, lastName, email,
                DateUtils.convertStringToDate(birthDateFrom), DateUtils.convertStringToDate(birthDateTo),
                DateUtils.convertStringToDate(createdAtFrom), DateUtils.convertStringToDate(createdAtTo),
                telephone, convertStringToCountry(country), validateRolesList(roles), convertStringListToUserStatusList(statuses),
                convertStringListToUserGenderList(genders), departments, sortBy, ascOrDesc);

        Page<User> userPage = userService.findUsersByPageByFilter(userFilter, pageable);
        List<UserDtoAdmin> users;
        users = userPage
                .stream()
                .map(user -> UserMapper.convertUserToUserDtoAdmin(user))
                .collect(Collectors.toList());
        users.forEach(u -> u.setUserRoles(userRoleRepository.findRolesByUserId(u.getId())));
        return new PageImpl<>(users, pageable, userPage.getTotalElements());
    }

    @GetMapping("/{id}")
    private UserDtoAdmin showUser(@PathVariable Long id) {
        return UserMapper.convertUserToUserDtoAdmin(userService.findById(id));
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/")
    private ResponseEntity<Void> updateUser(@RequestBody UserDtoAdmin userDtoAdmin) {
        User user = userService.findById(userDtoAdmin.getId());
        if (user != null) {
            User updateUser = UserMapper.convertUserDtoAdminToUser(userDtoAdmin);
            updateUser.setPassword(user.getPassword());
            updateUser.setDepartment(departmentService.findByName(userDtoAdmin.getDepartment()));
            userService.update(updateUser);
            List<String> roles = userDtoAdmin.getUserRoles();
            userRoleRepository.deleteUserRolesByUserId(user.getId());
            for (String role : roles) {
                userRoleRepository.save(UserRole.builder().user(updateUser).role(roleRepository.findRoleByName(role)).build());
            }
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private Country convertStringToCountry(String country) {
        if (country == "") {
            return null;
        } else {
            Country countries[] = Country.values();
            for (int i = 0; i < countries.length; i++) {
                if (countries[i].getCountry().equals(country)) {
                    return countries[i];
                }
            }
            return null;
        }
    }

    private List<UserStatus> convertStringListToUserStatusList(List<String> statuses) {
        if (statuses.size() == 0) {
            return Arrays.asList(UserStatus.values());
        } else {
            UserStatus userStatuses[] = UserStatus.values();
            ArrayList<UserStatus> resultStatusList = new ArrayList<>();
            for (int i = 0; i < userStatuses.length; i++) {
                for (int j = 0; j < statuses.size(); j++) {
                    if (statuses.get(j).equals(userStatuses[i].getStatus())) {
                        resultStatusList.add(userStatuses[i]);
                    }
                }
            }
            return resultStatusList;
        }
    }

    private List<UserGender> convertStringListToUserGenderList(List<String> genders) {
        if (genders.size() == 0) {
            return Arrays.asList(UserGender.values());
        } else {
            UserGender userGenders[] = UserGender.values();
            List<UserGender> resultGenderList = new ArrayList<>();
            for (int i = 0; i < userGenders.length; i++) {
                for (int j = 0; j < genders.size(); j++) {
                    if (genders.get(j).equals(userGenders[i].getGenderName())) {
                        resultGenderList.add(userGenders[i]);
                    }
                }
            }
            return resultGenderList;
        }
    }

    private List<String> validateRolesList(List<String> roles) {
        if (roles.size() == 0) {
            roleRepository.findAll().forEach(role -> roles.add(role.getRoleName()));
        }
        return roles;
    }
}


