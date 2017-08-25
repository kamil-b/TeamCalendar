package common;

import org.springframework.context.annotation.Configuration;

import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {
    
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/tips").setViewName("tips");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/logout").setViewName("logout");
        registry.addViewController("/addtip").setViewName("addtip");
        registry.addViewController("/tipadded").setViewName("tipadded");
        registry.addViewController("/board").setViewName("board");
        registry.addViewController("/details").setViewName("details");
        registry.addViewController("/addevent").setViewName("addevent");
    }
    
}
