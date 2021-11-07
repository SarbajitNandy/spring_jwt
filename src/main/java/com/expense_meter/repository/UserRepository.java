package com.expense_meter.repository;

import com.expense_meter.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {
    public Optional<User> findByEmail(String email) ;
    public Optional<User> findByUuid(String uuid) ;

}
