package com.github.KostyaTr.hospital.web.config;

import com.github.KostyaTr.hospital.service.config.ServiceConfig;
import com.github.KostyaTr.hospital.web.controller.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;

import java.util.Locale;

@Configuration
@EnableWebMvc
public class WebConfig {

    private ServiceConfig serviceConfig;

    public WebConfig(ServiceConfig serviceConfig) {
        this.serviceConfig = serviceConfig;
    }

    @Bean
    public AppointmentController appointmentController(){
        return new AppointmentController(serviceConfig.userService(), serviceConfig.queueService());
    }

    @Bean
    public AuthorizationController authorizationController(){
        return new AuthorizationController(serviceConfig.authorizationService(), serviceConfig.registrationService());
    }

    @Bean
    public InfoController infoController(){
        return new InfoController(serviceConfig.userService(), serviceConfig.medDoctorService());
    }

    @Bean
    public InpatientController inpatientController(){
        return new InpatientController(serviceConfig.medDoctorService());
    }

    @Bean
    public PatientController patientController(){
        return new PatientController(serviceConfig.medDoctorService());
    }

    @Bean
    public UserController userController(){
        return new UserController(serviceConfig.userService());
    }

    @Bean
    public UrlBasedViewResolver tilesViewResolver(){
        UrlBasedViewResolver resolver = new UrlBasedViewResolver();
        resolver.setViewClass(TilesView.class);
        return resolver;
    }

    @Bean
    public TilesConfigurer tilesConfigurer(){
        final TilesConfigurer tilesConfigurer = new TilesConfigurer();
        tilesConfigurer.setDefinitions("/WEB-INF/tiles.xml");
        return tilesConfigurer;
    }

    @Bean
    public ReloadableResourceBundleMessageSource messageSource(){
        ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();
        source.setBasename("classpath:i18n/messages");
        source.setDefaultEncoding("UTF-8");

        return source;
    }

    @Bean
    public CookieLocaleResolver localeResolver(){
        CookieLocaleResolver resolver = new CookieLocaleResolver();
        resolver.setDefaultLocale(Locale.forLanguageTag("en"));
        resolver.setCookieName("LocaleCookie");
        resolver.setCookieMaxAge(3600);
        return resolver;
    }

}
