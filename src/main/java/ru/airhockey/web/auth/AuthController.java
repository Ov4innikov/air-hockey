package ru.airhockey.web.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.airhockey.statistics.dao.GameHistoryDAO;
import ru.airhockey.statistics.entity.GameHistory;

import java.security.Principal;
import java.util.List;

@Controller
public class AuthController {
    @Autowired
    private AppUserDao appUserDao;

    @Autowired
    private AppUserValidator appUserValidator;

    @Autowired
    GameHistoryDAO historyDAO;

    @InitBinder
    protected void initBinder(WebDataBinder dataBinder){
        Object target = dataBinder.getTarget();
        if(target == null){
            return;
        }
        System.out.println("Target="+target);

        if(target.getClass() == AppUser.class) {
            dataBinder.setValidator(appUserValidator);
        }
    }

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
    public String createEmployee(@ModelAttribute AppUser appuser,final RedirectAttributes redirectAttributes) {
        System.out.println(appuser.toString());
        AppUser newUser = null;
        try{
            appUserDao.registerUserAccount(appuser);
        }
        catch (Exception e){

        }
        redirectAttributes.addFlashAttribute("flashUser", appuser);
        return "redirect:/registerSuccessful";
    }

    @RequestMapping(value = "/sockets", method = RequestMethod.GET)
    public String socket(Model model, Principal principal) {
        return "sockets";
    }

    @RequestMapping(value = "/demo", method = RequestMethod.GET)
    public String demo(Model model, Principal principal) {
        return "demo";
    }

    @RequestMapping(value = "/game", method = RequestMethod.GET)
    public String game(Model model, Principal principal) {
        return "game";
    }

    @RequestMapping(value = "/userStatistics", method = RequestMethod.GET)
    public String gameHistory(Model model, Principal principal) {
        User loginedUser = (User) ((Authentication) principal).getPrincipal();
        AppUser appUser = appUserDao.findUserAccount(loginedUser.getUsername());
        List<GameHistory> historyList = historyDAO.getGamesByIdUser(appUser.getId());
        model.addAttribute("gameHistory", historyList);
        return "userStatistics";
    }
}
