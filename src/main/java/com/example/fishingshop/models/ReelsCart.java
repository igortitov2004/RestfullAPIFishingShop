package com.example.fishingshop.models;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "reels_carts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReelsCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user")
    private User user;

    @ManyToOne
    @JoinColumn(name="reel")
    private Reel reel;

    @Column(name = "amount")
    private Integer amount;


}
