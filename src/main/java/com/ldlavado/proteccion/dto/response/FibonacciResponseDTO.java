package com.ldlavado.proteccion.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response with generated Fibonacci series and metadata")
public class FibonacciResponseDTO {

    @Schema(description = "Original time used to generate the sequence", example = "12:23:04")
    private String time;

    @Schema(description = "Seed values extracted from the minute", example = "[2, 3]")
    private List<Integer> seeds;

    @Schema(description = "Generated Fibonacci series in descending order", example = "[89, 58, 31, 27]")
    private List<Integer> sequence;

}