package com.example.fishingshop.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "rods_orders")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RodsOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne
    @JoinColumn(name="rod")
    private Rod rod;
    @Column(name = "amount")
    private Integer amount;
    @ManyToOne
    @JoinColumn(name="order_obj")
    private Order order;
}
