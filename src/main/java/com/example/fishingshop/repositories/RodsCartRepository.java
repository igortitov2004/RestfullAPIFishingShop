package com.example.fishingshop.repositories;

import com.example.fishingshop.models.ReelsCart;
import com.example.fishingshop.models.RodsCart;
import com.example.fishingshop.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RodsCartRepository extends JpaRepository<RodsCart,Long> {
    List<RodsCart> findByUserId(Long id);

    boolean existsRodsCartByUserId(Long id);

    boolean existsRodsCartById(Long id);

    void deleteRodsCartByUserId(Long id);

    Optional<RodsCart> findRodsCartByUserIdAndRodId(Long idUser, Long idRod);
    void deleteAllByUserId(Long id);


}
