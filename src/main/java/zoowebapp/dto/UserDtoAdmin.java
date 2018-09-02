package zoowebapp.dto;

import lombok.Data;
import zoowebapp.entity.Department;
import zoowebapp.entity.enums.Country;
import zoowebapp.entity.enums.UserGender;
import zoowebapp.entity.enums.UserStatus;

import java.util.Date;
import java.util.List;

@Data
public class UserDtoAdmin {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private List<String> userRoles;

    private String imageUrl;

    private UserGender gender;

    private Date birthDate;

    private String telephone;

    private Country country;

    private String department;

    private UserStatus status;

    private Date createdAt = new Date();

    private String token;

    private String globalNumber;
}
