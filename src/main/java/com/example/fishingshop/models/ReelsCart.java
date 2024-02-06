package com.example.fishingshop.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "reels_carts")
@Data
@NoArgsConstructor
@AllArgsConstructor
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
