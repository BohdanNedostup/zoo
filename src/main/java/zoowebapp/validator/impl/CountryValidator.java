package zoowebapp.validator.impl;

import zoowebapp.entity.enums.Country;
import zoowebapp.validator.CountryValid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CountryValidator implements ConstraintValidator<CountryValid, Country> {

    @Override
    public void initialize(CountryValid constraintAnnotation) {

    }

    @Override
    public boolean isValid(Country country, ConstraintValidatorContext context) {
        return country != Country.SELECT_COUNTRY;
    }
}
