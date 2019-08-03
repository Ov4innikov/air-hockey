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
        System.out.println("!!!!!! VALIDATION");
        AppUser appUser = (AppUser) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty.appUser.name");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "login", "NotEmpty.appUser.login");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty.appUser.password");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "passwordConfirm", "NotEmpty.appUser.passwordConfirm");
//
//        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
//        if (appUser.getName().length() < 6 || appUser.getName().length() > 32) {
//            errors.rejectValue("name", "NotEmpty.appUser.name");
//        }
//        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");

        if (!errors.hasErrors()) {
            if (!appUser.getPasswordConfirm().equals(appUser.getPassword())) {
                errors.rejectValue("passwordConfirm", "Match.appUser.passwordConfirm");
            }
        }

    }
}
