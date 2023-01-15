package ru.kata.spring.boot_security.demo.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepositories;
import ru.kata.spring.boot_security.demo.repositories.UserRepositories;

import javax.annotation.PostConstruct;

@Component
public class Init {

    private final UserRepositories userRepositories;
    private final RoleRepositories roleRepositories;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public Init(UserRepositories userRepositories, RoleRepositories roleRepositories, PasswordEncoder passwordEncoder) {
        this.userRepositories = userRepositories;
        this.roleRepositories = roleRepositories;
        this.passwordEncoder = passwordEncoder;
    }

    //Создаю админа и роли
    @PostConstruct
    @Transactional
    public void doInit() {
        if (userRepositories.findAll().size() == 0) {
            Role roleUser = new Role("ROLE_USER");
            Role roleAdmin = new Role("ROLE_ADMIN");
            User user = new User();
            user.setAge(0);
            user.setEmail("admin@mail.ru");
            user.setFirstName("admin");
            user.setLastName("admin");
            user.setPassword(passwordEncoder.encode("admin"));

            roleRepositories.save(roleAdmin);
            roleRepositories.save(roleUser);
            userRepositories.save(user);
            user.getRoles().add(roleRepositories.findRoleByRole("ROLE_ADMIN"));
            user.getRoles().add(roleRepositories.findRoleByRole("ROLE_USER"));
            userRepositories.save(user);
        }
    }
}
