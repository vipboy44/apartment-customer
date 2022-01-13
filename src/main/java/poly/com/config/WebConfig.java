package poly.com.config;


import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
@Configuration
public class WebConfig implements WebMvcConfigurer {
 
      
    @Bean(name = "localeResolver")
    public LocaleResolver getLocaleResolver()  {
    	SessionLocaleResolver resolver = new  SessionLocaleResolver();
    	resolver.setDefaultLocale(Locale.US);

    	return resolver;
    } 
     
    @Bean
    public LocaleChangeInterceptor LocaleChangeInterceptor() {
    	LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
    	lci.setParamName("lang");
    	return lci;
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
       registry.addInterceptor(LocaleChangeInterceptor());
    }
     
    @Bean(name = "messageSource")
    public MessageSource getMessageResource()  {
        ReloadableResourceBundleMessageSource messageResource= new ReloadableResourceBundleMessageSource();
         
        // Đọc vào file i18n/messages_xxx.properties
        // Ví dụ: i18n/messages_en.properties
        messageResource.setBasename("classpath:i18n/messages");
        messageResource.setDefaultEncoding("UTF-8");
        return messageResource;
    }
   
    
}
     
