package fi.tamk.beerbros.kaljakauppa.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import fi.tamk.beerbros.kaljakauppa.components.user.User;
import fi.tamk.beerbros.kaljakauppa.components.user.UserRepository;
import java.io.InputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.web.authentication.*;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final String API_ROOT_URL = "/kaljakauppa";
    private final String LOGIN_URL = API_ROOT_URL + "/login";

   @Autowired
   UserRepository ur;
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers(
                        HttpMethod.GET,
                        //Almost everything is temporarly open for business
                        "/",
                        "/*",
                        //"/**/**/*",
                        //"/**/*",
                        "/**/*.html",
                        "/**/*.js",
                        "/**/*.css",
                        "/**/*.jpg",
                        "/kaljakauppa",
                        //"/kaljakauppa/users",
                        //"/kaljakauppa/users/**",
                        //"/kaljakauppa/users/*/*",
                        "/kaljakauppa/beers",
                        "/kaljakauppa/beers/*",
                        "/kaljakauppa/beers/*/*",
                        "/kaljakauppa/beers/*/*/*",
                        "/kaljakauppa/beers/*/*/*/*",
                        "/kaljakauppa/beers/*/*/*/*/*",
                        "/kaljakauppa/reviews",
                        "/kaljakauppa/reviews/*",
                        "/kaljakauppa/reviews/*/*",
                        "/kaljakauppa/reviews/*/*/*",
                        "/kaljakauppa/reviews/*/*/*/*",
                        "/kaljakauppa/reviews/*/*/*/*/*",
                        "/kaljakauppa/manufacturers",
                        "/kaljakauppa/manufacturers/*",
                        "/kaljakauppa/manufacturers/*/*",
                        "/kaljakauppa/manufacturers/*/*/*",
                        "/kaljakauppa/manufacturers/*/*/*/*",
                        "/kaljakauppa/manufacturers/*/*/*/*/*",
                        "/kaljakauppa/countries",
                        "/kaljakauppa/countries/*",
                        "/kaljakauppa/countries/*",
                        "/kaljakauppa/countries/*/*",
                        "/kaljakauppa/countries/*/*/*",
                        "/kaljakauppa/countries/*/*/*/*",
                        "/kaljakauppa/countries/*/*/*/*/*",
                        "/kaljakauppa/beertypes",
                        "/kaljakauppa/beertypes/*",
                        "/kaljakauppa/beertypes/*/*",
                        "/kaljakauppa/beertypes/*/*/*",
                        "/kaljakauppa/beertypes/*/*/*/*",
                        "/kaljakauppa/beertypes/*/*/*/*/*",
                        "/kaljakauppa/images/*")
                .permitAll()
                .antMatchers(
                        HttpMethod.POST, 
                        LOGIN_URL,
                        "/kaljakauppa/users",
                        "/kaljakauppa/images",
                        "/kaljakauppa/images/",
                        "/kaljakauppa/images/*",
                        "/kaljakauppa/images/**",
                        "/kaljakauppa/images/**/*",
                        "/kaljakauppa/reviews",             //TMP
                        "/kaljakauppa/reviews/*",           //TMP
                        "/kaljakauppa/reviews/*/*",         //TMP
                        "/kaljakauppa/reviews/*/*/*",       //TMP
                        "/kaljakauppa/reviews/*/*/*/*",     //TMP
                        "/kaljakauppa/reviews/*/*/*/*/*",   //TMP
                        "/kaljakauppa/users/**")            //TMP
                .permitAll()
                .antMatchers(
                        HttpMethod.PUT, 
                        LOGIN_URL,
                        "/kaljakauppa/users/**",            //TMP
                        "/kaljakauppa/reviews",             //TMP
                        "/kaljakauppa/reviews/*",           //TMP
                        "/kaljakauppa/reviews/*/*",         //TMP
                        "/kaljakauppa/reviews/*/*/*",       //TMP
                        "/kaljakauppa/reviews/*/*/*/*",     //TMP
                        "/kaljakauppa/reviews/*/*/*/*/*")   //TMP
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new LoginFilter(LOGIN_URL,
                        authenticationManager()),
                        UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new AuthenticationFilter(),
                        UsernamePasswordAuthenticationFilter.class);
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder am) throws Exception {
        am.inMemoryAuthentication().withUser("admin").password("password")
                .roles("ADMIN");
        
        ObjectMapper mapper = new ObjectMapper();
        InputStream isUsers = new ClassPathResource("users.json").getInputStream();
        User[] users = mapper.readValue(isUsers, User[].class);
        
        for(User u : users) {
            ur.save(u);
            am.inMemoryAuthentication().withUser(u.getUsername()).password(u.getPassword())
                .roles("USER");    
        }
    }
}
