package ru.airhockey.web.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.airhockey.playingarea.model.PlayerPosition;
import ru.airhockey.replay.dao.GameReplayDAO;
import ru.airhockey.statistics.dao.GameHistoryDAO;
import ru.airhockey.statistics.dao.UserStatisticsDAO;
import ru.airhockey.statistics.entity.GameHistory;
import ru.airhockey.statistics.entity.UserStatistics;

import java.security.Principal;
import java.time.Instant;
import java.util.List;

@Controller
public class AuthController {
    @Autowired
    private AppUserDao appUserDao;

    @Autowired
    private AppUserValidator appUserValidator;

    @Autowired
    private GameHistoryDAO historyDAO;

    @Autowired
    private UserStatisticsDAO userStatisticsDAO;

    @Autowired
    private GameReplayDAO replayDAO;

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
        AppUser appUser = appUserDao.findUserAccount(loginedUser.getUsername());
        model.addAttribute( "UserQueueID", appUser.getId());
        return "waitingList";
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
        model.addAttribute("appUser", new AppUser());
        return "registerPage";
    }

    @PostMapping(value = "/j_spring_security_register")
    public String createEmployee(Model model,@ModelAttribute("appUser") @Validated AppUser appuser, BindingResult result, final RedirectAttributes redirectAttributes) {
        System.out.println(appuser.toString());

        if (result.hasErrors()) {
            model.addAttribute("appUser", appuser);
            model.addAttribute("errorMessage", "Error: " + "При регистрации возникли ошибки");
            return "registerPage";
        }

        AppUser newUser = null;
        try{
            appUserDao.registerUserAccount(appuser);
        }
        catch (Exception e){
            model.addAttribute("errorMessage", "Error: " + e.getMessage() );
            return "registerPage";
        }
        AppUser registeredUser = appUserDao.findUserAccount(appuser.getName());
        userStatisticsDAO.insertStatistics(registeredUser.getId());
        redirectAttributes.addFlashAttribute("flashUser", appuser);
        return "redirect:/registerSuccessful";
    }

    @RequestMapping(value = "/sockets", method = RequestMethod.GET)
    public String socket(Model model, Principal principal) {
        return "sockets";
    }

    @RequestMapping(value = "/demo", method = RequestMethod.GET)
    public String demo(Model model, Principal principal, @RequestParam String gameId) {
        model.addAttribute("gameId", gameId);
        return "demo";
    }

    @RequestMapping(value = "/game", method = RequestMethod.GET)
    public String game(Model model, Principal principal, @RequestParam int user1, @RequestParam int user2 , @RequestParam String gameID, @RequestParam PlayerPosition userPosition) {
        model.addAttribute("user1", user1);
        model.addAttribute("user2", user2);
        if ("bot".equals(gameID)) {
            gameID = gameID = String.valueOf(user1) + "_" + String.valueOf(user2) + "_" + Instant.now().toString();
        }
        model.addAttribute("gameID", gameID);
        model.addAttribute("userPosition", userPosition);
        String username1 = "Bot";
        String username2 = "Bot";
        if (user1 != -1) {
            AppUser appUser = appUserDao.findUserById(user1);
            username1 = appUser.getName();
        }
        if (user2 != -1) {
            AppUser appUser = appUserDao.findUserById(user2);
            username2 = appUser.getName();
        }
        model.addAttribute("userName1", username1);
        model.addAttribute("userName2", username2);
        return "game";
    }

    @RequestMapping(value = "/userStatistics", method = RequestMethod.GET)
    public String gameHistory(Model model, Principal principal) {
        User loginedUser = (User) ((Authentication) principal).getPrincipal();
        AppUser appUser = appUserDao.findUserAccount(loginedUser.getUsername());
//        userStatisticsDAO.insertStatistics(appUser.getId());
//        historyDAO.insertGame("demoPlay", appUser.getId());
        List<GameHistory> historyList = historyDAO.getGamesByIdUser(appUser.getId());
        for (GameHistory history: historyList) {
            if(history.getOpponent() != 0) {
                AppUser opponent = appUserDao.findUserById(history.getOpponent());
                history.setOpponentName(opponent.getName());
            } else history.setOpponentName("none");
        }
        UserStatistics statistics = userStatisticsDAO.getStatisticsByUserId(appUser.getId());
        model.addAttribute("gameHistory", historyList);
        model.addAttribute("userStatistics", statistics);
        model.addAttribute("username", appUser.getName());
        return "userStatistics";
    }

    @RequestMapping(value = "/waitingList", method = RequestMethod.GET)
    public String waitingList(Model model, Principal principal) {
        User loginedUser = (User) ((Authentication) principal).getPrincipal();
        AppUser appUser = appUserDao.findUserAccount(loginedUser.getUsername());
        model.addAttribute( "UserQueueID", appUser.getId());
        return "waitingList";
    }
}
