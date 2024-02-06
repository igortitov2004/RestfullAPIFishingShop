package com.example.fishingshop.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user")
    private User user;

    @Column(name = "dateTime")
    private LocalDateTime localDateTime;
    @Column(name = "address")
    private String address;

    @OneToMany(mappedBy = "order")
    private List<ReelsOrder> reelsOrders;
    @OneToMany(mappedBy = "order")
    private List<RodsOrder> rodsOrders;
}
