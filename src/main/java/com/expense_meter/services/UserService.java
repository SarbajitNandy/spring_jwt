package com.expense_meter.services;

import com.expense_meter.dto.UserDTO;
import com.expense_meter.model.User;
import com.expense_meter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserDTO insertUser(UserDTO user) throws Exception {
        Optional<User> option = userRepository.findByEmail(user.getEmail());
//        if (option.isEmpty()) {
//
//        } else {
//            throw new Exception(user.getName() + " already present.");
//        }
        try {
            option.get();
            throw new Exception(user.getName() + " already present.");
        } catch (Exception e) {
            User newUser = User.createUser(user);
            System.out.println("new Id " + newUser.getId());
            userRepository.save(newUser);
            return UserDTO.createUserDto(newUser);
        }

    }

    public UserDTO findById(int id) throws Exception{
        Optional<User> op= userRepository.findById(id);
        User u = op.get();
        return UserDTO.createUserDto(u);
    }
    public UserDTO findByEmail(String email) throws NoSuchElementException {
        Optional<User> op= userRepository.findByEmail(email);
        User u = op.get();
        return UserDTO.createUserDto(u);
    }
    public UserDTO findByUuid(String uuid) throws NoSuchElementException {
        Optional<User> op= userRepository.findByUuid(uuid);
        User u = op.get();
        return UserDTO.createUserDto(u);
    }

    public UserDTO update(UserDTO user) throws NoSuchElementException {
        Optional<User> op= userRepository.findByUuid(user.getUuid());
        User u = op.get();
        // TODO User Entity based on DTO
        return UserDTO.createUserDto(u);
    }

}
