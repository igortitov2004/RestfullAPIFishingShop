package com.example.fishingshop.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "rods_carts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
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
