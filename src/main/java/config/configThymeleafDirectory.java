package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@Configuration
public class configThymeleafDirectory {
    @Bean
    public String title() {
        return "Foodshop";
    }

    @Bean
    public int adminRoleId() {return 2;}

    @Bean
    public int ManagerRoleId() {return 3;}

    @Bean
    public int userRoleId() {return 1;}
    @Bean
    public ClassLoaderTemplateResolver commonTemplateResolver() {
        ClassLoaderTemplateResolver yourTemplateResolver = new ClassLoaderTemplateResolver();
        yourTemplateResolver.setPrefix("templates/common/");
        yourTemplateResolver.setSuffix(".html");
        yourTemplateResolver.setTemplateMode(TemplateMode.HTML);
        yourTemplateResolver.setCharacterEncoding("UTF-8");
        yourTemplateResolver.setOrder(0); // this is iportant. This way springboot will listen to both places 0 and 1
        yourTemplateResolver.setCheckExistence(true);

        return yourTemplateResolver;
    }

    @Bean
    public ClassLoaderTemplateResolver adminTemplateResolver() {
        ClassLoaderTemplateResolver yourTemplateResolver = new ClassLoaderTemplateResolver();
        yourTemplateResolver.setPrefix("templates/admin/");
        yourTemplateResolver.setSuffix(".html");
        yourTemplateResolver.setTemplateMode(TemplateMode.HTML);
        yourTemplateResolver.setCharacterEncoding("UTF-8");
        yourTemplateResolver.setOrder(1); // this is iportant. This way springboot will listen to both places 0 and 1
        yourTemplateResolver.setCheckExistence(true);

        return yourTemplateResolver;
    }
}
