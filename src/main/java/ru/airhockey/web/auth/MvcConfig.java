package ru.airhockey.web.auth;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
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

    /*
     * Подключение разных ресурсов
     * Для локальных файлов: выложить в resources/static файлы, затем подключить по аналогии с js папкой
     * Использование потом такое: /js/app.js
     * По дополнительным вопросам к farhutdinov
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (!registry.hasMappingForPattern("/webjars/**")) {
            registry.addResourceHandler("/webjars/**").addResourceLocations("/webjars/");
        }
//        if (!registry.hasMappingForPattern("/js/**")) {
            registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/");
//        }
    }
}
