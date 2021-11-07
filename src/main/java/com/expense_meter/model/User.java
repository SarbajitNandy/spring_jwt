package com.expense_meter.model;

import com.expense_meter.dto.UserDTO;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    private int id;

    @Column(unique = true,nullable = false)
    private String uuid;

    private String name;
    @Column(unique = true, nullable = false)
    private String email;
    private String password;
    private int role;

    public User() {
    }

    public String getUuid() {
        return uuid;
    }

    private void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public static User createUser(UserDTO user) {
        User newU = new User();
        newU.setName(user.getName());
        newU.setEmail(user.getEmail());
        newU.setRole(user.getRole());
        newU.setPassword(user.getPassword());
        newU.setUuid(UUID.randomUUID().toString());
        return newU;
    }

    public void update(UserDTO userDTO) {
        this.setName(userDTO.getName());
        this.setRole(userDTO.getRole());
    }
}
