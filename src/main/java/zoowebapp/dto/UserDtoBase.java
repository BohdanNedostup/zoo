package zoowebapp.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import zoowebapp.entity.enums.Country;
import zoowebapp.entity.enums.UserGender;
import zoowebapp.validator.CountryValid;
import zoowebapp.validator.EmailValid;
import zoowebapp.validator.PasswordValid;
import zoowebapp.validator.TelephoneValid;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@PasswordValid
public class UserDtoBase {

    @NotEmpty(message = "First name field can`t be empty")
    private String firstName;

    @NotEmpty(message = "Last name field can`t be empty")
    private String lastName;

    @NotEmpty(message = "Password field can`t be empty")
    private String password;

    @NotEmpty(message = "Password confirm field can`t be empty")
    private String passwordConfirm;

    @CountryValid(message = "Country field can`t be empty")
    private Country country;

    private UserGender userGender;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Date field can`t be empty")
    private Date birthDate;
}
