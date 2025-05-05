package com.ldlavado.proteccion.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Request object to generate a Fibonacci series based on time input")
public class FibonacciRequestDTO {

    @Schema(description = "Time in HH:mm:ss format used to generate the series", example = "12:23:04", required = true)
    private String time;

}
