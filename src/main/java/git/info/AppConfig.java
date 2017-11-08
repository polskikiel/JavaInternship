package git.info;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
public class AppConfig {
    @Bean
    public org.springframework.web.servlet.ViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/jsp/");
        resolver.setSuffix(".jsp");

        return resolver;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
