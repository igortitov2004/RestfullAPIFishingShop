package com.example.fishingshop.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "types_of_rods")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TypeOfRod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "type")
    private String type;
    @OneToMany(mappedBy = "type")
    private List<Rod> rods;
}
