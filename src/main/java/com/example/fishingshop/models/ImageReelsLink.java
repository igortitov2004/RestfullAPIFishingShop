package com.example.fishingshop.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "image_reel_links")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageReelsLink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "link")
    private String link;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "reel_obj")
    private Reel reel;

}