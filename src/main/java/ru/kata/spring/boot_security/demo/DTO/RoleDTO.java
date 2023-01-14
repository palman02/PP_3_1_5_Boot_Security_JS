package ru.kata.spring.boot_security.demo.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Set;

public class RoleDTO {

    private int id;
    private String role;

    private Set<UserDTO> users;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Set<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(Set<UserDTO> users) {
        this.users = users;
    }
}
