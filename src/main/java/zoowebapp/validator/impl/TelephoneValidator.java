package zoowebapp.validator.impl;

import org.springframework.beans.factory.annotation.Autowired;
import zoowebapp.entity.User;
import zoowebapp.service.UserService;
import zoowebapp.validator.TelephoneValid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TelephoneValidator implements ConstraintValidator<TelephoneValid, String> {

    @Autowired
    private UserService userService;

    @Override
    public void initialize(TelephoneValid constraintAnnotation) {

    }

    @Override
    public boolean isValid(String telephone, ConstraintValidatorContext context) {
        return userService.findByTelephone(telephone) == null;
    }
}
