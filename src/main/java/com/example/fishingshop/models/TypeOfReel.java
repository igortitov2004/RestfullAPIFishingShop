package com.example.fishingshop.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "types_of_reels")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TypeOfReel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "type")
    private String type;
    @OneToMany(mappedBy = "type")
    private List<Reel> reels;
}
