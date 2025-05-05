package com.ldlavado.proteccion.dto.response;

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
@Schema(description = "Generic API response wrapper")
public class APIResponseDTO<T> {

    @Schema(description = "Informational message about the response", example = "Fibonacci series generated successfully")
    private String message;

    @Schema(description = "Response data (can be a single object or list)")
    private T data;

}
