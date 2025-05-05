package com.ldlavado.proteccion.repository;

import com.ldlavado.proteccion.entity.Fibonacci;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetTime;

@Repository
public interface FibonacciRepository extends JpaRepository<Fibonacci, Long> {
    boolean existsByTime(OffsetTime time);
}
