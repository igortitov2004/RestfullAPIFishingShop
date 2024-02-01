package com.example.fishingshop.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Entity
@Table(name = "Rods")
@Data
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
    @ManyToOne
    @JoinColumn(name="type_of_rod")
    private TypeOfRod type;
    @ManyToOne
    @JoinColumn(name="manufacturer")
    private Manufacturer manufacturer;

    @OneToMany(mappedBy = "rod")
    private List<RodsCart> rodsCarts;
}
