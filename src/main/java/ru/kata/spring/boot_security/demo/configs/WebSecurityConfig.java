package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import ru.kata.spring.boot_security.demo.service.ServiceUserImp;


@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    //private final AuthProvider authProvider;

    private final ServiceUserImp serviceUserImp;

    private final SuccessUserHandler successUserHandler;

    private final MvcConfig mvcConfig;

    @Autowired
    public WebSecurityConfig(ServiceUserImp serviceUserImp, SuccessUserHandler successUserHandler, MvcConfig mvcConfig) {
        this.serviceUserImp = serviceUserImp;
        this.successUserHandler = successUserHandler;
        this.mvcConfig = mvcConfig;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/login", "/error", "/registration").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/**").hasRole("USER")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/process_login")
                .successHandler(successUserHandler)
                .failureUrl("/login?error")
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login");
    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //настраивает аутенфикацию
        // auth.authenticationProvider(authProvider); - кастомная проверка пользователя
        auth.userDetailsService(serviceUserImp).passwordEncoder(mvcConfig.passwordEncoder()); //так можно делать если наш класс имплеменит UserDetailsService
    }

    // аутентификация inMemory
//    @Bean
//    @Override
//    public UserDetailsService userDetailsService() {
//        UserDetails user =
//                User.withDefaultPasswordEncoder()
//                        .username("user")
//                        .password("user")
//                        .roles("USER")
//                        .build();
//
//        return new InMemoryUserDetailsManager(user);
//    }


}