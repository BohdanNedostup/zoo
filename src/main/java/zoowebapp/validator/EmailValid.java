package zoowebapp.validator;

import zoowebapp.validator.impl.EmailValidator;
import zoowebapp.validator.impl.PasswordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailValidator.class)
public @interface EmailValid {

    String message() default "User with such email already exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
