package zoowebapp.validator.impl;

import zoowebapp.dto.UserDtoBase;
import zoowebapp.dto.UserDtoRegister;
import zoowebapp.validator.PasswordValid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class PasswordValidator implements ConstraintValidator<PasswordValid, UserDtoBase> {

    @Override
    public void initialize(PasswordValid constraintAnnotation) {

    }

    @Override
    public boolean isValid(UserDtoBase userDtoBase, ConstraintValidatorContext context) {
        return userDtoBase.getPassword().equals(userDtoBase.getPasswordConfirm());
//        return true;
    }
}
