package com.example.fishingshop.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user")
    private User user;
    @Column(name = "dateTime")
    private String localDateTime;
    @Column(name = "address")
    private String address;
    @OneToMany(mappedBy = "order",cascade = CascadeType.REMOVE)
    private List<ReelsOrder> reelsOrders;
    @OneToMany(mappedBy = "order",cascade = CascadeType.REMOVE)
    private List<RodsOrder> rodsOrders;
    @PrePersist
    private void setDate(){
        localDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
    }
}
