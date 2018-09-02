package zoowebapp.validator;

import zoowebapp.validator.impl.PasswordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
public @interface PasswordValid {

    String message() default "Don`t confirm password";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
