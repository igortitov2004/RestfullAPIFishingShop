package com.example.fishingshop.repositories;

import com.example.fishingshop.models.RodsCart;
import com.example.fishingshop.models.RodsOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RodsOrderRepository extends JpaRepository<RodsOrder,Long> {

    List<RodsOrder> findRodsOrderByOrderId(Long id);
    boolean existsRodsOrderById(Long id);

    boolean existsRodsOrderByOrderId(Long id);


}
