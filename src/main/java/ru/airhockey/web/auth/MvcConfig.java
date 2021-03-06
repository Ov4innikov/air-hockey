package ru.airhockey.web.auth;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
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
       registry.addViewController("/logoutSuccessful").setViewName("logoutSuccessfullPage");
        registry.addViewController("/registerSuccessful").setViewName("registerSuccessfulPage");
    }

    /*
     * Подключение разных ресурсов
     * Для локальных файлов: выложить в resources/static файлы, затем подключить по аналогии с js папкой
     * Использование потом такое: /js/app.js
     * По дополнительным вопросам к farhutdinov
     *
     * salakhov: Добавлен resourceChain(false) что бы заработал webjars-locator. Что бы подключать ресурсы без указания версий на странице.
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (!registry.hasMappingForPattern("/webjars/**")) {
            registry.addResourceHandler("/webjars/**").addResourceLocations("/webjars/").resourceChain(false);
        }
//        if (!registry.hasMappingForPattern("/js/**")) {
            registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/");
//        }
    }

    @Bean
    public MessageSource messageSource(){
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();

        messageSource.setBasename("classpath:validation");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}
