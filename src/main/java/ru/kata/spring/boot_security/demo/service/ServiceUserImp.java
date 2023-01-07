package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepositories;
import ru.kata.spring.boot_security.demo.repositories.UserRepositories;
import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;


@Service
@Transactional(readOnly = true)
public class ServiceUserImp implements ServiceUser {


    private final UserRepositories userRepositories;
    private final RoleRepositories roleRepositories;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public ServiceUserImp(UserRepositories userRepositories, RoleRepositories roleRepositories, PasswordEncoder passwordEncoder) {
        this.userRepositories = userRepositories;
        this.roleRepositories = roleRepositories;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public List<User> findAll() {
        return userRepositories.findAll();
    }

    @Override
    public User findOne(int id) {
        return userRepositories.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getRoleList().contains(roleRepositories.findRoleByRole("ROLE_ADMIN"))) {
            user.getRoleList().add(roleRepositories.findRoleByRole("ROLE_USER"));
        }
        userRepositories.save(user);
    }

    @Override
    @Transactional
    public void update(int id, User updatedUser) {
        updatedUser.setId(id);
        updatedUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        userRepositories.save(updatedUser);
    }

    @Override
    @Transactional
    public void deleteUser(int id) {
        userRepositories.deleteById(id);
    }

    @Override
    public Set<Role> getRole() {
        return new HashSet<>(roleRepositories.findAll());
    }
}
