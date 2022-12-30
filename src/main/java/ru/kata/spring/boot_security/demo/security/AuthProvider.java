package ru.kata.spring.boot_security.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.service.ServiceUserImp;

import java.util.Collections;

//Не использую этот класс, делал проверку кастомной проверки пароля
@Component
public class AuthProvider implements AuthenticationProvider {

    private final ServiceUserImp serviceUserImp;

    @Autowired
    public AuthProvider(ServiceUserImp serviceUserImp) {
        this.serviceUserImp = serviceUserImp;
    }


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        UserDetails userDetails = serviceUserImp.loadUserByUsername(name);
        String password = authentication.getCredentials().toString(); //Получаем пароль
        if(!password.equals(userDetails.getPassword())){
            throw new BadCredentialsException("Incorrect password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, password, Collections.emptyList());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
