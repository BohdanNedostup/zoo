package zoowebapp.validator.impl;

import zoowebapp.validator.TelephoneNumberNotString;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TelephoneNumberNotStringValidator implements ConstraintValidator<TelephoneNumberNotString, String> {

    @Override
    public void initialize(TelephoneNumberNotString constraintAnnotation) {

    }

    @Override
    public boolean isValid(String telephoneNumber, ConstraintValidatorContext context) {
        try{
            Long.parseLong(telephoneNumber, 10);
            return true;
        } catch (Exception e){
            return false;
        }
    }
}
