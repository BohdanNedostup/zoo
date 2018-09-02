package zoowebapp.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import zoowebapp.validator.EmailValid;
import zoowebapp.validator.TelephoneNumberNotString;
import zoowebapp.validator.TelephoneValid;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class UserDtoRegister extends UserDtoBase{

    @TelephoneValid
    @TelephoneNumberNotString
    @NotNull(message = "Telephone field can`t be empty")
    private String telephone;

    @NotEmpty(message = "Email field can`t be empty")
    @Email(message = "Email is not valid")
    @EmailValid
    private String email;
}