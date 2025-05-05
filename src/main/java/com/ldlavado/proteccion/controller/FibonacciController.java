package com.ldlavado.proteccion.controller;

import com.ldlavado.proteccion.dto.request.FibonacciRequestDTO;
import com.ldlavado.proteccion.dto.response.APIResponseDTO;
import com.ldlavado.proteccion.dto.response.FibonacciResponseDTO;
import com.ldlavado.proteccion.service.FibonacciService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/fibonacci")
public class FibonacciController {

    @Autowired
    private FibonacciService fibonacciService;

    @Operation(summary = "Generate a custom Fibonacci series based on time")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Series generated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = APIResponseDTO.class)))
    })
    @PostMapping
    public ResponseEntity<APIResponseDTO<FibonacciResponseDTO>> generateFibonacci(
            @RequestBody FibonacciRequestDTO request
    ) {
        FibonacciResponseDTO response = fibonacciService.generateSeriesFromTime(request.getTime());

        return ResponseEntity.ok(
                APIResponseDTO.<FibonacciResponseDTO>builder()
                        .message("Fibonacci series generated successfully")
                        .data(response)
                        .build()
        );
    }

    @Operation(summary = "Retrieve all generated Fibonacci series")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List returned successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = APIResponseDTO.class)))
    })
    @GetMapping
    public ResponseEntity<APIResponseDTO<List<FibonacciResponseDTO>>> getAllFibonacciSeries() {
        List<FibonacciResponseDTO> list = fibonacciService.getAllFibonacciSeries();

        return ResponseEntity.ok(
                APIResponseDTO.<List<FibonacciResponseDTO>>builder()
                        .message("List of all generated Fibonacci series")
                        .data(list)
                        .build()
        );
    }

}
