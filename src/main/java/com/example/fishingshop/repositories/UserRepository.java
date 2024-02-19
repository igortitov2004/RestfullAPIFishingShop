package com.example.fishingshop.repositories;

import com.example.fishingshop.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsUserById(Long id);
    boolean existsUserByEmail(String email);
    Optional<User> findUserByEmail(String email);
}
