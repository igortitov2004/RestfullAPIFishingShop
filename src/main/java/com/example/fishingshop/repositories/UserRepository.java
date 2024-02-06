package com.example.fishingshop.repositories;

import com.example.fishingshop.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsUserById(Long id);
}
