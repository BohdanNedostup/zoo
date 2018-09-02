package zoowebapp.validator;

import zoowebapp.validator.impl.TelephoneNumberNotStringValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TelephoneNumberNotStringValidator.class)
public @interface TelephoneNumberNotString {

    String message() default "Telephone number must have only numbers";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
