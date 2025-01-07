package com.example.fishingshop.repositories;

import com.example.fishingshop.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findOrderByUserId(Long id);
    boolean existsOrderById(Long id);
    boolean existsOrderByUserId(Long id);
}
