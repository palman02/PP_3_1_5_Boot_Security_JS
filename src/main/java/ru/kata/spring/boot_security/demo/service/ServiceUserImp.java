package ru.kata.spring.boot_security.demo.service;

import org.hibernate.Session;
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
import javax.persistence.EntityManager;
import java.util.*;


@Service
@Transactional(readOnly = true)
public class ServiceUserImp implements ServiceUser, UserDetailsService {

    private final EntityManager entityManager;

    private final UserRepositories userRepositories;
    private final RoleRepositories roleRepositories;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public ServiceUserImp(EntityManager entityManager, UserRepositories userRepositories, RoleRepositories roleRepositories, PasswordEncoder passwordEncoder) {
        this.entityManager = entityManager;
        this.userRepositories = userRepositories;
        this.roleRepositories = roleRepositories;
        this.passwordEncoder = passwordEncoder;
    }


    //Создаю админа и роли
    @PostConstruct
    @Transactional
    public void doInit() {
        if (userRepositories.findAll().size() == 0) {
            User user = new User("Main_admin", "admin", "admin", 0, "admin");
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            Role roleUser = new Role("ROLE_USER");
            Role roleAdmin = new Role("ROLE_ADMIN");
            roleRepositories.save(roleAdmin);
            roleRepositories.save(roleUser);
            userRepositories.save(user);
            user.getRoleList().add(roleRepositories.findRoleByRole("ROLE_ADMIN"));
            userRepositories.save(user);

        }
    }

    @Override
    public List<User> findAll() {
        try (Session session = entityManager.unwrap(Session.class)) {
            return session.createQuery("select u from User u LEFT join fetch u.roleList", User.class).getResultList();
        }
//        return userRepositories.findAll();
    }

    @Override
    public User findOne(int id) {
        return userRepositories.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoleList().add(roleRepositories.findRoleByRole("ROLE_USER"));
        userRepositories.save(user);
    }

    @Override
    @Transactional
    public void update(int id, User updatedUser) {
        updatedUser.setId(id);
        updatedUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        updatedUser.getRoleList().add(roleRepositories.findRoleByRole("ROLE_USER")); // Делается для того чтобы, при обновлении юзера его список не оказался пустым
        userRepositories.save(updatedUser);
    }

    @Override
    @Transactional
    public void deleteUser(int id) {
        userRepositories.deleteById(id);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepositories.findByUserName(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found!");
        }
        return user.get();
    }
}
