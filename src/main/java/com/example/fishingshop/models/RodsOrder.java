package com.example.fishingshop.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "rods_orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RodsOrder {
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
    @Column(name = "date")
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    private Date date;
    @Column(name = "address")
    private String address;
}
