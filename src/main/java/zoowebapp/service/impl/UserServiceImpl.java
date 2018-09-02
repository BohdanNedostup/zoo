package zoowebapp.service.impl;

import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import zoowebapp.dto.filter.UserFilter;
import zoowebapp.entity.Department;
import zoowebapp.entity.Order;
import zoowebapp.entity.User;
import zoowebapp.entity.UserRole;
import zoowebapp.entity.enums.Country;
import zoowebapp.entity.enums.UserStatus;
import zoowebapp.mail.Mail;
import zoowebapp.repository.RoleRepository;
import zoowebapp.repository.UserRepository;
import zoowebapp.repository.UserRoleRepository;
import zoowebapp.service.CloudinaryService;
import zoowebapp.service.DepartmentService;
import zoowebapp.service.EmailService;
import zoowebapp.service.UserService;
import zoowebapp.service.utils.DateUtils;
import zoowebapp.service.utils.RandomToken;
import zoowebapp.service.utils.StringUtils;

import javax.persistence.criteria.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CloudinaryService cloudinaryService;
    private final String DEFAULT_USER_PHOTO_URL = "http://res.cloudinary.com/dtn7opvqz/image/upload/v1529618431/user/default-user.png";

    @Autowired
    private DepartmentService departmentService;

    @Override
    public void save(User user) {
        String password = user.getPassword();
        user.setPassword(passwordEncoder.encode(password));

        String token = RandomToken.generateToken(50);
        user.setStatus(UserStatus.ACTIVATION_PENDING);
        user.setToken(token);

        user.setGlobalNumber(generateGlobalNumber());

        user.setImageUrl(DEFAULT_USER_PHOTO_URL);
        userRepository.save(user);

        userRoleRepository.save(UserRole.builder().user(user).role(roleRepository.findRoleByName("ROLE_USER")).build());

        Mail mail = new Mail();
        mail.setTo(user.getEmail());
        mail.setSubject("You are successfully registered");
        mail.setContent("Please verify your account follow the link: "
                + "https://immense-springs-86257.herokuapp.com/verify?token=" + token
                + "&id=" + user.getId());
        emailService.sendMessage(mail);
    }

    @Override
    public User findById(Long id) {
        return userRepository.getOne(id);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void verifyAccount(String token, String id) {
        long userId = Long.valueOf(id);

        User user = userRepository.getOne(userId);
        if (user.getToken().equals(token)) {
            user.setStatus(UserStatus.OFFLINE);
            user.setToken(null);
            userRepository.save(user);
        }
    }

    @Override
    public void update(User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteById(Long id) {
        User user = userRepository.getOne(id);
        if (user.getImageUrl() != null && !user.getImageUrl().equals(DEFAULT_USER_PHOTO_URL)) {
            try {
                cloudinaryService.destroyFile("user/user" + id, ObjectUtils.emptyMap());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        userRoleRepository.deleteUserRolesByUserId(id);
        userRepository.deleteById(id);
    }

    private String generateGlobalNumber() {
        String globalNumber = StringUtils.generateAlphaNumeric(20);
        if (userRepository.findByGlobalNumber(globalNumber) != null) {
            globalNumber = generateGlobalNumber();
        }
        return globalNumber;
    }

    public User findByTelephone(String telephone) {
        return userRepository.findUserByTelephone(telephone);
    }

    @Override
    public Page<User> findUsersByPage(Pageable pageable) {
        return userRepository.findAll(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()));
    }

    public Page<User> findUsersByPageByFilter(UserFilter userFilter, Pageable pageable) {

        return userRepository.findAll(getSpecification(userFilter), PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()));
    }

    private Specification<User> getSpecification(UserFilter userFilter) {
        return new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicateFirstName = criteriaBuilder.like(root.get("firstName"), "%" + userFilter.getFirstName() + "%");
                Predicate predicateLastName = criteriaBuilder.like(root.get("lastName"), "%" + userFilter.getLastName() + "%");
                Predicate predicateEmail = criteriaBuilder.like(root.get("email"), "%" + userFilter.getEmail() + "%");
                Predicate predicateTelephone = criteriaBuilder.like(root.get("telephone"), "%" + userFilter.getTelephone() + "%");
                Predicate predicateCountry = userFilter.getCountry() == null ? criteriaBuilder.notEqual(root.get("country"), Country.SELECT_COUNTRY) : criteriaBuilder.equal(root.get("country"), userFilter.getCountry());

                Predicate predicateDepartment = criteriaBuilder.and();
                if (userFilter.getDepartments().size() != 0) {
                    List<Long> departmentsId = new ArrayList<>();
                    departmentService.findByNameList(userFilter.getDepartments()).forEach(department -> departmentsId.add(department.getId()));
                    Join<User, Department> userDepartmentJoin = root.join("department");
                    predicateDepartment = userDepartmentJoin.get("id").in(departmentsId);
                }


                Calendar calendarMin = Calendar.getInstance();
                Calendar calendarMax = Calendar.getInstance();
                calendarMin.set(1900, 0, 1);
                calendarMax.set(3000, 0, 1);

                userFilter.setCreatedAtTo(DateUtils.plusOneDay(userFilter.getCreatedAtTo()));
                userFilter.setBirthDateTo(DateUtils.plusOneDay(userFilter.getBirthDateTo()));

                Predicate predicateBirthDate =
                        userFilter.getBirthDateFrom() == null ?
                                userFilter.getBirthDateTo() == null ?
                                        criteriaBuilder.between(root.get("birthDate"), calendarMin.getTime(), calendarMax.getTime()) :
                                        criteriaBuilder.lessThanOrEqualTo(root.get("birthDate"), userFilter.getBirthDateTo()) :
                                userFilter.getBirthDateTo() == null ?
                                        criteriaBuilder.greaterThanOrEqualTo(root.get("birthDate"), userFilter.getBirthDateFrom()) :
                                        criteriaBuilder.between(root.get("birthDate"), userFilter.getBirthDateFrom(), userFilter.getBirthDateTo());
                Predicate predicateCreatedAt =
                        userFilter.getCreatedAtFrom() == null ?
                                userFilter.getCreatedAtTo() == null ?
                                        criteriaBuilder.between(root.get("createdAt"), calendarMin.getTime(), calendarMax.getTime()) :
                                        criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), userFilter.getCreatedAtTo()) :
                                userFilter.getCreatedAtTo() == null ?
                                        criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), userFilter.getCreatedAtFrom()) :
                                        criteriaBuilder.between(root.get("createdAt"), userFilter.getCreatedAtFrom(), userFilter.getCreatedAtTo());


                Predicate predicateGender = root.get("gender").in(userFilter.getGenders());
                Predicate predicateStatus = root.get("status").in(userFilter.getStatuses());

                List<Long> usersIdListFilteredByRoleNames = userRoleRepository.findUsersByRolesList(userFilter.getRoles());
                Predicate predicateRoles = root.get("id").in(usersIdListFilteredByRoleNames);

                switch(userFilter.getSortBy()){
                    case "id": {
                        criteriaQuery.orderBy(userFilter.getAscOrDesc().equals("asc") ? criteriaBuilder.asc(root.get("id")) : criteriaBuilder.desc(root.get("id")));
                        break;
                    }
                    case "firstName": {
                        criteriaQuery.orderBy(userFilter.getAscOrDesc().equals("asc") ? criteriaBuilder.asc(root.get("firstName")) : criteriaBuilder.desc(root.get("firstName")));
                        break;
                    }
                    case "lastName": {
                        criteriaQuery.orderBy(userFilter.getAscOrDesc().equals("asc") ? criteriaBuilder.asc(root.get("lastName")) : criteriaBuilder.desc(root.get("lastName")));
                        break;
                    }
                    case "email": {
                        criteriaQuery.orderBy(userFilter.getAscOrDesc().equals("asc") ? criteriaBuilder.asc(root.get("email")) : criteriaBuilder.desc(root.get("email")));
                        break;
                    }
                    case "birthDate": {
                        criteriaQuery.orderBy(userFilter.getAscOrDesc().equals("asc") ? criteriaBuilder.asc(root.get("birthDate")) : criteriaBuilder.desc(root.get("birthDate")));
                        break;
                    }
                    case "createdAt": {
                        criteriaQuery.orderBy(userFilter.getAscOrDesc().equals("asc") ? criteriaBuilder.asc(root.get("createdAt")) : criteriaBuilder.desc(root.get("createdAt")));
                        break;
                    }
                }

                return criteriaBuilder.and(predicateFirstName, predicateLastName, predicateEmail, predicateTelephone, predicateCountry, predicateBirthDate, predicateCreatedAt, predicateGender, predicateStatus, predicateDepartment, predicateRoles);
            }
        };
    }
}