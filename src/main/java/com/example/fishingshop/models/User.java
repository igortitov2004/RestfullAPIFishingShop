package com.example.fishingshop.models;


import com.example.fishingshop.DTOs.orders.OrderDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "surname")
    private String surname;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "patronymic")
    private String patronymic;

    @OneToMany(mappedBy = "user")
    private List<RodsCart> rodsCarts;
    @OneToMany(mappedBy = "user")
    private List<ReelsCart> reelsCarts;

//    @OneToMany(mappedBy = "user")
//    private List<RodsOrder> rodsOrders;


    @OneToMany(mappedBy = "user")
    private List<Order> orders;

//    @OneToMany(mappedBy = "user")
//    private List<ReelsOrder> reelsOrders;


}
