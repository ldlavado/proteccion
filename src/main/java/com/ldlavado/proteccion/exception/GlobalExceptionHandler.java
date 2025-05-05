package com.ldlavado.proteccion.exception;

import com.ldlavado.proteccion.dto.response.APIResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<APIResponseDTO<Map<String, String>>> handleNoResourceFoundException(
            NoResourceFoundException ex,
            HttpServletRequest request) {

        Map<String, String> errorData = new HashMap<>();
        errorData.put("path", request.getRequestURI());

        APIResponseDTO<Map<String, String>> response = APIResponseDTO.<Map<String, String>>builder()
                .message("Resource not found")
                .data(errorData)
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(FibonacciBusinessException.class)
    public ResponseEntity<APIResponseDTO<Map<String, String>>> handleFibonacciBusinessException(
            FibonacciBusinessException ex,
            HttpServletRequest request) {

        Map<String, String> errorData = new HashMap<>();
        errorData.put("error", ex.getMessage());
        errorData.put("path", request.getRequestURI());

        APIResponseDTO<Map<String, String>> response = APIResponseDTO.<Map<String, String>>builder()
                .message("Business rule violation")
                .data(errorData)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIResponseDTO<Map<String, String>>> handleGenericException(
            Exception ex) {

        log.error("Unhandled exception occurred", ex);

        Map<String, String> error = new HashMap<>();
        error.put("message", ex.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                APIResponseDTO.<Map<String, String>>builder()
                        .message("Unexpected error occurred")
                        .data(error)
                        .build()
        );
    }

}
