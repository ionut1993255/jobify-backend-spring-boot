package dev.ionut.jobify.exception;

import dev.ionut.jobify.domain.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleNotFound(ResourceNotFoundException e) {
        HttpStatus httpStatusNotFound = HttpStatus.NOT_FOUND;

        ApiResponse<Object> apiResponse = new ApiResponse<>(
                e.getMessage(),
                httpStatusNotFound
        );

        return new ResponseEntity<>(apiResponse, httpStatusNotFound);
    }
}