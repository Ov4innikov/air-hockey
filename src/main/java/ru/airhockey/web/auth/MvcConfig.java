package ru.airhockey.web.auth;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@Configuration
public class MvcConfig implements WebMvcConfigurer {
    public void addViewControllers(ViewControllerRegistry registry){
       registry.addViewController("/").setViewName("welcomePage");
       registry.addViewController("/main").setViewName("welcomePage");
       registry.addViewController("/login").setViewName("loginPage");
       registry.addViewController("/register").setViewName("registerPage");
       registry.addViewController("/logoutSuccessful").setViewName("logoutSuccessfullPage");
    }
}
