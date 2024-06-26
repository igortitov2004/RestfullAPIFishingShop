package com.example.fishingshop.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "reels")
@Data
@Builder
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


    @OneToMany(mappedBy = "reel",cascade = CascadeType.ALL)
    private List<ReelsCart> reelsCarts;

    @OneToMany(mappedBy = "reel",cascade = CascadeType.ALL)
    private List<ReelsOrder> reelsOrders;

    @OneToOne(cascade = CascadeType.ALL,mappedBy = "reel")
    private ImageReelsLink imageReelsLink;
}
