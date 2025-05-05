package com.ldlavado.proteccion.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetTime;

@Entity
@Table(name = "fibonacci")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Fibonacci {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fibonacciId;

    @Column(nullable = false, unique = true)
    private OffsetTime time;

    private String beans;

    private int length;

    @Column(length = 1000)
    private String fibonacciCreated;
}
