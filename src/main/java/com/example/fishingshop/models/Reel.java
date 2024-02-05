package com.example.fishingshop.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "reels")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private Double price;
    @ManyToOne
    @JoinColumn(name="type_of_rod")
    private TypeOfReel type;
    @ManyToOne
    @JoinColumn(name="manufacturer")
    private Manufacturer manufacturer;

//    @OneToMany(mappedBy = "rod")
//    private List<RodsCart> rodsCarts;
//
//    @OneToMany(mappedBy = "rod")
//    private List<RodsOrder> rodsOrders;
}
