package zoowebapp.dto.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import zoowebapp.entity.enums.Country;
import zoowebapp.entity.enums.UserGender;
import zoowebapp.entity.enums.UserStatus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class UserFilter {

    private String firstName;
    private String lastName;
    private String email;
    private Date birthDateFrom;
    private Date birthDateTo;
    private Date createdAtFrom;
    private Date createdAtTo;
    private String telephone;
    private Country country;
    private List<String> roles;
    private List<UserStatus> statuses;
    private List<UserGender> genders;
    private List<String> departments;

    private String sortBy;
    private String ascOrDesc;

}