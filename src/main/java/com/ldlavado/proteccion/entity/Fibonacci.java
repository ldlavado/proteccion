package com.ldlavado.proteccion.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

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

    private LocalTime date;

    private String beans;

    private int length;

    @Column(length = 1000)
    private String fibonacciCreated;

}
