package com.example.repository;

import com.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {

    @Query(value = "select * from user where email = :email and password = :password", nativeQuery = true)
    Optional<User> userLogin(String email, String password);

    @Query(value = "select * from user where user_id = :id", nativeQuery = true)
    User findUserById(int id);
}
