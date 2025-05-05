package com.ldlavado.proteccion.service;

import com.ldlavado.proteccion.dto.response.FibonacciResponseDTO;

import java.util.List;

public interface FibonacciService {

    FibonacciResponseDTO generateSeriesFromTime(String time);

    List<FibonacciResponseDTO> getAllFibonacciSeries();

}
