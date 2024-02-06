package com.example.fishingshop.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "rods_carts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RodsCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="user")
    private User user;
    @ManyToOne
    @JoinColumn(name="rod")
    private Rod rod;
    @Column(name = "amount")
    private Integer amount;

}
