package ru.airhockey.web.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Map;

@Controller
public class AuthController {

    @RequestMapping(value = "/userInfo", method = RequestMethod.GET)
    public String userInfo(Model model, Principal principal) {
        String userName = principal.getName();
        System.out.println("User Name: " + userName);
        User loginedUser = (User) ((Authentication) principal).getPrincipal();
        String userInfo = WebUtils.toString(loginedUser);
        model.addAttribute("userInfo", userInfo);
        return "userInfoPage";
    }

    @RequestMapping(value = "/accessDenied", method = RequestMethod.GET)
    public String accessDenied(Model model, Principal principal) {
        if (principal != null) {
            User loginedUser = (User) ((Authentication) principal).getPrincipal();
            String userInfo;
            userInfo = WebUtils.toString(loginedUser);
            model.addAttribute("userInfo", userInfo);
            String message = "Hi " + principal.getName() //
                    + "<br> You do not have permission to access this page!";
            model.addAttribute("message", message);
        }

        return "accessDeniedPage";
    }

    @RequestMapping(value = "/register",method = RequestMethod.GET)
    public String register(Model model,Principal principal){
        model.addAttribute("appuser", new AppUser());
        return "registerPage";
    }

    @PostMapping(value = "/j_spring_security_register")
    public String createEmployee(@ModelAttribute AppUser appuser) {
        System.out.println(appuser.toString());
        return "loginPage";
    }

    @RequestMapping(value = "/sockets", method = RequestMethod.GET)
    public String socket(Model model, Principal principal) {
        return "sockets";
    }

    @RequestMapping(value = "/demo", method = RequestMethod.GET)
    public String demo(Model model, Principal principal) {
        return "demo";
    }
}
