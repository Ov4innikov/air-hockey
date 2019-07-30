package ru.airhockey.web.auth;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.ValidationUtils;

@Component
public class AppUserValidator implements Validator {

    private EmailValidator emailValidator = EmailValidator.getInstance();
    //private UserDetailServiceImpl userService;

    @Autowired
    private AppUserDao appUserDao;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz == AppUser.class;
    }

    public void validate(Object target, Errors errors) {
        AppUser appUser = (AppUser) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName", "NotEmpty.appUserForm.userName");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty.appUserForm.email");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty.appUserForm.password");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
        if (appUser.getName().length() < 6 || appUser.getName().length() > 32) {
            errors.rejectValue("username", "Size.userForm.username");
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");

    }
}
