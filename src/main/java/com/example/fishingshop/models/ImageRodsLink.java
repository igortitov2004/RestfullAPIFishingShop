package com.example.fishingshop.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "image_rod_links")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageRodsLink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "link")
    private String link;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "rod_obj")
    private Rod rod;

}
