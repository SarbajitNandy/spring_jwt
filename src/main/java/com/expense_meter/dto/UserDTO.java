package com.expense_meter.dto;

import com.expense_meter.model.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.UUID;

public class UserDTO {

    private String uuid;
    @NotNull(message = "Name can't be null")
    private String name;
    @NotNull(message = "Password can't be null")
    private String password;

    @Email(message = "Invalid Email Id")
    private String email;

    private int role;

    public UserDTO(String name, String email, String password, int role, String uuid) {
//        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.uuid=uuid;
    }

//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public static UserDTO createUserDto(User user) {
        return new UserDTO(user.getName(),user.getEmail(), user.getPassword(),user.getRole(), user.getUuid());
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "uuid='" + uuid + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                '}';
    }
}
