package zoowebapp.validator.impl;

import org.springframework.beans.factory.annotation.Autowired;
import zoowebapp.service.UserService;
import zoowebapp.validator.EmailValid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<EmailValid, String> {

    @Autowired
    private UserService userService;

    @Override
    public void initialize(EmailValid constraintAnnotation) {

    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return userService.findByEmail(email) == null;
    }
}
