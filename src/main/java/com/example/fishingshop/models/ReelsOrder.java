package com.example.fishingshop.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "reels_orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReelsOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne
    @JoinColumn(name="reel")
    private Reel reel;
    @Column(name = "amount")
    private Integer amount;
    @ManyToOne
    @JoinColumn(name="order_obj")
    private Order order;
}
