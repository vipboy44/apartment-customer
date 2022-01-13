package poly.com.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    /* ------------------------------------ WebSecurityConfig -------------------------------- */

   @Autowired
   UserDetailServiceImpl userDetailService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    /* ----------------------------------------------------------------------- */

    @Override /* ------------cung cap account cho spring security  -----------------*/
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
    }

    
    /*  ------------------ config CorsOrigin ---------------------- */  
    @Bean 
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.addAllowedOrigin("http://localhost:5000"); 
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("GET");
        configuration.addAllowedMethod("POST");
        configuration.addAllowedMethod("PUT");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    
    
    @Override  /* --------------- configure HttpSecurity --------------- */
    protected void configure(HttpSecurity http) throws Exception {
    	http.rememberMe().key("uniqueAndSecret").tokenValiditySeconds(86400); 
        http.csrf().disable();
        http.headers().cacheControl();    // khong cho browser tu dong cache
       
        http
        	.cors()
        	.and()
        	.authorizeRequests()   	 	
        	.antMatchers("/assets/**").permitAll()
        	.antMatchers("/").permitAll()
        	.antMatchers("/api/**").permitAll()
        	.antMatchers("/resetpassword/**").permitAll()
        	.antMatchers("/trang-chu/**").permitAll()
            .antMatchers("/can-ho").hasAnyRole("USER")
        	.anyRequest().authenticated() 
            .and()                
        	.formLogin()                                      
           		.loginPage("/login").permitAll()
           		.loginProcessingUrl("/login-now")                    
           		.usernameParameter("username")                 
           		.passwordParameter("password")               
           		.defaultSuccessUrl("/can-ho")               
           	.and()               		
          	.logout()                                         
           		.invalidateHttpSession(true)                       
           		.clearAuthentication(true)                         
           		.deleteCookies("JSESSIONID")                      
           		.logoutUrl("/logout")                             
           		.logoutSuccessUrl("/trang-chu").permitAll(); 

    }
       

}