package com.example.fishingshop.repositories;

import com.example.fishingshop.models.ReelsOrder;
import com.example.fishingshop.models.RodsOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReelsOrderRepository extends JpaRepository<ReelsOrder,Long> {

    boolean existsReelsOrderById(Long id);

}
