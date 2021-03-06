package app.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final SuccessUserHandler successUserHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/**", "/login", "/css/*", "/img/*",
                        "/js/*", "/api/flight/**",
                        "/api/aircraft/**", "/api/category",
                        "/api/destinations", "api/registration",
                        "/api/passenger", "/api/search",
                        "/api/seat", "/api/ticket", "/api/user").permitAll()
                .antMatchers("/swagger-ui.html/**",
                        "/api/admin", "/api/airlineManager",
                         "/api/role").hasAuthority("ADMIN")
                .anyRequest().authenticated();
                //.anyRequest().permitAll();
        http.formLogin()
                .successHandler(successUserHandler)
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
}