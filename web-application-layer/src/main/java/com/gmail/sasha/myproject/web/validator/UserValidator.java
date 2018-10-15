package com.gmail.sasha.myproject.web.validator;

import com.gmail.sasha.myproject.service.model.UserDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;
@Component("userValidator")
public class UserValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return UserDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object object, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "surname", "user.surname.empty");
        ValidationUtils.rejectIfEmpty(errors, "name", "user.name.empty");
        ValidationUtils.rejectIfEmpty(errors, "email", "user.email.empty");
        ValidationUtils.rejectIfEmpty(errors, "password", "user.password.empty");
        UserDTO user = (UserDTO) object;

        Pattern pattern = Pattern.compile(
                "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
                Pattern.CASE_INSENSITIVE
        );
        if (!(pattern.matcher(user.getEmail()).matches())) {
            errors.rejectValue("email", "user.email.invalid");
        }
    }
}