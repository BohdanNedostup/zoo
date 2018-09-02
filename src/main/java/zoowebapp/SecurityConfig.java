package zoowebapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(this.userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/static/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .cors().and()
                .formLogin()
                    .usernameParameter("emailParam")
                    .passwordParameter("passwordParam")
                    .loginPage("/login")
    //                .loginProcessingUrl("/login")
                    .successHandler(new CustomAuthenticationSuccessHandler())
                    .failureUrl("/login?fail=true")
                .and()
                    .logout()
                    .logoutUrl("/logout")
                    .deleteCookies("JSESSIONID", "RememberMeCookie")
                    .invalidateHttpSession(true)
                .and()
                    .authorizeRequests()
                    .antMatchers("/api/manage/**").hasRole("ADMIN")
                    .antMatchers("/api/animals/**").hasAnyRole("ADMIN", "DOCTOR")
                    .antMatchers("/admin/manage/**").hasRole("ADMIN")
                    .antMatchers("/admin/animals/**").hasAnyRole("DOCTOR", "ADMIN")
                    .antMatchers("/user/**").hasRole("USER")
                    .antMatchers("/api/user/**").hasRole("USER")
                    .anyRequest().permitAll()
                .and()
                    .exceptionHandling().accessDeniedPage("/403")
                .and()
                    .sessionManagement()
                .maximumSessions(1)
                .and().and()
                    .rememberMe()
                    .rememberMeParameter("rememberMe")
                    .key("super secret key")
                    .rememberMeCookieName("RememberMeCookie")
                    .tokenValiditySeconds(365*24*60*60);
    }
}
