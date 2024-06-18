package com.example.fishingshop.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "Rods")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Rod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "length")
    private Integer length;
    @Column(name = "weight")
    private Integer weight;
    @Column(name = "testLoad")
    private Integer testLoad;
    @Column(name = "price")
    private Double price;
    @ManyToOne
    @JoinColumn(name="type_of_rod")
    private TypeOfRod type;
    @ManyToOne
    @JoinColumn(name="manufacturer")
    private Manufacturer manufacturer;
    @OneToMany(mappedBy = "rod", cascade = CascadeType.ALL)
    private List<RodsCart> rodsCarts;
    @OneToMany(mappedBy = "rod",cascade = CascadeType.ALL)
    private List<RodsOrder> rodsOrders;
    @OneToOne(cascade = CascadeType.ALL,mappedBy = "rod")
    private ImageRodsLink imageRodsLink;
}
