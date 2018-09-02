package zoowebapp.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import zoowebapp.entity.*;
import zoowebapp.entity.enums.*;
import zoowebapp.repository.RoleRepository;
import zoowebapp.repository.UserRepository;
import zoowebapp.repository.UserRoleRepository;
import zoowebapp.service.*;
import zoowebapp.service.utils.StringUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;


@Component
public class Bootstrap implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AnimalService animalService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductSizeService productSizeService;

    @Autowired
    private DepartmentService departmentService;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            if (roleRepository.count() == 0) {
                generateRoles();
            }
            generateUser();
//            generateMyUser();
            generateUsers(60);
//            generateAnimals(60);
            generateProductSizes();
            generateCategoriesAndProducts(100);
            generateDepartments();
        }
    }

    private void generateRoles() {
        List<String> roles = new ArrayList<>(Arrays.asList("ROLE_ADMIN", "ROLE_USER", "ROLE_DOCTOR", "ROLE_WORKER"));
        roles.forEach(r -> {
            Role role = Role.builder().roleName(r).build();
            roleRepository.save(role);
        });
    }

    private void generateUser() {
        User user = User.builder()
                .email("admin@gmail.com")
                .password(passwordEncoder.encode("123"))
                .gender(UserGender.MALE)
                .firstName("admin")
                .lastName("admin")
                .createdAt(new Date())
                .birthDate(new Date())
                .telephone("380669052715")
                .country(Country.UA)
                .status(UserStatus.OFFLINE)
                .token("")
                .globalNumber(generateGlobalNumber())
                .imageUrl("http://res.cloudinary.com/dtn7opvqz/image/upload/v1529618431/user/default-user.png")
                .build();

        userRepository.save(user);
        userRoleRepository.save(UserRole.builder().user(user).role(roleRepository.getOne(1L)).build());
        userRoleRepository.save(UserRole.builder().user(user).role(roleRepository.getOne(2L)).build());

    }

    private void generateMyUser() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(1997, 0, 25);
        User user = User.builder()
                .email("nbohdan999@gmail.com")
                .password(passwordEncoder.encode("123"))
                .gender(UserGender.MALE)
                .firstName("Bohdan")
                .lastName("Nedostup")
                .createdAt(new Date())
                .birthDate(calendar.getTime())
//                .telephone(380669052716L)
                .telephone("380669052716")
                .country(Country.UA)
                .status(UserStatus.OFFLINE)
                .token("")
                .globalNumber(generateGlobalNumber())
                .imageUrl("http://res.cloudinary.com/dtn7opvqz/image/upload/v1529618431/user/default-user.png")
                .build();

        userRepository.save(user);
        userRoleRepository.save(UserRole.builder().user(user).role(roleRepository.getOne(2L)).build());
    }

    private String generateGlobalNumber() {
        String globalNumber = StringUtils.generateAlphaNumeric(20);
        if (userRepository.findByGlobalNumber(globalNumber) != null) {
            generateGlobalNumber();
        }

        return globalNumber;
    }

    private void generateUsers(Integer num) throws IOException {
        BufferedReader firstNameReader = new BufferedReader(new FileReader("src/main/java/first_name.txt"));
        BufferedReader lastNameReader = new BufferedReader(new FileReader("src/main/java/last_name.txt"));
        ArrayList<String> firstNameList = new ArrayList<>();
        ArrayList<String> lastNameList = new ArrayList<>();
        String firstName, lastName;
        while ((firstName = firstNameReader.readLine()) != null) {
            firstNameList.add(firstName);
        }
        while ((lastName = lastNameReader.readLine()) != null) {
            lastNameList.add(lastName);
        }
        Long telNum = 380669052717L;
        Boolean isMale = true;
        for (int i = 0; i < num; i++) {
            int firstNameNum = new Random().nextInt(5494);
            int lastNameNum = new Random().nextInt(88000);
            UserGender userGender = isMale ? UserGender.MALE : UserGender.FEMALE;
            isMale = !isMale;
            User user = User.builder()
                    .email(firstNameList.get(firstNameNum) + lastNameList.get(lastNameNum) + i + "@gmail.com")
                    .password(passwordEncoder.encode("123"))
                    .gender(userGender)
                    .firstName(firstNameList.get(firstNameNum))
                    .lastName(lastNameList.get(lastNameNum))
                    .createdAt(new Date())
                    .birthDate(new Date())
//                    .telephone(telNum++)
                    .telephone(String.valueOf(telNum++))
                    .country(Country.UA)
                    .status(UserStatus.OFFLINE)
                    .token("")
                    .globalNumber(generateGlobalNumber())
                    .imageUrl("http://res.cloudinary.com/dtn7opvqz/image/upload/v1529618431/user/default-user.png")
                    .build();
            userRepository.save(user);
            userRoleRepository.save(UserRole.builder().user(user).role(roleRepository.getOne(2L)).build());
        }
    }

    private void generateAnimals(int num) {
        boolean isMale = true;
        AnimalGender genders[] = AnimalGender.values();
        AnimalStatus statuses[] = AnimalStatus.values();
        for (int i = 0; i < num; i++) {
            int gender = new Random().nextInt(4);
            int status = new Random().nextInt(2);
            Animal animal = Animal.builder()
                    .createdAt(new Date())
                    .description("description" + i)
                    .gender(genders[gender])
                    .imageUrl("http://res.cloudinary.com/dtn7opvqz/image/upload/v1530181411/animal/default-animal.png")
                    .name("name" + i)
                    .status(statuses[status])
                    .wikiUrl("http://localhost:8080")
                    .globalNumber(generateGlobalNumber())
                    .illnessesHistory("Illness history " + i)
                    .build();
            animalService.save(animal);
            isMale = !isMale;
        }
    }


    private void generateCategoriesAndProducts(int num) {
        Category category1 = new Category("Cap");
        Category category2 = new Category("Trousers");
        Category category3 = new Category("Dress");
        Category category4 = new Category("Blouse");
        Category category5 = new Category("T-shirt");
        categoryService.save(category1);
        categoryService.save(category2);
        categoryService.save(category3);
        categoryService.save(category4);
        categoryService.save(category5);
        List<Category> categories = new ArrayList<>();
        categories.add(category1);
        categories.add(category2);
        categories.add(category3);
        categories.add(category4);
        categories.add(category5);

//        for (int i = 0; i < num; i++) {
//            int category = new Random().nextInt(5);
//            Product product = Product.builder()
//                    .category(categories.get(category))
//                    .createdAt(new Date())
//                    .description("description" + i)
//                    .imageUrl("http://res.cloudinary.com/dtn7opvqz/image/upload/v1530292716/product/default-product.jpg")
//                    .name("name" + i)
//                    .price(new BigDecimal(i + 100))
//                    .sizes(generateSizeList())
//                    .globalNumber(generateGlobalNumber())
//                    .build();
//            productService.save(product);
//        }
    }

    private void generateProductSizes(){
        ProductSize productSize = new ProductSize("XXS");
        productSizeService.save(productSize);
        productSize = new ProductSize("XS");
        productSizeService.save(productSize);
        productSize = new ProductSize("S");
        productSizeService.save(productSize);
        productSize = new ProductSize("M");
        productSizeService.save(productSize);
        productSize = new ProductSize("L");
        productSizeService.save(productSize);
        productSize = new ProductSize("XL");
        productSizeService.save(productSize);
        productSize = new ProductSize("XXL");
        productSizeService.save(productSize);
    }

    private List<ProductSize> generateSizeList(){
        List<ProductSize> sizes = productSizeService.findAll();
        int sizesNum = new Random().nextInt(sizes.size() - 2) + 1;
        List<ProductSize> productSizes = new ArrayList<>();
        for (int i = 0; i < sizesNum; i++){
            ProductSize productSize = sizes.get(new Random().nextInt(sizes.size() - 1));
            productSizes.add(productSize);
            sizes.remove(productSize);
        }
        return productSizes;
    }

    private void generateDepartments(){
        Department department = new Department("Arachnida", new ArrayList<>(), new ArrayList<>());
        departmentService.save(department);
        department = new Department("Arthropoda", new ArrayList<>(), new ArrayList<>());
        departmentService.save(department);
        department = new Department("Aves", new ArrayList<>(), new ArrayList<>());
        departmentService.save(department);
        department = new Department("Pisces", new ArrayList<>(), new ArrayList<>());
        departmentService.save(department);
        department = new Department("Mammalia", new ArrayList<>(), new ArrayList<>());
        departmentService.save(department);
        department = new Department("Reptilia", new ArrayList<>(), new ArrayList<>());
        departmentService.save(department);
    }
}
