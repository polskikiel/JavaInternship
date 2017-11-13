package git.info;

import git.info.interceptors.ErrorHandler;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@AllArgsConstructor
public class MvcConfig extends WebMvcConfigurerAdapter{
    ErrorHandler errorHandler;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(errorHandler).addPathPatterns("/**");
    }
}
