package zoowebapp.dto;

import lombok.Data;
import zoowebapp.entity.Department;
import zoowebapp.entity.enums.UserStatus;
import zoowebapp.validator.EmailValid;
import zoowebapp.validator.TelephoneNumberNotString;
import zoowebapp.validator.TelephoneValid;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class UserDtoProfile extends UserDtoBase{

    @TelephoneNumberNotString
    @NotNull(message = "Telephone field can`t be empty")
    private String telephone;

    private String imageUrl;

    /**/
    private Department department;

    //
    private UserStatus status;
}