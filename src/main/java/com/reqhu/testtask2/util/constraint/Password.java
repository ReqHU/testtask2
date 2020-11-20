package com.reqhu.testtask2.util.constraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
public @interface Password {

    String message() default
            "в пароле не должно быть пробелов, " +
            "должна быть как минимум одна заглавная буква " +
            "и как минимум одна цифра";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
