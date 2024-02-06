package com.example.fishingshop.repositories;

import com.example.fishingshop.models.ReelsCart;
import com.example.fishingshop.models.RodsCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReelsCartRepository extends JpaRepository<ReelsCart,Long> {
    List<ReelsCart> findByUserId(Long id);

    boolean existsReelsCartByUserId(Long id);

    boolean existsReelsCartById(Long id);
    void deleteReelsCartByUserId(Long id);
}
