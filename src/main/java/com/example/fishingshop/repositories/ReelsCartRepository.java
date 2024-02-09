package com.example.fishingshop.repositories;

import com.example.fishingshop.models.ReelsCart;
import com.example.fishingshop.models.RodsCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReelsCartRepository extends JpaRepository<ReelsCart,Long> {
    List<ReelsCart> findByUserId(Long id);

    boolean existsReelsCartByUserId(Long id);

    boolean existsReelsCartById(Long id);

//    boolean existsReelsCartByReelId(Long id);
    void deleteReelsCartByUserId(Long id);

    Optional<ReelsCart> findReelsCartByUserIdAndReelId(Long idUser,Long idReel);

    void deleteAllByUserId(Long id);
}
