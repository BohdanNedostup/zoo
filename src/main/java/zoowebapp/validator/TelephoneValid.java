package zoowebapp.validator;

import zoowebapp.validator.impl.TelephoneValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TelephoneValidator.class)
public @interface TelephoneValid {

    String message() default "Such telephone number is already exist";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
