package zoowebapp.validator;

import zoowebapp.validator.impl.CountryValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CountryValidator.class)
public @interface CountryValid {

    String message() default "Country is not valid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
