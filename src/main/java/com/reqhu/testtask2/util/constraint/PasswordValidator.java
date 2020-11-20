package com.reqhu.testtask2.util.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<Password, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && value.matches(
                /*
                * (?=.*[A-ZА-ЯЁ]) - как минимум одна заглавная буква, русская или английская;
                * (?=.*[0-9]) - как минимум одна цифра;
                * (?=\S+$) - без пробелов.
                * */
                "^(?=.*[A-ZА-ЯЁ])(?=.*[0-9])(?=\\S+$).{2,}$"
        );
    }

}
